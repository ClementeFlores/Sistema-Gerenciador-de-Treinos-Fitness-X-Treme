/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.controlador.dao.AulaDao;
import com.auinfo.fitnessxtreme.controlador.dao.ExercicioDao;
import com.auinfo.fitnessxtreme.modelo.Aula;
import com.auinfo.fitnessxtreme.modelo.Exercicio;
import com.auinfo.fitnessxtreme.modelo.ExercicioTV;
import com.auinfo.fitnessxtreme.modelo.Serie;
import com.auinfo.fitnessxtreme.util.AutoCompleteComboBoxListener;
import com.auinfo.fitnessxtreme.util.Validacao;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author BrunoRicardo
 */
public class CadastroAulaV2Controlador implements Initializable {

    @FXML
    private Label lbObservacao;

    @FXML
    private Label lbDtCadastrado;

    @FXML
    private Label lbObjetivo;

    @FXML
    private Label lbDtInicio;

    @FXML
    private Button btAdicionar;

    @FXML
    private Label lbDtFim;

    @FXML
    private TextField tfDescAula;

    @FXML
    private ComboBox<Exercicio> cbExercicios;

    @FXML
    private ComboBox<String> cbDiaSemana;

    @FXML
    private AnchorPane principal;

    @FXML
    private TextField tfEsteira;

    @FXML
    private TextField tfBicicleta;

    @FXML
    private TextField tfElipticon;

    @FXML
    private TableView<ExercicioTV> tvExercicios;

    @FXML
    private Label lbNome;

    @FXML
    private Button btEditar;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btRemover;

    @FXML
    private Label lbIdade;

    @FXML
    private Button btCancelar;

    @FXML
    private TableColumn<?, ?> tcExercicio;

    @FXML
    private TableColumn<?, ?> tcSerie;

    @FXML
    private TableColumn<?, ?> tcPeso;

    public static Aula AULA;
    private AulaDao aDao = new AulaDao();
    ExercicioDao eDao = new ExercicioDao();
    private Navegacao nav = new Navegacao();
    private boolean validar[] = {false, false, false, false, false};

    ObservableList<ExercicioTV> exerciciosTabela;
    ObservableList<Exercicio> exerciciosCb;
    FilteredList<ExercicioTV> filteredExercicios;

    Validacao valida = new Validacao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);

        TelaBaseControlador.ANTERIOR = "ConsultaAula";
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        Serie serie = ConsultaSeriesControlador.SERIE;
        LocalDate hoje = LocalDate.now();
        LocalDate nasc;
        Calendar cal = Calendar.getInstance();

        cbDiaSemana.getItems().addAll(new String[]{"Domingo", "Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira", "Sábado"});

        lbNome.setText(serie.getUsuario().getNome());

        cal.setTime(serie.getUsuario().getDatanascimento());
        nasc = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        long idade = ChronoUnit.YEARS.between(nasc, hoje);
        lbIdade.setText(idade + "");

        lbObjetivo.setText(serie.getUsuario().getObjetivo());
        lbObservacao.setText(serie.getUsuario().getObservacao());

        lbDtCadastrado.setText(formatador.format(serie.getUsuario().getDatacadastramento()));

        lbDtInicio.setText("" + formatador.format(serie.getDataInicio()));

        lbDtFim.setText("" + formatador.format(serie.getDataFim()));

        eDao.abreConnection();
        List<Exercicio> listExercicio = eDao.getLista();
        eDao.fechaConnection();

        exerciciosCb = FXCollections.observableArrayList();

        listExercicio.forEach(e -> exerciciosCb.add(e));

        cbExercicios.setItems(exerciciosCb);

        tcExercicio.setCellValueFactory(new PropertyValueFactory("nomeExercicio"));
        tcSerie.setCellValueFactory(new PropertyValueFactory("serie"));
        tcPeso.setCellValueFactory(new PropertyValueFactory("peso"));

        //Action Event
        btCadastrar.setOnAction(event -> cadastrar());
        btCancelar.setOnAction(event -> cancelar());
        cbDiaSemana.setOnAction(event -> validar[4] = valida.validaCb(cbDiaSemana));
        btAdicionar.setOnAction(event -> adicionarExercicio());
        btEditar.setOnAction(event -> editarExercicio());
        btRemover.setOnAction(event -> removerExercicio());

        //Key Released Event
        new AutoCompleteComboBoxListener<>(cbExercicios);
        tfDescAula.setOnKeyReleased(event -> validar[0] = valida.validaTexto(tfDescAula, 5));
        tfEsteira.setOnKeyReleased(event -> validar[1] = valida.validaNumero(tfEsteira, 1));
        tfBicicleta.setOnKeyReleased(event -> validar[2] = valida.validaNumero(tfBicicleta, 1));
        tfElipticon.setOnKeyReleased(event -> validar[3] = valida.validaNumero(tfElipticon, 1));

        tfDescAula.requestFocus();

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (AULA != null) {
                    btCadastrar.setText("Salvar");
                    editarAula();
                } else {
                    exerciciosTabela = FXCollections.observableArrayList();
                    tvExercicios.setItems(exerciciosTabela);
                }
            }
        });
    }

    private boolean validar() {
        boolean resultado = true;

        validar[0] = valida.validaTexto(tfDescAula, 5);
        validar[1] = valida.validaNumero(tfEsteira, 1);
        validar[2] = valida.validaNumero(tfBicicleta, 1);
        validar[3] = valida.validaNumero(tfElipticon, 1);

        if (validar[0] == false) {
            tfDescAula.setStyle(valida.vermelhoGradiente);
            tfDescAula.setPromptText("");
            resultado = false;
        }
        if (validar[1] == false) {
            tfEsteira.setStyle(valida.vermelhoGradiente);
            tfEsteira.setPromptText("");
            resultado = false;
        }

        if (validar[2] == false) {
            tfBicicleta.setStyle(valida.vermelhoGradiente);
            tfBicicleta.setPromptText("");
            resultado = false;
        }

        if (validar[3] == false) {
            tfElipticon.setStyle(valida.vermelhoGradiente);
            tfElipticon.setPromptText("");
            resultado = false;
        }

        if (validar[4] == false) {
            cbDiaSemana.setStyle(valida.vermelhoBorda);
            resultado = false;
        }

        return resultado;
    }

    private void preencheAula() {
        AULA.setDescaula(tfDescAula.getText());
        if (btCadastrar.getText().equals("Cadastrar")) {
            AULA.setImpresso(0);
        }
        AULA.setRepetir(validaCbSemana());
        AULA.setSerie(ConsultaSeriesControlador.SERIE);
        AULA.setTempoelipticon(Integer.valueOf(tfElipticon.getText()));
        AULA.setTempobicicleta(Integer.valueOf(tfBicicleta.getText()));
        AULA.setTempoesteira(Integer.valueOf(tfEsteira.getText()));
        AULA.setExercicioList(preencheLista());
    }

    private void cadastrar() {
        if (!validar()) {
            return;
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(TelaBaseControlador.BORDERPANE.getScene().getWindow());
        alert.setHeaderText(null);
        aDao.abreConnection();
        boolean resultado = true;

        if (btCadastrar.getText().equals("Cadastrar")) {
            AULA = new Aula();
            preencheAula();
            alert.setTitle("Cadastro de Aula");
            AULA.setIdaula(aDao.adicionaAula(AULA));
            if (AULA.getIdAula() != 0) {

                if (!aDao.adicionaAulaExercicios(AULA)) {
                    resultado = false;
                }

            }
            if (resultado) {
                alert.setContentText("Aula cadastrada com sucesso!");
            } else {
                alert.setContentText("Falha ao cadastrar a aula!");
            }

        } else if (btCadastrar.getText().equals("Salvar")) {
            preencheAula();
            alert.setTitle("Atualizar Aula");

            if (aDao.deletaAulaExercicio(AULA.getIdAula())) {
                if (aDao.atualizaAula(AULA)) {
                    if (!aDao.adicionaAulaExercicios(AULA)) {
                        resultado = false;
                    }
                } else {
                    resultado = false;
                }
            } else {
                resultado = false;
            }
            if (resultado) {
                alert.setContentText("Aula atualizada com sucesso!");
            } else {
                alert.setContentText("Falha ao atualizar a aula!");
            }

        }

        alert.showAndWait();
        aDao.fechaConnection();
        nav.navega("ConsultaAula");
    }

    private void cancelar() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(TelaBaseControlador.BORDERPANE.getScene().getWindow());
        alert.setTitle("Cancelar Cadastro de Aula");
        alert.setHeaderText("Os dados preenchidos serão perdidos!");
        alert.setContentText("Você tem certeza que deseja cancelar o cadastramento da aula?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            nav.navega("ConsultaAula");
        }
    }

    private List<Exercicio> preencheLista() {
        Exercicio exercicio = null;
        List<Exercicio> listExercicios = new ArrayList<>();

        for (int i = 0; i < tvExercicios.getItems().size(); i++) {

            exercicio = tvExercicios.getItems().get(i).getExercicio();
            listExercicios.add(exercicio);
        }
        return listExercicios;
    }

    private String validaCbSemana() {
        if (cbDiaSemana.getSelectionModel().getSelectedItem() != null && cbDiaSemana.getSelectionModel().getSelectedIndex() != -1) {
            return (cbDiaSemana.getItems().get(cbDiaSemana.getSelectionModel().getSelectedIndex()));
        }
        return null;
    }

    private Exercicio validaCbExercicio() {
        if (cbExercicios.getSelectionModel().getSelectedItem() != null && cbExercicios.getSelectionModel().getSelectedIndex() != -1) {
            return (cbExercicios.getItems().get(cbExercicios.getSelectionModel().getSelectedIndex()));
        }
        return null;
    }

    public void editarAula() {
        tfDescAula.setText(AULA.getDescaula());
        tfEsteira.setText(AULA.getTempoesteira() + "");
        tfElipticon.setText(AULA.getTempoelipticon() + "");
        tfBicicleta.setText(AULA.getTempobicicleta() + "");

        int index = -1;
        switch (AULA.getRepetir()) {

            case "Domingo":
                index = 0;
                break;
            case "Segunda-Feira":
                index = 1;
                break;
            case "Terça-Feira":
                index = 2;
                break;
            case "Quarta-Feira":
                index = 3;
                break;
            case "Quinta-Feira":
                index = 4;
                break;
            case "Sexta-Feira":
                index = 5;
                break;
            case "Sábado":
                index = 6;
                break;
        }
        cbDiaSemana.getSelectionModel().select(index);
        exerciciosTabela = FXCollections.observableArrayList();

        AULA.getExercicioList().forEach(e -> exerciciosTabela.add(new ExercicioTV(e)));
        tvExercicios.setItems(exerciciosTabela);

    }

    private void adicionarExercicio() {
        Exercicio e = validaCbExercicio();

        if (e != null) {

            e = preencheSerie(e);

            if (e.getSerie() != null && !"".equals(e.getSerie())) {
                exerciciosTabela.add(new ExercicioTV(e));
                cbExercicios.getSelectionModel().clearSelection();
                cbExercicios.setValue(null);
                cbExercicios.requestFocus();
            }
        }
    }

    private void editarExercicio() {

        if (tvExercicios.getSelectionModel().getSelectedIndex() < 0) {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Editar Exercicio");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um exercicio na tabela para poder editar!");

            alert.showAndWait();

            return;
        }

        int index = tvExercicios.getSelectionModel().getSelectedIndex();

        Exercicio e = preencheSerie(exerciciosTabela.get(index).getExercicio());

        if (e != null) {
            exerciciosTabela.set(index, new ExercicioTV(e));
        }
    }

    private void removerExercicio() {
        if (tvExercicios.getSelectionModel().getSelectedIndex() < 0) {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Remover Exercicio");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um exercicio na tabela para poder remover!");
            alert.initOwner(TelaBaseControlador.BORDERPANE.getScene().getWindow());

            alert.showAndWait();

        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Remover Exercicio");
            alert.setHeaderText(null);
            alert.setContentText("Tem certeza que deseja remover o exercicio?");
            alert.initOwner(TelaBaseControlador.BORDERPANE.getScene().getWindow());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int index = tvExercicios.getSelectionModel().getSelectedIndex();

                exerciciosTabela.remove(index);
            }
        }
    }

    private Exercicio preencheSerie(Exercicio e) {
        boolean[] validos = {false, false};
        // Create the custom dialog.
        Dialog<Exercicio> dialog = new Dialog<>();
        dialog.setTitle("Cadastro Exercicio");
        dialog.setHeaderText("Digite os dados do exercicio:");
        dialog.initOwner(TelaBaseControlador.BORDERPANE.getScene().getWindow());

        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField serie = new TextField();
        serie.setOnKeyReleased(event -> validos[0] = valida.validaTexto(serie, 3));
        serie.setPromptText("Serie");
        TextField peso = new TextField();
        peso.setOnKeyReleased(event -> validos[1] = valida.validaNumero(peso, 1));
        peso.setPromptText("Peso");

        grid.add(new Label("Série:"), 0, 0);
        grid.add(serie, 1, 0);
        grid.add(new Label("Peso:"), 0, 1);
        grid.add(peso, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node btOk = dialog.getDialogPane().lookupButton(ButtonType.OK);
        btOk.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        serie.textProperty().addListener((observable, oldValue, newValue) -> {
            btOk.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> serie.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK && validos(validos)) {
                e.setSerie(serie.getText());
                System.out.println(e.getSerie());
                e.setPeso(Integer.valueOf(peso.getText()));
                System.out.println(e.getPeso());
                return e;
            }
            return null;
        });

        dialog.showAndWait();

        return e;
    }

    private boolean validos(boolean validos[]) {
        boolean resultado = true;

        if (!validos[0]) {
            resultado = false;
        } else if (!validos[1]) {
            resultado = false;
        }
        return resultado;
    }
}
