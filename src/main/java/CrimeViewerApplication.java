import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private SummaryView scv;
    private ScrollPane summaryView;
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
        this.listView = setUpTableView();
        this.mapView = setUpMapView();
        this.summaryView = setUpSummaryView();

        basePane.setCenter(this.mapView);
    }

    private void createTopMenu(BorderPane basePane) {
        TopMenu topMenu = new TopMenu(basePane);
        setPaneStyle(topMenu.getTopMenuPane(), "#b6b6af");
        this.setUpSearchButton(topMenu);
    }

    private void setUpSearchButton(TopMenu topMenu) {
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

                // update map view
                updateMapView();

                //update list view
                updateTableView();

                //update summary view
                this.scv.updateSummaryForNewAddress();
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

    private void updateMapView() {
        String jsFunctionCall;
        jsFunctionCall = "showCrimesOnMap('" +
                JSONArray.toJSONString(this.latestCrimes.getCrimesRelativeTo()
                        .stream()
                        .limit(1000)
                        .collect(toList())) + "', '" + this.latestCrimes.getRelativeAddress().toString() + "')";

        this.mapView.getEngine().executeScript(jsFunctionCall);
    }

    private WebView setUpMapView() {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        URL mapPage = this.getClass().getResource("map.html");
        webEngine.load(mapPage.toString());

        return webView;
    }

    private ScrollPane setUpTableView() {
        this.table = new TableView<>();
        setUpTableDesign();
        this.table.setPlaceholder(new Label("To get started, search for an address above!"));

        // make a scroll bar
        ScrollPane s = new ScrollPane();
        s.setFitToHeight(true);
        s.setFitToWidth(true);
        s.setContent(table);

        return s;
    }

    private void setUpTableDesign() {
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

    private void updateTableView() {
        System.out.println("Crimes found within radius: " + this.latestCrimes.count());

        ObservableList<CrimeRelativeToAddress> data =
                FXCollections.observableList(this.latestCrimes.getCrimesRelativeTo());
        table.setItems(data);
    }

    private ScrollPane setUpSummaryView() {
        ScrollPane s = new ScrollPane();
        this.scv = new SummaryView(this.latestCrimes);
        FlowPane vb = this.scv.getViewOfCharts();

        s.setContent(vb);
        return s;
    }

    private void createBottomMenu(BorderPane basePane) {
        //set up properties for each button
        Map<String, Node> menu = new LinkedHashMap<>(3);
        menu.put("View Map", this.mapView);
        menu.put("View List", this.listView);
        menu.put("Summary", this.summaryView);

        BottomMenu bottom = new BottomMenu(basePane, menu);
        setPaneStyle(bottom.getFullBottomMenu(), " #b6b6af");
    }

    private void setPaneStyle(Pane menu, String color) {
        menu.setPadding(new Insets(15, 12, 15, 12));
        menu.setStyle("-fx-background-color: " + color+ ";");
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