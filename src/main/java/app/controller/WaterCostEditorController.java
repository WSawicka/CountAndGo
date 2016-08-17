package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by mloda on 2016-08-17.
 */
public class WaterCostEditorController {
    @FXML private Label priceByMeter;
    @FXML private TextField consumption;

    private SceneController controller;

    public void setSceneController(SceneController controller) {
        this.controller = controller;
        priceByMeter.setText(this.controller.getAppData().getEnergyWaterTime().get("woda").toString());
    }

    @FXML private void handleSave (ActionEvent event) {
        double cost = Double.parseDouble(priceByMeter.getText()) * Double.parseDouble(consumption.getText());
        cost = this.controller.getMath().round4(cost);
        this.controller.getAppData().setPriceWater(cost);
        this.controller.updatePriceAll_extra();
        Stage stage = (Stage) this.priceByMeter.getScene().getWindow();
        stage.close();
    }
}
