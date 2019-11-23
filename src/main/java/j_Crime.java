import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class j_Crime {
    private String type;
    private Double latitude;
    private Double longitude;
    private Date date;

    public j_Crime(String type, String latitude, String longitude, String sDate) throws ParseException {
        this.type = type;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.date = convertDate(sDate);
    }

    public Date convertDate(String sDate) throws ParseException {
        sDate = sDate.substring(0,10);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(sDate);
        return date;
    }
    public String getType(){
        return this.type;
    }

    public double convertLatitudeToDouble(){
        return 4.0;
    }
    public Double getLatitude(){
        return this.latitude;
    }

    public Double getLongitude() { return this.longitude; }

    public Date getDate(){
        return this.date;
    }

    public String printCrimeInfo(){
        return this.type + ": " + this.latitude + ", " + this.longitude + ", " + this.date;
    }
}
