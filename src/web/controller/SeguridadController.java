package web.controller;

import control.ControlVentana;
import ejb.services.TipoUsuarioBean;
import entities.persistence.entities.Permisosusuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import ejb.services.UsuariosBean;
import entities.constantes.ConstantesErrores;
import entities.persistence.entities.Tipousuario;
import exceptions.ServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import viviendas.Viviendas;

public class SeguridadController implements Initializable { 
    
    @FXML
    private ComboBox<Tipousuario> cmbTipoUsuario;
    
    // <editor-fold defaultstate="collapsed" desc=" Labels ">

    @FXML
    private Label lblListaPrecios;
    
    @FXML
    private Label lblCotizacion;
    
    @FXML
    private Label lblConcepto;
    
    @FXML
    private Label lblUrgencia;
    
    @FXML
    private Label lblTecnico;

    @FXML
    private Label lblSeguridad;
    
    @FXML
    private Label lblUnidades;
    
    @FXML
    private Label lblGastosComunes;
      
    @FXML
    private Label lblTipoUsuario; 
    
    @FXML
    private Label lblPagoConvenios;

    @FXML
    private Label lblReglaBonificacion;

    @FXML
    private Label lblEstado;
    
    @FXML
    private Label lblTipoDuracion;

    @FXML
    private Label lblMaterial;

    @FXML
    private Label lblGrupo;
    
    @FXML
    private Label lblCargasMasivas;

    @FXML
    private Label lblBajaLicencia;
    
    @FXML
    private Label lblUsuario;
    
    @FXML
    private Label lblConvenios;   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Check Box ">

        @FXML
        private CheckBox chkUnidadesVer;

        @FXML
        private CheckBox chkUnidadesAgregar;

        @FXML
        private CheckBox chkUnidadesBorrar;

        @FXML
        private CheckBox chkUnidadesEditar;

        @FXML
        private CheckBox chkUnidadesAdministrador;

        @FXML
        private CheckBox chkConveniosAdministrador;

        @FXML
        private CheckBox chkUsuarioAgregar;

        @FXML
        private CheckBox chkUsuarioEditar;

        @FXML
        private CheckBox chkMaterialVer;

        @FXML
        private CheckBox chkUrgenciaBorrar;

        @FXML
        private CheckBox chkConveniosVer;

        @FXML
        private CheckBox chkUrgenciaAdministrador;

        @FXML
        private CheckBox chkUsuarioBorrar;

        @FXML
        private CheckBox chkBajaLicenciaVer;

        @FXML
        private CheckBox chkCotizacionAdministrador;

        @FXML
        private CheckBox chkGrupoEditar;

        @FXML
        private CheckBox chkUrgenciaEditar;

        @FXML
        private CheckBox chkCotizacionBorrar;

        @FXML
        private CheckBox chkCotizacionEditar;

        @FXML
        private CheckBox chkTipoDuracionVer;    

        @FXML
        private CheckBox chkTecnicoAgregar;

        @FXML
        private CheckBox chkBajaLicenciaEditar;

        @FXML
        private CheckBox chkCotizacionVer;

        @FXML
        private CheckBox chkBajaLicenciaAdministrador;

        @FXML
        private CheckBox chkBajaLicenciaBorrar;

        @FXML
        private CheckBox chkReglaBonificacionBorrar;

        @FXML
        private CheckBox chkReglaBonificacionAgregar;

        @FXML
        private CheckBox chkReglaBonificacionEditar;

        @FXML
        private CheckBox chkPagoConvenioEditar;

        @FXML
        private CheckBox chkListaPreciosVer;

        @FXML
        private CheckBox chkListaPreciosAdministrador;

        @FXML
        private CheckBox chkBajaLicenciaAgregar;

        @FXML
        private CheckBox chkGrupoVer;

        @FXML
        private CheckBox chkUrgenciaVer;

        @FXML
        private CheckBox chkPagoConvenioBorrar;

        @FXML
        private CheckBox chkEstadoVer;

        @FXML
        private CheckBox chkSeguridadAdministrador;

        @FXML
        private CheckBox chkGastosComunesAgregar;

        @FXML
        private CheckBox chkPagoConvenioVer;

        @FXML
        private CheckBox chkGastosComunesEditar;

        @FXML
        private CheckBox chkTecnicoBorrar;

        @FXML
        private CheckBox chkConceptoBorrar;

        @FXML
        private CheckBox chkPagoConvenioAgregar;    

        @FXML
        private CheckBox chkTecnicoEditar;

        @FXML
        private CheckBox chkTipoDuracionAdministrador;

        @FXML
        private CheckBox chkEstadoAdministrador;

        @FXML
        private CheckBox chkCotizacionAgregar;

        @FXML
        private CheckBox chkEstadoEditar;

        @FXML
        private CheckBox chkMaterialAgregar;

        @FXML
        private CheckBox chkTipoDuracionAgregar;

        @FXML
        private CheckBox chkTecnicoAdministrador;

        @FXML
        private CheckBox chkGastosComunesAdministrador;

        @FXML
        private CheckBox chkListaPreciosAgregar;

        @FXML
        private CheckBox chkEstadoAgregar;    

        @FXML
        private CheckBox chkEstadoBorrar;

        @FXML
        private CheckBox chkConveniosAgregar;

        @FXML
        private CheckBox chkGastosComunesBorrar;

        @FXML
        private CheckBox chkGrupoAdministrador;

        @FXML
        private CheckBox chkConceptoAdministrador;

        @FXML
        private CheckBox chkTipoDuracionEditar;

        @FXML
        private CheckBox chkGrupoAgregar;

        @FXML
        private CheckBox chkListaPreciosBorrar;

        @FXML
        private CheckBox chkTipoDuracionBorrar;

        @FXML
        private CheckBox chkListaPreciosEditar;

        @FXML
        private CheckBox chkConveniosEditar;

        @FXML
        private CheckBox chkReglaBonificacionVer;

        @FXML
        private CheckBox chkTipoUsuarioAgregar;

        @FXML
        private CheckBox chkCargasMasivasAdministrador;   

        @FXML
        private CheckBox chkPagoConvenioAdministrador;

        @FXML
        private CheckBox chkTipoUsuarioAdministrador;   

        @FXML
        private CheckBox chkConceptoEditar;

        @FXML
        private CheckBox chkUsuarioAdministrador;

        @FXML
        private CheckBox chkReglaBonificacionAdministrador;

        @FXML
        private CheckBox chkTecnicoVer;

        @FXML
        private CheckBox chkMaterialBorrar;

        @FXML
        private CheckBox chkUsuarioVer;

        @FXML
        private CheckBox chkConveniosBorrar;

        @FXML
        private CheckBox chkTipoUsuarioEditar;

        @FXML
        private CheckBox chkMaterialEditar;

        @FXML
        private CheckBox chkConceptoVer;

        @FXML
        private CheckBox chkGrupoBorrar;

        @FXML
        private CheckBox chkTipoUsuarioBorrar;

        @FXML
        private CheckBox chkMaterialAdministrador;

        @FXML
        private CheckBox chkTipoUsuarioVer;

        @FXML
        private CheckBox chkConceptoAgregar;

        @FXML
        private CheckBox chkGastosComunesVer;

        @FXML
        private CheckBox chkUrgenciaAgregar;

        @FXML
        private CheckBox chkSeleccionarTodos;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Flags ">
        
        int flagUnidades = 0;
        int flagGastosComunes= 0;
        int flagConvenios = 0;
        int flagPagoConvenio = 0;
        int flagReglaBonificacion = 0;
        int flagTecnico = 0;
        int flagCotizacion = 0;
        int flagMaterial = 0;
        int flagBajaLicencia = 0;
        int flagListaPrecios = 0;
        int flagGrupo = 0;
        int flagUrgencia = 0;
        int flagConcepto = 0;
        int flagUsuario = 0;
        int flagTipoUsuario = 0;
        int flagTipoDuracion = 0;
        int flagEstado = 0;
        
    // </editor-fold>        
        
    // <editor-fold defaultstate="collapsed" desc=" permisos ">
        
            int Administracion=0;
            int BajaLicencia=0;
            int CargarMasivas=0;
            int Concepto=0;
            int Configuracion=0;
            int Convenios=0;
            int Cotizacion=0;
            int Estado=0;
            int GastosComunes=0;
            int Grupo=0;
            int Inicio=15;
            int ListaPrecios=0;
            int Mantenimiento=0;
            int Material=0;
            int PagoConvenios=0;
            int ReglaBonificacion=0;
            int Reportes=0;
            int Seguridad=0;
            int Tecnico=0;
            int TipoDuracion=0;
            int TipoUsuario=0;
            int Unidades=0;
            int Urgencia=0;
            int Usuario=0;
            
            List<Permisosusuario> listaPermisos=new ArrayList<>();
            Tipousuario tu=new Tipousuario();
            
        // </editor-fold>
        
        /*
        "Inicio"
        "Administracion"
        "Mantenimiento"
        "Reportes"
        "Configuracion"
        */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaComboTipoUsuario();
        nadaVisible();
        UsuariosBean ub=new UsuariosBean();        
        List<Permisosusuario> listaSeguridad=ub.TraePermisos(Viviendas.getTipoUsuario());
        recorreListaSeguridad(listaSeguridad);
    }
    
    public void cargaComboTipoUsuario(){
        try {
            TipoUsuarioBean tb=new TipoUsuarioBean();
            List<Tipousuario> listaTipoUsuarios = tb.traerTodos();
            ObservableList<Tipousuario>listaTipoUsuarioO = FXCollections.observableList(listaTipoUsuarios);
            cmbTipoUsuario.setItems(listaTipoUsuarioO);
        } catch (ServiceException ex) {
            Logger.getLogger(SeguridadController.class.getName()).log(Level.SEVERE, null, ex);
        }             
    }
    
    public void guardar(){
        
        ControlVentana cv=new ControlVentana(); 
        if(cmbTipoUsuario.getSelectionModel().getSelectedItem()!=null){
            tu=cmbTipoUsuario.getSelectionModel().getSelectedItem();
            cargaPermisos();
            try {
                UsuariosBean ub2=new UsuariosBean();
                ub2.EliminaPermisos(tu);
                Permisosusuario pu;
                if(Administracion!=-1){ pu= new Permisosusuario("Administracion", tu, Administracion); listaPermisos.add(pu);}
                if(BajaLicencia!=-1){pu= new Permisosusuario("BajaLicencia", tu, BajaLicencia); listaPermisos.add(pu);}
                if(CargarMasivas!=-1){pu= new Permisosusuario("CargarMasivas", tu, CargarMasivas); listaPermisos.add(pu);}
                if(Concepto!=-1){pu= new Permisosusuario("Concepto", tu, Concepto); listaPermisos.add(pu);}
                if(Configuracion!=-1){pu= new Permisosusuario("Configuracion", tu, Configuracion); listaPermisos.add(pu);}
                if(Convenios!=-1){pu= new Permisosusuario("Convenios", tu, Convenios); listaPermisos.add(pu);}
                if(Cotizacion!=-1){pu= new Permisosusuario("Cotizacion", tu, Cotizacion); listaPermisos.add(pu);}
                if(Estado!=-1){pu= new Permisosusuario("Estado", tu, Estado); listaPermisos.add(pu);}
                if(GastosComunes!=-1){pu= new Permisosusuario("GastosComunes", tu, GastosComunes); listaPermisos.add(pu);}
                if(Grupo!=-1){pu= new Permisosusuario("Grupo", tu, Grupo); listaPermisos.add(pu);}
                if(Inicio!=-1){pu= new Permisosusuario("Inicio", tu, Inicio); listaPermisos.add(pu);}
                if(ListaPrecios!=-1){pu= new Permisosusuario("ListaPrecios", tu, ListaPrecios); listaPermisos.add(pu);}
                if(Mantenimiento!=-1){pu= new Permisosusuario("Mantenimiento", tu, Mantenimiento); listaPermisos.add(pu);}
                if(Material!=-1){pu= new Permisosusuario("Material", tu, Material); listaPermisos.add(pu);}
                if(PagoConvenios!=-1){pu= new Permisosusuario("PagoConvenios", tu, PagoConvenios); listaPermisos.add(pu);}
                if(ReglaBonificacion!=-1){pu= new Permisosusuario("ReglaBonificacion", tu, ReglaBonificacion); listaPermisos.add(pu);}
                if(Reportes!=-1){pu= new Permisosusuario("Reportes", tu, Reportes); listaPermisos.add(pu);}
                if(Seguridad!=-1){pu= new Permisosusuario("Seguridad", tu, Seguridad); listaPermisos.add(pu);}
                if(Tecnico!=-1){pu= new Permisosusuario("Tecnico", tu, Tecnico); listaPermisos.add(pu);}
                if(TipoDuracion!=-1){pu= new Permisosusuario("TipoDuracion", tu, TipoDuracion); listaPermisos.add(pu);}
                if(TipoUsuario!=-1){pu= new Permisosusuario("TipoUsuario", tu, TipoUsuario); listaPermisos.add(pu);}
                if(Unidades!=-1){pu= new Permisosusuario("Unidades", tu, Unidades); listaPermisos.add(pu);}
                if(Urgencia!=-1){pu= new Permisosusuario("Urgencia", tu, Urgencia); listaPermisos.add(pu);}
                if(Usuario!=-1){pu= new Permisosusuario("Usuario", tu, Usuario); listaPermisos.add(pu);}
                UsuariosBean ub=new UsuariosBean();
                ub.guardaPermisos(listaPermisos);
                cv.creaVentanaNotificacionCorrecto();
                initialize(null, null);
            } catch (ServiceException ex) {
                Logger.getLogger(SeguridadController.class.getName()).log(Level.SEVERE, null, ex);
                cv.creaVentanaNotificacionError(ex.getMessage());
            }catch(Exception ex){
                Logger.getLogger(SeguridadController.class.getName()).log(Level.SEVERE, null, ex);
                cv.creaVentanaNotificacionError(ex.getMessage());
            }
        }else{
            cv.creaVentanaNotificacionError(ConstantesErrores.TIPO_USUARIO_VACIO);
        }
    }
    
    public void cargaPermisos(){
        
        // <editor-fold defaultstate="collapsed" desc=" Unidades ">
        
        if(chkUnidadesAdministrador.isSelected()){
            Unidades=15;
        }else{
            if(chkUnidadesVer.isSelected()){ Unidades+=1;}
            if(chkUnidadesEditar.isSelected()){Unidades+=8;}
            if(chkUnidadesBorrar.isSelected()){Unidades+=4;}
            if(chkUnidadesAgregar.isSelected()){Unidades+=2;}
        }
        
        // </editor-fold>
         
        // <editor-fold defaultstate="collapsed" desc=" GastosComunes ">  
         
            if(chkGastosComunesAdministrador.isSelected()){
                GastosComunes=15;
            }else{    
                if(chkGastosComunesVer.isSelected()){GastosComunes+=1;}
                if(chkGastosComunesEditar.isSelected()){GastosComunes+=8;}
                if(chkGastosComunesBorrar.isSelected()){GastosComunes+=4;}
                if(chkGastosComunesAgregar.isSelected()){GastosComunes+=2;}
            }
            
         // </editor-fold>         
         
        // <editor-fold defaultstate="collapsed" desc=" Convenios ">
        
            if(chkConveniosAdministrador.isSelected()){
                Convenios=15;
            }else{    
                if(chkConveniosVer.isSelected()){Convenios+=1;}
                if(chkConveniosEditar.isSelected()){Convenios+=8;}
                if(chkConveniosBorrar.isSelected()){Convenios+=4;}
                if(chkConveniosAgregar.isSelected()){Convenios+=2;}
            }
            
         // </editor-fold>         
         
        // <editor-fold defaultstate="collapsed" desc=" PagoConvenio ">
        
            if(chkPagoConvenioAdministrador.isSelected()){
                PagoConvenios=15;
            }else{
                if(chkPagoConvenioVer.isSelected()){PagoConvenios+=1;}
                if(chkPagoConvenioEditar.isSelected()){PagoConvenios+=8;}
                if(chkPagoConvenioBorrar.isSelected()){PagoConvenios+=4;}
                if(chkPagoConvenioAgregar.isSelected()){PagoConvenios+=2;}
            }
         
         // </editor-fold>         
         
        // <editor-fold defaultstate="collapsed" desc=" ReglaBonificacion ">
        
            if(chkReglaBonificacionAdministrador.isSelected()){
                ReglaBonificacion=15;
            }else{    
                if(chkReglaBonificacionVer.isSelected()){ReglaBonificacion+=1;}
                if(chkReglaBonificacionEditar.isSelected()){ReglaBonificacion+=8;}
                if(chkReglaBonificacionBorrar.isSelected()){ReglaBonificacion+=4;}
                if(chkReglaBonificacionAgregar.isSelected()){ReglaBonificacion+=2;}
            }
            
        // </editor-fold>
        
        if(Unidades+GastosComunes+Convenios+PagoConvenios+ReglaBonificacion>0){
            Administracion=15;
        }
                     
        // <editor-fold defaultstate="collapsed" desc=" Tecnico ">
         
            if(chkTecnicoAdministrador.isSelected()){
                Tecnico=15;
            }else{    
                if(chkTecnicoVer.isSelected()){Tecnico+=1;}
                if(chkTecnicoEditar.isSelected()){Tecnico+=8;}
                if(chkTecnicoBorrar.isSelected()){Tecnico+=4;}
                if(chkTecnicoAgregar.isSelected()){Tecnico+=2;}
            }
            
        // </editor-fold>
         
        // <editor-fold defaultstate="collapsed" desc=" Cotizacion ">
        
            if(chkCotizacionAdministrador.isSelected()){
                Cotizacion=15;
            }else{
                if(chkCotizacionVer.isSelected()){Cotizacion+=1;}
                if(chkCotizacionEditar.isSelected()){Cotizacion+=8;}
                if(chkCotizacionBorrar.isSelected()){Cotizacion+=4;}
                if(chkCotizacionAgregar.isSelected()){Cotizacion+=2;}
            }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Material ">
        
            if(chkCotizacionAdministrador.isSelected()){
                Cotizacion=15;
            }else{
                if(chkMaterialVer.isSelected()){Material+=1;}
                if(chkMaterialEditar.isSelected()){Material+=8;}
                if(chkMaterialBorrar.isSelected()){Material+=4;}
                if(chkMaterialAgregar.isSelected()){Material+=2;}
            }
            
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" BajaLicencia ">
        
            if(chkBajaLicenciaAdministrador.isSelected()){
                BajaLicencia=15;
            }else{
                if(chkBajaLicenciaVer.isSelected()){BajaLicencia+=1;}
                if(chkBajaLicenciaEditar.isSelected()){BajaLicencia+=8;}
                if(chkBajaLicenciaBorrar.isSelected()){BajaLicencia+=4;}
                if(chkBajaLicenciaAgregar.isSelected()){BajaLicencia+=2;}
            }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" ListaPrecios ">
        
            if(chkListaPreciosAdministrador.isSelected()){
                ListaPrecios=15;
            }else{
                if(chkListaPreciosVer.isSelected()){ListaPrecios+=1;}
                if(chkListaPreciosEditar.isSelected()){ListaPrecios+=8;}
                if(chkListaPreciosBorrar.isSelected()){ListaPrecios+=4;}
                if(chkListaPreciosAgregar.isSelected()){ListaPrecios+=2;}
            }
        // </editor-fold>
        
        if(Tecnico+Cotizacion+Material+BajaLicencia+ListaPrecios>0){
            Mantenimiento=15;
        }
        
        // <editor-fold defaultstate="collapsed" desc=" Grupo ">
        
            if(chkGrupoAdministrador.isSelected()){
                Grupo=15;
            }else{
                if(chkGrupoVer.isSelected()){Grupo+=1;}
                if(chkGrupoEditar.isSelected()){Grupo+=8;}
                if(chkGrupoBorrar.isSelected()){Grupo+=4;}
                if(chkGrupoAgregar.isSelected()){Grupo+=2;}
            }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Urgencia ">
        
            if(chkUrgenciaAdministrador.isSelected()){
                Urgencia=15;
            }else{
                if(chkUrgenciaVer.isSelected()){Urgencia+=1;}
                if(chkUrgenciaEditar.isSelected()){Urgencia+=8;}
                if(chkUrgenciaBorrar.isSelected()){Urgencia+=4;}
                if(chkUrgenciaAgregar.isSelected()){Urgencia+=2;}
            }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Concepto ">
        
            if(chkConceptoAdministrador.isSelected()){
                Concepto=15;
            }else{
                if(chkConceptoVer.isSelected()){Concepto+=1;}
                if(chkConceptoEditar.isSelected()){Concepto+=8;}
                if(chkConceptoBorrar.isSelected()){Concepto+=4;}
                if(chkConceptoAgregar.isSelected()){Concepto+=2;}
            }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Usuario ">
        
            if(chkUsuarioAdministrador.isSelected()){
                Usuario=15;
            }else{
                if(chkUsuarioVer.isSelected()){Usuario+=1;}
                if(chkUsuarioEditar.isSelected()){Usuario+=8;}
                if(chkUsuarioBorrar.isSelected()){Usuario+=4;}
                if(chkUsuarioAgregar.isSelected()){Usuario+=2;}
            }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" TipoUsuario ">
        
            if(chkTipoUsuarioAdministrador.isSelected()){
                TipoUsuario=15;
            }else{
                if(chkTipoUsuarioVer.isSelected()){TipoUsuario+=1;}
                if(chkTipoUsuarioEditar.isSelected()){TipoUsuario+=8;}
                if(chkTipoUsuarioBorrar.isSelected()){TipoUsuario+=4;}
                if(chkTipoUsuarioAgregar.isSelected()){TipoUsuario+=2;}
            }
            
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" TipoDuracion ">
        
            if(chkTipoDuracionAdministrador.isSelected()){
                TipoDuracion=15;
            }else{
                if(chkTipoDuracionVer.isSelected()){TipoDuracion+=1;}
                if(chkTipoDuracionEditar.isSelected()){TipoDuracion+=8;}
                if(chkTipoDuracionBorrar.isSelected()){TipoDuracion+=4;}
                if(chkTipoDuracionAgregar.isSelected()){TipoDuracion+=2;}
            }
        
            // </editor-fold>
            
        // <editor-fold defaultstate="collapsed" desc=" CargasMasivas ">
        
            if(chkCargasMasivasAdministrador.isSelected()){CargarMasivas=15;}
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Estado ">    
        
            if(chkEstadoAdministrador.isSelected()){
                Estado=15;
            }else{    
                if(chkEstadoVer.isSelected()){Estado+=1;}
                if(chkEstadoEditar.isSelected()){Estado+=8;}
                if(chkEstadoBorrar.isSelected()){Estado+=4;}
                if(chkEstadoAgregar.isSelected()){Estado+=2;}
            }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Seguridad ">  
            if(chkSeguridadAdministrador.isSelected()){Seguridad=15;}
        // </editor-fold>
        
        if(Grupo+Urgencia+Concepto+Usuario+TipoUsuario+TipoDuracion+CargarMasivas+Estado+Seguridad>0){
            Configuracion=15;
        }

    }
    
    // <editor-fold defaultstate="collapsed" desc=" Seguridad visibilidad ">

        public void recorreListaSeguridad(List<Permisosusuario> listaSeguridad){
        for(Permisosusuario pu:listaSeguridad){
            if(pu.getPermiso()==15){
                switch(pu.getPagina()){                    
                    case "BajaLicencia":
                        seguridadBajaLicencia(true);
                        break;
                    case "CargarMasivas":
                        seguridadMasivas(true);
                        break;
                    case "Concepto":
                        seguridadConcepto(true);                    
                        break;
                    case "Convenios":
                        seguridadConvenios(true);
                        break;
                    case "Cotizacion":
                        seguridadCotizacion(true);
                        break;
                    case "Estado":
                        seguridadEstado(true);
                        break;
                    case "GastosComunes":
                        seguridadGastosComunes(true);
                        break;
                    case "Grupo":
                        seguridadGrupo(true);
                        break;
                    case "ListaPrecios":
                        seguridadListaPrecios(true);
                        break;
                    case "Material":
                        seguridadMaterial(true);
                        break;
                    case "PagoConvenios":
                        seguridadPagoConvenio(true);
                        break;
                    case "ReglaBonificacion":
                        seguridadReglaBonificacion(true);
                        break;
                    case "Tecnico":
                        seguridadTecnico(true);
                        break;
                    case "TipoDuracion":
                        seguridadTipoDuracion(true);
                        break;
                    case "TipoUsuario":
                        seguridadTipoUsuario(true);
                        break;
                    case "Unidades":
                        seguridadUnidades(true);
                        break;
                    case "Urgencia":
                        seguridadUrgencia(true);
                        break;
                    case "Usuario":
                        seguridadUsuario(true);
                        break;
                    case "Seguridad":
                        seguridadSeguridad(true);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    public void nadaVisible(){
        seguridadBajaLicencia(false);                
        seguridadMasivas(false);
        seguridadConcepto(false);
        seguridadConvenios(false);
        seguridadCotizacion(false);
        seguridadEstado(false);
        seguridadGastosComunes(false);
        seguridadGrupo(false);
        seguridadListaPrecios(false);
        seguridadMaterial(false);
        seguridadPagoConvenio(false);
        seguridadReglaBonificacion(false);
        seguridadTecnico(false);
        seguridadTipoDuracion(false);
        seguridadTipoUsuario(false);
        seguridadUnidades(false);
        seguridadUrgencia(false);
        seguridadUsuario(false);
        seguridadSeguridad(false);
    }
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc=" Unidades ">
        public void seguridadUnidades(boolean tiene){        
            lblUnidades.setVisible(tiene);
            chkUnidadesVer.setVisible(tiene);
            chkUnidadesEditar.setVisible(tiene);
            chkUnidadesBorrar.setVisible(tiene);
            chkUnidadesAgregar.setVisible(tiene);
            chkUnidadesAdministrador.setVisible(tiene);
        }

        public void AdministraUnidades(){

            if(chkUnidadesAdministrador.isSelected()){
                managedUnidades();
                flagUnidades=1;
            }else if(!chkUnidadesAdministrador.isSelected() && flagUnidades==1){
                unmanagedUnidades();
                flagUnidades=0;
            }
            if(chkUnidadesAgregar.isSelected() || chkUnidadesBorrar.isSelected() || chkUnidadesEditar.isSelected()){
                chkUnidadesVer.setSelected(true);
            }
        }

        public void managedUnidades(){
            chkUnidadesVer.setSelected(true);
            chkUnidadesEditar.setSelected(true);
            chkUnidadesBorrar.setSelected(true);
            chkUnidadesAgregar.setSelected(true);
        }

        public void unmanagedUnidades(){
            chkUnidadesVer.setSelected(false);
            chkUnidadesEditar.setSelected(false);
            chkUnidadesBorrar.setSelected(false);
            chkUnidadesAgregar.setSelected(false);
        }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Gastos Comunes ">
        
        public void seguridadGastosComunes(boolean tiene){        
        lblGastosComunes.setVisible(tiene);
        chkGastosComunesVer.setVisible(tiene);
        chkGastosComunesEditar.setVisible(tiene);
        chkGastosComunesBorrar.setVisible(tiene);
        chkGastosComunesAgregar.setVisible(tiene);
        chkGastosComunesAdministrador.setVisible(tiene);
        }
    
        public void AdministraGastosComunes(){

            if(chkGastosComunesAdministrador.isSelected()){
                managedGastosComunes();
                flagGastosComunes=1;
            }else if(!chkGastosComunesAdministrador.isSelected() && flagGastosComunes==1){
                unmanagedGastosComunes();
                flagGastosComunes=0;
            }
            if(chkGastosComunesAgregar.isSelected() || chkGastosComunesBorrar.isSelected() || chkGastosComunesEditar.isSelected()){
                chkGastosComunesVer.setSelected(true);
            }
        }

        public void managedGastosComunes(){
            chkGastosComunesVer.setSelected(true);
            chkGastosComunesEditar.setSelected(true);
            chkGastosComunesBorrar.setSelected(true);
            chkGastosComunesAgregar.setSelected(true);
        }

        public void unmanagedGastosComunes(){
            chkGastosComunesVer.setSelected(false);
            chkGastosComunesEditar.setSelected(false);
            chkGastosComunesBorrar.setSelected(false);
            chkGastosComunesAgregar.setSelected(false);
        }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Convenios ">
    public void seguridadConvenios(boolean tiene){        
        lblConvenios.setVisible(tiene);
        chkConveniosVer.setVisible(tiene);
        chkConveniosEditar.setVisible(tiene);
        chkConveniosBorrar.setVisible(tiene);
        chkConveniosAgregar.setVisible(tiene);
        chkConveniosAdministrador.setVisible(tiene);
    }
    
    public void AdministraConvenios(){
        
        if(chkConveniosAdministrador.isSelected()){
            managedConvenios();
            flagConvenios=1;
        }else if(!chkConveniosAdministrador.isSelected() && flagConvenios==1){
            unmanagedConvenios();
            flagConvenios=0;
        }
        if(chkConveniosAgregar.isSelected() || chkConveniosBorrar.isSelected() || chkConveniosEditar.isSelected()){
            chkConveniosVer.setSelected(true);
        }
    }
    
    public void managedConvenios(){
        chkConveniosVer.setSelected(true);
        chkConveniosEditar.setSelected(true);
        chkConveniosBorrar.setSelected(true);
        chkConveniosAgregar.setSelected(true);
    }
    
    public void unmanagedConvenios(){
        chkConveniosVer.setSelected(false);
        chkConveniosEditar.setSelected(false);
        chkConveniosBorrar.setSelected(false);
        chkConveniosAgregar.setSelected(false);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Pago Convenios ">
    
        public void seguridadPagoConvenio(boolean tiene){        
        lblPagoConvenios.setVisible(tiene);
        chkPagoConvenioVer.setVisible(tiene);
        chkPagoConvenioEditar.setVisible(tiene);
        chkPagoConvenioBorrar.setVisible(tiene);
        chkPagoConvenioAgregar.setVisible(tiene);
        chkPagoConvenioAdministrador.setVisible(tiene);
    }
    
    public void AdministraPagoConvenio(){
        
        if(chkPagoConvenioAdministrador.isSelected()){
            managedPagoConvenio();
            flagPagoConvenio=1;
        }else if(!chkPagoConvenioAdministrador.isSelected() && flagPagoConvenio==1){
            unmanagedPagoConvenio();
            flagPagoConvenio=0;
        }
        if(chkPagoConvenioAgregar.isSelected() || chkPagoConvenioBorrar.isSelected() || chkPagoConvenioEditar.isSelected()){
            chkPagoConvenioVer.setSelected(true);
        }
    }
    
    public void managedPagoConvenio(){
        chkPagoConvenioVer.setSelected(true);
        chkPagoConvenioEditar.setSelected(true);
        chkPagoConvenioBorrar.setSelected(true);
        chkPagoConvenioAgregar.setSelected(true);
    }
    
    public void unmanagedPagoConvenio(){
        chkPagoConvenioVer.setSelected(false);
        chkPagoConvenioEditar.setSelected(false);
        chkPagoConvenioBorrar.setSelected(false);
        chkPagoConvenioAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Regla Bonificacion ">
    
        public void seguridadReglaBonificacion(boolean tiene){        
        lblReglaBonificacion.setVisible(tiene);
        chkReglaBonificacionVer.setVisible(tiene);
        chkReglaBonificacionEditar.setVisible(tiene);
        chkReglaBonificacionBorrar.setVisible(tiene);
        chkReglaBonificacionAgregar.setVisible(tiene);
        chkReglaBonificacionAdministrador.setVisible(tiene);
    }   
    
    public void AdministraReglaBonificacion(){
        
        if(chkReglaBonificacionAdministrador.isSelected()){
            managedReglaBonificacion();
            flagReglaBonificacion=1;
        }else if(!chkReglaBonificacionAdministrador.isSelected() && flagReglaBonificacion==1){
            unmanagedReglaBonificacion();
            flagReglaBonificacion=0;
        }
        if(chkReglaBonificacionAgregar.isSelected() || chkReglaBonificacionBorrar.isSelected() || chkReglaBonificacionEditar.isSelected()){
            chkReglaBonificacionVer.setSelected(true);
        }
    }
    
    public void managedReglaBonificacion(){
        chkReglaBonificacionVer.setSelected(true);
        chkReglaBonificacionEditar.setSelected(true);
        chkReglaBonificacionBorrar.setSelected(true);
        chkReglaBonificacionAgregar.setSelected(true);
    }
    
    public void unmanagedReglaBonificacion(){
        chkReglaBonificacionVer.setSelected(false);
        chkReglaBonificacionEditar.setSelected(false);
        chkReglaBonificacionBorrar.setSelected(false);
        chkReglaBonificacionAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Tecnico ">
    
           public void seguridadTecnico(boolean tiene){        
        lblTecnico.setVisible(tiene);
        chkTecnicoVer.setVisible(tiene);
        chkTecnicoEditar.setVisible(tiene);
        chkTecnicoBorrar.setVisible(tiene);
        chkTecnicoAgregar.setVisible(tiene);
        chkTecnicoAdministrador.setVisible(tiene);
    }
    
    public void AdministraTecnico(){
        
        if(chkTecnicoAdministrador.isSelected()){
            managedTecnico();
            flagTecnico=1;
        }else if(!chkTecnicoAdministrador.isSelected() && flagTecnico==1){
            unmanagedTecnico();
            flagTecnico=0;
        }
        if(chkTecnicoAgregar.isSelected() || chkTecnicoBorrar.isSelected() || chkTecnicoEditar.isSelected()){
            chkTecnicoVer.setSelected(true);
        }
    }
    
    public void managedTecnico(){
        chkTecnicoVer.setSelected(true);
        chkTecnicoEditar.setSelected(true);
        chkTecnicoBorrar.setSelected(true);
        chkTecnicoAgregar.setSelected(true);
    }
    
    public void unmanagedTecnico(){
        chkTecnicoVer.setSelected(false);
        chkTecnicoEditar.setSelected(false);
        chkTecnicoBorrar.setSelected(false);
        chkTecnicoAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Cotizacion ">

        public void seguridadCotizacion(boolean tiene){        
        lblCotizacion.setVisible(tiene);
        chkCotizacionVer.setVisible(tiene);
        chkCotizacionEditar.setVisible(tiene);
        chkCotizacionBorrar.setVisible(tiene);
        chkCotizacionAgregar.setVisible(tiene);
        chkCotizacionAdministrador.setVisible(tiene);
    }
    
    public void AdministraCotizacion(){
        
        if(chkCotizacionAdministrador.isSelected()){
            managedCotizacion();
            flagCotizacion=1;
        }else if(!chkCotizacionAdministrador.isSelected() && flagCotizacion==1){
            unmanagedCotizacion();
            flagCotizacion=0;
        }
        if(chkCotizacionAgregar.isSelected() || chkCotizacionBorrar.isSelected() || chkCotizacionEditar.isSelected()){
            chkCotizacionVer.setSelected(true);
        }
    }
    
    public void managedCotizacion(){
        chkCotizacionVer.setSelected(true);
        chkCotizacionEditar.setSelected(true);
        chkCotizacionBorrar.setSelected(true);
        chkCotizacionAgregar.setSelected(true);
    }
    
    public void unmanagedCotizacion(){
        chkCotizacionVer.setSelected(false);
        chkCotizacionEditar.setSelected(false);
        chkCotizacionBorrar.setSelected(false);
        chkCotizacionAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Material ">

        public void seguridadMaterial(boolean tiene){        
        lblMaterial.setVisible(tiene);
        chkMaterialVer.setVisible(tiene);
        chkMaterialEditar.setVisible(tiene);
        chkMaterialBorrar.setVisible(tiene);
        chkMaterialAgregar.setVisible(tiene);
        chkMaterialAdministrador.setVisible(tiene);
    }
    
    public void AdministraMaterial(){
        
        if(chkMaterialAdministrador.isSelected()){
            managedMaterial();
            flagMaterial=1;
        }else if(!chkMaterialAdministrador.isSelected() && flagMaterial==1){
            unmanagedMaterial();
            flagMaterial=0;
        }
        if(chkMaterialAgregar.isSelected() || chkMaterialBorrar.isSelected() || chkMaterialEditar.isSelected()){
            chkMaterialVer.setSelected(true);
        }
    }
    
    public void managedMaterial(){
        chkMaterialVer.setSelected(true);
        chkMaterialEditar.setSelected(true);
        chkMaterialBorrar.setSelected(true);
        chkMaterialAgregar.setSelected(true);
    }
    
    public void unmanagedMaterial(){
        chkMaterialVer.setSelected(false);
        chkMaterialEditar.setSelected(false);
        chkMaterialBorrar.setSelected(false);
        chkMaterialAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Baja Licencia ">
        
        public void seguridadBajaLicencia(boolean tiene){        
        lblBajaLicencia.setVisible(tiene);
        chkBajaLicenciaVer.setVisible(tiene);
        chkBajaLicenciaEditar.setVisible(tiene);
        chkBajaLicenciaBorrar.setVisible(tiene);
        chkBajaLicenciaAgregar.setVisible(tiene);
        chkBajaLicenciaAdministrador.setVisible(tiene);
    }
    
    public void AdministraBajaLicencia(){
        
        if(chkBajaLicenciaAdministrador.isSelected()){
            managedBajaLicencia();
            flagBajaLicencia=1;
        }else if(!chkBajaLicenciaAdministrador.isSelected() && flagBajaLicencia==1){
            unmanagedBajaLicencia();
            flagBajaLicencia=0;
        }
        if(chkBajaLicenciaAgregar.isSelected() || chkBajaLicenciaBorrar.isSelected() || chkBajaLicenciaEditar.isSelected()){
            chkBajaLicenciaVer.setSelected(true);
        }
    }
    
    public void managedBajaLicencia(){
        chkBajaLicenciaVer.setSelected(true);
        chkBajaLicenciaEditar.setSelected(true);
        chkBajaLicenciaBorrar.setSelected(true);
        chkBajaLicenciaAgregar.setSelected(true);
    }
    
    public void unmanagedBajaLicencia(){
        chkBajaLicenciaVer.setSelected(false);
        chkBajaLicenciaEditar.setSelected(false);
        chkBajaLicenciaBorrar.setSelected(false);
        chkBajaLicenciaAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" ListaPrecios ">

        public void seguridadListaPrecios(boolean tiene){        
        lblListaPrecios.setVisible(tiene);
        chkListaPreciosVer.setVisible(tiene);
        chkListaPreciosEditar.setVisible(tiene);
        chkListaPreciosBorrar.setVisible(tiene);
        chkListaPreciosAgregar.setVisible(tiene);
        chkListaPreciosAdministrador.setVisible(tiene);
    }
    
    public void AdministraListaPrecios(){
        
        if(chkListaPreciosAdministrador.isSelected()){
            managedListaPrecios();
            flagListaPrecios=1;
        }else if(!chkListaPreciosAdministrador.isSelected() && flagListaPrecios==1){
            unmanagedListaPrecios();
            flagListaPrecios=0;
        }
        if(chkListaPreciosAgregar.isSelected() || chkListaPreciosBorrar.isSelected() || chkListaPreciosEditar.isSelected()){
            chkListaPreciosVer.setSelected(true);
        }
    }
    
    public void managedListaPrecios(){
        chkListaPreciosVer.setSelected(true);
        chkListaPreciosEditar.setSelected(true);
        chkListaPreciosBorrar.setSelected(true);
        chkListaPreciosAgregar.setSelected(true);
    }
    
    public void unmanagedListaPrecios(){
        chkListaPreciosVer.setSelected(false);
        chkListaPreciosEditar.setSelected(false);
        chkListaPreciosBorrar.setSelected(false);
        chkListaPreciosAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Grupo ">

        public void seguridadGrupo(boolean tiene){        
        lblGrupo.setVisible(tiene);
        chkGrupoVer.setVisible(tiene);
        chkGrupoEditar.setVisible(tiene);
        chkGrupoBorrar.setVisible(tiene);
        chkGrupoAgregar.setVisible(tiene);
        chkGrupoAdministrador.setVisible(tiene);
    }
    
    public void AdministraGrupo(){
        
        if(chkGrupoAdministrador.isSelected()){
            managedGrupo();
            flagGrupo=1;
        }else if(!chkGrupoAdministrador.isSelected() && flagGrupo==1){
            unmanagedGrupo();
            flagGrupo=0;
        }
        if(chkGrupoAgregar.isSelected() || chkGrupoBorrar.isSelected() || chkGrupoEditar.isSelected()){
            chkGrupoVer.setSelected(true);
        }
    }
    
    public void managedGrupo(){
        chkGrupoVer.setSelected(true);
        chkGrupoEditar.setSelected(true);
        chkGrupoBorrar.setSelected(true);
        chkGrupoAgregar.setSelected(true);
    }
    
    public void unmanagedGrupo(){
        chkGrupoVer.setSelected(false);
        chkGrupoEditar.setSelected(false);
        chkGrupoBorrar.setSelected(false);
        chkGrupoAgregar.setSelected(false);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Urgencia ">
        
        public void seguridadUrgencia(boolean tiene){        
        lblUrgencia.setVisible(tiene);
        chkUrgenciaVer.setVisible(tiene);
        chkUrgenciaEditar.setVisible(tiene);
        chkUrgenciaBorrar.setVisible(tiene);
        chkUrgenciaAgregar.setVisible(tiene);
        chkUrgenciaAdministrador.setVisible(tiene);
    }
    
    public void AdministraUrgencia(){
        
        if(chkUrgenciaAdministrador.isSelected()){
            managedUrgencia();
            flagUrgencia=1;
        }else if(!chkUrgenciaAdministrador.isSelected() && flagUrgencia==1){
            unmanagedUrgencia();
            flagUrgencia=0;
        }
        if(chkUrgenciaAgregar.isSelected() || chkUrgenciaBorrar.isSelected() || chkUrgenciaEditar.isSelected()){
            chkUrgenciaVer.setSelected(true);
        }
    }
    
    public void managedUrgencia(){
        chkUrgenciaVer.setSelected(true);
        chkUrgenciaEditar.setSelected(true);
        chkUrgenciaBorrar.setSelected(true);
        chkUrgenciaAgregar.setSelected(true);
    }
    
    public void unmanagedUrgencia(){
        chkUrgenciaVer.setSelected(false);
        chkUrgenciaEditar.setSelected(false);
        chkUrgenciaBorrar.setSelected(false);
        chkUrgenciaAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Concepto ">

        public void seguridadConcepto(boolean tiene){        
        lblConcepto.setVisible(tiene);
        chkConceptoVer.setVisible(tiene);
        chkConceptoEditar.setVisible(tiene);
        chkConceptoBorrar.setVisible(tiene);
        chkConceptoAgregar.setVisible(tiene);
        chkConceptoAdministrador.setVisible(tiene);
    }
    
    public void AdministraConcepto(){
        
        if(chkConceptoAdministrador.isSelected()){
            managedConcepto();
            flagConcepto=1;
        }else if(!chkConceptoAdministrador.isSelected() && flagConcepto==1){
            unmanagedConcepto();
            flagConcepto=0;
        }
        if(chkConceptoAgregar.isSelected() || chkConceptoBorrar.isSelected() || chkConceptoEditar.isSelected()){
            chkConceptoVer.setSelected(true);
        }
    }
    
    public void managedConcepto(){
        chkConceptoVer.setSelected(true);
        chkConceptoEditar.setSelected(true);
        chkConceptoBorrar.setSelected(true);
        chkConceptoAgregar.setSelected(true);
    }
    
    public void unmanagedConcepto(){
        chkConceptoVer.setSelected(false);
        chkConceptoEditar.setSelected(false);
        chkConceptoBorrar.setSelected(false);
        chkConceptoAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Usuario ">

        public void seguridadUsuario(boolean tiene){        
        lblUsuario.setVisible(tiene);
        chkUsuarioVer.setVisible(tiene);
        chkUsuarioEditar.setVisible(tiene);
        chkUsuarioBorrar.setVisible(tiene);
        chkUsuarioAgregar.setVisible(tiene);
        chkUsuarioAdministrador.setVisible(tiene);
    }
    
    public void AdministraUsuario(){
        
        if(chkUsuarioAdministrador.isSelected()){
            managedUsuario();
            flagUsuario=1;
        }else if(!chkUsuarioAdministrador.isSelected() && flagUsuario==1){
            unmanagedUsuario();
            flagUsuario=0;
        }
        if(chkUsuarioAgregar.isSelected() || chkUsuarioBorrar.isSelected() || chkUsuarioEditar.isSelected()){
            chkUsuarioVer.setSelected(true);
        }
    }
    
    public void managedUsuario(){
        chkUsuarioVer.setSelected(true);
        chkUsuarioEditar.setSelected(true);
        chkUsuarioBorrar.setSelected(true);
        chkUsuarioAgregar.setSelected(true);
    }
    
    public void unmanagedUsuario(){
        chkUsuarioVer.setSelected(false);
        chkUsuarioEditar.setSelected(false);
        chkUsuarioBorrar.setSelected(false);
        chkUsuarioAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Tipo Usuario ">

        public void seguridadTipoUsuario(boolean tiene){        
        lblTipoUsuario.setVisible(tiene);
        chkTipoUsuarioVer.setVisible(tiene);
        chkTipoUsuarioEditar.setVisible(tiene);
        chkTipoUsuarioBorrar.setVisible(tiene);
        chkTipoUsuarioAgregar.setVisible(tiene);
        chkTipoUsuarioAdministrador.setVisible(tiene);
    }
    
    public void AdministraTipoUsuario(){
        
        if(chkTipoUsuarioAdministrador.isSelected()){
            managedTipoUsuario();
            flagTipoUsuario=1;
        }else if(!chkTipoUsuarioAdministrador.isSelected() && flagTipoUsuario==1){
            unmanagedTipoUsuario();
            flagTipoUsuario=0;
        }
        if(chkTipoUsuarioAgregar.isSelected() || chkTipoUsuarioBorrar.isSelected() || chkTipoUsuarioEditar.isSelected()){
            chkTipoUsuarioVer.setSelected(true);
        }
    }
    
    public void managedTipoUsuario(){
        chkTipoUsuarioVer.setSelected(true);
        chkTipoUsuarioEditar.setSelected(true);
        chkTipoUsuarioBorrar.setSelected(true);
        chkTipoUsuarioAgregar.setSelected(true);
    }
    
    public void unmanagedTipoUsuario(){
        chkTipoUsuarioVer.setSelected(false);
        chkTipoUsuarioEditar.setSelected(false);
        chkTipoUsuarioBorrar.setSelected(false);
        chkTipoUsuarioAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Tipo Duracion ">

        public void seguridadTipoDuracion(boolean tiene){        
        lblTipoDuracion.setVisible(tiene);
        chkTipoDuracionVer.setVisible(tiene);
        chkTipoDuracionEditar.setVisible(tiene);
        chkTipoDuracionBorrar.setVisible(tiene);
        chkTipoDuracionAgregar.setVisible(tiene);
        chkTipoDuracionAdministrador.setVisible(tiene);
    }
    
    public void AdministraTipoDuracion(){
        
        if(chkTipoDuracionAdministrador.isSelected()){
            managedTipoDuracion();
            flagTipoDuracion=1;
        }else if(!chkTipoDuracionAdministrador.isSelected() && flagTipoDuracion==1){
            unmanagedTipoDuracion();
            flagTipoDuracion=0;
        }
        if(chkTipoDuracionAgregar.isSelected() || chkTipoDuracionBorrar.isSelected() || chkTipoDuracionEditar.isSelected()){
            chkTipoDuracionVer.setSelected(true);
        }
    }
    
    public void managedTipoDuracion(){
        chkTipoDuracionVer.setSelected(true);
        chkTipoDuracionEditar.setSelected(true);
        chkTipoDuracionBorrar.setSelected(true);
        chkTipoDuracionAgregar.setSelected(true);
    }
    
    public void unmanagedTipoDuracion(){
        chkTipoDuracionVer.setSelected(false);
        chkTipoDuracionEditar.setSelected(false);
        chkTipoDuracionBorrar.setSelected(false);
        chkTipoDuracionAgregar.setSelected(false);
    }   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Masivas ">

        public void seguridadMasivas(boolean tiene){        
        lblCargasMasivas.setVisible(tiene);        
        chkCargasMasivasAdministrador.setVisible(tiene);
    }
    
    public void managedCargasMasivas(){
        chkCargasMasivasAdministrador.setSelected(true);       
    }
    
    public void unmanagedCargasMasivas(){
        chkCargasMasivasAdministrador.setSelected(false);        
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Estado ">

        public void seguridadEstado(boolean tiene){        
        lblEstado.setVisible(tiene);
        chkEstadoVer.setVisible(tiene);
        chkEstadoEditar.setVisible(tiene);
        chkEstadoBorrar.setVisible(tiene);
        chkEstadoAgregar.setVisible(tiene);
        chkEstadoAdministrador.setVisible(tiene);
    }
    
    public void AdministraEstado(){
        
        if(chkEstadoAdministrador.isSelected()){
            managedEstado();
            flagEstado=1;
        }else if(!chkEstadoAdministrador.isSelected() && flagEstado==1){
            unmanagedEstado();
            flagEstado=0;
        }
        if(chkEstadoAgregar.isSelected() || chkEstadoBorrar.isSelected() || chkEstadoEditar.isSelected()){
            chkEstadoVer.setSelected(true);
        }
    }
    
    public void managedEstado(){
        chkEstadoVer.setSelected(true);
        chkEstadoEditar.setSelected(true);
        chkEstadoBorrar.setSelected(true);
        chkEstadoAgregar.setSelected(true);
    }
    
    public void unmanagedEstado(){
        chkEstadoVer.setSelected(false);
        chkEstadoEditar.setSelected(false);
        chkEstadoBorrar.setSelected(false);
        chkEstadoAgregar.setSelected(false);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Seguridad ">

        public void seguridadSeguridad(boolean tiene){        
        lblSeguridad.setVisible(tiene);       
        chkSeguridadAdministrador.setVisible(tiene);
    }
    
    public void managedSeguridad(){
        chkSeguridadAdministrador.setSelected(true);       
    }
    
    public void unmanagedSeguridad(){
        chkSeguridadAdministrador.setSelected(false);        
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Todos ">
        
        public void chkTodos(){       
        Todos(chkSeleccionarTodos.isSelected());
    }
  
    public void Todos(boolean marcado){
        if(lblUnidades.isVisible()){
            chkUnidadesAdministrador.setSelected(marcado);     
            AdministraUnidades();
        }
        if(lblGastosComunes.isVisible()){
            chkGastosComunesAdministrador.setSelected(marcado);
            AdministraGastosComunes();
        }
        if(lblConvenios.isVisible()){
            chkConveniosAdministrador.setSelected(marcado);
            AdministraConvenios();
        }
        if(lblPagoConvenios.isVisible()){
            chkPagoConvenioAdministrador.setSelected(marcado);
            AdministraPagoConvenio();
        }
        if(lblReglaBonificacion.isVisible()){
            chkReglaBonificacionAdministrador.setSelected(marcado);
            AdministraReglaBonificacion();
        }
        if(lblTecnico.isVisible()){
            chkTecnicoAdministrador.setSelected(marcado);
            AdministraTecnico();
        }
        if(lblCotizacion.isVisible()){
            chkCotizacionAdministrador.setSelected(marcado);
            AdministraCotizacion();
        }
        if(lblMaterial.isVisible()){
            chkMaterialAdministrador.setSelected(marcado);
            AdministraMaterial();
        }
        if(lblBajaLicencia.isVisible()){
            chkBajaLicenciaAdministrador.setSelected(marcado);
            AdministraBajaLicencia();
        }
        if(lblListaPrecios.isVisible()){
            chkListaPreciosAdministrador.setSelected(marcado);
            AdministraListaPrecios();
        }
        if(lblGrupo.isVisible()){
            chkGrupoAdministrador.setSelected(marcado);
            AdministraGrupo();
        }
        if(lblUrgencia.isVisible()){
            chkUrgenciaAdministrador.setSelected(marcado);
            AdministraUrgencia();
        }
        if(lblConcepto.isVisible()){
            chkConceptoAdministrador.setSelected(marcado);
            AdministraConcepto();
        }
        if(lblUsuario.isVisible()){
            chkUsuarioAdministrador.setSelected(marcado);
            AdministraUsuario();
        }
        if(lblTipoUsuario.isVisible()){
            chkTipoUsuarioAdministrador.setSelected(marcado);
            AdministraTipoUsuario();
        }
        if(lblTipoDuracion.isVisible()){
            chkTipoDuracionAdministrador.setSelected(marcado);
            AdministraTipoDuracion();
        }
        if(lblEstado.isVisible()){
             chkEstadoAdministrador.setSelected(marcado);
             AdministraEstado();
        }
        if(lblCargasMasivas.isVisible()){
            chkCargasMasivasAdministrador.setSelected(marcado);
        }
        if(lblSeguridad.isVisible()){
            chkSeguridadAdministrador.setSelected(marcado);
        }
    }
    
    // </editor-fold>
    
} 
