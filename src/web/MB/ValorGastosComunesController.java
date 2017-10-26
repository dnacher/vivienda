package web.MB;

import UtilsGeneral.ConfiguracionControl;
import ejb.services.ConfiguracionBean;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.constantes.ConstantesMensajes;
import entities.enums.MenuAdministracion;
import entities.persistence.entities.Configuracion;
import eu.hansolo.enzo.notification.Notification;
import exceptions.ServiceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;

public class ValorGastosComunesController implements Initializable {

	@FXML
	private AnchorPane paneCrud;

	@FXML
	private AnchorPane paneTabel;

	@FXML
	private TableView<Configuracion> tableData;

	@FXML
	private ProgressBar bar;

	@FXML
	private TextField txtValor;

	@FXML
	private Label lblHabitaciones;

	@FXML
	private Button btnAgregar;

	public ObservableList<Configuracion> lista;
	Notification.Notifier notifier = Notification.Notifier.INSTANCE;
	Configuracion configuracion;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			btnAgregar.setDisable(ConfiguracionControl.traePermisos(MenuAdministracion.ValorGastosComunes.getPagina(), Constantes.PERMISO_OPERADOR));
			task();
			aksiBack(null);
		} catch (Exception ex) {
			Logger.getLogger(ValorGastosComunesController.class.getName()).log(Level.SEVERE, null, ex);
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
					ConfiguracionBean cb = new ConfiguracionBean();
					List<Configuracion> listaConf = cb.traerValorGastosComunes();
					lista = FXCollections.observableArrayList(listaConf);
					cargaTabla();
				} catch (ServiceException ex) {
					Logger.getLogger(ValorGastosComunesController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		bar.progressProperty().bind(longTask.progressProperty());
		new Thread(longTask).start();
	}

	private void clear() {
		txtValor.clear();
	}

	@FXML
	private void aksiNew(ActionEvent event) {
		try {
			configuracion = tableData.getSelectionModel().getSelectedItem();
			if (configuracion != null) {
				paneTabel.setOpacity(0);
				new FadeInUpTransition(paneCrud).play();
				Platform.runLater(() -> {
					clear();
				});
				lblHabitaciones.setText(configuracion.getNombreTabla());
				txtValor.setText(configuracion.getId().toString());
			} else {
				notifier.notify(new Notification("Verificar", ConstantesErrores.DEBE_SELECIONAR, Notification.WARNING_ICON));
			}
		} catch (Exception ex) {
			notifier.notify(new Notification("Verificar", ConstantesErrores.DEBE_SELECIONAR, Notification.WARNING_ICON));
		}

	}

	@FXML
	private void aksiSave(ActionEvent event) {
		try {
			if (verificaNumerico()) {
				ConfiguracionBean cb = new ConfiguracionBean();
				configuracion.setId(Integer.valueOf(txtValor.getText()));
				cb.modificar(configuracion);
				clear();
				llenaTabla();
				aksiBack(null);
				notifier.notify(new Notification("Correcto", ConstantesMensajes.GUARDADO_OK, Notification.SUCCESS_ICON));
			} else {
				notifier.notify(new Notification("Error", ConstantesErrores.CAMPO_NUMERICO, Notification.ERROR_ICON));
			}

		} catch (Exception ex) {
			notifier.notify(new Notification("Error", ex.getMessage(), Notification.ERROR_ICON));
		}
	}

	public boolean verificaNumerico() {
		try {
			int i = Integer.valueOf(txtValor.getText());
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@FXML
	private void aksiBack(ActionEvent event) {
		paneCrud.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
	}

	public void cargaTabla() {
		TableColumn Habitaciones = new TableColumn(ConstantesEtiquetas.DESCRIPCION_UPPER);
		TableColumn Valor = new TableColumn(ConstantesEtiquetas.VALOR_UPPER);

		Habitaciones.setMinWidth(150);
		Habitaciones.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.NOMBRE_TABLA));

		Valor.setMinWidth(150);
		Valor.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.ID));

		tableData.getColumns().addAll(Habitaciones, Valor);
		tableData.setItems(lista);
	}

	public void llenaTabla() {
		try {
			ConfiguracionBean rbb = new ConfiguracionBean();
			lista = FXCollections.observableArrayList(rbb.traerValorGastosComunes());
			tableData.setItems(lista);
		} catch (ServiceException ex) {
			Logger.getLogger(ValorGastosComunesController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
