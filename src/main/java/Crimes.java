import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Crimes {
    private List<Crime> crimes; //list of loaded crimes for the past 2 weeks
    private List<CrimeRelativeToAddress> crimesRelativeTo;
    private double radius;  // in lat/long units
    private Address relativeAddress;

    public Crimes() throws ParseException, IOException {
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

    public void setCrimesWithinRadius(double radius, String address) throws IOException, NotARadiusException, NotAnAddressException {
        /*
        *  sets the instance variable crimesRelativeToAddress to the filtered specified by the user
        *  throws:
        *  IOException: if there is an issue with the server (API)
        *  NotAnAddressException if there are no results found from the google API
        *  NotARadiusException if there is a radius that has value 0 or less
        * */
        if (radius <= 0) throw new NotARadiusException("Not a radius: "+ radius);
        this.relativeAddress = AddressHelper.getAddressFromGoogleAPI(address);
        this.radius = radius;
        keepCrimesWithinRadius();
    }

    private void keepCrimesWithinRadius(){
        // method that uses the loaded crimes to filter them within the radius of selected address
        this.crimesRelativeTo = this.crimes.stream()
                .map(CrimeRelativeToAddress::new)
                .peek(cRel -> cRel.setProximity(this.relativeAddress))
                .filter(cRel -> AddressHelper.isWithinRadius(cRel.getAddress(), this.relativeAddress, this.radius))
                .collect(toList());
    }

    private String getFullURL(String url, int numOfPastWeeks){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime previous = today.minus(numOfPastWeeks, ChronoUnit.WEEKS);
        String today2 = today.toString().split("\\.")[0];
        String previous2 = previous.toString().split("\\.")[0];

        String url_dateRange = "date between '" + previous2+ "' and '" + today2 + "'";
        String fullUrl = url + url_dateRange;
        System.out.println(fullUrl);
        System.out.println(url);
        try {
            return url + "?$where=" + URLEncoder.encode(url_dateRange, "UTF-8");
        }catch(UnsupportedEncodingException e){
            System.out.println("oops, i did it again!");
        }
        return "";
//        return "https://data.cityofchicago.org/resource/ijzp-q8t2.json?$where=date between '2019-09-01T12:00:00' and '2019-11-01T12:00:00'";
    }


    private void query(String url, int numOfPastWeeks) throws IOException, ParseException{
        String fullUrl = getFullURL(url, numOfPastWeeks);

        JSONArray jsonArr = APITalker.getArrayResponse(fullUrl, false);
        this.crimes = createCrimeList(jsonArr);
    }

    private static List<Crime> createCrimeList(JSONArray jsonArr) {
        List<Crime> listOfCrimes = new ArrayList<>();

        for(int i = 0; i < jsonArr.size(); i++){
            JSONObject json1 = (JSONObject) jsonArr.get(i);
            Crime newCrime = createCrime(json1);
            System.out.println(newCrime);
            if (newCrime != null){
            listOfCrimes.add(newCrime);}
        }
        return listOfCrimes;
    }

    private static Crime createCrime(JSONObject j) {
        try {
            String sDate = (String) j.get("date");
            String type = (String) j.get("primary_type");
            String typeDescription = (String) j.get("description");
            String latitude = (String) j.get("latitude");
            String longitude = (String) j.get("longitude");
            String block = (String) j.get("block");
            return new Crime(type, typeDescription, latitude, longitude, sDate, block);
        }
        catch(Exception e){
            System.out.println("key not found!!");
        }

        return null;
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
                    if(dateCompare == 0 && typeCompare == 0 && streetCompare == 0){
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
        return this.crimesRelativeTo.stream()
                .sorted(dateComparator)
                .collect(toList());
    }

    // Sort by location (alpha by street)
    public List<CrimeRelativeToAddress> filterByB(){
        Comparator<CrimeRelativeToAddress> locationComparator =
                Comparator.comparing(c -> c.getAddress().getStreet());

        return this.crimesRelativeTo.stream()
                .sorted(locationComparator)
                .collect(toList());
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
        return this.crimesRelativeTo.stream()
                .sorted(typeComparator)
                .collect(toList());
    }



    public List<CrimeRelativeToAddress> filterByD(){

        return new LinkedList<CrimeRelativeToAddress>();
    }

}
