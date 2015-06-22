/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author BrunoRicardo
 */
public class Navegacao {

    public void navega(String tela) {
        try {
            Parent root;

            switch (tela) {
                case "CadastroUsuario":
                    validaPainelInferior();
                    System.out.println("CadastroUsuario");
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/CadastroUsuario.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "CadastroExercicio":
                    validaPainelInferior();
                    System.out.println("CadastroExercicio");
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/CadastroExercicio.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "CadastroAula":
                    System.out.println("CadastroAula");
                    TelaBaseControlador.BORDERPANE.setBottom(null);
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/CadastroAulaV2.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "ConsultaSerie":
                    validaPainelInferior();
                    System.out.println("ElaborarTreino");
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/ConsultaSeries.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "TelaBase":
                    System.out.println("TelaBase");
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/TelaBase.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "MenuPrincipal":
                    System.out.println("MenuPrincipal");
                    TelaBaseControlador.BORDERPANE.setBottom(null);
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/MenuPrincipal.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "Login":
                    System.out.println("Login");
                    TelaBaseControlador.BORDERPANE.setTop(null);
                    TelaBaseControlador.BORDERPANE.setBottom(null);
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/Login.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "Login2":
                    System.out.println("Login2");
                    TelaBaseControlador.BORDERPANE.setTop(null);
                    TelaBaseControlador.BORDERPANE.setBottom(null);
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/Login2.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "Sobre":
                    TelaBaseControlador.BORDERPANE.setTop(null);
                    validaPainelInferior();
                    System.out.println("Sobre");
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/Sobre.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "ConsultaAula":
                    validaPainelInferior();
                    System.out.println("ConsultaAula");
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/ConsultaAula.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
                case "Configuracoes":
                    validaPainelInferior();
                    System.out.println("Configuracoes");
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/Configuracao.fxml"));
                    TelaBaseControlador.CONTEUDO.getChildren().clear();
                    TelaBaseControlador.CONTEUDO.getChildren().add(root);
                    break;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void validaPainelInferior() {
        TelaBaseControlador.BORDERPANE.setBottom(TelaBaseControlador.getBottom());
    }
}
