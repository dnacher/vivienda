/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controller;

import web.animations.FadeInLeftTransition;
import web.animations.FadeInRightTransition;
import web.animations.FadeInTransition;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Herudi
 */
public class controllSplash implements Initializable {
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
     /*   Service<ApplicationContext> service = new Service<ApplicationContext>() {
            @Override
            protected Task<ApplicationContext> createTask() {
                return new Task<ApplicationContext>() {           
                    @Override
                    protected ApplicationContext call() throws Exception {
                        ApplicationContext appContex = config.getInstance().getApplicationContext();
                        int max = appContex.getBeanDefinitionCount();
                        updateProgress(0, max);
                        for (int k = 0; k < max; k++) {
                            Thread.sleep(50);
                            updateProgress(k+1, max);
                        }
                        return appContex;
                    }
                };
            }*/
        };
      /*  service.start();
        service.setOnRunning((WorkerStateEvent event) -> {
            new FadeInLeftTransition(lblWelcome).play();
            new FadeInRightTransition(lblRudy).play();
            new FadeInTransition(vboxBottom).play();
        });
        service.setOnSucceeded((WorkerStateEvent event) -> {
            config2 config = new config2();
            config.newStage(stage, lblClose, "/herudi/view/login.fxml", "Sample Apps", true, StageStyle.UNDECORATED, false);
        });
    } */
}
