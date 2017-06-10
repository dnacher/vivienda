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
import ejb.validaciones.OtrosGastosViewValidation;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.enums.Mensajes;
import entities.persistence.entities.OtrosgastosId;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import web.animations.FadeInUpTransition;

public class OtrosGastosController implements Initializable {
    
    @FXML
    private TextField txtMonto;

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;
    
    @FXML
    private AnchorPane paneUnidades;

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private DatePicker cmbFecha;

    @FXML
    private TableView<Otrosgastos> tableData;

    @FXML
    private TextField txtSecuencia;

    @FXML
    private ComboBox<Monto> cmbTipoMoneda;

    @FXML
    private ProgressBar bar;

    @FXML
    private ComboBox<Concepto> cmbConcepto;
    
    @FXML
    private ComboBox<Integer> cmbTorre;

    @FXML
    private ComboBox<String> cmbBlock;
    
    @FXML
    private TableView<Unidad> tableUnidades;
    
    @FXML
    private Label lblInfo;
    
    @FXML
    private Label lblUnidad;
    
    @FXML
    private TextArea txtDescripcion;
    
    ObservableList<Unidad> listaUnidad;
    Unidad unidad;
    ObservableList<Otrosgastos> otrosGastos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        try {
            UnidadBean ub=new UnidadBean();
            listaUnidad=FXCollections.observableArrayList(ub.traerTodos());
            cargaTablaUnidades();
            OtrosGastosBean og=new OtrosGastosBean();
            otrosGastos=FXCollections.observableArrayList(og.traerTodos());
            cargaTabla();        
            cargarComboTipoMoneda();
            cmbTipoMoneda.getSelectionModel().selectFirst();
            cargarComboConcepto();
            cargaHoy();
            mostrarTabla();
        } catch (ServiceException ex) {
            Logger.getLogger(OtrosGastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
    
    public void UnidadSeleccionada(){
        try{
            lblInfo.setText(Mensajes.VACIO.getMensaje());
            unidad=tableUnidades.getSelectionModel().getSelectedItem();
            nuevaUnidad();
            lblUnidad.setText(unidad.toString());
        }
        catch(Exception ex){
            lblInfo.setText(ConstantesErrores.DEBE_SELECCIONAR_UNIDAD);
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
       TableColumn Monto = new TableColumn("Moneda");
       TableColumn Monto1 = new TableColumn("Monto");
       TableColumn Unidad = new TableColumn("Unidad");
       TableColumn Fecha= new TableColumn("Fecha");

       Secuencia.setMinWidth(150);
       Secuencia.setCellValueFactory(new PropertyValueFactory<>("Secuencia"));

       Descripcion.setMinWidth(150);
       Descripcion.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));

       Monto.setMinWidth(100);
       Monto.setCellValueFactory(new PropertyValueFactory<>("Monto"));

       Monto1.setMinWidth(100);
       Monto1.setCellValueFactory(new PropertyValueFactory<>("Monto_1"));

       
       Unidad.setMinWidth(100);
       Unidad.setCellValueFactory(new PropertyValueFactory<>("Unidad"));

       Fecha.setMinWidth(110);
       Fecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));

       tableData.getColumns().addAll(Secuencia,Unidad,Monto,Monto1,Descripcion,Fecha);
       tableData.setItems(otrosGastos);
      
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
        paneUnidades.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
        });
    }
    
    public void mostrarUnidad(){
        paneTabel.setOpacity(0);
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneUnidades).play();
    }
    
    public void mostrarTabla(){
        paneCrud.setOpacity(0);
        paneUnidades.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
     
    public void guardar(){
        ControlVentana cv=new ControlVentana();
        switch(OtrosGastosViewValidation.validar(txtSecuencia, cmbTipoMoneda, txtMonto, cmbFecha, cmbConcepto,unidad)){
            case 0:
                try{                    
                    Otrosgastos og=new Otrosgastos();
                    OtrosgastosId ogI=new OtrosgastosId();
                    ogI.setIdotrosGastos(ConfiguracionControl.traeUltimoId("OtrosGastos"));
                    ogI.setUnidadIdUnidad(unidad.getIdUnidad());
                    og.setId(ogI);
                    og.setActivo(ChkActivo.isSelected());
                    og.setConcepto(cmbConcepto.getSelectionModel().getSelectedItem());
                    og.setDescripcion(txtDescripcion.getText());
                    og.setFecha(ConfiguracionControl.TraeFecha(cmbFecha.getValue()));
                    og.setMonto(cmbTipoMoneda.getSelectionModel().getSelectedItem());
                    og.setMonto_1(Integer.valueOf(txtMonto.getText()));
                    og.setPago(true);
                    og.setSecuencia(Integer.valueOf(txtSecuencia.getText()));
                    og.setUnidad(unidad);
                    OtrosGastosBean oga=new OtrosGastosBean();
                    oga.guardar(og);
                    cv.creaVentanaNotificacionCorrecto();
                    limpiarForm();
                    llenaTablaGastos();
                    mostrarTabla();
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
    
    public void limpiarForm(){
        unidad=null;
        txtSecuencia.setText(Mensajes.VACIO.getMensaje());
        txtMonto.setText(Mensajes.VACIO.getMensaje());
        tableUnidades.getSelectionModel().clearSelection();
    }
    
    public void cargarComboBlock(){
       ObservableList<String> options = 
       FXCollections.observableArrayList(Constantes.LISTA_BLOCKS);
       cmbBlock.setItems(options);
    }
    
    public void cargarComboTorre(){
        ObservableList<Integer> listaTorres;
        listaTorres=FXCollections.observableArrayList(1,2,3,4,5,6);
        cmbTorre.setItems(listaTorres);
    }
    
    public void cargaTablaUnidades(){
       TableColumn Nombre = new TableColumn("Nombre");
       TableColumn Apellido = new TableColumn("Apellido");
       TableColumn Block = new TableColumn("Block");
       TableColumn Torre = new TableColumn("Torre");
       TableColumn Puerta= new TableColumn("Puerta");

       Nombre.setMinWidth(150);
       Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

       Apellido.setMinWidth(150);
       Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

       Block.setMinWidth(100);
       Block.setCellValueFactory(new PropertyValueFactory<>("Block"));

       Torre.setMinWidth(100);
       Torre.setCellValueFactory(new PropertyValueFactory<>("Torre"));

       Puerta.setMinWidth(110);
       Puerta.setCellValueFactory(new PropertyValueFactory<>("Puerta"));

       tableUnidades.getColumns().addAll(Nombre, Apellido, Block,Torre,Puerta);
       tableUnidades.setItems(listaUnidad);
    }
    
    public void llenaTabla(){
        lblInfo.setText("Se muestran " + listaUnidad.size() + " registros.");
        tableUnidades.setItems(listaUnidad);
    }
    
    public void llenaTablaGastos(){
        try {
            OtrosGastosBean ogb=new OtrosGastosBean();
            otrosGastos=FXCollections.observableArrayList(ogb.traerTodos());
            tableData.setItems(otrosGastos);
        } catch (ServiceException ex) {
            Logger.getLogger(OtrosGastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrar(ActionEvent event) {       
            try{
            lblInfo.setText(Mensajes.VACIO.getMensaje());
            UnidadBean ub= new UnidadBean();
            List<Unidad> listaTorreBlock=ub.TraeUnidadesXBlockTorreNoPago(cmbBlock.getValue(), cmbTorre.getValue());        
            listaUnidad = FXCollections.observableList(listaTorreBlock);
            llenaTabla();            
            }
            catch(Exception ex){
                lblInfo.setText(ConstantesErrores.VALORES_BLOCK_TORRE);
            }
        }
       
        public void mostrarTodos() {
            UnidadBean ub=new UnidadBean();
            List<Unidad> listaTotal=ub.TraeUnidadesGastosComunesNoPago();            
            listaUnidad = FXCollections.observableList(listaTotal);
            llenaTabla();           
        }
}
