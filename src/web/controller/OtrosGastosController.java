package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ConceptoBean;
import ejb.services.MontoBean;
import ejb.services.OtrosGastosBean;
import ejb.services.UnidadBean;
import entities.enums.errores;
import entities.persistence.entities.Concepto;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Otrosgastos;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import ejb.validaciones.OtrosGastosBuisinessValidation;
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
            cargarComboUnidad();
            cargarComboTipoMoneda();
            cargarComboConcepto();
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
    
    public void cargarComboUnidad(){
        try{
            UnidadBean ub=new UnidadBean();
            List<Unidad>lista=ub.traerTodos();
            ObservableList<Unidad> listaUnidad;
            listaUnidad=FXCollections.observableArrayList(lista);
            cmbUnidad.setItems(listaUnidad);
        }
        catch(ServiceException se){            
        }
    }
    
    public void cargarComboTipoMoneda(){
        try{
            MontoBean mb=new MontoBean();
            List<Monto>lista=mb.traerTodos();
            ObservableList<Monto> listaMonto;
            listaMonto=FXCollections.observableArrayList(lista);
            cmbTipoMoneda.setItems(listaMonto);
        }
        catch(ServiceException se){            
        }
    }
    
    public void cargarComboConcepto(){
        try{
            ConceptoBean cb=new ConceptoBean();
            List<Concepto>lista=cb.traerTodos();
            ObservableList<Concepto> listaConcepto;
            listaConcepto=FXCollections.observableArrayList(lista);
            cmbConcepto.setItems(listaConcepto);
        }
        catch(ServiceException se){            
        }
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
     
    public void guardar(){
        ControlVentana cv=new ControlVentana();
        switch(OtrosGastosBuisinessValidation.validar(txtSecuencia, cmbTipoMoneda, txtMonto, cmbFecha, cmbConcepto, cmbUnidad)){
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
                cv.creaVentanaNotificacionError(errores.FALTA_SECUENCIA.getError());
                break;
            case 2:
                cv.creaVentanaNotificacionError(errores.FALTA_TIPO_MONEDA.getError());
                break;
            case 3:
                cv.creaVentanaNotificacionError(errores.FALTA_MONTO.getError());
                break;
            case 4:
                cv.creaVentanaNotificacionError(errores.FALTA_FECHA.getError());
                break;
            case 5:
                cv.creaVentanaNotificacionError(errores.FALTA_CONCEPTO.getError());
                break;
            case 6:
                cv.creaVentanaNotificacionError(errores.FALTA_UNIDAD.getError());
                break;             
         }
     }
     
}
