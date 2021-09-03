package br.com.app.kardex;

import br.com.app.kardex.controller.coletor.fxml_coletorController;
import br.com.app.kardex.controller.login.fxml_loginController;
import br.com.app.kardex.controller.logistica.fxml_logisticaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main extends Application {

    public Stage stage;
    private String title = "Kardex";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.centerOnScreen();
        gotoLogin();
        stage.show();
    }

    public void gotoLogin(){
        try {
            fxml_loginController login = (fxml_loginController) replaceSceneContent("/br.com.app.kardex/view/login/fxml_login.fxml");
            stage.setTitle("Kardex - Login");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/br.com.app.kardex/icons/logo.png")));
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoLogist(){
        try {
            fxml_logisticaController controller = (fxml_logisticaController) replaceSceneContent("/br.com.app.kardex/view/logistica/fxml_logistica.fxml");
            stage.setTitle(title);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/br.com.app.kardex/icons/logo.png")));
            controller.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoColetor(){
        try {
            fxml_coletorController controller = (fxml_coletorController) replaceSceneContent("/br.com.app.kardex/view/coletor/fxml_coletor.fxml");
            stage.setTitle("Kardex");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/br.com.app.kardex/icons/logo.png")));
            controller.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(main.class.getResource(fxml));
        Pane page;
        try {
            page = (Pane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        return (Initializable) loader.getController();
    }

    private String getDataFormat(Date data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dt = sdf.format(data);
        return dt;
    }
}
