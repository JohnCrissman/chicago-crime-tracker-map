import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import static java.util.stream.Collectors.*;

public class Crimes {
    private List<Crime> crimes; //list of loaded crimes for the past X weeks
    private List<CrimeRelativeToAddress> crimesRelativeTo;
    private double radius;  // in miles
    private Address relativeAddress;

    public Crimes() throws ParseException, IOException {
        int numOfWeeks = 4; //shows 4 weeks of data, i.e. 5 weeks ago to 1 week ago
        this.query(numOfWeeks);
        this.radius = 0.0;
    }

    private void query(int numOfPastWeeks) throws IOException, ParseException{
        String fullUrl = createFullQueryURL(numOfPastWeeks);
        System.out.println("Query: " + fullUrl);
        JSONArray jsonArr = APITalker.getArrayResponse(fullUrl, false);
        this.crimes = Crimes.createCrimeList(jsonArr);
    }

    private String createFullQueryURL(int numOfPastWeeks){
        String url = "https://data.cityofchicago.org/resource/ijzp-q8t2.json";
        LocalDateTime endOfTimeFrame = LocalDateTime.now().minus(1, ChronoUnit.WEEKS);
        LocalDateTime beginningOfTimeFrame = endOfTimeFrame.minus(numOfPastWeeks, ChronoUnit.WEEKS);

        String endDate = endOfTimeFrame.toString().split("\\.")[0];
        String startDate = beginningOfTimeFrame.toString().split("\\.")[0];

        String dateRangeQuery = "date between '" + startDate + "' and '" + endDate + "'";

        return url + "?$limit=100000&$where=" + URLEncoder.encode(dateRangeQuery, StandardCharsets.UTF_8);
    }

    private static List<Crime> createCrimeList(JSONArray jsonArr) {
        List<Crime> listOfCrimes = new ArrayList<>();
        String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String filename =  "logFile_"+ formatDateTime +".log";
        String pathOfLog = Crimes.class.getResource("map.html").getPath();
        pathOfLog = pathOfLog.replace("map.html", filename);

        try(FileOutputStream fOut = new FileOutputStream(pathOfLog);
            DataOutputStream output = new DataOutputStream(fOut)) {
            output.writeBytes("Date\t Type\t Description\t Latitude\t Longitude\t Block\n");
            for(Object o : jsonArr) {
                JSONObject jsonItem = (JSONObject) o;
                Crime newCrime = Crimes.createCrime(jsonItem, output);
                if (newCrime != null) {
                    listOfCrimes.add(newCrime);
                }
            }
        } catch(IOException ignored) { }

        System.out.println("Log of incomplete crime entries (discarded) was saved to: " + pathOfLog);

        return listOfCrimes;
    }

    private static Crime createCrime(JSONObject json, DataOutputStream log) throws IOException
    {
        String sDate = (String) json.get("date");
        String type = (String) json.get("primary_type");
        String typeDescription = (String) json.get("description");
        String latitude = (String) json.get("latitude");
        String longitude = (String) json.get("longitude");
        String block = (String) json.get("block");

        Crime newCrime = null;
        try {
            newCrime = new Crime(type, typeDescription, latitude, longitude, sDate, block);
        } catch(java.text.ParseException e){
            System.out.println("Not a number, dropped crime.");
        } catch(NullPointerException e) {
            String logMessage = sDate + "\t " + type + "\t " + typeDescription + "\t "
                    + latitude + "\t " + longitude + "\t " + block + "\n";
            log.writeBytes(logMessage);
        }

        return newCrime;
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

        // filters the full list of crimes for those within the radius of selected address
        this.crimesRelativeTo = this.crimes.stream()
                .map(CrimeRelativeToAddress::new)
                .peek(cRel -> cRel.setProximity(this.relativeAddress))
                .filter(cRel -> AddressHelper.isWithinRadius(cRel.getAddress(), this.relativeAddress, this.radius))
                .collect(toList());
    }

    public int count(){
        return this.crimesRelativeTo.size();
    }

    public Map<String, Integer> countByType(){
        return this.crimesRelativeTo.stream()
                .collect(groupingBy(Crime::getType)).entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> e.getValue().size()));
    }

    public Map<DayOfWeekCrime, Integer> countByDayOfWeek(){
        return this.crimesRelativeTo.stream()
            .collect(groupingBy(this::getDayOfWeek)).entrySet().stream()
            .collect(toMap(Map.Entry::getKey, e -> e.getValue().size()));
    }

    public Map<Integer, Integer> countByDayOfMonth(){
        return this.crimesRelativeTo.stream()
            .collect(groupingBy(this::getDayOfMonth)).entrySet().stream()
            .collect(toMap(Map.Entry::getKey, e -> e.getValue().size()));
    }


    private DayOfWeekCrime getDayOfWeek(CrimeRelativeToAddress c){
        Calendar cal = new Calendar.Builder().setInstant(c.getDate()).build();
        return DayOfWeekCrime.of(cal.get(Calendar.DAY_OF_WEEK));
    }
    private int getDayOfMonth(CrimeRelativeToAddress c){
        Calendar cal = new Calendar.Builder().setInstant(c.getDate()).build();
        return cal.get(Calendar.DAY_OF_MONTH);
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
