package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ListaPreciosBean;
import ejb.utils.UtilsConfiguracion;
import entities.persistence.entities.Listaprecios;
import entities.persistence.entities.ListapreciosId;
import entities.persistence.entities.Material;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;
import UtilsGeneral.ListaPreciosTable;

public class listaPreciosController implements Initializable {
    
     @FXML
    private CheckBox ChkActivo;

    @FXML
    private DatePicker cmbFechaCompra;

    @FXML
    private ComboBox<Material> cmbMaterial;

    @FXML
    private TableView<ListaPreciosTable> tableListaPrecios;

    @FXML
    private TextField txtPrecio;

    @FXML
    private ProgressBar bar;

    @FXML
    private TextField txtCantidad;
    
    @FXML
    private AnchorPane paneTable;

    @FXML
    private AnchorPane paneForm;
    
    @FXML
    private Button btnAgregar;
    
    public ObservableList<ListaPreciosTable> lista;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lista=FXCollections.observableArrayList(ListaPreciosTable.devuelveTodosPrecios());
        cargaTabla();
        task();
        atras(null);      
    }   
    
    private void clear(){
        txtPrecio.clear();
        txtCantidad.clear(); 
    }    

    @FXML
    private void nuevaListaPrecios(ActionEvent event) {
        paneTable.setOpacity(0);
        paneTable.setVisible(false);
        new FadeInUpTransition(paneForm).play();
        paneForm.setVisible(true);
    }
    
    @FXML
    private void guardar(ActionEvent event){       
        ControlVentana cv=new ControlVentana();
            
            if(!UtilsConfiguracion.esNumero(txtCantidad.getText()) && !UtilsConfiguracion.esNumero(txtPrecio.getText())){                
                cv.creaVentanaNotificacionError("El o los campos deben ser numericos");
            }
            else{
                try{
                    Listaprecios listaPrecios=new Listaprecios();
                    listaPrecios.setActivo(ChkActivo.isSelected());
                    listaPrecios.setCantidad(Integer.valueOf(txtCantidad.getText()));
                    listaPrecios.setFecha(ConfiguracionControl.TraeFecha(cmbFechaCompra.getValue()));
                    ListapreciosId preciosId=new ListapreciosId();
                    preciosId.setIdlistaPrecios(ConfiguracionControl.traeUltimoId("ListaPrecios"));
                    preciosId.setMaterialIdmaterial(cmbMaterial.getSelectionModel().getSelectedItem().getIdmaterial());
                    listaPrecios.setId(preciosId);
                    listaPrecios.setMaterial(cmbMaterial.getSelectionModel().getSelectedItem());
                    listaPrecios.setPrecio(Integer.valueOf(txtPrecio.getText()));
                    ListaPreciosBean lpb=new ListaPreciosBean();
                    lpb.guardar(listaPrecios);
                    clear();
                    cv.creaVentanaNotificacionCorrecto();
                }
                catch(Exception ex){
                    cv.creaVentanaNotificacionError(ex.getMessage());
                    cv.creaVentanaNotificacionError(ex.getMessage());
                }       
            }       
        }

        @FXML
        private void atras(ActionEvent event) {
            paneForm.setVisible(false);
            paneForm.setOpacity(0);
            new FadeInUpTransition(paneTable).play();
            paneTable.setVisible(true);
            
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
       TableColumn Material = new TableColumn("material");
       TableColumn Precio = new TableColumn("precio");
       TableColumn Cantidad = new TableColumn("cantidad");
       TableColumn Fecha = new TableColumn("fecha");
       
       
       Material.setMinWidth(150);
       Material.setCellValueFactory(new PropertyValueFactory<>("material"));

       Precio.setMinWidth(150);
       Precio.setCellValueFactory(new PropertyValueFactory<>("precio"));

       Cantidad.setMinWidth(100);
       Cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

       Fecha.setMinWidth(100);
       Fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
      
       tableListaPrecios.getColumns().addAll(Material, Precio, Cantidad,Fecha);
       tableListaPrecios.setItems(lista);
    
    }
    
    public void llenaTabla(){
        //lblInfo.setText("Se muestran " + lista.size() + " registros.");        
        lista=FXCollections.observableArrayList(ListaPreciosTable.devuelveTodosPrecios());
        tableListaPrecios.setItems(lista);
    }  
        
        
}
