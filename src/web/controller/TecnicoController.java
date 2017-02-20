package web.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class TecnicoController implements Initializable {
    
    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private TableView<?> tableData;

    @FXML
    private Label LblNombre;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;
     
    @FXML
    private TextField txtTelefono;
   
    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtApellido;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }      
    
    public void nuevoTecnico(){
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();        
    }
    
    
} 
