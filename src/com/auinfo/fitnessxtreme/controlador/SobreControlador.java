/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import static com.auinfo.fitnessxtreme.controlador.TelaBaseControlador.ANTERIOR;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author BrunoRicardo
 */
public class SobreControlador implements Initializable {

    @FXML
    private AnchorPane principal;
    
    @FXML
    private Button btVoltar;
    
    Navegacao nav = new Navegacao();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);
        
        TelaBaseControlador.ANTERIOR = "MenuPrincipal";
        
        //ActionEvent
        btVoltar.setOnAction(event -> {
            TelaBaseControlador.BORDERPANE.setTop(TelaBaseControlador.getTop());
            nav.navega(ANTERIOR);
        });
        
    }    
    
}
