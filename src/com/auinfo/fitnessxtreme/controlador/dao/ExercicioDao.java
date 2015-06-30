/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador.dao;

import com.auinfo.fitnessxtreme.conexao.FabricaConexao;
import com.auinfo.fitnessxtreme.modelo.Exercicio;
import com.auinfo.fitnessxtreme.modelo.Grupo;
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
        String sql = "INSERT INTO FITNESSXTREME.Exercicio(nomeExercicio, idGrupo) values(?,?)";
        PreparedStatement stmt;
        int resultado = 0;

        try {
            stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, novoExercicio.getNomeExercicio());
            stmt.setInt(2, novoExercicio.getGrupo().getIdGrupo());
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
            Logger.getLogger(ExercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //READ
    public List<Exercicio> getLista() {
        String sql = "SELECT e.idExercicio, e.nomeExercicio, e.idGrupo, g.nomeGrupo FROM FITNESSXTREME.Exercicio AS e JOIN FITNESSXTREME.Grupo AS g ON e.idGrupo = g.idGrupo ORDER BY nomeExercicio ASC";
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
                exercicio.getGrupo().setIdGrupo(res.getInt("idGrupo"));
                exercicio.getGrupo().setNomeGrupo(res.getString("nomeGrupo"));
                listaResExercicio.add(exercicio);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaResExercicio;
    }

    public List<Exercicio> getLista(int idAula, boolean controle) {
        String sql = "SELECT e.idExercicio, e.nomeExercicio, e.idGrupo, g.nomeGrupo, ae.serie, ae.peso FROM FITNESSXTREME.Exercicio AS e JOIN FITNESSXTREME.AulaExercicio AS ae ON e.idExercicio = ae.idExercicio JOIN FITNESSXTREME.Aula AS a ON ae.idAula = a.idAula JOIN FITNESSXTREME.Grupo AS g ON e.idGrupo = g.idGrupo WHERE a.idAula=?  ORDER BY e.nomeExercicio ASC";
        String sql2 = "SELECT e.idExercicio, e.nomeExercicio, e.idGrupo, g.nomeGrupo, g.ordem, ae.serie, ae.peso FROM FITNESSXTREME.Exercicio AS e JOIN FITNESSXTREME.AulaExercicio AS ae ON e.idExercicio = ae.idExercicio JOIN FITNESSXTREME.Aula AS a ON ae.idAula = a.idAula JOIN FITNESSXTREME.Grupo AS g ON e.idGrupo = g.idGrupo WHERE a.idAula=?  ORDER BY g.ordem ASC";
        PreparedStatement stmt;
        ResultSet res;
        List<Exercicio> listaResExercicio = new ArrayList<>();

        try {
            if(controle){
                stmt = conexao.prepareStatement(sql);
            } else {
                stmt = conexao.prepareStatement(sql2);
            }
            
            stmt.setInt(1, idAula);
            res = stmt.executeQuery();

            while (res.next()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setIdExercicio(res.getInt("idExercicio"));
                exercicio.setNomeExercicio(res.getString("nomeExercicio"));
                exercicio.getGrupo().setIdGrupo(res.getInt("idGrupo"));
                exercicio.getGrupo().setNomeGrupo(res.getString("nomeGrupo"));
                exercicio.setSerie(res.getString("serie"));
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
    
    public boolean getLista(Grupo grupo) {
        String sql = "SELECT e.idExercicio, e.nomeExercicio, e.idGrupo, g.nomeGrupo FROM FITNESSXTREME.Exercicio AS e JOIN FITNESSXTREME.Grupo AS g ON e.idGrupo = g.idGrupo WHERE e.idGrupo=? ORDER BY nomeExercicio ASC";
        PreparedStatement stmt;
        ResultSet res;
        boolean resultado = false;
        
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, grupo.getIdGrupo());
            res = stmt.executeQuery();

            if (res.next()) {
              resultado = true;
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

//UPDATE
    public boolean atualizaExercicio(Exercicio novoExercicio) {
        String sql = "UPDATE FITNESSXTREME.Exercicio SET nomeExercicio=?, idGrupo=? WHERE idExercicio=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, novoExercicio.getNomeExercicio());
            stmt.setInt(2, novoExercicio.getGrupo().getIdGrupo());
            stmt.setInt(3, novoExercicio.getIdExercicio());

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
