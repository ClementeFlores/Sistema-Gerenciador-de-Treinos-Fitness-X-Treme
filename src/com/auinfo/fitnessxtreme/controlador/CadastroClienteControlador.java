/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Tiago
 */
public class CadastroClienteControlador {
    
    @FXML
    private TextField campoProcurar;
    
    @FXML
    private void handleBotaoProcurar() {
    String procura = campoProcurar.getText();
    if (procura.equalsIgnoreCase("") || procura.length() == 0) {
        System.out.println("Procura inválida");
    } else {
        //procurar o campo e selecioná-lo na tabela
    }
}
    
    
    
    
}
