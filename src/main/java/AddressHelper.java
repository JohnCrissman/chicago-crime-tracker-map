import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AddressHelper {


        public static double distanceBetweenTwoLocations(Address add1, Address add2){
            double xDelta = add1.getLongInMiles() - add2.getLongInMiles();
            double yDelta = add1.getLatInMiles() - add2.getLatInMiles();
            return Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
        }

        public static boolean isWithinRadius(Address add1, Address add2, Double radius){
            // TODO: Troubleshoot this!
            //  "2400 N St Louis Ave"/"5 mi" returns 3896 crimes,
            //  "2900 N St Louis Ave"/"5 mi" returns 0 crimes.
            double distance = distanceBetweenTwoLocations(add1, add2);
            boolean result = distance < radius;
            return result;
        }

        public static Address getAddressFromGoogleAPI(String address) throws  NotAnAddressException, IOException{
            String googleApiKey = "AIzaSyCN7hTS17iGOG-yLy7lBknC5TcCUCHq7Qo";
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address, StandardCharsets.UTF_8) + "&key=" + googleApiKey;

            JSONObject jobj = APITalker.getObjectResponse(url, false);

            try{
                JSONArray jar = (JSONArray) jobj.get("results");
                JSONObject job = (JSONObject) jar.get(0);
                String block = (String) job.get("formatted_address");
                job = (JSONObject) job.get("geometry");
                job = (JSONObject) job.get("location");
                double longitud = (double) job.get("lng");
                double latitud = (double) job.get("lat");
                return new Address(latitud,longitud,block);
            }
            catch (Exception e){
                throw new NotAnAddressException("Not valid address:" + address);
            }
        }

}
