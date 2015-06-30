/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BrunoRicardo
 */
public class HexSha {

    private String senha;

    public HexSha(String senha) {
        this.senha = senha;
    }

    public String convertSha() {
        MessageDigest md;
        StringBuffer hexString = new StringBuffer();

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(this.senha.getBytes());
            byte byteData[] = md.digest();

            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HexSha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hexString.toString();
    }
}
