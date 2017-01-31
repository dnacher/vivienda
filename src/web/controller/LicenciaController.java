package web.controller;

import entities.persistence.entities.Tecnico;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class LicenciaController implements Initializable {
    
    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private ProgressBar bar;
    
    @FXML
    private ComboBox<Tecnico> cmbTecnico;

    @FXML
    private TableView<Tecnico> tableData;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }      
    
    public void cargarLicencia(){
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();
            auto();
        });
    }
    
     private void clear(){
        
    }
     
     private void auto(){
      
    }
} 
