import java.util.LinkedList;
import java.util.List;

public class j_CrimeList {
    private double radius;
    private List<j_Crime> crimes;

    public j_CrimeList(double radius, List<j_Crime> crimes){
        this.radius = radius;
        this.crimes = crimes;
    }

    public double getRadius(){
        return this.radius;
    }

    public List<j_Crime> getCrimes(){
        return this.crimes;
    }
}
