package thirteen.component.tile;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import thirteen.store.Store;
import thirteen.store.StoreFactory;

import java.util.Observable;
import java.util.Observer;

public class LevelLabel extends Label implements Observer {

    private Store simpleStore;
    private Integer level;

    public LevelLabel(){

        this.simpleStore = StoreFactory.getStore();
        this.simpleStore.addObserver(this);

        this.level = simpleStore.getScoreState();

        setStyle("-fx-font: 20 arial");
        setText(level.toString());

        setAlignment(Pos.CENTER);
    }


    @Override
    public void update(Observable o, Object arg) {
        this.level = simpleStore.getScoreState();
        setText(level.toString());
    }
}
