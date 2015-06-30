/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import java.time.LocalDate;
import java.util.Calendar;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author BrunoRicardo
 */
public class AulaTV {
    
    StringProperty nomeAula;
    
    Aula aula;
    
    Calendar cal = Calendar.getInstance();

    public AulaTV(Aula aula) {
        this.aula = aula;
        
        nomeAula = new SimpleStringProperty();
        nomeAula.set(aula.getDescaula());
        
    }

    public StringProperty nomeAulaProperty() {
        return nomeAula;
    }

    public Aula getAula() {
        return aula;
    }    
    
    public boolean filtrar(String text)    {
        
        String dados = nomeAula.get();
        
        if(dados.toUpperCase().contains(text.toUpperCase())){
            return true;
        }
        else {
            return false;
        }
        
    }
}