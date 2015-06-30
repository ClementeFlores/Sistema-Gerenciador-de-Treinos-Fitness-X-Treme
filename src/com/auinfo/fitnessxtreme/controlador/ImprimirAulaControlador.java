/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import static com.auinfo.fitnessxtreme.controlador.TelaBaseControlador.ANTERIOR;
import com.auinfo.fitnessxtreme.controlador.dao.AulaDao;
import com.auinfo.fitnessxtreme.controlador.dao.ExercicioDao;
import com.auinfo.fitnessxtreme.controlador.dao.SerieDao;
import com.auinfo.fitnessxtreme.impressora.Impressora;
import com.auinfo.fitnessxtreme.modelo.Aula;
import com.auinfo.fitnessxtreme.modelo.AulaTV;
import com.auinfo.fitnessxtreme.modelo.Exercicio;
import com.auinfo.fitnessxtreme.modelo.ExercicioTV;
import com.auinfo.fitnessxtreme.modelo.Serie;
import com.auinfo.fitnessxtreme.modelo.Usuario;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class ImprimirAulaControlador implements Initializable {

    @FXML
    private AnchorPane principal;

    @FXML
    private TableView<ExercicioTV> tvExercicios;

    @FXML
    private TableColumn<?, ?> tcExercicio;

    @FXML
    private TableColumn<?, ?> tcSerie;

    @FXML
    private TableColumn<?, ?> tcPeso;

    @FXML
    private Label lbCliente;

    @FXML
    private ComboBox<Serie> cbSeries;

    @FXML
    private TableView<AulaTV> tvAulas;

    @FXML
    private TableColumn<?, ?> tcNome;

    @FXML
    private Button btImprimir;

    @FXML
    private Button btVoltar;

    private Usuario usuario;

    SerieDao sDao = new SerieDao();
    AulaDao aDao = new AulaDao();
    ExercicioDao eDao = new ExercicioDao();

    ObservableList<Serie> series;
    ObservableList<AulaTV> aulas;
    ObservableList<ExercicioTV> exercicios;

    Calendar serieDataFim = Calendar.getInstance();
    Calendar hoje = Calendar.getInstance();

    Navegacao nav = new Navegacao();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = TelaBaseControlador.usuario;

        lbCliente.setText(usuario.getNome());

        TelaBaseControlador.ANTERIOR = "Login";

        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);

        sDao.abreConnection();
        List<Serie> listSeries = sDao.getLista(usuario);
        sDao.fechaConnection();

        series = FXCollections.observableArrayList();
        aulas = FXCollections.observableArrayList();
        exercicios = FXCollections.observableArrayList();

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        LocalDate ld = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        LocalDate ld2;

        for (int i = 0; i < listSeries.size(); i++) {
            cal2.setTime(listSeries.get(i).getDataFim());
            ld2 = LocalDate.of(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH) + 1, cal2.get(Calendar.DAY_OF_MONTH));
            if (!ld.isAfter(ld2)) {
                series.add(listSeries.get(i));
            }

        }

        if (series.size() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Imprimir Aula");
            alert.setContentText("Sem aulas cadastradas, converse com o seu Instrutor!");
            alert.showAndWait();
        }

        cbSeries.setItems(series);
        tvAulas.setItems(aulas);
        tvExercicios.setItems(exercicios);

        tcExercicio.setCellValueFactory(new PropertyValueFactory("nomeExercicio"));
        tcSerie.setCellValueFactory(new PropertyValueFactory("serieCompleta"));
        tcPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        tcNome.setCellValueFactory(new PropertyValueFactory("nomeAula"));

        //Action Event
        btVoltar.setOnAction(event -> nav.navega(ANTERIOR));
        btImprimir.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Imprimir Aula");
            if (tvAulas.getSelectionModel().getSelectedIndex() != -1) {

                Aula aula = tvAulas.getSelectionModel().getSelectedItem().getAula();
                aula.setImpresso(aula.getImpresso() + 1);

                aDao.abreConnection();
                aDao.atualizaAula(aula);
                aDao.fechaConnection();

                new Impressora(aula);
                alert.setContentText("Aula Impressa com Sucesso!");
                alert.showAndWait();
                nav.navega("Login");
            } else {
                alert.setContentText("Selecione uma aula para poder imprimir!");
                alert.showAndWait();
            }
        });
        cbSeries.setOnAction(event -> serieToAula(validaCb()));
        tvAulas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                aulaToExercicios();
            } else {
                exercicios.clear();
            }
        });
        //Key Event

    }

    private void aulaToExercicios() {
        if (tvAulas.getSelectionModel().getSelectedIndex() != -1) {
            exercicios.clear();
            eDao.abreConnection();
            List<Exercicio> listExercicios = eDao.getLista(tvAulas.getSelectionModel().getSelectedItem().getAula().getIdAula(), true);
            listExercicios.forEach(e -> exercicios.add(new ExercicioTV(e)));
        } else {
            exercicios.clear();
        }
    }

    private void serieToAula(Serie serie) {
        if (serie != null) {
            aDao.abreConnection();
            List<Aula> listAulas = aDao.getLista(serie);
            aDao.fechaConnection();
            aulas.clear();
            listAulas.forEach(a -> aulas.add(new AulaTV(a)));
        } else {
            aulas.clear();
        }
    }

    private Serie validaCb() {
        if (cbSeries.getSelectionModel().getSelectedItem() != null && cbSeries.getSelectionModel().getSelectedIndex() != -1) {
            return (cbSeries.getItems().get(cbSeries.getSelectionModel().getSelectedIndex()));
        }
        return null;
    }

}
