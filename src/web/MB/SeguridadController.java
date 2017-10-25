package web.MB;

import ejb.services.GrupoBean;
import ejb.services.TipoUsuarioBean;
import entities.constantes.ConstantesEtiquetas;
import entities.enums.MenuAdministracion;
import entities.persistence.entities.Grupo;
import entities.persistence.entities.Permisosusuario;
import entities.persistence.entities.Tipousuario;
import exceptions.ServiceException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class SeguridadController implements Initializable {

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private CheckBox chkAgregar;

    @FXML
    private CheckBox chkVer;

    @FXML
    private CheckBox chkAdministrador;

    @FXML
    private TableView<Tipousuario> tableData;

    @FXML
    private Button btnAgregar;

    @FXML
    private ProgressBar bar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnNew;

    @FXML
    private TableView<Permisosusuario> tblPermisos;

    @FXML
    private CheckBox chkActivo;

    @FXML
    private ComboBox<Tipousuario> cmbTipoUsuario;

    @FXML
    private ComboBox<String> cmbPagina;

    public ObservableList<Tipousuario> listaTipoUsuarios;
    public ObservableList<Permisosusuario> listaPermisos;
    public ObservableList<String> listaPaginas;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            task();
            atras(null);
        } catch (Exception ex) {
            Logger.getLogger(SeguridadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void task() {
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
                try {
                    TipoUsuarioBean tub = new TipoUsuarioBean();
                    listaTipoUsuarios = FXCollections.observableArrayList(tub.traerTodos());
                    cmbTipoUsuario.setItems(listaTipoUsuarios);
                    cargaTabla();
                    cargaComboPagina();
                } catch (ServiceException ex) {
                    Logger.getLogger(SeguridadController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bar.progressProperty().bind(longTask.progressProperty());
        new Thread(longTask).start();
    }

    private void cargaComboPagina() {  
        if(listaPaginas==null){
            listaPaginas=FXCollections.observableArrayList(new ArrayList());
        }
        for (MenuAdministracion p : MenuAdministracion.values()) {
            listaPaginas.add(p.getPagina());
        }       
        cmbPagina.setItems(listaPaginas);
    }

    private void clear() {

    }

    @FXML
    private void nuevoGrupo(ActionEvent event) {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();
        });
    }

    @FXML
    private void guardar(ActionEvent event) {
        //LblNombre.setText(ConstantesEtiquetas.VACIO);
//        ControlVentana cv = new ControlVentana();
//        if (txtNombre.getText().isEmpty()) {
//            ConfiguracionControl.notifier.notify(new Notification("Correcto", ConstantesErrores.FALTA_NOMBRE, Notification.WARNING_ICON));
//        } else {
//            try {
//                Grupo grupo = new Grupo();
//                int ind = ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.GRUPO);
//                grupo.setIdgrupo(ind);
//                grupo.setActivo(ChkActivo.isSelected());
//                grupo.setNombre(txtNombre.getText());
//                grupo.setDescripcion(TxtDescripcion.getText());
//                GrupoBean gb = new GrupoBean();
//                gb.guardar(grupo);
//                cv.creaVentanaNotificacionCorrecto();
//                clear();
//                llenaTabla();
//                atras(null);
//            } catch (Exception ex) {
//                cv.creaVentanaNotificacionError(ex.getMessage());
//            }
//        }
    }

    @FXML
    private void atras(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }

    public void cargaTabla() {
        TableColumn Nombre = new TableColumn(ConstantesEtiquetas.NOMBRE_UPPER);
        TableColumn Descripcion = new TableColumn(ConstantesEtiquetas.DESCRIPCION_UPPER);

        Nombre.setMinWidth(150);
        Nombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.NOMBRE));

        Descripcion.setMinWidth(150);
        Descripcion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DESCRIPCION));

        tableData.getColumns().addAll(Nombre, Descripcion);
        tableData.setItems(listaTipoUsuarios);

    }

    public void llenaTabla() {
//        try {
//            GrupoBean gb = new GrupoBean();
//            lista = FXCollections.observableArrayList(gb.traerTodos());
//            tableData.setItems(lista);
//        } catch (ServiceException ex) {
//            Logger.getLogger(SeguridadController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
