import java.io.IOException;
import java.net.*;

import org.json.simple.*;
import org.json.simple.parser.ParseException;


public class m_DemoChicagoCrimes {

// TODO: use the google api to convert address to long and lat

    public static void main(String[] args) {
        System.out.println("Hello from Mari Demo");

        //        url that is going to be queried
        String base = "https://geocoder.api.here.com/6.2/geocode.json?";
        String apiHereKey = "app_id=kWDa074FyNMh0xqkXKFB";
        String apiHereCode = "app_code=mN0YqfpiVu6jmm0I0uzZlA";
        String searchText = "searchtext=425+W+Randolph+Chicago";

        String address = "1600+Amphitheatre+Parkway,+Mountain+View,+CA";
        String apiKey = "AIzaSyCn_pLa9MetQ-5wn4ygQNChNq48j0bMo04";
        String query_url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;

        String jsonUrl = "https://data.cityofchicago.org/resource/crimes.json";


        System.out.println(jsonUrl);

        try {

            JSONObject jsonObj = m_APITalker.getObjectResponse(query_url, false);
            System.out.println("This is the response\n\t" + jsonObj);

//            example for an API all expecting a Json Array as a response
//            JSONArray jsonArr = m_APITalker.getArrayResponse(jsonUrl);
//            System.out.println("This is the response\n\t" + jsonArr);


        }
        catch(MalformedURLException e){
            System.out.println("Malformed URL");
            e.printStackTrace();
        }catch(UnknownHostException e){
            System.out.println("Make sure you are connected to internet");
        } catch (IOException e) {
            System.out.println("conn something and BufferedReader");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("issue with JSONParser.parse()");
            e.printStackTrace();
        }

    }

}
