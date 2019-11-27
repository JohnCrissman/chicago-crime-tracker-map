import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Distance {

    enum Imperial {
        MILE, YARD, FEET, INCH;

        Imperial(){

        }

        // TODO: create conversion between Imperial units and any other
        public static double convertMilesMKS() {
            return 0.0;
        }

    }
    enum Metric{
        KM, M;

        Metric(){

        }

        // TODO: create conversion between metric units and any other
        public static double convertMilesMKS(){
            return 0.0;
        }

    }

    public class LatLong{
        public double latitude;
        public double longitude;

        LatLong(double lat, double lon){
            this.latitude = lat;
            this.longitude = lon;
        }
    }

    public static class LatLongHelper{

        public static double distanceBetweenTwoLocations(Address add1, Address add2){
            return Math.sqrt(Math.pow((add1.getLongitude())*53.0 - (add2.getLongitude())*53.0, 2) + Math.pow((add1.getLatitude())*69.0 - (add2.getLatitude())*69.0, 2));
        }

        public static boolean isWithinRadius(Address add1, Address add2, Double radius){
            return Math.sqrt(Math.pow((add1.getLongitude())*53.0 - (add2.getLongitude())*53.0, 2) + Math.pow((add1.getLatitude())*69.0 - (add2.getLatitude())*69.0,2)) < radius;
        }

        public static LatLong getLatLonFromGoogleAPI(String address) throws IOException, ParseException {
            String googleApiKey = "AIzaSyCN7hTS17iGOG-yLy7lBknC5TcCUCHq7Qo";
            // TODO: Marianela. Convert 5633 N Kenmore into 5633+N+Kenmore
            //  so I can make a good/clean request

            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + googleApiKey;

            JSONObject jobj = APITalker.getObjectResponse(url, false);
            JSONArray jar = (JSONArray) jobj.get("results");
            JSONObject job = (JSONObject) jar.get(0);
            job = (JSONObject) job.get("geometry");
            job = (JSONObject) job.get("location");
//            System.out.println(job.get("lng") + "\ttype: " + job.get("lng").getClass());
            double longitud = (double) job.get("lng");
            double latitud = (double) job.get("lat");
//            System.out.println(job);
//            System.out.println("long " + longitud + "and lat " + latitud);

            return new Distance().new LatLong(latitud, longitud);

        }

    }

}
