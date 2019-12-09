import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class CrimeViewerApplication extends Application {
    private Crimes latestCrimes;
    private WebView mapView;
    private ScrollPane listView;
    private SummaryView summaryView;
    private ScrollPane summaryPane;
    private TableView<CrimeRelativeToAddress> table;

    @Override
    public void init() {
        try{
            this.latestCrimes = new Crimes();
        }catch (IOException e){
            System.out.println("You must be connected to the internet; please check your connection and try again.");
            System.out.println(e.getMessage());
            System.exit(0);
        } catch(ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {
        BorderPane basePane = new BorderPane();

        createTopMenu(basePane);
        createMainViews(basePane);
        createBottomMenu(basePane);

        Scene scene = new Scene(basePane, 1000, 700, Color.web("#666970"));
        stage.setScene(scene);
        stage.setTitle("Crimes in Chicago");
        stage.show();
    }

    private void createMainViews(BorderPane basePane) {
        createTableView();
        this.mapView = new WebView();
        WebEngine webEngine = this.mapView.getEngine();

        URL mapPage = this.getClass().getResource("map.html");
        webEngine.load(mapPage.toString());
        createSummaryView();

        basePane.setCenter(this.mapView);
    }

    private void createTopMenu(BorderPane basePane) {
        TopMenu topMenu = new TopMenu(basePane);
        this.createSearchFunction(topMenu);
    }

    private void createBottomMenu(BorderPane basePane) {
        //set up properties for each button
        LinkedHashMap<String, Node> menu = new LinkedHashMap<>(3);
        menu.put("View Map", this.mapView);
        menu.put("View List", this.listView);
        menu.put("Summary", this.summaryPane);
        BottomMenu bottom = new BottomMenu(basePane, menu);
    }

    private void createSearchFunction(TopMenu topMenu) {
        Button search = topMenu.getSearchButton();
        TextField addr = topMenu.getAddressSearchField();
        ChoiceBox<String> radius = topMenu.getRadiusMenu();

        search.setOnAction(e -> {
            // acquire search terms
            String searchQuery = addr.getCharacters().toString();
            String radiusSelection = radius.valueProperty().get();
            double radiusValue = Double.parseDouble(radiusSelection.split(" ")[0]);
            System.out.println(searchQuery + ", radius: " + radiusSelection + ", radiusValue: "+ radiusValue);

            try {
                //update crimesRelativeTo in latestCrimes
                this.latestCrimes.setCrimesWithinRadius(radiusValue, searchQuery);

                //update search bar to show query result
                addr.setText(this.latestCrimes.getRelativeAddress().getFullAddress());

                updateMapView();
                updateTableView();
                this.summaryView.updateForNewAddress();

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (NotARadiusException ex) {
                ex.printStackTrace();
                System.out.println("not a valid radius");
            } catch (NotAnAddressException ex) {
                addr.setText("No address found. Please try a different search.");
            }
        });
    }

    private void createTableView() {
        this.table = new TableView<>();
        createTableDesign();
        this.table.setPlaceholder(new Label("To get started, search for an address above!"));

        // make a scroll bar
        this.listView = new ScrollPane();
        this.listView.setFitToHeight(true);
        this.listView.setFitToWidth(true);
        this.listView.setContent(table);
    }

    private void createTableDesign() {
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

        TableColumn<CrimeRelativeToAddress, String> address = new TableColumn<>("Block");
        address.setMinWidth(300);
        address.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddress().getFullAddress()));

        table.getColumns().addAll(date, type, description, address);
    }

    private void createSummaryView() {
        this.summaryPane = new ScrollPane();
        this.summaryView = new SummaryView(this.latestCrimes);
        FlowPane vb = this.summaryView.getViewOfCharts();

        this.summaryPane.setContent(vb);
    }

    private void updateMapView() {
        String jsFunctionCall;
        jsFunctionCall = "showCrimesOnMap('" +
                JSONArray.toJSONString(this.latestCrimes.getCrimesRelativeTo()
                        .stream()
                        .limit(1000)
                        .collect(toList())) + "', '" + this.latestCrimes.getRelativeAddress().toString() + "')";

        this.mapView.getEngine().executeScript(jsFunctionCall);
    }

    private void updateTableView() {
        System.out.println("Crimes found within radius: " + this.latestCrimes.count());

        ObservableList<CrimeRelativeToAddress> data =
                FXCollections.observableList(this.latestCrimes.getCrimesRelativeTo());
        table.setItems(data);
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