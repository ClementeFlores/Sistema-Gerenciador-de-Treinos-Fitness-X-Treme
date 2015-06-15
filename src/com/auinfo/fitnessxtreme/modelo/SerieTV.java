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
public class SerieTV {
    
    IntegerProperty idSerie, idUsuario;
    StringProperty nomeSerie, descSerie;
    DoubleProperty peso1, peso2, peso3;
    ObjectProperty<LocalDate> dataInicio, dataFim;
    
    Serie serie;
    
    Calendar cal = Calendar.getInstance();

    public SerieTV(Serie serie) {
        this.serie = serie;
        
        idSerie = new SimpleIntegerProperty();
        idSerie.set(serie.getIdSerie());
        
        idUsuario = new SimpleIntegerProperty();
        idUsuario.set(serie.getUsuario().getIdUsuario());
        
        nomeSerie = new SimpleStringProperty();
        nomeSerie.set(serie.getNomeSerie());
        
        descSerie = new SimpleStringProperty();
        descSerie.set(serie.getDescSerie());
        
        cal.setTime(serie.getDataInicio());
        dataInicio = new SimpleObjectProperty<>(LocalDate.of( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));
        
        cal.setTime(serie.getDataFim());
        dataFim = new SimpleObjectProperty<>(LocalDate.of( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));
        
        peso1 = new SimpleDoubleProperty();
        peso1.set(serie.getPeso1());
        
        peso2 = new SimpleDoubleProperty();
        peso2.set(serie.getPeso1());
        
        peso3 = new SimpleDoubleProperty();
        peso3.set(serie.getPeso1());
    }

    public IntegerProperty idUsuarioProperty() {
        return idUsuario;
    }

    public StringProperty nomeSerieProperty() {
        return nomeSerie;
    }

    public StringProperty descSerieProperty() {
        return descSerie;
    }

    public IntegerProperty idSerieProperty() {
        return idSerie;
    }

    public DoubleProperty peso1Property() {
        return peso1;
    }

    public DoubleProperty peso2Property() {
        return peso2;
    }

    public DoubleProperty peso3Property() {
        return peso3;
    }

    public ObjectProperty<LocalDate> dataInicioProperty() {
        return dataInicio;
    }

    public ObjectProperty<LocalDate> dataFimProperty() {
        return dataFim;
    }

    public Serie getSerie() {
        return serie;
    }    
    
    public boolean filtrar(String text)    {
        
        String dados = nomeSerie.get()+dataInicio.get()+dataFim.get();
        
        if(dados.toUpperCase().contains(text.toUpperCase())){
            return true;
        }
        else {
            return false;
        }
        
    }
}