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

    public static class LatLong{

    public static double distanceBetweenTwoLocations(Address add1, Address add2){
        // TODO: this is obviously how NOT to calculate the proximity.
//           the reason is because we are talking about 3D vs a 2D
//           I am using lat and lon as if they were x and y in a 2D plane.
//          there is more math into calculating the proximity between these two locations
        return Math.sqrt(Math.pow(add1.getLongitude() - add2.getLongitude(), 2) + Math.pow(add1.getLatitude() - add2.getLatitude(), 2));
    }

    public static boolean isWithinRadius(Address add1, Address add2, Double radius){
//        TODO: this will be a function that returns true if the address add1 is within the
//           radius (in Miles) of address add2.

        return Math.sqrt(Math.pow(add1.getLongitude() - add2.getLongitude(), 2) + Math.pow(add1.getLatitude() - add2.getLatitude(),2)) < radius;
    }

    }
}
