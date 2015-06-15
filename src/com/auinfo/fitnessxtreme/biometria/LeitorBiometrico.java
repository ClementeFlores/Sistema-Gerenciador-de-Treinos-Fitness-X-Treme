package com.auinfo.fitnessxtreme.biometria;

import com.nitgen.SDK.BSP.NBioBSPJNI;
import java.awt.Canvas;

/**
 *
 * @author Ulisses
 */
public class LeitorBiometrico {

    NBioBSPJNI bsp;
    NBioBSPJNI.WINDOW_OPTION winOption;

    public LeitorBiometrico() {

        bsp = new NBioBSPJNI();

    }

    public void finalizar() {
        bsp.dispose(); // Teminate NBioBSPJNI Class Object
        bsp = null;
    }

    public String capturar() {

        bsp.OpenDevice();

        NBioBSPJNI.FIR_HANDLE hSavedFIR;

        hSavedFIR = bsp.new FIR_HANDLE();

        //     bsp.Enroll(hSavedFIR, null); // Enroll
        bsp.Capture(hSavedFIR); // Capture
// Get Text FIR
        if (bsp.IsErrorOccured() == false) {
            NBioBSPJNI.FIR_TEXTENCODE textSavedFIR;
            textSavedFIR = bsp.new FIR_TEXTENCODE();
            bsp.GetTextFIRFromHandle(hSavedFIR, textSavedFIR);

            return textSavedFIR.TextFIR;
        }
// Get Binary FIR
        if (bsp.IsErrorOccured() == false) {
            NBioBSPJNI.FIR fullSavedFIR;
            fullSavedFIR = bsp.new FIR();
            bsp.GetFIRFromHandle(hSavedFIR, fullSavedFIR);

        }
        return null;
    }

    public boolean verificar(String textSavedFIRBanco, String textSavedFIRLido) {

        NBioBSPJNI.FIR_TEXTENCODE inputTEXTFIR = bsp.new FIR_TEXTENCODE();
        NBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
        NBioBSPJNI.FIR_TEXTENCODE inputTEXTFIR2 = bsp.new FIR_TEXTENCODE();
        NBioBSPJNI.INPUT_FIR inputFIR2 = bsp.new INPUT_FIR();

        Boolean bResult = new Boolean(false);

        Boolean resultado = false;

        NBioBSPJNI.FIR_PAYLOAD payload = bsp.new FIR_PAYLOAD();

        inputTEXTFIR.TextFIR = textSavedFIRBanco;
        inputTEXTFIR2.TextFIR = textSavedFIRLido;

        inputFIR.SetTextFIR(inputTEXTFIR);
        inputFIR2.SetTextFIR(inputTEXTFIR2);

        bsp.VerifyMatch(inputFIR, inputFIR2, bResult, payload);
        if (bsp.IsErrorOccured() == false) {
            if (bResult) {
                System.out.println("Verify OK - Payload: " + payload.GetText());
                resultado = true;
            } else {
                System.out.println("Verify Failed");
            }
        }
        return resultado;
    }

    public void verificar(String textSavedFIRBanco) {

        NBioBSPJNI.FIR_TEXTENCODE inputTEXTFIR = bsp.new FIR_TEXTENCODE();
        NBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
        NBioBSPJNI.FIR_TEXTENCODE inputTEXTFIR2 = bsp.new FIR_TEXTENCODE();
        NBioBSPJNI.INPUT_FIR inputFIR2 = bsp.new INPUT_FIR();

        Boolean bResult = new Boolean(false);

        NBioBSPJNI.FIR_PAYLOAD payload = bsp.new FIR_PAYLOAD();

        inputTEXTFIR.TextFIR = textSavedFIRBanco;
        inputTEXTFIR2.TextFIR = capturar();

        inputFIR.SetTextFIR(inputTEXTFIR);
        inputFIR2.SetTextFIR(inputTEXTFIR2);

        bsp.VerifyMatch(inputFIR, inputFIR2, bResult, payload);
        if (bsp.IsErrorOccured() == false) {
            if (bResult) {
                System.out.println("Verify OK - Payload: " + payload.GetText());
            } else {
                System.out.println("Verify Failed");
            }
        } else {

            System.out.println("Ocorreu um erro");
            System.out.println(bsp.GetErrorCode());
        }
    }

    public String capturar2() {
        winOption = bsp.new WINDOW_OPTION();
        winOption.WindowStyle = NBioBSPJNI.WINDOW_STYLE.INVISIBLE;
        
        bsp.OpenDevice();

        NBioBSPJNI.FIR_HANDLE hSavedFIR;

        hSavedFIR = bsp.new FIR_HANDLE();

        bsp.Capture(NBioBSPJNI.FIR_PURPOSE.VERIFY, hSavedFIR, -1, null, winOption);
        if (bsp.IsErrorOccured() == false) {
            NBioBSPJNI.FIR_TEXTENCODE textSavedFIR;
            textSavedFIR = bsp.new FIR_TEXTENCODE();
            bsp.GetTextFIRFromHandle(hSavedFIR, textSavedFIR);

            return textSavedFIR.TextFIR;
        }

        return capturar();
    }

}
