/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.FitnessXTreme;
import com.auinfo.fitnessxtreme.controlador.dao.AulaDao;
import com.auinfo.fitnessxtreme.modelo.Aula;
import com.auinfo.fitnessxtreme.modelo.Exercicio;
import com.auinfo.fitnessxtreme.modelo.Serie;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author BrunoRicardo
 */
public class CadastroAulaControlador implements Initializable {

    @FXML
    private CheckBox cbRoscaPunhoInversa;

    @FXML
    private CheckBox cbRoscaDireta;

    @FXML
    private CheckBox cbFrancesaC;

    @FXML
    private CheckBox cbTricepsCorda;

    @FXML
    private CheckBox cbLegPressH;

    @FXML
    private CheckBox cbLegPress45;

    @FXML
    private CheckBox cbTricepsCoice;

    @FXML
    private CheckBox cbFrancesaH;

    @FXML
    private CheckBox cbSerrote;

    @FXML
    private CheckBox cbRoscaScotch;

    @FXML
    private CheckBox cbEncolhimento;

    @FXML
    private CheckBox cbAbdutor;

    @FXML
    private CheckBox cbFlexaoP;

    @FXML
    private CheckBox cbAgachamento;

    @FXML
    private CheckBox cbFlexaoS;

    @FXML
    private CheckBox cbTricepsRoldana;

    @FXML
    private CheckBox cbSupino;

    @FXML
    private CheckBox cbFlexaoD;

    @FXML
    private CheckBox cbAdutor;

    @FXML
    private CheckBox cbPullDown;

    @FXML
    private CheckBox cbPuxada;

    @FXML
    private CheckBox cbVoadorInvertido;

    @FXML
    private CheckBox cbTricepsTesta;

    @FXML
    private CheckBox cbGluteo;

    @FXML
    private CheckBox cbRemadaAberta;

    @FXML
    private CheckBox cb4Apoios;

    @FXML
    private CheckBox cbPanturrilha;

    @FXML
    private CheckBox cbRoscaMartelo;

    @FXML
    private CheckBox cbStiff;

    @FXML
    private CheckBox cbTricepsBanco;

    @FXML
    private CheckBox cbInfra;

    @FXML
    private CheckBox cbSupra;

    @FXML
    private CheckBox cbCrossOver;

    @FXML
    private CheckBox cbTricepsParalela;

    @FXML
    private CheckBox cbFlexao;

    @FXML
    private CheckBox cbRoldanaFrontal;

    @FXML
    private CheckBox cbCrucifixo;

    @FXML
    private CheckBox cbRoscaPunho;
    @FXML
    private CheckBox cbSupinoInclinado;

    @FXML
    private CheckBox cbRemadaFechada;

    @FXML
    private CheckBox cbCavalinho;

    @FXML
    private CheckBox cbAgachamentoH;

    @FXML
    private CheckBox cbFrancesa;

    @FXML
    private CheckBox cbRemadaP;

    @FXML
    private CheckBox cbRosca;

    @FXML
    private CheckBox cbDesenvolvimento;

    @FXML
    private CheckBox cbAgachamentoS;

    @FXML
    private CheckBox cbRoscaConcentrada;

    @FXML
    private CheckBox cbExtensao;

    @FXML
    private CheckBox cbLevantamentoLateral;

    @FXML
    private CheckBox cbLevantamentoFrontal;

    @FXML
    private CheckBox cbAvanco;

    @FXML
    private CheckBox cbSupinoDeclinado;

    @FXML
    private CheckBox cbCrucifixoInclinado;

    @FXML
    private CheckBox cbRoscaAlternada;

    @FXML
    private CheckBox cbGluteoPolia;

    @FXML
    private CheckBox cbObliquo;

    @FXML
    private CheckBox cbCruzamentoCabos;

    @FXML
    private CheckBox cbLombar;

    @FXML
    private CheckBox cbVoador;

    @FXML
    private TitledPane tpAnteBraco;

    @FXML
    private Label lbObservacao;

    @FXML
    private TitledPane tpTriceps;

    @FXML
    private Label lbNome;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btCancelar;

    @FXML
    private TitledPane tpGluteos;

    @FXML
    private Label lbDtCadastrado;

    @FXML
    private Label lbDtInicio;

    @FXML
    private Label lbObjetivo;

    @FXML
    private TitledPane tpBiceps;

    @FXML
    private TitledPane tpOmbros;

    @FXML
    private TitledPane tpAbdomen;

    @FXML
    private ComboBox<String> cbDiaSemana;

    @FXML
    private TitledPane tpCostas;

    @FXML
    private Label lbIdade;

    @FXML
    private Accordion acExercicios;

    @FXML
    private TitledPane tpPernas;

    @FXML
    private TitledPane tpPeito;

    @FXML
    private Label lbDtFim;

    @FXML
    private AnchorPane principal;

    @FXML
    private TextField tfDescAula;

    @FXML
    private TextField tfElipticon;

    @FXML
    private TextField tfEsteira;

    @FXML
    private TextField tfBicicleta;

    public static Aula AULA = new Aula();
    private AulaDao aDao = new AulaDao();
    private Navegacao nav = new Navegacao();
    private boolean validar[] = {false, false, false, false, false};
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

        tpAbdomen.setOnMouseClicked((MouseEvent event) -> {
            retrair(tpAbdomen);
        });

        tpAnteBraco.setOnMouseClicked((MouseEvent event) -> {
            retrair(tpAnteBraco);
        });

        tpBiceps.setOnMouseClicked((MouseEvent event) -> {
            retrair(tpBiceps);
        });

        tpCostas.setOnMouseClicked((MouseEvent event) -> {
            retrair(tpCostas);
        });

        tpGluteos.setOnMouseClicked((MouseEvent event) -> {
            retrair(tpGluteos);
        });

        tpOmbros.setOnMouseClicked((MouseEvent event) -> {
            retrair(tpOmbros);
        });

        tpPeito.setOnMouseClicked((MouseEvent event) -> {
            retrair(tpPeito);
        });

        tpPernas.setOnMouseClicked((MouseEvent event) -> {
            retrair(tpPernas);
        });

        tpTriceps.setOnMouseClicked((MouseEvent event) -> {
            retrair(tpTriceps);
        });

        btCadastrar.setOnAction(event -> cadastrar());
        btCancelar.setOnAction(event -> cancelar());
        cbDiaSemana.setOnAction(event -> validar[4] = valida.validaCb(cbDiaSemana));

        tfDescAula.setOnKeyReleased(event -> validar[0] = valida.validaTexto(tfDescAula, 5));
        tfEsteira.setOnKeyReleased(event -> validar[1] = valida.validaNumero(tfEsteira, 1));
        tfBicicleta.setOnKeyReleased(event -> validar[2] = valida.validaNumero(tfBicicleta, 1));
        tfElipticon.setOnKeyReleased(event -> validar[3] = valida.validaNumero(tfElipticon, 1));

        tfDescAula.requestFocus();

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                System.out.println("OIIII");
                if (AULA != null) {
                    btCadastrar.setText("Salvar");
                    editarAula();
                } else {
                    /*
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                    cb.setOnAction(event -> valida.validaCheckBoxActionEvent(cb));
                            */
                }
            }
        });

        System.out.println("End Inicialization");

    }

    private boolean validar() {
        boolean resultado = true;

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

    private void retrair(TitledPane expandir) {
        TitledPane retrair = acExercicios.getExpandedPane();
        if (expandir.equals(retrair)) {
            retrair.setExpanded(false);
            expandir.setExpanded(true);
        }
    }

    private void preencheAula() {
        AULA.setDescaula(tfDescAula.getText());
        AULA.setImpresso(0);
        AULA.setRepetir(validaCb());
        AULA.setSerie(ConsultaSeriesControlador.SERIE);
        AULA.setTempoelipticon(Integer.valueOf(tfElipticon.getText()));
        AULA.setTempobicicleta(Integer.valueOf(tfBicicleta.getText()));
        AULA.setTempoesteira(Integer.valueOf(tfEsteira.getText()));
        AULA.setExercicioList(preencheCheckBox());
    }

    private void cadastrar() {
        if (!validar()) {
            return;
        }
        Alert alert = new Alert(AlertType.INFORMATION);
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
        TelaBaseControlador.BORDERPANE.setBottom(TelaBaseControlador.getBottom());
        nav.navega("ConsultaAula");
    }

    private void cancelar() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Cadastro de Aula");
        alert.setHeaderText("Os dados preenchidos serão perdidos!");
        alert.setContentText("Você tem certeza que deseja cancelar o cadastramento da aula?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            TelaBaseControlador.BORDERPANE.setBottom(TelaBaseControlador.getBottom());
            nav.navega("ConsultaAula");
        }
    }

    private List<Exercicio> preencheCheckBox() {
        Exercicio exercicio = null;
        List<Exercicio> listExercicios = new ArrayList<>();
        CheckBox cb;
        TextField tfSerie;
        TextField tfQtd;
        TextField tfPeso;

        for (int i = 1; i < 61; i++) {
            cb = (CheckBox) FitnessXTreme.scenes.lookup("#" + i);
            tfSerie = (TextField) FitnessXTreme.scenes.lookup("#" + (i + 100));
            tfQtd = (TextField) FitnessXTreme.scenes.lookup("#" + (i + 200));
            tfPeso = (TextField) FitnessXTreme.scenes.lookup("#" + (i + 300));

            exercicio = preencheExercicio(cb, tfSerie, tfQtd, tfPeso);

            if (exercicio != null) {
                listExercicios.add(exercicio);
            }
        }

        return listExercicios;
    }

    private String validaCb() {
        if (cbDiaSemana.getSelectionModel().getSelectedItem() != null && cbDiaSemana.getSelectionModel().getSelectedIndex() != -1) {
            return (cbDiaSemana.getItems().get(cbDiaSemana.getSelectionModel().getSelectedIndex()));
        }
        return null;
    }

    private Exercicio preencheExercicio(CheckBox cb, TextField tfSerie, TextField tfQtd, TextField tfPeso) {
        Exercicio exercicio;
        if (cb.isSelected()) {
            exercicio = new Exercicio();
            exercicio.setIdExercicio(Integer.valueOf(cb.getId()));
            if (tfSerie.getText() != null && !"".equals(tfSerie.getText())) {
                exercicio.setSerie(Integer.valueOf(tfSerie.getText()));
            } else {
            }
            if (tfQtd.getText() != null && !"".equals(tfQtd.getText())) {
                exercicio.setQuantidade(Integer.valueOf(tfQtd.getText()));
            } else {
            }
            if (tfPeso.getText() != null && !"".equals(tfPeso.getText())) {
                exercicio.setPeso(Integer.valueOf(tfPeso.getText()));
            } else {
            }

            return exercicio;
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

        CheckBox cb;
        TextField tfSerie;
        TextField tfQtd;
        TextField tfPeso;

        for (Exercicio e : AULA.getExercicioList()) {

            cb = (CheckBox) FitnessXTreme.scenes.lookup("#" + e.getIdExercicio());
            cb.setSelected(true);

            tfSerie = (TextField) FitnessXTreme.scenes.lookup("#" + (e.getIdExercicio() + 100));
            tfSerie.setText(e.getSerie() + "");

            tfQtd = (TextField) FitnessXTreme.scenes.lookup("#" + (e.getIdExercicio() + 200));
            tfQtd.setText(e.getQuantidade() + "");

            tfPeso = (TextField) FitnessXTreme.scenes.lookup("#" + (e.getIdExercicio() + 300));
            tfPeso.setText(e.getPeso() + "");

        }
    }
}
