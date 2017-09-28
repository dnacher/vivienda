package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.TecnicoBean;
import ejb.utils.TecnicoImage;
import entities.constantes.ConstantesEtiquetas;
import entities.persistence.entities.Tecnico;
import exceptions.ServiceException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import web.animations.FadeInUpTransition;

public class TecnicoController implements Initializable {

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private TableView<TecnicoImage> tableData;

    @FXML
    private Label LblNombre;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtApellido;

    @FXML
    private Label lblApellido;

    @FXML
    private Label lblTelefono;

    public ObservableList<TecnicoImage> lista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            task();
            atras();
        } catch (Exception ex) {
            Logger.getLogger(TecnicoController.class.getName()).log(Level.SEVERE, null, ex);
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
                    lista = FXCollections.observableArrayList(TecnicoImage.devuelveTecnicoConImagenEstado());
                    cargaTabla();
                    ChkActivo.setSelected(true);
                } catch (ServiceException ex) {
                    Logger.getLogger(TecnicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bar.progressProperty().bind(longTask.progressProperty());
        new Thread(longTask).start();
    }

    public void nuevoTecnico() {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
    }

    public void atras() {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }

    public void guardar() {
        if (validar() == 0) {
            ControlVentana cv = new ControlVentana();
            try {
                Tecnico tecnico = new Tecnico();
                tecnico.setNombre(txtNombre.getText());
                tecnico.setApellido(txtApellido.getText());
                tecnico.setTelefono(txtTelefono.getText());
                tecnico.setMail(txtMail.getText());
                tecnico.setIdTecnico(ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.TECNICO));
                tecnico.setEstado(1);
                tecnico.setActivo(ChkActivo.isSelected());
                Date date = new Date();
                tecnico.setFechaInicioEstado(date);
                TecnicoBean tb = new TecnicoBean();
                tb.guardar(tecnico);
                cv.creaVentanaNotificacionCorrecto();
                llenaTabla();
                limpiaForm();
            } catch (ServiceException ex) {
                cv.creaVentanaNotificacionError(ex.getMessage());
                Logger.getLogger(TecnicoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int validar() {
        int i = 0;
        normalizarLabels();
        if (txtNombre.getText().isEmpty()) {
            LblNombre.setTextFill(Color.RED);
            i = 1;
        }
        if (txtApellido.getText().isEmpty()) {
            lblApellido.setTextFill(Color.RED);
            i = 2;
        }
        if (txtTelefono.getText().isEmpty()) {
            lblTelefono.setTextFill(Color.RED);
            i = 3;
        }
        return i;
    }

    public void normalizarLabels() {
        LblNombre.setTextFill(Color.BLACK);
        lblApellido.setTextFill(Color.BLACK);
        lblTelefono.setTextFill(Color.BLACK);
    }

    public void cargaTabla() {
        TableColumn Nombre = new TableColumn(ConstantesEtiquetas.NOMBRE_UPPER);
        TableColumn Apellido = new TableColumn(ConstantesEtiquetas.APELLIDO_UPPER);
        TableColumn Telefono = new TableColumn(ConstantesEtiquetas.TELEFONO);
        TableColumn Mail = new TableColumn(ConstantesEtiquetas.EMAIL);
        TableColumn Calificacion = new TableColumn(ConstantesEtiquetas.CALIFICACION);
        TableColumn Estado = new TableColumn(ConstantesEtiquetas.ESTADO_UPPER);
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
            Logger.getLogger(TecnicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiaForm() {
        txtNombre.setText(ConstantesEtiquetas.VACIO);
        txtApellido.setText(ConstantesEtiquetas.VACIO);
        txtTelefono.setText(ConstantesEtiquetas.VACIO);
        txtMail.setText(ConstantesEtiquetas.VACIO);
    }

}
