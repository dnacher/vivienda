package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.MaterialBean;
import entities.persistence.entities.Material;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;


public class MaterialController implements Initializable {
        
    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private TableView<Material> tableData;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;

    @FXML
    private TextArea txtDescripcion;
    
    @FXML
    private TextField txtCantidad;
   /* 
    @FXML
    private CheckBox ChkActivo;
    */
    @FXML
    private Label LblNombre;
    
    public ObservableList<Material> lista;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            task();
            MaterialBean mb=new MaterialBean();
            lista=FXCollections.observableArrayList(mb.traerTodos());
            cargaTabla();
            task();
            atras(null);
        } catch (ServiceException ex) {
            Logger.getLogger(MaterialController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }   
    
    private void clear(){
        try{
            txtNombre.clear();
            txtCantidad.clear();
            txtDescripcion.clear();       
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }    

    @FXML
    private void nuevoMaterial(ActionEvent event) {
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
                try{
                    Material material=new Material();
                    int ind=ConfiguracionControl.traeUltimoId("Material");
                    material.setIdmaterial(ind);
                    material.setActivo(true);
                    material.setNombre(txtNombre.getText());
                    material.setDescripcion(txtDescripcion.getText());
                    material.setEntrada(Integer.valueOf(txtCantidad.getText()));
                    material.setSalida(0);
                    material.setCantidad(material.getEntrada());
                    MaterialBean mb=new MaterialBean();
                    mb.guardar(material);                
                    cv.creaVentanaNotificacionCorrecto();
                    clear();
                }
                catch(Exception ex){
                    cv.creaVentanaNotificacionError(ex.getMessage());
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
       TableColumn Cantidad = new TableColumn("Cantidad");
       TableColumn Entrada = new TableColumn("Entrada");
       TableColumn Salida = new TableColumn("Salida");
       TableColumn Activo = new TableColumn("Activo");
       
       Nombre.setMinWidth(150);
       Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

       Descripcion.setMinWidth(150);
       Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
      
       Entrada.setMinWidth(100);
       Entrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
       
       Salida.setMinWidth(100);
       Salida.setCellValueFactory(new PropertyValueFactory<>("salida"));
       
       Cantidad.setMinWidth(100);
       Cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

       Activo.setMinWidth(100);
       Activo.setCellValueFactory(new PropertyValueFactory<>("activo"));
      
       tableData.getColumns().addAll(Nombre, Descripcion,Entrada,Salida,Cantidad,Activo);
       tableData.setItems(lista);
    
    }
    
    public void llenaTabla(){
        //lblInfo.setText("Se muestran " + lista.size() + " registros.");
        tableData.setItems(lista);       
    }  
        
        
}
