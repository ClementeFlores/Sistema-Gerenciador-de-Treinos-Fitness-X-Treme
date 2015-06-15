/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador.dao;

import com.auinfo.fitnessxtreme.conexao.FabricaConexao;
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
public class UsuarioDao {

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
    public int adicionaUsuario(Usuario novoUsuario) {
        String sql = "INSERT INTO FITNESSXTREME.Usuario(matricula, indicadorDireito, indicadorEsquerdo, senha, objetivo, nome, observacao, dataCadastramento, eAdministrador, dataNascimento ) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt;
        int resultado = 0;

        try {
            stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, novoUsuario.getMatricula());
            stmt.setString(2, novoUsuario.getIndicadordireito());
            stmt.setString(3, novoUsuario.getIndicadoresquerdo());
            stmt.setString(4, novoUsuario.getSenha());
            stmt.setString(5, novoUsuario.getObjetivo());
            stmt.setString(6, novoUsuario.getNome());
            stmt.setString(7, novoUsuario.getObservacao());
            stmt.setDate(8, new java.sql.Date(novoUsuario.getDatacadastramento().getTime()));
            stmt.setBoolean(9, novoUsuario.getEadministrador());
            stmt.setDate(10, new java.sql.Date(novoUsuario.getDatanascimento().getTime()));
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
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //READ
    public List<Usuario> getLista() {
        String sql = "SELECT * FROM FITNESSXTREME.Usuario ORDER BY matricula ASC";
        PreparedStatement stmt;
        ResultSet res;
        List<Usuario> listaResUsuario = new ArrayList<>();

        try {
            stmt = conexao.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(res.getInt("idUsuario"));
                usuario.setMatricula(res.getInt("matricula"));
                usuario.setIndicadordireito(res.getString("indicadorDireito"));
                usuario.setIndicadoresquerdo(res.getString("indicadorEsquerdo"));
                usuario.setSenha(res.getString("senha"));
                usuario.setObjetivo(res.getString("objetivo"));
                usuario.setNome(res.getString("nome"));
                usuario.setObservacao(res.getString("observacao"));
                usuario.setDatacadastramento(res.getDate("dataCadastramento"));
                usuario.setEadministrador(res.getBoolean("eAdministrador"));
                usuario.setDatanascimento(res.getDate("dataNascimento"));
                listaResUsuario.add(usuario);
            }

            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaResUsuario;
    }

    public Usuario getLista(int matricula) {
        String sql = "SELECT * FROM FITNESSXTREME.Usuario WHERE matricula=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, matricula);
            res = stmt.executeQuery();

            while (res.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(res.getInt("idUsuario"));
                usuario.setMatricula(res.getInt("matricula"));
                usuario.setIndicadordireito(res.getString("indicadorDireito"));
                usuario.setIndicadoresquerdo(res.getString("indicadorEsquerdo"));
                usuario.setSenha(res.getString("senha"));
                usuario.setObjetivo(res.getString("objetivo"));
                usuario.setNome(res.getString("nome"));
                usuario.setObservacao(res.getString("observacao"));
                usuario.setDatacadastramento(res.getDate("dataCadastramento"));
                usuario.setEadministrador(res.getBoolean("eAdministrador"));
                usuario.setDatanascimento(res.getDate("dataNascimento"));
                return usuario;
            }

            res.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//UPDATE

    public boolean atualizaUsuario(Usuario novoUsuario) {
        String sql = "UPDATE FITNESSXTREME.Usuario SET indicadorDireito=?, indicadorEsquerdo=?, senha=?, objetivo=?, nome=?, observacao=?, dataCadastramento=?, eAdministrador=?, dataNascimento=? WHERE idUsuario=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, novoUsuario.getIndicadordireito());
            stmt.setString(2, novoUsuario.getIndicadoresquerdo());
            stmt.setString(3, novoUsuario.getSenha());
            stmt.setString(4, novoUsuario.getObjetivo());
            stmt.setString(5, novoUsuario.getNome());
            stmt.setString(6, novoUsuario.getObservacao());
            stmt.setDate(7, new java.sql.Date(novoUsuario.getDatacadastramento().getTime()));
            stmt.setBoolean(8, novoUsuario.getEadministrador());
            stmt.setDate(9, new java.sql.Date(novoUsuario.getDatanascimento().getTime()));
            stmt.setInt(10, novoUsuario.getIdUsuario());

            stmt.execute();
            stmt.close();
            resultado = true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    //DELETE
    public boolean deletaUsuario(Usuario novoUsuario) {
        String sql = "DELETE FROM FITNESSXTREME.Usuario WHERE idUsuario=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, novoUsuario.getIdUsuario());

            stmt.execute();
            stmt.close();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
