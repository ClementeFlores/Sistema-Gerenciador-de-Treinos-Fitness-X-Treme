/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.modelo.Usuario;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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
    
    /**
     * Construtor da Classe, chamado antes do initialize.
     */
    public CadastroUsuarioControlador() {
        
    }
    
    /**
     * Método que é iniciado por padrão e automaticamente após o carregamento do arquivo FXML
     */
    @FXML
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Carregar os dados da tabela
//        List<Usuario> usuarios = new ArrayList<>();
//        usuarios.
//        tabelaUsuario.setItems(FXCollections.observableArrayList(usuarios));
//
//        colunaMatricula.setCellValueFactory(new PropertyValueFactory<> ("Merda"));
//        colunaNome.setCellFactory(cellData -> cellData.getCell);
        
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

}
