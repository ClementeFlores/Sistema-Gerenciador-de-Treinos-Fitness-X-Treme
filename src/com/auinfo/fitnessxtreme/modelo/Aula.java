/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author BrunoRicardo
 */
@Entity
@Table(name = "AULA")
@NamedQueries({
    @NamedQuery(name = "Aula.findAll", query = "SELECT a FROM Aula a")})
public class Aula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDAULA", nullable = false)
    private Integer idaula;
    @Basic(optional = false)
    @Column(name = "OBJETIVO", nullable = false, length = 200)
    private String objetivo;
    @Column(name = "DESCAULA", length = 100)
    private String descaula;
    @Basic(optional = false)
    @Column(name = "DATAINICIOSERIE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datainicioserie;
    @Basic(optional = false)
    @Column(name = "DATAFIMSERIE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datafimserie;
    @Basic(optional = false)
    @Column(name = "OBSERVACAO", nullable = false, length = 200)
    private String observacao;
    @Basic(optional = false)
    @Column(name = "DATAAULA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataaula;
    @Basic(optional = false)
    @Column(name = "REPETIRTODA", nullable = false, length = 50)
    private String repetirtoda;
    @Column(name = "TEMPOESTEIRA", length = 10)
    private String tempoesteira;
    @Column(name = "TEMPOBICICLETA", length = 10)
    private String tempobicicleta;
    @Column(name = "TEMPOELIPTICON", length = 10)
    private String tempoelipticon;
    @JoinTable(name = "AULAEXERCICIO", joinColumns = {
        @JoinColumn(name = "IDAULA", referencedColumnName = "IDAULA", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "IDEXERCICIO", referencedColumnName = "IDEXERCICIO", nullable = false)})
    @ManyToMany
    private List<Exercicio> exercicioList;
    @JoinColumn(name = "USUARIO_IDUSUARIO", referencedColumnName = "IDUSUARIO", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;

    public Aula() {
    }

    public Aula(Integer idaula) {
        this.idaula = idaula;
    }

    public Aula(Integer idaula, String objetivo, Date datainicioserie, Date datafimserie, String observacao, Date dataaula, String repetirtoda) {
        this.idaula = idaula;
        this.objetivo = objetivo;
        this.datainicioserie = datainicioserie;
        this.datafimserie = datafimserie;
        this.observacao = observacao;
        this.dataaula = dataaula;
        this.repetirtoda = repetirtoda;
    }

    public Integer getIdaula() {
        return idaula;
    }

    public void setIdaula(Integer idaula) {
        this.idaula = idaula;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getDescaula() {
        return descaula;
    }

    public void setDescaula(String descaula) {
        this.descaula = descaula;
    }

    public Date getDatainicioserie() {
        return datainicioserie;
    }

    public void setDatainicioserie(Date datainicioserie) {
        this.datainicioserie = datainicioserie;
    }

    public Date getDatafimserie() {
        return datafimserie;
    }

    public void setDatafimserie(Date datafimserie) {
        this.datafimserie = datafimserie;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataaula() {
        return dataaula;
    }

    public void setDataaula(Date dataaula) {
        this.dataaula = dataaula;
    }

    public String getRepetirtoda() {
        return repetirtoda;
    }

    public void setRepetirtoda(String repetirtoda) {
        this.repetirtoda = repetirtoda;
    }

    public String getTempoesteira() {
        return tempoesteira;
    }

    public void setTempoesteira(String tempoesteira) {
        this.tempoesteira = tempoesteira;
    }

    public String getTempobicicleta() {
        return tempobicicleta;
    }

    public void setTempobicicleta(String tempobicicleta) {
        this.tempobicicleta = tempobicicleta;
    }

    public String getTempoelipticon() {
        return tempoelipticon;
    }

    public void setTempoelipticon(String tempoelipticon) {
        this.tempoelipticon = tempoelipticon;
    }

    public List<Exercicio> getExercicioList() {
        return exercicioList;
    }

    public void setExercicioList(List<Exercicio> exercicioList) {
        this.exercicioList = exercicioList;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaula != null ? idaula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aula)) {
            return false;
        }
        Aula other = (Aula) object;
        if ((this.idaula == null && other.idaula != null) || (this.idaula != null && !this.idaula.equals(other.idaula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.auinfo.fitnessxtreme.modelo.Aula[ idaula=" + idaula + " ]";
    }
    
}
