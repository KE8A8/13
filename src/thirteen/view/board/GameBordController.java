package thirteen.view.board;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import thirteen.component.tile.LevelLabel;
import thirteen.component.tile.Tile;
import thirteen.store.Store;
import thirteen.store.StoreFactory;

import java.net.URL;
import java.util.*;

public class GameBordController implements Initializable {

    @FXML
    private BorderPane boarderpaneFXML;

    @FXML
    private Pane centerPane = new Pane();

    @FXML
    private Button bombButton = new Button();

    @FXML
    private Button undoButton = new Button();

    @FXML
    private StackPane statesPane = new StackPane();

    /**
     * Common Stuff
     */

    private Random rnd = new Random();
    private GridPane gridpane = new GridPane();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int size = 5;

        List<List<Integer>> gameboard = new ArrayList<List<Integer>>(size);

        new StoreFactory(size);

        Store simpleStore = StoreFactory.getStore();

        gridpane.setPrefHeight(420);
        gridpane.setPrefWidth(350);
        gridpane.setGridLinesVisible(true);

        for (int i = 0; i < size; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            row.setPercentHeight(20);
            gridpane.getRowConstraints().add(row);
        }
        for ( int j =0; j<size; j++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPercentWidth(20);
            gridpane.getColumnConstraints().add(col);

        }
        for (int i = 0; i<size; i++){

            gameboard.add(new ArrayList<Integer>(size));

            for (int j=0; j< size; j++){

                int value = rnd.nextInt(4)+1;

                gameboard.get(i).add(j, value);

                Tile tile = new Tile(i,j);

                tile.prefHeightProperty().bind(gridpane.heightProperty());
                tile.prefWidthProperty().bind(gridpane.widthProperty());

                gridpane.add(tile, j, i);

            }
        }

        simpleStore.updateGameboardAction(gameboard);

        Label levelLabel = new LevelLabel();
        statesPane.getChildren().add(levelLabel);

        this.centerPane.getChildren().add(gridpane);
    }

    @FXML
    public void bombAction(){
        Store store = StoreFactory.getStore();
        store.setBomb(true);
    }

    @FXML
    public void undoAction(){
        Store store = StoreFactory.getStore();
        store.undoGameBoardAction();
    }
}