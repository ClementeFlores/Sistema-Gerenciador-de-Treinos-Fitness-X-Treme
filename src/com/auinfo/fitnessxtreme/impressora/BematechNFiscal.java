package com.auinfo.fitnessxtreme.impressora;

/*
 * Copyright (C) 2014 Tiago
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

/**
 * Interface para utilização das dll's mp2032 e mp2064 da bematech
 *
 * @author Tiago
 */
import com.sun.jna.Library;
import com.sun.jna.Native;

public interface BematechNFiscal extends Library {

    public BematechNFiscal Instance = (BematechNFiscal) Native.loadLibrary("mp2064", BematechNFiscal.class);

    public int IniciaPorta(String porta);
    public int FechaPorta();
    public int BematechTX(String bufTrans);
    public int ComandoTX(String bufTrans, int tamBufTrans);
    public int CaracterGrafico(String bufTrans, int tamBufTrans);
    public int DocumentInserted();
    public int Le_Status();
    public int AutenticaDoc(String texto, int tempo);
    public int Le_Status_Gaveta();
    public int ConfiguraTamanhoExtrato(int numeroLinhas);
    public int HabilitaExtratoLongo(int flag);
    public int HabilitaEsperaImpressao(int flag);
    public int EsperaImpressao();
    public int ConfiguraModeloImpressora(int modelo);
    public int AcionaGuilhotina(int modo);
    public int FormataTX(String bufTras, int tipoletra, int italic, int sublin, int expand, int enfat);
    public int HabilitaPresenterRetratil(int flag);
    public int ProgramaPresenterRetratil(int tempo);
    public int VerificaPapelPresenter();
    public int ImprimeBitmap(String bitmap, int orientacao);

// Função para Configuração dos Códigos de Barras
//function ConfiguraCodigoBarras( Altura: integer; Largura: integer; PosicaoCaracteres: integer; Fonte: integer; Margem: integer ): integer; stdcall; far; external 'MP2032.DLL';
// Funções para impressão dos códigos de barras
    /*function ImprimeCodigoBarrasUPCA( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasUPCE( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasEAN13( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasEAN8( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasCODE39( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasCODE93( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasCODE128( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasITF( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasCODABAR( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasISBN( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasMSI( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasPLESSEY( Codigo: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeCodigoBarrasPDF417( NivelCorrecaoErros: integer; Altura: integer; Largura: integer; Colunas: integer; Codigo: string ): integer; stdcall; far; external 'MP2032.DLL'; */
// Funções para impressão de BitMap
    /*function ImprimeBitmap ( name: string; mode: integer): integer; stdcall; far; external 'MP2032.DLL';
 
     function ImprimeBmpEspecial ( name: string; xScale: integer; yScale: integer; angle: integer): integer; stdcall; far; external 'MP2032.DLL';
 
     function AjustaLarguraPapel ( width: integer): integer; stdcall; far; external 'MP2032.DLL';
 
     function SelectDithering ( mode: integer): integer; stdcall; far; external 'MP2032.DLL';
 
     function PrinterReset : integer; stdcall; far; external 'MP2032.DLL';
 
     function LeituraStatusEstendido ( A: array of byte ): integer; stdcall; far; external 'MP2032.DLL';
 
     function IoControl ( flag: Integer; mode : Boolean ): integer; stdcall; far; external 'MP2032.DLL';
 
     function DefineNVBitmap ( count: integer; filenames: array of string ): integer; stdcall; far; external 'MP2032.DLL';
     */
    public int PrintNVBitmap(int image, int mode);
    /*
     function Define1NVBitmap ( filename : string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function DefineDLBitmap ( filename: string ): integer; stdcall; far; external 'MP2032.DLL';
 
     function PrintDLBitmap ( mode: integer ): integer; stdcall; far; external 'MP2032.DLL'; */

// Função de Firmware
 //function AtualizaFirmware ( fileName: string): integer; stdcall; far; external 'MP2032.DLL';
}
