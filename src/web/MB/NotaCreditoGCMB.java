package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.GastosComunesBean;
import ejb.services.UnidadBean;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.constantes.ConstantesMensajes;
import entities.persistence.entities.Configuracion;
import entities.persistence.entities.Unidad;
import entities.persistence.entities.Gastoscomunes;
import entities.persistence.entities.Reglabonificacion;
import eu.hansolo.enzo.notification.Notification;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class NotaCreditoGCMB implements Initializable {  

    @FXML
    private TableView<Unidad> tableGastosComunes;

    @FXML
    private PieChart chartGastosComunes;

    @FXML
    private ProgressBar bar;

    @FXML
    private ComboBox<Integer> cmbTorre;

    @FXML
    private ComboBox<String> cmbBlock;

    @FXML
    private Label lblInfoPieChart;

    @FXML
    private Label lblInfo;

    public List<Configuracion> habitaciones;
    public List<Reglabonificacion> bonificaciones;
    public int periodo;
    public Unidad unidad;
    public UnidadBean ub;
    public GastosComunesBean gcb;
    public boolean guardado = false;
    public ObservableList<Unidad> unidadesGastosComunesNoPago;
    public Notification.Notifier notifier = Notification.Notifier.INSTANCE;
    public Reglabonificacion rb;
    public Configuracion configuracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        task();
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
                ub = new UnidadBean();
                unidadesGastosComunesNoPago = FXCollections.observableArrayList(ub.traeUnidadesXEstadoXBlockXTorre(null, null, true, Constantes.PAGO, ConfiguracionControl.devuelvePeriodoActual(), Constantes.COMPARA_EQUAL, false));
                cargaTabla();
                cargarComboBlock();
                cargarComboTorre();
            }
        });
        bar.progressProperty().bind(longTask.progressProperty());
        new Thread(longTask).start();
    }

    public void agregarGastosComunes() {
        try {
            unidad = tableGastosComunes.getSelectionModel().getSelectedItem();
            if (unidad != null) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle(ConstantesEtiquetas.CERRAR_MES);
                alert.setHeaderText("¿" + ConstantesEtiquetas.ESTA_SEGURO_GC + "?");

                ButtonType btnAceptar = new ButtonType(ConstantesEtiquetas.ACEPTAR);
                ButtonType btnCancelar = new ButtonType(ConstantesEtiquetas.CANCELAR, ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(btnAceptar, btnCancelar);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == btnAceptar) {
                    GastosComunesBean gcb = new GastosComunesBean();  
                    periodo= ConfiguracionControl.devuelvePeriodoActual();
                    Gastoscomunes gc = gcb.traerGCXUnidad(unidad, periodo);
                    gc.setEstado(Constantes.NOTA_CREDITO);
                    gcb = new GastosComunesBean();
                    gcb.modificar(gc);
                    ControlVentana cv= new ControlVentana();
                    cv.creaVentanaNotificacionCorrecto();
                }
            } else {
                lblInfo.setText(ConstantesErrores.DEBE_SELECCIONAR_UNIDAD);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            lblInfo.setText(ConstantesErrores.DEBE_SELECCIONAR_UNIDAD);
        }
    }

    public void cargarComboBlock() {
        ObservableList<String> options
                = FXCollections.observableArrayList(Constantes.LISTA_BLOCKS);
        cmbBlock.setItems(options);
    }

    public void cargarComboTorre() {
        ObservableList<Integer> listaTorres;
        listaTorres = FXCollections.observableArrayList(Constantes.LISTA_TORRES);
        cmbTorre.setItems(listaTorres);
    }

    public void cargaTabla() {
        TableColumn Nombre = new TableColumn(ConstantesEtiquetas.NOMBRE_UPPER);
        TableColumn Apellido = new TableColumn(ConstantesEtiquetas.APELLIDO_UPPER);
        TableColumn Block = new TableColumn(ConstantesEtiquetas.BLOCK_UPPER);
        TableColumn Torre = new TableColumn(ConstantesEtiquetas.TORRE_UPPER);
        TableColumn Puerta = new TableColumn(ConstantesEtiquetas.PUERTA_UPPER);

        Nombre.setMinWidth(150);
        Nombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.NOMBRE));

        Apellido.setMinWidth(150);
        Apellido.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.APELLIDO));

        Block.setMinWidth(100);
        Block.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.BLOCK_UPPER));

        Torre.setMinWidth(100);
        Torre.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.TORRE_UPPER));

        Puerta.setMinWidth(110);
        Puerta.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.PUERTA_UPPER));

        tableGastosComunes.getColumns().addAll(Nombre, Apellido, Block, Torre, Puerta);
        tableGastosComunes.setItems(unidadesGastosComunesNoPago);
        cargaGrafica(null, null);
    }

    public void llenaTabla() {
        lblInfo.setText(ConstantesMensajes.SE_MUESTRAN + unidadesGastosComunesNoPago.size() + ConstantesMensajes.REGISTROS);
        tableGastosComunes.setItems(unidadesGastosComunesNoPago);
        if (guardado) {
            cargaGrafica(null, null);
        } else if (cmbBlock.getValue() != null && cmbTorre.getValue() != null) {
            cargaGrafica(cmbBlock.getValue(), cmbTorre.getValue());
        } else {
            cargaGrafica(null, null);
        }
    }

    public void cargaGrafica(String block, Integer torre) {
        lblInfoPieChart.setText(ConstantesEtiquetas.VACIO);
        ub = new UnidadBean();
        int total = ub.traeUnidadesXEstadoXBlockXTorre(block, torre, Constantes.NOT_IN, Constantes.PAGO, ConfiguracionControl.devuelvePeriodoActual(), Constantes.COMPARA_EQUAL, Constantes.SIN_EDIFICIO).size();
        int totalPago = total - unidadesGastosComunesNoPago.size();
        int totalNoPago = total - totalPago;
        ObservableList<PieChart.Data> lista = FXCollections.observableArrayList(
                new PieChart.Data(ConstantesMensajes.PAGO, totalNoPago),
                new PieChart.Data(ConstantesMensajes.NO_PAGO, totalPago)
        );
        chartGastosComunes.setData(lista);

        for (final PieChart.Data data : chartGastosComunes.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                int num = (int) data.getPieValue();
                double porc = (((double) num * (double) 100) / total);
                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.CEILING);
                String porcent = df.format(porc);
                lblInfoPieChart.setText(data.getName() + ": " + num + " (" + porcent + " % aprox.)");
            });
        }
    }

    public void mostrar(ActionEvent event) {
        try {
            lblInfo.setText(ConstantesEtiquetas.VACIO);
            String block;
            Integer torre;
            List<Unidad> listaTorreBlock;
            ub = new UnidadBean();
            if (cmbBlock.getValue() != null) {
                if (cmbTorre.getValue() != null) {
                    listaTorreBlock = ub.traeUnidadesXEstadoXBlockXTorre(cmbBlock.getValue(), cmbTorre.getValue(), true, Constantes.PAGO, ConfiguracionControl.devuelvePeriodoActual(), Constantes.COMPARA_EQUAL, false);
                    block = cmbBlock.getValue();
                    torre = cmbTorre.getValue();
                } else {
                    listaTorreBlock = ub.traeUnidadesXEstadoXBlockXTorre(cmbBlock.getValue(), null, true, Constantes.PAGO, ConfiguracionControl.devuelvePeriodoActual(), Constantes.COMPARA_EQUAL, false);
                    block = cmbBlock.getValue();
                    torre = null;
                }
            } else if (cmbTorre.getValue() != null) {
                listaTorreBlock = ub.traeUnidadesXEstadoXBlockXTorre(null, cmbTorre.getValue(), true, Constantes.PAGO, ConfiguracionControl.devuelvePeriodoActual(), Constantes.COMPARA_EQUAL, false);
                block = null;
                torre = cmbTorre.getValue();
            } else {
                listaTorreBlock = ub.traeUnidadesXEstadoXBlockXTorre(null, null, true, Constantes.PAGO, ConfiguracionControl.devuelvePeriodoActual(), Constantes.COMPARA_EQUAL, false);
                block = null;
                torre = null;
            }
            unidadesGastosComunesNoPago = FXCollections.observableList(listaTorreBlock);
            tableGastosComunes.setItems(null);
            tableGastosComunes.setItems(unidadesGastosComunesNoPago);
            cargaGrafica(block, torre);
            lblInfo.setText("Se muestran " + unidadesGastosComunesNoPago.size() + " unidades");
        } catch (Exception ex) {
            lblInfo.setText(ConstantesErrores.VALORES_BLOCK_TORRE);
        }
    }

    public void mostrarTodos() {
        try {
            ub = new UnidadBean();
            List<Unidad> listaTotal = ub.traeUnidadesXEstadoXBlockXTorre(null, null, true, Constantes.PAGO, ConfiguracionControl.devuelvePeriodoActual(), Constantes.COMPARA_EQUAL, false);
            unidadesGastosComunesNoPago = FXCollections.observableList(listaTotal);
            guardado = true;
            llenaTabla();
            guardado = false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
