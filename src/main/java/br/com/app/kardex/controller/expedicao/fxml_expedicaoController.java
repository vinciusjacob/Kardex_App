package br.com.app.kardex.controller.expedicao;

import br.com.app.kardex.model.cadastros.Departamento;
import br.com.app.kardex.model.expedicao.FormCarga;
import br.com.app.kardex.model.expedicao.FormConferencia;
import br.com.app.kardex.model.expedicao.FormNota;
import br.com.app.kardex.model.kardex.*;
import br.com.app.kardex.model.login.Logon;
import br.com.app.kardex.model.login.Usuario;
import br.com.app.kardex.util.KTextField;
import br.com.app.kardex.util.MsgBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class fxml_expedicaoController implements Initializable {


    //COMPONENTES PRINCIPAIS
    @FXML private BorderPane paneMain;
    @FXML private AnchorPane paneCad;
    @FXML private AnchorPane paneCarga;
    @FXML private AnchorPane anchorPane;
    @FXML private KTextField txtArquivo;
    @FXML private ComboBox cbModelo;
    @FXML private Button btnSelecionar;
    @FXML private Button btnImportar;
    @FXML private TextField txtPesquisar;
    @FXML public TableView<Carga> tblExpedicao;
    @FXML private ComboBox cbDepFiltro;
    @FXML private DatePicker dtFaturamento;
    @FXML private DatePicker dtFiltro;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private Button btnEncerrar;

    /*********** COMPONENTES FORM CARGA ***********/
    //COMPONENTES DA ABA CARGA
    @FXML public Tab tabCarga;
    @FXML public TextField txtIdCarga;
    @FXML public DatePicker dtDescarga;
    @FXML public ComboBox cbFilial;
    @FXML public TextField txtDescricao;
    @FXML public ComboBox cbDepartamento;
    @FXML public TextField txtPlaca;
    @FXML public TextField txtTransportadora;
    @FXML public TextField txtMotorista;
    @FXML public Button btnImportarXml;
    @FXML public Button btnImprimirMapa;
    @FXML public Button btnExcluirNota;
    @FXML public Button btnCancelarNota;
    @FXML public TableView<NotaFiscal> tblNotas;
    @FXML public Button btnSalvar;

    //COMPONENTES DA ABA NOTA
    @FXML public Tab tabNota;
    @FXML public TextField txtNumero;
    @FXML public TableView<NotaFiscalItem> tblItens;
    @FXML public TableColumn colQtdProd;
    @FXML public TableColumn colQtdCx;

    //COMPONENTES DA ABA CONFERENCIA
    @FXML public Tab tabConferencia;
    @FXML public TextField txtDoca;
    @FXML public TextField txtLacre;
    @FXML public ComboBox cbConferente;
    @FXML public TextField txtQtdPallet;
    @FXML public TextField txtTempBau;
    @FXML public TextField txtPesoIni;
    @FXML public TextField txtPesoFim;
    @FXML public TextField txtProdutoLote;
    @FXML public TextField txtQtdCxLote;
    @FXML public TextField txtQtdProdLote;
    @FXML public TextField txtNumlote;
    @FXML public DatePicker dtFabLote;
    @FXML public DatePicker dtVencLote;
    @FXML public TextArea txtObsLote;
    @FXML public CheckBox checkAvariado;
    @FXML public Button btnIncluirLote;
    @FXML public Button btnExcluirLote;
    @FXML public Button btnSalvarLote;
    @FXML public Button btnCancelar;
    @FXML public TableView<NotaFiscalItemLote> tblLotes;

    //COMPONENTES DIVERSOS
    public ObservableList<Departamento> listaDepartamento;
    public ObservableList<Usuario> listaConferente;
    private File arquivo;
    public Carga cargaForm = new Carga();
    public ObservableList<Carga> listaCarga;
    public ObservableList<NotaFiscalItemLote> listaLote;
    public ObservableList<NotaFiscalItemLote> listaLoteSintetica;
    public NotaFiscalItemLote loteForm = new NotaFiscalItemLote();
    private Thread thread = new Thread();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneMain.setRight(null);
        dtFiltro.setValue(LocalDate.now());
        dtFiltro.valueProperty().addListener((observable, oldValue, newValue)->{
            if(newValue != null){
                setTblExpedicao();
            }
        });

        btnSelecionar.disableProperty().bind(cbModelo.getSelectionModel().selectedItemProperty().isNull());
        btnImportar.disableProperty().bind(txtArquivo.textProperty().isEmpty());
        btnAlterar.disableProperty().bind(tblExpedicao.getSelectionModel().selectedItemProperty().isNull());
        btnExcluir.disableProperty().bind(tblExpedicao.getSelectionModel().selectedItemProperty().isNull());
        btnEncerrar.disableProperty().bind(tblExpedicao.getSelectionModel().selectedItemProperty().isNull());
        setCb();
    }

    /**
     * Atualiza todos os combox da tela de expedição em segundo plano.
     * Após atualizar, inicia automaticamente a atualização da tabela e listas.
     */
    private void setCb(){
        ObservableList<String> listaDep = FXCollections.observableArrayList();
        listaDep.add("Todos");
        cbDepFiltro.setItems(listaDep);
        cbDepFiltro.getSelectionModel().select(0);

        Task<String> tarefa = new Task<String>() {
            @Override
            protected String call() throws Exception {
                Departamento departamento = new Departamento();
                listaDepartamento = departamento.getLista();

                for(Departamento rowDep : listaDepartamento){
                    listaDep.add(rowDep.getDescricao());
                }
                return null;
            }
            @Override
            protected void succeeded(){
                ObservableList<String> lista = FXCollections.observableArrayList();
                lista.add("Arquivo Vigor");
                cbModelo.setItems(lista);

                cbDepFiltro.setItems(listaDep);
                cbDepFiltro.getSelectionModel().select(0);
                setTblExpedicao();
            }
        };
        Thread t  = new Thread(tarefa);
        t.start();
    }

    /**
     * Abre formulario de altera carga.
     */
    @FXML
    private void onBtnAlterar(){
        cargaForm = tblExpedicao.getSelectionModel().getSelectedItem();
        if(cargaForm.getVerifica().equals("Pendente")){
            new FormCarga(this).onStageEmrecebimento(cargaForm);
            new FormNota(this).onStageEmrecebimento();
            new FormConferencia(this).onStagePendente(cargaForm);
            paneMain.setRight(paneCarga);
            tblExpedicao.setDisable(true);
        }else if(cargaForm.getVerifica().equals("Em Andamento")){
            new FormCarga(this).onStageEmrecebimento(cargaForm);
            new FormNota(this).onStageEmrecebimento();
            new FormConferencia(this).onStageEmandamento(cargaForm);
            paneMain.setRight(paneCarga);
            tblExpedicao.setDisable(true);
        }else if(cargaForm.getVerifica().equals("Faturado")){
            new FormCarga(this).onStageRecebido(cargaForm);
            new FormNota(this).onStageRecebido();
            new FormConferencia(this).onStageConcluido(cargaForm);
            paneMain.setRight(paneCarga);
            tblExpedicao.setDisable(true);
        }

    }

    @FXML
    private void onBtnEncerrar(){
        Carga carga = tblExpedicao.getSelectionModel().getSelectedItem();
        if(carga.getStatus().equals("Em Andamento")){
            tblExpedicao.getSelectionModel().getSelectedItem().setStatus("Faturado");
            tblExpedicao.getSelectionModel().getSelectedItem().setVerifica("Faturado");
            tblExpedicao.getSelectionModel().getSelectedItem().update();
            new MsgBox(anchorPane,"Carga encerrada com sucesso!").setMsgSucces(true);
        }
    }

    /**
     * Abre formulario para importação de pedidos.
     */
    @FXML
    private void onBtnImpPedidos(){
        paneMain.setRight(paneCad);
        dtFaturamento.setValue(LocalDate.now());
    }

    /**
     * Salva dados do formulario de carregamento.
     */
    @FXML
    private void onBtnSalvarCarga() {
        MsgBox msgBox = new MsgBox(anchorPane,"");
        String msg = onSalvarCarga();
        if (msg == null) {
            if(txtIdCarga.getText().isEmpty()){
                new FormCarga(this).onReset();
                new FormNota(this).onReset();
            }
            msgBox.setMsgSucces(true);
            msgBox.setMensage("Carga salva com sucesso!");
        } else {
            msgBox.setMsgError(true);
            msgBox.setMensage("Não foi possivel salvar carga!");
            System.out.println("Error: SalvarCarga." + msg);
        }
    }

    private String onSalvarCarga(){
        try{
            if(txtIdCarga.getText().isEmpty()){
                Carga carga = new Carga();
                carga.setTipo(1);
                carga.setDescricao(txtDescricao.getText());
                carga.setTransportadora(txtTransportadora.getText());
                carga.setMotorista(txtMotorista.getText());
                carga.setPlaca(txtPlaca.getText());
                carga.setIdusuario(Logon.getUsuario().getIdusuario());
                carga.setIdconferente(0);
                carga.setIdempresa(Logon.getIdempresa());
                carga.setIddepartamento(listaDepartamento.get(cbDepartamento.getSelectionModel().getSelectedIndex()).getIddepartamento());
                carga.setEncerrada(false);
                carga.setDoca(0);
                carga.setNum_lacre("");
                carga.setStatus("Pendente");
                carga.setQtdpallet(0);
                carga.setTemperaturabau(0.0);
                carga.setPesoinicial(0.0);
                carga.setPesofinal(0.0);
                carga.setDtcarga(dtDescarga.getValue().format(DateTimeFormatter.ISO_DATE));
                carga.setDtmovimento(dtDescarga.getValue().format(DateTimeFormatter.ISO_DATE));
                carga.setDtinicio("");
                carga.setDtfim("");

                String msg = carga.insert();
                if (msg == null) {
                    return null;
                } else {
                    return msg;
                }

            }else{
                tblExpedicao.getSelectionModel().getSelectedItem().setDescricao(txtDescricao.getText());
                tblExpedicao.getSelectionModel().getSelectedItem().setTransportadora(txtTransportadora.getText());
                tblExpedicao.getSelectionModel().getSelectedItem().setMotorista(txtMotorista.getText());
                tblExpedicao.getSelectionModel().getSelectedItem().setPlaca(txtPlaca.getText());
                tblExpedicao.getSelectionModel().getSelectedItem().setIddepartamento(listaDepartamento.get(cbDepartamento.getSelectionModel().getSelectedIndex()).getIddepartamento());
                tblExpedicao.getSelectionModel().getSelectedItem().setDtcarga(dtDescarga.getValue().format(DateTimeFormatter.ISO_DATE));
                tblExpedicao.getSelectionModel().getSelectedItem().setDtmovimento(dtDescarga.getValue().format(DateTimeFormatter.ISO_DATE));
                String msg = tblExpedicao.getSelectionModel().getSelectedItem().update();

                if (msg == null) {
                    cargaForm = tblExpedicao.getSelectionModel().getSelectedItem();
                    return null;
                } else {
                    return msg;
                }
            }
        }catch (Exception ex){
            System.out.println("Exception: SalvarCarga."+ex.toString());
            return "Error ao salvar carga. [Exception.onSalvar]";
        }
    }


    @FXML
    private void onBtnCancelarPed(){
        NotaFiscal notaFiscal = tblNotas.getSelectionModel().getSelectedItem();
        for(NotaFiscalItem item : notaFiscal.getListaItem()){
            for(NotaFiscalItemLote lote : tblLotes.getItems()){
                if(lote.getIdnotaitem() == item.getIdnotaitem()){
                    lote.deleteoOne();
//                    tblLotes.getItems().remove(lote);
                }
            }
        }
        new MsgBox(anchorPane,"Pedido cancelado com sucesso!").setMsgSucces(true);
    }

    /**
     * Exclui pedido selecionado.
     */
    @FXML
    private void onBtnExcluirPed(){
        NotaFiscal pedido = tblNotas.getSelectionModel().getSelectedItem();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("KARDEX");
        dialog.setHeaderText("Deseja realmente excluir o pedido "+pedido.getNumpedido()+"?");
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não",ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getButtonTypes().setAll(btnSim, btnNao);
        dialog.showAndWait().ifPresent(b -> {
            if(b == btnSim){
                for (NotaFiscalItemLote lote : new NotaFiscalItemLote().getLista(Integer.parseInt(txtIdCarga.getText()))){
                    lote.deleteoOne();
                }
                if(pedido.getListaItem().size() > 0) {
                    pedido.getListaItem().get(0).deleteAll();
                }
                pedido.delete();
                setTblExpedicao();
                new FormNota(this).onStageEmrecebimento();
                new FormCarga(this).onStageEmrecebimento(cargaForm);
                new MsgBox(anchorPane,"Excluido com sucesso!").setMsgSucces(true);
            }else{
                new MsgBox(anchorPane,"Exclusão cancelada!").setMsgError(true);
            }
        });
    }

    /**
     * Exclui carga selecionada.
     * Só é possivel excluir cagas que não tenham pedidos/notas vinculado.
     */
    @FXML
    private void onBtnExcluir(){
        Carga carga = tblExpedicao.getSelectionModel().getSelectedItem();

        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("KARDEX");
        dialog.setHeaderText("Deseja realmente excluir a carga "+carga.getIdcarga()+"?");
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não",ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getButtonTypes().setAll(btnSim, btnNao);
        dialog.showAndWait().ifPresent(b -> {
            if(b == btnSim){
                String msg = carga.delete();
                if (msg == null){
                    setTblExpedicao();
                    new MsgBox(anchorPane,"Carga  excluida com sucesso!").setMsgSucces(true);
                }else{
                    new MsgBox(anchorPane,"Não foi possivel excluida carga!").setMsgError(true);
                }
            }else{
                new MsgBox(anchorPane,"Exclusão cancelada!").setMsgError(true);
            }
        });
    }

    @FXML
    private void onBtnCortarLote(){
        NotaFiscalItemLote lote = tblLotes.getSelectionModel().getSelectedItem();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("KARDEX");
        dialog.setHeaderText("Confirma corte do produto "+lote.getIdproduto()+"?");
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não",ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getButtonTypes().setAll(btnSim, btnNao);
        dialog.showAndWait().ifPresent(b -> {
            if(b == btnSim){
                String msg = lote.deleteoOne();
                if (msg == null){
                    ObservableList<NotaFiscalItemLote> lista = tblLotes.getItems();
                    lista.remove(lote);
                    tblLotes.setItems(lista);
                    new MsgBox(anchorPane,"Corte efetuado com sucesso!").setMsgSucces(true);
                }else{
                    new MsgBox(anchorPane,"Não foi possivel cortar o produto!").setMsgError(true);
                }
            }
        });
    }

    @FXML
    private void onBtnAlteraLote(){
        onTxtQtdloteEvento();
        loteForm = tblLotes.getSelectionModel().getSelectedItem();
        txtProdutoLote.setText(Integer.toString(loteForm.getIdproduto()));
        txtQtdCxLote.setText(Integer.toString(loteForm.getQtdcx()));
        txtQtdCxLote.setDisable(false);
        if(loteForm.getProduto().getPeso_variavel()){
            txtQtdProdLote.setDisable(false);
        }
        txtQtdProdLote.setText(Double.toString(loteForm.getPeso()));
        txtObsLote.setText(loteForm.getObs());
        btnIncluirLote.setVisible(false);
        btnExcluirLote.setVisible(false);
        btnCancelar.setVisible(true);
        btnSalvarLote.setVisible(true);
    }

    @FXML
    private void onBtnCancelar(){
        NotaFiscalItemLote lote = tblLotes.getSelectionModel().getSelectedItem();
        txtProdutoLote.setText("");
        txtQtdCxLote.setText("");
        txtQtdProdLote.setText("");
        txtObsLote.setText("");

        txtQtdCxLote.setDisable(true);
        txtQtdProdLote.setDisable(true);
        btnIncluirLote.setVisible(true);
        btnExcluirLote.setVisible(true);
        btnCancelar.setVisible(false);
        btnSalvarLote.setVisible(false);
    }

    @FXML
    private void onBtnSalvarLote(){
        ObservableList<NotaFiscalItemLote> lista = tblLotes.getItems();
        lista.remove(loteForm);
        NotaFiscalItemLote lote = loteForm;
        loteForm.deleteoOne();
        lote.setQtdcx(Integer.parseInt(txtQtdCxLote.getText()));
        lote.setPeso(Double.parseDouble(txtQtdProdLote.getText()));
        lote.setObs(txtObsLote.getText());
        lote.insert();

        onBtnCancelar();
        lista.add(lote);
        tblLotes.setItems(lista);
    }

    private void onTxtQtdloteEvento(){
        txtQtdCxLote.textProperty().addListener((observable,oldValue, newValue)->{
            if(!newValue.isEmpty()) {
                Double valor = Integer.parseInt(newValue) * loteForm.getProduto().getPesocx();
                System.out.println("Passei aqui: " + loteForm.getProduto().getPesocx());
                txtQtdProdLote.setText(Double.toString(valor));
            }
        });
    }

    /**
     * Atualiza listas (Notas/Lotes) em segundo plano.
     */
    private void onAtualizaListas(MsgBox msgBox){
        Task<String> tarefa = new Task<String>() {
            @Override
            protected String call() throws Exception {
                Platform.runLater(() -> msgBox.setMensage("Atualizando listas!.."));
                for (int x = 0;x < tblExpedicao.getItems().size();x++){
                    ObservableList<NotaFiscal> listaNota = new NotaFiscal().getLista(tblExpedicao.getItems().get(x).getIdcarga());
                    ObservableList<NotaFiscalItemLote> listaLoteSintetica = new NotaFiscalItemLote().getListaSintetica(tblExpedicao.getItems().get(x).getIdcarga());

//                    ObservableList<NotaFiscalItemLote> listaLote = new NotaFiscalItemLote().getLista(tblExpedicao.getItems().get(x).getIdcarga());
                    ObservableList<NotaFiscalItemLote> listaLote = FXCollections.observableArrayList();
                    tblExpedicao.getItems().get(x).setListaLote(listaLote);


                    tblExpedicao.getItems().get(x).setListaNota(listaNota);
                    tblExpedicao.getItems().get(x).setListaLoteSintetica(listaLoteSintetica);
                    tblExpedicao.getItems().get(x).setVerifica(tblExpedicao.getItems().get(x).getStatus());
                }
                return null;
            }
            @Override
            protected void succeeded(){
                msgBox.setMsgProgress(false);
                msgBox.setMensage("Atualizado com sucesso!");
                msgBox.setMsgSucces(true);
            }
        };
        thread.interrupt();
        thread = new Thread(tarefa);
        thread.start();
    }

    /**
     * Atualiza tabela em segundo plano.
     */
    private void setTblExpedicao(){
        tblExpedicao.setItems(null);
        MsgBox msgBox = new MsgBox(anchorPane,"Atualizando Dados!..");
        msgBox.setMsgProgress(true);
        Task<String> tarefa = new Task<String>() {
            @Override
            protected String call() throws Exception {
                listaCarga = new Carga().getLista(2,dtFiltro.getValue().toString());
                FilteredList<Carga> filteredData = new FilteredList<>(listaCarga);
                txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(t -> {
                        if ((newValue == null || newValue.isEmpty())) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (t.getDescricao().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        if (t.getPlaca().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }


                        return false;
                    });
                });
                cbDepFiltro.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
                    filteredData.setPredicate(a -> {
                        if (newValue.equals("Todos")) {
                            return true;
                        }
                        int id = listaDepartamento.get(cbDepFiltro.getSelectionModel().getSelectedIndex()-1).getIddepartamento();
                        if (a.getIddepartamento() == id) {
                            return true;
                        }
                        return false;
                    });
                });
                SortedList<Carga> sortedData = new SortedList(filteredData);
                sortedData.comparatorProperty().bind(tblExpedicao.comparatorProperty());
                tblExpedicao.setItems(sortedData);
                return null;
            }

            @Override
            protected void succeeded(){
                onAtualizaListas(msgBox);
            }

        };
        Thread t= new Thread(tarefa);
        t.start();
    }

    @FXML
    private void onFechar(){
        paneMain.setRight(null);
        tblExpedicao.setDisable(false);
    }

    @FXML
    private void onBtnImprimirMapa(){
        onImprimir(false);
    }

    @FXML
    private void onBtnVisualizarMapa(){
        onImprimir(true);
    }

    private void onImprimir(Boolean visualizar) {
        try {
            if(cargaForm.getStatus().equals("Pendente")){
                cargaForm.setStatus("Em Andamento");
                cargaForm.update();
                tblExpedicao.getSelectionModel().getSelectedItem().setVerifica("Em Andamento");
//                new FormConferencia(this).onStageEmandamento(cargaForm);
            }

            onSalvarCarga();
            cargaForm.setPlaca(txtPlaca.getText());

            String local = new File(".").getCanonicalPath();
            String relatorio = local + "\\Relatorios\\Novo_Mapa.jrxml";

            Map parameters = new HashMap();
            parameters.put("total", Integer.toString(cargaForm.getVolume()));
            parameters.put("data", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH/mm/ss")));
            parameters.put("corte", Integer.toString(cargaForm.getVolume_corte()));
            int total = cargaForm.getVolume()+cargaForm.getVolume_corte();
            parameters.put("totalcarga", Integer.toString(total));
            JasperReport report = JasperCompileManager.compileReport(relatorio);
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(tblExpedicao.getSelectionModel().getSelectedItem().getListaLoteSintetica(), false));

            if(visualizar){
                JasperViewer view = new JasperViewer(print, false);
                view.setTitle("Logística - Mapa de Separação");
                view.setVisible(true);
            }else{
                JasperPrintManager.printPage(print, 0, true);
            }
        }catch (Exception ex){
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("KARDEX");
            dialog.setHeaderText("Falha ao imprimir relatório.");
            dialog.setContentText(ex.toString());
            dialog.show();
            System.out.println("Error: onImprimir."+ex.toString());
        }
    }

    @FXML
    private void onSelecionar(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o arquivo de texto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivo de texto", "*.txt","*.TXT","*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            txtArquivo.setText(selectedFile.getName());
            arquivo = selectedFile;
        }else{
            txtArquivo.setText("");
            arquivo = null;
        }
    }

    @FXML
    private void onImportar(){
        MsgBox msgBox = new MsgBox(anchorPane,"Importando arquivo...");
        msgBox.setMsgProgress(true);
        btnImportar.disableProperty().unbind();
        btnImportar.setDisable(true);

        Task tarefa = new Task() {
            @Override
            protected String call() throws Exception {
                ImportarPedidos imp  = new ImportarPedidos();
                if(cbModelo.getSelectionModel().getSelectedIndex() == 0){
                    return imp.ImportarVigor(arquivo,dtFaturamento.getValue().toString(),msgBox);
                }if(cbModelo.getSelectionModel().getSelectedIndex() == 1){
                    return imp.ImportarCencosud(arquivo,dtFaturamento.getValue().toString());
                }
                else{
                    return "Formato do arquivo invalido!";
                }
            }

            @Override
            protected void succeeded(){
                if(getValue() == null){
                    msgBox.setMsgProgress(false);
                    msgBox.setMensage("Arquivo importado com sucesso!");
                    msgBox.setMsgSucces(true);
                    txtArquivo.setText("");
                    arquivo = null;
                    btnImportar.disableProperty().bind(txtArquivo.textProperty().isEmpty());
                    setTblExpedicao();
                }else{
                    msgBox.setMsgProgress(false);
                    msgBox.setMensage(getValue().toString());
                    msgBox.setMsgError(true);
                    btnImportar.disableProperty().bind(txtArquivo.textProperty().isEmpty());
                }
            }
        };
        Thread t = new Thread(tarefa);
        t.start();
    }
}
