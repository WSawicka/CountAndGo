package app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppData {

    private Map<String, Product> products = new HashMap<>();
    private Map<String, Double> energyWaterTime = new HashMap<>();
    private ObservableList<Item> items = FXCollections.observableArrayList();
    private MainApp mainApp;
    private SceneController controller;
    private Double priceAll = 0.0;

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
        this.priceAll += cost;
    }

    public ObservableList<String> getAllProductsNames() {
        ObservableList<String> productsNames = FXCollections.observableArrayList();
        Iterator entries = products.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            Object key = thisEntry.getKey();
            productsNames.add(key.toString());
        }
        return productsNames.sorted();
    }
}
