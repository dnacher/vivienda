package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.TipoUsuarioBean;
import ejb.services.UsuariosBean;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;


public class usuarioController implements Initializable {

     @FXML
    private PasswordField txtPass;

    @FXML
    private Button btnBack;

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
    private ImageView imgLoad;

    @FXML
    private ComboBox<Tipousuario> cmbTipoUsuario;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aksiNew(null);
        TipoUsuarioBean tb=new TipoUsuarioBean();
        List<Tipousuario> lista;
         try {
             lista = tb.traerTodos();
             ObservableList<Tipousuario> listaTipoUsuario = FXCollections.observableList(lista);
             cmbTipoUsuario.setItems(listaTipoUsuario);
         } catch (ServiceException ex) {
             Logger.getLogger(usuarioController.class.getName()).log(Level.SEVERE, null, ex);
         }             
    }   
    
    private void clear(){
        txtNombre.clear();
        txtPass.clear();
        txtPass2.clear();
    }
    
    private void displayDiscountCode(){
      
    }
    
    private void displayZip(){
     
    }
    
    private void auto(){
     
    }
    
    private void selectData(){
      
    }
    
    private void selectWithService(){
     
    }
    
    
    
    @FXML
    private void keyState(KeyEvent e){
      
    }

    @FXML
    private void aksiKlikTableData(MouseEvent event) {
      
    }

    @FXML
    private void aksiNew(ActionEvent event) {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();
            auto();
        });
    }
    
    @FXML
    private void aksiSave(ActionEvent event){
        ControlVentana cv=new ControlVentana();
        if(txtNombre.getText().isEmpty()){
           
        }
        else{
            try{
                if(txtPass.getText().equals(txtPass2.getText())){
                    Usuario usuario=new Usuario();
                    int ind=ConfiguracionControl.traeUltimoId("Usuario");
                    usuario.setIdUsuario(ind);
                    usuario.setTipousuario(cmbTipoUsuario.getSelectionModel().getSelectedItem());
                    usuario.setActivo(chkActivo.isSelected());
                    usuario.setNombre(txtNombre.getText());
                    usuario.setPassword(txtPass.getText());
                    UsuariosBean tb=new UsuariosBean();
                    tb.guardar(usuario);
                    ConfiguracionControl.ActualizaId("Usuario");
                    cv.creaVentanaNotificacionCorrecto();
                    clear();
                }else{
                    cv.creaVentanaNotificacionError("Las contraseñas no coinciden");
                }
                
            }
            catch(Exception ex){
                cv.creaVentanaNotificacionError(ex.getMessage());
            }       
        }
    }

    @FXML
    private void aksiBack(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
    
    private class ButtonCell extends TableCell<Object, Boolean> {
      
        }

        protected void updateItem(Boolean t, boolean empty) {
       
        }
    }
