package app;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private StringProperty unit;
    private DoubleProperty amount;
    private DoubleProperty price;

    public Product() {
        this.unit = new SimpleStringProperty();
        this.amount = new SimpleDoubleProperty(0.0);
        this.price = new SimpleDoubleProperty(0.0);
    }

    public Product(Double amount, Double price, String unit) {
        this.unit = new SimpleStringProperty(unit);
        this.amount = new SimpleDoubleProperty(amount);
        this.price = new SimpleDoubleProperty(price);
    }


}
