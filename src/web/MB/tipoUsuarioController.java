package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.TipoUsuarioBean;
import entities.persistence.entities.Tipousuario;
import exceptions.ServiceException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;


public class tipoUsuarioController implements Initializable {

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private TextField txtNombre;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private TextArea TxtDescripcion;

    @FXML
    private TableView<Tipousuario> tableData;

    List<Tipousuario> lista;
    ObservableList<Tipousuario> listaTipoUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        cargaTabla();
        nuevoTipoUsuario();
    }   
    
    private void clear(){
        txtNombre.clear();
        TxtDescripcion.clear();
        
    }    
    
    @FXML
    private void nuevoTipoUsuario() {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();           
        });
    }
   
    @FXML
    private void guardarTipoUsuario(){       
        ControlVentana cv=new ControlVentana();
        if(txtNombre.getText().isEmpty()){
           
        }
        else{
            try{
                Tipousuario tipoUsuario=new Tipousuario();
                int ind=ConfiguracionControl.traeUltimoId("TipoUsuario");
                tipoUsuario.setId(ind);
                tipoUsuario.setActivo(ChkActivo.isSelected());
                tipoUsuario.setNombre(txtNombre.getText());
                tipoUsuario.setDescripcion(TxtDescripcion.getText());
                TipoUsuarioBean tb=new TipoUsuarioBean();
                tb.guardar(tipoUsuario);                
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
            TipoUsuarioBean tub=new TipoUsuarioBean();
            lista=tub.traerTodos();
            listaTipoUsuario = FXCollections.observableList(lista);
            tableData.setItems(listaTipoUsuario);
        } catch (ServiceException ex) {
            Logger.getLogger(tipoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargaTabla(){
        try {
            TipoUsuarioBean tub=new TipoUsuarioBean();
            lista=tub.traerTodos();
            listaTipoUsuario = FXCollections.observableList(lista);
            TableColumn id = new TableColumn("Id");
            TableColumn Nombre = new TableColumn("Nombre");
            TableColumn Descripcion = new TableColumn("Descripcion");
            
            id.setMinWidth(100);
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
   
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
    private void atras() {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
     
    }
