import java.io.IOException;
import java.net.*;

import org.json.simple.*;
import org.json.simple.parser.ParseException;


public class m_DemoChicagoCrimes {

// TODO: use the google api to convert address to long and lat

    public static void main(String[] args) {
        System.out.println("Hello from Mari Demo");

        //        url that is going to be queried
        String address = "1600+Amphitheatre+Parkway,+Mountain+View,+CA";
        String apiKey = "AIzaSyCn_pLa9MetQ-5wn4ygQNChNq48j0bMo04";
        String query_url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;
        System.out.println(query_url);

        try {

            JSONObject json = m_APITalker.makeRequestResponse(query_url);
            System.out.println("This is the response\n" + json);
//            Do something with the json

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
