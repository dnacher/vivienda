package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.MontoBean;
import ejb.services.ReglaBonificacionBean;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Reglabonificacion;
import eu.hansolo.enzo.notification.Notification;
import exceptions.ServiceException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class reglaBonificacionController implements Initializable {

    @FXML
    private ComboBox<Monto> cmbMoneda;

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private ComboBox<String> cmbTipoBonificacion;

    @FXML
    private TextField txtValor;

    @FXML
    private TableView<Reglabonificacion> tableData;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;

    @FXML
    private TextField TxtDescripcion;

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private TextField TxtDiasaPagar;

    @FXML
    private Label lblValor;

    @FXML
    private Label lblHabitaciones;

    @FXML
    private Slider cmbHabitaciones;

    public ObservableList<Reglabonificacion> lista;
    Reglabonificacion rb;
    public boolean editar = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aksiBack(null);
        ReglaBonificacionBean rbb = new ReglaBonificacionBean();
        try {
            lista = FXCollections.observableArrayList(rbb.traerTodos());
            cargaTabla();
            cargaComboMonto();
            cargaTipoBonificacion();
        } catch (ServiceException ex) {
            Logger.getLogger(reglaBonificacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmbHabitaciones.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int i = (int) cmbHabitaciones.getValue();
                lblHabitaciones.setText(String.valueOf(i));
            }
        });
    }

    private void clear() {
        TxtDescripcion.setDisable(false);
        TxtDescripcion.clear();
        TxtDiasaPagar.clear();
        txtValor.clear();
        cmbHabitaciones.setValue((double) 1);
    }

    @FXML
    private void aksiNew(ActionEvent event) {
        if (tableData.getSelectionModel().getSelectedItem() != null) {
            rb = tableData.getSelectionModel().getSelectedItem();
            if (rb.getHabitaciones() != null && rb.getHabitaciones() > 0) {
                editar = true;
                TxtDescripcion.setText(rb.getDescripcion());
                TxtDiasaPagar.setText(rb.getDiaApagar().toString());
                cmbTipoBonificacion.setValue(traeValorTipoBonificacion(rb.getTipoBonificacion()));
                txtValor.setText(rb.getValor().toString());
                cmbMoneda.setValue(rb.getMonto());
                ChkActivo.setSelected(true);
                cmbHabitaciones.setValue((double) rb.getHabitaciones());
            } else {
                editar = false;
            }
        } else {
            editar = false;
        }
        TxtDescripcion.setDisable(editar);
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            if (!editar) {
                clear();
            }
        });
    }

    @FXML
    private void aksiSave(ActionEvent event) {
        ControlVentana cv = new ControlVentana();
        if (!ConfiguracionControl.esNumero(TxtDiasaPagar.getText())) {
            ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.DIAS_A_PAGAR_NUMERICO, Notification.ERROR_ICON));
        } else if (!ConfiguracionControl.esNumero(txtValor.getText())) {
            ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.INFO, ConstantesErrores.VALOR_NUMERICO, Notification.ERROR_ICON));
        } else if (Integer.valueOf(TxtDiasaPagar.getText()) > 0 && Integer.valueOf(TxtDiasaPagar.getText()) < 32) {
            try {
                if (Integer.valueOf(TxtDiasaPagar.getText()) > 29) {
                    ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.MESES_29, Notification.INFO_ICON));
                }
                Reglabonificacion reglaBonificacion = new Reglabonificacion();
                reglaBonificacion.setDescripcion(TxtDescripcion.getText());
                reglaBonificacion.setDiaApagar(Integer.valueOf(TxtDiasaPagar.getText()));
                reglaBonificacion.setTipoBonificacion(traeTipoBonificacion());
                reglaBonificacion.setValor(Integer.valueOf(txtValor.getText()));
                reglaBonificacion.setMonto(cmbMoneda.getValue());
                reglaBonificacion.setActivo(ChkActivo.isSelected());
                reglaBonificacion.setHabitaciones((int) cmbHabitaciones.getValue());
                ReglaBonificacionBean rbb = new ReglaBonificacionBean();
                if (editar) {
                    reglaBonificacion.setIdreglaBonificacion(rb.getIdreglaBonificacion());
                    rbb.modificar(reglaBonificacion);
                } else {
                    boolean unico = false;
                    if (cmbTipoBonificacion.getValue().equals("Habitaciones")) {
                        ReglaBonificacionBean reg = new ReglaBonificacionBean();
                        unico = reg.verificaUnicoHabitaciones(rb);
                    }
                    if (unico) {
                        int ind = ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.REGLA_BONIFICACION);
                        reglaBonificacion.setIdreglaBonificacion(ind);
                        rbb.guardar(reglaBonificacion);
                    } else {
                        ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.YA_EXISTE_HABITACION, Notification.ERROR_ICON));
                    }

                }
                cv.creaVentanaNotificacionCorrecto();
                clear();
                llenaTabla();
                aksiBack(null);
            } catch (ServiceException | NumberFormatException ex) {
                cv.creaVentanaNotificacionError(ex.getMessage());
            }
        } else {
            ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.FECHA_INCORRECTA, Notification.ERROR_ICON));
        }
    }

    public int traeTipoBonificacion() {
        switch (cmbTipoBonificacion.getValue()) {
            case "Valor":
                return 0;
            case "Porcentaje":
                return 1;
            default:
                return 2;
        }
    }

    public String traeValorTipoBonificacion(int tipo) {
        if (tipo == 0) {
            return "Valor";
        } else if (tipo == 1) {
            return "Porcentaje";
        } else {
            return "Habitaciones";
        }
    }

    @FXML
    private void aksiBack(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }

    public void cargaTabla() {
        TableColumn descripcion = new TableColumn(ConstantesEtiquetas.DESCRIPCION_UPPER);
        TableColumn diaAPagar = new TableColumn(ConstantesEtiquetas.DIA_A_PAGAR);
        TableColumn tipoBonidifcacion = new TableColumn(ConstantesEtiquetas.TIPO_BONIFICACION);
        TableColumn valor = new TableColumn(ConstantesEtiquetas.VALOR);
        TableColumn activo = new TableColumn(ConstantesEtiquetas.ACTIVO_UPPER);

        descripcion.setMinWidth(150);
        descripcion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DESCRIPCION));

        diaAPagar.setMinWidth(150);
        diaAPagar.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DIAPAGAR));

        tipoBonidifcacion.setMinWidth(50);
        tipoBonidifcacion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.TIPO_BONIFICACION_COLUMNA));

        valor.setMinWidth(150);
        valor.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.VALOR));

        activo.setMinWidth(100);
        activo.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.ACTIVO));

        tableData.getColumns().addAll(descripcion, diaAPagar, tipoBonidifcacion, valor, activo);
        tableData.setItems(lista);
    }

    public void llenaTabla() {
        try {
            ReglaBonificacionBean rbb = new ReglaBonificacionBean();
            lista = FXCollections.observableArrayList(rbb.traerTodos());
            tableData.setItems(lista);
        } catch (ServiceException ex) {
            Logger.getLogger(reglaBonificacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargaComboMonto() throws ServiceException {
        MontoBean mb = new MontoBean();
        ObservableList<Monto> montos = FXCollections.observableArrayList(mb.traerTodos());
        cmbMoneda.setItems(montos);
        cmbMoneda.getSelectionModel().selectFirst();
    }

    public void cargaTipoBonificacion() throws ServiceException {
        List<String> tipoBonificaciones = new ArrayList<>();
        tipoBonificaciones.add("Valor");
        tipoBonificaciones.add("Porcentaje");
        tipoBonificaciones.add("Habitaciones");
        ObservableList<String> montos = FXCollections.observableArrayList(tipoBonificaciones);
        cmbTipoBonificacion.setItems(montos);
        cmbTipoBonificacion.getSelectionModel().selectFirst();
        cmbTipoBonificacion.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (cmbTipoBonificacion.getValue() != null) {
                    switch (cmbTipoBonificacion.getValue()) {
                        case "Valor":
                            lblValor.setLayoutX(155.0);
                            lblValor.setText("Valor");
                            break;
                        case "Porcentaje":
                            lblValor.setLayoutX(155.0);
                            lblValor.setText("%");
                            break;
                        /*case "Habitaciones":
                            lblValor.setLayoutX(110.0);
                            lblValor.setText("Habitaciones");
                            break;*/
                    }
                }
            }
        });
    }

}
