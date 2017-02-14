package web.controller;

import ejb.services.MontoBean;
import ejb.services.ReglaBonificacionBean;
import ejb.services.UnidadBean;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Reglabonificacion;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.net.URL;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import web.animations.FadeInUpTransition;

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
    private Button btnMostrar;

    @FXML
    private TextField txtSaldoInicial;

    @FXML
    private ProgressBar bar;

    @FXML
    private ComboBox<String> cmbTipoConvenio;

    @FXML
    private Label lblCuotas;

    @FXML
    private Button btnStepAdelante;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnNew;

    @FXML
    private ComboBox<String> cmbTipoBonificacion;

    @FXML
    private ImageView imgLoad;

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
     
    ObservableList listaUnidades;
    Image img1=new Image("/web/images/step1.png");
    Image img2=new Image("/web/images/step2.png");
    Image img3=new Image("/web/images/step3.png");
    int paneActual=1;
    Unidad unidad;
    public int i;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRefresh.setVisible(false);
        txtTipoConvenio.setVisible(false);
        cmbFechaTipoConvenio.setVisible(false);
        cargaTabla();
        btnStepAtras.setDisable(true);
        atras();              
        imgProgressTracker.setImage(img1);
        cargarComboBlock();
        cargarComboTorre();
        cargarComboTipoConvenio();
          try {
              cargaComboMonto();
              cargaComboReglaBonificacion();
              cargaComboTipoBonificacion();
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
                        int num=Integer.valueOf(txtTipoConvenio.getText());
                        System.err.println("calcula por cuotas");
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
                    System.err.println("calcula por fecha");
                }else{
                    System.err.println("no hay fecha seleccionada");
                }
                break;
            case "Limite Monto":
                if(!txtTipoConvenio.getText().isEmpty()){
                    try{
                        int num=Integer.valueOf(txtTipoConvenio.getText());
                        System.err.println("calcula por monto");
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
    
    public void cargaComboTipoBonificacion(){
       ObservableList<String> options = 
       FXCollections.observableArrayList("Porcentaje","Monto");
       cmbTipoBonificacion.setItems(options);
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
        try{
            lblInfo.setText("");
            unidad=tblUnidades.getSelectionModel().getSelectedItem();
            txtUnidad.setText(unidad.getNombre() + " " + unidad.getApellido());
            txtUnidad2.setText(unidad.getNombre() + " " + unidad.getApellido());
            UnidadBean ub=new UnidadBean();
            Long deuda=ub.TraeTotalImporteXUnidadParaConvenio(unidad);
            lblDeudaTotal.setText(String.valueOf(deuda));
            if(paneActual==1){
            paneActual=2;
            agregarGastosComunes();
            }
            else if(paneActual==2){
                paneActual=3;
                agregarGastosComunes();
            }   
        }
        catch(Exception ex){
            lblInfo.setText("Debe seleccionar una unidad");
        }
            
    }
    
    public void btnAtras() throws InterruptedException{
        if(paneActual==3){
            paneActual=2;
            agregarGastosComunes();
        }
        else if(paneActual==2){
            paneActual=1;
            agregarGastosComunes(); 
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
            Monto monto=cmbMoneda.getSelectionModel().getSelectedItem();
            lblSimbolo.setText(monto.getSimbolo());   
    }
     
      public void mostrar(ActionEvent event) {       
            try{
                lblInfo.setText("");
                List<Unidad> listaTorreBlock;
                UnidadBean ub= new UnidadBean();
                if(cmbBlock.getValue()!=null){
                    if(cmbTorre.getValue()!=null){
                       listaTorreBlock=ub.TraeUnidadesXBlockTorreNoPago(cmbBlock.getValue(), cmbTorre.getValue());
                    }
                    else{
                        listaTorreBlock=ub.TraeUnidadesXBlockTorreNoPago(cmbBlock.getValue(), 0);
                    }
                }else{
                    if(cmbTorre.getValue()!=null){
                       listaTorreBlock=ub.TraeUnidadesXBlockTorreNoPago("", cmbTorre.getValue());
                    }
                    else{
                        listaTorreBlock=ub.TraeUnidadesXBlockTorreNoPago("", 0);
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
            UnidadBean ub=new UnidadBean();
            List<Unidad> listaTotal=ub.TraeUnidadesConvenioXBlockTorre("",0);
            listaUnidades = FXCollections.observableList(listaTotal);
            tblUnidades.setItems(null);
            tblUnidades.setItems(listaUnidades);
        }
        
        public void guardaConvenio(){
            try{
            UnidadBean ub= new UnidadBean();
            ub.actualizaGastosComunesAConvenios(unidad);
            }
            catch(Exception ex){
                System.err.println(ex.getMessage());
            }
        }
    
}
 
