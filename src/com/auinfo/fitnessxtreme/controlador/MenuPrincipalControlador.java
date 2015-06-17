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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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

        TelaBaseControlador.habilitaBtVoltar();
        btCadastroUsuario.setOnAction(event -> nav.navega("CadastroUsuario"));
        btCadastroExercicio.setOnAction(event -> nav.navega("CadastroExercicio"));
        btElaborarTreinamento.setOnAction(event -> nav.navega("ConsultaSerie"));
        btSobre.setOnAction(event -> nav.navega("Sobre"));
        btSair.setOnAction(event -> nav.navega("Login"));
    }

    private void cadastroExercicio() {
        Dialog<Exercicio> dialog = new Dialog<>();
        dialog.setTitle("Cadastro Exercicio");
        dialog.setHeaderText("Digite o nome do exercicio:");
        dialog.initOwner(TelaBaseControlador.BORDERPANE.getScene().getWindow());

        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nome = new TextField();
        nome.setOnKeyReleased(event -> validar = valida.validaTexto(nome, 3));
        nome.setPromptText("Nome");

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nome, 1, 0);

        // Enable/Disable login button depending on whether a username was entered.
        Node btOk = dialog.getDialogPane().lookupButton(ButtonType.OK);
        btOk.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        nome.textProperty().addListener((observable, oldValue, newValue) -> {
            btOk.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> nome.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            System.out.println("OK");
            if (dialogButton == ButtonType.OK && validar) {
                e.setNomeExercicio(nome.getText());
                return e;
            }
            return null;
        });

        dialog.showAndWait();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(TelaBaseControlador.BORDERPANE.getScene().getWindow());
        alert.setHeaderText(null);
        alert.setTitle("Cadastro de Exercicio");

        if (e != null) {
            alert.setContentText("Exercicio cadastradado com sucesso!");
        } else {
            alert.setContentText("Falha ao cadastrar Exercicio verifique os dados informados!");
        }
    }

}
