public class Address {
    private double latitude;
    private double longitude;
    private String restOfAddress; // from block ie: N Paulina etc
    private String block; // from block, ie: 0078XX

    Address(double latitude, double longitude, String blockAddress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.block = AddressHelper.parseBlock(blockAddress);
        this.restOfAddress = AddressHelper.parseStreet(blockAddress);
    }

    @Override
    public String toString(){
        return "{ \"block\" : \"" + this.block + "\", \"street\" : \"" + this.restOfAddress + "\", \"lat\" : \"" + this.latitude+"\", \"long\" : \""+this.longitude+ "\"}";
    }

    public String getFullAddress() {
        return (this.block + " " + this.restOfAddress).toUpperCase();
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

}