package com.auinfo.fitnessxtreme.impressora;


import com.auinfo.fitnessxtreme.modelo.Usuario;
import java.text.Normalizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiago
 */
public class Impressora {
    
    
    
    public Impressora () {
        
    }
    
    public boolean geraCupom (Usuario usuario) {
        if (1 != 1 ) { //der merda
            return false;
        } else return true;
    }
    
    private String removeAcentos(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }
    
}
