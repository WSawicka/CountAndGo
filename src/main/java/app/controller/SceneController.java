package app.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import app.AlertWindow;
import app.AppData;
import app.file.ExcelRead;
import app.file.PDFCreate;
import app.model.AlertEnum;
import app.model.Item;
import app.model.Math;
import app.model.Product;
import com.itextpdf.text.DocumentException;
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
import javafx.stage.FileChooser;
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
        appData.setPriceProducts(0.0);
        priceAllField.setText(appData.getPriceAll().toString());
        handleWyczysc(event);
    }

    @FXML
    private void handleFindTyped() {
        productComboBox.hide();
        String typed = productComboBox.getEditor().getText();
        List<String> productsLike = this.appData.getProducts().entrySet().stream()
                .filter(e -> e.getKey().toLowerCase().contains(typed.toLowerCase()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        ObservableList<String> options = FXCollections.observableArrayList();
        productsLike.forEach(name -> options.add(name));
        FXCollections.sort(options);
        productComboBox.getItems().clear();
        productComboBox.setItems(FXCollections.observableArrayList(options));
        productComboBox.show();
    }

    @FXML
    private void handleDodaj(ActionEvent event) {
        Double amountUsed;
        try {
            amountUsed = Double.valueOf(amountUsedField.getText());
        } catch (NumberFormatException nfex){
            new AlertWindow().show(AlertEnum.NOT_NUMERIC_VALUE);
            return;
        }
        Product p = appData.getProducts().get(productChosen);

        if (p!= null){
            double cost = (p.getPrice().doubleValue() * amountUsed) / p.getAmount().doubleValue();
            math.round4(cost);
            Item item = new Item(productChosen, amountUsed, cost);
            appData.getItems().add(item);
            tableView.setItems(appData.getItems());
            updatePriceAll_product(cost);
        } else {
            new AlertWindow().show(AlertEnum.PRODUCT_NOT_FOUND);
            productComboBox.getEditor().setText("");
        }
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
        if(productComboBox.getSelectionModel().getSelectedItem() != null){
            productChosen = (String) productComboBox.getSelectionModel().getSelectedItem().toString();
            Product p = appData.getProducts().get(productChosen);
            if (p != null) {
                amountField.setText(p.getAmount().getValue().toString());
                priceField.setText(p.getPrice().getValue().toString());
                unitLabel.setText(p.getUnit().getValue());
            }
        }
        else productComboBox.getEditor().setText("");
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

    @FXML
    private void changeDBFilePath(ActionEvent event) throws FileNotFoundException {
        ExcelRead elRd = new ExcelRead();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage stage = new Stage();
        fileChooser.setTitle("Wybierz plik");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            elRd.saveXML(file.toString());
        }
        else throw new FileNotFoundException();
    }

    @FXML
    private void handleExportPDF(ActionEvent event) throws IOException, URISyntaxException, DocumentException {
        showExcelInfo();
        List<Item> listProd = new ArrayList<>(appData.getItems());
        PDFCreate pdf = new PDFCreate(listProd, appData);
        try {
            pdf.create();
        } catch (IOException ioex){
            new AlertWindow().show(AlertEnum.FILE_ERROR);
        }
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

    private Stage setSceneAndStage(Parent root) {
        Scene newScene = new Scene(root);
        newScene.getStylesheets().add("/styles/Styles.css");
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        return newStage;
    }

    private void showWindow(String controllerName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + controllerName +".fxml"));
        Parent root = loader.load();
        Controller c = loader.getController();
        Stage newStage = setSceneAndStage(root);
        c.setSceneController(this);
        newStage.showAndWait();
    }

    private void showPowerCostEditor() throws IOException {
        showWindow("PowerCostEditor");
    }

    private void showWaterCostEditor() throws IOException {
        showWindow("WaterCostEditor");
    }

    private void showTimeCostEditor() throws IOException {
        showWindow("TimeCostEditor");
    }

    private void showExcelInfo() throws IOException {
        showWindow("ExcelInfo");
    }

    private void showSetProduct() throws IOException {
        showWindow("SetProduct");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> options = FXCollections.observableArrayList();
        appData.getAllProductsNames().forEach(name -> options.add(name));

        productComboBox.getItems().clear();
        FXCollections.sort(options);
        productComboBox.setItems(FXCollections.observableArrayList(options));
        priceAllField.setText(appData.getPriceAll().toString());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        tableView.getItems().setAll(appData.getItems());
    }

    public Stage getStage(){
        return this.getStage();
    }
}
