/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.MB;

import web.animations.FadeInLeftTransition;
import web.animations.FadeInRightTransition;
import web.animations.FadeInTransition;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SplashMB implements Initializable {
    @FXML
    private Text lblWelcome;
    @FXML
    private Text lblRudy;
    @FXML
    private VBox vboxBottom;
    @FXML
    private Label lblClose;
    Stage stage;
    @FXML
    private ImageView imgLoading;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        longStart();
        lblClose.setOnMouseClicked((MouseEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
        // TODO
    }   
    
    private void longStart() {
        
            new FadeInLeftTransition(lblWelcome).play();
            new FadeInRightTransition(lblRudy).play();
            new FadeInTransition(vboxBottom).play();
        };
    
}
