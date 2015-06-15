/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.controlador.dao.AulaDao;
import com.auinfo.fitnessxtreme.controlador.dao.SerieDao;
import com.auinfo.fitnessxtreme.controlador.dao.UsuarioDao;
import com.auinfo.fitnessxtreme.modelo.Aula;
import com.auinfo.fitnessxtreme.modelo.Serie;
import com.auinfo.fitnessxtreme.modelo.SerieTV;
import com.auinfo.fitnessxtreme.modelo.Usuario;
import com.auinfo.fitnessxtreme.util.AutoCompleteComboBoxListener;
import com.auinfo.fitnessxtreme.util.Validacao;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author BrunoRicardo
 */
public class ConsultaSeriesControlador implements Initializable {

    public static Serie SERIE;
    public static Usuario USUARIO;

    @FXML
    private ComboBox<Usuario> cbClientes;

    @FXML
    private TextField tfPeso3;

    @FXML
    private Button btSalvar;

    @FXML
    private DatePicker dpFim;

    @FXML
    private TableColumn<Serie, Boolean> tcCadastrarAula;

    @FXML
    private TableColumn<Serie, Boolean> tcVerAulas;

    @FXML
    private TextField tfDesc;

    @FXML
    private TableColumn<?, ?> tcSerie;

    @FXML
    private Button btNovo;

    @FXML
    private DatePicker dpInicio;

    @FXML
    private TableView<SerieTV> tvSerie;

    @FXML
    private TextField tfPeso1;

    @FXML
    private TextField tfPeso2;

    @FXML
    private Button btRemover;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfId;

    @FXML
    private AnchorPane principal;

    int index = -1;

    private boolean validar[] = {false, false, false, false};
    Validacao valida = new Validacao();

    ObservableList<SerieTV> series;
    ObservableList<Usuario> usuariosCb;

    AulaDao aDao = new AulaDao();
    SerieDao sDao = new SerieDao();
    UsuarioDao uDao = new UsuarioDao();

    FilteredList<SerieTV> filteredSeries;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);

        TelaBaseControlador.ANTERIOR = "MenuPrincipal";

        validaBotoesSalvar(true);
        uDao.abreConnection();
        List<Usuario> listUsuario = uDao.getLista();
        uDao.fechaConnection();

        usuariosCb = FXCollections.observableArrayList();
        listUsuario.forEach(u -> usuariosCb.add(u));

        cbClientes.setItems(usuariosCb);

        tcSerie.setCellValueFactory(new PropertyValueFactory("nomeSerie"));

        // define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.
        tcCadastrarAula.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Serie, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Serie, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });

        tcVerAulas.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Serie, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Serie, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });

        // create a cell value factory with an add button for each row in the tvSerie.
        tcCadastrarAula.setCellFactory(new Callback<TableColumn<Serie, Boolean>, TableCell<Serie, Boolean>>() {
            @Override
            public TableCell<Serie, Boolean> call(TableColumn<Serie, Boolean> personBooleanTableColumn) {
                return new BotaoTabela(tvSerie, "novaAula");
            }
        });

        tcVerAulas.setCellFactory(new Callback<TableColumn<Serie, Boolean>, TableCell<Serie, Boolean>>() {
            @Override
            public TableCell<Serie, Boolean> call(TableColumn<Serie, Boolean> personBooleanTableColumn) {
                return new BotaoTabela(tvSerie, "verAulas");
            }
        });

        //Action Event
        btNovo.setOnAction(event -> novo());
        btRemover.setOnAction(event -> remover());
        btSalvar.setOnAction(event -> cadastrar());
        cbClientes.setOnAction(event -> buscaSeries());

        tvSerie.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                serieToForm();
            }
        });
        dpInicio.setOnAction(event -> validar[2] = valida.validaData(dpInicio));
        dpFim.setOnAction(event -> validar[3] = valida.validaData(dpFim));

        //Key Released Event
        new AutoCompleteComboBoxListener<>(cbClientes);
        tfNome.setOnKeyReleased(event -> validar[0] = valida.validaTexto(tfNome, 5));
        tfDesc.setOnKeyReleased(event -> validar[1] = valida.validaTexto(tfDesc, 0));

    }

    private void buscaSeries() {
        if (!cbClientes.isShowing()) {
            Usuario u = validaCb();
            USUARIO = u;
            if (u != null) {
                sDao.abreConnection();
                List<Serie> listSerie = sDao.getLista(u);
                sDao.fechaConnection();

                series = FXCollections.observableArrayList();

                listSerie.forEach(s -> series.add(new SerieTV(s)));
                filteredSeries = new FilteredList<>(series);
                tvSerie.setItems(series);
            }
        }
    }

    private void novo() {
        tfId.setText("");
        index = -1;
        tfNome.setText("");
        tfDesc.setText("");
        tfPeso1.setText("");
        tfPeso2.setText("");
        tfPeso3.setText("");
        dpInicio.setValue(null);
        dpFim.setValue(null);
        validaBotoesSalvar(true);
        btSalvar.setText("Cadastrar");
        tvSerie.getSelectionModel().clearSelection();
        clear();
    }

    private void remover() {
        Serie s = formToSerie();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Excluir Série");
        alert.setHeaderText("A serie " + s.getNomeSerie() + " será excluida permanentemente!");
        alert.setContentText("Você tem certeza que deseja excluir a série?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            aDao.abreConnection();
            List<Aula> listAulas = aDao.getLista(s);
            for (Aula aula : listAulas) {
                aDao.deletaAula(aula);
            }
            aDao.fechaConnection();

            sDao.abreConnection();
            if (sDao.deletaSerie(s)) {
                System.out.println("Removido");
                System.out.println("Removido: " + index);
                series.remove(index);

                alert.setTitle("Excluir Série");
                alert.setHeaderText(null);
                alert.setContentText("Série removida com sucesso!");
                alert.showAndWait();
            } else {
                alert.setTitle("Excluir Série");
                alert.setHeaderText(null);
                alert.setContentText("Falha ao remover a série!");
                alert.showAndWait();
            }
            sDao.fechaConnection();
            novo();
        }
    }

    private void cadastrar() {

        if (!validar()) {
            return;
        }

        if (validaCb() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Cadastro de Série");
            alert.setContentText("Você precisa selecionar um cliente antes de cadastrar a série!");
            alert.showAndWait();
            return;
        }
        Serie s = formToSerie();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if ("Cadastrar".equals(btSalvar.getText())) {
            sDao.abreConnection();
            s.setIdSerie(sDao.adicionaSerie(s));
            if (s.getIdSerie() != 0) {
                System.out.println("Cadastrado");
                series.add(new SerieTV(s));

                alert.setTitle("Cadastro de Série");
                alert.setContentText("Série cadastrada com sucesso!");
                alert.showAndWait();
            } else {
                alert.setTitle("Cadastro de Série");
                alert.setContentText("Falha ao cadastrar série!");
                alert.showAndWait();
            }
            sDao.fechaConnection();
            novo();
        } else if ("Salvar".equals(btSalvar.getText())) {
            sDao.abreConnection();
            if (sDao.atualizaSerie(s)) {
                System.out.println("Atualizado");
                series.set(index, new SerieTV(s));
                alert.setTitle("Atualização de Série");
                alert.setContentText("Série atualizada com sucesso!");
                alert.showAndWait();
            } else {
                alert.setTitle("Atualização de Série");
                alert.setContentText("Falha ao atualizar asérie!");
                alert.showAndWait();
            }
            sDao.fechaConnection();
            novo();
        }
    }

    private void serieToForm() {
        Serie serie = tvSerie.getSelectionModel().getSelectedItem().getSerie();
        index = tvSerie.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        Calendar cal = Calendar.getInstance();

        tfId.setText(serie.getIdSerie() + "");

        tfNome.setText(serie.getNomeSerie());
        tfDesc.setText(serie.getDescSerie());
        tfPeso1.setText(serie.getPeso1() + "");
        tfPeso2.setText(serie.getPeso2() + "");
        tfPeso3.setText(serie.getPeso3() + "");

        cal.setTime(serie.getDataInicio());
        dpInicio.setValue(LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));

        cal.setTime(serie.getDataFim());
        dpFim.setValue(LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));

        validaBotoesSalvar(false);
        btSalvar.setText("Salvar");
        clear();
    }

    private Usuario validaCb() {
        if (cbClientes.getSelectionModel().getSelectedItem() != null && cbClientes.getSelectionModel().getSelectedIndex() != -1) {
            return (cbClientes.getItems().get(cbClientes.getSelectionModel().getSelectedIndex()));
        }
        return null;
    }

    private Serie formToSerie() {

        Serie serie = new Serie();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

        if (cbClientes.getSelectionModel().getSelectedItem() != null && cbClientes.getSelectionModel().getSelectedIndex() != -1) {
            serie.setUsuario(validaCb());
        }

        if (tfId.getText() != null && !"".equals(tfId.getText())) {
            serie.setIdSerie(Integer.valueOf(tfId.getText()));
        }

        serie.setNomeSerie(tfNome.getText());
        serie.setDescSerie(tfDesc.getText());

        if ("".equals(tfPeso1.getText())) {
            serie.setPeso1(0.0);
        } else {
            serie.setPeso1(Double.valueOf(tfPeso1.getText()));
        }

        if ("".equals(tfPeso2.getText())) {
            serie.setPeso2(0.0);
        } else {
            serie.setPeso2(Double.valueOf(tfPeso2.getText()));
        }

        if ("".equals(tfPeso3.getText())) {
            serie.setPeso3(0.0);
        } else {
            serie.setPeso3(Double.valueOf(tfPeso3.getText()));
        }

        try {
            serie.setDataInicio(formatador.parse(dpInicio.getValue().toString()));
            serie.setDataFim(formatador.parse(dpFim.getValue().toString()));

        } catch (ParseException ex) {
            Logger.getLogger(ConsultaSeriesControlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        clear();
        return serie;
    }

    private void validaBotoesSalvar(boolean b) {
        btNovo.setDisable(b);
        btRemover.setDisable(b);
    }

    public void clear() {
        tfNome.setStyle(valida.normal);
        tfDesc.setStyle(valida.normal);
        tfPeso1.setStyle(valida.normal);
        tfPeso2.setStyle(valida.normal);
        tfPeso3.setStyle(valida.normal);
        dpInicio.setStyle(valida.normalBorda);
        dpFim.setStyle(valida.normalBorda);
    }

    private boolean validar() {
        boolean resultado = true;

        if (validar[0] == false) {
            tfNome.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }
        if (validar[1] == false) {
            tfDesc.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }

        if (validar[2] == false) {
            dpInicio.setStyle(valida.vermelhoGradiente);
            dpInicio.setPromptText("");
            resultado = false;
        }

        if (validar[3] == false) {
            dpFim.setStyle(valida.vermelhoGradiente);
            dpFim.setPromptText("");
            resultado = false;
        }

        if (!tfPeso1.getText().isEmpty()) {
            Double test = null;
            test = Double.parseDouble(tfPeso1.getText());
            if (test != null) {
                tfPeso1.setStyle(valida.normal);
            } else {
                tfPeso1.setStyle(valida.vermelhoGradiente);
            }
            resultado = false;
        }

        if (!tfPeso2.getText().isEmpty()) {
            Double test = null;
            test = Double.parseDouble(tfPeso2.getText());
            if (test != null) {
                tfPeso2.setStyle(valida.normal);
            } else {
                tfPeso2.setStyle(valida.vermelhoGradiente);
            }
            resultado = false;
        }

        if (!tfPeso3.getText().isEmpty()) {
            Double test = null;
            test = Double.parseDouble(tfPeso3.getText());
            if (test != null) {
                tfPeso3.setStyle(valida.normal);
            } else {
                tfPeso3.setStyle(valida.vermelhoGradiente);
            }
            resultado = false;
        }

        return resultado;

    }

}

/**
 * A tvSerie cell containing a button for adding a new person.
 */
class BotaoTabela extends TableCell<Serie, Boolean> {

    TableView<SerieTV> tvSerie;
    // a button for adding a new person.
    final Button btNovaAula = new Button("Nova Aula");

    final Button btVerAulas = new Button("Ver Aulas");
    // pads and centers the add button in the cell.
    final StackPane paddedButton = new StackPane();
    // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
    final DoubleProperty buttonY = new SimpleDoubleProperty();

    Navegacao nav = new Navegacao();

    /**
     * AddPersonCell constructor
     *
     * @param stage the stage in which the tvSerie is placed.
     * @param table the tvSerie to which a new person can be added.
     */
    BotaoTabela(TableView table, String coluna) {
        this.tvSerie = table;
        if (null != coluna) {
            switch (coluna) {
                case "novaAula":
                    novaAula();
                    break;
                case "verAulas":
                    verAulaS();
                    break;
            }
        }
    }

    private void novaAula() {
        paddedButton.getChildren().add(btNovaAula);
        btNovaAula.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buttonY.set(mouseEvent.getScreenY());
            }
        });
        btNovaAula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tvSerie.getSelectionModel().select(getTableRow().getIndex());
                ConsultaSeriesControlador.SERIE = tvSerie.getSelectionModel().getSelectedItem().getSerie();
                System.out.println(tvSerie.getSelectionModel().getSelectedIndex());
                CadastroAulaV2Controlador.AULA = null;
                nav.navega("CadastroAula");

            }
        });
    }

    private void verAulaS() {
        paddedButton.getChildren().add(btVerAulas);
        btVerAulas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buttonY.set(mouseEvent.getScreenY());
            }
        });
        btVerAulas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tvSerie.getSelectionModel().select(getTableRow().getIndex());
                ConsultaSeriesControlador.SERIE = tvSerie.getSelectionModel().getSelectedItem().getSerie();
                System.out.println(tvSerie.getSelectionModel().getSelectedIndex());
                nav.navega("ConsultaAula");
            }
        });
    }

    /**
     * places an add button in the row only if the row is not empty.
     */
    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(paddedButton);
        } else {
            setGraphic(null);
        }
    }
}
