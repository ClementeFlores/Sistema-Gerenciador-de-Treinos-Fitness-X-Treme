/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador.dao;

import com.auinfo.fitnessxtreme.conexao.FabricaConexao;
import com.auinfo.fitnessxtreme.modelo.Serie;
import com.auinfo.fitnessxtreme.modelo.Usuario;
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
public class SerieDao {

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
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //CRUD
    //CREATE    
    public int adicionaSerie(Serie novaSerie) {
        String sql = "INSERT INTO FITNESSXTREME.Serie(dataInicio, dataFim, peso1, peso2, peso3, idUsuario, nomeSerie, descSerie) values(?,?,?,?,?,?,?,?)";
        PreparedStatement stmt;
        int resultado = 0;

        try {
            stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, new java.sql.Date(novaSerie.getDataInicio().getTime()));
            stmt.setDate(2, new java.sql.Date(novaSerie.getDataFim().getTime()));
            stmt.setDouble(3, novaSerie.getPeso1());
            stmt.setDouble(4, novaSerie.getPeso2());
            stmt.setDouble(5, novaSerie.getPeso3());
            stmt.setInt(6, novaSerie.getUsuario().getIdUsuario());
            stmt.setString(7, novaSerie.getNomeSerie());
            stmt.setString(8, novaSerie.getDescSerie());
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
            Logger.getLogger(SerieDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //READ
    public List<Serie> getLista() {
        String sql = "SELECT * FROM FITNESSXTREME.Serie ORDER BY nomeSerie ASC";
        PreparedStatement stmt;
        ResultSet res;
        List<Serie> listaResSerie = new ArrayList<>();

        try {
            stmt = conexao.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {
                Serie serie = new Serie();
                serie.setIdSerie(res.getInt("idSerie"));
                serie.setDataInicio(res.getDate("dataInicio"));
                serie.setDataFim(res.getDate("dataFim"));
                serie.setPeso1(res.getDouble("peso1"));
                serie.setPeso2(res.getDouble("peso2"));
                serie.setPeso3(res.getDouble("peso3"));
                serie.getUsuario().setIdUsuario(res.getInt("idUsuario"));
                serie.setNomeSerie(res.getString("nomeSerie"));
                serie.setDescSerie(res.getString("descSerie"));
                listaResSerie.add(serie);
            }

            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SerieDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaResSerie;
    }

    public List<Serie> getLista(Usuario usuario) {
        String sql = "SELECT * FROM FITNESSXTREME.Serie WHERE idUsuario=? ORDER BY nomeSerie ASC";
        PreparedStatement stmt;
        ResultSet res;
        List<Serie> listaResSerie = new ArrayList<>();

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, usuario.getIdUsuario());
            res = stmt.executeQuery();

            while (res.next()) {
                Serie serie = new Serie();
                serie.setIdSerie(res.getInt("idSerie"));
                serie.setDataInicio(res.getDate("dataInicio"));
                serie.setDataFim(res.getDate("dataFim"));
                serie.setPeso1(res.getDouble("peso1"));
                serie.setPeso2(res.getDouble("peso2"));
                serie.setPeso3(res.getDouble("peso3"));
                serie.setUsuario(usuario);
                serie.setNomeSerie(res.getString("nomeSerie"));
                serie.setDescSerie(res.getString("descSerie"));
                listaResSerie.add(serie);
            }

            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SerieDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaResSerie;
    }

//UPDATE
    public boolean atualizaSerie(Serie novaSerie) {
        String sql = "UPDATE FITNESSXTREME.Serie SET dataInicio=?, dataFim=?, peso1=?, peso2=?, peso3=?, idUsuario=?, nomeSerie=?, descSerie=?  WHERE idSerie=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(novaSerie.getDataInicio().getTime()));
            stmt.setDate(2, new java.sql.Date(novaSerie.getDataFim().getTime()));
            stmt.setDouble(3, novaSerie.getPeso1());
            stmt.setDouble(4, novaSerie.getPeso2());
            stmt.setDouble(5, novaSerie.getPeso3());
            stmt.setInt(6, novaSerie.getUsuario().getIdUsuario());
            stmt.setString(7, novaSerie.getNomeSerie());
            stmt.setString(8, novaSerie.getDescSerie());
            stmt.setInt(9, novaSerie.getIdSerie());

            stmt.execute();
            stmt.close();
            resultado = true;
        } catch (SQLException ex) {
            Logger.getLogger(SerieDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //DELETE
    public boolean deletaSerie(Serie novaSerie) {
        String sql = "DELETE FROM FITNESSXTREME.Serie WHERE idSerie=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, novaSerie.getIdSerie());

            stmt.execute();
            stmt.close();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(SerieDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
