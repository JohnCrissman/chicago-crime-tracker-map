import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class AddressHelper {
    private AddressHelper() {}

    public static double distanceBetweenTwoLocations(Address add1, Address add2) {
        double lat1 = add1.getLatitude();
        double lat2 = add2.getLatitude();
        double lon1 = add1.getLongitude();
        double lon2 = add2.getLongitude();

        double R = 6371e3 / 1609.34; // miles
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double theta = Math.toRadians((lat2-lat1));
        double lambda = Math.toRadians((lon2-lon1));

        double a = Math.sin(theta/2) * Math.sin(theta/2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(lambda/2) * Math.sin(lambda/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return  R * c ;
    }

    public static boolean isWithinRadius(Address add1, Address add2, Double radius) {
        double distance = distanceBetweenTwoLocations(add1, add2);
        return distance < radius;
    }

    public static Address getAddressFromGoogleAPI(String addressString) throws NotAnAddressException, IOException {
        String googleApiKey = "AIzaSyCN7hTS17iGOG-yLy7lBknC5TcCUCHq7Qo";
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                URLEncoder.encode(addressString, StandardCharsets.UTF_8) + "&key=" + googleApiKey;

        JSONObject jobj = APITalker.getObjectResponse(url, false);

        try {
            JSONArray jar = (JSONArray) jobj.get("results");
            JSONObject job = (JSONObject) jar.get(0);
            String block = (String) job.get("formatted_address");
            job = (JSONObject) job.get("geometry");
            job = (JSONObject) job.get("location");
            double longitude = (double) job.get("lng");
            double latitude = (double) job.get("lat");
            return new Address(latitude, longitude, block);
        }
        catch (Exception e) {
            throw new NotAnAddressException("Not valid address:" + addressString);
        }
    }

    public static String parseBlock(String fullAddress) {
        // Splits fullAddress on actual block
        String newBlock = fullAddress.split(" ")[0];
        if(newBlock.charAt(0) == '0') {
            newBlock = newBlock.replaceFirst("[0]+", "");
        }
        newBlock = newBlock.replaceFirst("[X]+", "00");
        return newBlock;
    }

    public static String parseStreet(String fullAddress){
        // Splits fullAddress on actual street
        String[] newBlock = fullAddress.split(" ");
        return fullAddress.substring(newBlock[0].length());
    }

}
