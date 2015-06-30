/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador.dao;

import com.auinfo.fitnessxtreme.conexao.FabricaConexao;
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
public class GrupoDao {

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
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //CRUD
    //CREATE    
    public int adicionaGrupo(Grupo novoGrupo) {
        String sql = "INSERT INTO FITNESSXTREME.Grupo(nomeGrupo) values(?)";
        PreparedStatement stmt;
        int resultado = 0;

        try {
            stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, novoGrupo.getNomeGrupo());
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
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //READ
    public List<Grupo> getLista() {
        String sql = "SELECT * FROM FITNESSXTREME.Grupo ORDER BY nomeGrupo ASC";
        PreparedStatement stmt;
        ResultSet res;
        List<Grupo> listaResGrupo = new ArrayList<>();

        try {
            stmt = conexao.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {
                Grupo grupo = new Grupo();
                grupo.setIdGrupo(res.getInt("idGrupo"));
                grupo.setNomeGrupo(res.getString("nomeGrupo"));
                grupo.setOrdem(res.getInt("ordem"));
                listaResGrupo.add(grupo);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaResGrupo;
    }

//UPDATE
    public boolean atualizaGrupo(Grupo novoGrupo) {
        String sql = "UPDATE FITNESSXTREME.Grupo SET nomeGrupo=? WHERE idGrupo=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, novoGrupo.getNomeGrupo());
            stmt.setInt(2, novoGrupo.getIdGrupo());

            stmt.execute();
            stmt.close();
            resultado = true;
        } catch (SQLException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //DELETE
    public boolean deletaGrupo(Grupo novoGrupo) {
        String sql = "DELETE FROM FITNESSXTREME.Grupo WHERE idGrupo=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, novoGrupo.getIdGrupo());

            stmt.execute();
            stmt.close();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
