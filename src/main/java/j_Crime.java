import java.util.Date;
import java.util.List;

public class j_Crime {
    private String type;
    private String latitude;
    private String longitude;
    private Date date;

    public j_Crime(String type, String latitude, String longitude, Date date){
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }

    public String getType(){
        return this.type;
    }

    public String getLatitude(){
        return this.latitude;
    }

    public String getLongitude() { return this.longitude; }

    public Date getDate(){
        return this.date;
    }

    public String printCrimeInfo(){
        return this.type + ": " + this.latitude + ", " + this.longitude + ", " + this.date;
    }
}
