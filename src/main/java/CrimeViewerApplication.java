import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;


public class CrimeViewerApplication extends Application {
    private Crimes latestCrimes;
    private WebView mapView;
    private ScrollPane listView;
    private TableView<Crime> table;
    private BorderPane basePane;

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
        this.basePane = new BorderPane();

        HBox topMenu = createTopMenu();
        this.basePane.setTop(topMenu);

        this.listView = setUpTableView();
        this.mapView = setUpMapView();
        this.basePane.setCenter(this.mapView);

        BorderPane bottomMenu = createBottomMenu(this.basePane);
        this.basePane.setBottom(bottomMenu);

        //make visible
        Scene scene = new Scene(basePane, 1000, 700, Color.web("#666970"));
        stage.setScene(scene);
        stage.setTitle("Crimes in Chicago");
        stage.show();
    }

    private HBox createTopMenu() {
        HBox topMenu = new HBox();
        setPaneStyle(topMenu, "#b6b6af");
        topMenu.setSpacing(10);
        topMenu.setAlignment(Pos.CENTER);
        TextField addr = setUpAddressSearch();
        ChoiceBox<String> radius = setUpRadiusMenu();
        Button search = setUpSearchButton(addr, radius);
        topMenu.getChildren().addAll(addr, radius, search);

        return topMenu;
    }

    private void setPaneStyle(Pane menu, String color) {
        menu.setPadding(new Insets(15, 12, 15, 12));
        menu.setStyle("-fx-background-color: " + color+ ";");
    }

    private Button setUpSearchButton(TextField addr, ChoiceBox<String> radius) {
        Button searchButton = new Button("Search");
        searchButton.setPrefSize(100, 20);

        searchButton.setOnAction(e -> {
            // get search query terms
            String searchQuery = addr.getCharacters().toString();
            String radiusSelection = radius.valueProperty().get();
            double radiusValue = Double.parseDouble(radiusSelection.split(" ")[0]);

            try {
                //update crimesRelativeTo in latestCrimes
                this.latestCrimes.setCrimesWithinRadius(radiusValue, searchQuery);

                // update map
                m_Dummy.execJsFunc(this.mapView, this.latestCrimes, "rel");

                //update list
                this.listView = setUpFilteredTableView();
                //TODO: this current starts with listView, but maybe change?
                this.basePane.setCenter(this.listView);
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
        URL mapPage = this.getClass().getResource("map.html");
        webEngine.load(mapPage.toString());

        return webView;
    }

    private ScrollPane setUpFilteredTableView() {
        VBox vb = new VBox();
        vb.getChildren().add(new Pane(new Label("Shift+click to sort by multiple columns.")));

        TableView<CrimeRelativeToAddress> table = new TableView<>();
        setUpNewFilteredTableView(table);

        System.out.println(this.latestCrimes.getCrimesRelativeTo().size());

        //add data
        ObservableList<CrimeRelativeToAddress> data =
                FXCollections.observableList(this.latestCrimes.getCrimesRelativeTo());
        table.setItems(data);
        vb.getChildren().add(table);
        // TODO: Make this child of vb extend to the bottom of the scrollable pane.

        //set up scrollable
        ScrollPane s = new ScrollPane();
        s.setFitToHeight(true);
        s.setFitToWidth(true);
        s.setContent(vb);

        return s;
    }

    private void setUpNewFilteredTableView(TableView<CrimeRelativeToAddress> table) {
        table.setEditable(false);

        //set up columns
        TableColumn<CrimeRelativeToAddress, Date> date = new TableColumn<>("Date");
        date.setMinWidth(100);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<CrimeRelativeToAddress, String> type = new TableColumn<>("Type");
        type.setMinWidth(150);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<CrimeRelativeToAddress, String> description = new TableColumn<>("Description");
        description.setMinWidth(300);
        description.setCellValueFactory(new PropertyValueFactory<>("typeDescription"));

        TableColumn<CrimeRelativeToAddress, String> address = new TableColumn<>("Address");
        address.setMinWidth(300);
        address.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddress().getFullAddress()));

        table.getColumns().addAll(date, type, description, address);
    }

    private ScrollPane setUpTableView() {
        VBox vb = new VBox();
        vb.getChildren().add(new Pane(new Label("Shift+click to sort by multiple columns.")));

        this.table = new TableView<>();
        setUpNewTableView();

        //add data
        ObservableList<Crime> data = FXCollections.observableList(this.latestCrimes.getAllCrimes());
        this.table.setItems(data);
        vb.getChildren().add(this.table);
        // TODO: Make this child of vb extend to the bottom of the scrollable pane.

        //set up scrollable
        ScrollPane s = new ScrollPane();
        s.setFitToHeight(true);
        s.setFitToWidth(true);
        s.setContent(vb);

        return s;
    }

    private void setUpNewTableView() {
        this.table.setEditable(false);

        //set up columns
        TableColumn<Crime, Date> date = new TableColumn<>("Date");
        date.setMinWidth(100);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Crime, String> type = new TableColumn<>("Type");
        type.setMinWidth(150);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Crime, String> description = new TableColumn<>("Description");
        description.setMinWidth(300);
        description.setCellValueFactory(new PropertyValueFactory<>("typeDescription"));

        TableColumn<Crime, String> address = new TableColumn<>("Address");
        address.setMinWidth(300);
        address.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddress().getFullAddress()));

        this.table.getColumns().addAll(date, type, description, address);
    }

    private BorderPane createBottomMenu(BorderPane basePane) {
        //create bottom pane and set up style
        BorderPane fullBottomMenu = new BorderPane();
        setPaneStyle(fullBottomMenu, " #b6b6af");

        HBox changeViewPalette = new HBox();

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

        //add buttons to pane
        changeViewPalette.getChildren().addAll(
                mapViewButton,
                listViewButton,
                m_Dummy.createDummyBtn(mapView, latestCrimes) //TODO: <---- MARI should ERASE this method call
        );

        //create Exit button
        Button exitProgramButton = new Button("Exit");
        exitProgramButton.setPrefSize(100,20);
        exitProgramButton.setOnAction(e -> {
            try {
                this.stop();
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
            } } );

        fullBottomMenu.setLeft(changeViewPalette);
        fullBottomMenu.setRight(exitProgramButton);

        return fullBottomMenu;
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