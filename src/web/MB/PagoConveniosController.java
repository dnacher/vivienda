package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ConvenioBean;
import ejb.services.CuotaConvenioBean;
import ejb.services.GastosComunesBean;
import ejb.services.MontoBean;
import ejb.services.UnidadBean;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.constantes.ConstantesMensajes;
import entities.persistence.entities.Convenio;
import entities.persistence.entities.Cuotaconvenio;
import entities.persistence.entities.CuotaconvenioId;
import entities.persistence.entities.Gastoscomunes;
import entities.persistence.entities.GastoscomunesId;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Unidad;
import eu.hansolo.enzo.notification.Notification;
import exceptions.ServiceException;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class PagoConveniosController implements Initializable {

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
    private PieChart chartCuotas;

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
    private Label lblInfoPieChartCuotas;

    @FXML
    private Label lblSaldoRestante;

    @FXML
    private Label lblSugerido;

    @FXML
    private Label lblDescuento;

    @FXML
    private Label lblTotalPago;

    private Integer periodo;
    private Integer cuotasRestantes = 0;
    private Integer sugerido = 0;
    private Integer descuento = 0;
    private Integer totalAPagar = 0;
    private Double totalPagado = 0.0;
    private Double saldoRestante = 0.0;
    private Unidad unidad;
    private boolean guardado = false;
    private ObservableList<Unidad> unidadConvenios;
    private Convenio convenio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        task();
        try {
            lblTotalPago.setText("");
            cargaComboMonto();
            cargarComboBlock();
            cargarComboTorre();
            cargaHoy();
        } catch (ServiceException ex) {
            Logger.getLogger(PagoConveniosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmbMonedaListener();
        cmbMoneda.getSelectionModel().selectFirst();
    }

    public void cmbMonedaListener() {
        cmbMoneda.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                monedaCombo();
            }

        });
    }

    public int devuelveDeudaTotal() {
        CuotaConvenioBean ccb = new CuotaConvenioBean();
        totalPagado = Double.valueOf(ccb.devuelveTotalCuotas(unidad));
        int deudaTotal = (int) ((convenio.getDeudaTotal() - convenio.getSaldoInicial() - totalPagado));
        return deudaTotal;
    }

    public void listenerChkBonificacion() {
        chkBonificacion.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {                
                int deudaTotal = devuelveDeudaTotal();
                sugerido = (int) ((convenio.getDeudaTotal() - convenio.getSaldoInicial() - totalPagado) / convenio.getCuotas());
                if (chkBonificacion.isSelected()) {
                    descuento = ConfiguracionControl.calculaBonificacion(convenio.getReglabonificacion(), sugerido);
                } else {
                    descuento = 0;
                }
                totalAPagar = sugerido - descuento;
                lblSugerido.setText(ConstantesEtiquetas.SUBTOTAL + sugerido);
                txtMonto.setText(sugerido.toString());
                lblDescuento.setText(ConstantesEtiquetas.DESCUENTO + descuento);
                lblTotalPago.setText(ConstantesEtiquetas.TOTAL_A_PAGAR + totalAPagar);
                lblSaldoRestante.setText(ConstantesMensajes.SALDO_RESTANTE + deudaTotal);

                /* int montoAprox = ((convenio.getDeudaTotal() - convenio.getSaldoInicial()) / convenio.getCuotas());
                if (chkBonificacion.isSelected() && convenio.getReglabonificacion() != null) {
                    if (ConfiguracionControl.tieneBonificacion(convenio.getReglabonificacion())) {
                        descuento = ConfiguracionControl.calculaBonificacion(convenio.getReglabonificacion(), montoAprox);
                        sugerido = montoAprox - descuento;
                        if (sugerido > montoAprox) {                            
                            txtMonto.setText(sugerido.toString());
                            lblSugerido.setText("Sugerido        :" + sugerido);
                        } else {
                            calculaRestante();
                        }
                    } else {
                        sugerido = montoAprox;
                        txtMonto.setText(sugerido.toString());
                        lblSugerido.setText("Sugerido        :" + sugerido);
                    }
                } else {
                    sugerido = montoAprox;
                    txtMonto.setText(sugerido.toString());
                    lblSugerido.setText("Sugerido        :" + sugerido);
                }
                if (chkBonificacion.isSelected()) {
                    descuento = ConfiguracionControl.calculaBonificacion(convenio.getReglabonificacion(), sugerido);
                    //sugerido = sugerido - descuento;                    
                    lblDescuento.setText("Descuento      :" + descuento);
                    lblTotalPago.setText("Total a pagar  :" + sugerido);
                } else {
                    lblDescuento.setText("Descuento      :" + 0);
                    lblTotalPago.setText("Total a pagar  :" + sugerido);
                }*/
            }
        });
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
                UnidadBean ub = new UnidadBean();
                unidadConvenios = FXCollections.observableArrayList(ub.TraeUnidadesConConvenioXBlockTorre(ConstantesEtiquetas.VACIO, 0));
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
            CuotaConvenioBean cb = new CuotaConvenioBean();
            convenio = cb.traerConvenioXUnidad(unidad);
            lblPeriodo.setText(String.valueOf(periodo));
            listenerChkBonificacion();
            lblUnidadNombre.setText(unidad.getNombre() + ConstantesEtiquetas.ESPACIO + unidad.getApellido());
            lblUnidadDireccion.setText(unidad.getBlock() + unidad.getTorre() + "/ " + unidad.getPuerta());
            chkBonificacion.setSelected(ConfiguracionControl.esBonificacion(convenio.getReglabonificacion()));
            cargaGraficaCuotas();
            if (sugerido > 0) {
                paneGastosComunes.setOpacity(0);
                new FadeInUpTransition(paneFormulario).play();

            } else {
                try {
                    ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.CORRECTO, ConstantesMensajes.FELICITACIONES, Notification.SUCCESS_ICON));
                    convenio.setActivo(false);
                    ConvenioBean cbe = new ConvenioBean();
                    cbe.modificar(convenio);
                    UnidadBean ub = new UnidadBean();
                    unidadConvenios = FXCollections.observableArrayList(ub.TraeUnidadesConConvenioXBlockTorre(ConstantesEtiquetas.VACIO, 0));
                    llenaTabla();
                } catch (ServiceException ex) {
                    Logger.getLogger(PagoConveniosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            /*calculaRestante();
            if (saldoRestante > 0) {
                
            } else {
                ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.CORRECTO, ConstantesMensajes.FELICITACIONES, Notification.SUCCESS_ICON));
                convenio.setActivo(false);
                ConvenioBean cbe = new ConvenioBean();
                cbe.modificar(convenio);
                UnidadBean ub = new UnidadBean();
                unidadConvenios = FXCollections.observableArrayList(ub.TraeUnidadesConConvenioXBlockTorre(ConstantesEtiquetas.VACIO, 0));
                llenaTabla();
            }

            if (sugerido != -1) {
                if (sugerido < saldoRestante) {
                    txtMonto.setText(String.valueOf(sugerido));
                } else {
                    txtMonto.setText(saldoRestante.toString());
                }
            } else if (convenio != null) {
                int montoAprox = ((convenio.getDeudaTotal() - convenio.getSaldoInicial()) / convenio.getCuotas());
                if (montoAprox < saldoRestante) {
                    txtMonto.setText(String.valueOf(montoAprox));
                } else {
                    txtMonto.setText(saldoRestante.toString());
                }
            }*/
        } catch (Exception ex) {
            lblInfo.setText(ConstantesErrores.DEBE_SELECCIONAR_UNIDAD);
        }
    }

    public boolean tieneBonificacion() throws ServiceException {
        boolean tiene = false;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        ConvenioBean cb = new ConvenioBean();
        convenio = cb.traeConvenioXUnidad(unidad);
        int diaAPagar = convenio.getReglabonificacion().getDiaApagar();
        if (diaAPagar >= day) {
            tiene = true;
        }
        return tiene;
    }

    public void atras() {
        paneFormulario.setOpacity(0);
        new FadeInUpTransition(paneGastosComunes).play();
        Platform.runLater(() -> {
        });
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
        tableGastosComunes.setItems(unidadConvenios);
        cargaGrafica(ConstantesEtiquetas.VACIO, 0);
    }

    public void llenaTabla() {
        lblInfo.setText(ConstantesMensajes.SE_MUESTRAN + unidadConvenios.size() + ConstantesMensajes.REGISTROS);
        tableGastosComunes.setItems(unidadConvenios);
        if (guardado) {
            cargaGrafica(ConstantesEtiquetas.VACIO, 0);
        } else if (cmbBlock.getValue() != null && cmbTorre.getValue() != null) {
            cargaGrafica(cmbBlock.getValue(), cmbTorre.getValue());
        } else {
            cargaGrafica(ConstantesEtiquetas.VACIO, 0);
        }
    }

    public void cargaGrafica(String block, int torre) {
        lblInfoPieChart.setText(ConstantesEtiquetas.VACIO);
        UnidadBean ub = new UnidadBean();
        int totalSinConvenio = ub.TraeUnidadesConvenioCount();
        int totalConConvenio = unidadConvenios.size();
        int total = totalSinConvenio + totalConConvenio;

        ObservableList<PieChart.Data> lista = FXCollections.observableArrayList(
                new PieChart.Data(ConstantesMensajes.NO_CONVENIO, totalSinConvenio),
                new PieChart.Data(ConstantesMensajes.SI_CONVENIO, totalConConvenio)
        );
        chartGastosComunes.setData(lista);

        for (final PieChart.Data data : chartGastosComunes.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                int num = (int) data.getPieValue();
                double porc = (((double) num * (double) 100) / total);
                DecimalFormat df = new DecimalFormat(Constantes.DECIMAL_FORMAT_SHORT);
                df.setRoundingMode(RoundingMode.CEILING);
                String porcent = df.format(porc);
                lblInfoPieChart.setText(data.getName() + ConstantesEtiquetas.PUNTOS + num + ConstantesEtiquetas.PARENTESIS_ABIERTO + porcent + ConstantesEtiquetas.APROX);
            });
        }
    }

    public void cargaGraficaCuotas() {
        lblInfoPieChartCuotas.setText(ConstantesEtiquetas.VACIO);

        ConvenioBean cb = new ConvenioBean();
        convenio = cb.traeConvenioXUnidad(unidad);

        CuotaConvenioBean ccb = new CuotaConvenioBean();
        int cantidadCuotas = ccb.devuelveCantidadCuotas(unidad);
        cuotasRestantes = convenio.getCuotas() - cantidadCuotas;

        ObservableList<PieChart.Data> lista = FXCollections.observableArrayList(
                new PieChart.Data(ConstantesEtiquetas.A_PAGAR, cuotasRestantes),
                new PieChart.Data(ConstantesEtiquetas.PAGAS, cantidadCuotas)
        );
        chartCuotas.setData(lista);

        for (final PieChart.Data data : chartCuotas.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                int num = (int) data.getPieValue();
                double porc = (((double) num * (double) 100) / convenio.getCuotas());
                DecimalFormat df = new DecimalFormat(Constantes.DECIMAL_FORMAT_SHORT);
                df.setRoundingMode(RoundingMode.CEILING);
                String porcent = df.format(porc);
                lblInfoPieChartCuotas.setText(data.getName() + ConstantesEtiquetas.PUNTOS + num + ConstantesEtiquetas.PARENTESIS_ABIERTO + porcent + ConstantesEtiquetas.APROX);
            });
        }
    }

    public void mostrar(ActionEvent event) {
        try {
            lblInfo.setText(ConstantesEtiquetas.VACIO);
            String block;
            int torre;
            List<Unidad> listaTorreBlock;
            UnidadBean ub = new UnidadBean();
            if (cmbBlock.getValue() != null) {
                if (cmbTorre.getValue() != null) {
                    listaTorreBlock = ub.TraeUnidadesConvenioXBlockTorre(cmbBlock.getValue(), cmbTorre.getValue());
                    block = cmbBlock.getValue();
                    torre = cmbTorre.getValue();
                } else {
                    listaTorreBlock = ub.TraeUnidadesConvenioXBlockTorre(cmbBlock.getValue(), 0);
                    block = cmbBlock.getValue();
                    torre = 0;
                }
            } else if (cmbTorre.getValue() != null) {
                listaTorreBlock = ub.TraeUnidadesConvenioXBlockTorre(ConstantesEtiquetas.VACIO, cmbTorre.getValue());
                block = ConstantesEtiquetas.VACIO;
                torre = cmbTorre.getValue();
            } else {
                listaTorreBlock = ub.TraeUnidadesConvenioXBlockTorre(ConstantesEtiquetas.VACIO, 0);
                block = ConstantesEtiquetas.VACIO;
                torre = 0;
            }
            unidadConvenios = FXCollections.observableList(listaTorreBlock);
            llenaTabla();
            cargaGrafica(block, torre);
            lblInfo.setText(ConstantesMensajes.SE_MUESTRAN + unidadConvenios.size() + ConstantesMensajes.UNIDADES);
        } catch (Exception ex) {
            lblInfo.setText(ConstantesErrores.VALORES_BLOCK_TORRE);
        }
    }

    public void mostrarTodos() {
        UnidadBean ub = new UnidadBean();
        List<Unidad> listaTotal = ub.TraeUnidadesConvenioXBlockTorre(ConstantesEtiquetas.VACIO, 0);
        unidadConvenios = FXCollections.observableList(listaTotal);
        llenaTabla();
    }

    public boolean validar() {
        boolean correcto = false;
        if (!txtMonto.getText().isEmpty()) {
            correcto = true;
        }
        return correcto;
    }

    public void guardar() {
        ControlVentana cv = new ControlVentana();
        try {
            if (!txtMonto.getText().isEmpty()) {
                if (ConfiguracionControl.esNumero(txtMonto.getText())) {
                    int mont = Integer.valueOf(txtMonto.getText());
                    /*if (chkBonificacion.isSelected()) {
                        mont += descuento;
                    }*/
                    saldoRestante = Double.valueOf(devuelveDeudaTotal());                    
                    double total = (saldoRestante - mont);
                    if (total >= 0) {
                        Cuotaconvenio cuotaConvenio = new Cuotaconvenio();
                        cuotaConvenio.setConvenio(convenio);
                        //cuotaConvenio.setDescripcion("Nnnnnnnnnnnnnnnnnnnnnno hay descripcion");
                        CuotaconvenioId cuotaConvenioId = new CuotaconvenioId();
                        cuotaConvenioId.setConvenioIdconvenio(convenio.getId().getIdconvenio());
                        cuotaConvenioId.setConvenioUnidadIdUnidad(unidad.getIdUnidad());
                        cuotaConvenioId.setIdcuotaConvenio(ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.CUOTA_CONVENIO));
                        cuotaConvenioId.setMontoIdmonto(cmbMoneda.getValue().getIdmonto());
                        cuotaConvenio.setId(cuotaConvenioId);
                        cuotaConvenio.setMonto(cmbMoneda.getValue());
                        cuotaConvenio.setNumeroCuota(Integer.valueOf(lblPeriodo.getText()));
                        cuotaConvenio.setPago(mont);
                        cuotaConvenio.setTieneBonificacion(chkBonificacion.isSelected());
                        cuotaConvenio.setFechaPago(ConfiguracionControl.TraeFecha(cmbFecha.getValue()));
                        CuotaConvenioBean cb = new CuotaConvenioBean();
                        cb.guardar(cuotaConvenio);
                        cv.creaVentanaNotificacionCorrecto();
                        atras();
                    } else {
                        ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.SOBREPASA_DEUDA, Notification.ERROR_ICON));
                    }
                } else {
                    ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.MONTO_NUMERICO, Notification.ERROR_ICON));
                }
            }
        } catch (ServiceException ex) {
            cv.creaVentanaNotificacionError(ex.getMessage());
            Logger.getLogger(PagoConveniosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculaRestante() {
        int deudaTotal = convenio.getDeudaTotal();
        int saldoInicial = convenio.getSaldoInicial();
        CuotaConvenioBean ccb = new CuotaConvenioBean();
        //  totalPagado = ccb.devuelveTotalCuotas(unidad);

        if (cuotasRestantes > 0) {
            sugerido = (int) Math.ceil(saldoRestante / cuotasRestantes);
            if (chkBonificacion.isSelected()) {
                descuento = ConfiguracionControl.calculaBonificacion(convenio.getReglabonificacion(), sugerido);
                lblSugerido.setText("Sugerido        :" + sugerido);
                txtMonto.setText(sugerido.toString());
                sugerido -= descuento;
                if (sugerido < 0) {
                    sugerido = (int) Math.ceil(saldoRestante / cuotasRestantes);
                }
            }
        }
        saldoRestante = deudaTotal - saldoInicial - totalPagado;
        lblSaldoRestante.setText(ConstantesMensajes.SALDO_RESTANTE + saldoRestante);
    }

    public void anular() throws IOException {
        lblInfo.setText(ConstantesEtiquetas.VACIO);
        ControlVentana cv = new ControlVentana();
        creaDialogoConfirmacion();
        if (viviendas.Viviendas.confirmacion) {
            try {
                unidad = tableGastosComunes.getSelectionModel().getSelectedItem();
                if (unidad != null) {
                    CuotaConvenioBean cb = new CuotaConvenioBean();
                    convenio = cb.traerConvenioXUnidad(unidad);
                    calculaRestante();
                    convenio.setActivo(false);
                    ConvenioBean cb2 = new ConvenioBean();
                    cb2.modificar(convenio);
                    Gastoscomunes gc = new Gastoscomunes();
                    gc.setActivo(true);
                    gc.setEstado(1);
                    GastoscomunesId gci = new GastoscomunesId();
                    gci.setIdGastosComunes(ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.GASTOS_COMUNES));
                    gci.setUnidadIdUnidad(unidad.getIdUnidad());
                    gc.setId(gci);
                    gc.setIsBonificacion(false);
                    ConfiguracionControl cc = new ConfiguracionControl();
                    gc.setMonto(cc.traeMontoPesos());
                    gc.setMonto_1(saldoRestante.intValue());
                    gc.setPeriodo((ConfiguracionControl.devuelvePeriodoActual() - 1));
                    gc.setUnidad(unidad);
                    Date date = new Date(0000, 00, 00);
                    gc.setFechaPago(date);
                    GastosComunesBean gcb = new GastosComunesBean();
                    gcb.guardar(gc);
                    viviendas.Viviendas.confirmacion = false;
                    cv.creaVentanaNotificacionCorrecto();
                } else {
                    lblInfo.setText(ConstantesErrores.DEBE_SELECCIONAR_UNIDAD);
                }
            } catch (ServiceException ex) {
                cv.creaVentanaNotificacionError(ex.getMessage());
                Logger.getLogger(PagoConveniosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">    
    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getCuotasRestantes() {
        return cuotasRestantes;
    }

    public void setCuotasRestantes(Integer cuotasRestantes) {
        this.cuotasRestantes = cuotasRestantes;
    }

    public Integer getSugerido() {
        return sugerido;
    }

    public void setSugerido(Integer sugerido) {
        this.sugerido = sugerido;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Double getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(Double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public ObservableList<Unidad> getUnidadConvenios() {
        return unidadConvenios;
    }

    public void setUnidadConvenios(ObservableList<Unidad> unidadConvenios) {
        this.unidadConvenios = unidadConvenios;
    }

    public Integer getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(Integer totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public Double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(Double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    // </editor-fold>
}
