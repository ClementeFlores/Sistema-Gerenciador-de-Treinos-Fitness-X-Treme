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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author BrunoRicardo
 */
@Entity
@Table(name = "EXERCICIO")
@NamedQueries({
    @NamedQuery(name = "Exercicio.findAll", query = "SELECT e FROM Exercicio e")})
public class Exercicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDEXERCICIO", nullable = false)
    private Integer idexercicio;
    @Basic(optional = false)
    @Column(name = "NOMEEXERCICIO", nullable = false, length = 100)
    private String nomeexercicio;
    @ManyToMany(mappedBy = "exercicioList")
    private List<Aula> aulaList;
    @JoinTable(name = "EXERCICIOPESO", joinColumns = {
        @JoinColumn(name = "IDEXERCICIO", referencedColumnName = "IDEXERCICIO", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "IDPESO", referencedColumnName = "IDPESO", nullable = false)})
    @ManyToMany
    private List<Peso> pesoList;
    @JoinTable(name = "EXERCICIOSERIE", joinColumns = {
        @JoinColumn(name = "IDEXERCICIO", referencedColumnName = "IDEXERCICIO", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "IDSERIE", referencedColumnName = "IDSERIE", nullable = false)})
    @ManyToMany
    private List<Serie> serieList;

    public Exercicio() {
    }

    public Exercicio(Integer idexercicio) {
        this.idexercicio = idexercicio;
    }

    public Exercicio(Integer idexercicio, String nomeexercicio) {
        this.idexercicio = idexercicio;
        this.nomeexercicio = nomeexercicio;
    }

    public Integer getIdexercicio() {
        return idexercicio;
    }

    public void setIdexercicio(Integer idexercicio) {
        this.idexercicio = idexercicio;
    }

    public String getNomeexercicio() {
        return nomeexercicio;
    }

    public void setNomeexercicio(String nomeexercicio) {
        this.nomeexercicio = nomeexercicio;
    }

    public List<Aula> getAulaList() {
        return aulaList;
    }

    public void setAulaList(List<Aula> aulaList) {
        this.aulaList = aulaList;
    }

    public List<Peso> getPesoList() {
        return pesoList;
    }

    public void setPesoList(List<Peso> pesoList) {
        this.pesoList = pesoList;
    }

    public List<Serie> getSerieList() {
        return serieList;
    }

    public void setSerieList(List<Serie> serieList) {
        this.serieList = serieList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idexercicio != null ? idexercicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exercicio)) {
            return false;
        }
        Exercicio other = (Exercicio) object;
        if ((this.idexercicio == null && other.idexercicio != null) || (this.idexercicio != null && !this.idexercicio.equals(other.idexercicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.auinfo.fitnessxtreme.modelo.Exercicio[ idexercicio=" + idexercicio + " ]";
    }
    
}
