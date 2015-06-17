/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author BrunoRicardo
 */
public class ExercicioGrupoTV {

    IntegerProperty idExercicio;
    StringProperty nomeExercicio, grupoExercicio;

    Exercicio exercicio;

    public ExercicioGrupoTV(Exercicio exercicio) {
        this.exercicio = exercicio;

        idExercicio = new SimpleIntegerProperty();
        idExercicio.set(exercicio.getIdExercicio());

        nomeExercicio = new SimpleStringProperty();
        nomeExercicio.set(exercicio.getNomeExercicio());

        grupoExercicio = new SimpleStringProperty();
        grupoExercicio.set(exercicio.getGrupo().getNomeGrupo());

    }

    public IntegerProperty idExercicioProperty() {
        return idExercicio;
    }

    public StringProperty grupoExercicioProperty() {
        return grupoExercicio;
    }

    public StringProperty nomeExercicioProperty() {
        return nomeExercicio;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public boolean filtrar(String text) {

        String dados = nomeExercicio.get() + grupoExercicio.get();

        if (dados.toUpperCase().contains(text.toUpperCase())) {
            return true;
        } else {
            return false;
        }

    }
}
