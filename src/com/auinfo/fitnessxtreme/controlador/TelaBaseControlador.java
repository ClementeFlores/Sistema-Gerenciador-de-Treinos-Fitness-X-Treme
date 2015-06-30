/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.biometria.LeitorBiometrico;
import com.auinfo.fitnessxtreme.console.Console;
import com.auinfo.fitnessxtreme.controlador.dao.UsuarioDao;
import com.auinfo.fitnessxtreme.modelo.Usuario;
import com.auinfo.fitnessxtreme.util.ManipulaConfigs;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class TelaBaseControlador implements Initializable {

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
    private static Node top;
    private Boolean encontrou = false;
    private Properties props;
    public static Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        usuario = null;
        AnchorPane.setTopAnchor(conteudo, 0d);
        AnchorPane.setRightAnchor(conteudo, 0d);
        AnchorPane.setLeftAnchor(conteudo, 0d);
        AnchorPane.setBottomAnchor(conteudo, 0d);

        CONTEUDO = conteudo;
        BORDERPANE = borderPane;

        top = borderPane.getTop();
        borderPane.setTop(null);
        borderPane.setBottom(null);

        btLogar.setOnAction(event -> logar());

        btLogar.setOnKeyPressed( event -> {
            if(event.getCode() == KeyCode.ENTER){
                logar();
            }
        });

        btLogar.requestFocus();

        try {
            props = ManipulaConfigs.getProp("config\\main.properties");
        } catch (IOException e) {
            System.out.println("Houve um erro ao carregar as configurações. Possíveis causas incluem arquivo de configuração danificado e/ou ausente.\n");
            e.printStackTrace();
        }

        if (Boolean.parseBoolean(props.getProperty("prop.sistema.console"))) {
            new Console().run();
        }
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
                encontrou = true;
                if (u.getEadministrador()) {
                    entrarMenu();
                } else {
                    usuario = u;
                    nav.navega("Imprimir");
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

    private void entrarMenu() {
        TelaBaseControlador.BORDERPANE.setTop(top);
        nav.navega("MenuPrincipal");
    }
}
