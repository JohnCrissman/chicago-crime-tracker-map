public class Address{
    private static final double LATINMILES = 69.0;
    private static final double LONGINMILES = 53.0;

    private double latitude;
    private double longitude;
    private String street; // from block ie: N Paulina etc
    private String block; // from block, ie: 0078XX

    public Address(){
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.street = "";
        this.block = "";
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
        return this.latitude*LATINMILES;
    }

    public double getLongInMiles() {
        return this.longitude*LONGINMILES;
    }

    public String getFulAddress(){
        return this.block + " " + this.street;
    }

    @Override
    public String toString(){
        return this.block + " " + this.street + " (" + this.latitude+"\" "+ + this.longitude+ "\")";
    }

}