package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.TipoDuracionBean;
import entities.persistence.entities.Tipoduracion;
import exceptions.ServiceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entities.constantes.ConstantesEtiquetas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;


public class tipoDuracionController implements Initializable {
  
    @FXML
    private Button btnBack;

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private TableView<Tipoduracion> tableData;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;

    @FXML
    private TextArea TxtDescripcion;
    
    @FXML
    private CheckBox ChkActivo;
    
    @FXML
    private Label LblNombre;
    
    ObservableList<Tipoduracion> listaTipoUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aksiNew(null);
        cargaTabla();
    }   
    
    private void clear(){
        txtNombre.clear();
        TxtDescripcion.clear();
        
    }         
    
    @FXML
    private void aksiNew(ActionEvent event) {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();          
        });
    }
    
    @FXML
    private void aksiSave(ActionEvent event){
        LblNombre.setText(ConstantesEtiquetas.VACIO);
        ControlVentana cv=new ControlVentana();
        if(txtNombre.getText().isEmpty()){
            LblNombre.setText("El campo nombre no puede estar vacio");
        }
        else{
            try{
                Tipoduracion tipoDuracion=new Tipoduracion();
                int ind=ConfiguracionControl.traeUltimoId("TipoDuracion");
                tipoDuracion.setIdtipoDuracion(ind);
                tipoDuracion.setActivo(ChkActivo.isSelected());
                tipoDuracion.setNombre(txtNombre.getText());
                tipoDuracion.setDescripcion(TxtDescripcion.getText());
                TipoDuracionBean tb=new TipoDuracionBean();
                tb.guardar(tipoDuracion);                
                cv.creaVentanaNotificacionCorrecto();
                clear();
                llenaTabla();
            }
            catch(Exception ex){
                cv.creaVentanaNotificacionError(ex.getMessage());
            }       
        }        
    }
    
    public void llenaTabla(){
        try {
            TipoDuracionBean tdb=new TipoDuracionBean();
            List<Tipoduracion> lista;
            lista=tdb.traerTodos();
            listaTipoUsuario = FXCollections.observableList(lista);
            tableData.setItems(listaTipoUsuario);
        } catch (ServiceException ex) {
            Logger.getLogger(tipoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargaTabla(){
        try {
            TipoDuracionBean tdb=new TipoDuracionBean();
            List<Tipoduracion> lista;
            lista=tdb.traerTodos();
            listaTipoUsuario = FXCollections.observableList(lista);
            TableColumn id = new TableColumn("id");
            TableColumn Nombre = new TableColumn("Nombre");
            TableColumn Descripcion = new TableColumn("Descripcion");
            
            id.setMinWidth(100);
            id.setCellValueFactory(new PropertyValueFactory<>("idtipoDuracion"));
   
            Nombre.setMinWidth(100);
            Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            
            Descripcion.setMinWidth(100);
            Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            
            tableData.getColumns().addAll(id,Nombre,Descripcion);
            tableData.setItems(listaTipoUsuario);
        } catch (ServiceException ex) {
            Logger.getLogger(urgenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void aksiBack(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }  
    
    }
