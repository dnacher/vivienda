package web.controller;

import ejb.services.UnidadBean;
import entities.persistence.entities.Unidad;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;


public class CotizacionController implements Initializable {
    
    @FXML
    private AnchorPane paneFirst;

    @FXML
    private AnchorPane paneSecond;
    
    @FXML
    private AnchorPane paneThird;
    
    @FXML
    private ComboBox<Integer> cmbTorre;
    
    @FXML
    private ComboBox<String> cmbBlock;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        cargarComboBlock();
        cargarComboTorre();
        paneSecond.setVisible(false);
        paneThird.setVisible(false);   
    }
    
    public void atras() {
        paneSecond.setOpacity(0);
        new FadeInUpTransition(paneFirst).play();
    }
    
    public void atras2() {
        paneThird.setOpacity(0);
        new FadeInUpTransition(paneFirst).play();
    }
    
  
     public void nuevoTrabajo(){
        paneSecond.setVisible(true);
        paneFirst.setOpacity(0);
        new FadeInUpTransition(paneSecond).play();
     }
     
     public void editarTrabajo(){
        paneThird.setVisible(true);
        paneFirst.setOpacity(0);        
        new FadeInUpTransition(paneThird).play();
     }
     
     public void guardar(){}
     
     public void mostrarTabla(){}
     
     public void nuevaUnidad(){}
     
     public void mostrar(ActionEvent event) {}
       
        public void mostrarTodos() {
               
        }
        
       public void cargarComboBlock(){
            ObservableList<String> options = 
            FXCollections.observableArrayList("A","B","C","D","E");
            cmbBlock.setItems(options);
       }
    
       public void cargarComboTorre(){
        ObservableList<Integer> listaTorres;
        listaTorres=FXCollections.observableArrayList(1,2,3,4,5,6);
        cmbTorre.setItems(listaTorres);
       }
} 
