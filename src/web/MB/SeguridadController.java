package web.MB;

import UtilsGeneral.ConfiguracionControl;
import ejb.services.TipoUsuarioBean;
import ejb.services.UsuariosBean;
import entities.constantes.ConstantesEtiquetas;
import entities.enums.MenuAdministracion;
import entities.enums.MenuConfiguracion;
import entities.enums.MenuMantenimiento;
import entities.persistence.entities.Permisosusuario;
import entities.persistence.entities.Tipousuario;
import eu.hansolo.enzo.notification.Notification;
import exceptions.ServiceException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import viviendas.Viviendas;
import web.animations.FadeInUpTransition;

public class SeguridadController implements Initializable {

	@FXML
	private AnchorPane paneCrud;

	@FXML
	private AnchorPane paneTabel;

	@FXML
	private CheckBox chkAgregar;

	@FXML
	private CheckBox chkVer;

	@FXML
	private CheckBox chkAdministrador;

	@FXML
	private TableView<Tipousuario> tableData;

	@FXML
	private ProgressBar bar;

	@FXML
	private TableView<Permisosusuario> tblPermisos;

	@FXML
	private CheckBox chkActivo;

	@FXML
	private Label lblTIpoUsuario;

	@FXML
	private Label lblInicio;

	@FXML
	private Label lblAdministracion;

	@FXML
	private Label lblMantenimiento;

	@FXML
	private Label lblReportes;

	@FXML
	private Label lblConfiguracion;

	@FXML
	private ComboBox<String> cmbPagina;

	public ObservableList<Tipousuario> listaTipoUsuarios;
	public ObservableList<Permisosusuario> listaPermisos;
	public ObservableList<String> listaPaginas;
	public List<Permisosusuario> listaPermisosUsuario;
	public Tipousuario tu;

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			lblnoVisibles(false);
			task();
			atras(null);
			agregaListenerChkBox();
		} catch (Exception ex) {
			Logger.getLogger(SeguridadController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void lblnoVisibles(boolean visible) {
		lblAdministracion.setVisible(visible);
		lblMantenimiento.setVisible(visible);
		lblReportes.setVisible(visible);
		lblConfiguracion.setVisible(visible);
	}

	public void agregaListenerChkBox() {

		chkVer.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (chkAgregar.isSelected()) {
					chkVer.setSelected(true);
				} else {
					if (chkAdministrador.isSelected()) {
						chkVer.setSelected(true);
						chkAgregar.setSelected(true);
					}
				}
			}
		});

		chkAgregar.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (chkAgregar.isSelected()) {
					chkVer.setSelected(true);
				} else {
					if (chkAdministrador.isSelected()) {
						chkVer.setSelected(true);
						chkAgregar.setSelected(true);
					}
				}
			}
		});

		chkAdministrador.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (chkAdministrador.isSelected()) {
					chkVer.setSelected(true);
					chkAgregar.setSelected(true);
				}
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
				try {
					TipoUsuarioBean tub = new TipoUsuarioBean();
					listaTipoUsuarios = FXCollections.observableArrayList(tub.traerTodos());
					cargaTabla();
					cargaTablaPermisosUsuarios();
					cargaComboPagina();
				} catch (ServiceException ex) {
					Logger.getLogger(SeguridadController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		bar.progressProperty().bind(longTask.progressProperty());
		new Thread(longTask).start();
	}

	private void cargaComboPagina() {
		if (listaPaginas == null) {
			listaPaginas = FXCollections.observableArrayList(new ArrayList());
		}
//		UsuariosBean ub = new UsuariosBean();
//		List<Permisosusuario> listaSeguridad = ub.TraePermisos(Viviendas.getTipoUsuario());
		for (Permisosusuario pu : Viviendas.listaPermisos) {
			if (pu.getPermiso() == 7) {
				if (noEs(pu.getPagina())) {
					listaPaginas.add(pu.getPagina());
				}
			}
		}
		cmbPagina.setItems(listaPaginas);
	}

	private boolean noEs(String pagina) {
		boolean noEs = true;
		switch (pagina) {
			case "Administracion":
			case "Inicio":
			case "Mantenimiento":
			case "Configuracion":
				noEs = false;
				break;
		}
		return noEs;
	}

	public void nuevoGrupo() {
		if (tableData.getSelectionModel().getSelectedItem() != null) {
			tu = tableData.getSelectionModel().getSelectedItem();
			lblTIpoUsuario.setText(tu.getNombre());
			if (listaPermisos != null) {
				listaPermisos.clear();
				listaPermisosUsuario.clear();
			} else {
				listaPermisosUsuario = new ArrayList<>();
				listaPermisos = FXCollections.observableArrayList(listaPermisosUsuario);
			}
			UsuariosBean ub = new UsuariosBean();
			listaPermisosUsuario = ub.TraePermisos(tu);
			listaPermisos = FXCollections.observableArrayList(listaPermisosUsuario);
			for(Permisosusuario pu: listaPermisos){
				verificaCarpeta(pu.getPagina(), true);
			}
			tblPermisos.setItems(listaPermisos);
			paneTabel.setOpacity(0);
			new FadeInUpTransition(paneCrud).play();			
		}
	}

	public void cargaTablaPermisosUsuarios() {
		TableColumn Pagina = new TableColumn(ConstantesEtiquetas.PAGINA_UPPER);
		TableColumn Permiso = new TableColumn(ConstantesEtiquetas.PERMISO_UPPER);

		Pagina.setMinWidth(150);
		Pagina.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.PAGINA));

		Permiso.setMinWidth(150);
		Permiso.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.PERMISO));

		tblPermisos.getColumns().addAll(Pagina, Permiso);
		tblPermisos.setItems(listaPermisos);
	}

	public void agregar() {
		if (cmbPagina.getSelectionModel().getSelectedItem() != null) {
			if (listaPermisosUsuario == null) {
				listaPermisosUsuario = new ArrayList<>();
			}
			if (!existePagina(cmbPagina.getSelectionModel().getSelectedItem())) {
				Permisosusuario pu = new Permisosusuario();
				pu.setPagina(cmbPagina.getSelectionModel().getSelectedItem());
				pu.setPermiso(traeValorPermiso());
				pu.setTipousuario(tu);
				listaPermisosUsuario.add(pu);
				listaPermisos = FXCollections.observableArrayList(listaPermisosUsuario);
				tblPermisos.setItems(listaPermisos);
				verificaCarpeta(pu.getPagina(), true);
			} else {
				ConfiguracionControl.notifier.notify(new Notification("Verifique", "La pagina ya esta en los permisos", Notification.WARNING_ICON));
			}

		} else {
			ConfiguracionControl.notifier.notify(new Notification("Verifique", "debe seleccionar los combos", Notification.WARNING_ICON));
		}
	}

	public void verificaCarpeta(String pagina, boolean agregar) {
		if (pagina.equals("Reportes")) {
			lblReportes.setVisible(agregar);
		} else {
			for (MenuAdministracion ma : MenuAdministracion.values()) {
				if (ma.getPagina().equals(pagina)) {
					lblAdministracion.setVisible(agregar);
					break;
				}
			}
			for (MenuConfiguracion ma : MenuConfiguracion.values()) {
				if (ma.getPagina().equals(pagina)) {
					lblConfiguracion.setVisible(agregar);
					break;
				}
			}
			for (MenuMantenimiento ma : MenuMantenimiento.values()) {
				if (ma.getPagina().equals(pagina)) {
					lblMantenimiento.setVisible(agregar);
					break;
				}
			}
		}
	}

	public void agregarTodos() {
		listaPermisos.clear();
		listaPermisosUsuario.clear();
		for (String s : cmbPagina.getItems()) {
			Permisosusuario pu = new Permisosusuario();
			pu.setPagina(s);
			pu.setPermiso(traeValorPermiso());
			pu.setTipousuario(tu);
			listaPermisosUsuario.add(pu);
		}
		listaPermisos = FXCollections.observableArrayList(listaPermisosUsuario);
		tblPermisos.setItems(listaPermisos);
		lblnoVisibles(true);
	}

	public void eliminar() {
		if (tblPermisos.getSelectionModel().getSelectedItem() != null) {
			listaPermisosUsuario.remove(tblPermisos.getSelectionModel().getSelectedItem());
			verificaCarpeta(tblPermisos.getSelectionModel().getSelectedItem().getPagina(), false);
			listaPermisos.remove(tblPermisos.getSelectionModel().getSelectedItem());
		} else {
			ConfiguracionControl.notifier.notify(new Notification("Verifique", "debe seleccionar un permiso a borrar", Notification.WARNING_ICON));
		}
	}

	public void eliminarTodos() {
		listaPermisos.clear();
		listaPermisosUsuario.clear();
		lblnoVisibles(false);
	}

	public boolean existePagina(String pagina) {
		boolean existe = false;
		for (Permisosusuario pu : listaPermisosUsuario) {
			if (pu.getPagina().equals(pagina)) {
				existe = true;
				break;
			}
		}
		return existe;
	}

	public int traeValorPermiso() {
		int permiso = 0;
		if (chkVer.isSelected()) {
			permiso = 1;
		}
		if (chkAgregar.isSelected()) {
			permiso = 3;
		}
		if (chkAdministrador.isSelected()) {
			permiso = 7;
		}
		return permiso;
	}

	@FXML
	private void guardar(ActionEvent event) {
		if (listaPermisos != null && listaPermisos.size() > 0) {
			try {
				UsuariosBean ub = new UsuariosBean();
				ub.EliminaPermisos(tu);
				ub = new UsuariosBean();
				ub.guardaPermisos(listaPermisos);
				tu = null;
				atras(null);
			} catch (ServiceException ex) {
				Logger.getLogger(SeguridadController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@FXML
	private void atras(ActionEvent event) {
		paneCrud.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
		lblnoVisibles(false);
	}

	public void cargaTabla() {
		TableColumn Nombre = new TableColumn(ConstantesEtiquetas.NOMBRE_UPPER);
		TableColumn Descripcion = new TableColumn(ConstantesEtiquetas.DESCRIPCION_UPPER);

		Nombre.setMinWidth(150);
		Nombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.NOMBRE));

		Descripcion.setMinWidth(150);
		Descripcion.setCellValueFactory(new PropertyValueFactory<>(ConstantesEtiquetas.DESCRIPCION));

		tableData.getColumns().addAll(Nombre, Descripcion);
		tableData.setItems(listaTipoUsuarios);

	}

}
