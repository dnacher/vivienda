package web.controller;

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

public class UnidadesController implements Initializable {
    
    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private ComboBox<String> cmbPropietarioInquilino;

    @FXML
    private TextField txtPuerta;

    @FXML
    private TableView<Unidad> tableData;

    @FXML
    private DatePicker cmbFechaIngreso;

    @FXML
    private Label LblNombre;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private ComboBox<String> cmbBlock;

    @FXML
    private ComboBox<Integer> cmbTorre;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtApellido;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }      
    
    public void nuevaUnidad(){
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
