package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.GastosComunesBean;
import ejb.services.MontoBean;
import ejb.services.UnidadBean;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.constantes.ConstantesMensajes;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Unidad;
import entities.persistence.entities.Gastoscomunes;
import entities.persistence.entities.GastoscomunesId;
import exceptions.ServiceException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class GastosComunesController implements Initializable {

    @FXML
    private TextField txtMonto;

    @FXML
    private ComboBox<Monto> cmbMoneda;

    @FXML
    private Label lblSimbolo;

    @FXML
    private TableView<Unidad> tableGastosComunes;

    @FXML
    private DatePicker cmbFecha;

    @FXML
    private AnchorPane paneFormulario;

    @FXML
    private PieChart chartGastosComunes;

    @FXML
    private ProgressBar bar;

    @FXML
    private ComboBox<Integer> cmbTorre;

    @FXML
    private AnchorPane paneGastosComunes;

    @FXML
    private CheckBox chkBonificacion;

    @FXML
    private ComboBox<String> cmbBlock;

    @FXML
    private Label lblInfoPieChart;

    @FXML
    private Label lblInfo;

    @FXML
    private Label lblPeriodo;

    @FXML
    private Label lblUnidadNombre;

    @FXML
    private Label lblUnidadDireccion;

    @FXML
    private DatePicker cmbFechaMes;

    int periodo;
    Unidad unidad;
    UnidadBean ub;
    GastosComunesBean gcb;
    boolean guardado = false;
    public ObservableList<Unidad> unidadesGastosComunesNoPago;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        task();
        try {
            cargaComboMonto();
            cargarComboBlock();
            cargarComboTorre();
            cargaHoy();
        } catch (ServiceException ex) {
            Logger.getLogger(GastosComunesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cmbMoneda.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                monedaCombo();
            }

        });
        cmbMoneda.getSelectionModel().selectFirst();
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
                unidadesGastosComunesNoPago = FXCollections.observableArrayList(ub.TraeUnidadesGastosComunesNoPago());
                cargaTabla();
                atras();
            }
        });
        bar.progressProperty().bind(longTask.progressProperty());
        new Thread(longTask).start();
    }

    public void cargaComboMonto() throws ServiceException {
        MontoBean mb = new MontoBean();
        ObservableList<Monto> montos = FXCollections.observableArrayList(mb.traerTodos());
        cmbMoneda.setItems(montos);
    }

    public void cargaHoy() {
        Date date = new Date();
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cmbFecha.setValue(ld);
    }

    public void monedaCombo() {
        Monto monto = cmbMoneda.getSelectionModel().getSelectedItem();
        lblSimbolo.setText(monto.getSimbolo());
    }

    public void agregarGastosComunes() {
        try {
            lblPeriodo.setText(ConstantesEtiquetas.VACIO);
            lblUnidadNombre.setText(ConstantesEtiquetas.VACIO);
            lblUnidadDireccion.setText(ConstantesEtiquetas.VACIO);
            periodo = ConfiguracionControl.devuelvePeriodoActual();
            unidad = tableGastosComunes.getSelectionModel().getSelectedItem();
            lblPeriodo.setText(String.valueOf(periodo));
            lblUnidadNombre.setText(unidad.getNombre() + ConstantesEtiquetas.ESPACIO
                    + unidad.getApellido());
            lblUnidadDireccion.setText(unidad.getBlock()
                    + unidad.getTorre() + "/ "
                    + unidad.getPuerta());
            paneGastosComunes.setOpacity(0);
            new FadeInUpTransition(paneFormulario).play();
        } catch (Exception ex) {
            lblInfo.setText(ConstantesErrores.DEBE_SELECCIONAR_UNIDAD);
        }
    }

    public void atras() {
        paneFormulario.setOpacity(0);
        new FadeInUpTransition(paneGastosComunes).play();
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
        cargaGrafica(ConstantesEtiquetas.VACIO, 0);
    }

    public void llenaTabla() {
        lblInfo.setText(ConstantesMensajes.SE_MUESTRAN + unidadesGastosComunesNoPago.size() + ConstantesMensajes.REGISTROS);
        tableGastosComunes.setItems(unidadesGastosComunesNoPago);
        if (guardado) {
            cargaGrafica(ConstantesEtiquetas.VACIO, 0);
        } else {
            if (cmbBlock.getValue() != null && cmbTorre.getValue() != null) {
                cargaGrafica(cmbBlock.getValue(), cmbTorre.getValue());
            } else {
                cargaGrafica(ConstantesEtiquetas.VACIO, 0);
            }
        }
    }

    public void cargaGrafica(String block, int torre) {
        lblInfoPieChart.setText(ConstantesEtiquetas.VACIO);
        ub = new UnidadBean();
        int total = ub.totalUnidades(block, torre);
        int totalPago = total - unidadesGastosComunesNoPago.size();
        int totalNoPago = total - totalPago;
        ObservableList<PieChart.Data> lista = FXCollections.observableArrayList(
                new PieChart.Data(ConstantesMensajes.NO_PAGO, totalNoPago),
                new PieChart.Data(ConstantesMensajes.PAGO, totalPago)
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
            int torre;
            List<Unidad> listaTorreBlock;
            ub = new UnidadBean();
            if (cmbBlock.getValue() != null) {
                if (cmbTorre.getValue() != null) {
                    listaTorreBlock = ub.TraeUnidadesXBlockTorreNoPago(cmbBlock.getValue(), cmbTorre.getValue());
                    block = cmbBlock.getValue();
                    torre = cmbTorre.getValue();
                } else {
                    listaTorreBlock = ub.TraeUnidadesXBlockTorreNoPago(cmbBlock.getValue(), 0);
                    block = cmbBlock.getValue();
                    torre = 0;
                }
            } else {
                if (cmbTorre.getValue() != null) {
                    listaTorreBlock = ub.TraeUnidadesXBlockTorreNoPago(ConstantesEtiquetas.VACIO, cmbTorre.getValue());
                    block = ConstantesEtiquetas.VACIO;
                    torre = cmbTorre.getValue();
                } else {
                    listaTorreBlock = ub.TraeUnidadesXBlockTorreNoPago(ConstantesEtiquetas.VACIO, 0);
                    block = ConstantesEtiquetas.VACIO;
                    torre = 0;
                }
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
            List<Unidad> listaTotal = ub.TraeUnidadesGastosComunesNoPago();
            unidadesGastosComunesNoPago = FXCollections.observableList(listaTotal);
            guardado = true;
            llenaTabla();
            guardado = false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean validar() {
        boolean correcto = false;
        if (!txtMonto.getText().isEmpty()) {
            correcto = true;
        }
        return correcto;
    }

    public void cerrarMes() {
        if (cmbFechaMes.getValue() != null) {
            try {
                int mes = cmbFechaMes.getValue().getMonth().getValue();
                int anio = cmbFechaMes.getValue().getYear();
                int periodoCerrar = ConfiguracionControl.traePeriodo(anio, mes);
                ub= new UnidadBean();
                List<Unidad> unis = ub.TraeUnidadesGastosComunesNoPagoXPeriodo(anio, mes);
                List<Gastoscomunes> listaGastos = new ArrayList<>();
                int id=ConfiguracionControl.traeUltimoId("GastosComunes");
                if (unis != null && unis.size() > 0) {
                    for (Unidad u : unis) {
                        Gastoscomunes gc = new Gastoscomunes();
                        GastoscomunesId gcId = new GastoscomunesId();
                        gcId.setIdGastosComunes(id);
                        gcId.setUnidadIdUnidad(u.getIdUnidad());
                        gc.setId(gcId);
                        gc.setUnidad(u);
                        gc.setMonto_1(999999999);
                        
                        gc.setActivo(true);
                        gc.setEstado(1);
                        gc.setIsBonificacion(false);
                        gc.setMonto(cmbMoneda.getSelectionModel().getSelectedItem());
                        gc.setPeriodo(periodoCerrar);
                        
                        listaGastos.add(gc);
                        id++;
                    }
                }
                for (Gastoscomunes gcom : listaGastos) {
                    gcb = new GastosComunesBean();
                    gcb.guardar(gcom);
                }
                System.out.println("termino");
            } catch (ServiceException ex) {
                System.out.println("Error");
                Logger.getLogger(GastosComunesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Error");
        }
    }

    public void guardar() {
        ControlVentana cv = new ControlVentana();
        if (validar()) {
            try {
                GastoscomunesId gcId = new GastoscomunesId();
                gcId.setIdGastosComunes(ConfiguracionControl.traeUltimoId("GastosComunes"));
                gcId.setUnidadIdUnidad(unidad.getIdUnidad());
                Gastoscomunes gc = new Gastoscomunes();
                gc.setActivo(true);
                gc.setEstado(2);
                gc.setFechaPago(ConfiguracionControl.TraeFecha(cmbFecha.getValue()));
                gc.setId(gcId);
                gc.setIsBonificacion(chkBonificacion.isSelected());
                gc.setMonto(cmbMoneda.getSelectionModel().getSelectedItem());
                gc.setMonto_1(Integer.valueOf(txtMonto.getText()));
                gc.setPeriodo(periodo);
                gc.setUnidad(unidad);
                gcb = new GastosComunesBean();
                gcb.guardar(gc);
                ConfiguracionControl cc = new ConfiguracionControl();
                HashMap parameters = new HashMap();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String str = format1.format(gc.getFechaPago());
                parameters.put("fechaGasto", str);
                parameters.put("idUnidad", gc.getUnidad().getIdUnidad());
                parameters.put("periodo", gc.getPeriodo());
                cc.generarReporteConParametros("GastosComunesImpresion", parameters);
                guardado = true;
                mostrarTodos();
                atras();
                /*   Mail mail=new Mail();
                String mimail="";
                mimail+="Nombre: " + gc.getUnidad().getNombre() + " " + gc.getUnidad().getApellido() + "\n";
                mimail+="Valor: " + "$" + gc.getMonto_1() + "\n";
                mimail+="Periodo: " + gc.getPeriodo() +"\n";
                mimail+="para valorar el tecnico ingresar al siguiente link: \n";
                mimail+="https://docs.google.com/forms/d/1s9lU03xaVxu2L0VIlrQ4Qj_cBpDO3o-CPsHy2IAIuTw/prefill" + "\n";
                
                mail.SendMail(mimail);*/
                cv.creaVentanaNotificacionCorrecto();
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
    }
}