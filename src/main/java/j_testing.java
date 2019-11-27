
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class j_testing {
    public static void main(String[] args) throws ParseException, java.text.ParseException, IOException {
        // input for method:  String url    int numOfPastWeeks
//        TODO: John: GENERATE a method that created a url_dateRange so that it contains dates that look like
//        '2019-09-01T12:00:00'
//        ie: "$where=date between '<<<TODAY minus 2 weeks>>>' and '<<<TODAY>>>'"
//                */
//        Date today = new Date();
//        String url_dateRange = "$where=date between '2019-09-01T12:00:00' and '2019-11-01T12:00:00'"; // <-- TODO: should not be hardcoded
//        String fullUrl = url+"?"+url_dateRange;
//         TODO: John, big chunks of your code will go here
//          this method calls the API using APITalker and the url loads the crimes with the crimes for the past
//          numOfPastWeeks weeks.  Puts them into a the crime list.

        LocalDateTime today1 = LocalDateTime.now();
        System.out.println("Current date: " + today1);

        //minus 2 week from the current date
        LocalDateTime previous2Week = today1.minus(2, ChronoUnit.WEEKS);
        System.out.println("Previous two weeks: " + previous2Week);

        String today2 = today1.toString();
        String previous2 = previous2Week.toString();
        System.out.println();
        System.out.println(today2);
        System.out.println(previous2);
        System.out.println();
        String today3 = today2.split("\\.")[0];
        String previous3 = previous2.split("\\.")[0];
        System.out.println(today3);
        System.out.println(previous3);

        Date date = new Date();
        System.out.println(date.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        System.out.println();
        System.out.println();
        System.out.println(sdf.format(date));
        System.out.println(sdf.format(date).getClass());

        Crimes myCrimes = new Crimes();
        List<Crime> listOfCrimes = myCrimes.getAllCrimes();
        System.out.println(listOfCrimes.get(0));
        System.out.println(listOfCrimes.size());

    }
}
