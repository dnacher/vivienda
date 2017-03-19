package web.controller;

import ejb.services.MontoBean;
import ejb.services.ReglaBonificacionBean;
import ejb.services.UnidadBean;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Reglabonificacion;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import web.animations.FadeInUpTransition;
import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import entities.persistence.entities.Tipobonificacion;
import ejb.services.TipoBonificacionBean;
import ejb.services.ConvenioBean;
import entities.persistence.entities.Convenio;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import entities.persistence.entities.ConvenioId;
import java.util.HashMap;



public class ConveniosController implements Initializable {
    
    @FXML
    private ComboBox<Monto> cmbMoneda;

    @FXML
    private ComboBox<Reglabonificacion> cmbReglaBonificacion;

    @FXML
    private TableView<Unidad> tblUnidades;

    @FXML
    private AnchorPane paneStep1;
    
    @FXML
    private AnchorPane paneStep2;

    @FXML
    private AnchorPane paneStep3;

    @FXML
    private Button btnStepAtras;   

    @FXML
    private ImageView imgProgressTracker;

    @FXML
    private TextField txtSaldoInicial;

    @FXML
    private ComboBox<String> cmbTipoConvenio;

    @FXML
    private Label lblCuotas;

    @FXML
    private Button btnStepAdelante;

    @FXML
    private ComboBox<Tipobonificacion> cmbTipoBonificacion;

    @FXML
    private ComboBox<Integer> cmbTorre;

    @FXML
    private ComboBox<String> cmbBlock;

    @FXML
    private Label lblDeudaTotal;   
        
    @FXML
    private Text txtUnidad;
    
    @FXML
    private Text txtUnidad2;
    
    @FXML
    private Label lblInfo;
    
    @FXML
    private Label lblSimbolo;
    
    @FXML
    private DatePicker cmbFechaTipoConvenio;
    
    @FXML
    private TextField txtTipoConvenio;
    
    @FXML
    private Label lblTipoConvenio;
    
    @FXML
    private Button btnRefresh;
    
    @FXML
    private CheckBox chkActivo;
    
    @FXML
    private TextArea txtDescripcion;
     
    ObservableList listaUnidades;
    Image img1=new Image("/web/images/step1.png");
    Image img2=new Image("/web/images/step2.png");
    Image img3=new Image("/web/images/step3.png");
    int paneActual=1;
    Unidad unidad;
    public int i;
    Long deudaPesos=0L;
    Monto monto=null;
    Reglabonificacion reglaBonificacion=null;
    Tipobonificacion tipoBonificacion=null;
    double deudaOtraMoneda=0;
    double cuotas=0;
    int saldoInicial=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRefresh.setVisible(false);
        txtTipoConvenio.setVisible(false);
        cmbFechaTipoConvenio.setVisible(false);
        cargaTabla();
        btnStepAtras.setDisable(true);
        atras();        
        cargarComboBlock();
        cargarComboTorre();
        cargarComboTipoConvenio();
          try {
              cargaComboMonto();
              cargaComboReglaBonificacion();
              cargaComboTipoBonificacion();
              cmbReglaBonificacion.getSelectionModel().selectFirst();
              cmbTipoBonificacion.getSelectionModel().selectFirst();
          } catch (ServiceException ex) {
              Logger.getLogger(ConveniosController.class.getName()).log(Level.SEVERE, null, ex);
          }
    }  
    
    public void cargarComboBlock(){
       ObservableList<String> options = 
       FXCollections.observableArrayList("A","B","C","D","E");
       cmbBlock.setItems(options);
    }
    
    public void cargarComboTipoConvenio(){
       ObservableList<String> options = 
       FXCollections.observableArrayList("Limite Cuotas","Limite Fecha","Limite Monto");
       cmbTipoConvenio.setItems(options);
       cmbTipoConvenio.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                calculaTipoConvenio(cmbTipoConvenio.getValue());
            }
            
        });
    }
    
    public void calculaTipoConvenio(String valor){
        btnRefresh.setVisible(true);
        switch(valor){
            case "Limite Cuotas":
                txtTipoConvenio.setVisible(true);
                cmbFechaTipoConvenio.setVisible(false);
                lblTipoConvenio.setText("Cuotas");
                break;
            case "Limite Fecha":
                txtTipoConvenio.setVisible(false);
                cmbFechaTipoConvenio.setVisible(true);
                lblTipoConvenio.setText("Fecha");
                break;
            case "Limite Monto":
                txtTipoConvenio.setVisible(true);
                cmbFechaTipoConvenio.setVisible(false);
                lblTipoConvenio.setText("Monto");                
                break;
        }
    }
    
    public void calculaTipoConveniotext(){
        switch(cmbTipoConvenio.getValue()){
            case "Limite Cuotas":
                if(!txtTipoConvenio.getText().isEmpty()){
                    try{
                        cuotas=Double.valueOf(txtTipoConvenio.getText());
                        double total=deudaPesos;                                
                        if(!txtSaldoInicial.getText().isEmpty()){
                            saldoInicial=Integer.valueOf(txtSaldoInicial.getText());
                            double monedaSaldo=saldoInicial;
                            total-=monedaSaldo;
                        }
                        double informacion=total/cuotas;
                        //formatea el valor a 2 digitos
                        DecimalFormat df = new DecimalFormat("####0.00");                        
                        lblCuotas.setText("Cuota aproximada: $" + df.format(informacion));
                    }
                    catch(Exception ex){
                        System.err.println("debe ser un valor numerico");
                    }
                }else{
                    System.err.println("No debe estar vacio");
                }
                break;
            case "Limite Fecha":
                if(cmbFechaTipoConvenio.getValue()!=null){
                    int currentyear =Calendar.getInstance().get(Calendar.YEAR);
                    int currentmonth=Calendar.getInstance().get(Calendar.MONTH)+1; 
                    Calendar cal = Calendar.getInstance();
                    Date date=ConfiguracionControl.TraeFecha(cmbFechaTipoConvenio.getValue());                    
                    cal.setTime(date);
                    int finalYear=cal.get(Calendar.YEAR);
                    int finalMonth=cal.get(Calendar.MONTH)+1;                    
                    ConfiguracionControl cc=new ConfiguracionControl();
                    cuotas=cc.devuelveCuotas(currentmonth, finalMonth, currentyear, finalYear);
                    double monto=deudaPesos/(double)cuotas;
                    DecimalFormat df = new DecimalFormat("####0.00"); 
                    lblCuotas.setText((int)cuotas + " cuotas de $" + df.format(monto));
                }else{
                    System.err.println("no hay fecha seleccionada");
                }
                break;
            case "Limite Monto":
                if(!txtTipoConvenio.getText().isEmpty()){
                    try{
                        double monto=Integer.valueOf(txtTipoConvenio.getText());
                        double total=deudaPesos;
                        if(!txtSaldoInicial.getText().isEmpty()){
                            saldoInicial=Integer.valueOf(txtSaldoInicial.getText());
                            double monedaSaldo=saldoInicial;
                            total-=monedaSaldo;
                        }
                        //redondea hacia arriba siempre
                        cuotas=(int) Math.ceil(total/monto);
                        lblCuotas.setText("Numero de cuotas aproximadas: " + (int)cuotas);
                    }
                    catch(Exception ex){
                        System.err.println("debe ser un valor numerico");
                    }
                }else{
                    System.err.println("No debe estar vacio");
                }
                break;
        }
    }
    
    public void cargaComboTipoBonificacion() throws ServiceException{
       TipoBonificacionBean tb=new TipoBonificacionBean();
       ObservableList<Tipobonificacion> options = 
       FXCollections.observableArrayList(tb.traerTodos());
       cmbTipoBonificacion.setItems(options);
       cmbTipoBonificacion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                tipoBonificacion=cmbTipoBonificacion.getValue();
            }
            
        }); 
    }
    
    public void cargarComboTorre(){
        ObservableList<Integer> listaTorres;
        listaTorres=FXCollections.observableArrayList(1,2,3,4,5,6);
        cmbTorre.setItems(listaTorres);
    }
   
    public void agregarGastosComunes() throws InterruptedException{
        
        switch(paneActual){
            case 1: 
                btnStepAdelante.setDisable(false);
                btnStepAtras.setDisable(true);
                imgProgressTracker.setImage(null);
                new FadeInUpTransition(imgProgressTracker).play();  
                imgProgressTracker.setImage(img1);                
                break;
            case 2:     
                btnStepAdelante.setDisable(false);
                btnStepAtras.setDisable(false);
                imgProgressTracker.setImage(null);
                new FadeInUpTransition(imgProgressTracker).play(); 
                imgProgressTracker.setImage(img2); 
                break;
            case 3:  
                btnStepAdelante.setDisable(true);
                btnStepAtras.setDisable(false);
                imgProgressTracker.setImage(null);
                new FadeInUpTransition(imgProgressTracker).play(); 
                imgProgressTracker.setImage(img3);
                break;
        }
        manageSteps();
    }
    
    public void manageSteps(){
        switch(paneActual){
            case 1:
                paneStep2.setOpacity(0);
                paneStep3.setOpacity(0);
                new FadeInUpTransition(paneStep1).play();
                break;
            case 2:
                paneStep1.setOpacity(0);
                paneStep3.setOpacity(0);
                new FadeInUpTransition(paneStep2).play();
                break;
            case 3:
                paneStep1.setOpacity(0);
                paneStep2.setOpacity(0);
                new FadeInUpTransition(paneStep3).play();
                break;
        }
    }
    
    public void btnAdelante() throws InterruptedException{
        
        switch(paneActual){
            case 1:
                lblInfo.setText("");
                paneActual=2;
                unidad=tblUnidades.getSelectionModel().getSelectedItem();
                txtUnidad.setText(unidad.getNombre() + " " + unidad.getApellido());
                txtUnidad2.setText(unidad.getNombre() + " " + unidad.getApellido());
                UnidadBean ub=new UnidadBean();
                deudaPesos=ub.TraeTotalImporteXUnidadParaConvenio(unidad);
                lblDeudaTotal.setText(String.valueOf(deudaPesos));
                agregarGastosComunes();
                break;
            case 2:
                paneActual=3;
                agregarGastosComunes();
                break;            
        }
    }
    
    public void btnAtras() throws InterruptedException{
        switch(paneActual){
            case 3:
                paneActual=2;
                agregarGastosComunes();
                break;
            case 2:
                paneActual=1;
                agregarGastosComunes();
                break;
        }       
    }
    
    public void atras(){
        paneStep2.setOpacity(0);
        paneStep3.setOpacity(0);
        new FadeInUpTransition(paneStep1).play();
        paneActual=1;
        btnStepAdelante.setDisable(false);
        btnStepAtras.setDisable(true);
        imgProgressTracker.setImage(null);
        new FadeInUpTransition(imgProgressTracker).play(); 
        imgProgressTracker.setImage(img1);
    }
    
    public void cargaComboMonto() throws ServiceException{
        MontoBean mb=new MontoBean();
        ObservableList<Monto> montos=FXCollections.observableArrayList(mb.traerTodos());             
        cmbMoneda.setItems(montos);
        cmbMoneda.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                monedaCombo();
            }
            
        });  
        cmbMoneda.getSelectionModel().selectFirst();
    }
    
    public void cargaComboReglaBonificacion() throws ServiceException{
        ReglaBonificacionBean rb=new ReglaBonificacionBean();
        ObservableList<Reglabonificacion> bonificaciones=FXCollections.observableArrayList(rb.traerTodos());             
        cmbReglaBonificacion.setItems(bonificaciones);
        cmbReglaBonificacion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                reglaBonificacion=cmbReglaBonificacion.getValue();
            }
            
        });        
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

       tblUnidades.getColumns().addAll(Nombre, Apellido, Block,Torre,Puerta);
       UnidadBean ub=new UnidadBean();
       List<Unidad> lista=ub.TraeUnidadesConvenio();
       listaUnidades=FXCollections.observableList(lista);
       tblUnidades.setItems(listaUnidades);      
    }
    
     public void monedaCombo(){        
            monto=cmbMoneda.getSelectionModel().getSelectedItem();
            lblSimbolo.setText(monto.getSimbolo());
            if(monto.getValorPesos()!=1){
                deudaOtraMoneda=deudaPesos*monto.getValorPesos();
                DecimalFormat df = new DecimalFormat("####0.00");
                lblDeudaTotal.setText(df.format(deudaOtraMoneda));
            }else{
                 lblDeudaTotal.setText(String.valueOf(deudaPesos));
            }
            
    }
     
      public void mostrar(ActionEvent event) {
            lblInfo.setText("");
            try{
                lblInfo.setText("");
                List<Unidad> listaTorreBlock;
                UnidadBean ub= new UnidadBean();
                if(cmbBlock.getValue()!=null){
                    if(cmbTorre.getValue()!=null){
                       listaTorreBlock=ub.TraeUnidadesConvenioXBlockTorre(cmbBlock.getValue(), cmbTorre.getValue());
                    }
                    else{
                        listaTorreBlock=ub.TraeUnidadesConvenioXBlockTorre(cmbBlock.getValue(), 0);
                    }
                }else{
                    if(cmbTorre.getValue()!=null){
                       listaTorreBlock=ub.TraeUnidadesConvenioXBlockTorre("", cmbTorre.getValue());
                    }
                    else{
                        listaTorreBlock=ub.TraeUnidadesConvenioXBlockTorre("", 0);
                    }
                }                        
                listaUnidades = FXCollections.observableList(listaTorreBlock);
                tblUnidades.setItems(null);
                tblUnidades.setItems(listaUnidades);                
            }
            catch(Exception ex){
                lblInfo.setText("Debe seleccionar valores de Block y Torre para buscar");
            }
        }
       
        public void mostrarTodos() {
            lblInfo.setText("");
            UnidadBean ub=new UnidadBean();
            List<Unidad> listaTotal=ub.TraeUnidadesConvenioXBlockTorre("",0);
            listaUnidades = FXCollections.observableList(listaTotal);
            tblUnidades.setItems(null);
            tblUnidades.setItems(listaUnidades);
        }
        
        public void guardaConvenio(){
            ControlVentana cv= new ControlVentana();
            try {
                Convenio convenio=new Convenio();
                convenio.setActivo(chkActivo.isSelected());
                convenio.setCuotas((int)cuotas);
                if(!txtDescripcion.getText().isEmpty()){
                    convenio.setDescripcion(txtDescripcion.getText());
                }
                if(cmbMoneda.getValue().getSimbolo().equals("$")){
                    double deu=deudaPesos;
                    convenio.setDeudaTotal((int)deu);
                }else{
                    convenio.setDeudaTotal((int)deudaOtraMoneda);
                }
                ConvenioId convenioId=new ConvenioId();
                convenioId.setUnidadIdUnidad(unidad.getIdUnidad());
                convenioId.setIdconvenio(ConfiguracionControl.traeUltimoId("Convenio"));
                convenio.setId(convenioId);
                convenio.setMonto(monto);
                convenio.setReglabonificacion(reglaBonificacion);            
                convenio.setSaldoInicial(saldoInicial);
                convenio.setTipobonificacion(tipoBonificacion);
                convenio.setUnidad(unidad);
                ConvenioBean cb=new ConvenioBean();
                cb.guardar(convenio);
                ConfiguracionControl cc=new ConfiguracionControl();
                HashMap parameters=new HashMap();
                parameters.put("idUnidad",convenio.getUnidad().getIdUnidad());               
                cc.generarReporteConParametros("CreacionConvenioImpresion", parameters);
                cv.creaVentanaNotificacionCorrecto();
            } catch (ServiceException ex) {
                cv.creaVentanaNotificacionError(ex.getMessage());
                Logger.getLogger(ConveniosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
}
 
