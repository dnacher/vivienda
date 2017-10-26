package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.GrupoBean;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.enums.MenuAdministracion;
import entities.enums.MenuMantenimiento;
import entities.persistence.entities.Grupo;
import eu.hansolo.enzo.notification.Notification;
import exceptions.ServiceException;
import java.net.URL;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class grupoController implements Initializable {

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private TableView<Grupo> tableData;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;

    @FXML
    private TextArea TxtDescripcion;

    @FXML
    private CheckBox ChkActivo;

    @FXML
	private Button btnAgregar;

    public ObservableList<Grupo> lista;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
			btnAgregar.setDisable(ConfiguracionControl.traePermisos(MenuMantenimiento.Grupo.getPagina(), Constantes.PERMISO_OPERADOR));
            task();
            atras(null);
        } catch (Exception ex) {
            Logger.getLogger(grupoController.class.getName()).log(Level.SEVERE, null, ex);
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
                    GrupoBean gb = new GrupoBean();
                    lista = FXCollections.observableArrayList(gb.traerTodos());
                    cargaTabla();
                } catch (ServiceException ex) {
                    Logger.getLogger(grupoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bar.progressProperty().bind(longTask.progressProperty());
        new Thread(longTask).start();
    }

    private void clear() {
        txtNombre.clear();
        TxtDescripcion.clear();

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
        ControlVentana cv = new ControlVentana();
        if (txtNombre.getText().isEmpty()) {
            ConfiguracionControl.notifier.notify(new Notification("Correcto", ConstantesErrores.FALTA_NOMBRE, Notification.WARNING_ICON));
        } else {
            try {
                Grupo grupo = new Grupo();
                int ind = ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.GRUPO);
                grupo.setIdgrupo(ind);
                grupo.setActivo(ChkActivo.isSelected());
                grupo.setNombre(txtNombre.getText());
                grupo.setDescripcion(TxtDescripcion.getText());
                GrupoBean gb = new GrupoBean();
                gb.guardar(grupo);
                cv.creaVentanaNotificacionCorrecto();
                clear();
                llenaTabla();
                atras(null);
            } catch (Exception ex) {
                cv.creaVentanaNotificacionError(ex.getMessage());
            }
        }
    }

    @FXML
    private void atras(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }

    public void cargaTabla() {
        TableColumn Nombre = new TableColumn(ConstantesEtiquetas.NOMBRE_UPPER);
        TableColumn Descripcion = new TableColumn(ConstantesEtiquetas.DESCRIPCION_UPPER);
        TableColumn Activo = new TableColumn(ConstantesEtiquetas.ACTIVO_UPPER);

        Nombre.setMinWidth(150);
        Nombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.NOMBRE));

        Descripcion.setMinWidth(150);
        Descripcion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DESCRIPCION));

        Activo.setMinWidth(100);
        Activo.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.ACTIVO));

        tableData.getColumns().addAll(Nombre, Descripcion, Activo);
        tableData.setItems(lista);

    }

    public void llenaTabla() {
        try {
            GrupoBean gb = new GrupoBean();
            lista = FXCollections.observableArrayList(gb.traerTodos());
            tableData.setItems(lista);
        } catch (ServiceException ex) {
            Logger.getLogger(grupoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
