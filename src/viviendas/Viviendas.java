package viviendas;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Daniel
 */
public class Viviendas extends Application {   
    
    public static String user;
    
    @Override
    public void start(Stage stage) {        
              
        try {    
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/vista/splash.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(3),
        new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.hide();
                Parent root;
                try {                 
                    root = FXMLLoader.load(getClass().getResource("/vista/login.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception ex) {
                    Logger.getLogger(Viviendas.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }));
        timeline.play();
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        } 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
