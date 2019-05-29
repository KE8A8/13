package thirteen.component.tile;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import thirteen.store.Store;
import thirteen.store.StoreFactory;

import java.util.Observable;
import java.util.Observer;

public class Tile extends Button implements Observer {

    private Store simpleStore;

    private int x;
    private int y;

    private Integer value;

    public Tile(int x, int y) {

        this.simpleStore = StoreFactory.getStore();
        this.simpleStore.addObserver(this);

        this.x = x;
        this.y = y;

        this.value = simpleStore.getGameboardState().get(x).get(y);

        setText(value.toString());
        setAlignment(Pos.CENTER);

        setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                int[] position = {x,y};
                if( simpleStore.isBomb() ){
                    simpleStore.bombGameBoardAction(position);
                }else{
                simpleStore.incrementTileAction(position);
                }
            }

        });

    }
    @Override
    public void update(Observable o, Object arg) {
        this.value = simpleStore.getGameboardState().get(x).get(y);
        setText(value.toString());
        setColor(value);
    }

    private void setColor(int val){
        switch (val){
            case 1:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(0,255,0,0.55)");
                break;
            case 2:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(238,34,17,0.54);");
                break;
            case 3:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(240,255,51,0.58)");
                break;
            case 4:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(51,255,227,0.54)");
                break;
            case 5:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(51,63,255,0.62)");
                break;
            case 6:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(162,51,255,0.58)");
                break;
            case 7:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(255,51,255,0.6)");
                break;
            case 8:
                setStyle("-fx-font: 20 arial; -fx-background-color: #ffe8b0");
                break;
            case 9:
                setStyle("-fx-font: 20 arial; -fx-background-color: #296e70");
                break;
            case 10:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(112,12,16,0.62)");
                break;
            case 11:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(19,112,75,0.73)");
                break;
            case 12:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(95,56,112,0.51)");
                break;
            case 13:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(101,112,61,0.49)");
                break;
            case 14:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(112,50,79,0.71)");
                break;
            case 15:
                setStyle("-fx-font: 20 arial; -fx-background-color: rgba(9,101,112,0.6)");
                break;
        }
    }
}