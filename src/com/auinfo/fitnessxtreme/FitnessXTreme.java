package com.auinfo.fitnessxtreme;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author BrunoRicardo
 */
public class FitnessXTreme extends Application {
    
    public static Scene scenes;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/TelaBase.fxml"));
        
        Scene scene = new Scene(root);
        
        scenes = scene;
        
        stage.setScene(scene);
        //stage.setFullScreen(true);
        //stage.initStyle(StageStyle.UNDECORATED);  

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
