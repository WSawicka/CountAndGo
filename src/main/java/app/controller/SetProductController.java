package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by mloda on 2016-08-24.
 */
public class SetProductController implements Controller{
    @FXML private TextField name;
    @FXML private TextField price;
    @FXML private TextField amount;
    @FXML private TextField unit;

    private SceneController controller;

    @Override
    public void setSceneController(SceneController controller) {
        this.controller = controller;
    }

    @FXML
    private void handleSave(ActionEvent event){
        Stage stage = (Stage) this.name.getScene().getWindow();
        stage.close();
    }
}
