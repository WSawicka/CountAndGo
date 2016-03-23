package app;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Setter;


@Setter
public class Item {

    private StringProperty product;
    private DoubleProperty amount;
    private DoubleProperty cost;

    public Item() {
    }

    public Item(String product, Double amount, Double cost) {
        this.product = new SimpleStringProperty(product);
        this.amount = new SimpleDoubleProperty(amount);
        this.cost = new SimpleDoubleProperty(cost);
    }

    public String getProduct() {
        return product.get();
    }

    public Double getAmount() {
        return amount.get();
    }

    public Double getCost() {
        return cost.get();
    }
}
