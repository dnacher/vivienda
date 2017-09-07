package web.MB;

import entities.constantes.Constantes;
import entities.persistence.entities.Tipoduracion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;



public class ReportesController implements Initializable {
  
    @FXML
    private TextField txtPuerta;

    @FXML
    private ComboBox<String> cmbReporte;

    @FXML
    private ComboBox<String> cmbBlock;
    
    @FXML
    private ComboBox<Integer> cmbTorre;

    @FXML
    private DatePicker cmbFechaDesde;
    
    @FXML
    private DatePicker cmbFechaHasta;
    
    ObservableList<Tipoduracion> listaTipoUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaReportes();
        cargaComboBlock();
        cargaComboTorre();
    }     
    
    public void cargaReportes(){
        ObservableList<String> lista=FXCollections.observableArrayList(Constantes.LISTA_REPORTES);
        cmbReporte.setItems(lista);
    }
    
    public void cargaComboBlock(){
        ObservableList<String> lista=FXCollections.observableArrayList(Constantes.LISTA_BLOCKS);
        cmbBlock.setItems(lista);
    }
    
    public void cargaComboTorre(){         
        ObservableList<Integer> listaTorres=FXCollections.observableArrayList(Constantes.LISTA_TORRES);
        cmbTorre.setItems(listaTorres);
    }
    
    public void manageForm(){}
    
    public void cerrar(){}
    
    public void aceptar(){}
    
    }
