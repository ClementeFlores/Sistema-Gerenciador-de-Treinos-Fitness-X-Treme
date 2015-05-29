/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.modelo;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    StringProperty nome, indicadorDireito, indicadorEsquerdo, senha;
    ObjectProperty<LocalDate> dataNascimento, dataCadastramento;
    BooleanProperty eAdministrador;  
    
    Usuario usuario;
    
    Calendar cal = Calendar.getInstance();

    public UsuarioTV(Usuario usuario) {
        this.usuario = usuario;
        
        id = new SimpleIntegerProperty();
        id.set(usuario.getIdusuario());
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
        
        cal.setTime(usuario.getDatanascimento());
        dataNascimento = new SimpleObjectProperty<>(LocalDate.of( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));
        
        cal.setTime(usuario.getDatacadastramento());
        dataCadastramento = new SimpleObjectProperty<>(LocalDate.of( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));

        eAdministrador = new SimpleBooleanProperty();
        
    }

    public IntegerProperty getId() {
        return id;
    }

    public IntegerProperty getMatricula() {
        return matricula;
    }

    public StringProperty getNome() {
        return nome;
    }

    public StringProperty getIndicadorDireito() {
        return indicadorDireito;
    }

    public StringProperty getIndicadorEsquerdo() {
        return indicadorEsquerdo;
    }

    public StringProperty getSenha() {
        return senha;
    }

    public ObjectProperty<LocalDate> getDataNascimento() {
        return dataNascimento;
    }

    public ObjectProperty<LocalDate> getDataCadastramento() {
        return dataCadastramento;
    }

    public BooleanProperty geteAdministrador() {
        return eAdministrador;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Calendar getCal() {
        return cal;
    }
    
    public boolean filtrar(String text)    {
        
        String dados = matricula.get()+nome.get();
        
        if(dados.toUpperCase().contains(text.toUpperCase())){
            return true;
        }
        else {
            return false;
        }
        
    }
    
    
}
