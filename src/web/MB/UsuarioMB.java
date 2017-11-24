package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.TipoUsuarioBean;
import ejb.services.UsuariosBean;
import entities.constantes.Constantes;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import entities.enums.MenuConfiguracion;
import entities.persistence.entities.Tipousuario;
import entities.persistence.entities.Usuario;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;
import seguridad.Seguridad;

public class UsuarioMB implements Initializable {

	@FXML
	private PasswordField txtPass;

	@FXML
	private AnchorPane paneCrud;

	@FXML
	private AnchorPane paneTabel;

	@FXML
	private PasswordField txtPass2;

	@FXML
	private TableView<Usuario> tableData;

	@FXML
	private TextField txtNombre;

	@FXML
	private ProgressBar bar;

	@FXML
	private CheckBox chkActivo;

	@FXML
	private ComboBox<Tipousuario> cmbTipoUsuario;
	
	@FXML
	private Button btnAgregar;
	
	ObservableList<Tipousuario> listaTipoUsuarioO;
	ObservableList<Usuario> listaUsuariosO;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		btnAgregar.setDisable(ConfiguracionControl.traePermisos(MenuConfiguracion.Usuarios.getPagina(), Constantes.PERMISO_OPERADOR));
		task();
		aksiBack(null);
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
				aksiNew(null);
				List<Tipousuario> listaTipoUsuarios;
				List<Usuario> listaUsuarios;
				try {
					TipoUsuarioBean tb = new TipoUsuarioBean();
					listaTipoUsuarios = tb.traerTodos();
					UsuariosBean ub = new UsuariosBean();
					listaUsuarios = ub.traerTodos();
					listaTipoUsuarioO = FXCollections.observableList(listaTipoUsuarios);
					listaUsuariosO = FXCollections.observableList(listaUsuarios);
					cmbTipoUsuario.setItems(listaTipoUsuarioO);
					cargaTabla();
				} catch (ServiceException ex) {
					Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		bar.progressProperty().bind(longTask.progressProperty());
		new Thread(longTask).start();
	}

	public void cargaTabla() {
		TableColumn Nombre = new TableColumn(ConstantesEtiquetas.NOMBRE_UPPER);
		Nombre.setMinWidth(150);
		Nombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.NOMBRE));
		
		TableColumn TipoUsuario = new TableColumn(ConstantesEtiquetas.LBL_TIPO_USUARIO);
		TipoUsuario.setMinWidth(150);
		TipoUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreTipoUsuario"));
		
		tableData.getColumns().addAll(Nombre);
		tableData.getColumns().addAll(TipoUsuario);
		tableData.setItems(listaUsuariosO);
	}

	public void llenaTabla() {
		try {
			UsuariosBean ub = new UsuariosBean();
			List<Usuario> listaUsuarios;
			listaUsuarios = ub.traerTodos();
			listaUsuariosO = FXCollections.observableList(listaUsuarios);
			tableData.setItems(listaUsuariosO);
		} catch (ServiceException ex) {
			Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void clear() {
		txtNombre.clear();
		txtPass.clear();
		txtPass2.clear();
		cmbTipoUsuario.getSelectionModel().select(-1);
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
		if (txtNombre.getText().isEmpty()) {
			cv.creaVentanaNotificacionError(ConstantesErrores.FALTA_NOMBRE);
		} else {
			try {
				if (txtPass.getText().equals(txtPass2.getText())) {
					Usuario usuario = new Usuario();
					int ind = ConfiguracionControl.traeUltimoId(ConstantesEtiquetas.USUARIO);
					usuario.setIdUsuario(ind);
					usuario.setTipousuario(cmbTipoUsuario.getSelectionModel().getSelectedItem());
					usuario.setActivo(chkActivo.isSelected());
					usuario.setNombre(txtNombre.getText());
					usuario.setPassword(txtPass.getText());
					usuario = Seguridad.hashUserPassword(usuario);
					UsuariosBean tb = new UsuariosBean();
					tb.guardar(usuario);
					cv.creaVentanaNotificacionCorrecto();
					clear();
					llenaTabla();
					aksiBack(null);
				} else {
					cv.creaVentanaNotificacionError(ConstantesErrores.PASS_NO_COINCIDE);
				}

			} catch (Exception ex) {
				cv.creaVentanaNotificacionError(ex.getMessage());
			}
		}
	}

	@FXML
	private void aksiBack(ActionEvent event) {
		paneCrud.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
	}

}
