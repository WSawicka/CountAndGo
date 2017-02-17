package app.file;

import app.AlertWindow;
import app.AppData;
import app.model.AlertEnum;
import app.model.Product;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

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

            try{
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
            } catch (NoSuchElementException nseex) {
                //add nothing
            }
        }
    }

    public void saveXML(String path){
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("settings");
        doc.appendChild(rootElement);

        Element staff = doc.createElement("database");
        rootElement.appendChild(staff);
        staff.setAttribute("id", "1");

        Element file = doc.createElement("file");
        file.appendChild(doc.createTextNode(path));
        staff.appendChild(file);


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("settings.xml"));

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private String getFile() throws FileNotFoundException {
        //at first try to get file from xml
        try {
            File fXmlFile = new File("settings.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("database");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    return eElement.getElementsByTagName("file").item(0).getTextContent();
                }
            }
        } catch (Exception ex){
            System.out.println("Not found! now ask user about file and then save it.");
        }

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
            saveXML(file.toString());
            return file.toString();
        }
        else throw new FileNotFoundException();
    }
}
