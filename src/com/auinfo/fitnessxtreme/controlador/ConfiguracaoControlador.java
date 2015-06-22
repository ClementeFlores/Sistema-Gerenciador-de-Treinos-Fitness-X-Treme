/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador;

import com.auinfo.fitnessxtreme.util.ManipulaConfigs;
import static com.auinfo.fitnessxtreme.util.ManipulaConfigs.getProp;
import com.auinfo.fitnessxtreme.util.Validacao;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author BrunoRicardo
 */
public class ConfiguracaoControlador implements Initializable {

    @FXML
    private AnchorPane principal;

    @FXML
    private ComboBox<String> cbModelo;

    @FXML
    private RadioButton cbEthernet;

    @FXML
    private ToggleGroup inter;

    @FXML
    private RadioButton cbParalela;

    @FXML
    private RadioButton cbUsb;

    @FXML
    private TextField tfIp;

    @FXML
    private RadioButton cbSim;

    @FXML
    private ToggleGroup logo;

    @FXML
    private RadioButton cbNao;

    @FXML
    private RadioButton cbLeitorSim;

    @FXML
    private ToggleGroup bio;

    @FXML
    private RadioButton cbLeitorNao;

    @FXML
    private RadioButton cbX86;

    @FXML
    private ToggleGroup sistema;

    @FXML
    private RadioButton cbX64;

    @FXML
    private Button btSalvar;

    Validacao valida = new Validacao();

    private Properties prop;
    private int modeloImpressora;
    private String interfaceImpressora;
    private String enderecoImpressora;
    private boolean imprimirLogo;
    private boolean verificaDigital;
    private boolean sistema32bits;
    private boolean[] validar = {false, false, false, false, false, false};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        AnchorPane.setTopAnchor(principal, 0d);
        AnchorPane.setRightAnchor(principal, 0d);
        AnchorPane.setLeftAnchor(principal, 0d);
        AnchorPane.setBottomAnchor(principal, 0d);

        TelaBaseControlador.ANTERIOR = "MenuPrincipal";

        try {
            prop = getProp();
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracaoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbModelo.getItems().addAll(new String[]{"MP-4000 TH", "MP-4200 TH"});

        preencheCampos();

        //Action Event
        cbEthernet.setOnAction(event -> validaEthernet());
        cbParalela.setOnAction(event -> validaEthernet());
        cbUsb.setOnAction(event -> validaEthernet());
        btSalvar.setOnAction(event -> salvar());

    }

    private void preencheCampos() {
        //Recuperando configurações do arquivo para as variáveis;
        modeloImpressora = Integer.parseInt(prop.getProperty("prop.impressora.modelo"));

        interfaceImpressora = prop.getProperty("prop.impressora.interface");

        enderecoImpressora = prop.getProperty("prop.impressora.endereco");

        imprimirLogo = Boolean.parseBoolean(prop.getProperty("prop.impressora.logo"));

        verificaDigital = Boolean.parseBoolean(prop.getProperty("prop.biometria.digital"));

        sistema32bits = Boolean.parseBoolean(prop.getProperty("prop.sistema.sistema32bits"));

        //Configurando a opção selecionada na Combo Box
        switch (modeloImpressora) {
            case 5:
                cbModelo.getSelectionModel().select(0);
                break;
            case 7:
                cbModelo.getSelectionModel().select(1);
                break;
            default:
                cbModelo.getSelectionModel().clearSelection();
                cbModelo.setValue(null);
                ;
        }

        //Configurando qual RadioButton iniciará selecionado
        switch (interfaceImpressora) {
            case "ethernet":
                cbEthernet.setSelected(true);
                break;
            case "paralela":
                cbParalela.setSelected(true);
                break;
            case "usb":
                cbUsb.setSelected(true);
                break;
            default:
                cbUsb.getToggleGroup().selectToggle(null);
        }

        //Ativa o texto e campo de endereço no caso da opção Ethernet ser selecionada, bem como colocar o valor do endereço no campo
        if (cbEthernet.isSelected()) {
            tfIp.setDisable(false);
            tfIp.setText(enderecoImpressora);
        }

        if (imprimirLogo == true) {
            cbSim.setSelected(true);
        } else {
            cbNao.setSelected(true);
        }

        if (verificaDigital) {
            cbLeitorSim.setSelected(true);
        } else {
            cbLeitorNao.setSelected(true);
        }

        if (sistema32bits) {
            cbX86.setSelected(true);
        } else {
            cbX64.setSelected(true);
        }
    }

    private boolean salvarConfigs() {
        switch (cbModelo.getSelectionModel().getSelectedIndex()) {
            case -1:
                valida.validaCb(cbModelo);
                return false;
            case 0:
                modeloImpressora = 5;
                break;
            case 1:
                modeloImpressora = 7;
                break;
            default:
                valida.validaCb(cbModelo);
                return false;
        }
        if (cbEthernet.isSelected()) {
            interfaceImpressora = "ethernet";
            enderecoImpressora = tfIp.getText();
        } else if (cbParalela.isSelected()) {
            interfaceImpressora = "paralela";
            enderecoImpressora = "LPT1";
        } else if (cbUsb.isSelected()) {
            interfaceImpressora = "usb";
            enderecoImpressora = "USB";
        }

        if (cbSim.isSelected()) {
            imprimirLogo = true;
        } else {
            imprimirLogo = false;
        }

        if (cbLeitorSim.isSelected()) {
            verificaDigital = true;
        } else {
            verificaDigital = false;
        }

        if (cbX86.isSelected()) {
            sistema32bits = true;
        } else {
            sistema32bits = false;
        }

        try {
            validar[0] = ManipulaConfigs.setProp("prop.impressora.modelo", String.valueOf(modeloImpressora));
            validar[1] = ManipulaConfigs.setProp("prop.impressora.interface", interfaceImpressora);
            validar[2] = ManipulaConfigs.setProp("prop.impressora.endereco", enderecoImpressora);
            validar[3] = ManipulaConfigs.setProp("prop.impressora.logo", imprimirLogo + "");
            validar[4] = ManipulaConfigs.setProp("prop.biometria.digital", verificaDigital + "");
            validar[5] = ManipulaConfigs.setProp("prop.sistema.sistema32bits", sistema32bits + "");
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracaoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < 3; i++) {
            if (validar[i] == false) {
                return false;
            }
        }
        return true;
    }

    private void validaEthernet() {
        if (cbEthernet.isSelected()) {
            tfIp.setDisable(false);
        } else {
            tfIp.setText("");
            tfIp.setDisable(true);
        }
    }

    private void salvar() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Configurações");

        if (salvarConfigs()) {
            alert.setContentText("Configurações salvas com sucesso!");
            alert.showAndWait();
        } else {
            alert.setContentText("Falha ao salvar configurações!");
            alert.showAndWait();
        }
    }

}
