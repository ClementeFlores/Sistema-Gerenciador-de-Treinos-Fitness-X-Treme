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
public class ExercicioTV {
    
    IntegerProperty idExercicio, serie, quantidade, peso;
    StringProperty nomeExercicio, serieCompleta;
    
    Exercicio exercicio;
    
    public ExercicioTV(Exercicio exercicio) {
        this.exercicio = exercicio;
        
        idExercicio = new SimpleIntegerProperty();
        idExercicio.set(exercicio.getIdExercicio());
        
        serie = new SimpleIntegerProperty();
        serie.set(exercicio.getSerie());
        
        quantidade = new SimpleIntegerProperty();
        quantidade.set(exercicio.getQuantidade());
        
        peso = new SimpleIntegerProperty();
        peso.set(exercicio.getPeso());
        
        nomeExercicio = new SimpleStringProperty();
        nomeExercicio.set(exercicio.getNomeExercicio());
        
        serieCompleta = new SimpleStringProperty();
        serieCompleta.set(exercicio.getSerie() + " x " + exercicio.getQuantidade());
        
    }

    public IntegerProperty idExercicioProperty() {
        return idExercicio;
    }

    public StringProperty serieCompletaProperty() {
        return serieCompleta;
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public IntegerProperty pesoProperty() {
        return peso;
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
    
    public boolean filtrar(String text)    {
        
        String dados = nomeExercicio.get();
        
        if(dados.toUpperCase().contains(text.toUpperCase())){
            return true;
        }
        else {
            return false;
        }
        
    }
}