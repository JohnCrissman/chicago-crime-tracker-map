import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;


public class CrimeViewerApplication extends Application {
    private Crimes latestCrimes;
    private WebView mapView;
    private ScrollPane listView;

    @Override
    public void init() {
        try{
            this.latestCrimes = new Crimes();
        }catch (IOException | ParseException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start(Stage stage) {
        BorderPane basePane = new BorderPane();

        HBox topMenu = createTopMenu();
        basePane.setTop(topMenu);

        this.listView = setUpListView();
        this.mapView = setUpMapView();
        basePane.setCenter(this.mapView);

        BorderPane bottomMenu = createBottomMenu(basePane);
        basePane.setBottom(bottomMenu);

        //make visible
        Scene scene = new Scene(basePane, 1000, 700, Color.web("#666970"));
        stage.setScene(scene);
        stage.setTitle("Crimes in Chicago");
        stage.show();
    }

    private HBox createTopMenu() {
        HBox topMenu = new HBox();
        setPaneStyle(topMenu, "blue");
        topMenu.setSpacing(10);
        topMenu.setAlignment(Pos.CENTER);
        TextField addr = setUpAddressSearch();
        ChoiceBox<String> radius = setUpRadiusMenu();
        Button search = setUpSearchButton(addr, radius);
        topMenu.getChildren().addAll(addr, radius, search);

        return topMenu;
    }

    private void setPaneStyle(Pane menu, String colorName) {
        menu.setPadding(new Insets(15, 12, 15, 12));
        menu.setStyle("-fx-background-color: " + colorName + ";");
    }

    private Button setUpSearchButton(TextField addr, ChoiceBox<String> radius) {
        Button searchButton = new Button("Search");
        searchButton.setPrefSize(100, 20);
        searchButton.setOnAction(e -> {
            String searchQuery = addr.getCharacters().toString();
            String radiusSelection = radius.valueProperty().get();
            double radiusValue = Double.parseDouble(radiusSelection.split(" ")[0]);
            System.out.println("Address search: " + searchQuery + ", \tRadius: " + radiusValue + " mi");
            //TODO: execute API/ crimesRelativeTo tasks

            try {
                this.latestCrimes.setCrimesWithinRadius(radiusValue, searchQuery);
                m_Dummy.execJsFunc(this.mapView, this.latestCrimes, "rel");

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (NotARadiusException ex) {
                ex.printStackTrace();
                System.out.println("not a valid radius");
            } catch (NotAnAddressException ex) {
//                TODO: send a message to the user saying "No results. Enter a different address"
                ex.printStackTrace();
                System.out.println("not an address");
            }

        });
        return searchButton;
    }

    private ChoiceBox<String> setUpRadiusMenu() {
        ChoiceBox<String> radius = new ChoiceBox<>();
        radius.getItems().addAll
                ("0.1 mi", "0.25 mi", "0.5 mi", "0.75 mi", "1 mi", "2 mi", "5 mi");
        radius.setValue("0.1 mi");
        radius.setPrefWidth(100);
        return radius;
    }

    private TextField setUpAddressSearch() {
        TextField addr = new TextField("5500 N St Louis Ave");
        addr.setPrefWidth(400);
        return addr;
    }

    private WebView setUpMapView() {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        URL mapPage = this.getClass().getResource("b_mapdemo.html");
        webEngine.load(mapPage.toString());

        return webView;
    }

    private ScrollPane setUpListView() {
        GridPane crimeList = new GridPane();
        //style
        setPaneStyle(crimeList, "pink");
        crimeList.setHgap(10);
        crimeList.setVgap(10);
        crimeList.setAlignment(Pos.CENTER);
        //set up scrollable
        ScrollPane s = new ScrollPane();
        s.setFitToHeight(true);
        s.setFitToWidth(true);
        s.setContent(crimeList);

        //header row
        setUpListHeaders(crimeList);

        //table contents
        addCrimesToListView(crimeList);

        return s;
    }

    private void addCrimesToListView(GridPane crimeList) {
        int[] i = {0};
        //TODO: Type/description to normal case
        //TODO: In address, remove leading 0's and replace XX with 00
        this.latestCrimes.getCrimesRelativeTo().stream()
                .peek((aCrime)-> i[0]++)
                .sorted((cp1,cp2) -> (int)(cp1.getProximity() - cp2.getProximity()))
                .forEach((aCrime) -> {
                            crimeList.add(new Text(DateFormat.getDateInstance().format(aCrime.getDate())), 1, i[0]);
                            //crimeList.add(new Text(aCrime.getDate().toString()), 1, i[0]);
                            crimeList.add(new Text(aCrime.getType()), 2, i[0]);
                            crimeList.add(new Text(aCrime.getTypeDescription()), 3, i[0]);
                            crimeList.add(new Text(aCrime.getAddress().getFullAddress()), 4, i[0]);
                        }
                );
        /* Populate list with dummy information
        Text type;
        Text description;
        Text date;
        Text address;
        for(int i = 1; i < 100; i++) {
            type = new Text("Battery");
            description = new Text("Description");
            date = new Text("11/26/2019");
            address = new Text("5500 N St. Louis Ave, Chicago, IL");
            crimeList.add(date, 1, i);
            crimeList.add(type, 2, i);
            crimeList.add(description, 3, i);
            crimeList.add(address, 4, i);

        }*/
    }

    private void setUpListHeaders(GridPane crimeList) {
        Text type = new Text("Type of crime");
        type.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Text description = new Text("Description");
        description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Text date = new Text("Date");
        date.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Text address = new Text("Nearest block");
        address.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        crimeList.add(date, 1, 0);
        crimeList.add(type, 2, 0);
        crimeList.add(description, 3, 0);
        crimeList.add(address, 4, 0);
    }

    private BorderPane createBottomMenu(BorderPane basePane) {
        //create bottom pane and set up style
        BorderPane bottomMenu2 = new BorderPane();
        bottomMenu2.setPadding(new Insets(15, 12, 15, 12));
        bottomMenu2.setStyle("-fx-background-color: green;");

        HBox bottomMenu = new HBox();
        //bottomMenu.setAlignment(Pos.CENTER);

        Button mapViewButton = new Button("View Map");
        Button listViewButton = new Button("View List");

        //create View Map button
        mapViewButton.setPrefSize(100, 20);
        mapViewButton.setOnAction(e -> {
            basePane.setCenter(this.mapView);
            mapViewButton.setDisable(true);
            listViewButton.setDisable(false);
        });
        mapViewButton.setDisable(true);

        //create View List button
        listViewButton.setPrefSize(100, 20);
        listViewButton.setOnAction(e -> {
            //TODO: show the search on the list
//             this.latestCrimes.getCrimesRelativeTo()
//            updateCrimesRelativeToListView(???????????????)
            basePane.setCenter(this.listView);
            listViewButton.setDisable(true);
            mapViewButton.setDisable(false);
        });

        //create Exit button
        Button exitProgramButton = new Button("Exit");
        exitProgramButton.setPrefSize(100,20);
        exitProgramButton.setOnAction(e -> {
            try {
                this.stop();
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
            } } );

        //add buttons to pane
        bottomMenu.getChildren().addAll(
                mapViewButton,
                listViewButton,
                m_Dummy.createDummyBtn(mapView, latestCrimes) //TODO: <---- MARI should ERASE this method call
        );

        bottomMenu2.setLeft(bottomMenu);
        bottomMenu2.setRight(exitProgramButton);

        return bottomMenu2;
    }

    @Override
    public void stop() throws Exception {
        try {
            System.exit(0);
        } catch (SecurityException ex) {
            throw new Exception("Unable to quit program normally.");
        }
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}