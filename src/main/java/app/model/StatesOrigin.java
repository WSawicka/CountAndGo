package app.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ptaszysko on 18.02.2017.
 */
public class StatesOrigin {
    private States states = new States();
    private Map<String,String> stateList = new HashMap<>();

    public void saveState(String name, String desc, Map<String,Double> ewt, List<Item> it){
        this.states.saveState(name, desc, ewt, it);
    }

    public States restoreState(String name){

        return null;
    }
}
