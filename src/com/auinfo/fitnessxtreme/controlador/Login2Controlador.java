/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.controlador.dao.UsuarioDao;
import com.auinfo.fitnessxtreme.impressora.Impressora;
import com.auinfo.fitnessxtreme.modelo.Usuario;
import com.auinfo.fitnessxtreme.util.Validacao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author BrunoRicardo
 */
public class Login2Controlador implements Initializable {

    @FXML
    private AnchorPane principal;
    @FXML
    private TextField tfMatricula;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private Button btLogar;
    @FXML
    private Button btVoltar;

    Navegacao nav = new Navegacao();
    Validacao valida = new Validacao();
    private boolean validar[] = {false, false};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);

        //Action Event
        btVoltar.setOnAction(event -> nav.navega("Login"));
        btLogar.setOnAction(event -> logar());
        tfMatricula.setOnAction(event -> pfSenha.requestFocus());
        pfSenha.setOnAction(event -> logar());

        //Key Released Event
        tfMatricula.setOnKeyReleased(event -> validar[0] = valida.validaNumero(tfMatricula, 1));
        pfSenha.setOnKeyReleased(event -> validar[1] = valida.validaPf(pfSenha, 6));

    }

    private void logar() {
        
        if(!validar()){
            return;
        }
        UsuarioDao uDao = new UsuarioDao();

        uDao.abreConnection();
        Usuario usuario = uDao.getLista(Integer.valueOf(tfMatricula.getText()));
        uDao.fechaConnection();

        if (usuario != null) {

            if (usuario.getEadministrador()) {
                if (pfSenha.getText().equals(usuario.getSenha())) {
                    TelaBaseControlador.BORDERPANE.setTop(TelaBaseControlador.getTop());
                    TelaBaseControlador.BORDERPANE.setBottom(TelaBaseControlador.getBottom());
                    nav.navega("MenuPrincipal");
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Senha inválida!");
                    alert.setHeaderText("A senha informada não é válida.");
                    alert.setContentText("Senha inválida.");

                    alert.showAndWait();
                }
            } else {
                new Impressora().geraCupom(usuario);
                nav.navega("Login");
            }

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Matricula não encontrada!");
            alert.setHeaderText("A matricula não foi encontrada no banco de dados.");
            alert.setContentText("Matricula inválida.");

            alert.showAndWait();
        }

    }

    private boolean validar() {
        boolean resultado = true;

        if (validar[0] == false) {
            tfMatricula.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }
        if (validar[1] == false) {
            pfSenha.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }

        return resultado;
    }

}
