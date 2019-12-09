import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Crime {
    private String type; // primary_type
    private String typeDescription; // secondary_type
    private Address address;  // address contains block street, lat and lon
    private Date date;

    // constructor for createCrime() in Crimes class.
    public Crime(String type, String typeDescription, String latitude,
                 String longitude, String dateAsString, String blockAddress) throws ParseException {
        this.type = type;
        this.typeDescription = typeDescription;
        this.address = new Address(Double.parseDouble(latitude),
                Double.parseDouble(longitude), blockAddress);
        // TODO: use this try-catch to look for lat/long by block -- however,
        //  it takes a LONG time!! :( and some results return "Chicago, IL USA" which should then be discarded
        /*try {
            this.address = new Address(Double.parseDouble(latitude),
                    Double.parseDouble(longitude), block);
        } catch (Exception ex) {
            String addressSearch = AddressHelper.parseBlock(block) + " " + AddressHelper.parseStreet(block) + ", Chicago, IL";

            try {
                this.address = AddressHelper.getAddressFromGoogleAPI(addressSearch);
                System.out.println(this.address.getFullAddress());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }*/
        this.date = convertDate(dateAsString);
    }

    // constructor given an address object
    public Crime(String type, String typeDescription, Date dateAsString, Address address) {
        this.type = type;
        this.typeDescription = typeDescription;
        this.date = dateAsString;
        this.address = address;
    }

    private Date convertDate(String dateAsString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateAsString.substring(0,10)); // substring retains yyyy-MM-dd info
    }

    @Override
    public String toString(){
        return "{\"type\": \"" + this.getType() + "\"," +
                " \"typeDescription\": \"" +this.typeDescription + "\"," +
                " \"address\": " +this.getAddress()+"," +
                " \"date\": \""+ this.getDate() +"\"}";
    }

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
}
