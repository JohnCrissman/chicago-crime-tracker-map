import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    // constructor for query() in Crimes class.
    public Crime(String type, String typeDescription, String latitude,
                 String longitude, String date, String block) throws ParseException {
        this.type = type;
        this.typeDescription = typeDescription;
        this.address = new Address(Double.parseDouble(latitude),
                Double.parseDouble(longitude), block);
        this.date = convertDate(date);
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


    private Date convertDate(String sDate) throws ParseException {
        sDate = sDate.substring(0,10);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(sDate);
        return date;
    }

    // getters
    public String getType(){
        return this.type;
    }
    public Address getAddress() {
        return this.address;
    }
    public Date getDate(){
        return this.date;
    }
    public String getTypeDescription(){ return this.typeDescription; }

    @Override
    public String toString(){
        return "{\"type\": \"" + this.getType() + "\"," +
                " \"typeDescription\": \"" +this.typeDescription + "\"," +
                " \"address\": " +this.getAddress()+"," +
                " \"date\": \""+ this.getDate() +"\"}";
    }
}
