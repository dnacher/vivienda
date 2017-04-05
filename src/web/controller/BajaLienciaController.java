package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.TecnicoBean;
import ejb.utils.TecnicoImage;
import entities.persistence.entities.Tecnico;
import exceptions.ServiceException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class BajaLienciaController implements Initializable {

    @FXML
    private TableView<TecnicoImage> tableData;

    @FXML
    private ProgressBar bar;

    @FXML
    private Label lblApellido;

    @FXML
    private Label lblTelefono;
    
    @FXML
    private Label lblNombre;
  
    @FXML
    private ComboBox<String> cmbBajaLicencia;

    @FXML
    private DatePicker cmbFechaDesde;
    
    @FXML
    private DatePicker cmbFechaHasta;

    @FXML
    private Label lblHasta;

    @FXML
    private Label lblDesde;

    public ObservableList<TecnicoImage> lista;
    Tecnico tecnico;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            noVisible();
            lista=FXCollections.observableArrayList(TecnicoImage.devuelveTecnicoConImagenEstado());
            cargaTabla();            
            cargaComboBajaLicencia();         
        } catch (ServiceException ex) {
            Logger.getLogger(BajaLienciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void noVisible(){
        lblDesde.setVisible(false);
        lblHasta.setVisible(false);
        cmbBajaLicencia.setVisible(false);
        cmbFechaDesde.setVisible(false);
        cmbFechaHasta.setVisible(false);
    }
            
    public void guardar(){
        ControlVentana cv=new ControlVentana();
        //Licencia
        if(cmbBajaLicencia.getSelectionModel().getSelectedItem().equals("Licencia")){
            if(cmbFechaDesde.getValue().isAfter(cmbFechaHasta.getValue())){
                cv.creaVentanaNotificacion("Verificar", "La fecha fin no puede ser menor a la de comienzo", 5, "error");
            }
            else{
            if(cmbFechaDesde.getValue().isBefore(LocalDate.now()) || cmbFechaHasta.getValue().isBefore(LocalDate.now())){
                cv.creaVentanaNotificacion("Verificar", "La fecha de comienzo o fin son anteriores a la fecha.", 5, "warning");
            }
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Licencia");
                alert.setHeaderText("Licencia");
                alert.setContentText("¿Desea confirmar la licencia?");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/web/css/myDialogs.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");
                
              
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    try {
                        tecnico.setEstado(2);
                        tecnico.setFechaInicioEstado(ConfiguracionControl.TraeFecha(cmbFechaDesde.getValue()));
                        tecnico.setFechaFinEstado(ConfiguracionControl.TraeFecha(cmbFechaHasta.getValue()));
                        TecnicoBean tb=new TecnicoBean();
                        tb.modificar(tecnico);
                        cv.creaVentanaNotificacionCorrecto();
                    } catch (ServiceException ex) {
                        cv.creaVentanaNotificacionError(ex.getMessage());
                        Logger.getLogger(BajaLienciaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
         //Baja
        }else{           
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Baja");
                alert.setHeaderText("Baja");
                alert.setContentText("¿Desea confirmar la baja?");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/web/css/myDialogs.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");
                
              
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    try {
                        tecnico.setEstado(3);
                        tecnico.setFechaFinEstado(ConfiguracionControl.TraeFecha(cmbFechaHasta.getValue()));
                        tecnico.setActivo(false);
                        TecnicoBean tb=new TecnicoBean();
                        tb.modificar(tecnico);
                        cv.creaVentanaNotificacion("Baja", "Se ha dado de baja correctamente. " + cmbFechaHasta.getValue(), 3, "tick");
                    } catch (ServiceException ex) {
                        cv.creaVentanaNotificacionError(ex.getMessage());
                        Logger.getLogger(BajaLienciaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
        }
        llenaTabla();
    }
  
    
    public void creaDialogoConfirmacion() throws IOException{
                Stage stage=new Stage(StageStyle.UNDECORATED);
                Parent root = FXMLLoader.load(getClass().getResource("/web/vista/dialog.fxml"));
                Scene scene = new Scene(root);
                FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();
                stage.setTitle("Confirma");
                stage.setScene(scene);
                stage.showAndWait();
        }
   
    
    public void cargaTabla(){
       TableColumn Nombre = new TableColumn("Nombre");
       TableColumn Apellido = new TableColumn("Apellido");
       TableColumn Telefono = new TableColumn("Telefono");
       TableColumn Mail = new TableColumn("Email");
       TableColumn Calificacion = new TableColumn("Calificacion");
       TableColumn Estado = new TableColumn("Estado");
       TableColumn FechaInicio = new TableColumn("Fecha de inicio");
       TableColumn FechaFin = new TableColumn("Fecha fin");
       
       Nombre.setMinWidth(150);
       Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

       Apellido.setMinWidth(150);
       Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

       Telefono.setMinWidth(100);
       Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
       
       Mail.setMinWidth(100);
       Mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       
       Calificacion.setMinWidth(110);
       Calificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
              
       Estado.setMinWidth(100);
       Estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
      
       FechaInicio.setMinWidth(100);
       FechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicioEstado"));
       
       FechaFin.setMinWidth(100);
       FechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFinEstado"));
       
       tableData.getColumns().addAll(Nombre,Apellido,Telefono,Mail,Calificacion,Estado,FechaInicio,FechaFin);
       tableData.setItems(lista);     
       }
    
    public void llenaTabla(){
        try {
            lista=FXCollections.observableArrayList(TecnicoImage.devuelveTecnicoConImagenEstado());
            //lblInfo.setText("Se muestran " + Lista.size() + " registros.");
            tableData.setItems(lista);
        } catch (ServiceException ex) {
            Logger.getLogger(BajaLienciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void cargaComboBajaLicencia(){
        ObservableList<String> options = FXCollections.observableArrayList("Licencia","Baja");
        cmbBajaLicencia.setItems(options);        
        cmbBajaLicencia.valueProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue ov, String t, String t1) {
             managedCombos();
            }    
        });          
    }
    
    public void managedCombos(){
        lblDesde.setVisible(false);
        lblHasta.setVisible(false);        
        cmbFechaDesde.setVisible(false);
        cmbFechaHasta.setVisible(false);
        switch(cmbBajaLicencia.getSelectionModel().getSelectedItem()){
            case "Licencia":
                cmbFechaDesde.setVisible(true);
                cmbFechaHasta.setVisible(true);
                lblDesde.setVisible(true);
                lblHasta.setVisible(true);
                break;
            case "Baja":
                cmbFechaDesde.setVisible(false);
                cmbFechaHasta.setVisible(true);
                lblDesde.setVisible(false);
                lblHasta.setVisible(true);
                break;
        }
    }
    
    public void managedTabla() throws ServiceException{
        try{
            lblNombre.setText("");
            lblApellido.setText("");
            lblTelefono.setText("");
            TecnicoBean tb=new TecnicoBean();
            TecnicoImage ti=tableData.getSelectionModel().getSelectedItem();
            tecnico=tb.traerTecnicoXId(ti.getIdTecnico());
            lblNombre.setText(tecnico.getNombre());
            lblApellido.setText(tecnico.getApellido());
            lblTelefono.setText(tecnico.getTelefono());
            cmbBajaLicencia.setVisible(true);
        }
        catch(Exception ex){
            
        }    
    }
} 
