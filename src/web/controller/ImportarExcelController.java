package web.controller;

import ejb.importaDatos.ImportarDatosExcel;
import ejb.services.UnidadBean;
import entities.constantes.Constantes;
import entities.persistence.entities.Unidad;
import exceptions.ImportarExcelException;
import exceptions.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class ImportarExcelController implements Initializable {
  
     @FXML
    private Text lblArchivo;

    @FXML
    private ComboBox<String> cmbImportar;

    @FXML
    private Label lblClose;

    List<Unidad> unidades;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaComboImportar();
        
    }     
           
    public void cargaComboImportar(){
        ObservableList<String> lista=FXCollections.observableArrayList(Constantes.LISTA_TIPO_ARCHIVO_IMPORTAR);
        cmbImportar.setItems(lista);
        cmbImportar.valueProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue ov, String t, String t1) {
                switch(cmbImportar.getValue()){
                    case "Unidad":
                        Importar();
                        break;
                    default:
                }
            }    
        });  
    }
            
    public void Importar(){
         try {
             final FileChooser fileChooser = new FileChooser();
             lblArchivo.setText(Constantes.TRABAJANDO_ESPERE);
             fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(Constantes.EXCEL, Constantes.EXTENSION_EXCEL));
             Stage stage = (Stage) lblClose.getScene().getWindow();
             File file = fileChooser.showOpenDialog(stage);
             ImportarDatosExcel ide=new ImportarDatosExcel();
             unidades=ide.ImportarUnidadesDeExcel(file);
             lblArchivo.setText(Constantes.PRONTO_CARGAR);
         } catch (ImportarExcelException ex) {
             Logger.getLogger(ImportarExcelController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public void cargar(){
         try {
             lblArchivo.setText(Constantes.CARGANDO);
             UnidadBean ub=new UnidadBean();
             ub.guardarUnidades(unidades);
             lblArchivo.setText("Pronto");
         } catch (ServiceException ex) {
             Logger.getLogger(ImportarExcelController.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(ImportarExcelController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}