/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import static com.auinfo.fitnessxtreme.controlador.TelaBaseControlador.ANTERIOR;
import com.auinfo.fitnessxtreme.controlador.dao.AulaDao;
import com.auinfo.fitnessxtreme.controlador.dao.ExercicioDao;
import com.auinfo.fitnessxtreme.modelo.Aula;
import com.auinfo.fitnessxtreme.modelo.Exercicio;
import com.auinfo.fitnessxtreme.modelo.ExercicioTV;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author BrunoRicardo
 */
public class ConsultaAulaControlador implements Initializable {

    @FXML
    private Label lbTempoElipticon;

    @FXML
    private Label lbDataInicio;

    @FXML
    private Label lbDescricao;

    @FXML
    private ComboBox<Aula> cbAulas;

    @FXML
    private Label lbDataFim;

    @FXML
    private Label lbTempoEsteira;

    @FXML
    private Label lbQtdImpressao;

    @FXML
    private TableColumn<?, ?> tcSerie;

    @FXML
    private TableColumn<?, ?> tcExercicio;

    @FXML
    private AnchorPane principal;

    @FXML
    private Label lbCliente;

    @FXML
    private Label lbTempoBicicleta;

    @FXML
    private Label lbNomeAula;

    @FXML
    private Label lbPeso1;

    @FXML
    private TableView<ExercicioTV> tvExercicios;

    @FXML
    private Label lbDiaDaSemana;

    @FXML
    private Label lbPeso2;

    @FXML
    private Label lbNome;

    @FXML
    private TableColumn<?, ?> tcPeso;

    @FXML
    private Label lbPeso3;

    @FXML
    private Button btRemoverAula;

    @FXML
    private Button btNovaAula;

    @FXML
    private Button btEditarAula;
    
    @FXML
    private Button btVoltar;

    Navegacao nav = new Navegacao();

    int index = -1;

    ObservableList<Aula> aulasCb;
    ObservableList<ExercicioTV> exercicios;

    AulaDao aDao = new AulaDao();
    ExercicioDao eDao = new ExercicioDao();

    FilteredList<ExercicioTV> filteredExercicios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);

        TelaBaseControlador.ANTERIOR = "ConsultaSerie";

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        lbCliente.setText(ConsultaSeriesControlador.USUARIO.getNome());

        lbNome.setText(ConsultaSeriesControlador.SERIE.getNomeSerie());
        lbDescricao.setText(ConsultaSeriesControlador.SERIE.getDescSerie());

        lbDataInicio.setText(formatador.format(ConsultaSeriesControlador.SERIE.getDataInicio()));
        lbDataFim.setText(formatador.format(ConsultaSeriesControlador.SERIE.getDataFim()));

        lbPeso1.setText(ConsultaSeriesControlador.SERIE.getPeso1() + "");
        lbPeso2.setText(ConsultaSeriesControlador.SERIE.getPeso2() + "");
        lbPeso3.setText(ConsultaSeriesControlador.SERIE.getPeso3() + "");

        aDao.abreConnection();
        List<Aula> listAula = aDao.getLista(ConsultaSeriesControlador.SERIE);
        aDao.fechaConnection();

        aulasCb = FXCollections.observableArrayList();
        listAula.forEach(a -> aulasCb.add(a));

        cbAulas.setItems(aulasCb);

        tcExercicio.setCellValueFactory(new PropertyValueFactory("nomeExercicio"));
        tcSerie.setCellValueFactory(new PropertyValueFactory("serieCompleta"));
        tcPeso.setCellValueFactory(new PropertyValueFactory("peso"));

        //Action Event
        btVoltar.setOnAction(event -> nav.navega(ANTERIOR));
        cbAulas.setOnAction(event -> buscarExercicios());
        btNovaAula.setOnAction(event -> novaAula());
        btRemoverAula.setOnAction(event -> removerAula());
        btEditarAula.setOnAction(event -> editarAula());

        //Key Released Event
    }

    private void novaAula() {
        CadastroAulaV2Controlador.AULA = null;
        nav.navega("CadastroAula");
    }

    private void buscarExercicios() {
        Aula aula = validaCb();
        if (aula != null) {
            lbNomeAula.setText(aula.getDescaula());
            lbDiaDaSemana.setText(aula.getRepetir());
            lbTempoBicicleta.setText(aula.getTempobicicleta() + "");
            lbTempoElipticon.setText(aula.getTempoelipticon() + "");
            lbTempoEsteira.setText(aula.getTempoesteira() + "");
            lbQtdImpressao.setText(aula.getImpresso() + "");

            eDao.abreConnection();
            List<Exercicio> listaExercicio = eDao.getLista(aula.getIdAula(), true);
            eDao.fechaConnection();

            exercicios = FXCollections.observableArrayList();

            listaExercicio.forEach(e -> exercicios.add(new ExercicioTV(e)));
            filteredExercicios = new FilteredList<>(exercicios);
            tvExercicios.setItems(exercicios);
        }

    }

    private Aula validaCb() {
        if (cbAulas.getSelectionModel().getSelectedItem() != null && cbAulas.getSelectionModel().getSelectedIndex() != -1) {
            return (cbAulas.getItems().get(cbAulas.getSelectionModel().getSelectedIndex()));
        }
        return null;
    }

    private void removerAula() {
        aDao = new AulaDao();

        aDao.abreConnection();

        Aula aula = validaCb();

        if (aula != null) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Excluir Aula");
            alert.setHeaderText("A aula " + aula.getDescaula() + " será excluida permanentemente!");
            alert.setContentText("Você tem certeza que deseja excluir a aula?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (aDao.deletaAula(aula)) {
                    aulasCb.remove(cbAulas.getSelectionModel().getSelectedIndex());
                    tvExercicios.getItems().clear();

                    alert.setHeaderText(null);
                    alert.setContentText("Aula excluida com sucesso!");

                } else {
                    alert.setHeaderText(null);
                    alert.setContentText("Falha ao excluir a aula!");
                }
                alert.showAndWait();
            }
        }
        aDao.fechaConnection();
    }

    private void editarAula() {
        Aula aula = validaCb();

        if (aula != null) {
            eDao.abreConnection();
            aula.setExercicioList(eDao.getLista(aula.getIdAula(), true));
            eDao.fechaConnection();
            CadastroAulaV2Controlador.AULA = aula;
            nav.navega("CadastroAula");
        }

    }
}
