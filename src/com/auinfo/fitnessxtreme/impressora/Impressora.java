package com.auinfo.fitnessxtreme.impressora;

import com.auinfo.fitnessxtreme.controlador.dao.AulaDao;
import com.auinfo.fitnessxtreme.controlador.dao.ExercicioDao;
import com.auinfo.fitnessxtreme.controlador.dao.GrupoDao;
import com.auinfo.fitnessxtreme.controlador.dao.SerieDao;
import com.auinfo.fitnessxtreme.modelo.Aula;
import com.auinfo.fitnessxtreme.modelo.Grupo;
import com.auinfo.fitnessxtreme.modelo.Serie;
import com.auinfo.fitnessxtreme.modelo.Usuario;
import com.auinfo.fitnessxtreme.util.ManipulaConfigs;
import java.io.IOException;
import java.sql.Time;
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
    private boolean sistema32bits;
    private String enderecoImpressora;
    private final String nomeProp;

    SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm:ss");
    String dataStr;
    String horaStr;
    Usuario usuario;

    SerieDao sDao = new SerieDao();
    AulaDao aDao = new AulaDao();
    ExercicioDao eDao = new ExercicioDao();
    GrupoDao gDao = new GrupoDao();

    List<Serie> listSeries;
    List<Aula> listAulas;
    List<Grupo> listGrupos;

    Calendar serieDataFim = Calendar.getInstance();
    Calendar hoje = Calendar.getInstance();

    public Impressora(Usuario usuario) {
        this.usuario = usuario;
        nomeProp = "prop.impressora.";
        
        try {
            props = ManipulaConfigs.getProp();
        } catch (IOException e) {
            System.out.println("Houve um erro ao carregar as configurações. Possíveis causas incluem arquivo de configuração danificado e/ou ausente.\n");
            e.printStackTrace();
        }

        modeloImpressora = Integer.parseInt(props.getProperty(nomeProp + "modelo"));
        enderecoImpressora = props.getProperty(nomeProp + "endereco");
        imprimirLogo = Boolean.parseBoolean(props.getProperty(nomeProp + "logo"));
        sistema32bits = Boolean.parseBoolean(props.getProperty("prop.sistema.sistema32bits"));

        dataStr = formatador.format(hoje.getTime());
        horaStr = formatadorHora.format(hoje.getTime());

        sDao.abreConnection();
        listSeries = sDao.getLista(usuario);
        sDao.fechaConnection();

        System.out.println("Lista Serie " + listSeries.size());

        for (int i = 0; i < listSeries.size(); i++) {
            serieDataFim.setTime(listSeries.get(i).getDataFim());
            if (hoje.before(serieDataFim)) {
                System.out.println(hoje.get(Calendar.DAY_OF_MONTH) + "\\" + (hoje.get(Calendar.MONTH) + 1) + "\\" + hoje.get(Calendar.YEAR));
                System.out.println(serieDataFim.get(Calendar.DAY_OF_MONTH) + "\\" + (serieDataFim.get(Calendar.MONTH) + 1) + "\\" + serieDataFim.get(Calendar.YEAR));

                aDao.abreConnection();
                listAulas = aDao.getLista(listSeries.get(i));
                aDao.fechaConnection();

                String diaDaSemana = "";

                switch (hoje.get(Calendar.DAY_OF_WEEK)) {
                    case 1:
                        diaDaSemana = "Domingo";
                        break;
                    case 2:
                        diaDaSemana = "Segunda-Feira";
                        break;
                    case 3:
                        diaDaSemana = "Terça-Feira";
                        break;
                    case 4:
                        diaDaSemana = "Quarta-Feira";
                        break;
                    case 5:
                        diaDaSemana = "Quinta-Feira";
                        break;
                    case 6:
                        diaDaSemana = "Sexta-Feira";
                        break;
                    case 7:
                        diaDaSemana = "Sábado";
                        break;
                }

                for (int j = 0; j < listAulas.size(); j++) {
                    if (listAulas.get(j).getRepetir().equals(diaDaSemana)) {
                        if(sistema32bits){
                            System.out.println("x86");
                            geraCupom86(listAulas.get(j));
                        } else {
                            System.out.println("x64");
                            geraCupom64(listAulas.get(j));
                        }
                        
                        listAulas.get(j).setImpresso(listAulas.get(j).getImpresso() + 1);
                        aDao.abreConnection();
                        aDao.atualizaAula(listAulas.get(j));
                        aDao.fechaConnection();
                    }
                }
            }
        }
    }

    public boolean geraCupom64(Aula aula) {
        boolean resultado = false;

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
        
        
        BematechNFiscal64 cupom = BematechNFiscal64.Instance;
        iRetorno = cupom.ConfiguraModeloImpressora(modeloImpressora);
        iRetorno = cupom.IniciaPorta(enderecoImpressora);
        iRetorno = cupom.BematechTX(BematechComandosDiretos64.INICIALIZA);
        if (imprimirLogo) {
            iRetorno = cupom.PrintNVBitmap(1, 0);
        }
        //---------------------------------------------------------------
        iRetorno = cupom.BematechTX("\n\n" + dataStr + "                    " + horaStr + "\r\n");
        iRetorno = cupom.BematechTX("\n" + BematechComandosDiretos64.SO + BematechComandosDiretos64.NEGRITO_ON + removeAcentos(usuario.getNome()) + BematechComandosDiretos64.NEGRITO_OFF + "\r\n");
        iRetorno = cupom.BematechTX("\n" + "Serie: " + BematechComandosDiretos64.NEGRITO_ON + removeAcentos(aula.getSerie().getNomeSerie()) + BematechComandosDiretos64.NEGRITO_OFF + "\r\n");
        iRetorno = cupom.BematechTX("Descricao: " + BematechComandosDiretos64.NEGRITO_ON + removeAcentos(aula.getSerie().getDescSerie()) + BematechComandosDiretos64.NEGRITO_OFF + "\r\n");
        iRetorno = cupom.BematechTX("Inicio: " + formatador.format(aula.getSerie().getDataInicio()) + "    " + "Termino: " + formatador.format(aula.getSerie().getDataFim()) + "\r\n");
        iRetorno = cupom.BematechTX("Aula: " + aula.getDescaula() + "\r\n");
        iRetorno = cupom.BematechTX("\n" + "Exercicio \nSerie \t\t\tPeso" + "\n\r");

        for (int i = 0; i < aula.getExercicioList().size(); i++) {
            if (!aula.getExercicioList().get(i).getGrupo().getNomeGrupo().equals(nomeGrupo)) {
                nomeGrupo = aula.getExercicioList().get(i).getGrupo().getNomeGrupo();
                iRetorno = cupom.BematechTX("------------------------------------------------" + "\r\n");
                iRetorno = cupom.BematechTX(removeAcentos(aula.getExercicioList().get(i).getGrupo().getNomeGrupo()));
                iRetorno = cupom.BematechTX("\n" + "------------------------------------------------" + "\r\n");

                iRetorno = cupom.BematechTX(BematechComandosDiretos64.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getNomeExercicio()) + "\n\r");
                iRetorno = cupom.BematechTX(BematechComandosDiretos64.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getSerie() + " x " + aula.getExercicioList().get(i).getQuantidade()) + "\t\t\t" + removeAcentos(aula.getExercicioList().get(i).getPeso() + "") + BematechComandosDiretos64.NEGRITO_OFF + " KG\r\n");
            } else {
                iRetorno = cupom.BematechTX(BematechComandosDiretos64.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getNomeExercicio()) + "\n\r");
                iRetorno = cupom.BematechTX(BematechComandosDiretos64.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getSerie() + " x " + aula.getExercicioList().get(i).getQuantidade()) + "\t\t\t" + removeAcentos(aula.getExercicioList().get(i).getPeso() + "") + BematechComandosDiretos64.NEGRITO_OFF + " KG\r\n");
            }
        }
        iRetorno = cupom.BematechTX("\n" + "------------------------------------------------" + "\r\n");
        iRetorno = cupom.BematechTX("Peso: " + aula.getSerie().getPeso1() + "        Peso: " + aula.getSerie().getPeso2() + "        Peso: " + aula.getSerie().getPeso3() + "\r\n");
        iRetorno = cupom.BematechTX("\n" + "Quantidade de Impresoes: " + aula.getImpresso() + "\r\n");
        iRetorno = cupom.AcionaGuilhotina(1);

        return resultado;
    }
    
    public boolean geraCupom86(Aula aula) {
        boolean resultado = false;

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
                iRetorno = cupom.BematechTX(BematechComandosDiretos86.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getSerie() + " x " + aula.getExercicioList().get(i).getQuantidade()) + "\t\t\t" + removeAcentos(aula.getExercicioList().get(i).getPeso() + "") + BematechComandosDiretos86.NEGRITO_OFF + " KG\r\n");
            } else {
                iRetorno = cupom.BematechTX(BematechComandosDiretos86.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getNomeExercicio()) + "\n\r");
                iRetorno = cupom.BematechTX(BematechComandosDiretos86.NEGRITO_ON + removeAcentos(aula.getExercicioList().get(i).getSerie() + " x " + aula.getExercicioList().get(i).getQuantidade()) + "\t\t\t" + removeAcentos(aula.getExercicioList().get(i).getPeso() + "") + BematechComandosDiretos86.NEGRITO_OFF + " KG\r\n");
            }
        }
        iRetorno = cupom.BematechTX("\n" + "------------------------------------------------" + "\r\n");
        iRetorno = cupom.BematechTX("Peso: " + aula.getSerie().getPeso1() + "        Peso: " + aula.getSerie().getPeso2() + "        Peso: " + aula.getSerie().getPeso3() + "\r\n");
        iRetorno = cupom.BematechTX("\n" + "Quantidade de Impresoes: " + aula.getImpresso() + "\r\n");
        iRetorno = cupom.AcionaGuilhotina(1);

        return resultado;
    }

    private String removeAcentos(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }
}
