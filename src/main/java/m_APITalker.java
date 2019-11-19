import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

public class m_APITalker {
    public static JSONObject makeRequestResponse(String query_url) throws MalformedURLException, UnknownHostException, IOException, ParseException{

            JSONObject json = getJsonObject(query_url);
//            System.out.println("\tjson.get(\"status\"):\t"+json.get("status"));
            System.out.println("\tresult as Map<String,JSONValue>:\n\t" + getJSONFromAPIResponse(json));
            System.out.println("\t********************************************** - debug ends\n");
            return json;

    }


    private static JSONObject getJsonObject(String query_url) throws  ParseException, IOException {
        String result = APIRequestAsString(query_url);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(result);
        System.out.println("\ttype of json:\t" + json.getClass());
        System.out.println();
        return json;
    }


    private static Map<String, JSONValue> getJSONFromAPIResponse(JSONObject js) {

        System.out.println("\tresult as JSONObject:\n\t" + js+"\n");
//      (Map<String,JSONValue>) js;

        return (Map<String,JSONValue>)js;
    }

    private static String APIRequestAsString(String raw_url) throws UnknownHostException, MalformedURLException, IOException{
        System.out.println("\t********************************************** - debug starts");
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

        System.out.println("\tresult as String:\n\t" + result+"\n");
        return result.toString();
    }
}
