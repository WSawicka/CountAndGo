package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Calendar;

/**
 * Created by mloda on 2016-08-18.
 */
public class ExcelInfoController implements Controller{
    @FXML private TextField name;
    @FXML private TextField title;
    @FXML private TextField year;
    @FXML private TextField month;
    @FXML private TextField day;

    private SceneController controller;

    @Override
    public void setSceneController(SceneController controller) {
        this.controller = controller;
    }

    @FXML
    private void handleExport(ActionEvent event){
        this.controller.getAppData().setFileName(name.getText());
        this.controller.getAppData().setFileTitle(title.getText());
        int y = Integer.parseInt(year.getText());
        int m = Integer.parseInt(month.getText());
        int d = Integer.parseInt(day.getText());
        Calendar date = Calendar.getInstance();
        date.set(y, m, d);
        this.controller.getAppData().setDate(date);
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }
}
