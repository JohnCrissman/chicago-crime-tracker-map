import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Crimes {
    private List<Crime> crimes; //list of loaded crimes for the past 2 weeks
    private List<CrimeRelativeToAddress> crimesRelativeTo;
    private double radius;
    private Address relativeAddress;

    public Crimes(){
        String url = "https://data.cityofchicago.org/resource/ijzp-q8t2.json"; // <-- TODO: add the right url here
        int numOfweeks = 2;
//        TODO: John: query(url, numOfWeek) should be called here.

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
        this.radius = radius*(1);// TODO: <-- John: make conversion of the radius from miles to the corresponding unit that lat/lon use.
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



    public void query(String url, int numOfPastWeeks) {
        /*
          TODO: John: GENERATE a method that created a url_dateRange so that it contains dates that look like
            '2019-09-01T12:00:00'
           ie: "where=date between '<<<TODAY minus 2 weeks>>>' and '<<<TODAY>>>'"
*/
        Date today = new Date();
        String url_dateRange = "$where=date between '2019-09-01T12:00:00' and '2019-11-01T12:00:00'"; // <-- TODO: should not be hardcoded
        String fullUrl = url+"?"+url_dateRange;

/*
         TODO: John, big chunks of your code will go here
          this method calls the API using APITalker and the url loads the crimes with the crimes for the past
          numOfPastWeeks weeks.  Puts them into a the crime list.
*/




        this.crimes = new LinkedList<>();
    }




    //TODO: John you can add the method that return a sorted list of crimes within the radius
    public List<CrimeRelativeToAddress> filterByA(){

        return new LinkedList<CrimeRelativeToAddress>();
    }

    public List<CrimeRelativeToAddress> filterByB(){

        return new LinkedList<CrimeRelativeToAddress>();
    }

    public List<CrimeRelativeToAddress> filterByC(){

        return new LinkedList<CrimeRelativeToAddress>();
    }

    public List<CrimeRelativeToAddress> filterByD(){

        return new LinkedList<CrimeRelativeToAddress>();
    }

}
