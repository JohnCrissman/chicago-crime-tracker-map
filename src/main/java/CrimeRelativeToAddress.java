import java.util.Date;

public class CrimeRelativeToAddress extends Crime {
    private double proximity;

    public CrimeRelativeToAddress(Crime crime) {
        super(crime.getType(), crime.getTypeDescription(), crime.getDate(), crime.getAddress());
        this.proximity = Double.MAX_VALUE;
    }

    public void setProximity(Address address) {
        this.proximity = AddressHelper.distanceBetweenTwoLocations(this.getAddress(), address);
    }

    public double getProximity(){
        return this.proximity;
    }

    @Override
    public String toString() {
        String crimeStr = super.toString();
        return crimeStr.substring(0,crimeStr.length()-1) + ", \"proximity\": \""+this.proximity+"\"}";
    }

}
