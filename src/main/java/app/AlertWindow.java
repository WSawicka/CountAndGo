package app;

import app.model.AlertEnum;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by mloda on 2016-08-30.
 */
public class AlertWindow {
    public void show(AlertEnum alertEnumm){
        Alert alert = new Alert(Alert.AlertType.ERROR, alertEnumm.getText(), ButtonType.OK);
        alert.showAndWait();
    }
}
