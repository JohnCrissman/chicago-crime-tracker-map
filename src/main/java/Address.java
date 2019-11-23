public class Address{

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
        // TODO: split fullAddress on actual block
        String[] newBlock = block.split(" ");
        return newBlock[0];
    }

    public String parseStreet(String fullAddress){
        // TODO: split fullAddress on actual street
        String[] newBlock = block.split(" ");
        return block.substring(newBlock[0].length());
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


    public String toString(){
        return this.block + " " + this.street + " (" + this.latitude+"\" "+ + this.longitude+ "\")";
    }

}