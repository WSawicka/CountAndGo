package simple.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import simple.Simple;
import simple.model.Item;

/**
 * Created by mloda on 2015-07-12.
 */
public class CountController {
    public Simple simple;
    @FXML
    private ComboBox name;
    @FXML
    private TextField amount;
    @FXML
    private TextField unit;
    @FXML
    private TextField price;
    @FXML
    private TextField totalPrice;
    @FXML
    private TextField amountUsed;
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Integer> amountColumn;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button cancel;
    @FXML
    private Button clearList;

    @FXML
    public void initialize(){
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().nameProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
    }

    @FXML
    public void handleAdd(){
        simple.showNewProduct();
    }

    @FXML
    public void handleDelete() {
    }

    @FXML
    public void handleCancel(){

    }

    @FXML
    public void handleClearList(){

    }

    public void setSimple(Simple simple) {
        this.simple = simple;
        //name.getItems().addAll(simple.getAppData().getAllProducts());
    }
}
