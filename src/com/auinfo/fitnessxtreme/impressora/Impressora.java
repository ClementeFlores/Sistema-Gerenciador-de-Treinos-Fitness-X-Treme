package com.auinfo.fitnessxtreme.impressora;

import com.auinfo.fitnessxtreme.controlador.TelaBaseControlador;
import com.auinfo.fitnessxtreme.controlador.dao.ExercicioDao;
import com.auinfo.fitnessxtreme.controlador.dao.GrupoDao;
import com.auinfo.fitnessxtreme.modelo.Aula;
import com.auinfo.fitnessxtreme.modelo.Grupo;
import com.auinfo.fitnessxtreme.modelo.Serie;
import com.auinfo.fitnessxtreme.modelo.Usuario;
import com.auinfo.fitnessxtreme.util.ManipulaConfigs;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

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

    private Properties props;
    private int iRetorno;
    private String iComando;
    private int modeloImpressora;
    private boolean imprimirLogo;
    private String enderecoImpressora;
    private final String nomeProp;
    private boolean imprimiu = false;

    SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm:ss");
    String dataStr;
    String horaStr;
    Aula aula;
    Usuario usuario = TelaBaseControlador.usuario;

    ExercicioDao eDao = new ExercicioDao();
    GrupoDao gDao = new GrupoDao();

    List<Serie> listSeries;
    List<Aula> listAulas;
    List<Grupo> listGrupos;

    Calendar hoje = Calendar.getInstance();

    public Impressora(Aula aula) {
        this.aula = aula;
        nomeProp = "prop.impressora.";

        try {
            props = ManipulaConfigs.getProp("config\\main.properties");
        } catch (IOException e) {
            System.out.println("Houve um erro ao carregar as configurações. Possíveis causas incluem arquivo de configuração danificado e/ou ausente.\n");
            e.printStackTrace();
        }

        modeloImpressora = Integer.parseInt(props.getProperty(nomeProp + "modelo"));
        enderecoImpressora = props.getProperty(nomeProp + "endereco");
        imprimirLogo = Boolean.parseBoolean(props.getProperty(nomeProp + "logo"));

        dataStr = formatador.format(hoje.getTime());
        horaStr = formatadorHora.format(hoje.getTime());

        geraCupom86(aula);

    }

    public void geraCupom86(Aula aula) {

        eDao.abreConnection();
        aula.setExercicioList(eDao.getLista(aula.getIdAula(), false));
        eDao.fechaConnection();

        gDao.abreConnection();
        listGrupos = gDao.getLista();
        gDao.fechaConnection();
        String nomeGrupo = "";
        //-------------------------------------------------------------------
        //Inicializando Impressora
        iRetorno = 0;
        iComando = "";

        BematechNFiscal86 cupom = BematechNFiscal86.Instance;
        iRetorno = cupom.ConfiguraModeloImpressora(modeloImpressora);
        iRetorno = cupom.IniciaPorta(enderecoImpressora);
        iRetorno = cupom.BematechTX(BematechComandosDiretos86.INICIALIZA);
        if (imprimirLogo) {
            iRetorno = cupom.PrintNVBitmap(1, 0);
        }
        //---------------------------------------------------------------
        iRetorno = cupom.BematechTX("\n\n" + dataStr + "                    " + horaStr + "\r\n");
        iRetorno = cupom.BematechTX("\n" + BematechComandosDiretos86.SO + BematechComandosDiretos86.NEGRITO_ON + removeAcentos(usuario.getNome()) + BematechComandosDiretos86.NEGRITO_OFF + "\r\n");
        iRetorno = cupom.BematechTX("\n" + "Serie: " + BematechComandosDiretos86.NEGRITO_ON + removeAcentos(aula.getSerie().getNomeSerie()) + BematechComandosDiretos86.NEGRITO_OFF + "\r\n");
        iRetorno = cupom.BematechTX("Descricao: " + BematechComandosDiretos86.NEGRITO_ON + removeAcentos(aula.getSerie().getDescSerie()) + BematechComandosDiretos86.NEGRITO_OFF + "\r\n");
        iRetorno = cupom.BematechTX("Inicio: " + formatador.format(aula.getSerie().getDataInicio()) + "    " + "Termino: " + formatador.format(aula.getSerie().getDataFim()) + "\r\n");
        iRetorno = cupom.BematechTX("Aula: " + aula.getDescaula() + "\r\n");
        iRetorno = cupom.BematechTX("\n" + "Exercicio \nSerie \t\t\tPeso" + "\n\r");

        for (int i = 0; i < aula.getExercicioList().size(); i++) {
            if (!aula.getExercicioList().get(i).getGrupo().getNomeGrupo().equals(nomeGrupo)) {
                nomeGrupo = aula.getExercicioList().get(i).getGrupo().getNomeGrupo();
                iRetorno = cupom.BematechTX("------------------------------------------------" + "\r\n");
                iRetorno = cupom.BematechTX(removeAcentos(aula.getExercicioList().get(i).getGrupo().getNomeGrupo()));
                iRetorno = cupom.BematechTX("\n" + "------------------------------------------------" + "\r\n");

                iRetorno = cupom.BematechTX(BematechComandosDiretos86.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getNomeExercicio()) + "\n\r");
                iRetorno = cupom.BematechTX(BematechComandosDiretos86.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getSerie()) + "\t\t\t" + removeAcentos(aula.getExercicioList().get(i).getPeso() + "") + BematechComandosDiretos86.NEGRITO_OFF + " KG\r\n");
            } else {
                iRetorno = cupom.BematechTX(BematechComandosDiretos86.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getNomeExercicio()) + "\n\r");
                iRetorno = cupom.BematechTX(BematechComandosDiretos86.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getSerie()) + "\t\t\t" + removeAcentos(aula.getExercicioList().get(i).getPeso() + "") + BematechComandosDiretos86.NEGRITO_OFF + " KG\r\n");
            }
        }
        iRetorno = cupom.BematechTX("\n" + "------------------------------------------------" + "\r\n");
        iRetorno = cupom.BematechTX("Peso: " + aula.getSerie().getPeso1() + "        Peso: " + aula.getSerie().getPeso2() + "        Peso: " + aula.getSerie().getPeso3() + "\r\n");
        iRetorno = cupom.BematechTX("\n" + "Quantidade de Impresoes: " + aula.getImpresso() + "\r\n");
        iRetorno = cupom.AcionaGuilhotina(1);
    }

    private String removeAcentos(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }
}
