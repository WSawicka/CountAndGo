package app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SceneController implements Initializable {

    private AppData appData = AppData.getInstance();
    private String productChosed;

    @FXML private Label unitLabel;
    @FXML private ComboBox productComboBox;
    @FXML private TextField amountField;
    @FXML private TextField priceField;
    @FXML private TextField amountUsedField;
    @FXML private TextField priceAllField;
    @FXML private TextField energyCost;
    @FXML private TextField energyUsed;
    @FXML private TextField energyPower;
    @FXML private TextField waterCost;
    @FXML private TextField waterUsed;
    @FXML private TextField timeCost;
    @FXML private TextField timeUsed;
    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TableColumn<Item, Double> amountColumn;
    @FXML private TableColumn<Item, Double> priceColumn;

    @FXML
    private void handleClearTableView(ActionEvent event) {
        appData.getItems().clear();
        tableView.refresh();
        appData.setPriceAll(0.0);
        priceAllField.setText(appData.getPriceAll().toString());
        handleWyczysc(event);
        energyUsed.setText("");
        energyPower.setText("");
        waterUsed.setText("");
        timeUsed.setText("");
    }

    @FXML
    private void handleDodaj(ActionEvent event) {
        Double amountUsed = Double.valueOf(amountUsedField.getText());
        Product p = appData.getProducts().get(productChosed);
        double cost = (p.getPrice().doubleValue() * amountUsed) / p.getAmount().doubleValue();
        Item item = new Item(productChosed, amountUsed, cost);
        appData.getItems().add(item);
        tableView.setItems(appData.getItems());
        addAndUpdatePriceAll(cost);
    }

    @FXML
    private void handleUsun(ActionEvent event) {
    }

    @FXML
    private void handleWyczysc(ActionEvent event) {
        amountField.setText("");
        priceField.setText("");
        unitLabel.setText("");
    }

    @FXML
    private void handleComboBox(ActionEvent event) {
        productChosed = (String) productComboBox.getSelectionModel().getSelectedItem().toString();
        Product p = appData.getProducts().get(productChosed);
        if (p != null) {
            amountField.setText(p.getAmount().getValue().toString());
            priceField.setText(p.getPrice().getValue().toString());
            unitLabel.setText(p.getUnit().getValue());
        }
    }

    @FXML
    private void handleUpdatePower(ActionEvent event) {
        if (!energyCost.getText().equals("") && !energyUsed.getText().equals("") && !energyPower.getText().equals("")) {
            double cost = Double.parseDouble(energyCost.getText()) * (Double.parseDouble(energyUsed.getText()) / 60) * Double.parseDouble(energyPower.getText());
            cost /= 1000;
            addAndUpdatePriceAll(cost);
        }
    }

    @FXML
    private void handleUpdateWater(ActionEvent event) {
        if (!waterCost.getText().equals("") && !waterUsed.getText().equals("")) {
            double cost = Double.parseDouble(waterCost.getText()) * Double.parseDouble(waterUsed.getText());
            addAndUpdatePriceAll(cost);
        }
    }

    @FXML
    private void handleUpdateTime(ActionEvent event) {
        if (!timeCost.getText().equals("") && !timeUsed.getText().equals("")) {
            double cost = Double.parseDouble(timeCost.getText()) * Double.parseDouble(timeUsed.getText());
            addAndUpdatePriceAll(cost);
        }
    }

    private void addAndUpdatePriceAll(double cost) {
        appData.addToPrice(cost);
        priceAllField.setText(appData.getPriceAll().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> options = FXCollections.observableArrayList();
        appData.getAllProductsNames().forEach(name -> options.add(name));

        productComboBox.getItems().clear();
        productComboBox.setItems(FXCollections.observableArrayList(options));
        priceAllField.setText(appData.getPriceAll().toString());

        energyCost.setText(appData.getEnergyWaterTime().get("prąd").toString());
        waterCost.setText(appData.getEnergyWaterTime().get("woda").toString());
        timeCost.setText(appData.getEnergyWaterTime().get("praca").toString());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        tableView.getItems().setAll(appData.getItems());
    }
}
