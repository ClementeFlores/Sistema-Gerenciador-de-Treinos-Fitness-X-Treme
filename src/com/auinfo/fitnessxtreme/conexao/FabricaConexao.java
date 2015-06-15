/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author BrunoRicardo
 */
public class FabricaConexao {
    //H2
    String usuario = "sa";
    String senha = "sa";
    String url = "jdbc:h2:banco;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1";
    Connection ready = null;

    /**
     *
     * @return Função para criar uma conexão e retorna-la
     */
    public Connection getConexao() {
        try {
            ready = DriverManager.getConnection(url, usuario, senha);
            return ready;
        } catch (SQLException fail) {
            System.out.println("Impossivel Conectar: " + fail);
            return ready;
        }
    }
    
}
