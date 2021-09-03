/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.controller.logistica;

import br.com.app.kardex.model.logistica.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Vinicius
 */
public class fx_faturamentoController implements Initializable{
    
    private MontarCarga mont = new MontarCarga();
    private Nota nota = new Nota();
    private Faturamento faturamento = new Faturamento();
    private Escala escala = new Escala();
    private Expedicao expedicao = new Expedicao();
    private Depara depara = new Depara();
    private DeparaLista deparalista = new DeparaLista();
    private File arquivo;
    @FXML private BorderPane pane;
    @FXML private GridPane paneImportar;
    @FXML private GridPane paneMontCarga;
    @FXML private GridPane paneNfs;
    @FXML private GridPane paneDepara;
    @FXML private ProgressIndicator progress;
    @FXML private Label progressText;
    @FXML private Button btnImportar;
    @FXML private Button btnCancelar;
    @FXML private Button btnSelecionar;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtArquivo;
    @FXML private DatePicker dpData;
    @FXML private DatePicker dpDtMont;
    @FXML private TableView<MontarCarga> tabelaCarga;
    @FXML private TableColumn colSelec;
    @FXML private TableColumn colNf;
    @FXML private TableColumn colPeso;
    @FXML private TableColumn colVol;
    @FXML private TableColumn colStatus;
    @FXML private TableView tabelaFat;
    @FXML private TableColumn colFatViagem;
    @FXML private TableColumn colFatPlaca;
    @FXML private TableColumn colFatCodPro;
    @FXML private TableColumn colFatDescPro;
    @FXML private TableColumn colFatQtd;
    @FXML private TableColumn colFatUnd;
    @FXML private TableColumn colFatPeso;
    @FXML private TableColumn colFatNota;
    @FXML private TableColumn colFatData;
    @FXML private TableColumn colFatStatus;
    @FXML private TableView<DeparaLista> tabelaDep;
    @FXML private TableColumn colViagemDep;
    @FXML private TableColumn colExportarDep;
    @FXML private TableView<Escala> tabelaEscala;
    @FXML private TableColumn colPlacaEsc;
    @FXML private TableColumn colDataEsc;
    @FXML private TableColumn colQtdEsc;
    @FXML private TableColumn colPesoEsc;
    @FXML private TableColumn colVolEsc;
    @FXML private TextField txtPlaca;
    @FXML private TextField txtPesquisar;
    @FXML private DatePicker txtPesquisarM;
    @FXML private Label msgCarg;
    @FXML private Button btnExportar;
    @FXML private Button btnExcluir;
    @FXML private Button btnNf;
    @FXML private TableView<Nota> tabelaNf;
    @FXML private TableColumn colPlacaNf;
    @FXML private TableColumn colDataNf;
    @FXML private TableColumn colNotaNf;
    @FXML private TableColumn colPesoNf;
    @FXML private TableColumn colVolumeNf;
    @FXML private TableColumn colStatusNf;
    @FXML private TableColumn colNewDataNf;
    @FXML private TextField pesquisarNf;
    @FXML private CheckBox cbMarcados;
    @FXML private HBox HboxNfOk;
    @FXML private HBox msgProgressBoxDep;
    @FXML private CheckBox cbTabelaDep;
    @FXML private CheckBox cbTabelaNf;
    @FXML private HBox msgErrorBoxDep;
    @FXML private HBox msgConfBoxDep;
    @FXML private Label msgErrorDep;
    
    @FXML
    private void saveExcel(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione local para salvar arquivo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Arquivo Excel", "*.csv"));
        File saveFile = fileChooser.showSaveDialog(null);
        msgErrorBoxDep.setVisible(false);
        msgConfBoxDep.setVisible(false);
        msgProgressBoxDep.setVisible(true);
        Task<Object[]> tarefa = new Task<Object[]>(){
            @Override
            protected Object[] call() throws Exception {
                if(saveFile != null){    
                    File newFile = saveFile;
                    if(!saveFile.getPath().contains(".csv")){
                        newFile = new File(saveFile.getPath()+".csv");
                    }
                    Writer writer = null;
                    ObservableList<Depara> lista = Pesquisar();
                    if (lista == null){
                        return new Object[]{"Selecione pelo menos uma viagem.",false};
                    }
                    Integer rows = lista.size();
                    writer = new BufferedWriter(new FileWriter(newFile));
                    String titulo = "PLACA;VIAGEM;PRODUTO;QTD FATUR.;QTD CARREG.;DIFERENCA \n";
                    writer.write(titulo);
                    for(int i=0;i<rows;i++){
                        String text = ""
                            +lista.get(i).getPlaca()+ ";"
                            +lista.get(i).getViagem()+ ";"
                            +lista.get(i).getProduto()+ ";"
                            +lista.get(i).getQtdfat()+ ";"
                            +lista.get(i).getQtdcar()+ ";"
                            +lista.get(i).getDif()+ "\n";
                        writer.write(text);
                    }
                    writer.flush();
                    writer.close();
                    return new Object[]{"Exportado com sucesso!",true};
                }else{
                    return null;
                }
            }
            @Override
            protected void succeeded() {
                if(getValue() == null){
                    msgProgressBoxDep.setVisible(false);
                }else if((Boolean)getValue()[1]){
                    msgProgressBoxDep.setVisible(false);
                    msgConfBoxDep.setVisible(true);
                }else{
                    msgProgressBoxDep.setVisible(false);
                    msgErrorBoxDep.setVisible(true);
                    msgErrorDep.setText(getValue()[0].toString());
                }
            }
            @Override
            protected void failed(){
                msgProgressBoxDep.setVisible(false);
                msgErrorBoxDep.setVisible(true);
            }
            
        };
        new Thread(tarefa).start();
    }
    private ObservableList<Depara> Pesquisar() throws Exception{
        depara = new Depara();
        Integer row = tabelaDep.getItems().size();
        String verificador = null;
        for(int i = 0;i < row; i++){
            if(tabelaDep.getItems().get(i).isExportar()){
                depara.getListaDep(tabelaDep.getItems().get(i).getViagem());
                verificador = "ok";
            }
        }
        if(verificador == null)
            return null;
        
        return depara.listaNew;
    }
    @FXML
    private void montCarg(){
        Integer row = tabelaCarga.getItems().size();
        String placa = txtPlaca.getText();
        String data = dpDtMont.getValue().toString();
        Boolean Ck = false;
        String msg = "";
        try{
            if(placa.equals("")){
                msg = "Informe uma placa!";
                txtPlaca.requestFocus();
            }else if(placa.length() != 7){
                msg = "Placa Invalida!";
                txtPlaca.requestFocus();
            }else if(mont.isEscalaRep(placa, data)){
                msg = "Placa já existe para essa data!";
                txtPlaca.requestFocus();
            }else{
                msg = "Nenhuma nota foi selecionada!";
                for(int i=0;i<row;i++){
                    if(tabelaCarga.getItems().get(i).isSelc()){
                        Integer nota = tabelaCarga.getItems().get(i).getNota();
                        String status = tabelaCarga.getItems().get(i).getStatus();
                        Integer volume = tabelaCarga.getItems().get(i).getQtd();
                        Double peso = tabelaCarga.getItems().get(i).getPeso();
                        if(status.equals("Pendente"))
                            status = "Atendido";
                        else
                            status = "Reentrega";
                            mont.Cadastar(nota, placa, data, status, volume, peso);
                            msg = "Gravado com sucesso!";
                    }
                }
                this.setTableMontCarg();
                txtPlaca.setText("");
                dpDtMont.setValue(LocalDate.now());
            }
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error!");
            dialogoInfo.setHeaderText("Erro com o servidor!");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
            msg = "Erro ao gravar informações!";
            txtPlaca.setText("");
            dpDtMont.setValue(LocalDate.now());
        }
        setTableEsc();
        msgCarg.setText(msg);
        
    }
    private void importStatus(Boolean imp, String msg){
        progress.setVisible(imp);
        btnImportar.setDisable(imp);
        btnCancelar.setDisable(imp);
        btnSelecionar.setDisable(imp);
        progressText.setText(msg);
        if(imp){
            txtTitulo.setEditable(false);
        }else{
            txtTitulo.setEditable(true);
            txtTitulo.setText("");
            txtArquivo.setText("");
        }
        
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
        tabelaFat.setItems(expedicao.getLista());
    }
    private void setTableEsc(){
        colPlacaEsc.setCellValueFactory(new PropertyValueFactory<Escala,String>("placa"));
        colDataEsc.setCellValueFactory(new PropertyValueFactory<Escala,String>("data"));
        colQtdEsc.setCellValueFactory(new PropertyValueFactory<Escala,Integer>("qtdnf"));
        colPesoEsc.setCellValueFactory(new PropertyValueFactory<Escala,Double>("peso"));
        colVolEsc.setCellValueFactory(new PropertyValueFactory<Escala,Integer>("volume"));
        tabelaEscala.setItems(escala.getLista(dpData.getValue().toString()));
    }
    private void setTableDep(){
        colViagemDep.setCellValueFactory(new PropertyValueFactory<>("viagem"));
        colViagemDep.setEditable(true);
        colExportarDep.setCellValueFactory(new PropertyValueFactory<>("exportar"));
        String data = txtPesquisarM.getValue().toString();
        ObservableList lista = deparalista.getLista(data);
        tabelaDep.setItems(lista);
        tabelaDep.setEditable(true);
    }
    private void setTableMontCarg(){
        colSelec.setCellValueFactory(new PropertyValueFactory<MontarCarga,CheckBox>("selec"));
        colSelec.setEditable(true);
        colNf.setCellValueFactory(new PropertyValueFactory<MontarCarga,Integer>("nota"));
        colPeso.setCellValueFactory(new PropertyValueFactory<MontarCarga,Double>("peso"));
        colVol.setCellValueFactory(new PropertyValueFactory<MontarCarga,Integer>("qtd"));
        colStatus.setCellValueFactory(new PropertyValueFactory<MontarCarga,Integer>("status"));
        
        FilteredList<MontarCarga> filteredData = new FilteredList<>(mont.getLista());
        
        txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(carga -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (carga.getNota().toString().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        
        cbMarcados.selectedProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(carga -> {
                // If filter text is empty, display all persons.
                if (!newValue) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toString().toLowerCase();
                if (carga.isSelc().toString().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        
        SortedList<MontarCarga> sortedData = new SortedList(filteredData);
        sortedData.comparatorProperty().bind(tabelaCarga.comparatorProperty());
        
        tabelaCarga.setItems(sortedData);
        tabelaCarga.setEditable(true);
    }
    private void setTableNf(String placa,String data){
        colPlacaNf.setCellValueFactory(new PropertyValueFactory<Nota,String>("placa"));
        colDataNf.setCellValueFactory(new PropertyValueFactory<Nota,String>("data"));
        colNotaNf.setCellValueFactory(new PropertyValueFactory<Nota,Integer>("nota"));
        colPesoNf.setCellValueFactory(new PropertyValueFactory<Nota,Double>("peso"));
        colVolumeNf.setCellValueFactory(new PropertyValueFactory<Nota,Integer>("volume"));
        colStatusNf.setCellValueFactory(new PropertyValueFactory<Nota,String>("status"));
        colNewDataNf.setCellValueFactory(new PropertyValueFactory<Nota,String>("newdata"));
        FilteredList<Nota> filteredData = new FilteredList<>(nota.getLista(placa,data));
        pesquisarNf.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(nf->{
                if ((newValue == null || newValue.isEmpty())) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (nf.getNota().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Nota> sortedData = new SortedList(filteredData);
        sortedData.comparatorProperty().bind(tabelaNf.comparatorProperty());
        tabelaNf.setItems(sortedData);
    }
    @FXML
    private void btnNfGravar() throws Exception{
        Integer row = tabelaNf.getItems().size();
        for(int i = 0;i < row;i++){
            String newdata = tabelaNf.getItems().get(i).getNewdataValue().toString();
            Integer cod = tabelaNf.getItems().get(i).getCod();
            nota.Alterar(newdata, cod);
        }
        HboxNfOk.setVisible(true);
    }
    private void cbSelectionData(){
        dpData.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setTableEsc();
            }
        });
    }
    private void cbSelectionDataDep(){
        txtPesquisarM.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setTableDep();
            }
        });
    }
    private void cbTabelaDep(){
        cbTabelaDep.selectedProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Integer row = tabelaDep.getItems().size();
                for(int i = 0;i < row;i++){
                    tabelaDep.getItems().get(i).setExportar((Boolean)newValue);
                }
            }
            
        });
    }
    private void cbTabelaNf(){
        cbTabelaNf.selectedProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Integer row = tabelaNf.getItems().size();
                for(int i = 0;i < row;i++){
                    tabelaNf.getItems().get(i).setNewdata((Boolean)newValue);
                }
            }
            
        });
    }
    private void tblSelection(){
        tabelaEscala.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnExportar.setDisable(false);
                btnExcluir.setDisable(false);
                btnNf.setDisable(false);
            }else{
                btnExportar.setDisable(true);
                btnExcluir.setDisable(true);
                btnNf.setDisable(true);
            }
        });
    }
    @FXML
    private void Importar(){
        importStatus(true,"Importando...");
        Task importar = new Task(){
            @Override
            protected String call() throws Exception {    
                return TarefaImportar();
            }
            @Override
            protected void succeeded(){
                String msg = getValue().toString();
                setTableMontCarg();
                //setTableFat();
                //setTableDep();
                importStatus(false, msg);
            }
            @Override
            protected void failed(){
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setTitle("Error!");
                dialogoInfo.setHeaderText("Erro ao importar arquivo!");
                dialogoInfo.setContentText(getException().toString());
                dialogoInfo.showAndWait();
                importStatus(false, "Não foi possivel realizar importação!");
            }
        };
        Thread t = new Thread(importar);
        t.setDaemon(true);
        t.start();
    }
    private String TarefaImportar() throws Exception{
        String msg = "";
        Importacao imp = new Importacao();
        if(arquivo == null){
            msg = ("Selecione um arquivo de texto.");
        }else if(txtTitulo.getText().equals("")){
            msg = ("Defina um titulo!");
        }else if(imp.isTxtRep(txtTitulo.getText())){
            msg = ("Titulo já existe!");
        }else{
            String titulo = txtTitulo.getText();
            String data = getDataAtual();
            faturamento.Importar(arquivo, titulo, data);
            msg = ("Faturamento importado com sucesso!");
            
        }
        return msg;
    }
    private String getDataAtual(){
        Calendar data = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataSt = sdf.format(data.getTime());
        return dataSt;
    }
    @FXML
    private void ExcluirEsc(){
        try{
            String placa = tabelaEscala.getSelectionModel().getSelectedItem().getPlaca();
            String data = tabelaEscala.getSelectionModel().getSelectedItem().getData();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirmar exclusão");
            alert.setContentText("Tem certeza que deseja excluir a carga "+placa+"?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                escala.Excluir(placa,data);
                setTableEsc();
            }
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
        }
    }
    @FXML
    private void selectFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o arquivo de texto");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Arquivo de texto", "*.txt","*.TXT","*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            txtArquivo.setText(selectedFile.getName());
            arquivo = selectedFile;
        }
        else {
            txtArquivo.setText("");
            arquivo = null;
        }
    }
    @FXML
    private void saveFile(){
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione local para salvar arquivo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Arquivo de texto", "*.txt"),
            new FileChooser.ExtensionFilter("Arquivo de texto", "*.TXT"));
        File saveFile = fileChooser.showSaveDialog(null);
        try{
        if (saveFile != null){
            String data = dpData.getValue().toString();
            faturamento.Exportar(saveFile,data);
            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Informação");
            dialogoInfo.setHeaderText(null);
            dialogoInfo.setContentText("Arquivo exportado com sucesso!");
            dialogoInfo.showAndWait();
        }
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString()+" ");
            dialogoInfo.showAndWait();
        }
    }
    @FXML
    private void Fechar() throws Exception{
        progress.setVisible(false);
        progressText.setText("");
        txtTitulo.setText("");
        txtArquivo.setText("");
        pane.setRight(null);
        msgProgressBoxDep.setVisible(false);
        msgErrorBoxDep.setVisible(false);
        msgConfBoxDep.setVisible(false);
        txtPesquisarM.setValue(LocalDate.now());
        setTableDep();
    }
    
    @FXML
    private void Cancelar(){
        progress.setVisible(false);
        progressText.setText("");
        txtTitulo.setText("");
        txtArquivo.setText("");
        pane.setRight(null);
        HboxNfOk.setVisible(false);
    }
    @FXML
    private void pImportar(){
        pane.setRight(paneImportar);
    }
    @FXML
    private void pMontCarga(){
        pane.setRight(paneMontCarga);
    }
    @FXML
    private void pNfs(){
        pane.setRight(paneNfs);
        String placa = tabelaEscala.getSelectionModel().getSelectedItem().getPlaca();
        String data = tabelaEscala.getSelectionModel().getSelectedItem().getData();
        setTableNf(placa,data);
        HboxNfOk.setVisible(false);
    }
    
    @FXML
    private void pDepara(){
        pane.setRight(paneDepara);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.setRight(null);
        dpData.setValue(LocalDate.now());
        dpDtMont.setValue(LocalDate.now());
        txtPesquisarM.setValue(LocalDate.now());
        setTableMontCarg();
        //setTableFat();
        setTableDep();
        cbTabelaNf();
        setTableEsc();
        cbSelectionData();
        cbSelectionDataDep();
        cbTabelaDep();
        tblSelection();
    }
    
}
