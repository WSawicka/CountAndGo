package simple;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import simple.controller.CountController;
import simple.controller.NewProductController;
import simple.model.AppData;

import java.io.IOException;

public class Simple extends Application {
    private Stage stage;
    private BorderPane pane;
    public AppData appData;
    public Simple(){
        this.appData = new AppData();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        this.stage.setTitle("Simple");
        initPane();
        showCount();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public AppData getAppData() {
        return appData;
    }

    public void initPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Simple.class.getResource("view/Pane.fxml"));
            pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCount() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Simple.class.getResource("view/Count.fxml"));
            AnchorPane count = loader.load();
            pane.setCenter(count);
            CountController controller = loader.getController();
            controller.setSimple(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewProduct() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Simple.class.getResource("view/NewProduct.fxml"));
            AnchorPane product = loader.load();
            pane.setCenter(product);
            NewProductController controller = loader.getController();
            controller.setSimple(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
