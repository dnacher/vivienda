package web.controller;

import entities.persistence.entities.Tecnico;
import entities.persistence.entities.Unidad;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class BajaTecnicoController implements Initializable {

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

    @FXML
    private DatePicker cmbFechaBaja;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }      
    
    public void bajaTecnico(){
        
    }
    
    public void darBaja(){
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
