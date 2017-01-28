package web.controller;

import UtilsGeneral.UtilsVentanas;
import entities.constantes.Constantes;
import entities.enums.Paginas;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import viviendas.Viviendas;

public class menu implements Initializable { 
    @FXML
    private Button maximize;
    @FXML
    private Button minimize;
    @FXML
    private Button resize;
    @FXML
    private Button fullscreen;   
    @FXML
    private Button btnLogout;
    Stage stage;
    Rectangle2D rec2;
    Double w,h;
    @FXML
    private ListView<String> listMenu;
    @FXML
    private AnchorPane paneData;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        rec2 = Screen.getPrimary().getVisualBounds(); 
        w = 0.1;
        h = 0.1;
        List<String> lista=cargaLista();
        listMenu.getItems().addAll(lista);
        Platform.runLater(() -> {
            stage = (Stage) maximize.getScene().getWindow();
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");
            resize.setVisible(false);         
            UtilsVentanas uv= new UtilsVentanas();
            uv.loadAnchorPane(paneData, Constantes.PAGINA_MAIN);
            listMenu.requestFocus();
        });
    }    
    
    @FXML
    private void aksiMaximized(ActionEvent event) {
        stage = (Stage) maximize.getScene().getWindow();
        if (stage.isMaximized()) {
            if (w == rec2.getWidth() && h == rec2.getHeight()) {
                stage.setMaximized(false);
                stage.setHeight(600);
                stage.setWidth(800);
                stage.centerOnScreen();
                maximize.getStyleClass().remove("decoration-button-restore");
                resize.setVisible(true);
            }else{
                stage.setMaximized(false);
                maximize.getStyleClass().remove("decoration-button-restore");
                resize.setVisible(true);
            }
            
        }else{
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");
            resize.setVisible(false);
        }
    }

    @FXML
    private void aksiminimize(ActionEvent event) {
        stage = (Stage) minimize.getScene().getWindow();
        if (stage.isMaximized()) {
            w = rec2.getWidth();
            h = rec2.getHeight();
            stage.setMaximized(false);
            stage.setHeight(h);
            stage.setWidth(w);
            stage.centerOnScreen();
            Platform.runLater(() -> {
                stage.setIconified(true);
            });
        }else{
            stage.setIconified(true);
        }        
    }

    @FXML
    private void aksiResize(ActionEvent event) {
    }

    @FXML
    private void aksifullscreen(ActionEvent event) {
        stage = (Stage) fullscreen.getScene().getWindow();
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        }else{
            stage.setFullScreen(true);
        }
    }

    @FXML
    private void aksiClose(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void aksiKlikListMenu(MouseEvent event) {
        UtilsVentanas uv= new UtilsVentanas();
        String str=creaRuta(listMenu.getSelectionModel().getSelectedItem());
        uv.loadAnchorPane(paneData,str);
    }

    @FXML
    private void aksiLogout(ActionEvent event) {  
        UtilsVentanas uv= new UtilsVentanas();
        uv.newStage2(stage, btnLogout, "/web/vista/login.fxml", "Sample Apps", true, StageStyle.UNDECORATED, false);
        Viviendas.user=null;
        //aqui debe actualizar la lista de configuracion.
    }
    
    public String creaRuta(String ruta){
        String rutaNueva="";        
        rutaNueva=Constantes.PAGINA_ROOT + ruta.trim() + Constantes.EXTENSION_FXML;
        return rutaNueva;
    }
    
    public List<String> cargaLista(){
        List<String> lista=new ArrayList<>();
        for(Paginas p: Paginas.values()){
            lista.add(p.getMenu());
        }        
        return lista;
    }
    
        
}
