import java.util.Date;

public class CrimeRelativeToAddress extends Crime {

    private double proximity;

    public CrimeRelativeToAddress() {}

    public CrimeRelativeToAddress(String type, Double latitude, Double longitude, Date date, String block){
        super(type, latitude, longitude, date, block);
    }

    public CrimeRelativeToAddress(Crime crime){
        super(crime.getType(), crime.getDate(), crime.getAddress());
    }

    public void setProximity(Address address) {
        this.proximity = Distance.LatLong.distanceBetweenTwoLocations(this.getAddress(), address);
    }

    @Override
    public String toString() {
        return super.toString() + " [prox: "+this.proximity+"]";
    }

}
