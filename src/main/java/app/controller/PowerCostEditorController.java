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
public class PowerCostEditorController implements Controller{
    @FXML private Label priceByHour;
    @FXML private TextField consumption;
    @FXML private TextField power;

    private SceneController controller;

    @Override
    public void setSceneController(SceneController controller) {
        this.controller = controller;
        priceByHour.setText(this.controller.getAppData().getEnergyWaterTime().get("prąd").toString());
    }

    @FXML
    private void handleSave(ActionEvent event){
        double cost = 0.0;
        try {
            cost = Double.parseDouble
                    (priceByHour.getText()) * (Double.parseDouble(consumption.getText()) / 60) * Double.parseDouble(power.getText());
        } catch(NumberFormatException nfex){
            new AlertWindow().show(AlertEnum.NOT_NUMERIC_VALUE);
            return;
        }

        cost /= 1000;
        cost = this.controller.getMath().round4(cost);
        this.controller.getAppData().setPriceEnergy(cost);
        this.controller.updatePriceAll_extra();
        Stage stage = (Stage) this.priceByHour.getScene().getWindow();
        stage.close();
    }
}
