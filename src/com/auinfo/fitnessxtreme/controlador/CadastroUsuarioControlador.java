/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.modelo.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Tiago
 */
public class CadastroUsuarioControlador implements Initializable {

    @FXML
    private TableView<Usuario> tabelaUsuario;

    @FXML
    private TableColumn<Usuario, Integer> colunaMatricula;

    @FXML
    private TableColumn<Usuario, String> colunaNome;

    @FXML
    private TextField campoProcurar;

    @FXML
    private TextField campoMatricula;

    @FXML
    private TextField campoNome;

    @FXML
    private CheckBox isAdministrador;
    
    @FXML
    private PasswordField campoSenha;
    
    /**
     * Construtor da Classe, chamado antes do initialize.
     */
    public CadastroUsuarioControlador() {
    }
    
    /**
     * Método que é iniciado por padrão e automaticamente após o carregamento do arquivo FXML
     */
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colunaMatricula.setCellValueFactory(new PropertyValueFactory<> ("Matrícula"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<> ("Nome"));
        
        System.out.println("Começar a criar o user");
        Usuario user = new Usuario();
        user.setNome("Sebastião Pereira Vida Loka");
        user.setMatricula(123456);
        
        System.out.println("Usuario criado, setar pra exibir na tela");
        mostrarDadosUsuario(user);
        
    }

    @FXML
    private void handleBotaoProcurar() {
        String procura = campoProcurar.getText();
        if (procura.equalsIgnoreCase("") || procura.length() == 0) {
            System.out.println("Procura inválida");
        } else {
            //procurar o campo e selecioná-lo na tabela
        }
    }
    
    /**
     * Método responsável por cuidar do estado do campo de senha, através do estado da checkbox administrador
     * @param event 
     */
    @FXML
    private void handleCheckboxIsAdministrador(ActionEvent event) {
        BooleanProperty selecionado = isAdministrador.selectedProperty();

        
    }
    
    private void mostrarDadosUsuario (Usuario usuario) {
        if (usuario != null) {
            campoMatricula.setText(String.valueOf(usuario.getMatricula()));
            campoNome.setText(usuario.getNome());

        } else {
            campoMatricula.setText("");
            campoNome.setText("");
        }
    }

}
