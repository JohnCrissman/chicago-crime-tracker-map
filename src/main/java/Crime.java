import java.util.Date;
import java.util.List;

public class Crime {
    private String type;
    private List<Integer> latAndLong;
    private Date date;

    public Crime(String type, List<Integer> latAndLong, Date date){
        this.type = type;
        this.latAndLong = latAndLong;
        this.date = date;
    }

    public String getType(){
        return this.type;
    }

    public List<Integer> getLatAndLong(){
        return this.latAndLong;
    }

    public Date getDate(){
        return this.date;
    }
}
