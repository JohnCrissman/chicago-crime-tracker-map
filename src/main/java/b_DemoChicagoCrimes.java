//from: http://fxexperience.com/2011/05/maps-in-javafx-2-0/
//modifications from: https://o7planning.org/en/11151/javafx-webview-and-webengine-tutorial

//charts here: https://www.tutorialspoint.com/javafx/javafx_application.htm
//layouts here: https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
//scrollbox: https://stackoverflow.com/questions/28037818/how-to-add-a-scrollbar-in-javafx

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DateFormat;

public class b_DemoChicagoCrimes extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane basePane = new BorderPane();

        HBox topMenu = createTopMenu();
        basePane.setTop(topMenu);

        ScrollPane crimeList = createCrimeList();
        basePane.setCenter(crimeList);

        HBox bottomMenu = createBottomMenu();
        basePane.setBottom(bottomMenu);

        Scene scene = new Scene(basePane, 1000, 700, Color.web("#666970"));
        stage.setScene(scene);
        stage.setTitle("List Test");
        // show stage
        stage.show();
    }

    private HBox createTopMenu() {
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(15, 12, 15, 12));
        topMenu.setSpacing(10);
        topMenu.setStyle("-fx-background-color: #5555ff;");
        //TextArea addr = new TextArea("Hello world");
        //Button search = new Button("Search");
        //search.setPrefSize(100, 20);
        //topMenu.getChildren().addAll(addr);

        return topMenu;
    }

    private ScrollPane createCrimeList() {
        GridPane crimeList = new GridPane();
        crimeList.setStyle("-fx-background-color: #ff5555;");
        crimeList.setHgap(10);
        crimeList.setVgap(10);
        crimeList.setPadding(new Insets(10, 20, 10, 20));
        Text type = new Text("Type of crime");
        type.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Text date = new Text("Date of crime");
        date.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Text address = new Text("Address");
        address.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        crimeList.add(type, 1, 0);
        crimeList.add(date, 2, 0);
        crimeList.add(address, 3, 0);
        for(int i = 1; i < 100; i++) {
            type = new Text("Battery");
            date = new Text("11/26/2019");
            address = new Text("5500 N St. Louis Ave");
            crimeList.add(type, 1, i);
            crimeList.add(date, 2, i);
            crimeList.add(address, 3, i);

        }
        ScrollPane s = new ScrollPane();
        s.setFitToHeight(true);
        s.setContent(crimeList);
        return s;
    }

    private HBox createBottomMenu() {
        HBox bottomMenu = new HBox();
        bottomMenu.setPadding(new Insets(15, 12, 15, 12));
        bottomMenu.setSpacing(10);
        bottomMenu.setStyle("-fx-background-color: #55ff55;");
        //TextArea addr = new TextArea("Hello world");
        //Button search = new Button("Search");
        //search.setPrefSize(100, 20);
        //topMenu.getChildren().addAll(addr);

        return bottomMenu;
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}