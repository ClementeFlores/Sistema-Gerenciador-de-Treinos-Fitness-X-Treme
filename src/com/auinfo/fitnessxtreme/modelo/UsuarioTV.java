/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import java.time.LocalDate;
import java.util.Calendar;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author BrunoRicardo
 */
public class UsuarioTV {
    
    IntegerProperty id, matricula;
    StringProperty nome, indicadorDireito, indicadorEsquerdo, senha, objetivo, observacao, eAdministrador;
    ObjectProperty<LocalDate> dataNascimento, dataCadastramento;
    
    Usuario usuario;
    
    Calendar cal = Calendar.getInstance();

    public UsuarioTV(Usuario usuario) {
        this.usuario = usuario;
        
        id = new SimpleIntegerProperty();
        id.set(usuario.getIdUsuario());
        matricula = new SimpleIntegerProperty();
        matricula.set(usuario.getMatricula());
        nome = new SimpleStringProperty();
        nome.set(usuario.getNome());
        indicadorDireito = new SimpleStringProperty();
        indicadorDireito.set(usuario.getIndicadordireito());
        indicadorEsquerdo = new SimpleStringProperty();
        indicadorEsquerdo.set(usuario.getIndicadoresquerdo());
        senha = new SimpleStringProperty();
        senha.set(usuario.getSenha());
        objetivo = new SimpleStringProperty();
        objetivo.set(usuario.getObjetivo());
        observacao = new SimpleStringProperty();
        observacao.set(usuario.getObservacao());
        
        cal.setTime(usuario.getDatanascimento());
        dataNascimento = new SimpleObjectProperty<>(LocalDate.of( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));
        
        cal.setTime(usuario.getDatacadastramento());
        dataCadastramento = new SimpleObjectProperty<>(LocalDate.of( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));

        eAdministrador = new SimpleStringProperty();
        if(usuario.getEadministrador()){
            eAdministrador.set("Sim");
        } else {
            eAdministrador.set("Não");
        }
        
        
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty matriculaProperty() {
        return matricula;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty indicadorDireitoProperty() {
        return indicadorDireito;
    }

    public StringProperty indicadorEsquerdoProperty() {
        return indicadorEsquerdo;
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public ObjectProperty<LocalDate> dataNascimentoProperty() {
        return dataNascimento;
    }

    public ObjectProperty<LocalDate> dataCadastramentoProperty() {
        return dataCadastramento;
    }

    public StringProperty eAdministradorProperty() {
        return eAdministrador;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    public boolean filtrar(String text)    {
        
        String dados = matricula.get()+nome.get()+eAdministrador.get();
        
        if(dados.toUpperCase().contains(text.toUpperCase())){
            return true;
        }
        else {
            return false;
        }
        
    }
    
    
}
