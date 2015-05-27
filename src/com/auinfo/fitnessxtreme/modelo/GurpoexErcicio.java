/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author BrunoRicardo
 */
@Entity
@Table(name = "GURPOEXERCICIO")
@NamedQueries({
    @NamedQuery(name = "Gurpoexercicio.findAll", query = "SELECT g FROM Gurpoexercicio g")})
public class GurpoexErcicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDGURPOEXERCICIO", nullable = false)
    private Integer idgurpoexercicio;
    @Basic(optional = false)
    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gurpoexercicioIdgurpoexercicio")
    private List<Exercicio> exercicioList;

    public GurpoexErcicio() {
    }

    public GurpoexErcicio(Integer idgurpoexercicio) {
        this.idgurpoexercicio = idgurpoexercicio;
    }

    public GurpoexErcicio(Integer idgurpoexercicio, String nome) {
        this.idgurpoexercicio = idgurpoexercicio;
        this.nome = nome;
    }

    public Integer getIdgurpoexercicio() {
        return idgurpoexercicio;
    }

    public void setIdgurpoexercicio(Integer idgurpoexercicio) {
        this.idgurpoexercicio = idgurpoexercicio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        hash += (idgurpoexercicio != null ? idgurpoexercicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GurpoexErcicio)) {
            return false;
        }
        GurpoexErcicio other = (GurpoexErcicio) object;
        if ((this.idgurpoexercicio == null && other.idgurpoexercicio != null) || (this.idgurpoexercicio != null && !this.idgurpoexercicio.equals(other.idgurpoexercicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.auinfo.fitnessxtreme.modelo.Gurpoexercicio[ idgurpoexercicio=" + idgurpoexercicio + " ]";
    }
    
}
