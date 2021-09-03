/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.controller.logistica;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import br.com.app.kardex.model.logistica.Depara;
import br.com.app.kardex.model.logistica.Expedicao;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Vinicius
 */
public class fx_histExpedicaoController implements Initializable {
    
    private final Expedicao expedicao = new Expedicao();
    @FXML
    private TextField txtPesquisar;
    @FXML
    private BorderPane pane;
    @FXML
    private GridPane paneCad;
    @FXML
    private TableView tabelaFat;
    @FXML
    private TableColumn colFatViagem;
    @FXML
    private TableColumn colFatPlaca;
    @FXML
    private TableColumn colFatCodPro;
    @FXML
    private TableColumn colFatDescPro;
    @FXML
    private TableColumn colFatQtd;
    @FXML
    private TableColumn colFatUnd;
    @FXML
    private TableColumn colFatPeso;
    @FXML
    private TableColumn colFatNota;
    @FXML
    private TableColumn colFatData;
    @FXML
    private TableColumn colFatStatus;
    @FXML
    private ComboBox cbStatus;
    
    @FXML
    private void Cadastro(){
        pane.setRight(paneCad);
    }
    @FXML
    private void Cancelar(){
        pane.setRight(null);
    }
    @FXML
    private void Atualizar(){
        setTableFat();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.setRight(null);
        setCbStatus();
        setTableFat();
    }
    private void setTableFat(){
        colFatViagem.setCellValueFactory(new PropertyValueFactory<>("viagem"));
        colFatPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colFatCodPro.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colFatDescPro.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colFatQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        colFatUnd.setCellValueFactory(new PropertyValueFactory<>("unidade"));
        colFatPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colFatNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        colFatData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colFatStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        FilteredList<Expedicao> filteredData = new FilteredList<>(expedicao.getLista());
        txtPesquisar.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(exp -> {
                String status = cbStatus.getSelectionModel().getSelectedItem().toString();
                if ((newValue == null || newValue.isEmpty()) && status.equals("Todos")){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(status.equals("Todos")){
                    status = "e";
                }
                if (exp.getStatus().contains(status) && exp.getViagem().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (exp.getStatus().contains(status) && exp.getNota().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (exp.getStatus().contains(status) && exp.getProduto().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if(null != exp.getPlaca()){
                    if (exp.getStatus().contains(status) && exp.getPlaca().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                if (exp.getStatus().contains(status) && exp.getDescricao().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        
        cbStatus.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(exp -> {
                String txt = txtPesquisar.getText();
                if ((txt == null || txt.isEmpty())&& newValue.equals("Todos")) {
                    return true;
                }
                String status = newValue.toString();
                String lowerCaseFilter = txt.toLowerCase();
                if(status.equals("Todos")){
                    status = "e";
                }
                if (exp.getStatus().contains(status) && exp.getViagem().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (exp.getStatus().contains(status) && exp.getNota().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (exp.getStatus().contains(status) && exp.getProduto().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if(null != exp.getPlaca()){
                    if (exp.getStatus().contains(status) && exp.getPlaca().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                if (exp.getStatus().contains(status) && exp.getDescricao().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Depara> sortedData = new SortedList(filteredData);
        sortedData.comparatorProperty().bind(tabelaFat.comparatorProperty());
        tabelaFat.setItems(sortedData);
    }
    private void setCbStatus(){
        ObservableList<String> dados = FXCollections.observableArrayList(
            "Todos",
            "Atendido",
            "Pendente"
        );
        cbStatus.setItems(dados);
        cbStatus.getSelectionModel().select(0);
        
        cbStatus.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue.equals("Pendentes")){
                    
                }else if(newValue.equals("Atendidos")){
                    
                }else{
                    
                }
            }
        });
    }
}
