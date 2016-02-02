package simple.model;

import javafx.beans.property.*;

/**
 * Created by mloda on 2015-07-12.
 */
public class Product {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty val;
    private DoubleProperty price;

    public Product(){
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty();
        this.val = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty(0.0);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getVal() {
        return val.get();
    }

    public StringProperty valProperty() {
        return val;
    }

    public void setVal(String val) {
        this.val.set(val);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}
