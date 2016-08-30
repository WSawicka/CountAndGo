package app.file;

import app.AlertWindow;
import app.AppData;
import app.model.AlertEnum;
import app.model.Product;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mloda on 2016-08-30.
 */
public class ExcelRead {
    private AppData appData = AppData.getInstance();
    private Map<String, Product> productsTemp;

    public ExcelRead(){
        productsTemp = new HashMap<>();
    }

    public void readFile() throws IOException {
        String filePath = getFile();
        this.appData.setExcelFilePath(filePath);

        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(file); //reference to .xlsx file
            Sheet sheet = workbook.getSheetAt(0); //first/desired sheet from the workbook

            Iterator<Row> rowIterator = sheet.iterator();
            setExtras(rowIterator);
            setProducts(rowIterator);

            appData.setProducts(productsTemp);
            file.close();
        } catch (FileNotFoundException fnfex) {
            new AlertWindow().show(AlertEnum.FILE_NOT_FOUND);
            throw new RuntimeException();
        } catch (InvalidFormatException ifex) {
            new AlertWindow().show(AlertEnum.FILE_NOT_FOUND);
        }
    }

    private void setExtras(Iterator<Row> rowIterator){
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
    }

    private void setProducts(Iterator<Row> rowIterator){
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
    }

    private String getFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage stage = new Stage();
        fileChooser.setTitle("Wybierz plik");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return file.toString();
        }
        else throw new FileNotFoundException();
    }
}
