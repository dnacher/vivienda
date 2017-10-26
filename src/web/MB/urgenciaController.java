package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.UrgenciaBean;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.enums.MenuConfiguracion;
import entities.persistence.entities.Urgencia;
import exceptions.ServiceException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class urgenciaController implements Initializable {

	@FXML
	private AnchorPane paneCrud;

	@FXML
	private AnchorPane paneTabel;

	@FXML
	private TableView<Urgencia> tableData;

	@FXML
	private TextField txtNombre;

	@FXML
	private ProgressBar bar;

	@FXML
	private TextArea TxtDescripcion;

	@FXML
	private CheckBox ChkActivo;

	@FXML
	private Label LblNombre;
	
	@FXML
	private Button btnAgregar;

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	UrgenciaBean ub = new UrgenciaBean();
	List<Urgencia> lista;
	ObservableList<Urgencia> listaUrgencia;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		btnAgregar.setDisable(ConfiguracionControl.traePermisos(MenuConfiguracion.Urgencia.getPagina(), Constantes.PERMISO_OPERADOR));
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
				cargaTabla();
				atras();
			}
		});
		bar.progressProperty().bind(longTask.progressProperty());
		new Thread(longTask).start();
	}

	public void clear() {
		txtNombre.clear();
		TxtDescripcion.clear();
	}

	public void nuevaUrgencia() {
		paneTabel.setOpacity(0);
		new FadeInUpTransition(paneCrud).play();
	}

	public void atras() {
		paneCrud.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
	}

	public void guardaUrgencia() {
		LblNombre.setText(ConstantesEtiquetas.VACIO);
		ControlVentana cv = new ControlVentana();
		if (txtNombre.getText().isEmpty()) {
			LblNombre.setText(ConstantesErrores.FALTA_NOMBRE);
		} else {
			try {
				Urgencia urgencia = new Urgencia();
				int ind = ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.URGENCIA);
				urgencia.setIdurgencia(ind);
				urgencia.setActivo(ChkActivo.isSelected());
				urgencia.setNombre(txtNombre.getText());
				urgencia.setDescripcion(TxtDescripcion.getText());
				UrgenciaBean ub = new UrgenciaBean();
				ub.guardar(urgencia);
				cv.creaVentanaNotificacionCorrecto();
				clear();
				llenaTabla();
				atras();
			} catch (Exception ex) {
				cv.creaVentanaNotificacionError(ex.getMessage());
			}
		}

	}

	public void cargaTabla() {
		try {
			lista = ub.traerTodos();
			listaUrgencia = FXCollections.observableList(lista);
			TableColumn id = new TableColumn(ConstantesEtiquetas.ID_UPPER);
			TableColumn Nombre = new TableColumn(ConstantesEtiquetas.NOMBRE_UPPER);
			TableColumn Descripcion = new TableColumn(ConstantesEtiquetas.DESCRIPCION_UPPER);

			id.setMinWidth(100);
			id.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.ID_URGENCIA));

			Nombre.setMinWidth(100);
			Nombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.NOMBRE));
			Descripcion.setMinWidth(100);
			Descripcion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DESCRIPCION));
			tableData.getColumns().addAll(id, Nombre, Descripcion);
			tableData.setItems(listaUrgencia);
		} catch (ServiceException ex) {
			Logger.getLogger(urgenciaController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void llenaTabla() {
		try {
			ub = new UrgenciaBean();
			lista = ub.traerTodos();
			listaUrgencia = FXCollections.observableList(lista);
			tableData.setItems(listaUrgencia);
		} catch (ServiceException ex) {
			Logger.getLogger(estadoController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
