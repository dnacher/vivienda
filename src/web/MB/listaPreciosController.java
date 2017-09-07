package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ListaPreciosBean;
import entities.persistence.entities.Listaprecios;
import entities.persistence.entities.ListapreciosId;
import entities.persistence.entities.Material;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import ejb.services.MaterialBean;
import exceptions.ServiceException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        cargarComboMaterial();
        task();
        atras();
    }
    
    public void cargarComboMaterial(){
        try {
            ObservableList<Material> listaMaterial;
            MaterialBean mb= new MaterialBean();
            listaMaterial= FXCollections.observableArrayList(mb.traerTodos());
            cmbMaterial.setItems(listaMaterial);
        } catch (ServiceException ex) {
            Logger.getLogger(CotizacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clear(){
        txtPrecio.clear();
        txtCantidad.clear(); 
    }
    
    public void atras() {            
       paneForm.setOpacity(0);       
       new FadeInUpTransition(paneTable).play();       
    }
  
    public void nuevaListaPrecios() {
        paneTable.setOpacity(0);
        new FadeInUpTransition(paneForm).play();         
    }

    public void guardar(){       
        ControlVentana cv=new ControlVentana();
            
            if(!ConfiguracionControl.esNumero(txtCantidad.getText()) && !ConfiguracionControl.esNumero(txtPrecio.getText())){                
                cv.creaVentanaNotificacionError("El o los campos deben ser numericos");
            }
            else{
                try{
                    Material material=cmbMaterial.getSelectionModel().getSelectedItem();
                    material.setEntrada(material.getEntrada()+Integer.valueOf(txtCantidad.getText()));
                    material.setCantidad(material.getEntrada()-material.getSalida());
                    MaterialBean mb=new MaterialBean();
                    mb.modificar(material);
                    Listaprecios listaPrecios=new Listaprecios();
                    listaPrecios.setActivo(ChkActivo.isSelected());
                    listaPrecios.setCantidad(Integer.valueOf(txtCantidad.getText()));
                    listaPrecios.setFecha(ConfiguracionControl.TraeFecha(cmbFechaCompra.getValue()));
                    ListapreciosId preciosId=new ListapreciosId();
                    preciosId.setIdlistaPrecios(ConfiguracionControl.traeUltimoId("ListaPrecios"));
                    preciosId.setMaterialIdmaterial(material.getIdmaterial());
                    listaPrecios.setId(preciosId);
                    listaPrecios.setMaterial(cmbMaterial.getSelectionModel().getSelectedItem());
                    listaPrecios.setPrecio(Integer.valueOf(txtPrecio.getText()));
                    ListaPreciosBean lpb=new ListaPreciosBean();
                    lpb.guardar(listaPrecios);
                    clear();
                    llenaTabla();
                    cv.creaVentanaNotificacionCorrecto();
                }
                catch(Exception ex){
                    cv.creaVentanaNotificacionError(ex.getMessage());
                    cv.creaVentanaNotificacionError(ex.getMessage());
                }       
            }       
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
