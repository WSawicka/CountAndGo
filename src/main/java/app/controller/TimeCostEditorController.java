package app.controller;

import app.AlertWindow;
import app.model.AlertEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by mloda on 2016-08-17.
 */
public class TimeCostEditorController implements Controller{
    @FXML private Label priceByHour;
    @FXML private TextField time;

    private SceneController controller;

    @Override
    public void setSceneController(SceneController controller) {
        this.controller = controller;
        priceByHour.setText(this.controller.getAppData().getEnergyWaterTime().get("praca").toString());
    }

    @FXML private void handleSave (ActionEvent event){
        double cost = 0.0;
        try {
            cost = Double.parseDouble(priceByHour.getText()) * Double.parseDouble(time.getText());
        }  catch(NumberFormatException nfex){
            new AlertWindow().show(AlertEnum.NOT_NUMERIC_VALUE);
            return;
        }
        cost = this.controller.getMath().round4(cost);
        this.controller.getAppData().setPriceTime(cost);
        this.controller.updatePriceAll_extra();
        Stage stage = (Stage) this.priceByHour.getScene().getWindow();
        stage.close();
    }
}
