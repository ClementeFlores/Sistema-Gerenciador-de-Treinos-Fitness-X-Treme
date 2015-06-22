/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.biometria.LeitorBiometrico;
import com.auinfo.fitnessxtreme.controlador.dao.UsuarioDao;
import com.auinfo.fitnessxtreme.impressora.Impressora;
import com.auinfo.fitnessxtreme.modelo.UsuarioTV;
import com.auinfo.fitnessxtreme.modelo.Usuario;
import static com.auinfo.fitnessxtreme.util.ManipulaConfigs.getProp;
import com.auinfo.fitnessxtreme.util.Validacao;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Tiago
 */
public class CadastroUsuarioControlador implements Initializable {

    @FXML
    private TextField tfObservacao;

    @FXML
    private CheckBox cbAdministrador;

    @FXML
    private DatePicker dpCad;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableView<UsuarioTV> tvUsuario;

    @FXML
    private TableColumn<?, ?> tcMatricula;

    @FXML
    private TableColumn<?, ?> tcNome;

    @FXML
    private TableColumn<?, ?> tcEAdministrador;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private DatePicker dpNasc;

    @FXML
    private TextField tfObjetivo;

    @FXML
    private TextField tfNome;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btRemover;

    @FXML
    private Button btNovo;

    @FXML
    private AnchorPane principal;

    @FXML
    private Button btDireito;

    @FXML
    private Button btEsquerdo;

    Properties prop;
    boolean verificaDigital;

    int index = -1;

    String digDireito = null, digEsquero = null;

    ObservableList<UsuarioTV> usuarios;

    UsuarioDao uDao = new UsuarioDao();

    Navegacao nav = new Navegacao();

    FilteredList<UsuarioTV> filteredUsuarios;
    private boolean validar[] = {false, false, false, false, false, false};
    Validacao valida = new Validacao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            prop = getProp();
        } catch (IOException ex) {
            Logger.getLogger(CadastroUsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        verificaDigital = Boolean.parseBoolean(prop.getProperty("prop.biometria.digital"));

        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);

        TelaBaseControlador.ANTERIOR = "MenuPrincipal";

        validaBotoesSalvar(true);
        pfSenha.setDisable(true);
        uDao.abreConnection();
        List<Usuario> listUsuario = uDao.getLista();
        uDao.fechaConnection();

        usuarios = FXCollections.observableArrayList();

        listUsuario.forEach(u -> usuarios.add(new UsuarioTV(u)));
        filteredUsuarios = new FilteredList<>(usuarios);

        tcMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        tcNome.setCellValueFactory(new PropertyValueFactory("nome"));
        tcEAdministrador.setCellValueFactory(new PropertyValueFactory("eAdministrador"));

        tvUsuario.setItems(filteredUsuarios);

        //Action Event
        btSalvar.setOnAction(event -> cadastrar());
        btNovo.setOnAction(event -> novo());
        btRemover.setOnAction(event -> remover());
        btDireito.setOnAction(event -> digDireito = cadDigital());
        btEsquerdo.setOnAction(event -> digEsquero = cadDigital());
        cbAdministrador.setOnAction(event -> validaAdministrador());
        tvUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                usuarioToForm();
            }
        });

        //Key Released Event
        tfPesquisar.setOnKeyReleased(event -> pesquisar());

        tfMatricula.setOnKeyReleased(event -> validar[0] = valida.validaNumero(tfMatricula, 1));
        tfNome.setOnKeyReleased(event -> validar[1] = valida.validaTexto(tfNome, 10));
        tfObjetivo.setOnKeyReleased(event -> validar[3] = valida.validaTexto(tfObjetivo, 5));
        dpNasc.setOnAction(event -> validar[2] = valida.validaData(dpNasc));
        dpCad.setOnAction(event -> validar[4] = valida.validaData(dpCad));
        pfSenha.setOnKeyReleased(event -> validar[5] = valida.validaPf(pfSenha, 6));

        filteredUsuarios.setPredicate(u -> true);

    }

    private void pesquisar() {
        filteredUsuarios.setPredicate(u -> u.filtrar(tfPesquisar.getText()));
    }

    private void cadastrar() {
        if (!validar()) {
            return;
        }

        Usuario u = formToUsuario();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if ("Cadastrar".equals(btSalvar.getText())) {
            uDao.abreConnection();
            u.setIdUsuario(uDao.adicionaUsuario(u));
            if (u.getIdUsuario() != 0) {
                System.out.println("Cadastrado");
                usuarios.add(new UsuarioTV(u));
                alert.setTitle("Cadastro de Usuario");
                alert.setContentText("Usuario cadastrado com sucesso!");
                alert.showAndWait();
            } else {
                System.out.println("Erro ao cadastrar");
                alert.setTitle("Cadastro de Usuario");
                alert.setContentText("Erro ao cadastrar o Usuario!");
                alert.showAndWait();
            }
            uDao.fechaConnection();

        } else if ("Salvar".equals(btSalvar.getText())) {

            uDao.abreConnection();
            if (uDao.atualizaUsuario(u)) {
                System.out.println("Atualizado");
                usuarios.set(index, new UsuarioTV(u));

                alert.setTitle("Atualizar Usuario");
                alert.setContentText("Usuario atualizado com sucesso!");
                alert.showAndWait();

            } else {
                alert.setTitle("Cadastro de Usuario");
                alert.setContentText("Falha ao atualizadar o usuario!");
                alert.showAndWait();
            }
            uDao.fechaConnection();
            novo();
        }
    }

    private void clear() {
        tfMatricula.setStyle(valida.normal);
        tfNome.setStyle(valida.normal);
        dpNasc.setStyle(valida.normalBorda);
        tfObjetivo.setStyle(valida.normal);
        dpCad.setStyle(valida.normalBorda);
        pfSenha.setStyle(valida.normalBorda);
    }

    private void novo() {

        tfId.setText("");
        index = -1;
        tfMatricula.setText("");
        tfNome.setText("");
        dpNasc.setValue(null);
        tfObjetivo.setText("");
        tfObservacao.setText("");
        dpCad.setValue(null);
        cbAdministrador.setSelected(false);
        pfSenha.setText("");
        pfSenha.setDisable(true);
        validaBotoesSalvar(true);
        btSalvar.setText("Cadastrar");
        tfMatricula.setEditable(true);
        tvUsuario.getSelectionModel().clearSelection();
        clear();
    }

    private void remover() {
        uDao.abreConnection();
        Usuario u = formToUsuario();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Excluir Usuario");
        alert.setHeaderText("O usuario " + u.getNome() + " será excluido permanentemente!");
        alert.setContentText("Você tem certeza que deseja excluir o usuario?");
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (uDao.deletaUsuario(u)) {
                System.out.println("Removido");
                System.out.println("Removido: " + index);
                usuarios.remove(index);
                alert.setTitle("Excluir Usuario");
                alert.setHeaderText(null);
                alert.setContentText("Usuario excluido com sucesso!");
                alert.showAndWait();
            } else {
                alert.setTitle("Excluir Usuario");
                alert.setHeaderText(null);
                alert.setContentText("Falha ao excluir o usuario!");
                alert.showAndWait();
            }
        }
        uDao.fechaConnection();
        novo();
    }

    private String cadDigital() {
        String lido = null;
        String lido2 = null;
        LeitorBiometrico biometria = new LeitorBiometrico();

        lido = new LeitorBiometrico().capturar();
        lido2 = new LeitorBiometrico().capturar();

        if (verificaDigital) {
            UsuarioDao uDao = new UsuarioDao();
            Integer j = null;

            uDao.abreConnection();
            List<Usuario> listUsuario = uDao.getLista();
            uDao.fechaConnection();

            for (int i = 0; i < listUsuario.size(); i++) {

                if (biometria.verificar(listUsuario.get(i).getIndicadordireito(), lido) || biometria.verificar(listUsuario.get(i).getIndicadoresquerdo(), lido)) {
                    System.out.println("Encontrou");
                    j = i;

                    System.out.println("j= " + j);
                    break;
                }
            }

            if (j != null) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Sua digital foi encontrada em outro cadastro!");
                alert.setHeaderText(null);
                alert.setContentText("Essa digital foi encontradano cadastro do usuario: " + listUsuario.get(j).getNome());
                alert.showAndWait();
            }
        }

        while (!biometria.verificar(lido, lido2)) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Digitais diferentes!");
            alert.setHeaderText(null);
            alert.setContentText("Falha ao capturar a digital, limpe o leitor e tente novamente!");
            alert.showAndWait();
            lido = new LeitorBiometrico().capturar();
            lido2 = new LeitorBiometrico().capturar();
        }

        return lido;
    }

    private void validaAdministrador() {
        if (cbAdministrador.isSelected()) {
            pfSenha.setDisable(false);
        } else {
            pfSenha.setDisable(true);
        }
    }

    private void validaBotoesSalvar(boolean b) {
        btNovo.setDisable(b);
        btRemover.setDisable(b);
    }

    private Usuario formToUsuario() {
        Usuario usuario = new Usuario();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

        if (tfId.getText() != null && !"".equals(tfId.getText())) {
            usuario.setIdUsuario(Integer.valueOf(tfId.getText()));
        }

        usuario.setMatricula(Integer.valueOf(tfMatricula.getText()));
        usuario.setNome(tfNome.getText());
        try {
            usuario.setDatanascimento(formatador.parse(dpNasc.getValue().toString()));
            usuario.setDatacadastramento(formatador.parse(dpCad.getValue().toString()));

        } catch (ParseException ex) {
            Logger.getLogger(CadastroUsuarioControlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        usuario.setObjetivo(tfObjetivo.getText());
        usuario.setObservacao(tfObservacao.getText());
        usuario.setEadministrador(cbAdministrador.isSelected());
        usuario.setSenha(pfSenha.getText());
        usuario.setIndicadordireito(digDireito);
        usuario.setIndicadoresquerdo(digEsquero);
        clear();
        return usuario;
    }

    private void usuarioToForm() {
        Usuario usuario = tvUsuario.getSelectionModel().getSelectedItem().getUsuario();
        index = tvUsuario.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        Calendar cal = Calendar.getInstance();

        tfId.setText(usuario.getIdUsuario() + "");

        tfMatricula.setText(usuario.getMatricula() + "");
        tfNome.setText(usuario.getNome() + "");

        cal.setTime(usuario.getDatanascimento());
        dpNasc.setValue(LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));

        cal.setTime(usuario.getDatacadastramento());
        dpCad.setValue(LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));

        tfObjetivo.setText(usuario.getObjetivo() + "");
        tfObservacao.setText(usuario.getObservacao() + "");

        cbAdministrador.setSelected(usuario.getEadministrador());
        if (usuario.getEadministrador()) {
            pfSenha.setDisable(false);
        } else {
            pfSenha.setDisable(true);
        }

        digDireito = usuario.getIndicadordireito();
        digEsquero = usuario.getIndicadoresquerdo();

        pfSenha.setText(usuario.getSenha() + "");

        validaBotoesSalvar(false);
        btSalvar.setText("Salvar");
        tfMatricula.setEditable(false);
        clear();
    }

    private boolean validar() {

        validar[0] = valida.validaNumero(tfMatricula, 1);
        validar[1] = valida.validaTexto(tfNome, 10);
        validar[2] = valida.validaData(dpNasc);
        validar[3] = valida.validaTexto(tfObjetivo, 5);
        validar[4] = valida.validaData(dpCad);
        validar[5] = valida.validaPf(pfSenha, 6);

        boolean resultado = true;

        if (validar[0] == false) {
            tfMatricula.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }
        if (validar[1] == false) {
            tfNome.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }

        if (validar[2] == false) {
            dpNasc.setStyle(valida.vermelhoGradiente);
            dpNasc.setPromptText("");
            resultado = false;
        }

        if (validar[3] == false) {
            tfObjetivo.setStyle(valida.vermelhoGradiente);
            resultado = false;
        }

        if (validar[4] == false) {
            dpCad.setStyle(valida.vermelhoGradiente);
            dpNasc.setPromptText("");
            resultado = false;
        }

        if (validar[5] == false) {
            if (cbAdministrador.isSelected()) {
                dpCad.setStyle(valida.vermelhoGradiente);
                dpNasc.setPromptText("");
                resultado = false;
            }
        }

        return resultado;
    }
}
