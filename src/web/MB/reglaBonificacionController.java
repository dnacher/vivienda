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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
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

    public ObservableList<Reglabonificacion> lista;

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
    }

    private void clear() {
        TxtDiasaPagar.clear();
        TxtDescripcion.clear();
    }

    @FXML
    private void aksiNew(ActionEvent event) {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();
        });
    }

    @FXML
    private void aksiSave(ActionEvent event) {
        ControlVentana cv = new ControlVentana();
        if (!ConfiguracionControl.esNumero(TxtDiasaPagar.getText())) {
            ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.DIAS_A_PAGAR_NUMERICO, Notification.ERROR_ICON));
        } else {
            try {
                Reglabonificacion reglaBonificacion = new Reglabonificacion();
                int ind = ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.REGLA_BONIFICACION);
                reglaBonificacion.setIdreglaBonificacion(ind);
                reglaBonificacion.setDescripcion(TxtDescripcion.getText());
                reglaBonificacion.setDiaApagar(Integer.valueOf(TxtDiasaPagar.getText()));
                reglaBonificacion.setTipoBonificacion(traeTipoBonificacion());
                reglaBonificacion.setValor(Integer.valueOf(txtValor.getText()));
                reglaBonificacion.setMonto(cmbMoneda.getValue());
                reglaBonificacion.setActivo(ChkActivo.isSelected());
                ReglaBonificacionBean rb = new ReglaBonificacionBean();
                rb.guardar(reglaBonificacion);
                cv.creaVentanaNotificacionCorrecto();
                clear();
                llenaTabla();
            } catch (Exception ex) {
                cv.creaVentanaNotificacionError(ex.getMessage());
            }
        }
    }

    public int traeTipoBonificacion() {
        switch (cmbTipoBonificacion.getValue()) {
            case "Valor":
                return 0;
            default:
                return 1;
        }
    }

    @FXML
    private void aksiBack(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }

    public void cargaTabla() {
        TableColumn Descripcion = new TableColumn(ConstantesEtiquetas.DESCRIPCION_UPPER);
        TableColumn DiaAPagar = new TableColumn(ConstantesEtiquetas.DIA_A_PAGAR);
        TableColumn Activo = new TableColumn(ConstantesEtiquetas.ACTIVO_UPPER);

        Descripcion.setMinWidth(150);
        Descripcion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DESCRIPCION));

        DiaAPagar.setMinWidth(150);
        DiaAPagar.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DIAPAGAR));

        Activo.setMinWidth(100);
        Activo.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.ACTIVO));

        tableData.getColumns().addAll(Descripcion, DiaAPagar, Activo);
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
        ObservableList<String> montos = FXCollections.observableArrayList(tipoBonificaciones);
        cmbTipoBonificacion.setItems(montos);
        cmbTipoBonificacion.getSelectionModel().selectFirst();
    }

}
