/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador.dao;

import com.auinfo.fitnessxtreme.conexao.FabricaConexao;
import com.auinfo.fitnessxtreme.modelo.Aula;
import com.auinfo.fitnessxtreme.modelo.Exercicio;
import com.auinfo.fitnessxtreme.modelo.Serie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BrunoRicardo
 */
public class AulaDao {

    Connection conexao = null;

    //Inicia Conexão
    public void abreConnection() {
        conexao = new FabricaConexao().getConexao();
    }

    //Finaliza Conexão
    public void fechaConnection() {
        try {
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //CRUD
    //CREATE    
    public int adicionaAula(Aula novaAula) {
        String sql = "INSERT INTO FITNESSXTREME.Aula(descAula, repetir, tempoEsteira, tempoBicicleta, tempoelipticon, impresso, idSerie) values(?,?,?,?,?,?,?)";
        PreparedStatement stmt;
        int resultado = 0;

        try {
            stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, novaAula.getDescaula());
            stmt.setString(2, novaAula.getRepetir());
            stmt.setInt(3, novaAula.getTempoesteira());
            stmt.setInt(4, novaAula.getTempobicicleta());
            stmt.setInt(5, novaAula.getTempoelipticon());
            stmt.setInt(6, novaAula.getImpresso());
            stmt.setInt(7, novaAula.getSerie().getIdSerie());
            stmt.execute();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    resultado = (int) generatedKeys.getLong(1);
                    System.out.println(resultado);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public boolean adicionaAulaExercicios(Aula novaAula) {
        String sql = "INSERT INTO FITNESSXTREME.AulaExercicio(idAula, idExercicio, serie, peso) values(?,?,?,?)";
        PreparedStatement stmt;
        boolean resultado = true;

        for (Exercicio e : novaAula.getExercicioList()) {
            
            
            try {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, novaAula.getIdAula());
                stmt.setInt(2, e.getIdExercicio());
                stmt.setString(3, e.getSerie());
                stmt.setInt(4, e.getPeso());
                stmt.execute();
                stmt.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
                resultado = false;
            }
            System.out.println("Exercicio ID: " + e.getIdExercicio());
            System.out.println("Serie: " + e.getSerie());
            System.out.println("Peso: " + e.getPeso());
            System.out.println("Aula ID: " + novaAula.getIdAula());
            System.out.println("");
        }
        return resultado;
    }

    //READ
    public List<Aula> getLista() {
        String sql = "SELECT * FROM FITNESSXTREME.Aula";
        PreparedStatement stmt;
        ResultSet res;
        List<Aula> listaResAula = new ArrayList<>();

        try {
            stmt = conexao.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {
                Aula aula = new Aula();
                aula.setIdaula(res.getInt("idAula"));
                aula.setDescaula(res.getString("descAula"));
                aula.setRepetir(res.getString("repetir"));
                aula.setTempoesteira(res.getInt("tempoEsteira"));
                aula.setTempobicicleta(res.getInt("tempoBicicleta"));
                aula.setTempoelipticon(res.getInt("tempoElipticon"));
                aula.setImpresso(res.getInt("impresso"));
                aula.getSerie().setIdSerie(res.getInt("idSerie"));
                listaResAula.add(aula);
            }

            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaResAula;
    }

    public List<Aula> getLista(Serie serie) {
        String sql = "SELECT * FROM FITNESSXTREME.Aula WHERE idSerie=?";
        PreparedStatement stmt;
        ResultSet res;
        List<Aula> listaResAula = new ArrayList<>();

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, serie.getIdSerie());
            res = stmt.executeQuery();

            while (res.next()) {
                Aula aula = new Aula();
                aula.setIdaula(res.getInt("idAula"));
                aula.setDescaula(res.getString("descAula"));
                aula.setRepetir(res.getString("repetir"));
                aula.setTempoesteira(res.getInt("tempoEsteira"));
                aula.setTempobicicleta(res.getInt("tempoBicicleta"));
                aula.setTempoelipticon(res.getInt("tempoElipticon"));
                aula.setImpresso(res.getInt("impresso"));
                aula.setSerie(serie);
                listaResAula.add(aula);
            }

            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaResAula;
    }
    
    public boolean getLista(Exercicio exercicio) {
        String sql = "SELECT * FROM FITNESSXTREME.AulaExercicio WHERE idExercicio=?";
        PreparedStatement stmt;
        ResultSet res;
        boolean resultado = false;
        
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, exercicio.getIdExercicio());
            res = stmt.executeQuery();

            if (res.next()) {
                resultado = true;
            }

            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

//UPDATE
    public boolean atualizaAula(Aula novoAula) {
        String sql = "UPDATE FITNESSXTREME.Aula SET descAula=?, repetir=?, tempoEsteira=?, tempoBicicleta=?, tempoElipticon=?, impresso=?, idSerie=? WHERE idAula=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, novoAula.getDescaula());
            stmt.setString(2, novoAula.getRepetir());
            stmt.setInt(3, novoAula.getTempoesteira());
            stmt.setInt(4, novoAula.getTempobicicleta());
            stmt.setInt(5, novoAula.getTempoelipticon());
            stmt.setInt(6, novoAula.getImpresso());
            stmt.setInt(7, novoAula.getSerie().getIdSerie());
            stmt.setInt(8, novoAula.getIdAula());

            stmt.execute();
            stmt.close();
            resultado = true;
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //DELETE
    public boolean deletaAula(Aula novoAula) {

        deletaAulaExercicio(novoAula.getIdAula());
        
        String sql = "DELETE FROM FITNESSXTREME.Aula WHERE idAula=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, novoAula.getIdAula());

            stmt.execute();
            stmt.close();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public boolean deletaAulaExercicio(int idAula) {
        String sql = "DELETE FROM FITNESSXTREME.AulaExercicio WHERE idAula=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idAula);

            stmt.execute();
            stmt.close();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
