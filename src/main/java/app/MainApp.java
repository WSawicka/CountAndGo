package app;

import java.io.IOException;

import app.file.ExcelRead;
import app.model.AlertEnum;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        readFile();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("CountAndGo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void readFile(){
        ExcelRead read = new ExcelRead();
        try {
            read.readFile();
        } catch(InvalidFormatException ifex){
            new AlertWindow().show(AlertEnum.INVALID_FILE_FORMAT);
        } catch(IOException ioex){
            new AlertWindow().show(AlertEnum.FILE_NOT_FOUND);
        }
    }
}
