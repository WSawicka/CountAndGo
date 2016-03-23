package simple.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import simple.Simple;

import java.lang.reflect.InvocationTargetException;


/**
 * Created by mloda on 2015-07-12.
 */
public class PaneController {
    public Simple simple;
    @FXML
    private MenuItem newProduct;
    @FXML
    private MenuItem about;

    @FXML
    public void handleNewProduct(){
        simple.showNewProduct();
    }

    @FXML
    public void handleAbout(){

    }

    public void setMain(Simple simple){
        this.simple = simple;
    }
}
