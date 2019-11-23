import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class m_CrimeList {
    private List<Crime> crimes; //list of loaded crimes for the past 2 weeks
    private List<CrimeRelativeToAddress> crimesRelativeTo;

    public m_CrimeList(){

    }

    public List<Crime> getCrimes(){
        return this.crimes;
    }

    public List<CrimeRelativeToAddress> crimesWithinRadius(double radius, Address address){
        // TODO: make conversion of the radius from miles to the corresponding unit that lat/lon use.
        return this.crimes.stream()
                .map(crime -> new CrimeRelativeToAddress(crime))
                .peek(cRel -> cRel.setProximity(address))
                .filter(cRel -> Distance.LatLong.isWithinRadius(cRel.getAddress(), address, radius))
                .collect(toList());
    }

    public List<Crime> query() { // not sure what the parameter should be.
        // add crimes to th list
        List<Crime> theList = new LinkedList<>();


        return theList;
    }
}
