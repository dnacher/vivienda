package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.TecnicoBean;
import ejb.utils.TecnicoImage;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.constantes.ConstantesMensajes;
import entities.enums.errores;
import entities.persistence.entities.Tecnico;
import exceptions.ServiceException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class BajaLienciaController implements Initializable {

    @FXML
    private TableView<TecnicoImage> tableData;

    @FXML
    private ProgressBar bar;

    @FXML
    private Label lblApellido;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblNombre;

    @FXML
    private ComboBox<String> cmbBajaLicencia;

    @FXML
    private DatePicker cmbFechaDesde;

    @FXML
    private DatePicker cmbFechaHasta;

    @FXML
    private Label lblHasta;

    @FXML
    private Label lblDesde;

    public ObservableList<TecnicoImage> lista;
    Tecnico tecnico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {			
            task();
        } catch (Exception ex) {
            Logger.getLogger(BajaLienciaController.class.getName()).log(Level.SEVERE, null, ex);
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
                    noVisible();
                    lista = FXCollections.observableArrayList(TecnicoImage.devuelveTecnicoConImagenEstado());
                    cargaTabla();
                    cargaComboBajaLicencia();
                } catch (ServiceException ex) {
                    Logger.getLogger(BajaLienciaController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        bar.progressProperty().bind(longTask.progressProperty());
        new Thread(longTask).start();
    }

    public void noVisible() {
        lblDesde.setVisible(false);
        lblHasta.setVisible(false);
        cmbBajaLicencia.setVisible(false);
        cmbFechaDesde.setVisible(false);
        cmbFechaHasta.setVisible(false);
    }

    public void guardar() {
        ControlVentana cv = new ControlVentana();
        //Licencia
        if (cmbBajaLicencia.getSelectionModel().getSelectedItem().equals(ConstantesEtiquetas.LICENCIA)) {
            if (cmbFechaDesde.getValue().isAfter(cmbFechaHasta.getValue())) {
                cv.creaVentanaNotificacion(errores.VERIFICAR.getError(), errores.FECHAFIN_MENOR_INICIO.getError(), 5, errores.ERROR.getError());
            } else {
                if (cmbFechaDesde.getValue().isBefore(LocalDate.now()) || cmbFechaHasta.getValue().isBefore(LocalDate.now())) {
                    cv.creaVentanaNotificacion(errores.VERIFICAR.getError(), errores.FECHAFIN_MENOR_INICIO.getError(), 5, errores.WARNING.getError());
                }
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle(ConstantesMensajes.CONFIRMA_LICENCIA);
                alert.setHeaderText(ConstantesEtiquetas.LICENCIA);
                alert.setContentText(ConstantesMensajes.DESEA_CONFIRMAR_LICENCIA);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource(Constantes.myDialogs).toExternalForm());
                dialogPane.getStyleClass().add(ConstantesEtiquetas.MY_DIALOG);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        tecnico.setEstado(2);
                        tecnico.setFechaInicioEstado(ConfiguracionControl.TraeFecha(cmbFechaDesde.getValue()));
                        tecnico.setFechaFinEstado(ConfiguracionControl.TraeFecha(cmbFechaHasta.getValue()));
                        TecnicoBean tb = new TecnicoBean();
                        tb.modificar(tecnico);
                        cv.creaVentanaNotificacionCorrecto();
                    } catch (ServiceException ex) {
                        cv.creaVentanaNotificacionError(ex.getMessage());
                        Logger.getLogger(BajaLienciaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //Baja
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(ConstantesEtiquetas.CONFIRMA_BAJA);
            alert.setHeaderText(ConstantesEtiquetas.BAJA);
            alert.setContentText(ConstantesEtiquetas.CONFIRMA_BAJA);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource(Constantes.myDialogs).toExternalForm());
            dialogPane.getStyleClass().add(ConstantesEtiquetas.MY_DIALOG);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    tecnico.setEstado(3);
                    tecnico.setFechaFinEstado(ConfiguracionControl.TraeFecha(cmbFechaHasta.getValue()));
                    tecnico.setActivo(false);
                    TecnicoBean tb = new TecnicoBean();
                    tb.modificar(tecnico);
                    cv.creaVentanaNotificacion(ConstantesEtiquetas.BAJA, ConstantesMensajes.BAJA_CORRECTO + cmbFechaHasta.getValue(), 3, ConstantesEtiquetas.OK);
                } catch (ServiceException ex) {
                    cv.creaVentanaNotificacionError(ex.getMessage());
                    Logger.getLogger(BajaLienciaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        llenaTabla();
    }

    public void creaDialogoConfirmacion() throws IOException {
        Stage stage = new Stage(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource(Constantes.PAGINA_DIALOG));
        Scene scene = new Scene(root);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        stage.setTitle(ConstantesEtiquetas.CONFIRMA);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void cargaTabla() {
        TableColumn Nombre = new TableColumn(ConstantesEtiquetas.NOMBRE);
        TableColumn Apellido = new TableColumn(ConstantesEtiquetas.APELLIDO);
        TableColumn Telefono = new TableColumn(ConstantesEtiquetas.TELEFONO);
        TableColumn Mail = new TableColumn(ConstantesEtiquetas.EMAIL);
        TableColumn Calificacion = new TableColumn(ConstantesEtiquetas.CALIFICACION);
        TableColumn Estado = new TableColumn(ConstantesEtiquetas.ESTADO);
        TableColumn FechaInicio = new TableColumn(ConstantesEtiquetas.FECHA_INICIO);
        TableColumn FechaFin = new TableColumn(ConstantesEtiquetas.FECHA_FIN);

        Nombre.setMinWidth(150);
        Nombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.NOMBRE));

        Apellido.setMinWidth(150);
        Apellido.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.APELLIDO));

        Telefono.setMinWidth(100);
        Telefono.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.TELEFONO));

        Mail.setMinWidth(100);
        Mail.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.MAIL));

        Calificacion.setMinWidth(110);
        Calificacion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.CALIFICACION));

        Estado.setMinWidth(100);
        Estado.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.ESTADO));

        FechaInicio.setMinWidth(100);
        FechaInicio.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.FECHA_INICIO_ESTADO));

        FechaFin.setMinWidth(100);
        FechaFin.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.FECHA_FIN_ESTADO));

        tableData.getColumns().addAll(Nombre, Apellido, Telefono, Mail, Calificacion, Estado, FechaInicio, FechaFin);
        tableData.setItems(lista);
    }

    public void llenaTabla() {
        try {
            lista = FXCollections.observableArrayList(TecnicoImage.devuelveTecnicoConImagenEstado());
            tableData.setItems(lista);
        } catch (ServiceException ex) {
            Logger.getLogger(BajaLienciaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargaComboBajaLicencia() {
        ObservableList<String> options = FXCollections.observableArrayList(Constantes.LISTA_LICENCIA_BAJA);
        cmbBajaLicencia.setItems(options);
        cmbBajaLicencia.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                managedCombos();
            }
        });
    }

    public void managedCombos() {
        lblDesde.setVisible(false);
        lblHasta.setVisible(false);
        cmbFechaDesde.setVisible(false);
        cmbFechaHasta.setVisible(false);
        switch (cmbBajaLicencia.getSelectionModel().getSelectedItem()) {
            case "Licencia":
                cmbFechaDesde.setVisible(true);
                cmbFechaHasta.setVisible(true);
                lblDesde.setVisible(true);
                lblHasta.setVisible(true);
                break;
            case "Baja":
                cmbFechaDesde.setVisible(false);
                cmbFechaHasta.setVisible(true);
                lblDesde.setVisible(false);
                lblHasta.setVisible(true);
                break;
        }
    }

    public void managedTabla() throws ServiceException {
        try {
            lblNombre.setText(ConstantesEtiquetas.VACIO);
            lblApellido.setText(ConstantesEtiquetas.VACIO);
            lblTelefono.setText(ConstantesEtiquetas.VACIO);
            TecnicoBean tb = new TecnicoBean();
            TecnicoImage ti = tableData.getSelectionModel().getSelectedItem();
            tecnico = tb.traerTecnicoXId(ti.getIdTecnico());
            lblNombre.setText(tecnico.getNombre());
            lblApellido.setText(tecnico.getApellido());
            lblTelefono.setText(tecnico.getTelefono());
            cmbBajaLicencia.setVisible(true);
        } catch (Exception ex) {
            ControlVentana cv = new ControlVentana();
            cv.creaVentanaNotificacionError(ConstantesErrores.DEBE_SELECCIONAR_TECNICO);
        }
    }
}
