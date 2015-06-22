/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.controlador.dao.AulaDao;
import com.auinfo.fitnessxtreme.controlador.dao.ExercicioDao;
import com.auinfo.fitnessxtreme.controlador.dao.GrupoDao;
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
    ObservableList<Grupo> grupos;

    ExercicioDao eDao = new ExercicioDao();
    GrupoDao gDao = new GrupoDao();

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
        validaBotoesSalvarGrupo(true);

        eDao.abreConnection();
        List<Exercicio> listExercicio = eDao.getLista();
        eDao.fechaConnection();

        gDao.abreConnection();
        List<Grupo> listGrupo = gDao.getLista();
        gDao.fechaConnection();

        exercicios = FXCollections.observableArrayList();
        grupos = FXCollections.observableArrayList();

        listGrupo.forEach(u -> grupos.add(u));
        cbGrupo.setItems(grupos);

        listExercicio.forEach(e -> exercicios.add(new ExercicioGrupoTV(e)));
        filteredExercicios = new FilteredList<>(exercicios);

        tcExercicio.setCellValueFactory(new PropertyValueFactory("nomeExercicio"));
        tcGrupo.setCellValueFactory(new PropertyValueFactory("grupoExercicio"));

        tvExercicios.setItems(filteredExercicios);

        //Action Event
        btCadastrarExercicio.setOnAction(event -> cadastrarExercicio());
        btNovoExercicio.setOnAction(event -> novoExercicio());
        btRemoverExercicio.setOnAction(event -> remover());
        btEditarGrupo.setOnAction(event -> editarGrupo());
        btRemoverGrupoE.setOnAction(event -> removerGrupo());
        btNovoGrupo.setOnAction(event -> novoGrupo());
        btCadastrarGrupo.setOnAction(event -> cadastrarGrupo());
        cbGrupo.setOnAction(event -> {
            validar[1] = valida.validaCb(cbGrupo);
            validaBotoesSalvarGrupo(false);
        });

        tvExercicios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                exercicioToForm();
            }
        });

        //Key Released Event
        tfPesquisar.setOnKeyReleased(event -> pesquisar());
        tfNomeExercicio.setOnKeyReleased(event -> validar[0] = valida.validaTexto(tfNomeExercicio, 2));
        cbGrupo.setOnKeyReleased(event -> validar[1] = valida.validaCb(cbGrupo));
        tfNomeGrupo.setOnKeyReleased(event -> validar[2] = valida.validaTexto(tfNomeGrupo, 2));

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
        }
        novoExercicio();
    }

    private void clearExercicio() {
        tfNomeExercicio.setStyle(valida.normal);
        cbGrupo.setStyle(valida.normal);
    }

    private void clearGrupo() {
        tfNomeGrupo.setStyle(valida.normal);
    }

    private void novoExercicio() {

        tfIdExercicio.setText("");
        index = -1;
        tfNomeExercicio.setText("");
        cbGrupo.getSelectionModel().clearSelection();
        cbGrupo.setValue(null);
        validaBotoesSalvarExercicio(true);
        btCadastrarExercicio.setText("Cadastrar");
        tvExercicios.getSelectionModel().clearSelection();
        clearExercicio();
    }

    private void remover() {
        AulaDao aDao = new AulaDao();
        Exercicio e = formToExercicio();

        aDao.abreConnection();
        boolean resultado = aDao.getLista(e);
        aDao.fechaConnection();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Excluir Exercicio");

        System.out.println(resultado);

        if (resultado) {
            alert.setHeaderText(null);
            alert.setContentText("Este exercicio não pode ser excluido, pois o mesmo esta sendo utilizado em uma ou mais Aulas");
            alert.showAndWait();
        } else {
            eDao.abreConnection();

            alert.setHeaderText("O Exercicio " + e.getNomeExercicio() + " será excluido permanentemente!");
            alert.setContentText("Você tem certeza que deseja excluir o Exercicio?");
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
    }

    private void validaBotoesSalvarExercicio(boolean b) {
        btRemoverExercicio.setDisable(b);
    }

    private void validaBotoesSalvarGrupo(boolean b) {
        btRemoverGrupoE.setDisable(b);
    }

    private Exercicio formToExercicio() {
        Exercicio exercicio = new Exercicio();

        if (tfIdExercicio.getText() != null && !"".equals(tfIdExercicio.getText())) {
            exercicio.setIdExercicio(Integer.valueOf(tfIdExercicio.getText()));
        }

        exercicio.setNomeExercicio(tfNomeExercicio.getText());
        exercicio.setGrupo(validaCb());
        index = tvExercicios.getSelectionModel().getSelectedIndex();
        clearExercicio();

        return exercicio;
    }

    private void exercicioToForm() {
        Exercicio exercicio = tvExercicios.getSelectionModel().getSelectedItem().getExercicio();
        index = tvExercicios.getSelectionModel().getSelectedIndex();
        System.out.println(index);

        tfIdExercicio.setText(exercicio.getIdExercicio() + "");
        tfNomeExercicio.setText(exercicio.getNomeExercicio() + "");
        int indexcb = -1;
        for (int i = 0; i < grupos.size(); i++) {
            if(exercicio.getGrupo().getIdGrupo() == grupos.get(i).getIdGrupo()){
                indexcb = i;
                break;
            }
        }
        cbGrupo.getSelectionModel().select(indexcb);

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

        return resultado;
    }

    private Grupo validaCb() {
        if (cbGrupo.getSelectionModel().getSelectedItem() != null && cbGrupo.getSelectionModel().getSelectedIndex() != -1) {
            return (cbGrupo.getItems().get(cbGrupo.getSelectionModel().getSelectedIndex()));
        }
        System.out.println("NULL GRUPO :'(");
        return null;
    }

    private void editarGrupo() {
        Grupo grupo = validaCb();
        tfNomeGrupo.setText(grupo.getNomeGrupo());
        tfIdGrupo.setText(grupo.getIdGrupo() + "");
        btCadastrarGrupo.setText("Salvar");
    }

    private void removerGrupo() {

        eDao.abreConnection();
        boolean resultado = eDao.getLista(validaCb());
        eDao.fechaConnection();

        Alert alert = new Alert(AlertType.CONFIRMATION);

        System.out.println(resultado);

        if (resultado) {
            alert.setHeaderText(null);
            alert.setContentText("Este grupo não pode ser excluido, pois o mesmo esta sendo utilizado em um ou mais exercícios");
            alert.showAndWait();
        } else {

            gDao.abreConnection();

            alert.setTitle("Excluir Grupo");
            alert.setHeaderText(null);

            if (cbGrupo.getSelectionModel().getSelectedIndex() == -1) {
                alert.setContentText("Falha ao excluir o grupo!");
                alert.showAndWait();
                return;
            }

            alert.setContentText("Você tem certeza que deseja excluir o Grupo?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                int indexCb = -1;
                indexCb = cbGrupo.getSelectionModel().getSelectedIndex();

                if (gDao.deletaGrupo(validaCb())) {
                    System.out.println("Removido");
                    System.out.println("Removido: " + indexCb);
                    grupos.remove(indexCb);
                    alert.setContentText("Grupo excluido com sucesso!");
                    alert.showAndWait();
                } else {
                    alert.setContentText("Falha ao excluir o grupo!");
                    alert.showAndWait();
                }
            }
            gDao.fechaConnection();
            novoGrupo();
        }
    }

    private void novoGrupo() {
        tfIdGrupo.setText("");
        tfNomeGrupo.setText("");
        validaBotoesSalvarGrupo(true);
        btCadastrarGrupo.setText("Cadastrar");
        clearGrupo();
    }

    private void cadastrarGrupo() {

        if (validar[2] == false) {
            tfNomeGrupo.setStyle(valida.vermelhoGradiente);
            return;
        } else {
            tfNomeGrupo.setStyle(valida.normal);
        }

        int indexGrupo = cbGrupo.getSelectionModel().getSelectedIndex();
        Grupo g = new Grupo();

        g.setNomeGrupo(tfNomeGrupo.getText());
        if (tfIdGrupo.getText() != null && !"".equals(tfIdGrupo.getText())) {
            g.setIdGrupo(Integer.valueOf(tfIdGrupo.getText()));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if ("Cadastrar".equals(btCadastrarGrupo.getText())) {
            gDao.abreConnection();
            g.setIdGrupo(gDao.adicionaGrupo(g));
            if (g.getIdGrupo() != 0) {
                System.out.println("Cadastrado");
                grupos.add(g);
                alert.setTitle("Cadastro de Grupo");
                alert.setContentText("Grupo cadastrado com sucesso!");
                alert.showAndWait();
            } else {
                System.out.println("Erro ao cadastrar");
                alert.setTitle("Cadastro de Grupo");
                alert.setContentText("Erro ao cadastrar o Grupo!");
                alert.showAndWait();
            }
            gDao.fechaConnection();

        } else if ("Salvar".equals(btCadastrarGrupo.getText())) {

            gDao.abreConnection();
            if (gDao.atualizaGrupo(g)) {
                System.out.println("Atualizado");
                grupos.set(indexGrupo, g);

                alert.setTitle("Atualizar Grupo");
                alert.setContentText("Grupo atualizado com sucesso!");
                alert.showAndWait();

            } else {
                alert.setTitle("Cadastro de Grupo");
                alert.setContentText("Falha ao atualizadar o Grupo!");
                alert.showAndWait();
            }
            eDao.fechaConnection();
        }
        novoGrupo();
    }

}
