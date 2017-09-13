package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.GrupoBean;
import entities.constantes.ConstantesEtiquetas;
import entities.persistence.entities.Grupo;
import exceptions.ServiceException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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


public class grupoController implements Initializable {

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private TableView<Grupo> tableData;

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
    
    public ObservableList<Grupo> lista;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            GrupoBean gb=new GrupoBean();
            lista=FXCollections.observableArrayList(gb.traerTodos());
            cargaTabla();
            atras(null);
        } catch (ServiceException ex) {
            Logger.getLogger(grupoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    private void clear(){
        txtNombre.clear();
        TxtDescripcion.clear();
        
    }

    @FXML
    private void nuevoGrupo(ActionEvent event) {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();           
        });
    }
    
    @FXML
    private void guardar(ActionEvent event){
        LblNombre.setText(ConstantesEtiquetas.VACIO);
        ControlVentana cv=new ControlVentana();
        if(txtNombre.getText().isEmpty()){
            LblNombre.setText("El campo nombre no puede estar vacio");
        }
        else{
            try{
                Grupo grupo=new Grupo();
                int ind=ConfiguracionControl.traeUltimoId("Grupo");
                grupo.setIdgrupo(ind);
                grupo.setActivo(ChkActivo.isSelected());
                grupo.setNombre(txtNombre.getText());
                grupo.setDescripcion(TxtDescripcion.getText());
                GrupoBean gb=new GrupoBean();
                gb.guardar(grupo);                
                cv.creaVentanaNotificacionCorrecto();
                clear();
                llenaTabla();
            }
            catch(Exception ex){
                cv.creaVentanaNotificacionError(ex.getMessage());
            }       
        }       
    }

    @FXML
    private void atras(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
    
    public void cargaTabla(){
       TableColumn Nombre = new TableColumn("Nombre");
       TableColumn Descripcion = new TableColumn("Descripcion");
       TableColumn Activo = new TableColumn("Activo");       

       Nombre.setMinWidth(150);
       Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

       Descripcion.setMinWidth(150);
       Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

       Activo.setMinWidth(100);
       Activo.setCellValueFactory(new PropertyValueFactory<>("activo"));
     
       tableData.getColumns().addAll(Nombre, Descripcion, Activo);
       tableData.setItems(lista);
      
    }
    
    public void llenaTabla(){
        try {
            GrupoBean gb=new GrupoBean();
            lista=FXCollections.observableArrayList(gb.traerTodos());       
            tableData.setItems(lista);
        } catch (ServiceException ex) {
            Logger.getLogger(grupoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    }
