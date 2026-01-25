/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package Modele;

import java.awt.geom.Rectangle2D;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author htw
 */
public class Auto_ecole extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
        
        Scene scene = new Scene(root);
        

        
        // Obtenir la taille de l'écran principal
        //javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        
        // Redimensionner la fenêtre pour qu'elle prenne toute la surface de l'écran
        //stage.setX(screenBounds.getMinX());
        //stage.setY(screenBounds.getMinY());
        //stage.setWidth(screenBounds.getWidth());
        //stage.setHeight(screenBounds.getHeight());
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
