public class Address {
    private static final double LAT_IN_MILES = 69.0;
    private static final double LONG_IN_MILES = 53.0;
    private double latitude;
    private double longitude;
    private String street; // from block ie: N Paulina etc
    private String block; // from block, ie: 0078XX

    Address() {
        this.latitude = 41.9803467;
        this.longitude = -87.7191019;
        this.street = "N St Louis Ave, Chicago, IL 60625";
        this.block = "5500";
    }

    Address(double latitude, double longitude, String block){
        this.latitude = latitude;
        this.longitude = longitude;
        this.block = parseBlock(block);
        this.street = parseStreet(block);
    }


    private String parseBlock(String fullAddress){
        // Splits fullAddress on actual block
        String[] newBlock = fullAddress.split(" ");
        return newBlock[0];
    }

    private String parseStreet(String fullAddress){
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


    String getStreet() {
        return this.street;
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

    String getFullAddress() {
        String cleanBlock = this.block;
        //remove leading 0's
        try {
            cleanBlock = cleanBlock.split("[0]+", 2)[1];
        } catch(ArrayIndexOutOfBoundsException ignored) {
        }
        //change 41XX to 4100
        cleanBlock = cleanBlock.replaceFirst("[X]+", "00");
        return (cleanBlock + " " + this.street).toUpperCase();
    }

    @Override
    public String toString(){
        return "{ \"block\" : \"" + this.block + "\", \"street\" : \"" + this.street + "\", \"lat\" : \"" + this.latitude+"\", \"long\" : \""+this.longitude+ "\"}";
    }

}