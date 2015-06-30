package com.auinfo.fitnessxtreme;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.auinfo.fitnessxtreme.util.Hardware4Win;
import com.auinfo.fitnessxtreme.util.HexSha;
import com.auinfo.fitnessxtreme.util.ManipulaConfigs;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author BrunoRicardo
 */
public class FitnessXTreme extends Application {

    public static Scene scenes;
    private Properties props;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/auinfo/fitnessxtreme/tela/TelaBase.fxml"));

        Scene scene = new Scene(root);

        scenes = scene;

        stage.setScene(scene);
        stage.setTitle("Sistema Gerenciador de Treinos - Fitness Xtreme");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/auinfo/fitnessxtreme/tela/imagens/icone.png")));
        //stage.setFullScreen(true);
        //stage.initStyle(StageStyle.UNDECORATED);

        //-----------------------------------------------
        try {
            props = ManipulaConfigs.getProp("config\\licenca.properties");
        } catch (IOException e) {
            System.out.println("Houve um erro ao carregar as configurações. Possíveis causas incluem arquivo de configuração danificado e/ou ausente.\n");
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        String serial = new Hardware4Win().getSerialNumber();
        String serial2 = new HexSha(serial).convertSha();
        String serialRegistrado = props.getProperty("prop.licenca.modelo");
        if (!serial2.equals(serialRegistrado)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Licença");
            alert.setContentText("Licença Inválida!\n"
                    + "Entre em contato com o desenvolvedor!\n"
                    + "Envie esta código de erro: "
                    + "\"Erro: 00x332&" + serial + "\"");
            alert.showAndWait();
            System.exit(1);
        }

        //-----------------------------------------------
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
