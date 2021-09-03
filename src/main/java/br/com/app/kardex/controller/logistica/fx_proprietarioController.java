/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.controller.logistica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Vinicius
 */
public class fx_proprietarioController implements Initializable{
    
    private fx_proprietarioClass proprietario = new fx_proprietarioClass();
    
    @FXML
    private BorderPane pane;
    @FXML
    private GridPane paneCad;
    @FXML
    private TableView<fx_proprietarioClass> tabela;
    @FXML
    private TableColumn<fx_proprietarioClass, Integer> colCod;
    @FXML
    private TableColumn<fx_proprietarioClass, String> colRs;
    @FXML
    private TableColumn<fx_proprietarioClass, String> colNf;
    @FXML
    private TableColumn<fx_proprietarioClass, String> colCnpj;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtRazao;
    @FXML
    private TextField txtFantasia;
    @FXML
    private TextField txtCnpj;
    
    
    
    @FXML
    private void Cadastro(){
        pane.setRight(paneCad);
        txtCod.requestFocus();
    }
    @FXML
    private void Cancelar(){
        pane.setRight(null);
        txtCod.setText("");
        txtRazao.setText("");
        txtFantasia.setText("");
        txtCnpj.setText("");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.setRight(null);
        
        colCod.setCellValueFactory(new PropertyValueFactory<>("cod"));
        colRs.setCellValueFactory(new PropertyValueFactory<>("razao"));
        colNf.setCellValueFactory(new PropertyValueFactory<>("fantasia"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        tabela.setItems(proprietario.getLista());
    }
     
}

