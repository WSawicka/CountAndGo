package simple.model;

import javafx.beans.property.IntegerProperty;

/**
 * Created by mloda on 2015-07-12.
 */
public class Item {

    private Product product;
    private IntegerProperty amount;

    public Item(){}

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }
}
