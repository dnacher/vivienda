package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ReglaBonificacionBean;
import entities.persistence.entities.Reglabonificacion;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;


public class reglaBonificacionController implements Initializable {
  
    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private TableView<Reglabonificacion> tableData;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;

    @FXML
    private TextField TxtDescripcion;
    
    @FXML
    private CheckBox ChkActivo;
    
    @FXML
    private TextField TxtDiasaPagar;
    
    @FXML
    private Label LblNombre;
    
    public ObservableList<Reglabonificacion> lista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aksiBack(null);
        ReglaBonificacionBean rbb=new ReglaBonificacionBean();
        try {
            lista=FXCollections.observableArrayList(rbb.traerTodos());
            cargaTabla();
        } catch (ServiceException ex) {
            Logger.getLogger(reglaBonificacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    private void clear(){
        TxtDiasaPagar.clear();
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
        LblNombre.setText("");
        ControlVentana cv=new ControlVentana();
        if(!ConfiguracionControl.esNumero(TxtDiasaPagar.getText())){
            LblNombre.setText("El campo dias a pagar debe ser numerico");
        }
        else{
            try{
                Reglabonificacion reglaBonificacion=new Reglabonificacion();
                int ind=ConfiguracionControl.traeUltimoId("ReglaBonificacion");
                reglaBonificacion.setIdreglaBonificacion(ind);                
                reglaBonificacion.setDiaApagar(Integer.valueOf(TxtDiasaPagar.getText()));
                reglaBonificacion.setDescripcion(TxtDescripcion.getText());
                reglaBonificacion.setActivo(ChkActivo.isSelected());
                ReglaBonificacionBean rb=new ReglaBonificacionBean();
                rb.guardar(reglaBonificacion);                
                cv.creaVentanaNotificacionCorrecto();
                clear();
            }
            catch(Exception ex){
                cv.creaVentanaNotificacionError(ex.getMessage());
            }       
        }      
    }

    @FXML
    private void aksiBack(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
    
    public void cargaTabla(){
       TableColumn Descripcion = new TableColumn("Descripcion");
       TableColumn DiaAPagar = new TableColumn("Dia a Pagar");
       TableColumn Activo = new TableColumn("Activo");

       Descripcion.setMinWidth(150);
       Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

       DiaAPagar.setMinWidth(150);
       DiaAPagar.setCellValueFactory(new PropertyValueFactory<>("diaApagar"));

       Activo.setMinWidth(100);
       Activo.setCellValueFactory(new PropertyValueFactory<>("activo"));

       tableData.getColumns().addAll(Descripcion,DiaAPagar,Activo);
       tableData.setItems(lista);
    }
    
    public void llenaTabla(){
        //lblInfo.setText("Se muestran " + Lista.size() + " registros.");
        tableData.setItems(lista);        
    }
}
