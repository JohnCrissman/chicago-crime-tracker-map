import java.util.Date;


public class Crime {
    private String type; // primary_type
    private String typeDescription; // secondary_type
    private Address address;  // address contains block street, lat and lon
    private Date date;

    // default constructor
    public Crime ()
    {
        this.address = new Address();
    }

    // constructor given almost all primitive types -- for testing
    public Crime(String type, Double latitude, Double longitude, Date date, String block){
        this.type = type;
        this.address = new Address(latitude, longitude, block);
        this.date = date;
    }

    // constructor given an address object
    public Crime(String type, Date date, Address address){
        this.type = type;
        this.date = date;
        this.address = address;
    }


    // getters
    public String getType(){
        return this.type;
    }
    public Address getAddress() {
        return this.address;
    }
    public Double getLongitude(){
        return this.address.getLongitude();
    }
    public Double getLatitude() { return this.address.getLatitude(); }
    public Date getDate(){
        return this.date;
    }

    @Override
    public String toString(){
        return this.type + ": " + this.address + ", " + this.date;
    }
}
