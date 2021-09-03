package br.com.app.kardex.controller.expedicao;

import br.com.app.kardex.model.expedicao.Corte;
import br.com.app.kardex.util.MsgBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class fxml_corteController implements Initializable {

    //COMPONENTES PRINCIPAIS
    @FXML private BorderPane paneMain;
    @FXML private AnchorPane anchorPane;
    @FXML private DatePicker dtFiltro;
    @FXML private ComboBox<String> cbDepFiltro;
    @FXML private TextField txtPesquisar;
    @FXML private TableView<Corte> tblCorte;
    private ObservableList<Corte> listaCorte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onFechar();
        dtFiltro.setValue(LocalDate.now());
        setCb();
        setTblCorte();
        dtFiltro.valueProperty().addListener((observable,oldValue,newValue)->{
            if(newValue != null){
                setTblCorte();
            }
        });
    }

    private void setCb(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        lista.add("VIGOR");
        cbDepFiltro.setItems(lista);
        cbDepFiltro.getSelectionModel().select(0);
    }

    @FXML
    private void onFechar(){
        paneMain.setRight(null);
    }

    @FXML
    private void setTblCorte(){
        MsgBox msgBox = new MsgBox(anchorPane,"Atualizando dados!..");
        msgBox.setMsgProgress(true);
        Task<String> tarefa = new Task<String>() {
            @Override
            protected String call() throws Exception {
                listaCorte = new Corte().getLista(dtFiltro.getValue().toString());
                return null;
            }

            @Override
            protected void succeeded(){
                tblCorte.setItems(listaCorte);
                msgBox.setMsgProgress(false);
                msgBox.setMensage("Atualizado com sucesso!");
                msgBox.setMsgSucces(true);
            }
        };
        Thread t = new Thread(tarefa);
        t.start();
    }

    @FXML
    private void onExportarCsv(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione local para salvar arquivo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivo Excel", "*.csv"));
        File saveFile = fileChooser.showSaveDialog(null);
        if (saveFile != null){
            if(!saveFile.getPath().contains(".csv")){
                saveFile = new File(saveFile.getPath()+".csv");
            }
            Integer rows = this.tblCorte.getItems().size();
            Writer writer = null;
            try{
                writer = new BufferedWriter(new FileWriter(saveFile));
                String titulo = "CARGA;PLACA;IDPRODUTO;CODFABRICA;DESCRICAO;UNIDADE;QTDCXCORTE \n";
                writer.write(titulo);
                for(int i=0;i<rows;i++){
                    String text = ""
                            +tblCorte.getItems().get(i).getCarga()+";"
                            +tblCorte.getItems().get(i).getPlaca()+ ";"
                            +tblCorte.getItems().get(i).getIdproduto()+ ";"
                            +tblCorte.getItems().get(i).getCodfabrica()+ ";"
                            +tblCorte.getItems().get(i).getDescricao()+ ";"
                            +tblCorte.getItems().get(i).getUnidade()+ ";"
                            +tblCorte.getItems().get(i).getQtdcxcorte()+ " \n";
                    writer.write(text);
                }
                writer.flush();
                writer.close();
            }catch(Exception ex){
                new MsgBox(anchorPane,"Falha ao salvar arquivo.").setMsgError(true);
            }
        }else{
            new MsgBox(anchorPane,"Não foi possivel salvar arquivo, Verifique o local selecionado.").setMsgError(true);
        }
    }

}
