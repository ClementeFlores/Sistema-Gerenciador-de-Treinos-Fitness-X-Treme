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
    private int serie;
    private int quantidade;
    private int peso;

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

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return nomeExercicio;
    }
}