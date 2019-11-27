import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;


public class APITalker {


    public static JSONObject getObjectResponse(String query_url, boolean debug) throws MalformedURLException, UnknownHostException, IOException, ParseException{

        if(debug) System.out.println("\t********************************************** - debug starts");
        JSONObject json = getJson(query_url, new JSONObject(), debug);
        if(debug) System.out.println("\t********************************************** - debug ends\n");
        return json;

    }

    public static JSONArray getArrayResponse(String query_url, boolean debug) throws MalformedURLException, UnknownHostException, IOException, ParseException{

        if(debug) System.out.println("\t********************************************** - debug starts");
        JSONArray json = getJson(query_url, new JSONArray(), debug);
        if(debug) System.out.println("\t********************************************** - debug ends\n");
        return json;

    }

    private static <E> E getJson(String query_url, E json, boolean debug) throws  ParseException, IOException {
//        converts the string json response into a json object or array, depending on the E type
        String result = APIRequestAsString(query_url);
        if(debug) System.out.println("\tresult as String:\n\t" + result+"\n");

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(result);

        @SuppressWarnings("There will only be JsonObject or JsonArray Types")
        E castedJson = (E) obj;

        if(debug) System.out.println("\ttype of response:\t"+castedJson.getClass());
        return castedJson;
    }

    private static String APIRequestAsString(String raw_url) throws UnknownHostException, MalformedURLException, IOException{

        StringBuilder result = new StringBuilder();
        URL url = new URL(raw_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }


    public static <E> E getJsonFromString(String JsonString, E json, boolean debug) throws  ParseException, IOException{
//        converts the jsonString response into a json object or array, depending on the E type
        if(debug) System.out.println("\tresult as String:\n\t" + JsonString+"\n");

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(JsonString);

        @SuppressWarnings("There will only be JsonObject or JsonArray Types")
        E castedJson = (E) obj;

        if(debug) System.out.println("\ttype of response:\t"+castedJson.getClass());
        return castedJson;
    }
}
