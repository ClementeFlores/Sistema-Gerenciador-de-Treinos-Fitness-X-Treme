/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author BrunoRicardo
 */
public class Serie {

    private int idSerie;
    private String nomeSerie;
    private String descSerie;
    private Date dataInicio;
    private Date dataFim;
    private Double peso1;
    private Double peso2;
    private Double peso3;
    private List<Aula> aulaList;
    private Usuario usuario;

    public Serie() {
    }

    public Serie(int idserie) {
        this.idSerie = idserie;
    }

    public Serie(int idserie, Date datainicio, Date datafim) {
        this.idSerie = idserie;
        this.dataInicio = datainicio;
        this.dataFim = datafim;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idserie) {
        this.idSerie = idserie;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date datainicio) {
        this.dataInicio = datainicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date datafim) {
        this.dataFim = datafim;
    }

    public Double getPeso1() {
        return peso1;
    }

    public void setPeso1(Double peso1) {
        this.peso1 = peso1;
    }

    public Double getPeso2() {
        return peso2;
    }

    public void setPeso2(Double peso2) {
        this.peso2 = peso2;
    }

    public Double getPeso3() {
        return peso3;
    }

    public void setPeso3(Double peso3) {
        this.peso3 = peso3;
    }

    public List<Aula> getAulaList() {
        return aulaList;
    }

    public void setAulaList(List<Aula> aulaList) {
        this.aulaList = aulaList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario idusuario) {
        this.usuario = idusuario;
    }

    public String getNomeSerie() {
        return nomeSerie;
    }

    public String getDescSerie() {
        return descSerie;
    }

    public void setNomeSerie(String nomeSerie) {
        this.nomeSerie = nomeSerie;
    }

    public void setDescSerie(String descSerie) {
        this.descSerie = descSerie;
    }
    
    @Override
    public String toString(){
        return nomeSerie;
    }
}
