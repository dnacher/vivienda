package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.TecnicoBean;
import ejb.utils.TecnicoImage;
import entities.enums.Mensajes;
import entities.persistence.entities.Tecnico;
import exceptions.ServiceException;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import web.animations.FadeInUpTransition;

public class TecnicoController implements Initializable {
    
    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private TableView<TecnicoImage> tableData;

    @FXML
    private Label LblNombre;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;
     
    @FXML
    private TextField txtTelefono;
   
    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtApellido;
    
    @FXML
    private Label lblApellido;

    @FXML
    private Label lblTelefono;

    public ObservableList<TecnicoImage> lista;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lista=FXCollections.observableArrayList(TecnicoImage.devuelveTecnicoConImagenEstado());
            cargaTabla();
            ChkActivo.setSelected(true);
            atras();
        } catch (ServiceException ex) {
            Logger.getLogger(TecnicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void nuevoTecnico(){
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
    }
    
    public void atras(){
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
    
    public void guardar(){
        if(validar()==0){ 
            ControlVentana cv=new ControlVentana();
            try {
                Tecnico tecnico=new Tecnico();
                tecnico.setNombre(txtNombre.getText());
                tecnico.setApellido(txtApellido.getText());
                tecnico.setTelefono(txtTelefono.getText());
                tecnico.setMail(txtMail.getText());
                tecnico.setIdTecnico(ConfiguracionControl.traeUltimoId("Tecnico"));
                tecnico.setEstado(1);
                tecnico.setActivo(ChkActivo.isSelected());
                Date date=new Date();
                tecnico.setFechaInicioEstado(date);
                TecnicoBean tb=new TecnicoBean();
                tb.guardar(tecnico);
                cv.creaVentanaNotificacionCorrecto();
                llenaTabla();
                limpiaForm();
            } catch (ServiceException ex) {
                cv.creaVentanaNotificacionError(ex.getMessage());
                Logger.getLogger(TecnicoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int validar(){
        int i=0;
        normalizarLabels();
        if(txtNombre.getText().isEmpty()){
            LblNombre.setTextFill(Color.RED);
            i=1;
        }
        if(txtApellido.getText().isEmpty()){
            lblApellido.setTextFill(Color.RED);
            i=2;
        }
        if(txtTelefono.getText().isEmpty()){
            lblTelefono.setTextFill(Color.RED);
            i=3;
        }
        return i;
    }
    
    public void normalizarLabels(){
        LblNombre.setTextFill(Color.BLACK);
        lblApellido.setTextFill(Color.BLACK);
        lblTelefono.setTextFill(Color.BLACK);
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
            tableData.setItems(lista);
        } catch (ServiceException ex) {
            Logger.getLogger(TecnicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void limpiaForm(){
        txtNombre.setText(Mensajes.VACIO.getMensaje());
        txtApellido.setText(Mensajes.VACIO.getMensaje());
        txtTelefono.setText(Mensajes.VACIO.getMensaje());
        txtMail.setText(Mensajes.VACIO.getMensaje());
    }
    
} 
