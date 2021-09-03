package br.com.app.kardex.controller.logistica;

import br.com.app.kardex.model.login.Logon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import br.com.app.kardex.util.SqlServer;
import br.com.app.kardex.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Vinicius
 */
public class fxml_logisticaController implements Initializable{
    
    private SqlServer sql = new SqlServer();
    private main application;
    @FXML private Label usuario;
    @FXML private Label data;
    @FXML private Label bd;
    @FXML private TabPane tabPane;

    public void setApp(main application){
        application.stage.setMaximized(true);
        this.application = application;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getTabs().clear();
        usuario.setText(Logon.getUsuario().getNome());
        data.setText(Logon.getDataLogin());
        bd.setText("BD: "+sql.bd);
    }
    
    @FXML
    private void Logof() throws IOException{
        application.stage.close();
        application.gotoLogin();
        application.stage.show();
    }

    @FXML private void onPosicao(){
        addTab("/br.com.app.kardex/view/cadastro/fxml_posicao.fxml","Posições");
    }

    @FXML private void onEstoque(){
        addTab("/br.com.app.kardex/view/logistica/fxml_estoque.fxml","Estoque");
    }

    @FXML private void onInventario(){
        addTab("/br.com.app.kardex/view/logistica/fxml_inventario.fxml","Inventario");
    }

    @FXML private void onRecebimento(){
        addTab("/br.com.app.kardex/view/recebimento/fxml_recebimento.fxml","Ordem Rec.");
    }

    @FXML private void onRecebimentoAn(){
        addTab("/br.com.app.kardex/view/recebimento/fxml_recebimento.fxml","Ordem Rec.");
    }

    @FXML private void onExpedicao(){
        addTab("/br.com.app.kardex/view/expedicao/fxml_expedicao.fxml","Carregamento");
    }

    @FXML private void onCorte(){
        addTab("/br.com.app.kardex/view/expedicao/fxml_corte.fxml","Cortes");
    }

    /**
     * Inclui ou Seleciona tab ao TabPane.
     * @param fxml Caminho do arquivo a ser incluido.
     * @param text Descrição da Tab.
     */
    private void addTab(String fxml,String text) {
        Tab tab = getTab(text);
        if(tab != null){
            tabPane.getSelectionModel().select(tab);
        }else{
            tab = new Tab();
            tab.setText(text);
            includeFxml(fxml,tab);
        }
    }

    /**
     * Inclui arquivo view ao Tab.
     * @param fxml Caminho do arquivo a ser incluido.
     * @param tab Tab onde será incluido o view.
     */
    private void includeFxml(String fxml,Tab tab){
        try{
            Parent root = FXMLLoader.load(main.class.getResource(fxml));
            tab.setContent(root);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
        }
    }

    /**
     * Retorna uma Tab do TabPane de acordo com a descrição.
     * @param text Descrição da tab a ser retornada.
     * @return Uma tab caso encontra ou null se não encontrar.
     */
    private Tab getTab(String text){
        for(Tab tab : tabPane.getTabs()){
            if(tab.getText().equals(text)){
                return tab;
            }
        }
        return null;
    }
}
