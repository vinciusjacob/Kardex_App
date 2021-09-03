package br.com.app.kardex.controller.login;

import br.com.app.kardex.model.login.Login;
import br.com.app.kardex.model.login.Logon;
import br.com.app.kardex.model.login.Usuario;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import br.com.app.kardex.main;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

public class fxml_loginController implements Initializable {


    //COMPONENTES VISUAIS
    @FXML private Label msgErro;
    @FXML private Label msgProgress;
    @FXML private HBox msgErroBox;
    @FXML private HBox msgProgressBox;
    @FXML private HBox msgConfBoxDep;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private Button btnLogar;

    //ATRIBUTOS DIVERSOS
    private Usuario usu;
    private Stage stage = new Stage();
    private main application;

    public void initialize(URL location, ResourceBundle resources) {
        btnLogar.disableProperty().bind(txtUsuario.textProperty().isEmpty().or(txtSenha.textProperty().isEmpty()));
        txtSenha.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    onLogar();
                }
            }
        });
    }

    public void setApp(main application){
        application.stage.setHeight(300);
        application.stage.setWidth(400);
        application.stage.centerOnScreen();
        application.stage.setMaximized(false);
        this.application = application;
    }

    /**
     * Valida usuario e senha.
     * Envia para formulario.
     */
    @FXML
    private void onLogar(){
        msgErroBox.setVisible(false);
        msgProgressBox.setVisible(true);
        txtUsuario.setDisable(true);
        txtSenha.setDisable(true);
        btnLogar.disableProperty().unbind();
        btnLogar.setDisable(true);

        //Tarefa que valida Login em segundo plano
        Task<String> logar = new Task<String>(){
            @Override
            protected String call() throws Exception {
                updateMessage("Validando Usuario e Senha...");
                usu = new Usuario().get(txtUsuario.getText());
                if (usu != null){
                    if(usu.getSenha().equals(txtSenha.getText())){
                        return null;
                    }else {
                        return ("Senha incorreta!");
                    }
                }else{
                    return ("Usuario não existe!");
                }
            }

            @Override
            protected void succeeded() {
                if(getValue() == null){
                    msgProgressBox.setVisible(false);
                    msgConfBoxDep.setVisible(true);

                    Logon.setUsuario(usu);
                    Logon.setDataLogin(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    Logon.setIdempresa(1);

                    Stage stageLogin = application.stage;
                    application.stage = new Stage();

                    if (usu.getConferente()){
                        application.gotoColetor();
                    }else{
                        application.gotoLogist();
                    }

                    application.stage.show();
                    stageLogin.close();
                }else{
                    txtUsuario.setDisable(false);
                    txtSenha.setDisable(false);
                    btnLogar.disableProperty().bind(txtUsuario.textProperty().isEmpty().or(txtSenha.textProperty().isEmpty()));
                    msgProgressBox.setVisible(false);
                    msgErro.setText(getValue());
                    msgErroBox.setVisible(true);
                }
            }

            @Override
            protected void failed(){
                msgProgressBox.setVisible(false);
                msgErro.setText("Erro ao conectar com o servidor.");
                msgErroBox.setVisible(true);
            }
        };
        Thread t = new Thread(logar);
        msgProgress.textProperty().bind(logar.messageProperty());
        t.start();
    }
}
