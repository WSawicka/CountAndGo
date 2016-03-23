package simple.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import simple.Simple;
import simple.model.Product;

/**
 * Created by mloda on 2015-07-12.
 */
public class NewProductController {
    public Simple simple;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private ComboBox units;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> unitsColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button goBack;

    @FXML
    public void handleAdd(){

    }

    @FXML
    public void handleDelete(){

    }

    @FXML
    public void handleGoBack(){
        simple.initPane();
        simple.showCount();
    }

    public void setSimple(Simple simple) {
        this.simple = simple;
    }
}
