/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.controller.logistica;

import br.com.app.kardex.model.cadastros.Departamento;
import br.com.app.kardex.model.logistica.Estoque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Vinicius
 */
public class fxml_estoqueController implements Initializable {
    
    private Estoque estoque = new Estoque();
    ObservableList<Departamento> listaDepartamento;
    @FXML private TableView<Estoque> tabela;
    @FXML private TextField txtPesquisar;
    @FXML private ComboBox cbCliente;
    
    @FXML private void Atualizar(){
        setTable();
    }

    @FXML
    private void saveExcel(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione local para salvar arquivo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Arquivo Excel", "*.csv"));
        File saveFile = fileChooser.showSaveDialog(null);
        if (saveFile != null){
            if(!saveFile.getPath().contains(".csv")){
                saveFile = new File(saveFile.getPath()+".csv");
            }
            Integer rows = this.tabela.getItems().size();
            Writer writer = null;
            try{
                writer = new BufferedWriter(new FileWriter(saveFile));
                String titulo = "CODIGO;SIGLA;DESCRICAO;QTD;FAIXA;FABRICACAO \n";
                writer.write(titulo);
                for(int i=0;i<rows;i++){
                    String text = ""
                        +tabela.getItems().get(i).getIdproduto()+";"
                        +tabela.getItems().get(i).getQtdcx()+ ";"
                        +tabela.getItems().get(i).getPeso().toString().replaceAll("\\.", ",")+ ";"
                        +tabela.getItems().get(i).getDtfabricacao()+ " \n";
                    writer.write(text);
                }
                writer.flush();
                writer.close();
            }catch(Exception ex){
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setTitle("Error");
                dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
                dialogoInfo.setContentText(ex.toString());
                dialogoInfo.showAndWait();
            }
        }
    }
    private void setTable(){
        if(!cbCliente.getSelectionModel().isEmpty()){
            Departamento dep = listaDepartamento.get(cbCliente.getSelectionModel().getSelectedIndex());
            FilteredList<Estoque> filteredData = new FilteredList<>(estoque.getLista(dep.getIddepartamento()));

            txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(est->{
                    if ((newValue == null || newValue.isEmpty())) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(Integer.toString(est.getIdproduto()).contains(lowerCaseFilter)) {
                        return true;
                    }
                    if(est.getCodfabrica().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if(est.getDescricao().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if(est.getDtvencimento().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Estoque> sortedData = new SortedList(filteredData);
            sortedData.comparatorProperty().bind(tabela.comparatorProperty());
            tabela.setItems(sortedData);
        }
    }

    private void setCbCliente(){
        Departamento departamento = new Departamento();
        listaDepartamento = departamento.getLista();
        ObservableList<String> lista = FXCollections.observableArrayList();
        for(Departamento dep : listaDepartamento){
            lista.add(dep.getDescricao());
        }
        cbCliente.setItems(lista);
        cbCliente.getSelectionModel().select(lista.size()-1);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCbCliente();
        setTable();
    }
}
