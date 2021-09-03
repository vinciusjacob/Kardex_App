/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.controller.logistica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import br.com.app.kardex.model.logistica.Importacao;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Vinicius
 */
public class fx_importacoesController implements Initializable {
    
    private Importacao importacao = new Importacao();
    
    @FXML
    private TableView<Importacao> tabela;
    @FXML
    private TableColumn colCod;
    @FXML
    private TableColumn colDescr;
    @FXML
    private TableColumn colDt;
    @FXML
    private Button btnExcluir;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTable();
        tblSelection();
    }    
    
    @FXML
    private void Atualizar(){
        setTable();
    }
    @FXML
    private void Excluir(){
        try{
            Integer cod = tabela.getSelectionModel().getSelectedItem().getCod();
            String txt = tabela.getSelectionModel().getSelectedItem().getDescr();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirmar exclusão");
            alert.setContentText("Tem certeza que deseja excluir txt '"+txt+"'?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                importacao.Excluir(cod);
                setTable();
            }
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error!");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor!");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
        }
    }
    private void tblSelection(){
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnExcluir.setDisable(false);
            }else{
                btnExcluir.setDisable(true);
            }
        });
    }
    
    private void setTable(){
        try {
            colCod.setCellValueFactory(new PropertyValueFactory<>("cod"));
            colDescr.setCellValueFactory(new PropertyValueFactory<>("descr"));
            colDt.setCellValueFactory(new PropertyValueFactory<>("data"));
            tabela.setItems(importacao.getLista());
        } catch (Exception ex) {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
        }
    }
}
