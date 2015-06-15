/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.util;

import com.auinfo.fitnessxtreme.FitnessXTreme;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author BrunoRicardo
 */
public class Validacao {

    public String vermelhoGradiente = "-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);";
    public String normal = "-fx-background-color: white;";
    public String vermelhoBorda = "-fx-border-color: red;";
    public String normalBorda = "-fx-border-color: rgb(181, 181, 181);";

    public boolean validaTexto(TextField tf, int i) {
        if (tf.getText().isEmpty() || tf.getText().length() < i) {
            tf.setStyle(vermelhoGradiente);
            tf.setPromptText("");
            return false;
        } else {
            tf.setStyle(normal);
            return true;
        }
    }

    public boolean validaNumero(TextField tf, int i) {
        if (tf.getText().isEmpty() || !tf.getText().matches("\\d+") || tf.getText().length() < i) {
            tf.setStyle(vermelhoGradiente);
            tf.setPromptText("");
            return false;
        } else {
            tf.setStyle(normal);
            return true;
        }
    }

    public boolean validaNumeroDouble(TextField tf, int i) {
        if (tf.getText().isEmpty() || !tf.getText().matches("\\d\\.\\d") || tf.getText().length() < i) {
            tf.setStyle(vermelhoGradiente);
            tf.setPromptText("");
            return false;
        } else {
            tf.setStyle(normal);
            return true;
        }
    }

    public boolean validaCb(ComboBox cb) {
        if (cb.getSelectionModel().getSelectedIndex() != -1) {
            cb.setStyle(normalBorda);
            return true;
        } else {
            cb.setStyle(vermelhoBorda);
            return false;
        }
    }

    public boolean validaData(DatePicker dp) {
        if (dp.getValue() == null) {
            dp.setStyle(vermelhoGradiente);
            return false;
        } else {
            dp.setStyle(normal);
            return true;
        }
    }

    public boolean validaPf(PasswordField pf, int i) {
        if (pf.getText().length() < i) {
            pf.setStyle(vermelhoGradiente);
            pf.setPromptText("");
            return false;
        } else {
            pf.setStyle(normal);
            return true;
        }
    }

    public void validaCheckBoxActionEvent(CheckBox cb) {
        System.out.println("PQPPPPPPPPPPP");
        TextField tfSerie = (TextField) FitnessXTreme.scenes.lookup("#" + (Integer.valueOf(cb.getId()) + 100));
        TextField tfQtd = (TextField) FitnessXTreme.scenes.lookup("#" + (Integer.valueOf(cb.getId()) + 200));
        TextField tfPeso = (TextField) FitnessXTreme.scenes.lookup("#" + (Integer.valueOf(cb.getId()) + 300));
        
        if (cb.isSelected()) {

            tfSerie.setDisable(false);
            tfSerie.setText("");

            tfQtd.setDisable(false);
            tfQtd.setText("");

            tfPeso.setDisable(false);
            tfPeso.setText("");
        } else {
            
            tfSerie.setDisable(true);

            tfQtd.setDisable(true);

            tfPeso.setDisable(true);
        }

    }

}
