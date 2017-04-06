package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ConvenioBean;
import ejb.services.CuotaConvenioBean;
import ejb.services.GastosComunesBean;
import ejb.services.MontoBean;
import ejb.services.UnidadBean;
import entities.persistence.entities.Convenio;
import entities.persistence.entities.Cuotaconvenio;
import entities.persistence.entities.CuotaconvenioId;
import entities.persistence.entities.Gastoscomunes;
import entities.persistence.entities.GastoscomunesId;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import web.animations.FadeInUpTransition;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class PagoConveniosController implements Initializable {
    
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
    private PieChart chartCuotas;

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
    
    @FXML
    private Label lblInfoPieChartCuotas;
    
    @FXML
    private Label lblSaldoRestante;
     
    
    int periodo;
    Unidad unidad;
    boolean guardado=false;
    public ObservableList<Unidad> unidadConvenios;
    Convenio convenio;
    int cuotasRestantes;
    int sugerido=-1;
    double saldoRestante=0.0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {             
            task(); 
        try {
            cargaComboMonto();
            cargarComboBlock();
            cargarComboTorre();
            cargaHoy();            
        } catch (ServiceException ex) {
            Logger.getLogger(PagoConveniosController.class.getName()).log(Level.SEVERE, null, ex);
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
                    unidadConvenios=FXCollections.observableArrayList(ub.TraeUnidadesConConvenioXBlockTorre("",0));
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
            periodo=ConfiguracionControl.devuelvePeriodoActual();
            unidad=tableGastosComunes.getSelectionModel().getSelectedItem();
            CuotaConvenioBean cb=new CuotaConvenioBean();
            convenio=cb.traerConvenioXUnidad(unidad);
            lblPeriodo.setText(String.valueOf(periodo));
            lblUnidadNombre.setText(unidad.getNombre()+ " " 
                                  + unidad.getApellido());
            lblUnidadDireccion.setText(unidad.getBlock()
                                     + unidad.getTorre() + "/ " 
                                     + unidad.getPuerta());
            paneGastosComunes.setOpacity(0);
            new FadeInUpTransition(paneFormulario).play();
            chkBonificacion.setSelected(tieneBonificacion());
            cargaGraficaCuotas();
            calculaRestante();
            if(sugerido!=-1){
                txtMonto.setText(String.valueOf(sugerido));               
            }
            else{
                if(convenio!=null){
                int montoAprox=((convenio.getDeudaTotal()-convenio.getSaldoInicial())/convenio.getCuotas());
                txtMonto.setText(String.valueOf(montoAprox));
                }
            }          
        }
        catch(Exception ex){
           // lblInfo.setText("Debe seleccionar una unidad.");
            System.out.println(ex.getMessage());
        }       
    }
    
    public boolean tieneBonificacion() throws ServiceException{
        boolean tiene=false;             
        int day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        ConvenioBean cb=new ConvenioBean();
        convenio=cb.traeConvenioXUnidad(unidad);
        int diaAPagar=convenio.getReglabonificacion().getDiaApagar();
        if(diaAPagar>=day){
            tiene=true;
        }
        return tiene;
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
       tableGastosComunes.setItems(unidadConvenios);
       cargaGrafica("",0);
    }
    
    public void llenaTabla(){
        lblInfo.setText("Se muestran " + unidadConvenios.size() + " registros.");
        tableGastosComunes.setItems(unidadConvenios);
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
        int totalPago=total-unidadConvenios.size();
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
    
    public void cargaGraficaCuotas(){
        lblInfoPieChartCuotas.setText("");
        
        ConvenioBean cb=new ConvenioBean();
        convenio=cb.traeConvenioXUnidad(unidad);
        
        CuotaConvenioBean ccb=new CuotaConvenioBean();
        int cantidadCuotas=ccb.devuelveCantidadCuotas(unidad);
        cuotasRestantes=convenio.getCuotas()-cantidadCuotas;
        
        
        ObservableList<PieChart.Data> lista=FXCollections.observableArrayList(                
                new PieChart.Data("A pagar",cuotasRestantes),
                new PieChart.Data("Pagas", cantidadCuotas)
        );
        chartCuotas.setData(lista);
        
        for(final PieChart.Data data: chartCuotas.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                int num=(int)data.getPieValue();
                double porc=(((double)num*(double)100)/convenio.getCuotas());
                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.CEILING);
                String porcent=df.format(porc);
                lblInfoPieChartCuotas.setText(data.getName()+ ": " + num + " (" + porcent + " % aprox.)");
            });
        }
    }

    public void mostrar(ActionEvent event) {       
        try{
            lblInfo.setText("");
            String block;
            int torre;
            List<Unidad> listaTorreBlock;
            UnidadBean ub= new UnidadBean();
            if(cmbBlock.getValue()!=null){
                if(cmbTorre.getValue()!=null){
                   listaTorreBlock=ub.TraeUnidadesConvenioXBlockTorre(cmbBlock.getValue(), cmbTorre.getValue());
                   block=cmbBlock.getValue();
                   torre=cmbTorre.getValue();
                }
                else{
                    listaTorreBlock=ub.TraeUnidadesConvenioXBlockTorre(cmbBlock.getValue(), 0);
                    block=cmbBlock.getValue();
                    torre=0;
                }
            }else{
                if(cmbTorre.getValue()!=null){
                   listaTorreBlock=ub.TraeUnidadesConvenioXBlockTorre("", cmbTorre.getValue());
                   block="";
                   torre=cmbTorre.getValue();
                }
                else{
                    listaTorreBlock=ub.TraeUnidadesConvenioXBlockTorre("", 0);
                    block="";
                    torre=0;
                }
            }
            unidadConvenios = FXCollections.observableList(listaTorreBlock);
            llenaTabla();
            cargaGrafica(block, torre);
            lblInfo.setText("Se muestran " + unidadConvenios.size() + " unidades");         
        }
        catch(Exception ex){
            lblInfo.setText("Debe seleccionar valores de Block y/o Torre para buscar");
        }
    }
       
        public void mostrarTodos() {
            UnidadBean ub=new UnidadBean();
            List<Unidad> listaTotal=ub.TraeUnidadesConvenioXBlockTorre("",0);            
            unidadConvenios = FXCollections.observableList(listaTotal);
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
            ControlVentana cv=new ControlVentana();
            try {
                Cuotaconvenio cuotaConvenio=new Cuotaconvenio();
                cuotaConvenio.setConvenio(convenio);
                //cuotaConvenio.setDescripcion("Nnnnnnnnnnnnnnnnnnnnnno hay descripcion");
                CuotaconvenioId cuotaConvenioId=new CuotaconvenioId();
                cuotaConvenioId.setConvenioIdconvenio(convenio.getId().getIdconvenio());
                cuotaConvenioId.setConvenioUnidadIdUnidad(unidad.getIdUnidad());
                cuotaConvenioId.setIdcuotaConvenio(ConfiguracionControl.traeUltimoId("CuotaConvenio"));
                cuotaConvenioId.setMontoIdmonto(cmbMoneda.getValue().getIdmonto());
                cuotaConvenio.setId(cuotaConvenioId);
                cuotaConvenio.setMonto(cmbMoneda.getValue());
                cuotaConvenio.setNumeroCuota(Integer.valueOf(lblPeriodo.getText()));
                cuotaConvenio.setPago(Integer.valueOf(txtMonto.getText()));
                cuotaConvenio.setTieneBonificacion(chkBonificacion.isSelected());
                CuotaConvenioBean cb=new CuotaConvenioBean();
                cb.guardar(cuotaConvenio);
                cv.creaVentanaNotificacionCorrecto();                
                atras();
            } catch (ServiceException ex) {
                cv.creaVentanaNotificacionError(ex.getMessage());
                Logger.getLogger(PagoConveniosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void calculaRestante(){
            int deudaTotal=convenio.getDeudaTotal();
            int saldoInicial=convenio.getSaldoInicial();
            CuotaConvenioBean ccb=new CuotaConvenioBean();
            double totalPagado=ccb.devuelveTotalCuotas(unidad);
            saldoRestante=deudaTotal-saldoInicial-totalPagado;
            sugerido=(int) Math.ceil(saldoRestante/cuotasRestantes);
            lblSaldoRestante.setText("El saldo restante es: " + saldoRestante);       
        }        
       
        public void anular() throws IOException {
            lblInfo.setText("");
            ControlVentana cv= new ControlVentana(); 
            creaDialogoConfirmacion();
            if(viviendas.Viviendas.confirmacion){
                try {
                    unidad=tableGastosComunes.getSelectionModel().getSelectedItem();
                    if(unidad!=null){
                        CuotaConvenioBean cb=new CuotaConvenioBean();
                        convenio=cb.traerConvenioXUnidad(unidad);
                        calculaRestante();
                        convenio.setActivo(false);
                        ConvenioBean cb2= new ConvenioBean();
                        cb2.modificar(convenio);
                        Gastoscomunes gc=new Gastoscomunes();
                        gc.setActivo(true);
                        gc.setEstado(1);
                        GastoscomunesId gci=new GastoscomunesId();
                        gci.setIdGastosComunes(ConfiguracionControl.traeUltimoId("GastosComunes"));
                        gci.setUnidadIdUnidad(unidad.getIdUnidad());
                        gc.setId(gci);
                        gc.setIsBonificacion(false);
                        ConfiguracionControl cc=new ConfiguracionControl();
                        gc.setMonto(cc.traeMontoPesos());
                        gc.setMonto_1((int)saldoRestante);                   
                        gc.setPeriodo((ConfiguracionControl.devuelvePeriodoActual()-1));
                        gc.setUnidad(unidad);
                        Date date=new Date(0000, 00, 00);
                        gc.setFechaPago(date);
                        GastosComunesBean gcb=new GastosComunesBean();
                        gcb.guardar(gc);
                        viviendas.Viviendas.confirmacion=false;
                        cv.creaVentanaNotificacionCorrecto();
                    }else{
                        lblInfo.setText("Debe Seleccionar una unidad.");
                    }
                } catch (ServiceException ex) {
                    cv.creaVentanaNotificacionError(ex.getMessage());
                    Logger.getLogger(PagoConveniosController.class.getName()).log(Level.SEVERE, null, ex);  
                }
            }            
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
}

