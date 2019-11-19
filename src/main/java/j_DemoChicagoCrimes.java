import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.net.*;
import java.util.*;

public class j_DemoChicagoCrimes {
    public static void main(String[] args) throws Exception {
        String jsonUrl = "https://data.cityofchicago.org/resource/crimes.json";

        System.out.println(jsonUrl);

        try {

//            JSONObject jsonObj = m_APITalker.getObjectResponse(query_url, false);
//            System.out.println("This is the response\n\t" + jsonObj);

            //example for an API all expecting a Json Array as a response
            JSONArray jsonArr = m_APITalker.getArrayResponse(jsonUrl, false);
            System.out.println("This is the response\n\t" + jsonArr);

            JSONObject json1 = (JSONObject) jsonArr.get(0);
//            System.out.println(json1.getClass()); // org.json.simple.JSONObject
//            List<Map<String, String>> listOfCrimes = new ArrayList<>();
//            for(int i = 0; i < jsonArr.size(); i++){
//                listOfCrimes.add(toMap((JSONObject) jsonArr.get(i)));
//            }
            // Assume you have a Map<String, String> in JSONObject jdata
            System.out.println(json1);
            System.out.println(json1.getClass());



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
