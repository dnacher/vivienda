package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ListaPreciosBean;
import entities.persistence.entities.Listaprecios;
import entities.persistence.entities.ListapreciosId;
import entities.persistence.entities.Material;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import web.animations.FadeInUpTransition;
import UtilsGeneral.ListaPreciosTable;
import ejb.services.MaterialBean;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.enums.MenuMantenimiento;
import exceptions.ServiceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ListaPreciosMB implements Initializable {

    @FXML
    private CheckBox ChkActivo;

    @FXML
    private DatePicker cmbFechaCompra;

    @FXML
    private ComboBox<Material> cmbMaterial;

    @FXML
    private TableView<ListaPreciosTable> tableListaPrecios;

    @FXML
    private TextField txtPrecio;

    @FXML
    private ProgressBar bar;

    @FXML
    private TextField txtCantidad;

    @FXML
    private AnchorPane paneTable;

    @FXML
    private AnchorPane paneForm;
	
	@FXML
	private Button btnAgregar;

    public ObservableList<ListaPreciosTable> lista;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		btnAgregar.setDisable(ConfiguracionControl.traePermisos(MenuMantenimiento.ListaPrecios.getPagina(), Constantes.PERMISO_OPERADOR));
        task();
        atras();
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
                lista = FXCollections.observableArrayList(ListaPreciosTable.devuelveTodosPrecios());
                cargaTabla();
                cargarComboMaterial();
            }
        });
        bar.progressProperty().bind(longTask.progressProperty());
        new Thread(longTask).start();

    }

    public void cargarComboMaterial() {
        try {
            ObservableList<Material> listaMaterial;
            MaterialBean mb = new MaterialBean();
            listaMaterial = FXCollections.observableArrayList(mb.traerTodos());
            cmbMaterial.setItems(listaMaterial);
        } catch (ServiceException ex) {
            Logger.getLogger(CotizacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clear() {
        txtPrecio.clear();
        txtCantidad.clear();
    }

    public void atras() {
        paneForm.setOpacity(0);
        new FadeInUpTransition(paneTable).play();
    }

    public void nuevaListaPrecios() {
        paneTable.setOpacity(0);
        new FadeInUpTransition(paneForm).play();
    }

    public void guardar() {
        ControlVentana cv = new ControlVentana();

        if (!ConfiguracionControl.esNumero(txtCantidad.getText()) && !ConfiguracionControl.esNumero(txtPrecio.getText())) {
            cv.creaVentanaNotificacionError(ConstantesErrores.CAMPO_NUMERICO);
        } else {
            try {
                Material material = cmbMaterial.getSelectionModel().getSelectedItem();
                material.setEntrada(material.getEntrada() + Integer.valueOf(txtCantidad.getText()));
                material.setCantidad(material.getEntrada() - material.getSalida());
                MaterialBean mb = new MaterialBean();
                mb.modificar(material);
                Listaprecios listaPrecios = new Listaprecios();
                listaPrecios.setActivo(ChkActivo.isSelected());
                listaPrecios.setCantidad(Integer.valueOf(txtCantidad.getText()));
                listaPrecios.setFecha(ConfiguracionControl.TraeFecha(cmbFechaCompra.getValue()));
                ListapreciosId preciosId = new ListapreciosId();
                preciosId.setIdlistaPrecios(ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.LISTA_PRECIOS));
                preciosId.setMaterialIdmaterial(material.getIdmaterial());
                listaPrecios.setId(preciosId);
                listaPrecios.setMaterial(cmbMaterial.getSelectionModel().getSelectedItem());
                listaPrecios.setPrecio(Integer.valueOf(txtPrecio.getText()));
                ListaPreciosBean lpb = new ListaPreciosBean();
                lpb.guardar(listaPrecios);
                clear();
                llenaTabla();
                cv.creaVentanaNotificacionCorrecto();
            } catch (Exception ex) {
                cv.creaVentanaNotificacionError(ex.getMessage());
                cv.creaVentanaNotificacionError(ex.getMessage());
            }
        }
    }

    public void cargaTabla() {
        TableColumn Material = new TableColumn(ConstantesEtiquetas.MATERIAL);
        TableColumn Precio = new TableColumn(ConstantesEtiquetas.PRECIO);
        TableColumn Cantidad = new TableColumn(ConstantesEtiquetas.CANTIDAD);
        TableColumn Fecha = new TableColumn(ConstantesEtiquetas.FECHA);

        Material.setMinWidth(150);
        Material.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.MATERIAL));

        Precio.setMinWidth(150);
        Precio.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.PRECIO));

        Cantidad.setMinWidth(100);
        Cantidad.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.CANTIDAD));

        Fecha.setMinWidth(100);
        Fecha.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.FECHA));

        tableListaPrecios.getColumns().addAll(Material, Precio, Cantidad, Fecha);
        tableListaPrecios.setItems(lista);
    }

    public void llenaTabla() {
        lista = FXCollections.observableArrayList(ListaPreciosTable.devuelveTodosPrecios());
        tableListaPrecios.setItems(lista);
    }

}
