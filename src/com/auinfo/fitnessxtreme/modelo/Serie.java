/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author BrunoRicardo
 */
@Entity
@Table(name = "SERIE")
@NamedQueries({
    @NamedQuery(name = "Serie.findAll", query = "SELECT s FROM Serie s")})
public class Serie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDSERIE", nullable = false)
    private Integer idserie;
    @Basic(optional = false)
    @Column(name = "SERIE", nullable = false)
    private int serie;
    @Basic(optional = false)
    @Column(name = "QUANTIDADE", nullable = false)
    private int quantidade;
    @ManyToMany(mappedBy = "serieList")
    private List<Exercicio> exercicioList;

    public Serie() {
    }

    public Serie(Integer idserie) {
        this.idserie = idserie;
    }

    public Serie(Integer idserie, int serie, int quantidade) {
        this.idserie = idserie;
        this.serie = serie;
        this.quantidade = quantidade;
    }

    public Integer getIdserie() {
        return idserie;
    }

    public void setIdserie(Integer idserie) {
        this.idserie = idserie;
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

    public List<Exercicio> getExercicioList() {
        return exercicioList;
    }

    public void setExercicioList(List<Exercicio> exercicioList) {
        this.exercicioList = exercicioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idserie != null ? idserie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Serie)) {
            return false;
        }
        Serie other = (Serie) object;
        if ((this.idserie == null && other.idserie != null) || (this.idserie != null && !this.idserie.equals(other.idserie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.auinfo.fitnessxtreme.modelo.Serie[ idserie=" + idserie + " ]";
    }
    
}
