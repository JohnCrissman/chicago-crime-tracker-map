public class Address{
    private static final double LAT_IN_MILES = 69.0;
    private static final double LONG_IN_MILES = 53.0;

    private double latitude;
    private double longitude;
    private String street; // from block ie: N Paulina etc
    private String block; // from block, ie: 0078XX

    public Address(){
        this.latitude = 41.9803467;
        this.longitude = -87.7191019;
        this.street = "N St Louis Ave, Chicago, IL 60625";
        this.block = "5500";
    }

    public Address (double latitude, double longitude, String block){
        this.latitude = latitude;
        this.longitude = longitude;
        this.block = parseBlock(block);
        this.street = parseStreet(block);
    }


    public String parseBlock(String fullAddress){
        // Splits fullAddress on actual block
        String[] newBlock = fullAddress.split(" ");
        return newBlock[0];
    }

    public String parseStreet(String fullAddress){
        // Splits fullAddress on actual street
        String[] newBlock = fullAddress.split(" ");
        return fullAddress.substring(newBlock[0].length());
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }


    public String getStreet() {
        return this.street;
    }

    public String getBlock() {
        return this.block;
    }

    public double getLatInMiles() {
        return this.latitude* LAT_IN_MILES;
    }

    public double getLongInMiles() {
        return this.longitude* LONG_IN_MILES;
    }

    public String getFullAddress(){
        return this.block + " " + this.street;
    }

    @Override
    public String toString(){
        return "{ \"block\" : \"" + this.block + "\", \"street\" : \"" + this.street + "\", \"lat\" : \"" + this.latitude+"\", \"long\" : \""+this.longitude+ "\"}";
    }

}