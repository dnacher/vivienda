package web.controller;

import ejb.services.MontoBean;
import ejb.services.UnidadBean;
import ejb.utils.UtilsConfiguracion;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class GastosComunesController implements Initializable {
    
    @FXML
    private TextField txtMonto;

    @FXML
    private ComboBox<Monto> cmbMoneda;

    @FXML
    private TableView<Unidad> tableGastosComunes;

    @FXML
    private DatePicker cmbFecha;

    @FXML
    private AnchorPane paneFormulario;

    @FXML
    private PieChart chartGastosComunes;

    @FXML
    private ProgressBar bar;     

    @FXML
    private ComboBox<Integer> cmbTorre;

    @FXML
    private AnchorPane paneGastosComunes;

    @FXML
    private CheckBox chkBonificacion;

    @FXML
    private ComboBox<String> cmbBlock;
    
    @FXML
    private Button btnMostrar;
    
    @FXML
    private Label lblInfoPieChart;
    
    @FXML
    private Label lblInfo;
    
    @FXML
    private Label lblInfoUnidad;
    
    
    ObservableList<Unidad> retorno;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UnidadBean ub=new UnidadBean();
       
        try {
            
            retorno=FXCollections.observableArrayList(ub.traerTodos());
             MontoBean mb=new MontoBean();
             ObservableList<Monto> montos=FXCollections.observableArrayList(mb.traerTodos());
            cmbMoneda.setItems(montos);
        } catch (ServiceException ex) {
            Logger.getLogger(GastosComunesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        atras();         
        cargaHoy();
        cargarComboBlock();
        cargarComboTorre();
        cargaTabla();
    }
    
    public void cargaHoy(){
        Date date= new Date();
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cmbFecha.setValue(ld);
    }
    
    public void agregarGastosComunes(){
        try{
            lblInfoUnidad.setText("");
            Unidad uni=tableGastosComunes.getSelectionModel().getSelectedItem();
            lblInfoUnidad.setText(uni.getNombre()+ " " 
                                + uni.getApellido()+ " " 
                                + uni.getBlock() 
                                + uni.getTorre() + " " 
                                + uni.getPuerta() + "- Periodo: " 
                                + UtilsConfiguracion.devuelvePeriodoActual());
            paneGastosComunes.setOpacity(0);
            new FadeInUpTransition(paneFormulario).play();
            Platform.runLater(() -> {
                clear();
                auto();
            });
        }
        catch(Exception ex){
            lblInfo.setText("Debe seleccionar una unidad.");
        }
        
        
    }
    
    public void atras(){
        paneFormulario.setOpacity(0);
        new FadeInUpTransition(paneGastosComunes).play();
        Platform.runLater(() -> {
            clear();
            auto();
        });   
    }
    
     private void clear(){
        
    }
     
     private void auto(){
      
    }
     
     public void cargarComboBlock(){
       ObservableList<String> options = 
       FXCollections.observableArrayList("A","B","C","D","E");
       cmbBlock.setItems(options);
    }
    
    public void cargarComboTorre(){
        ObservableList<Integer> listaTorres;
        listaTorres=FXCollections.observableArrayList(1,2,3,4,5,6);
        cmbTorre.setItems(listaTorres);
    }
    
    public void cargaTabla(){
       TableColumn Nombre = new TableColumn("Nombre");
       TableColumn Apellido = new TableColumn("Apellido");
       TableColumn Block = new TableColumn("Block");
       TableColumn Torre = new TableColumn("Torre");
       TableColumn Puerta= new TableColumn("Puerta");

       Nombre.setMinWidth(200);
       Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

       Apellido.setMinWidth(200);
       Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

       Block.setMinWidth(100);
       Block.setCellValueFactory(new PropertyValueFactory<>("Block"));

       Torre.setMinWidth(100);
       Torre.setCellValueFactory(new PropertyValueFactory<>("Torre"));

       Puerta.setMinWidth(110);
       Puerta.setCellValueFactory(new PropertyValueFactory<>("Puerta"));

       tableGastosComunes.getColumns().addAll(Nombre, Apellido, Block,Torre,Puerta);
       tableGastosComunes.setItems(retorno);
       cargaGrafica("",0);
    }
    
    public void llenaTabla(){
        lblInfo.setText("Se muestran " + retorno.size() + " registros.");
        tableGastosComunes.setItems(retorno);     
        cargaGrafica(cmbBlock.getValue(), cmbTorre.getValue());
    }
    
    public void cargaGrafica(String block, int torre){
        lblInfoPieChart.setText("");
        UnidadBean ub=new UnidadBean();
        int total=ub.totalUnidades(block,torre);
        int totalPago=total-retorno.size();
        int totalNoPago=total-totalPago;
        ObservableList<PieChart.Data> lista=FXCollections.observableArrayList(                
                new PieChart.Data("No pagó", totalNoPago),
                new PieChart.Data("Pagó", totalPago)
        );
        chartGastosComunes.setData(lista);
        
        for(final PieChart.Data data: chartGastosComunes.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                int num=(int)data.getPieValue();
                double porc=(((double)num*(double)100)/total);
                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.CEILING);
                String porcent=df.format(porc);
                lblInfoPieChart.setText(data.getName()+ ": " + num + " (" + porcent + " % aprox.)");
            });
        }
        }
    
        public void mostrar(ActionEvent event) {       
            try{
            lblInfo.setText("");
            UnidadBean ub= new UnidadBean();
            List<Unidad> listaTorreBlock=ub.TraeUnidadesXBlockTorreNoPago(cmbBlock.getValue(), cmbTorre.getValue());        
            retorno = FXCollections.observableList(listaTorreBlock);
            llenaTabla();
            cargaGrafica(cmbBlock.getValue(), cmbTorre.getValue());
            }
            catch(Exception ex){
                lblInfo.setText("Debe seleccionar valores de Block y Torre para buscar");
            }
        }
       
        public void mostrarTodos() {
            UnidadBean ub=new UnidadBean();
            List<Unidad> listaTotal=ub.TraeUnidadesGastosComunesNoPago();            
            retorno = FXCollections.observableList(listaTotal);
            llenaTabla();
            cargaGrafica("",0);
        }
    
    
}

