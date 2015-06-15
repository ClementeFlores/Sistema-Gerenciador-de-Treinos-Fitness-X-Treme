/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import java.util.List;
/**
 *
 * @author BrunoRicardo
 */
public class Aula {
    private int idAula;
    private String descAula;
    private String repetir;
    private Integer tempoEsteira;
    private Integer tempoBicicleta;
    private Integer tempoElipticon;
    private int impresso;
    private List<Exercicio> exercicioList;
    private Serie serie;

    public Aula() {
    }

    public Aula(int idaula) {
        this.idAula = idaula;
    }

    public Aula(int idaula, String descaula, String repetir, int impresso) {
        this.idAula = idaula;
        this.descAula = descaula;
        this.repetir = repetir;
        this.impresso = impresso;
    }

    public int getIdAula() {
        return idAula;
    }

    public void setIdaula(int idaula) {
        this.idAula = idaula;
    }

    public String getDescaula() {
        return descAula;
    }

    public void setDescaula(String descaula) {
        this.descAula = descaula;
    }

    public String getRepetir() {
        return repetir;
    }

    public void setRepetir(String repetir) {
        this.repetir = repetir;
    }

    public Integer getTempoesteira() {
        return tempoEsteira;
    }

    public void setTempoesteira(Integer tempoesteira) {
        this.tempoEsteira = tempoesteira;
    }

    public Integer getTempobicicleta() {
        return tempoBicicleta;
    }

    public void setTempobicicleta(Integer tempobicicleta) {
        this.tempoBicicleta = tempobicicleta;
    }

    public Integer getTempoelipticon() {
        return tempoElipticon;
    }

    public void setTempoelipticon(Integer tempoelipticon) {
        this.tempoElipticon = tempoelipticon;
    }

    public int getImpresso() {
        return impresso;
    }

    public void setImpresso(int impresso) {
        this.impresso = impresso;
    }

    public List<Exercicio> getExercicioList() {
        return exercicioList;
    }

    public void setExercicioList(List<Exercicio> exercicioList) {
        this.exercicioList = exercicioList;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie idserie) {
        this.serie = idserie;
    }
    
    @Override
    public String toString(){
        return descAula;
    }
}
