package thirteen.view.menu;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MenuController {

    @FXML
    private Button Start;

    @FXML
    Pane gridPane;

    @FXML
    public void handleClickStart(ActionEvent event){

        try{

            FXMLLoader gameboardLoader = new FXMLLoader(getClass().getResource("../board/boardLayout.fxml"));

            BorderPane root = gameboardLoader.load();

            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);

        } catch (Exception e){

            e.printStackTrace();

        }
    }
}
