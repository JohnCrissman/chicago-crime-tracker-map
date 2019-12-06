import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class BottomMenu {
    private List<Button> buttonList;
    private List<Node> nodeList;
    private BorderPane fullBottomMenu;

    public BottomMenu(BorderPane basePane, Map<String, Node> toAdd) {
        //create buttons
        this.buttonList = toAdd.keySet().stream()
                                        .map(Button::new)
                                        .collect(toList());
        this.nodeList = new ArrayList<>(toAdd.values());

        // add each button to the palette
        HBox buttonPalette = new HBox();
        for(Button b : this.buttonList) {
            setUpAndAddButton(basePane, buttonPalette, b);
        }

        //disable the first button at start
        this.buttonList.stream().limit(1).forEach(btn -> btn.setDisable(true));

        Button exitProgramButton = createExitButton();

        //create full bottom menu
        this.fullBottomMenu = new BorderPane();
        this.fullBottomMenu.setLeft(buttonPalette);
        this.fullBottomMenu.setRight(exitProgramButton);

        basePane.setBottom(this.fullBottomMenu);
    }

    private void setUpAndAddButton(BorderPane basePane, HBox buttonPalette, Button b) {
        b.setPrefSize(100, 20);
        b.setOnAction(e -> {
            Node showThisNode = this.nodeList.get(this.buttonList.indexOf(b));
            basePane.setCenter(showThisNode);
            this.buttonList.forEach(btn -> btn.setDisable(false));
            b.setDisable(true);
        });
        buttonPalette.getChildren().add(b);
    }

    private Button createExitButton() {
        Button exitProgramButton = new Button("Exit");
        exitProgramButton.setPrefSize(100,20);
        exitProgramButton.setOnAction(e -> {
            try {
                System.exit(0);
            } catch(SecurityException ex) {
                System.out.println("Unable to quit program normally.");
            } } );
        return exitProgramButton;
    }

    public BorderPane getFullBottomMenu() {
        return fullBottomMenu;
    }
}
