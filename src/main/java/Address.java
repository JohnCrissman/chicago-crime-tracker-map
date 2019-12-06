public class Address {
    private static final double LAT_IN_MILES = 69.0;
    private static final double LONG_IN_MILES = 53.0;
    private double latitude;
    private double longitude;
    private String restOfAddress; // from block ie: N Paulina etc
    private String block; // from block, ie: 0078XX

    Address() {
        this.latitude = 41.9803467;
        this.longitude = -87.7191019;
        this.restOfAddress = "N St Louis Ave, Chicago, IL 60625";
        this.block = "5500";
    }

    Address(double latitude, double longitude, String blockAddress){
        this.latitude = latitude;
        this.longitude = longitude;
        this.block = AddressHelper.parseBlock(blockAddress);
        this.restOfAddress = AddressHelper.parseStreet(blockAddress);
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }


    String getRestOfAddress() {
        return this.restOfAddress;
    }

    String getBlock() {
        return this.block;
    }

    double getLatInMiles() {
        return this.latitude* LAT_IN_MILES;
    }

    double getLongInMiles() {
        return this.longitude* LONG_IN_MILES;
    }

    public String getFullAddress() {
        return (this.block + " " + this.restOfAddress).toUpperCase();
    }

    @Override
    public String toString(){
        return "{ \"block\" : \"" + this.block + "\", \"street\" : \"" + this.restOfAddress + "\", \"lat\" : \"" + this.latitude+"\", \"long\" : \""+this.longitude+ "\"}";
    }

}