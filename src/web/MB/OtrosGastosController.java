package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ConceptoBean;
import ejb.services.MontoBean;
import ejb.services.OtrosGastosBean;
import ejb.services.UnidadBean;
import entities.enums.errores;
import entities.persistence.entities.Concepto;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Otrosgastos;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import ejb.validaciones.OtrosGastosViewValidation;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.constantes.ConstantesMensajes;
import entities.persistence.entities.OtrosgastosId;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import web.animations.FadeInUpTransition;

public class OtrosGastosController implements Initializable {

    @FXML
    private TextField txtMonto;

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private AnchorPane paneUnidades;

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private DatePicker cmbFecha;

    @FXML
    private TableView<Otrosgastos> tableData;

    @FXML
    private TextField txtSecuencia;

    @FXML
    private ComboBox<Monto> cmbTipoMoneda;

    @FXML
    private ProgressBar bar;

    @FXML
    private ComboBox<Concepto> cmbConcepto;

    @FXML
    private ComboBox<Integer> cmbTorre;

    @FXML
    private ComboBox<String> cmbBlock;

    @FXML
    private TableView<Unidad> tableUnidades;

    @FXML
    private Label lblInfo;

    @FXML
    private Label lblUnidad;

    @FXML
    private TextArea txtDescripcion;

    ObservableList<Unidad> listaUnidad;
    Unidad unidad;
    ObservableList<Otrosgastos> otrosGastos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            task();
        } catch (Exception ex) {
            Logger.getLogger(OtrosGastosController.class.getName()).log(Level.SEVERE, null, ex);
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
                    UnidadBean ub = new UnidadBean();
                    listaUnidad = FXCollections.observableArrayList(ub.traerTodos());
                    cargaTablaUnidades();
                    OtrosGastosBean og = new OtrosGastosBean();
                    otrosGastos = FXCollections.observableArrayList(og.traerTodos());
                    cargaTabla();
                    cargarComboTipoMoneda();
                    cmbTipoMoneda.getSelectionModel().selectFirst();
                    cargarComboConcepto();
                    cargaHoy();
                    mostrarTabla();
                } catch (ServiceException ex) {
                    Logger.getLogger(OtrosGastosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bar.progressProperty().bind(longTask.progressProperty());
        new Thread(longTask).start();
    }

    public void UnidadSeleccionada() {
        try {
            lblInfo.setText(ConstantesEtiquetas.VACIO);
            unidad = tableUnidades.getSelectionModel().getSelectedItem();
            nuevaUnidad();
            lblUnidad.setText(unidad.toString());
        } catch (Exception ex) {
            lblInfo.setText(ConstantesErrores.DEBE_SELECCIONAR_UNIDAD);
        }
    }

    public void cargaHoy() {
        Date date = new Date();
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cmbFecha.setValue(ld);
        ChkActivo.setSelected(true);
    }

    public void cargaTabla() {
        TableColumn Secuencia = new TableColumn(ConstantesEtiquetas.SECUENCIA_UPPER);
        TableColumn Descripcion = new TableColumn(ConstantesEtiquetas.DESCRIPCION_UPPER);
        TableColumn Monto = new TableColumn(ConstantesEtiquetas.MONEDA);
        TableColumn Monto1 = new TableColumn(ConstantesEtiquetas.MONTO);
        TableColumn Unidad = new TableColumn(ConstantesEtiquetas.UNIDAD);
        TableColumn Fecha = new TableColumn(ConstantesEtiquetas.FECHA_UPPER);

        Secuencia.setMinWidth(150);
        Secuencia.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.SECUENCIA_UPPER));

        Descripcion.setMinWidth(150);
        Descripcion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DESCRIPCION_UPPER));

        Monto.setMinWidth(100);
        Monto.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.MONTO));

        Monto1.setMinWidth(100);
        Monto1.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.MONTO_1));

        Unidad.setMinWidth(100);
        Unidad.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.UNIDAD));

        Fecha.setMinWidth(110);
        Fecha.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.FECHA_UPPER));

        tableData.getColumns().addAll(Secuencia, Unidad, Monto, Monto1, Descripcion, Fecha);
        tableData.setItems(otrosGastos);

    }

    public void cargarComboTipoMoneda() {
        try {
            MontoBean mb = new MontoBean();
            List<Monto> lista = mb.traerTodos();
            ObservableList<Monto> listaMonto;
            listaMonto = FXCollections.observableArrayList(lista);
            cmbTipoMoneda.setItems(listaMonto);
        } catch (ServiceException se) {
        }
    }

    public void cargarComboConcepto() {
        try {
            ConceptoBean cb = new ConceptoBean();
            List<Concepto> lista = cb.traerTodos();
            ObservableList<Concepto> listaConcepto;
            listaConcepto = FXCollections.observableArrayList(lista);
            cmbConcepto.setItems(listaConcepto);
        } catch (ServiceException se) {
        }
    }

    public void nuevaUnidad() {
        paneTabel.setOpacity(0);
        paneUnidades.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
        });
    }

    public void mostrarUnidad() {
        paneTabel.setOpacity(0);
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneUnidades).play();
    }

    public void mostrarTabla() {
        paneCrud.setOpacity(0);
        paneUnidades.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }

    public void guardar() {
        ControlVentana cv = new ControlVentana();
        switch (OtrosGastosViewValidation.validar(txtSecuencia, cmbTipoMoneda, txtMonto, cmbFecha, cmbConcepto, unidad)) {
            case 0:
                try {
                    Otrosgastos og = new Otrosgastos();
                    OtrosgastosId ogI = new OtrosgastosId();
                    ogI.setIdotrosGastos(ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.OTROS_GASTOS));
                    ogI.setUnidadIdUnidad(unidad.getIdUnidad());
                    og.setId(ogI);
                    og.setActivo(ChkActivo.isSelected());
                    og.setConcepto(cmbConcepto.getSelectionModel().getSelectedItem());
                    og.setDescripcion(txtDescripcion.getText());
                    og.setFecha(ConfiguracionControl.TraeFecha(cmbFecha.getValue()));
                    og.setMonto(cmbTipoMoneda.getSelectionModel().getSelectedItem());
                    og.setMonto_1(Integer.valueOf(txtMonto.getText()));
                    og.setPago(true);
                    og.setSecuencia(Integer.valueOf(txtSecuencia.getText()));
                    og.setUnidad(unidad);
                    OtrosGastosBean oga = new OtrosGastosBean();
                    oga.guardar(og);
                    cv.creaVentanaNotificacionCorrecto();
                    limpiarForm();
                    llenaTablaGastos();
                    mostrarTabla();
                } catch (Exception ex) {
                    cv.creaVentanaNotificacionError(ex.getMessage());
                }
                break;
            case 1:
                cv.creaVentanaNotificacionError(errores.FALTA_SECUENCIA.getError());
                break;
            case 2:
                cv.creaVentanaNotificacionError(errores.FALTA_TIPO_MONEDA.getError());
                break;
            case 3:
                cv.creaVentanaNotificacionError(errores.FALTA_MONTO.getError());
                break;
            case 4:
                cv.creaVentanaNotificacionError(errores.FALTA_FECHA.getError());
                break;
            case 5:
                cv.creaVentanaNotificacionError(errores.FALTA_CONCEPTO.getError());
                break;
            case 6:
                cv.creaVentanaNotificacionError(errores.FALTA_UNIDAD.getError());
                break;
        }
    }

    public void limpiarForm() {
        unidad = null;
        txtSecuencia.setText(ConstantesEtiquetas.VACIO);
        txtMonto.setText(ConstantesEtiquetas.VACIO);
        tableUnidades.getSelectionModel().clearSelection();
    }

    public void cargarComboBlock() {
        ObservableList<String> options
                = FXCollections.observableArrayList(Constantes.LISTA_BLOCKS);
        cmbBlock.setItems(options);
    }

    public void cargarComboTorre() {
        ObservableList<Integer> listaTorres;
        listaTorres = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6);
        cmbTorre.setItems(listaTorres);
    }

    public void cargaTablaUnidades() {
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

        tableUnidades.getColumns().addAll(Nombre, Apellido, Block, Torre, Puerta);
        tableUnidades.setItems(listaUnidad);
    }

    public void llenaTabla() {
        lblInfo.setText(ConstantesMensajes.SE_MUESTRAN + listaUnidad.size() + ConstantesMensajes.REGISTROS);
        tableUnidades.setItems(listaUnidad);
    }

    public void llenaTablaGastos() {
        try {
            OtrosGastosBean ogb = new OtrosGastosBean();
            otrosGastos = FXCollections.observableArrayList(ogb.traerTodos());
            tableData.setItems(otrosGastos);
        } catch (ServiceException ex) {
            Logger.getLogger(OtrosGastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrar(ActionEvent event) {
        try {
            lblInfo.setText(ConstantesEtiquetas.VACIO);
            UnidadBean ub = new UnidadBean();
            List<Unidad> listaTorreBlock = ub.TraeUnidadesXBlockTorreNoPago(cmbBlock.getValue(), cmbTorre.getValue());
            listaUnidad = FXCollections.observableList(listaTorreBlock);
            llenaTabla();
        } catch (Exception ex) {
            lblInfo.setText(ConstantesErrores.VALORES_BLOCK_TORRE);
        }
    }

    public void mostrarTodos() {
        UnidadBean ub = new UnidadBean();
        List<Unidad> listaTotal = ub.TraeUnidadesGastosComunesNoPago();
        listaUnidad = FXCollections.observableList(listaTotal);
        llenaTabla();
    }
}
