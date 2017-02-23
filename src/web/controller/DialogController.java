package web.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import viviendas.Viviendas;

public class DialogController implements Initializable {
    
    @FXML
    private Button btnAceptar;
  
    @FXML
    private Label lblTitle;
    
    @FXML
    private Label lblClose;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
         lblTitle.setText("¿Confirma que desea eliminar el Convenio?");
         lblClose.setOnMouseEntered((MouseEvent me) -> {
            lblClose.setCursor(Cursor.HAND); 
        });
         lblClose.setOnMouseExited((MouseEvent me) -> {
            lblClose.setCursor(Cursor.DEFAULT); 
        });
    }

    public void cerrar(){
        Stage stage = (Stage) btnAceptar.getScene().getWindow();        
        stage.close();
    }

    
    public void aceptar(){
        Viviendas.confirmacion=true;
        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.close();
    }   
}
