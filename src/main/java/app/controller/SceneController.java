package app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.AppData;
import app.model.Item;
import app.model.Math;
import app.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class SceneController implements Initializable {

    private AppData appData = AppData.getInstance();
    private Math math = appData.getMath();
    private String productChosen;

    @FXML private Label unitLabel;
    @FXML private ComboBox productComboBox;
    @FXML private TextField amountField;
    @FXML private TextField priceField;
    @FXML private TextField amountUsedField;
    @FXML private TextField priceAllField;
    @FXML private TextField energySummary;
    @FXML private TextField waterSummary;
    @FXML private TextField timeSummary;

    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TableColumn<Item, Double> amountColumn;
    @FXML private TableColumn<Item, Double> priceColumn;

    @FXML
    private void handleClearTableView(ActionEvent event) {
        appData.getItems().clear();
        tableView.getItems().clear();
        tableView.getItems().addAll(appData.getItems());
        appData.setPriceAll(0.0);
        priceAllField.setText(appData.getPriceAll().toString());
        handleWyczysc(event);
    }

    @FXML
    private void handleDodaj(ActionEvent event) {
        Double amountUsed;
        try {
            amountUsed = Double.valueOf(amountUsedField.getText());
        } catch (NumberFormatException nfex){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Możesz wpisać tylko liczbę!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Product p = appData.getProducts().get(productChosen);
        double cost = (p.getPrice().doubleValue() * amountUsed) / p.getAmount().doubleValue();
        math.round4(cost);
        Item item = new Item(productChosen, amountUsed, cost);
        appData.getItems().add(item);
        tableView.setItems(appData.getItems());
        updatePriceAll_product(cost);
    }

    @FXML
    private void handleUsun(ActionEvent event) {
        Item item = tableView.getSelectionModel().getSelectedItem();
        if(item != null){
            double cost = item.getCost();
            math.round4(cost);
            int itemIndex = appData.getItemIndex(item);
            if(itemIndex != -1)
                appData.getItems().remove(itemIndex);
            tableView.setItems(appData.getItems());
            updatePriceAll_product(cost * (-1));
        }
    }

    @FXML
    private void handleWyczysc(ActionEvent event) {
        amountField.setText("");
        priceField.setText("");
        unitLabel.setText("");
    }

    @FXML
    private void handleComboBox(ActionEvent event) {
        productChosen = (String) productComboBox.getSelectionModel().getSelectedItem().toString();
        Product p = appData.getProducts().get(productChosen);
        if (p != null) {
            amountField.setText(p.getAmount().getValue().toString());
            priceField.setText(p.getPrice().getValue().toString());
            unitLabel.setText(p.getUnit().getValue());
        }
    }

    @FXML
    private void handleCountPower(ActionEvent event) throws IOException {
        showPowerCostEditor();
        energySummary.setText(appData.getPriceEnergy().toString());
    }

    @FXML
    private void handleCountWater(ActionEvent event) throws IOException {
        showWaterCostEditor();
        waterSummary.setText(appData.getPriceWater().toString());
    }

    @FXML
    private void handleCountTime(ActionEvent event) throws IOException {
        showTimeCostEditor();
        timeSummary.setText(appData.getPriceTime().toString());
    }
    
    private void updatePriceAll_product(double cost) {
        appData.addToPrice(cost);
        priceAllField.setText(appData.getPriceAll().toString());
    }

    public void updatePriceAll_extra() {
        double price = appData.getPriceEnergy() + appData.getPriceProducts() + appData.getPriceTime() + appData.getPriceWater();
        appData.setPriceAll(math.round4(price));
        priceAllField.setText(appData.getPriceAll().toString());
    }

    private void showPowerCostEditor() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PowerCostEditor.fxml"));
        Parent root = (Parent) loader.load();
        PowerCostEditorController pcec = loader.getController();
        Stage newStage = setSceneAndStage(root);
        pcec.setSceneController(this);
        newStage.showAndWait();
    }

    private void showWaterCostEditor() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WaterCostEditor.fxml"));
        Parent root = (Parent) loader.load();
        WaterCostEditorController wcec = loader.getController();
        Stage newStage = setSceneAndStage(root);
        wcec.setSceneController(this);
        newStage.showAndWait();
    }

    private void showTimeCostEditor() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TimeCostEditor.fxml"));
        Parent root = (Parent) loader.load();
        TimeCostEditorController tcec = loader.getController();
        Stage newStage = setSceneAndStage(root);
        tcec.setSceneController(this);
        newStage.showAndWait();
    }

    private Stage setSceneAndStage(Parent root) {
        Scene newScene = new Scene(root);
        newScene.getStylesheets().add("/styles/Styles.css");
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        return newStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> options = FXCollections.observableArrayList();
        appData.getAllProductsNames().forEach(name -> options.add(name));

        productComboBox.getItems().clear();
        productComboBox.setItems(FXCollections.observableArrayList(options));
        priceAllField.setText(appData.getPriceAll().toString());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        tableView.getItems().setAll(appData.getItems());
    }
}
