/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import java.util.Date;

/**
 *
 * @author BrunoRicardo
 */
public class Usuario {

    private int idUsuario;
    private int matricula;
    private String indicadordireito;
    private String indicadoresquerdo;
    private String senha;
    private String objetivo;
    private String nome;
    private String observacao;
    private Date datacadastramento;
    private Boolean eadministrador;
    private Date datanascimento;

    public Usuario() {
    }

    public Usuario(int idusuario) {
        this.idUsuario = idusuario;
    }

    public Usuario(int idusuario, int matricula, String indicadordireito, String indicadoresquerdo, String objetivo, String nome, Date datacadastramento, Boolean eadministrador, Date datanascimento) {
        this.idUsuario = idusuario;
        this.matricula = matricula;
        this.indicadordireito = indicadordireito;
        this.indicadoresquerdo = indicadoresquerdo;
        this.objetivo = objetivo;
        this.nome = nome;
        this.datacadastramento = datacadastramento;
        this.eadministrador = eadministrador;
        this.datanascimento = datanascimento;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDatacadastramento() {
        return datacadastramento;
    }

    public void setDatacadastramento(Date datacadastramento) {
        this.datacadastramento = datacadastramento;
    }

    public Boolean getEadministrador() {
        return eadministrador;
    }

    public void setEadministrador(Boolean eadministrador) {
        this.eadministrador = eadministrador;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    @Override
    public String toString(){
        return nome;
    }
}
