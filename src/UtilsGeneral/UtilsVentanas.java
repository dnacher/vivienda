package UtilsGeneral;

import entities.constantes.Constantes;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Daniel
 */
public class UtilsVentanas {
    
    public void loadAnchorPane(AnchorPane ap, String a){
        try {
            AnchorPane p = FXMLLoader.load(getClass().getResource(a));
            ap.getChildren().setAll(p);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }   
    }
    
    public void newStage(Stage stage, Label lb, String load, String judul, boolean resize, StageStyle style, boolean maximized){
       try {
            Stage st = new Stage();
            stage = (Stage) lb.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            st.initStyle(style);
            st.setResizable(resize);
            st.setTitle(judul);
            st.setScene(scene);
            st.show();
            stage.close();
        } catch (Exception e) {
        } 
    }
    
    public void newStage2(Stage stage, Button lb, String load, String judul, boolean resize, StageStyle style, boolean maximized){
       try {
            Stage st = new Stage();
            stage = (Stage) lb.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            st.initStyle(style);
            st.setResizable(resize);
            st.setMaximized(maximized);
            st.setTitle(judul);
            st.setScene(scene);
            st.show();
            stage.close();
        } catch (Exception e) {
        } 
    }
    
    public static void setModelColumn(TableColumn tb,String a){
        tb.setCellValueFactory(new PropertyValueFactory(a));
    }
    
}
