import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.net.URLEncoder;

public class AddressHelper {


        public static double distanceBetweenTwoLocations(Address add1, Address add2){
            return Math.sqrt(Math.pow(add1.getLongInMiles() - add2.getLongInMiles(), 2) + Math.pow(add1.getLatInMiles() - add2.getLatInMiles(), 2));
        }

        public static boolean isWithinRadius(Address add1, Address add2, Double radius){
            return distanceBetweenTwoLocations(add1, add2) < radius;
        }

        public static Address getAddressFromGoogleAPI(String address) throws  NotAnAddressException, IOException{
            String googleApiKey = "AIzaSyCN7hTS17iGOG-yLy7lBknC5TcCUCHq7Qo";
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address,"UTF-8") + "&key=" + googleApiKey;


//            System.out.println("URL:" + url);
            JSONObject jobj = APITalker.getObjectResponse(url, false);

            try{
                JSONArray jar = (JSONArray) jobj.get("results");
                JSONObject job = (JSONObject) jar.get(0);
                String block = (String) job.get("formatted_address");
                job = (JSONObject) job.get("geometry");
                job = (JSONObject) job.get("location");
    //            System.out.println(job.get("lng") + "\ttype: " + job.get("lng").getClass());
                double longitud = (double) job.get("lng");
                double latitud = (double) job.get("lat");
                return new Address(latitud,longitud,block);
            }
            catch (Exception e){
                throw new NotAnAddressException("Not valid address:" + address);
            }
        }

}
