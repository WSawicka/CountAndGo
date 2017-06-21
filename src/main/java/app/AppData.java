package app;

import java.util.*;
import java.util.Map.Entry;

import app.controller.SceneController;
import app.model.Item;
import app.model.Math;
import app.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;

@Getter
@Setter
public class AppData {

    private Map<String, Product> products = new HashMap<>();
    private Map<String, Double> energyWaterTime = new HashMap<>();
    private ObservableList<Item> items = FXCollections.observableArrayList();

    private MainApp mainApp;
    private Math math = new Math();
    private SceneController controller;
    private Double priceAll = 0.0;
    private Double priceProducts = 0.0;
    private Double priceEnergy = 0.0;
    private Double priceWater = 0.0;
    private Double priceTime = 0.0;

    private String fileName;
    private String fileTitle;
    private LocalDate date;

    private String excelFilePath;

    private static AppData appData;

    public static AppData getInstance() {
        if (appData == null) {
            appData = new AppData();
        }
        return appData;
    }

    public void setAppData(MainApp mainApp, SceneController controller) {
        this.mainApp = mainApp;
        this.controller = controller;
    }

    public void addToPrice(double cost) {
        this.priceAll = (priceAll < 0) ? 0 : priceAll + cost;
        math.round4(this.priceAll);
    }

    public ObservableList<String> getAllProductsNames() {
        ObservableList<String> productsNames = FXCollections.observableArrayList();
        Iterator entries = products.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            Object key = thisEntry.getKey();
            productsNames.add(key.toString());
        }
        return productsNames;
    }

    public int getItemIndex(Item item){
        return items.indexOf(item);
    }

    public double getPriceProducts(){
        double all = 0.0;
        for(Item i : items){
            all += i.getCost();
        }
        return all;
    }
}
