import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.simple.JSONArray;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class m_Dummy {


    public static Button createDummyBtn(WebView mapView, Crimes latestCrimes){
        //TODO: ERASE <--- dummybutton for testing
        Button dummyButton = new Button("Dummy Btn");
        //TODO: END ERASE<---

        //TODO: ERASE <--- dummybutton set on action for testing
        dummyButton.setPrefSize(100, 20);
        dummyButton.setOnAction(e ->{
            execJsMethod(mapView, latestCrimes, "all");
        });
        //TODO: END ERASE <---

        return dummyButton;
    }

    private static void execJsMethod(WebView mapView, Crimes latestCrimes, String jsFuncName) {
        String jsFunctionCall;
        if (jsFuncName.equalsIgnoreCase("all")){
            jsFunctionCall = "showCrimesOnMap('" +
                JSONArray.toJSONString(latestCrimes.getAllCrimes()
                        .stream()
//                                        .map(crime -> crime.getAddress())
//                        .limit(5)
                        .collect(toList())) + "', '" + latestCrimes.getRelativeAddress().toString() + "')";

        }else if(jsFuncName.equalsIgnoreCase("rel")){
            jsFunctionCall = "showCrimesOnMap('" +
                    JSONArray.toJSONString(latestCrimes.getCrimesRelativeTo()
                            .stream()
                            .sorted((cp1,cp2) -> (int)(cp1.getProximity() - cp2.getProximity()))
//                             .map(crime -> crime.getAddress())
//                            .limit(10)
                            .collect(toList())) + "', '" + latestCrimes.getRelativeAddress().toString() + "')";

        }else{
            jsFunctionCall = "showCrimesOnMap('" +
                    JSONArray.toJSONString(latestCrimes.getAllCrimes()
                            .stream()
//                             .map(crime -> crime.getAddress())
//                            .limit(5)
                            .collect(toList())) + "', '" + latestCrimes.getRelativeAddress().toString() + "')";
        }

        System.out.println(jsFunctionCall);
        mapView.getEngine().executeScript(jsFunctionCall);
    }



    public static void execJsFunc(WebView webView, Crimes latestCrimes, String jsFuncName){
        execJsMethod(webView, latestCrimes, jsFuncName);
    }
}
