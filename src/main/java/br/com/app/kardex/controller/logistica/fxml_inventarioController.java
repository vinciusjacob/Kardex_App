package br.com.app.kardex.controller.logistica;

import br.com.app.kardex.model.coletor.Inventario;
import br.com.app.kardex.util.MsgBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class fxml_inventarioController implements Initializable {

    @FXML private BorderPane paneMain;
    @FXML private AnchorPane paneCad;
    @FXML private AnchorPane anchorPane;
    @FXML private TextField txtId;
    @FXML private TextField txtDescricao;


    @FXML
    private void onFechar(){
        paneMain.setRight(null);
    }

    @FXML
    private void onNovo(){
        paneMain.setRight(paneCad);
        txtId.setText("");
        txtDescricao.setText("");
    }

    @FXML
    private void onSalvar(){
        MsgBox msgBox = new MsgBox(anchorPane,"");
        if(txtDescricao.getText().isEmpty()){
            msgBox.setMensage("Descricao não pode ficar vazia!");
            msgBox.setMsgError(true);
        }else{
            Inventario inventario = new Inventario();
            inventario.setDescricao(txtDescricao.getText());
            inventario.setFinalizado(false);
            inventario.setDtfim(null);
            inventario.setIdusuario(1);
            String msg = inventario.insert();
            if(msg == null){
                msgBox.setMensage("Novo inventario cadastrado com sucesso!");
                msgBox.setMsgSucces(true);
            }else{
                msgBox.setMensage("Error: "+msg);
                msgBox.setMsgError(true);
            }

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneMain.setRight(null);
    }
}
