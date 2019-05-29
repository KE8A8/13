package thirteen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    public class ThirteenGameMain extends Application {


        //evtl override in Menu wo board gerufen wird
        @Override
        public void start(Stage primaryStage) throws Exception {

            try{

                FXMLLoader loader = new FXMLLoader(getClass().getResource("view/menu/MenuView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 350, 500);
                scene.getStylesheets().add(getClass().getResource("./resources/styles/game.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
