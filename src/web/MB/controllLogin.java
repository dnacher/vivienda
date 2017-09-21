package web.MB;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
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
import viviendas.Viviendas;
import web.animations.FadeInLeftTransition;
import web.animations.FadeInRightTransition;
import web.animations.FadeInLeftTransition1;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ejb.services.UsuariosBean;
import ejb.services.UsuariosLocal;
import entities.constantes.ConstantesErrores;
import entities.constantes.ConstantesEtiquetas;
import eu.hansolo.enzo.notification.Notification;
import exceptions.ServiceException;
import static viviendas.Viviendas.listaConfiguracion;
import seguridad.Seguridad;

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
    private UsuariosLocal ul;

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
            // new FadeInLeftTransition1(lblPassword).play();
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
        ControlVentana cv = new ControlVentana();
        UsuariosBean ub = new UsuariosBean();
        if (!txtUsername.getText().isEmpty()) {
            Viviendas.user = ub.traerUsuarioXNombre(txtUsername.getText());
            if (Viviendas.user != null) {
                if (Viviendas.user.getSalt() != null && Viviendas.user.getHashedPassword() != null) {
                    if (!txtPassword.getText().isEmpty()) {
                        Viviendas.user.setPassword(txtPassword.getText());
                        if (Viviendas.user.getNombre().equals(txtUsername.getText())
                                && Seguridad.verifyPassword(Viviendas.user)) {
                            logueoVerificado();
                        } else {
                            ConfiguracionControl.notifier.notify(new Notification("Error", "Credenciales incorrectas", Notification.ERROR_ICON));
                            cv.creaVentanaNotificacionError(ConstantesErrores.ERROR_LOGUEO);
                        }
                    } else {
                        ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.INGRESAR_PASS, Notification.ERROR_ICON));
                    }
                } else {
                    ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.CONTACTE_ADMINISTRADOR, Notification.ERROR_ICON));
                    ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.ERROR_USUARIO, Notification.ERROR_ICON));                    
                }

            } else {
                ConfiguracionControl.notifier.notify(new Notification("Error", "Credenciales incorrectas", Notification.ERROR_ICON));
                cv.creaVentanaNotificacionError(ConstantesErrores.ERROR_LOGUEO);
            }
        } else {
            ConfiguracionControl.notifier.notify(new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.INGRESAR_USU, Notification.ERROR_ICON));
        }

    }

    public void logueoVerificado() throws ServiceException, IOException {
        ConfiguracionBean cb = new ConfiguracionBean();
        listaConfiguracion = cb.traerTodos();
        ConfiguracionControl.notifier.notify(new Notification("Correcto", "Se logueo Correctamente", Notification.SUCCESS_ICON));
        //cv.creaVentanaNotificacionCorrecto();
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
