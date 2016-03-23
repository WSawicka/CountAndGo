package simple.model;

import javafx.collections.ObservableList;

/**
 * Created by mloda on 2015-07-12.
 */
public class AppData {
        private ObservableList<Product> product;
        private ObservableList <Item> item;

        public AppData(){
            //TODO read from file
        }

        public ObservableList<Product> getAllProducts() {
            return product;
        }

        public ObservableList<Item> getAllItems() {
            return item;
        }

}
