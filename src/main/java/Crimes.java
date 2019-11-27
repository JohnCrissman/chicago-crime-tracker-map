import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Crimes {
    private List<Crime> crimes; //list of loaded crimes for the past 2 weeks
    private List<CrimeRelativeToAddress> crimesRelativeTo;
    private double radius;  // in lat/long units
    private Address relativeAddress;

    public Crimes() throws ParseException, java.text.ParseException, IOException {
        String url = "https://data.cityofchicago.org/resource/ijzp-q8t2.json";
        int numOfweeks = 2;

        query(url, numOfweeks);
    }

    public List<Crime> getAllCrimes(){
        return this.crimes;
    }
    public List<CrimeRelativeToAddress> getCrimesRelativeTo(){
        return this.crimesRelativeTo;
    }

    public List<CrimeRelativeToAddress> getCrimesWithinRadius(double radius, String address) throws IOException, ParseException {
        Distance.LatLong latLong = Distance.LatLongHelper.getLatLonFromGoogleAPI(address);
        this.relativeAddress = new Address(latLong.latitude, latLong.longitude, address);
        this.radius = radius*(1);// TODO: DONE!!  Did the conversion in the distance class <-- John: make conversion of the radius from miles to the corresponding unit that lat/lon use.
        keepCrimesWithinRadius();
        return this.crimesRelativeTo;
    }

    private void keepCrimesWithinRadius(){
        // method that uses the loaded crimes to filter them within the radius of selected address
        this.crimesRelativeTo = this.crimes.stream()
                .map(CrimeRelativeToAddress::new)
                .peek(cRel -> cRel.setProximity(this.relativeAddress))
                .filter(cRel -> Distance.LatLongHelper.isWithinRadius(cRel.getAddress(), this.relativeAddress, this.radius))
                .collect(toList());
    }

    public String getFullURL(String url, int numOfPastWeeks){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime previous = today.minus(numOfPastWeeks, ChronoUnit.WEEKS);
        String today2 = today.toString().split("\\.")[0];
        String previous2 = previous.toString().split("\\.")[0];

        String url_dateRange = "$where=date between '" + previous2+ "' and '" + today2 + "'";
        String fullUrl = url+"?"+url_dateRange;
        return fullUrl;
    }


    public void query(String url, int numOfPastWeeks) throws IOException, ParseException, java.text.ParseException {
        String fullUrl = getFullURL(url, numOfPastWeeks);

        JSONArray jsonArr = APITalker.getArrayResponse(fullUrl, false);
        List<Crime> listOfCrimes = createCrimeList(jsonArr);
        this.crimes = listOfCrimes;
    }

    public static Crime createCrime(JSONObject j) throws java.text.ParseException {
        String sDate = (String) j.get("date");
        String type = (String)j.get("primary_type");
        String typeDescription = (String)j.get("description");
        String latitude = (String)j.get("latitude");
        String longitude = (String)j.get("longitude");
        String block = (String)j.get("block");
        return new Crime(type, typeDescription, latitude,
                longitude, sDate, block);
    }

    public static List<Crime> createCrimeList(JSONArray jsonArr) throws java.text.ParseException {
        List<Crime> listOfCrimes = new ArrayList<>();

        for(int i = 0; i < jsonArr.size(); i++){
            JSONObject json1 = (JSONObject) jsonArr.get(i);
            Crime newCrime = createCrime(json1);
            listOfCrimes.add(newCrime);
        }
        return listOfCrimes;
    }

    // Sort by date(newest).
    // Then sort by type, street, and block #.
    public List<CrimeRelativeToAddress> filterByA(){
        Comparator<CrimeRelativeToAddress> dateComparator =
                (c1,c2) -> {
                    int dateCompare = c2.getDate().compareTo(c1.getDate());
                    int typeCompare = c1.getType().compareTo(c2.getType());
                    int streetCompare = c1.getAddress().getStreet().compareTo(c2.getAddress().getStreet());
                    int blockCompare = c1.getAddress().getBlock().compareTo(c2.getAddress().getBlock());
                    if(dateCompare==0 && typeCompare==0 && streetCompare ==0){
                        return ((blockCompare==0)) ? streetCompare : blockCompare;
                    }
                    else if(dateCompare==0 && typeCompare == 0){
                        return ((streetCompare == 0)) ? typeCompare : streetCompare;
                    }
                    else if(dateCompare==0){
                        return ((typeCompare == 0)) ? dateCompare : typeCompare;
                    }
                    else{
                        return dateCompare;
                    }
                };
        List<CrimeRelativeToAddress> list = this.crimesRelativeTo;
        List<CrimeRelativeToAddress> newList = list.stream()
                .sorted(dateComparator)
                .collect(toList());
        return newList;
    }

    // Sort by location (alpha by street)
    public List<CrimeRelativeToAddress> filterByB(){
        Comparator<CrimeRelativeToAddress> locationComparator =
                Comparator.comparing(c -> c.getAddress().getStreet());
        List<CrimeRelativeToAddress> list = this.crimesRelativeTo;
        List<CrimeRelativeToAddress> newList = list.stream()
                .sorted(locationComparator)
                .collect(toList());
        return newList;
    }

    // Sort by type (alpha)
    // Then sort by date (newest), street, and block #.
    public List<CrimeRelativeToAddress> filterByC(){
        Comparator<CrimeRelativeToAddress> typeComparator =
                (c1,c2) -> {
                    int dateCompare = c2.getDate().compareTo(c1.getDate());
                    int typeCompare = c1.getType().compareTo(c2.getType());
                    int streetCompare = c1.getAddress().getStreet().compareTo(c2.getAddress().getStreet());
                    int blockCompare = c1.getAddress().getBlock().compareTo(c2.getAddress().getBlock());
                    if(typeCompare==0 && dateCompare==0 && streetCompare ==0){
                        return ((blockCompare==0)) ? streetCompare : blockCompare;
                    }
                    else if(typeCompare==0 && dateCompare == 0){
                        return ((streetCompare == 0)) ? dateCompare : streetCompare;
                    }
                    else if(typeCompare==0){
                        return ((dateCompare == 0)) ? typeCompare : dateCompare;
                    }
                    else{
                        return typeCompare;
                    }
                };
        List<CrimeRelativeToAddress> list = this.crimesRelativeTo;
        List<CrimeRelativeToAddress> newList = list.stream()
                .sorted(typeComparator)
                .collect(toList());
        return newList;
    }



    public List<CrimeRelativeToAddress> filterByD(){

        return new LinkedList<CrimeRelativeToAddress>();
    }

}
