/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

/**
 *
 * @author BrunoRicardo
 */
public class Exercicio {

    private int idExercicio;
    private String nomeExercicio;
    private String serie;
    private int peso;
    private Grupo grupo = new Grupo();

    public Exercicio() {
    }

    public Exercicio(int idExercicio) {
        this.idExercicio = idExercicio;
    }

    public Exercicio(int idexercicio, String nomeexercicio) {
        this.idExercicio = idexercicio;
        this.nomeExercicio = nomeexercicio;
    }

    public int getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(int idexercicio) {
        this.idExercicio = idexercicio;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeexercicio) {
        this.nomeExercicio = nomeexercicio;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return nomeExercicio;
    }
}