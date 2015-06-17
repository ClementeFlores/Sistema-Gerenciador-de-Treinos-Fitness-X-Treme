/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Atual
 */
public class CadastroExercicioControlador implements Initializable {
    @FXML
    private AnchorPane principal;
    @FXML
    private TableView<?> tvExercicios;
    @FXML
    private TableColumn<?, ?> tcExercicio;
    @FXML
    private TableColumn<?, ?> tcSerie;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private TextField tfNomeExercicio;
    @FXML
    private ComboBox<?> cbGrupo;
    @FXML
    private Button btEditarGrupo;
    @FXML
    private Button btRemoverGrupoE;
    @FXML
    private TextField tfIdExercicio;
    @FXML
    private Button btNovoExercicio;
    @FXML
    private Button btRemoverExercicio;
    @FXML
    private Button btCadastrarExercicio;
    @FXML
    private TextField tfNomeGrupo;
    @FXML
    private TextField tfIdGrupo;
    @FXML
    private Button btNovoGrupo;
    @FXML
    private Button btCadastrarGrupo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
