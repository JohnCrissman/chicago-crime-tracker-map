import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class BottomMenu {
    private List<Button> buttonList;
    private List<Node> nodeList;
    private BorderPane fullBottomMenu;

    public BottomMenu(BorderPane basePane, LinkedHashMap<String, Node> toAdd) {
        //create buttons
        this.buttonList = toAdd.keySet().stream()
                                        .map(Button::new)
                                        .collect(toList());
        this.nodeList = new ArrayList<>(toAdd.values());

        // add each button to the palette
        HBox buttonPalette = new HBox();
        for(Button b : this.buttonList) {
            createAndAddButton(basePane, buttonPalette, b);
        }

        //disable the first button at start
        this.buttonList.stream().limit(1).forEach(btn -> btn.setDisable(true));

        Button exitProgramButton = createExitButton();

        //create full bottom menu
        this.fullBottomMenu = new BorderPane();
        TopMenu.setPaneStyle(this.fullBottomMenu, " #b6b6af");
        this.fullBottomMenu.setLeft(buttonPalette);
        this.fullBottomMenu.setRight(exitProgramButton);

        basePane.setBottom(this.fullBottomMenu);
    }

    private void createAndAddButton(BorderPane basePane, HBox buttonPalette, Button button) {
        button.setPrefSize(100, 20);
        button.setOnAction(e -> {
            Node showThisNode = this.nodeList.get(this.buttonList.indexOf(button));
            basePane.setCenter(showThisNode);
            this.buttonList.forEach(btn -> btn.setDisable(false));
            button.setDisable(true);
        });
        buttonPalette.getChildren().add(button);
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
}
