package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ReglaBonificacionBean;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
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
        LblNombre.setText(ConstantesEtiquetas.VACIO);
        ControlVentana cv=new ControlVentana();
        if(!ConfiguracionControl.esNumero(TxtDiasaPagar.getText())){
            LblNombre.setText(ConstantesErrores.DIAS_A_PAGAR_NUMERICO);
        }
        else{
            try{
                Reglabonificacion reglaBonificacion=new Reglabonificacion();
                int ind=ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.REGLA_BONIFICACION);
                reglaBonificacion.setIdreglaBonificacion(ind);                
                reglaBonificacion.setDiaApagar(Integer.valueOf(TxtDiasaPagar.getText()));
                reglaBonificacion.setDescripcion(TxtDescripcion.getText());
                reglaBonificacion.setActivo(ChkActivo.isSelected());
                ReglaBonificacionBean rb=new ReglaBonificacionBean();
                rb.guardar(reglaBonificacion);                
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
    private void aksiBack(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
    
    public void cargaTabla(){
       TableColumn Descripcion = new TableColumn(ConstantesEtiquetas.DESCRIPCION_UPPER);
       TableColumn DiaAPagar = new TableColumn(ConstantesEtiquetas.DIA_A_PAGAR);
       TableColumn Activo = new TableColumn(ConstantesEtiquetas.ACTIVO_UPPER);

       Descripcion.setMinWidth(150);
       Descripcion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DESCRIPCION));

       DiaAPagar.setMinWidth(150);
       DiaAPagar.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DIAPAGAR));

       Activo.setMinWidth(100);
       Activo.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.ACTIVO));

       tableData.getColumns().addAll(Descripcion,DiaAPagar,Activo);
       tableData.setItems(lista);
    }
    
    public void llenaTabla(){
        try {
            ReglaBonificacionBean rbb=new ReglaBonificacionBean();
            lista=FXCollections.observableArrayList(rbb.traerTodos());        
            tableData.setItems(lista);
        } catch (ServiceException ex) {
            Logger.getLogger(reglaBonificacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}