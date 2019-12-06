import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class TopMenu {
    private TextField addressSearchField;
    private ChoiceBox<String> radiusMenu;
    private Button searchButton;
    private HBox topMenuPane;

    public TopMenu(BorderPane basePane) {
        this.topMenuPane = new HBox();
        this.topMenuPane.setSpacing(10);
        this.topMenuPane.setAlignment(Pos.CENTER);
        TopMenu.setPaneStyle(this.topMenuPane, "#b6b6af");

        this.setUpAddressSearch();
        this.setUpRadiusMenu();
        this.setUpSearchButton();
        this.topMenuPane.getChildren().addAll(this.addressSearchField, this.radiusMenu, this.searchButton);

        basePane.setTop(this.topMenuPane);
    }

    private void setUpAddressSearch() {
        this.addressSearchField = new javafx.scene.control.TextField("5500 N St Louis Ave");
        this.addressSearchField.setPrefWidth(400);
    }

    private void setUpRadiusMenu() {
        this.radiusMenu = new ChoiceBox<>();
        this.radiusMenu.getItems().addAll("0.25 mi", "0.5 mi", "0.75 mi", "1 mi", "2 mi", "5 mi");
        this.radiusMenu.setValue("0.5 mi");
        this.radiusMenu.setPrefWidth(100);
    }

    private void setUpSearchButton() {
        this.searchButton = new Button("Search");
        this.searchButton.setPrefSize(100, 20);
    }

    public static void setPaneStyle(Pane menu, String color) {
        menu.setPadding(new Insets(15, 12, 15, 12));
        menu.setStyle("-fx-background-color: " + color+ ";");
    }

    public TextField getAddressSearchField() {
        return addressSearchField;
    }
    public ChoiceBox<String> getRadiusMenu() {
        return radiusMenu;
    }
    public Button getSearchButton() {
        return searchButton;
    }
    public HBox getTopMenuPane() {
        return topMenuPane;
    }
}
