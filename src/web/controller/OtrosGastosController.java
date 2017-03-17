package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.OtrosGastosBean;
import ejb.services.UnidadBean;
import entities.persistence.entities.Concepto;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Otrosgastos;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class OtrosGastosController implements Initializable {
    
    @FXML
    private TextField txtMonto;

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private DatePicker cmbFecha;

    @FXML
    private TableView<Otrosgastos> tableData;

    @FXML
    private ComboBox<Unidad> cmbUnidad;

    @FXML
    private TextField txtSecuencia;

    @FXML
    private ComboBox<Monto> cmbTipoMoneda;

    @FXML
    private ProgressBar bar;

    @FXML
    private ComboBox<Concepto> cmbConcepto;
    
    ObservableList<Otrosgastos> otrosGastos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        try {
            OtrosGastosBean og=new OtrosGastosBean();
            otrosGastos=FXCollections.observableArrayList(og.traerTodos());
            cargaTabla();
            cargarComboTorre();
            cargaHoy();
            mostrarTabla();            
        } catch (ServiceException ex) {
            Logger.getLogger(OtrosGastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
    
    public void cargaHoy(){
        Date date= new Date();
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cmbFecha.setValue(ld);
        ChkActivo.setSelected(true);
    }
    
    public void cargaTabla(){
       TableColumn Secuencia = new TableColumn("Secuencia");
       TableColumn Descripcion = new TableColumn("Descripcion");
       TableColumn Monto = new TableColumn("Monto");
       TableColumn Unidad = new TableColumn("Unidad");
       TableColumn Fecha= new TableColumn("Fecha");

       Secuencia.setMinWidth(150);
       Secuencia.setCellValueFactory(new PropertyValueFactory<>("Secuencia"));

       Descripcion.setMinWidth(150);
       Descripcion.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));

       Monto.setMinWidth(100);
       Monto.setCellValueFactory(new PropertyValueFactory<>("Monto"));

       Unidad.setMinWidth(100);
       Unidad.setCellValueFactory(new PropertyValueFactory<>("Unidad"));

       Fecha.setMinWidth(110);
       Fecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));

       tableData.getColumns().addAll(Secuencia,Descripcion,Monto,Unidad,Fecha);
       tableData.setItems(otrosGastos);
      
    }
    
    public void cargarComboTorre(){
        ObservableList<Integer> listaTorres;
        listaTorres=FXCollections.observableArrayList(1,2,3,4,5,6);
        cmbTorre.setItems(listaTorres);
    }
    
    public void nuevaUnidad(){
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
        });
    }
    
    public void mostrarTabla(){
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
     
     public int validar(){
        int i=0;
        if(cmbBlock.getValue()==null){
            i=1;
        } 
        else{
            if(cmbTorre.getValue()==null){
                i=2;
            } 
            else{
                if(txtPuerta.getText().isEmpty()){
                    i=3;
                }
                else{
                    if(txtNombre.getText().isEmpty()){
                        i=4;
                    }
                    else{
                        if(txtApellido.getText().isEmpty()){
                            i=5;
                        }
                        else{
                            if(txtTelefono.getText().isEmpty()){
                                i=6;
                            }
                            else{
                                if(txtMail.getText().isEmpty()){
                                    i=7;
                                }
                                else{
                                    if(cmbPropietarioInquilino.getValue()==null){
                                        i=8;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return i;
     }
     
     public void guardar(){
         ControlVentana cv=new ControlVentana();
         switch(validar()){
             case 0:
                 try{                    
                    Otrosgastos og=new Otrosgastos();
                    og.setActivo(ChkActivo.isSelected());
                    og.setConcepto(cmbConcepto.getSelectionModel().getSelectedItem());
                    og.setDescripcion("Falta descripcionnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
                    og.setFecha(ConfiguracionControl.TraeFecha(cmbFecha.getValue()));
                    og.setMonto(cmbTipoMoneda.getSelectionModel().getSelectedItem());
                    og.setMonto_1(Integer.valueOf(txtMonto.getText()));
                    og.setPago(true);
                    og.setSecuencia(Integer.valueOf(txtSecuencia.getText()));
                    og.setUnidad(cmbUnidad.getSelectionModel().getSelectedItem());
                    OtrosGastosBean oga=new OtrosGastosBean();
                    oga.guardar(og);
                    cv.creaVentanaNotificacionCorrecto();
                 }
                 catch(Exception ex){
                     cv.creaVentanaNotificacionError(ex.getMessage());
                 }
                 break;
             case 1:
                 cv.creaVentanaNotificacionError("falta ingresar el block");
                 break;
             case 2:
                 cv.creaVentanaNotificacionError("falta ingresar la torre");
                 break;
             case 3:
                 cv.creaVentanaNotificacionError("falta ingresar la puerta");
                 break;
             case 4:
                 cv.creaVentanaNotificacionError("falta ingresar el nombre");
                 break;
             case 5:
                 cv.creaVentanaNotificacionError("falta ingresar el apellido");
                 break;
             case 6:
                 cv.creaVentanaNotificacionError("falta ingresar el telefono");
                 break;
             case 7:
                 cv.creaVentanaNotificacionError("falta ingresar el mail");
                 break;
             case 8:
                 cv.creaVentanaNotificacionError("falta ingresar si es propietario o no");
                 break;
         }
     }
     
}
