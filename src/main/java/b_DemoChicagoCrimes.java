//from: http://fxexperience.com/2011/05/maps-in-javafx-2-0/
//modifications from: https://o7planning.org/en/11151/javafx-webview-and-webengine-tutorial


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class b_DemoChicagoCrimes extends Application {

    @Override public void start(Stage stage) {
        // create web engine and view
        WebView webView = new WebView();
//        WebEngine newWebEngine = webView.getEngine();
//        String url = b_DemoChicagoCrimes.class.getClass().getResource("b_mapdemo.html").toString();
//        newWebEngine.load(url);
//        Scene scene = new Scene(webView, 1000, 700, Color.web("#666970"));
//        stage.setScene(scene);
        stage.setTitle("Map Test");
        // show stage
        stage.show();
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}