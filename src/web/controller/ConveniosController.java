package web.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class ConveniosController implements Initializable {
    
    @FXML
    private TextField txtMonto;

    @FXML
    private ComboBox<?> cmbMoneda;
   

    @FXML
    private TableView<?> tableGastosComunes;

    @FXML
    private DatePicker cmbFecha;

    @FXML
    private AnchorPane paneFormulario;

    @FXML
    private PieChart chartGastosComunes;

    @FXML
    private ProgressBar bar;     

    @FXML
    private ComboBox<?> cmbTorre;

    @FXML
    private AnchorPane paneGastosComunes;

    @FXML
    private CheckBox chkBonificacion;

    @FXML
    private ComboBox<?> cmbBlock;
    
    @FXML
    private Button btnMostrar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atras();
        btnMostrar.setDisable(true);   
        cargaHoy();
    }     
    
    public void cargaHoy(){
        Date date= new Date();
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cmbFecha.setValue(ld);
    }
    
    public void agregarGastosComunes(){
        paneGastosComunes.setOpacity(0);
        new FadeInUpTransition(paneFormulario).play();
        Platform.runLater(() -> {
            clear();
            auto();
        });
    }
    
    public void atras(){
        paneFormulario.setOpacity(0);
        new FadeInUpTransition(paneGastosComunes).play();
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
