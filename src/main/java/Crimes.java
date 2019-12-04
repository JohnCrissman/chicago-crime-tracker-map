import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Crimes {
    private List<Crime> crimes; //list of loaded crimes for the past X weeks
    private List<CrimeRelativeToAddress> crimesRelativeTo;
    private double radius;  // in miles
    private Address relativeAddress;

    public Crimes() throws ParseException, IOException {
        int numOfWeeks = 4;
        this.query(numOfWeeks);
        this.relativeAddress = new Address();
        this.radius = 0.0;
    }

    public void setCrimesWithinRadius(double radius, String address) throws IOException, NotARadiusException, NotAnAddressException {
        /*
        *  sets the instance variable crimesRelativeToAddress to the filtered specified by the user
        *  throws:
        *  IOException: if there is an issue with the server (API)
        *  NotAnAddressException if there are no results found from the google API
        *  NotARadiusException if there is a radius that has value 0 or less
        * */
        if (radius <= 0) {
            throw new NotARadiusException("Not a radius: "+ radius);
        }
        this.relativeAddress = AddressHelper.getAddressFromGoogleAPI(address);
        this.radius = radius;
        this.findCrimesWithinRadius();
    }

    private void findCrimesWithinRadius(){
        // method filters the full list of crimes for those within the radius of selected address
        this.crimesRelativeTo = this.crimes.stream()
                .map(CrimeRelativeToAddress::new)
                .peek(cRel -> cRel.setProximity(this.relativeAddress))
                .filter(cRel -> AddressHelper.isWithinRadius(cRel.getAddress(), this.relativeAddress, this.radius))
                .collect(toList());
    }

    private void query(int numOfPastWeeks) throws IOException, ParseException{
        String fullUrl = getFullURL("https://data.cityofchicago.org/resource/ijzp-q8t2.json", numOfPastWeeks);
        System.out.println("Query: " + fullUrl);
        JSONArray jsonArr = APITalker.getArrayResponse(fullUrl, false);
        this.crimes = Crimes.createCrimeList(jsonArr);
    }

    private String getFullURL(String url, int numOfPastWeeks){
        LocalDateTime endOfTimeFrame = LocalDateTime.now();
        LocalDateTime beginningOfTimeFrame = endOfTimeFrame.minus(numOfPastWeeks, ChronoUnit.WEEKS);
        String endDate = endOfTimeFrame.toString().split("\\.")[0];
        String startDate = beginningOfTimeFrame.toString().split("\\.")[0];

        String url_dateRange = "date between '" + startDate + "' and '" + endDate + "'";

        // TODO: Figure out why intelliJ is made at URLEncoder.encode?
        String fullUrl = url + "?$limit=10000&$where=" + URLEncoder.encode(url_dateRange, StandardCharsets.UTF_8);
        return fullUrl;
    }

    private static List<Crime> createCrimeList(JSONArray jsonArr) {
        List<Crime> listOfCrimes = new ArrayList<>();

        for(Object o : jsonArr) {
            JSONObject jsonItem = (JSONObject) o;
            Crime newCrime = Crimes.createCrime(jsonItem);
            if (newCrime != null) {
                listOfCrimes.add(newCrime);
            }
        }
        return listOfCrimes;
    }

    private static Crime createCrime(JSONObject j) {
        String sDate = (String) j.get("date");
        String type = (String) j.get("primary_type");
        String typeDescription = (String) j.get("description");
        String latitude = (String) j.get("latitude");
        String longitude = (String) j.get("longitude");
        String block = (String) j.get("block");

        Crime newCrime = null;
        try {
            newCrime = new Crime(type, typeDescription, latitude, longitude, sDate, block);
        } catch(java.text.ParseException e){
            System.out.println("Not a number, dropped crime.");
        } catch(NullPointerException e) {
            System.out.print("Dropped: ");
            System.out.println("Date: " + sDate + ", type: " + type + ", descr: " + typeDescription
                    + ", Lat: " + latitude + ", Long: " + longitude + ", Block:" + block);
        }

        return newCrime;
    }

    public Address getRelativeAddress(){
        return this.relativeAddress;
    }
    public List<Crime> getAllCrimes(){
        return this.crimes;
    }
    public List<CrimeRelativeToAddress> getCrimesRelativeTo(){
        return this.crimesRelativeTo;
    }
}
