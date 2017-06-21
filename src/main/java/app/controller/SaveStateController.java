package app.controller;

import app.AlertWindow;
import app.model.AlertEnum;
import app.model.StatesOrigin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by ptaszysko on 18.02.2017.
 */
public class SaveStateController implements Controller{
    @FXML TextField nameField;
    @FXML TextArea descriptionArea;

    private SceneController controller;
    private String name;

    @FXML
    private void setStateFileName(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("State File");
        Stage stage = new Stage();
        fileChooser.setTitle("Zapisz stan do");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
        this.name = fileChooser.showOpenDialog(stage).toString();
        nameField.setText(this.name);
    }

    @FXML
    private void handleSave(ActionEvent event){
        StatesOrigin origin = new StatesOrigin();
        origin.saveState(this.name, descriptionArea.getText(),
                this.controller.getAppData().getEnergyWaterTime(), this.controller.getAppData().getItems());

        Stage stage = (Stage) this.nameField.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setSceneController(SceneController controller) {
        this.controller = controller;
    }
}
