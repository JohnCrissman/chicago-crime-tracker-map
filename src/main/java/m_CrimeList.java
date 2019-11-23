import java.util.LinkedList;
import java.util.List;

public class m_CrimeList {
    private double radius;
    private List<j_Crime> crimes;

    public m_CrimeList(double radius, List<j_Crime> crimes){
        this.radius = radius;
        this.crimes = crimes;
    }

    public double getRadius(){
        return this.radius;
    }

    public List<j_Crime> getCrimes(){
        return this.crimes;
    }


    public List<Crime> query() { // not sure what the parameter should be.
        // make a list of crimes
        return new LinkedList<Crime>();
    }
}
