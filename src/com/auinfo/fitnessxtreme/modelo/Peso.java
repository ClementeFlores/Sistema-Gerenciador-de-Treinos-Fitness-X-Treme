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
@Table(name = "PESO")
@NamedQueries({
    @NamedQuery(name = "Peso.findAll", query = "SELECT p FROM Peso p")})
public class Peso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPESO", nullable = false)
    private Integer idpeso;
    @Basic(optional = false)
    @Column(name = "PESO", nullable = false)
    private int peso;
    @ManyToMany(mappedBy = "pesoList")
    private List<Exercicio> exercicioList;

    public Peso() {
    }

    public Peso(Integer idpeso) {
        this.idpeso = idpeso;
    }

    public Peso(Integer idpeso, int peso) {
        this.idpeso = idpeso;
        this.peso = peso;
    }

    public Integer getIdpeso() {
        return idpeso;
    }

    public void setIdpeso(Integer idpeso) {
        this.idpeso = idpeso;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
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
        hash += (idpeso != null ? idpeso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Peso)) {
            return false;
        }
        Peso other = (Peso) object;
        if ((this.idpeso == null && other.idpeso != null) || (this.idpeso != null && !this.idpeso.equals(other.idpeso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.auinfo.fitnessxtreme.modelo.Peso[ idpeso=" + idpeso + " ]";
    }
    
}
