/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.controller.logistica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Vinicius
 */
public class fx_devolucaoController implements Initializable {
    
    @FXML
    private BorderPane pane;
    @FXML
    private GridPane paneIncluir;
    
    @FXML
    private void Incluir(){
        pane.setRight(paneIncluir);
    }
    @FXML
    private void Cancelar(){
        pane.setRight(null);
    }

    public void initialize(URL location, ResourceBundle resources) {
        pane.setRight(null);
    } 
    
}
