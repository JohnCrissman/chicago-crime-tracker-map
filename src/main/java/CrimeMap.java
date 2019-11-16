import java.util.LinkedList;
import java.util.List;

public class CrimeMap {
    private String address;
    private List<Integer> latAndLong;
    private int radius;
    private List<Crime> crimes;

    public CrimeMap(String address, List<Integer> latAndLong, int radius){
        this.address = address;
        this.latAndLong = latAndLong;
        this.radius = radius;
    }

    public List<Integer> convertAddressToLatAndLong(String address){
        // converts the address (parameter) to a list of two elements (latitude and longitude)
        return new LinkedList<Integer>();
    }

    public List<Crime> query(){ // not sure what the parameter should be.
        // make a list of crimes
        return new LinkedList<Crime>();
    }

    public String getAddress(){
        return this.address;
    }

    public List<Integer> getLatAndLong(){
        return this.latAndLong;
    }

    public int getRadius(){
        return this.radius;
    }

    public List<Crime> getCrimes(){
        return this.crimes;
    }
}
