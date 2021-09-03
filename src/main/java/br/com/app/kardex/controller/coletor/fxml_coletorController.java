package br.com.app.kardex.controller.coletor;

import br.com.app.kardex.main;
import br.com.app.kardex.model.coletor.Inventario;
import br.com.app.kardex.model.coletor.InventarioItem;
import br.com.app.kardex.model.login.Logon;
import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class fxml_coletorController implements Initializable {

    private main application;
    private SqlServer sql = new SqlServer();
    private ObservableList<Inventario> listaInvantario;
    @FXML AnchorPane boxMenu;
    @FXML GridPane boxInventario;
    @FXML private Label usuario;
    @FXML private Label bd;
    @FXML private ComboBox cbInventario;
    @FXML private TextField txtPosicao;
    @FXML private TextField txtCodBarras;
    @FXML private TextField txtCodFabrica;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtQtdCx;
    @FXML private TextField txtVencimento;
    @FXML private TextField txtObs;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        usuario.setText(Logon.getUsuario().getNome());
        bd.setText("bd: "+sql.bd);
    }

    public void setApp(main application){
        application.stage.setMaximized(false);
        this.application = application;
    }

    @FXML
    private void onInventario(){
        boxMenu.setVisible(false);
        boxInventario.setVisible(true);
        application.stage.setTitle("kardex - Inventario");
        setCbInventario();
    }

    @FXML
    private void onMenu(){
        boxMenu.setVisible(true);
        boxInventario.setVisible(false);
        application.stage.setTitle("kardex");
    }

    @FXML
    private void onGravar(){
        InventarioItem invItem = new InventarioItem();
        invItem.setIdinventario(listaInvantario.get(cbInventario.getSelectionModel().getSelectedIndex()).getIdinventario());
        invItem.setIdposicao(1);
        invItem.setIdproduto(1);
        invItem.setQtdcx(0);
        invItem.setQtdprod(0.0);
        invItem.setPeso(0.0);
        invItem.setDtvencimento("");
    }

    private void setCbInventario(){
        Inventario inventario = new Inventario();
        listaInvantario = inventario.getLista();
        ObservableList<String> lista = FXCollections.observableArrayList();
        for(Inventario inv : listaInvantario){
            lista.add(inv.getIdinventario()+" - "+inv.getDescricao());
        }
        cbInventario.setItems(lista);
    }
}
