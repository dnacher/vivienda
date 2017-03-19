package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.EstadoBean;
import entities.persistence.entities.Estado;
import exceptions.ServiceException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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


public class estadoController implements Initializable {
    
    @FXML
    private TextField TxtOrden;

    @FXML
    private Label LblOrden;

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private TableView<Estado> tableData;

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
    
    public ObservableList<Estado> lista;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            EstadoBean eb=new EstadoBean();
            lista=FXCollections.observableArrayList(eb.traerTodos());
            cargaTabla();
            task();
            atras(null);
        } catch (ServiceException ex) {
            Logger.getLogger(estadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }   
    
    private void clear(){
        txtNombre.clear();
        TxtDescripcion.clear();
        
    }    

    @FXML
    private void nuevoEstado(ActionEvent event) {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();           
        });
    }
    
    @FXML
    private void guardar(ActionEvent event){
        LblNombre.setText("");
        ControlVentana cv=new ControlVentana();
            if(txtNombre.getText().isEmpty()){
                LblNombre.setText("El campo nombre no puede estar vacio");
            }
            else if(!ConfiguracionControl.esNumero(TxtOrden.getText())){
                LblOrden.setText("El campo Orden debe ser numerico");
            }
            else{
                try{
                    Estado estado=new Estado();
                    int ind=ConfiguracionControl.traeUltimoId("Estado");
                    estado.setIdestado(ind);
                    estado.setActivo(ChkActivo.isSelected());
                    estado.setNombre(txtNombre.getText());
                    estado.setDescripcion(TxtDescripcion.getText());
                    estado.setOrden(Integer.valueOf(TxtOrden.getText()));
                    EstadoBean eb=new EstadoBean();
                    eb.guardar(estado);                
                    cv.creaVentanaNotificacionCorrecto();
                    clear();
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
    
        public void task(){
            Task longTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    int max = 50;
                    for (int i = 1; i <= max; i++) {
                        if (isCancelled()) {
                            break;
                        }
                        updateProgress(i, max);                    
                        Thread.sleep(20);
                    }
                    return null;
                }
            };        
        }
        
        
      public void cargaTabla(){
       TableColumn Nombre = new TableColumn("Nombre");
       TableColumn Descripcion = new TableColumn("Descripcion");
       TableColumn Orden = new TableColumn("Orden");
       TableColumn Activo = new TableColumn("Activo");
       
       Nombre.setMinWidth(150);
       Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

       Descripcion.setMinWidth(150);
       Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

       Orden.setMinWidth(100);
       Orden.setCellValueFactory(new PropertyValueFactory<>("orden"));

       Activo.setMinWidth(100);
       Activo.setCellValueFactory(new PropertyValueFactory<>("activo"));
      
       tableData.getColumns().addAll(Nombre, Descripcion, Orden,Activo);
       tableData.setItems(lista);
    
    }
    
    public void llenaTabla(){
        //lblInfo.setText("Se muestran " + lista.size() + " registros.");
        tableData.setItems(lista);       
    }  
        
        
}
