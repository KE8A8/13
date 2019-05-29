package thirteen.view.highscore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HighscoreController implements Initializable {

    private String name;
    private int points;
    private List<HighscoreController> list;

    @FXML
    private Button ButtonBackToMenu;

    public HighscoreController(){ //String name, int points
        this.name = name;
        this.points = points;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleBackToMenuClick(ActionEvent event){

    }
    private void savePlayerName(String name){

    }
}
