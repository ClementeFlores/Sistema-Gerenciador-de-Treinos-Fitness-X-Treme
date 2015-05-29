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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author BrunoRicardo
 */
@Entity
@Table(name = "USUARIO")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDUSUARIO", nullable = false)
    private Integer idusuario;
    @Basic(optional = false)
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;
    @Basic(optional = false)
    @Column(name = "MATRICULA", nullable = false)
    private int matricula;
    @Basic(optional = false)
    @Column(name = "DATANASCIMENTO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datanascimento;
    @Basic(optional = false)
    @Column(name = "INDICADORDIREITO", nullable = false, length = 1000)
    private String indicadordireito;
    @Basic(optional = false)
    @Column(name = "INDICADORESQUERDO", nullable = false, length = 1000)
    private String indicadoresquerdo;
    @Column(name = "SENHA", length = 50)
    private String senha;
    @Basic(optional = false)
    @Column(name = "DATACADASTRAMENTO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datacadastramento;
    @Basic(optional = false)
    @Column(name = "EADMINISTRADOR", nullable = false)
    private short eadministrador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioIdusuario")
    private List<Aula> aulaList;

    public Usuario() {
    }

    public Usuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Usuario(Integer idusuario, String nome, int matricula, Date datanascimento, String indicadordireito, String indicadoresquerdo, Date datacadastramento, short eadministrador) {
        this.idusuario = idusuario;
        this.nome = nome;
        this.matricula = matricula;
        this.datanascimento = datanascimento;
        this.indicadordireito = indicadordireito;
        this.indicadoresquerdo = indicadoresquerdo;
        this.datacadastramento = datacadastramento;
        this.eadministrador = eadministrador;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getIndicadordireito() {
        return indicadordireito;
    }

    public void setIndicadordireito(String indicadordireito) {
        this.indicadordireito = indicadordireito;
    }

    public String getIndicadoresquerdo() {
        return indicadoresquerdo;
    }

    public void setIndicadoresquerdo(String indicadoresquerdo) {
        this.indicadoresquerdo = indicadoresquerdo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDatacadastramento() {
        return datacadastramento;
    }

    public void setDatacadastramento(Date datacadastramento) {
        this.datacadastramento = datacadastramento;
    }

    public short getEadministrador() {
        return eadministrador;
    }

    public void setEadministrador(short eadministrador) {
        this.eadministrador = eadministrador;
    }

    public List<Aula> getAulaList() {
        return aulaList;
    }

    public void setAulaList(List<Aula> aulaList) {
        this.aulaList = aulaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.auinfo.fitnessxtreme.modelo.Usuario[ idusuario=" + idusuario + " ]";
    }
    
}
