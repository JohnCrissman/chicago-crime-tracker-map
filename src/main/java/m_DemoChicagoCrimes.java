import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

import org.json.simple.*;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
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
        String googleApiKey = "AIzaSyCN7hTS17iGOG-yLy7lBknC5TcCUCHq7Qo";
        String query_url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + googleApiKey;

        String jsonUrl = "https://data.cityofchicago.org/resource/crimes.json";




        try {
// *************************************************************************************************************
//            **************
//            example for an API all expecting a Json Array as a response

//            JSONArray jsonArr = m_APITalker.getArrayResponse(jsonUrl);
//            System.out.println("This is the response\n\t" + jsonArr);

//            String fakeJsonString = "[\"results\": {\"boo\": \"john\",\"oboo\": \"mari\"}]";
//            JSONArray jarr = m_APITalker.getJsonFromString(fakeJsonString, new JSONArray(), true);
//            System.out.println(jarr);


//            **************
//            example for an API all expecting a Json Object as a response

//            JSONObject jsonObj = m_APITalker.getObjectResponse(query_url, false);
//            System.out.println("This is the response\n\t" + jsonObj);

// *************************************************************************************************************

//          TODO: TEST FOR GETTING THE URL GIVEN THE ADDRESS FROM USER INPUT






//          TEST FOR GETTING THE LAT AND LONG GIVEN THE JSON RESPONSE
            JSONObject jobj = m_APITalker.getJsonFromString(getFakeJsonString(), new JSONObject(), false);
//            System.out.println(jobj);
            System.out.println();
//            JSONParser parser = new JSONParser();
//            Object obj = parser.parse(getFakeJsonString());
            JSONArray jar = (JSONArray) jobj.get("results");
            JSONObject job = (JSONObject) jar.get(0);
            job = (JSONObject) job.get("geometry");
            job = (JSONObject) job.get("location");
            System.out.println(job.get("lng") + "\ttype: " + job.get("lng").getClass());
            Double longitud = (Double) job.get("lng");
            Double latitud = (Double) job.get("lat");


            System.out.println(job);
            System.out.println("long " + longitud + "and lat " + latitud);

            jar.stream()
                    .peek(a -> System.out.print("\t"+a+"testing here\t"))
                    .forEach(System.out::println);

            System.out.println(jobj.get("results").getClass());

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




    public static String getFakeJsonString(){

        return  "{\n" +
                "   \"results\": [\n" +
                "      {\n" +
                "         \"address_components\": [\n" +
                "            {\n" +
                "               \"long_name\": \"1600\",\n" +
                "               \"short_name\": \"1600\",\n" +
                "               \"types\": [\n" +
                "                  \"street_number\"\n" +
                "               ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\": \"Amphitheatre Parkway\",\n" +
                "               \"short_name\": \"Amphitheatre Pkwy\",\n" +
                "               \"types\": [\n" +
                "                  \"route\"\n" +
                "               ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\": \"Mountain View\",\n" +
                "               \"short_name\": \"Mountain View\",\n" +
                "               \"types\": [\n" +
                "                  \"locality\",\n" +
                "                  \"political\"\n" +
                "               ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\": \"Santa Clara County\",\n" +
                "               \"short_name\": \"Santa Clara County\",\n" +
                "               \"types\": [\n" +
                "                  \"administrative_area_level_2\",\n" +
                "                  \"political\"\n" +
                "               ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\": \"California\",\n" +
                "               \"short_name\": \"CA\",\n" +
                "               \"types\": [\n" +
                "                  \"administrative_area_level_1\",\n" +
                "                  \"political\"\n" +
                "               ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\": \"United States\",\n" +
                "               \"short_name\": \"US\",\n" +
                "               \"types\": [\n" +
                "                  \"country\",\n" +
                "                  \"political\"\n" +
                "               ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\": \"94043\",\n" +
                "               \"short_name\": \"94043\",\n" +
                "               \"types\": [\n" +
                "                  \"postal_code\"\n" +
                "               ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\": \"1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA\",\n" +
                "         \"geometry\": {\n" +
                "            \"location\": {\n" +
                "               \"lat\": 37.4210596,\n" +
                "               \"lng\": -122.0847774\n" +
                "            },\n" +
                "            \"location_type\": \"ROOFTOP\",\n" +
                "            \"viewport\": {\n" +
                "               \"northeast\": {\n" +
                "                  \"lat\": 37.4224085802915,\n" +
                "                  \"lng\": -122.0834284197085\n" +
                "               },\n" +
                "               \"southwest\": {\n" +
                "                  \"lat\": 37.4197106197085,\n" +
                "                  \"lng\": -122.0861263802915\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\": \"ChIJtYuu0V25j4ARwu5e4wwRYgE\",\n" +
                "         \"plus_code\": {\n" +
                "            \"compound_code\": \"CWC8+C3 Mountain View, California, United States\",\n" +
                "            \"global_code\": \"849VCWC8+C3\"\n" +
                "         },\n" +
                "         \"types\": [\n" +
                "            \"street_address\"\n" +
                "         ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\": \"OK\"\n" +
                "}";
    }
}
