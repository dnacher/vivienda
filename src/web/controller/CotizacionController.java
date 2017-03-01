package web.controller;

import ejb.services.GrupoBean;
import ejb.services.TecnicoBean;
import ejb.services.TipoDuracionBean;
import ejb.services.UrgenciaBean;
import ejb.services.UnidadBean;
import ejb.services.EstadoBean;
import ejb.services.MaterialBean;
import ejb.utils.MaterialTrabajo;
import UtilsGeneral.ConfiguracionControl;
import entities.persistence.entities.Estado;
import entities.persistence.entities.Grupo;
import entities.persistence.entities.Tecnico;
import entities.persistence.entities.Tipoduracion;
import entities.persistence.entities.Trabajo;
import entities.persistence.entities.Unidad;
import entities.persistence.entities.Urgencia;
import entities.persistence.entities.Material;
import entities.persistence.entities.Trabajoxmaterial;
import exceptions.ServiceException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;
import control.ControlVentana;
import ejb.services.TrabajoBean;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;


public class CotizacionController implements Initializable {
    
    @FXML
    private AnchorPane paneFirst;

    @FXML
    private AnchorPane paneSecond;
    
    @FXML
    private AnchorPane paneThird;
    
    @FXML
    private ComboBox<Integer> cmbTorre;
    
    @FXML
    private ComboBox<String> cmbBlock;
    
    @FXML
    private Label lblInfo;
    
    @FXML
    private Label lblInfoMaterial;
    
    @FXML
    private TableView<Unidad> tblUnidades;
    
    @FXML
    private TableView<MaterialTrabajo> tblMaterial;
    
    @FXML
    private DatePicker cmbFechaVisita;
    
    @FXML
    private ComboBox<Tecnico> cmbTecnico;
    
    @FXML
    private TextField txtDuracion;
    
    @FXML
    private ComboBox<Tipoduracion> cmbTipoDuracion;
    
    @FXML
    private TextArea txtDescripcion;
    
    @FXML
    private ComboBox<Grupo> cmbGrupo;
    
    @FXML
    private ComboBox<Urgencia> cmbUrgencia;

    @FXML
    private ComboBox<Estado> cmbEstado;
    
    @FXML
    private ComboBox<Material> cmbMaterial;
    
    @FXML
    private CheckBox chkVerificaStock;
    
    @FXML
    private TextField txtCantidad;
    
    @FXML
    private TabPane tab;
    
    @FXML
    private Label lblUnidad;
    
    @FXML
    private Label lblUnidadEnEdicion;
    
    @FXML
    private ComboBox<Tecnico> cmbTecnicoEnEdicion;
    
    @FXML
    private ComboBox<Estado> cmbEstadoEnEdicion;
    
    @FXML
    private DatePicker cmbFechaEnEdicion;
    
    @FXML
    private TextArea txtDescripcionEnEdicion;
            
            
    
 
    ObservableList listaUnidades;
    Unidad unidad;
    Date hoy=new Date();
    List<MaterialTrabajo> materiales=new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblInfoMaterial.setText("");
        cargaTabla();
        cargaTablaMateriales();
        cargarComboBlock();
        cargarComboTorre();
        paneSecond.setVisible(false);
        paneThird.setVisible(false);
        cargaCombos();  
    }
    
    public void cargaCombos(){
        cargarComboTipoDuracion();
        cargarComboTecnico();
        cargarComboGrupo();
        cargarComboEstado();
        cargarComboUrgencia();
        cargarComboMaterial();
    }
    
    public void atras() {
        paneSecond.setOpacity(0);
        new FadeInUpTransition(paneFirst).play();
        limpiaForm();
    }
    
    public void limpiaForm(){
        unidad=null;
        tblUnidades.getSelectionModel().clearSelection();        
        cmbTecnico.getSelectionModel().clearSelection();
        cmbFechaVisita.setValue(null);
        cmbGrupo.getSelectionModel().clearSelection();
        cmbTipoDuracion.getSelectionModel().clearSelection();
        cmbUrgencia.getSelectionModel().clearSelection();
        txtCantidad.setText("");
        txtDescripcion.setText("");
        txtDuracion.setText("");
        tab.getSelectionModel().selectFirst();
        materiales=null;
        materiales=new ArrayList<>();
        tblMaterial.setItems(null);
    }
    
    public void atras2() {
        paneThird.setOpacity(0);
        new FadeInUpTransition(paneFirst).play();
        unidad=null;
    }    
  
    public void nuevoTrabajo(){       
        lblInfo.setText("");
        unidad=tblUnidades.getSelectionModel().getSelectedItem();
        lblUnidad.setText(unidad.getNombre() + " " + unidad.getApellido());
        if(unidad!=null){
            paneSecond.setVisible(true);
            paneFirst.setOpacity(0);
            new FadeInUpTransition(paneSecond).play();
        }else{
            lblInfo.setText("Debe seleccionar una Unidad");
        }            
    }
     
    public void editarTrabajo(){
        try{
            lblInfo.setText("");
            unidad=tblUnidades.getSelectionModel().getSelectedItem();
            TrabajoBean tbe=new TrabajoBean();
            if(tbe.UnidadesConTrabajoActivo(unidad)){
                paneThird.setVisible(true);
                paneFirst.setOpacity(0);        
                new FadeInUpTransition(paneThird).play();
                lblUnidadEnEdicion.setText(unidad.getNombre() + " " + unidad.getApellido());
                tbe=new TrabajoBean();
                Trabajo trabajo=tbe.traeTrabajo(unidad);
                cmbEstadoEnEdicion.getSelectionModel().select(trabajo.getEstado());
            }else{
                lblInfo.setText("La unidad no tiene trabajos activos");
            }            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            lblInfo.setText("Debe seleccionar una Unidad");
        }       
    }
     
    public void guardar(){
        ControlVentana cv=new ControlVentana();
        try{
            unidad=tblUnidades.getSelectionModel().getSelectedItem();
            Trabajo trabajo=new Trabajo();
            trabajo.setActivo(true);
            trabajo.setCalificacion(0);
            trabajo.setDescripcion(txtDescripcion.getText());
            trabajo.setDuracionEstimada(Integer.valueOf(txtDuracion.getText()));
            trabajo.setEstado(cmbEstado.getSelectionModel().getSelectedItem());
            trabajo.setFechaCreacion(hoy);
            trabajo.setFechaVisita(ConfiguracionControl.TraeFecha(cmbFechaVisita.getValue()));
            trabajo.setGrupo(cmbGrupo.getValue());
            trabajo.setIdTrabajo(ConfiguracionControl.traeUltimoId("Trabajo"));
            trabajo.setTipoduracion(cmbTipoDuracion.getValue());
            trabajo.setUnidad(unidad);
            trabajo.setUrgencia(cmbUrgencia.getValue());
            TrabajoBean tb=new TrabajoBean();
            tb.guardar(trabajo);
            //agrega materiales si es que hay
            if(materiales.size()>0){
                List<Trabajoxmaterial> trabajosxMateriales= new ArrayList<>();                
                MaterialBean mb=new MaterialBean();
                List<Material> lista=mb.traerTodos();
                List<Material> actualizarlista=new ArrayList<>();
                for(MaterialTrabajo mt: materiales){
                    for(Material m: lista){
                        if(mt.getNombre().equals(m.getNombre()) && mt.getDescripcion().equals(m.getDescripcion())){
                            Trabajoxmaterial tm=new Trabajoxmaterial();
                            tm.setCantidad(mt.getCantidad());
                            tm.setMaterial(m);
                            tm.setTrabajo(trabajo);
                            tm.setTrabajoIdTrabajo(trabajo.getIdTrabajo());
                            trabajosxMateriales.add(tm);
                            m.setSalida(tm.getCantidad());
                            m.setCantidad((m.getEntrada()-m.getSalida()));
                            actualizarlista.add(m);
                        }
                    }
                }
             tb=new TrabajoBean();
             tb.cargaMaterialesEnTrabajo(trabajosxMateriales);
             mb=new MaterialBean();
             mb.modificarTodos(actualizarlista);
            }
            cv.creaVentanaNotificacionCorrecto();            
        }
        catch(Exception ex){
            cv.creaVentanaNotificacionError(ex.getMessage());
        }       
    }
       
    public void cargarComboBlock(){
        ObservableList<String> options = FXCollections.observableArrayList("A","B","C","D","E");
        cmbBlock.setItems(options);
    }
    
    public void cargarComboTorre(){
        ObservableList<Integer> listaTorres;
        listaTorres= FXCollections.observableArrayList(1,2,3,4,5,6);
        cmbTorre.setItems(listaTorres);
    }
    
    public void cargarComboTipoDuracion(){
        try {
            ObservableList<Tipoduracion> listaTipoDuracion;
            TipoDuracionBean tdb= new TipoDuracionBean();
            listaTipoDuracion= FXCollections.observableArrayList(tdb.traerTodos());
            cmbTipoDuracion.setItems(listaTipoDuracion);
        } catch (ServiceException ex) {
            Logger.getLogger(CotizacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarComboGrupo(){
        try {
            ObservableList<Grupo> listaGrupo;
            GrupoBean gb= new GrupoBean();
            listaGrupo= FXCollections.observableArrayList(gb.traerTodos());
            cmbGrupo.setItems(listaGrupo);
        } catch (ServiceException ex) {
            Logger.getLogger(CotizacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarComboUrgencia(){
        try {
            ObservableList<Urgencia> listaUrgencia;
            UrgenciaBean ub= new UrgenciaBean();
            listaUrgencia= FXCollections.observableArrayList(ub.traerTodos());
            cmbUrgencia.setItems(listaUrgencia);
        } catch (ServiceException ex) {
            Logger.getLogger(CotizacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarComboEstado(){
        try {
            ObservableList<Estado> listaEstado;
            EstadoBean eb= new EstadoBean();
            listaEstado= FXCollections.observableArrayList(eb.traerTodos());
            cmbEstadoEnEdicion.setItems(listaEstado);
            cmbEstado.setItems(listaEstado);
            cmbEstado.getSelectionModel().selectFirst();
        } catch (ServiceException ex) {
            Logger.getLogger(CotizacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarComboTecnico(){
        try {
            ObservableList<Tecnico> listaTecnico;
            TecnicoBean tb= new TecnicoBean();
            listaTecnico= FXCollections.observableArrayList(tb.traerTodos());
            cmbTecnico.setItems(listaTecnico);
            cmbTecnicoEnEdicion.setItems(listaTecnico);
        } catch (ServiceException ex) {
            Logger.getLogger(CotizacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void mostrar(ActionEvent event) {        
        try{   
            lblInfo.setText("");
            List<Unidad> listaTorreBlock;
            UnidadBean ub= new UnidadBean();
            if(cmbBlock.getValue()!=null){
                if(cmbTorre.getValue()!=null){
                   listaTorreBlock=ub.TraeUnidadesXBlockTorre(cmbBlock.getValue(), cmbTorre.getValue());
                }
                else{
                    listaTorreBlock=ub.TraeUnidadesXBlockTorre(cmbBlock.getValue(), 0);
                }
            }else{
                if(cmbTorre.getValue()!=null){
                   listaTorreBlock=ub.TraeUnidadesXBlockTorre("", cmbTorre.getValue());
                }
                else{
                    listaTorreBlock=ub.TraeUnidadesXBlockTorre("", 0);
                }
            }                        
            listaUnidades = FXCollections.observableList(listaTorreBlock);
            tblUnidades.setItems(null);
            tblUnidades.setItems(listaUnidades);                
        }
        catch(ServiceException ex){
            lblInfo.setText(ex.getMessage());
        }
    }
       
    public void mostrarTodos() {
        try {
            lblInfo.setText("");
            UnidadBean ub=new UnidadBean();
            List<Unidad> listaTotal=ub.TraeUnidadesXBlockTorre("",0);
            listaUnidades = FXCollections.observableList(listaTotal);
            tblUnidades.setItems(null);
            tblUnidades.setItems(listaUnidades);
        } catch (ServiceException ex) {
            lblInfo.setText(ex.getMessage());
            Logger.getLogger(CotizacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            UnidadBean ub=new UnidadBean();
            List<Unidad> lista;
            lista = ub.TraeUnidadesXBlockTorre("",0);
            listaUnidades=FXCollections.observableList(lista);
            tblUnidades.setItems(listaUnidades);
        } catch (ServiceException ex) {
            Logger.getLogger(CotizacionController.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
    
    public void cargaTablaMateriales(){
       TableColumn Nombre = new TableColumn("Nombre");
       TableColumn Descripcion = new TableColumn("Descripcion");
       TableColumn Cantidad = new TableColumn("Cantidad");      

       Nombre.setMinWidth(150);
       Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

       Descripcion.setMinWidth(150);
       Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

       Cantidad.setMinWidth(100);
       Cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));      

       tblMaterial.getColumns().addAll(Nombre, Descripcion, Cantidad);              
    }
    
    public void agregarMaterial(){
        lblInfoMaterial.setText("");
        if(cmbMaterial.getSelectionModel().getSelectedItem()!=null){
            if(cmbMaterial.getSelectionModel().getSelectedItem().getCantidad()>=Integer.valueOf(txtCantidad.getText())){
                agregaItem();
            }else{
                if(!chkVerificaStock.isSelected()){
                    agregaItem();
                }else{
                    lblInfoMaterial.setText("El stock del item es: " + cmbMaterial.getSelectionModel().getSelectedItem().getCantidad());
                }
            }
        }
    }
    
    public void agregaItem(){
        lblInfo.setText("");
        MaterialTrabajo mt=new MaterialTrabajo();
        mt.setNombre(cmbMaterial.getSelectionModel().getSelectedItem().getNombre());
        mt.setDescripcion(cmbMaterial.getSelectionModel().getSelectedItem().getDescripcion());
        mt.setCantidad(Integer.valueOf(txtCantidad.getText()));
        boolean hay=false;
        for(MaterialTrabajo m: materiales){
            if(m.getNombre().equals(mt.getNombre()) && m.getDescripcion().equals(mt.getDescripcion())){
                hay=true;
            }
        }
        if(!hay){
            materiales.add(mt);
            ObservableList materialesO=FXCollections.observableList(materiales);
            tblMaterial.setItems(materialesO);
        }else{
            lblInfoMaterial.setText("Ya se ha agregado este item.");
        }
    }
    
    public void sacarMaterial(){
        if(tblMaterial.getSelectionModel().getSelectedItem()!=null){
            materiales.remove(tblMaterial.getSelectionModel().getSelectedItem());
            ObservableList materialesO=FXCollections.observableList(materiales);
            tblMaterial.setItems(materialesO);
        }
    }
    
}