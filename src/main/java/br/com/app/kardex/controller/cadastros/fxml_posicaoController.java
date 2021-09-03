package br.com.app.kardex.controller.cadastros;

import br.com.app.kardex.model.cadastros.Posicao;
import br.com.app.kardex.model.logistica.Estoque;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class fxml_posicaoController implements Initializable {

    @FXML private TableView<Estoque> tabela;
    @FXML private TextField txtPesquisar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    private void setTable(){
        Posicao posicao = new Posicao();
        FilteredList<Posicao> filteredData = new FilteredList<>(posicao.getLista());

        txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pos->{
                if ((newValue == null || newValue.isEmpty())) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(pos.getIdposicao().contains(lowerCaseFilter)) {
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
