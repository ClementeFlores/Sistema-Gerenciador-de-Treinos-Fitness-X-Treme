/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.biometria.LeitorBiometrico;
import com.auinfo.fitnessxtreme.controlador.dao.UsuarioDao;
import com.auinfo.fitnessxtreme.impressora.Impressora;
import com.auinfo.fitnessxtreme.modelo.Usuario;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class TelaBaseControlador implements Initializable {

    @FXML
    private Button btVoltar;

    @FXML
    private AnchorPane conteudo;

    @FXML
    private Button btLogar;

    @FXML
    private BorderPane borderPane;

    Navegacao nav = new Navegacao();
    public static AnchorPane CONTEUDO;
    public static BorderPane BORDERPANE;
    public static String ANTERIOR;
    public static String BIOMETRIA;
    public static Button VOLTAR;
    private static Node top;
    private static Node bottom;
    private Boolean encontrou = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane.setTopAnchor(conteudo, 0d);
        AnchorPane.setRightAnchor(conteudo, 0d);
        AnchorPane.setLeftAnchor(conteudo, 0d);
        AnchorPane.setBottomAnchor(conteudo, 0d);

        CONTEUDO = conteudo;
        BORDERPANE = borderPane;
        VOLTAR = btVoltar;

        top = borderPane.getTop();
        borderPane.setTop(null);
        bottom = borderPane.getBottom();
        borderPane.setBottom(null);

        btLogar.setOnAction(event -> logar());
        btVoltar.setOnAction(event -> nav.navega(ANTERIOR));
        
        btLogar.requestFocus();
    }

    public static void habilitaBtVoltar() {
        VOLTAR.setVisible(true);
    }

    public static void desabilitaBtVoltar() {
        VOLTAR.setVisible(false);
    }

    public void logar() {

        UsuarioDao uDao = new UsuarioDao();
        LeitorBiometrico biometria = new LeitorBiometrico();
        String lido = biometria.capturar();

        uDao.abreConnection();
        List<Usuario> listUsuario = uDao.getLista();
        uDao.fechaConnection();

        for (Usuario u : listUsuario) {

            if (biometria.verificar(u.getIndicadordireito(), lido) || biometria.verificar(u.getIndicadoresquerdo(), lido)) {
                System.out.println(u.getNome());
                encontrou = true;
                if (u.getEadministrador()) {
                    entrarMenu();
                } else {
                    new Impressora(u);
                    nav.navega("Login");
                }
                break;
            }
        }

        if (!encontrou) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Sua digital não foi encontrada!");
            alert.setHeaderText("Digital não encontrada no banco de dados!");
            alert.setContentText("Deseja entrar utilizando o número da matricula?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                nav.navega("Login2");
            }
        }
    }

    public static Node getTop() {
        return top;
    }

    public static Node getBottom() {
        return bottom;
    }

    private void entrarMenu() {
        TelaBaseControlador.BORDERPANE.setTop(top);
        nav.navega("MenuPrincipal");
    }
}
