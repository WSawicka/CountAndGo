package app.model;

import app.AlertWindow;
import app.file.XMLWrite;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.*;

/**
 * Created by ptaszysko on 18.02.2017.
 */
public class States {
    private List<State> stateList = new ArrayList<>();

    @Setter
    @Getter
    @AllArgsConstructor
    private class State {
        private String description;
        private Map<String, Double> energyWaterTime = new HashMap<>();
        private List<Item> items = new ArrayList<>();
    }

    public void saveState(String name, String desc, Map<String,Double> ewt, List<Item> it){
        State newState = new State(desc, ewt, it);
        this.stateList.add(newState);
        XMLWrite xmlWrite = new XMLWrite();
        xmlWrite.write(name, stateList);
    }

    public Map<String,String> getStatesNames(){
        //wczytaj poprzez State.getAll()

        return null;
    }

    public State getStateWith(String name){
        //wyszukaj po nazwie

        return null;
    }
}
