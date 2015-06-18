/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.controlador.dao.ExercicioDao;
import com.auinfo.fitnessxtreme.modelo.Exercicio;
import com.auinfo.fitnessxtreme.modelo.ExercicioGrupoTV;
import com.auinfo.fitnessxtreme.modelo.Grupo;
import com.auinfo.fitnessxtreme.util.Validacao;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<ExercicioGrupoTV> tvExercicios;
    @FXML
    private TableColumn<?, ?> tcExercicio;
    @FXML
    private TableColumn<?, ?> tcGrupo;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private TextField tfNomeExercicio;
    @FXML
    private ComboBox<Grupo> cbGrupo;
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

    int index = -1;

    ObservableList<ExercicioGrupoTV> exercicios;

    ExercicioDao eDao = new ExercicioDao();

    Navegacao nav = new Navegacao();

    FilteredList<ExercicioGrupoTV> filteredExercicios;
    private boolean validar[] = {false, false, false};
    Validacao valida = new Validacao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);

        TelaBaseControlador.ANTERIOR = "MenuPrincipal";

        validaBotoesSalvarExercicio(true);

        eDao.abreConnection();
        List<Exercicio> listExercicio = eDao.getLista();
        eDao.fechaConnection();

        exercicios = FXCollections.observableArrayList();

        listExercicio.forEach(e -> exercicios.add(new ExercicioGrupoTV(e)));
        filteredExercicios = new FilteredList<>(exercicios);

        tcExercicio.setCellValueFactory(new PropertyValueFactory("nomeExercicio"));
        tcGrupo.setCellValueFactory(new PropertyValueFactory("grupoExercicio"));

        tvExercicios.setItems(filteredExercicios);

        //Action Event
        //Key Released Event
        filteredExercicios.setPredicate(e -> true);
    }

    private void pesquisar() {
        filteredExercicios.setPredicate(u -> u.filtrar(tfPesquisar.getText()));
    }

    private void cadastrarExercicio() {
        if (!validar()) {
            return;
        }

        Exercicio e = formToExercicio();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if ("Cadastrar".equals(btCadastrarExercicio.getText())) {
            eDao.abreConnection();
            e.setIdExercicio(eDao.adicionaExercicio(e));
            if (e.getIdExercicio() != 0) {
                System.out.println("Cadastrado");
                exercicios.add(new ExercicioGrupoTV(e));
                alert.setTitle("Cadastro de Exercicio");
                alert.setContentText("Exercicio cadastrado com sucesso!");
                alert.showAndWait();
            } else {
                System.out.println("Erro ao cadastrar");
                alert.setTitle("Cadastro de Exercicio");
                alert.setContentText("Erro ao cadastrar o Exercicio!");
                alert.showAndWait();
            }
            eDao.fechaConnection();

        } else if ("Salvar".equals(btCadastrarExercicio.getText())) {

            eDao.abreConnection();
            if (eDao.atualizaExercicio(e)) {
                System.out.println("Atualizado");
                exercicios.set(index, new ExercicioGrupoTV(e));

                alert.setTitle("Atualizar Exercicio");
                alert.setContentText("Exercicio atualizado com sucesso!");
                alert.showAndWait();

            } else {
                alert.setTitle("Cadastro de Exercicio");
                alert.setContentText("Falha ao atualizadar o Exercicio!");
                alert.showAndWait();
            }
            eDao.fechaConnection();
            novoExercicio();
        }
    }

    private void clearExercicio() {
        tfNomeExercicio.setStyle(valida.normal);
        cbGrupo.setStyle(valida.normal);
    }

    private void novoExercicio() {

        tfIdExercicio.setText("");
        index = -1;
        tfNomeExercicio.setText("");
        cbGrupo.getSelectionModel().clearSelection();
        validaBotoesSalvarExercicio(true);
        btCadastrarExercicio.setText("Cadastrar");
        tvExercicios.getSelectionModel().clearSelection();
        clearExercicio();
    }

    private void remover() {
        eDao.abreConnection();
        Exercicio e = formToExercicio();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Excluir Exercicio");
        alert.setHeaderText("O Exercicio " + e.getNomeExercicio() + " será excluido permanentemente!");
        alert.setContentText("Você tem certeza que deseja excluir o Exercicio?");
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (eDao.deletaExercicio(e)) {
                System.out.println("Removido");
                System.out.println("Removido: " + index);
                exercicios.remove(index);
                alert.setTitle("Excluir Exercicio");
                alert.setHeaderText(null);
                alert.setContentText("Exercicio excluido com sucesso!");
                alert.showAndWait();
            } else {
                alert.setTitle("Excluir Exercicio");
                alert.setHeaderText(null);
                alert.setContentText("Falha ao excluir o Exercicio!");
                alert.showAndWait();
            }
        }
        eDao.fechaConnection();
        novoExercicio();
    }

    private void validaBotoesSalvarExercicio(boolean b) {
        btNovoExercicio.setDisable(b);
        btRemoverExercicio.setDisable(b);
    }

    private Exercicio formToExercicio() {
        Exercicio exercicio = new Exercicio();

        if (tfIdExercicio.getText() != null && !"".equals(tfIdExercicio.getText())) {
            exercicio.setIdExercicio(Integer.valueOf(tfIdExercicio.getText()));
        }

        exercicio.setNomeExercicio(tfNomeExercicio.getText());
        exercicio.setGrupo(validaCb());

        clearExercicio();
        
        return exercicio;
    }

    private void exercicioToForm() {
        Exercicio exercicio = tvExercicios.getSelectionModel().getSelectedItem().getExercicio();
        index = tvExercicios.getSelectionModel().getSelectedIndex();
        System.out.println(index);

        tfIdExercicio.setText(exercicio.getIdExercicio()+ "");
        
        cbGrupo.getSelectionModel().select(exercicio.getGrupo());


        validaBotoesSalvarExercicio(false);
        btCadastrarExercicio.setText("Salvar");
        clearExercicio();
    }

    private boolean validar() {
        boolean resultado = true;

        if (validar[0] == false) {
            tfNomeExercicio.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }
        if (validar[1] == false) {
            cbGrupo.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }

        if (validar[2] == false) {
            tfNomeGrupo.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }

        return resultado;
    }

    private Grupo validaCb() {
        if (cbGrupo.getSelectionModel().getSelectedItem() != null && cbGrupo.getSelectionModel().getSelectedIndex() != -1) {
            return (cbGrupo.getItems().get(cbGrupo.getSelectionModel().getSelectedIndex()));
        }
        return null;
    }

}
