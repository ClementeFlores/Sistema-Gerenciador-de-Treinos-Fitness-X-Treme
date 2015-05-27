package com.auinfo.fitnessxtreme.util;


import com.nitgen.SDK.BSP.NBioBSPJNI;

/**
 *
 * @author Ulisses
 */
public class LeitorBiometrico {

    public String registrarDigital() {

        NBioBSPJNI bsp = new NBioBSPJNI();
        NBioBSPJNI.FIR_HANDLE salvarFIR = bsp.new FIR_HANDLE();

        bsp.OpenDevice();

        bsp.Capture(salvarFIR);

        NBioBSPJNI.FIR_TEXTENCODE textoCodificadoFIR = bsp.new FIR_TEXTENCODE();
        bsp.GetTextFIRFromHandle(salvarFIR, textoCodificadoFIR);

        return textoCodificadoFIR.TextFIR;
    }

    public boolean verificarDigital(String digital) {
        NBioBSPJNI bsp = new NBioBSPJNI();

        bsp.OpenDevice();

        NBioBSPJNI.INPUT_FIR entradaFIR = bsp.new INPUT_FIR();
        NBioBSPJNI.INPUT_FIR entrada2FIR = bsp.new INPUT_FIR();

        Boolean resultado = false;
        
        NBioBSPJNI.FIR_PAYLOAD payload = bsp.new FIR_PAYLOAD();

        NBioBSPJNI.FIR_TEXTENCODE textoCodificadoFIR = bsp.new FIR_TEXTENCODE();

        textoCodificadoFIR.TextFIR = digital;

        entrada2FIR = bsp.new INPUT_FIR();

        NBioBSPJNI.FIR_TEXTENCODE textoCodificado2FIR = bsp.new FIR_TEXTENCODE();
        
        textoCodificado2FIR.TextFIR = registrarDigital();

        entradaFIR.SetTextFIR(textoCodificadoFIR);
        entrada2FIR.SetTextFIR(textoCodificado2FIR);

        bsp.VerifyMatch(entrada2FIR, entradaFIR, resultado, payload);

        return resultado;
    }
}
