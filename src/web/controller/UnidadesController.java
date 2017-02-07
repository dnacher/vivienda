package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.UnidadBean;
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
    
    ObservableList<Unidad> unidades;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        try {
            UnidadBean ub= new UnidadBean();
            unidades=FXCollections.observableArrayList(ub.traerTodos());
            cargaTabla();
            cargarComboBlock();
            cargarComboTorre();
            cargarPropietarioInquilino();
            cargaHoy();
            mostrarTabla();            
        } catch (ServiceException ex) {
            Logger.getLogger(UnidadesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
    
    public void cargaHoy(){
        Date date= new Date();
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cmbFechaIngreso.setValue(ld);
        ChkActivo.setSelected(true);
    }
    
    public void cargaTabla(){
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

       tableData.getColumns().addAll(Nombre, Apellido, Block,Torre,Puerta);
       tableData.setItems(unidades);
      
    }
    
    public void cargarComboBlock(){
       ObservableList<String> options = 
       FXCollections.observableArrayList("A","B","C","D","E");
       cmbBlock.setItems(options);
    }
    
    public void cargarComboTorre(){
        ObservableList<Integer> listaTorres;
        listaTorres=FXCollections.observableArrayList(1,2,3,4,5,6);
        cmbTorre.setItems(listaTorres);
    }
    
    public void cargarPropietarioInquilino(){
       ObservableList<String> options = 
       FXCollections.observableArrayList("Propietario","Inquilino");
       cmbPropietarioInquilino.setItems(options);
       cmbPropietarioInquilino.getSelectionModel().selectLast();
    }
    
    public void nuevaUnidad(){
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();
            auto();
        });
    }
    
    public void mostrarTabla(){
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
    
     private void clear(){
        
    }
     
     private void auto(){
      
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
                    Unidad unidad=new Unidad();
                    unidad.setIdUnidad(ConfiguracionControl.traeUltimoId("Unidad"));
                    unidad.setBlock(cmbBlock.getValue());
                    unidad.setTorre(cmbTorre.getValue());
                    unidad.setPuerta(Integer.valueOf(txtPuerta.getText()));
                    unidad.setNombre(txtNombre.getText());
                    unidad.setApellido(txtApellido.getText());
                    unidad.setMail(txtMail.getText());
                    unidad.setTelefono(Integer.valueOf(txtTelefono.getText()));
                    unidad.setFechaIngreso(ConfiguracionControl.TraeFecha(cmbFechaIngreso.getValue()));
                    if(cmbPropietarioInquilino.getValue().equals("Propietario")){
                        unidad.setPropietarioInquilino(true);
                    }
                    else{
                        unidad.setPropietarioInquilino(false);
                    }
                    UnidadBean ub=new UnidadBean();
                    ub.guardar(unidad);
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
