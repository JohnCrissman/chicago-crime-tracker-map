import java.util.Date;


public class Crime {
    private String type; // primary_type
    private String typeDescription; // secondary_type
    private Address address;
    private Date date;


    public Crime ()
    {
        this.address = new Address();
    }


    public Crime(String type, Double latitude, Double longitude, Date date, String block){
        this.type = type;
        this.address = new Address(latitude, longitude, block);
        this.date = date;
    }

    public Crime(String type, Date date, Address address){
        this.type = type;
        this.date = date;
        this.address = address;
    }

    public String getType(){
        return this.type;
    }

    public Address getAddress() {
        return this.address;
    }

    public Double getLongitude(){
        return this.address.getLongitude();
    }
    public Double getLatitude(){
        return this.address.getLatitude();
    }

    public Date getDate(){
        return this.date;
    }

    @Override
    public String toString(){
        return this.type + ": " + this.address + ", " + this.date;
    }
}
