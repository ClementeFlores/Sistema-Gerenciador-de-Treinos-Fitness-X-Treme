/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.modelo.Exercicio;
import com.auinfo.fitnessxtreme.util.Validacao;
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
public class MenuPrincipalControlador implements Initializable {

    @FXML
    private AnchorPane principal;

    @FXML
    private Button btCadastroExercicio;

    @FXML
    private Button btCadastroUsuario;

    @FXML
    private Button btConfiguracoes;

    @FXML
    private Button btElaborarTreinamento;

    @FXML
    private Button btSobre;

    @FXML
    private Button btSair;

    boolean validar;

    Validacao valida = new Validacao();

    Exercicio e = new Exercicio();

    Navegacao nav = new Navegacao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);

        TelaBaseControlador.ANTERIOR = "Login";

        btCadastroUsuario.setOnAction(event -> nav.navega("CadastroUsuario"));
        btCadastroExercicio.setOnAction(event -> nav.navega("CadastroExercicio"));
        btElaborarTreinamento.setOnAction(event -> nav.navega("ConsultaSerie"));
        btConfiguracoes.setOnAction(event -> nav.navega("Configuracoes"));
        btSobre.setOnAction(event -> nav.navega("Sobre"));
        btSair.setOnAction(event -> nav.navega("Login"));
    }
}
