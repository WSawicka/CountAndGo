package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import app.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class MainApp extends Application {

    private AppData appData = AppData.getInstance();

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

    public void readFile() throws IOException, InvalidFormatException {
        try {
            FileInputStream file = new FileInputStream(new File("Data.xls"));
            Workbook workbook = WorkbookFactory.create(file); //reference to .xlsx file
            Sheet sheet = workbook.getSheetAt(0); //first/desired sheet from the workbook

            Map<String, Product> productsTemp = new HashMap<>();
            Iterator<Row> rowIterator = sheet.iterator();
            for (int i = 0; i < 3; i++) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Cell cell1 = cellIterator.next();
                String name = cell1.getStringCellValue();
                //amount cell
                Cell cell2 = cellIterator.next();
                Double price = cell2.getNumericCellValue();
                
                appData.getEnergyWaterTime().put(name, price);
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator(); //For each row, through all the 4 columns

                //name cell
                Cell cell1 = cellIterator.next();
                String name = cell1.getStringCellValue();
                //amount cell
                Cell cell2 = cellIterator.next();
                Double amount = cell2.getNumericCellValue();
                //price cell
                Cell cell3 = cellIterator.next();
                Double price = cell3.getNumericCellValue();
                //unit cell
                Cell cell4 = cellIterator.next();
                String unit = cell4.getStringCellValue();

                Product product = new Product(amount, price, unit);
                productsTemp.put(name, product);
            }

            appData.setProducts(productsTemp);
            file.close();
        } catch (FileNotFoundException fnfex) {
            Alert alert = new Alert(AlertType.ERROR, "Ups! Nie znaleziono pliku.", ButtonType.OK);
            alert.showAndWait();
            throw new RuntimeException();
        }
    }
}
