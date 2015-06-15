/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador.dao;

import com.auinfo.fitnessxtreme.conexao.FabricaConexao;
import com.auinfo.fitnessxtreme.modelo.Exercicio;
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
public class ExercicioDao {

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
            Logger.getLogger(ExercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //CRUD
    //CREATE    
    public int adicionaExercicio(Exercicio novoExercicio) {
        String sql = "INSERT INTO FITNESSXTREME.Exercicio(nomeExercicio) values(?)";
        PreparedStatement stmt;
        int resultado = 0;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, novoExercicio.getNomeExercicio());
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //READ
    public List<Exercicio> getLista() {
        String sql = "SELECT * FROM FITNESSXTREME.Exercicio ORDER BY nomeExercicio ASC";
        PreparedStatement stmt;
        ResultSet res;
        List<Exercicio> listaResExercicio = new ArrayList<>();

        try {
            stmt = conexao.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setIdExercicio(res.getInt("idExercicio"));
                exercicio.setNomeExercicio(res.getString("nomeExercicio"));
                listaResExercicio.add(exercicio);
            }

            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaResExercicio;
    }
    
    public List<Exercicio> getLista(int idAula) {
        String sql = "SELECT e.idExercicio, e.nomeExercicio, ae.serie, ae.quantidade, ae.peso FROM FITNESSXTREME.Exercicio AS e JOIN FITNESSXTREME.AulaExercicio AS ae ON e.idExercicio = ae.idExercicio JOIN FITNESSXTREME.Aula AS a ON ae.idAula = a.idAula WHERE a.idAula=?  ORDER BY e.nomeExercicio ASC";
        PreparedStatement stmt;
        ResultSet res;
        List<Exercicio> listaResExercicio = new ArrayList<>();

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idAula);
            res = stmt.executeQuery();

            while (res.next()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setIdExercicio(res.getInt("idExercicio"));
                exercicio.setNomeExercicio(res.getString("nomeExercicio"));
                exercicio.setSerie(res.getInt("serie"));
                exercicio.setQuantidade(res.getInt("quantidade"));
                exercicio.setPeso(res.getInt("peso"));
                listaResExercicio.add(exercicio);
            }

            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaResExercicio;
    }

//UPDATE
    public boolean atualizaExercicio(Exercicio novoExercicio) {
        String sql = "UPDATE FITNESSXTREME.Exercicio SET nomeExercicio=?, WHERE idExercicio=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, novoExercicio.getNomeExercicio());
            stmt.setInt(2, novoExercicio.getIdExercicio());

            stmt.execute();
            stmt.close();
            resultado = true;
        } catch (SQLException ex) {
            Logger.getLogger(ExercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //DELETE
    public boolean deletaExercicio(Exercicio novoExercicio) {
        String sql = "DELETE FROM FITNESSXTREME.Exercicio WHERE idExercicio=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, novoExercicio.getIdExercicio());

            stmt.execute();
            stmt.close();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(ExercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
