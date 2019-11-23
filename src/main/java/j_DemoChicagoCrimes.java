import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class j_DemoChicagoCrimes {
    public static void main(String[] args) throws Exception {
        String jsonUrl = "https://data.cityofchicago.org/resource/crimes.json?$limit=2000";

        System.out.println(jsonUrl);

        try {

//            JSONObject jsonObj = m_APITalker.getObjectResponse(query_url, false);
//            System.out.println("This is the response\n\t" + jsonObj);

            //example for an API all expecting a Json Array as a response
            JSONArray jsonArr = m_APITalker.getArrayResponse(jsonUrl, false);
            System.out.println("This is the response\n\t" + jsonArr);

            JSONObject json1 = (JSONObject) jsonArr.get(0);
            j_CrimeList crimeList = createCrimeList(jsonArr);
            System.out.println(crimeList.getCrimes().get(998).printCrimeInfo());

//            System.out.println(json1);
//            System.out.println(json1.getClass());
//            System.out.println("Date: ");
//            System.out.println(json1.get("date"));
//            System.out.println(json1.get("date").getClass());
//            System.out.println();
//            System.out.println("Type: ");
//            System.out.println(json1.get("primary_type"));
//            System.out.println(json1.get("primary_type").getClass());
//            System.out.println();
//            System.out.println("Location: ");
//            JSONObject jsonLocation = (JSONObject) json1.get("location");
//            System.out.println(jsonLocation.get("latitude"));
//            System.out.println(jsonLocation.get("latitude").getClass());
//            System.out.println(jsonLocation.get("longitude"));
//            System.out.println(jsonLocation.get("longitude").getClass());
//            System.out.println(jsonArr.size());




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
    public static j_Crime createCrime(JSONObject j) throws java.text.ParseException {
        String sDate = (String) j.get("date");
        String type = (String)j.get("primary_type");
        String latitude = (String)j.get("latitude");
        String longitude = (String)j.get("longitude");
        return new j_Crime(type, latitude, longitude, sDate);
    }

    public static j_CrimeList createCrimeList(JSONArray jsonArr) throws java.text.ParseException {
        List<j_Crime> listOfCrimes = new ArrayList<>();

        for(int i = 0; i < jsonArr.size(); i++){
            JSONObject json1 = (JSONObject) jsonArr.get(i);
            j_Crime newCrime = createCrime(json1);
            listOfCrimes.add(newCrime);
        }

        System.out.println(listOfCrimes.size());

        j_CrimeList crimeList = new j_CrimeList(10.0, listOfCrimes);
        return crimeList;
    }

//    public static Map<String, Object> toMap(JSONObject object) throws {
//        Map<String, String> map = new HashMap<String, String>();
//
//        Iterator<String> keysItr = object.keys();
//        while(keysItr.hasNext()) {
//            String key = keysItr.next();
//            Object value = object.get(key);
//
//            if(value instanceof JSONArray) {
//                value = toList((JSONArray) value);
//            }
//
//            else if(value instanceof JSONObject) {
//                value = toMap((JSONObject) value);
//            }
//            map.put(key, value);
//        }
//        return map;
//    }
//
//    public static List<Object> toList(JSONArray array) throws JSONException {
//        List<Object> list = new ArrayList<Object>();
//        for(int i = 0; i < array.length(); i++) {
//            Object value = array.get(i);
//            if(value instanceof JSONArray) {
//                value = toList((JSONArray) value);
//            }
//
//            else if(value instanceof JSONObject) {
//                value = toMap((JSONObject) value);
//            }
//            list.add(value);
//        }
//        return list;
//    }

}
