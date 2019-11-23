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
        // converts the address (parameter) to a list of two elements (latitude and longitude)
//        System.out.println("Address: " + address.longitude + " " + address.latitude);
//        System.out.println("long: " + this.getLongitude() +" "+ this.getLongitude().getClass());
//        System.out.println("lat: " + this.getLatitude() + " " +this.getLatitude().getClass());
        this.proximity = Distance.LatLong.distanceBetweenTwoLocations(this.getAddress(), address);
//        System.out.println("proximity would be: " + this.proximity);

    }

    @Override
    public String toString() {
        return super.toString() + " [prox: "+this.proximity+"]";
    }

}
