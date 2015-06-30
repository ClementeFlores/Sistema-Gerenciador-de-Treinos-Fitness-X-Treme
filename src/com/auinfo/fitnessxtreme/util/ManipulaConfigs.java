/*
 * Copyright (C) 2015 Tiago
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.auinfo.fitnessxtreme.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Tiago
 */
public class ManipulaConfigs {

    public static Properties getProp(String local) throws IOException {
        Properties props = new Properties();
        FileInputStream file;
        file = new FileInputStream(local);
        props.load(file);
        file.close();
        return props;
    }

    public static boolean setProp(String config, String valor, String local) throws IOException {
        Properties propSalvar = new Properties();
        try {
            FileInputStream arquivoIn = new FileInputStream(local);
            propSalvar.load(arquivoIn);
            arquivoIn.close();
            FileOutputStream arquivoOut = new FileOutputStream(local);
            propSalvar.setProperty(config, valor);
            propSalvar.store(arquivoOut, null);
            arquivoOut.close();
            return true;
        } catch (Exception e) {
            return false;
            //JOptionPane.showMessageDialog(null, "Não Foi possível salvar dados no arquivo config.properties\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
