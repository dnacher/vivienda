package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.GastosComunesBean;
import ejb.services.MontoBean;
import ejb.services.UnidadBean;
import ejb.utils.UtilsConfiguracion;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Unidad;
import entities.persistence.entities.Gastoscomunes;
import entities.persistence.entities.GastoscomunesId;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
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
import viviendas.Viviendas;
import web.animations.FadeInUpTransition;

public class GastosComunesController implements Initializable {
    
    @FXML
    private TextField txtMonto;

    @FXML
    private ComboBox<Monto> cmbMoneda;
    
    @FXML
    private Label lblSimbolo;

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
    private Label lblInfoPieChart;
    
    @FXML
    private Label lblInfo;
    
    @FXML
    private Label lblPeriodo;
    
    @FXML
    private Label lblUnidadNombre;
    
    @FXML
    private Label lblUnidadDireccion;
     
    
    int periodo;
    Unidad unidad;
    boolean guardado=false;
    Viviendas viviendas=new Viviendas();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {             
            task(); 
        try {
            cargaComboMonto();
            cargarComboBlock();
            cargarComboTorre();
            cargaHoy();
        } catch (ServiceException ex) {
            Logger.getLogger(GastosComunesController.class.getName()).log(Level.SEVERE, null, ex);
        }
           

        cmbMoneda.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                monedaCombo();
            }
            
        });  
        cmbMoneda.getSelectionModel().selectFirst();
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

        longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
               UnidadBean ub=new UnidadBean();       
                    viviendas.unidadesGastosComunesNoPago=FXCollections.observableArrayList(ub.TraeUnidadesGastosComunesNoPago());
                    cargaTabla();
                    atras();
                    
            }
        });
        bar.progressProperty().bind(longTask.progressProperty());        
        new Thread(longTask).start();
    }
    
    public void cargaComboMonto() throws ServiceException{
        MontoBean mb=new MontoBean();
        ObservableList<Monto> montos=FXCollections.observableArrayList(mb.traerTodos());             
        cmbMoneda.setItems(montos);
    }
    
    public void cargaHoy(){
        Date date= new Date();
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cmbFecha.setValue(ld);
    }
    
    public void monedaCombo(){        
            Monto monto=cmbMoneda.getSelectionModel().getSelectedItem();
            lblSimbolo.setText(monto.getSimbolo());   
    }
    
    public void agregarGastosComunes(){
        try{
            lblPeriodo.setText("");
            lblUnidadNombre.setText("");
            lblUnidadDireccion.setText("");
            periodo=UtilsConfiguracion.devuelvePeriodoActual();
            unidad=tableGastosComunes.getSelectionModel().getSelectedItem();
            lblPeriodo.setText(String.valueOf(periodo));
            lblUnidadNombre.setText(unidad.getNombre()+ " " 
                                + unidad.getApellido());
            lblUnidadDireccion.setText(unidad.getBlock()
                                + unidad.getTorre() + "/ " 
                                + unidad.getPuerta());
            paneGastosComunes.setOpacity(0);
            new FadeInUpTransition(paneFormulario).play();
            Platform.runLater(() -> {
                         
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
        });   
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

       Nombre.setMinWidth(150);
       Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

       Apellido.setMinWidth(150);
       Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

       Block.setMinWidth(100);
       Block.setCellValueFactory(new PropertyValueFactory<>("Block"));

       Torre.setMinWidth(100);
       Torre.setCellValueFactory(new PropertyValueFactory<>("Torre"));

       Puerta.setMinWidth(110);
       Puerta.setCellValueFactory(new PropertyValueFactory<>("Puerta"));

       tableGastosComunes.getColumns().addAll(Nombre, Apellido, Block,Torre,Puerta);
       tableGastosComunes.setItems(viviendas.unidadesGastosComunesNoPago);
       cargaGrafica("",0);
    }
    
    public void llenaTabla(){
        lblInfo.setText("Se muestran " + viviendas.unidadesGastosComunesNoPago.size() + " registros.");
        tableGastosComunes.setItems(viviendas.unidadesGastosComunesNoPago);
        if(guardado){
            cargaGrafica("", 0);
        }else{
            if(cmbBlock.getValue()!=null && cmbTorre.getValue()!=null){
                cargaGrafica(cmbBlock.getValue(), cmbTorre.getValue());
            }else{
                cargaGrafica("", 0);
            }      
        }
    }
    
    public void cargaGrafica(String block, int torre){
        lblInfoPieChart.setText("");
        UnidadBean ub=new UnidadBean();
        int total=ub.totalUnidades(block,torre);
        int totalPago=total-viviendas.unidadesGastosComunesNoPago.size();
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
            viviendas.unidadesGastosComunesNoPago = FXCollections.observableList(listaTorreBlock);
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
            viviendas.unidadesGastosComunesNoPago = FXCollections.observableList(listaTotal);
            llenaTabla();           
        }
        
        public boolean validar(){
            boolean correcto=false;
            if(!txtMonto.getText().isEmpty()){
                correcto=true;
            }
            return correcto;
        }
        
        public void guardar(){
            if(validar()){
                try{
                GastoscomunesId gcId=new GastoscomunesId();
                gcId.setIdGastosComunes(ConfiguracionControl.traeUltimoId("GastosComunes"));
                gcId.setUnidadIdUnidad(unidad.getIdUnidad());
                Gastoscomunes gc=new Gastoscomunes();
                gc.setActivo(true);
                gc.setEstado(2);
                gc.setFechaPago(ConfiguracionControl.TraeFecha(cmbFecha.getValue()));
                gc.setId(gcId);
                gc.setIsBonificacion(chkBonificacion.isSelected());
                gc.setMonto(cmbMoneda.getSelectionModel().getSelectedItem());
                gc.setMonto_1(Integer.valueOf(txtMonto.getText()));
                gc.setPeriodo(periodo);
                gc.setUnidad(unidad);
                GastosComunesBean gcb=new GastosComunesBean();
                gcb.guardar(gc);
                ControlVentana cv=new ControlVentana();
                cv.creaVentanaNotificacionCorrecto();  
                UnidadBean ub=new UnidadBean();   
                guardado=true;
                mostrarTodos();
                atras();
                }
                catch(Exception ex){
                    ex.getMessage();
                }
                
            }
        }             
}

