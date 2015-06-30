package com.auinfo.fitnessxtreme.controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author BrunoRicardo
 */
public class LoginControlador implements Initializable {

    @FXML
    private AnchorPane principal;

    @FXML
    private Button btLogar;
    
    Navegacao nav = new Navegacao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);
        
        btLogar.setOnAction(event -> new TelaBaseControlador().logar());
        
        btLogar.setOnKeyPressed( event -> {
            if(event.getCode() == KeyCode.ENTER){
                new TelaBaseControlador().logar();
            }
        });
        
        btLogar.requestFocus();
        
        
        
        
        
    }
}
