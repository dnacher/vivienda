package web.MB;

import UtilsGeneral.ConfiguracionControl;
import ejb.services.ConfiguracionBean;
import entities.constantes.Constantes;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import web.animations.FadeInLeftTransition;
import web.animations.FadeInRightTransition;
import web.animations.FadeInLeftTransition1;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ejb.validaciones.LoginValidation;
import eu.hansolo.enzo.notification.Notification;
import exceptions.ServiceException;
import static viviendas.Viviendas.listaConfiguracion;

public class controllLogin implements Initializable {

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Text lblWelcome;
	@FXML
	private Text lblUserLogin;
	@FXML
	private Button btnLogin;
	@FXML
	private Label lblClose;
	@FXML
	private Label lblVersion;

	Stage stage;

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		Platform.runLater(() -> {
			new FadeInRightTransition(lblUserLogin).play();
			new FadeInLeftTransition(lblWelcome).play();
			new FadeInLeftTransition1(lblVersion).play();
			new FadeInLeftTransition1(txtUsername).play();
			new FadeInLeftTransition1(txtPassword).play();
			new FadeInRightTransition(btnLogin).play();
			lblVersion.setText(Constantes.VERSION);
			lblClose.setOnMouseClicked((MouseEvent event) -> {
				Platform.exit();
				System.exit(0);
			});
		});

		txtPassword.setOnAction(event -> {
			try {
				login(event);
			} catch (ServiceException ex) {
				Logger.getLogger(controllLogin.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(controllLogin.class.getName()).log(Level.SEVERE, null, ex);
			} catch (Exception ex) {
				Logger.getLogger(controllLogin.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

	@FXML
	private void login(ActionEvent event) throws IOException, ServiceException, Exception {
		Notification notif = LoginValidation.login(txtUsername.getText(), txtPassword.getText());
		ConfiguracionControl.notifier.notify(notif);
		if (notif.IMAGE.equals(Notification.SUCCESS_ICON)) {
			logueoVerificado();
		}
	}

	public void logueoVerificado() throws ServiceException, IOException {
		Stage st = new Stage();
		stage = (Stage) lblClose.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource(Constantes.PAGINA_FORM_MENU));
		Scene scene = new Scene(root);
		st.initStyle(StageStyle.UNDECORATED);
		st.setResizable(false);
		st.setTitle(Constantes.VIVIENDA);
		st.setScene(scene);
		st.show();
		stage.close();
	}
}
