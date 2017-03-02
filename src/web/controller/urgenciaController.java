package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.UrgenciaBean;
import entities.persistence.entities.Urgencia;
import exceptions.ServiceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


public class urgenciaController implements Initializable {

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private TableView<Urgencia> tableData;

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

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
        UrgenciaBean ub=new UrgenciaBean();
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        cargaTabla();
        atras();
    }   
    
    public void clear(){
        txtNombre.clear();
        TxtDescripcion.clear();        
    }
      
    public void nuevaUrgencia() {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();        
    }
    
     public void atras(){
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();       
    }
    
    public void guardaUrgencia(){
        LblNombre.setText("");
        ControlVentana cv=new ControlVentana();
        if(txtNombre.getText().isEmpty()){
            LblNombre.setText("El campo nombre no puede estar vacio");
        }
        else{
            try{
                Urgencia urgencia=new Urgencia();
                int ind=ConfiguracionControl.traeUltimoId("Urgencia");
                urgencia.setIdurgencia(ind);
                urgencia.setActivo(ChkActivo.isSelected());
                urgencia.setNombre(txtNombre.getText());
                urgencia.setDescripcion(TxtDescripcion.getText());
                UrgenciaBean ub=new UrgenciaBean();
                ub.guardar(urgencia);
                cv.creaVentanaNotificacionCorrecto();
                clear();
            }
            catch(Exception ex){
                cv.creaVentanaNotificacionError(ex.getMessage());
            }       
        }    
      
    }
    
    public void cargaTabla(){
          try {
            List<Urgencia> lista=ub.traerTodos();
            ObservableList<Urgencia> listaUrgencia = FXCollections.observableList(lista);
            TableColumn id = new TableColumn("# Id");
            TableColumn Nombre = new TableColumn("# Nombre");
            TableColumn Descripcion = new TableColumn("# Descripcion");
            
            id.setMinWidth(100);
            id.setCellValueFactory(new PropertyValueFactory<>("idurgencia"));
   
            Nombre.setMinWidth(100);
            Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            Descripcion.setMinWidth(100);
            Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tableData.getColumns().addAll(id,Nombre,Descripcion);
            tableData.setItems(listaUrgencia);           
        } catch (ServiceException ex) {
            Logger.getLogger(urgenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void llenaTabla(){
    
    }
    
    }
