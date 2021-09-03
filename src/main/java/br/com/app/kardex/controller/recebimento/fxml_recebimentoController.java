package br.com.app.kardex.controller.recebimento;

import br.com.app.kardex.dao.nfe.Nfe;
import br.com.app.kardex.model.cadastros.Departamento;
import br.com.app.kardex.model.cadastros.Produto;
import br.com.app.kardex.model.cadastros.ProdutoFator;
import br.com.app.kardex.model.kardex.Carga;
import br.com.app.kardex.model.kardex.NotaFiscal;
import br.com.app.kardex.model.kardex.NotaFiscalItem;
import br.com.app.kardex.model.kardex.NotaFiscalItemLote;
import br.com.app.kardex.model.login.Logon;
import br.com.app.kardex.model.login.Usuario;
import br.com.app.kardex.model.recebimento.FormCarga;
import br.com.app.kardex.model.recebimento.FormConferencia;
import br.com.app.kardex.model.recebimento.FormNota;
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
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class fxml_recebimentoController implements Initializable {


    //COMPONENTES PRINCIPAIS
    @FXML public BorderPane paneMain;
    @FXML public AnchorPane paneCad;
    @FXML public AnchorPane anchorPane;
    @FXML public Button btnNovo;
    @FXML public Button btnAlterar;
    @FXML public Button btnExcluir;
    @FXML public TextField txtPesquisar;
    @FXML public DatePicker dtFiltro;
    @FXML public TableView<Carga> tblRecebimentos;
    @FXML public ComboBox<String> cbStatusFiltro;

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
    @FXML public Button btnIncluirNota;
    @FXML public Button btnImportarXml;
    @FXML public Button btnImprimirMapa;
    @FXML public TableView<NotaFiscal> tblNotas;
    @FXML public Button btnSalvar;

    //COMPONENTES DA ABA NOTA
    @FXML public Tab tabNota;
    @FXML public TextField txtNumero;
    @FXML public TextField txtVolumeTotal;
    @FXML public TextField txtPesoTotal;
    @FXML public TextField txtProdutoItem;
    @FXML public TextField txtQtdCxItem;
    @FXML public TextField txtPesoItem;
    @FXML public TextField txtPrUnitItem;
    @FXML public TextField txtValorTotalItem;
    @FXML public Button btnAlterarItem;
    @FXML public Button btnSalvarItem;
    @FXML public Button btnCancelarItem;
    @FXML public TableView<NotaFiscalItem> tblItens;

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
    @FXML public Button btnValidar;
    @FXML public TableView<NotaFiscalItemLote> tblLotes;
    @FXML public CheckBox checkItensFaltantes;

    //ATRIBUTOS DIVERSOS
    public ObservableList<Departamento> listaDepartamento;
    public ObservableList<Usuario> listaConferente;
    public ObservableList<NotaFiscalItemLote> listaLotes;
    public ObservableList<NotaFiscalItemLote> listaLotesSintetico;
    public Produto produto;
    public Carga cargaForm = new Carga();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneMain.setRight(null);
        dtFiltro.setValue(LocalDate.now());
        dtFiltro.valueProperty().addListener((observable, oldValue, newValue)->{
            if(newValue != null){
                onAtualizaDados();
            }
        });

        //Habilita e Desabilidata botões de acordo com os campos obrigatorios.
        btnAlterar.disableProperty().bind(tblRecebimentos.getSelectionModel().selectedItemProperty().isNull());
        btnExcluir.disableProperty().bind(tblRecebimentos.getSelectionModel().selectedItemProperty().isNull());
        onAtualizaDados();
        onValidaProduto();
        onValidaQtdCx();
        onValidaFaltantes();
        onValidaFabricacao();
        setCbStatusFiltro();
    }

    public void setCbStatusFiltro(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        lista.add("Todos");
        lista.add("Agendada");
        lista.add("Em recebimento");
        lista.add("Recebida");
        cbStatusFiltro.setItems(lista);
        cbStatusFiltro.getSelectionModel().select(0);
    }

    /**
     * Atualiza listas (Notas/Lotes) em segundo plano.
     */
    private void onAtualizaListas(MsgBox msgBox) {
        Task<String> tarefa = new Task<String>() {
            @Override
            protected String call() throws Exception {
                Platform.runLater(() -> msgBox.setMensage("Atualizando listas!.."));
                for (int x = 0;x < tblRecebimentos.getItems().size();x++){
                    ObservableList<NotaFiscal> listaNota = new NotaFiscal().getLista(tblRecebimentos.getItems().get(x).getIdcarga());

                    ObservableList<NotaFiscalItemLote> listaLoteSintetica = new NotaFiscalItemLote().getListaSintetica(tblRecebimentos.getItems().get(x).getIdcarga());

//                    ObservableList<NotaFiscalItemLote> listaLote = new NotaFiscalItemLote().getLista(tblRecebimentos.getItems().get(x).getIdcarga());
                    ObservableList<NotaFiscalItemLote> listaLote = FXCollections.observableArrayList();

                    tblRecebimentos.getItems().get(x).setListaLote(listaLote);
                    tblRecebimentos.getItems().get(x).setListaNota(listaNota);
                    tblRecebimentos.getItems().get(x).setListaLoteSintetica(listaLoteSintetica);
                    tblRecebimentos.getItems().get(x).setVerifica(tblRecebimentos.getItems().get(x).getStatus());
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
        Thread t = new Thread(tarefa);
        t.start();
    }

    /**
     * Atualiza tabela em segundo plano.
     * Atualiza lista de Departamento e Conferente em segundo plano.
     */
    public void onAtualizaDados(){
        tblRecebimentos.setItems(null);
        MsgBox msgBox = new MsgBox(anchorPane,"Atualizando Dados!..");
        msgBox.setMsgProgress(true);
        Task<String> tarefa = new Task<String>() {
            @Override
            protected String call() throws Exception {
                FilteredList<Carga> filteredData = new FilteredList<>(new Carga().getLista(1,dtFiltro.getValue().toString()));
                txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(t -> {
                        if ((newValue == null || newValue.isEmpty())) {
                            return true;
                        }
                        String UpperCaseFilter = newValue.toUpperCase();
                        if (t.getDescricao().contains(UpperCaseFilter)) {
                            return true;
                        }
                        return false;
                    });
                });

                cbStatusFiltro.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
                    filteredData.setPredicate(a -> {
                        if (newValue.equals("Todos")) {
                            return true;
                        }
                        if (a.getStatus().equals(newValue)) {
                            return true;
                        }
                        return false;
                    });
                });

                SortedList<Carga> sortedData = new SortedList(filteredData);
                sortedData.comparatorProperty().bind(tblRecebimentos.comparatorProperty());
                tblRecebimentos.setItems(sortedData);
                listaDepartamento = new Departamento().getLista();
                listaConferente = new Usuario().getListaConferente();
                return null;
            }
            @Override
            protected void succeeded(){
                if(tblRecebimentos.getItems().size() >0){
                    onAtualizaListas(msgBox);
                }else{
                    msgBox.setMsgProgress(false);
                }
            }
        };
        Thread t = new Thread(tarefa);
        t.start();

    }

    /**
     * Abri formulario para criar nova carga de recebimento.
     */
    @FXML
    private void onBtnNovo(){
        btnNovo.setDisable(true);
        btnAlterar.disableProperty().unbind();
        btnAlterar.setDisable(true);
        new FormCarga(this).onReset();
        new FormNota(this).onReset();
        new FormConferencia(this).onReset();
        paneMain.setRight(paneCad);
    }
    /**
     * Abri formulario alteração da carga de recebimento.
     */
    @FXML
    private void onBtnAlterar(){
        cargaForm = tblRecebimentos.getSelectionModel().getSelectedItem();;
        if(cargaForm.getVerifica().equals("Agendada")) {
            btnNovo.setDisable(true);
            btnAlterar.disableProperty().unbind();
            btnAlterar.setDisable(true);
            tblRecebimentos.setDisable(true);
            paneMain.setRight(paneCad);
            new FormCarga(this).onStageAgendado(cargaForm);
            new FormNota(this).onStageAgendado();
            new FormConferencia(this).onStageAgendado();
        }else if(cargaForm.getVerifica().equals("Em recebimento")) {
            btnNovo.setDisable(true);
            btnAlterar.disableProperty().unbind();
            btnAlterar.setDisable(true);
            tblRecebimentos.setDisable(true);
            paneMain.setRight(paneCad);
            new FormCarga(this).onStageEmrecebimento(cargaForm);
            new FormNota(this).onStageEmrecebimento();
            new FormConferencia(this).onStageEmrecebimento(cargaForm);
        }else if(cargaForm.getVerifica().equals("Recebida")) {
            btnNovo.setDisable(true);
            btnAlterar.disableProperty().unbind();
            btnAlterar.setDisable(true);
            paneMain.setRight(paneCad);
            tblRecebimentos.setDisable(true);
            new FormCarga(this).onStageRecebido(cargaForm);
            new FormNota(this).onStageRecebido();
            new FormConferencia(this).onStageRecebido(cargaForm);
        }
    }

    @FXML
    private void onBtnExcluir(){
        Carga carga = tblRecebimentos.getSelectionModel().getSelectedItem();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("KARDEX");
        dialog.setHeaderText("Deseja realmente excluir a carga "+carga.getIdcarga()+"?");
        ButtonType btnNao = new ButtonType("Não");
        ButtonType btnSim = new ButtonType("Sim");
        dialog.getButtonTypes().setAll(btnSim,btnNao);
        dialog.showAndWait().ifPresent(b -> {
            if(b == btnSim){
                String msg = carga.delete();
                if(msg == null){
                    new MsgBox(anchorPane,"Excluido com suceeso!").setMsgSucces(true);
                    onAtualizaDados();
                }else{
                    new MsgBox(anchorPane,"Não foi possivel excluir carga!").setMsgError(true);
                    System.out.println("Error: "+msg);
                }
            }
        });
    }

    /**
     * Fecha formulario de recebimento.
     */
    @FXML
    private void onBtnFechar(){
        btnNovo.setDisable(false);
        btnAlterar.disableProperty().bind(tblRecebimentos.getSelectionModel().selectedItemProperty().isNull());
        paneMain.setRight(null);
        tblRecebimentos.setDisable(false);
    }
    /**
     * Chama função que salva dados do carregamento no banco de dados,
     *  e ajusta formulario de acordo com novo status.
     */
    @FXML
    private void onBtnSalvar() {
        MsgBox msgBox = new MsgBox(anchorPane,"");
        String msg = onSalvarCarga();
        if (msg == null) {
            if(txtIdCarga.getText().isEmpty()){
                new FormCarga(this).onReset();
                new FormNota(this).onReset();
                new FormConferencia(this).onReset();
            }
            onAtualizaDados();

            msgBox.setMsgSucces(true);
            msgBox.setMensage("Carga salva com sucesso!");
        } else {
            msgBox.setMsgError(true);
            msgBox.setMensage("Não foi possivel salvar carga!");
            System.out.println("Error: onSalvarCarga." + msg);
        }
    }

    /**
     * Salva dados do carregamento no banco de dados
     * @return Nulo se dados é salvo com sucesso, String com msg se houver alguma falha.
     */
    private String onSalvarCarga(){
        try{
            //SE IDCARGA É IGUAL A VAZIO CADASTRA NOVA CARGA, SE NÃO ALTERA CARGA ATUAL.
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
                carga.setStatus("Agendada");
                carga.setQtdpallet(0);
                carga.setTemperaturabau(0.0);
                carga.setPesoinicial(0.0);
                carga.setPesofinal(0.0);
                carga.setDtcarga(dtDescarga.getValue().format(DateTimeFormatter.ISO_DATE));
                carga.setDtmovimento(dtDescarga.getValue().format(DateTimeFormatter.ISO_DATE));
                carga.setDtinicio("");
                carga.setDtfim("");
                carga.setVerifica("Agendada");

                String msg = carga.insert();
                if (msg == null) {
                    cargaForm = carga;
                    return null;
                } else {
                    return msg;
                }

            }else{
                Carga carga = tblRecebimentos.getSelectionModel().getSelectedItem();
                carga.setDescricao(txtDescricao.getText());
                carga.setTransportadora(txtTransportadora.getText());
                carga.setMotorista(txtMotorista.getText());
                carga.setPlaca(txtPlaca.getText());
                if(!cbConferente.getSelectionModel().isEmpty())carga.setIdconferente(listaConferente.get(cbConferente.getSelectionModel().getSelectedIndex()).getIdusuario());
                carga.setIddepartamento(listaDepartamento.get(cbDepartamento.getSelectionModel().getSelectedIndex()).getIddepartamento());
                if(!txtDoca.getText().isEmpty())carga.setDoca(Integer.parseInt(txtDoca.getText()));
                if(!txtLacre.getText().isEmpty())carga.setNum_lacre(txtLacre.getText());
                if(!txtQtdPallet.getText().isEmpty())carga.setQtdpallet(Integer.parseInt(txtQtdPallet.getText()));
                if(!txtTempBau.getText().isEmpty())carga.setTemperaturabau(Double.parseDouble(txtTempBau.getText()));
                if(!txtPesoIni.getText().isEmpty())carga.setPesoinicial(Double.parseDouble(txtPesoIni.getText()));
                if(!txtPesoFim.getText().isEmpty())carga.setPesofinal(Double.parseDouble(txtPesoFim.getText()));
                carga.setDtcarga(dtDescarga.getValue().format(DateTimeFormatter.ISO_DATE));
                carga.setDtmovimento(dtDescarga.getValue().format(DateTimeFormatter.ISO_DATE));
                String msg = carga.update();

                if (msg == null) {
                    cargaForm = carga;
                    return null;
                } else {
                    return msg;
                }
            }
        }catch (Exception ex){
            System.out.println("Exception: onSalvarCarga."+ex.toString());
            return "Error ao salvar carga. [Exception.onSalvar]";
        }
    }

    /**
     * Importar notas fiscais.
     */
    @FXML
    private void onBtnImportarXml(){
        MsgBox msgBox = new MsgBox(anchorPane,"");
        try {
            File arquivo = onSelecionarXml();
            if(arquivo == null) return;

            NotaFiscal notaFiscal = onLerXml(arquivo);

            if(notaFiscal == null){
                msgBox.setMensage("Não foi possivel realizar importação!");
                msgBox.setMsgError(true);
                return;
            }

            //VINCULAR CODIGO DO PRODUTO COM CODIGO DE FABRICA
            ObservableList<NotaFiscalItem> newListaItem = FXCollections.observableArrayList();
            for(NotaFiscalItem nfi : notaFiscal.getListaItem()){
                ProdutoFator produtoFator = new ProdutoFator().get(nfi.getCodfabrica(),notaFiscal.getCnpj_cpf_emitente());
                Produto produto = new Produto();

                if(produtoFator != null){
                    produto = new Produto().get(produtoFator.getIdproduto());
                    nfi.setIdproduto(produtoFator.getIdproduto());
                    nfi.setDescricao(produto.getDescricao());
                    nfi.setUnidade(produto.getUnidade());

                    Double qtdPrd = nfi.getQtdprod() * produtoFator.getFator();
                    Double qtd = qtdPrd / produto.getQtdundcx();
                    Double peso = qtdPrd * produto.getPesoun();
                    BigDecimal pesoArred = new BigDecimal(peso).setScale(3, RoundingMode.HALF_UP);

                    nfi.setQtdprod(qtdPrd);
                    nfi.setQtdcx(qtd.intValue());
                    nfi.setPeso(pesoArred.doubleValue());

                    newListaItem.add(nfi);
                }else{
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("KARDEX - Input Dialog");
                    dialog.setHeaderText("Vincular produto, Codigo de fabrica: "+nfi.getCodfabrica());
                    dialog.setContentText("Codigo produto:");
                    Optional<String> codProd = dialog.showAndWait();
                    if(codProd.isPresent()){
                        dialog.setTitle("KARDEX - Input Dialog");
                        dialog.setHeaderText("Vincular produto, Codigo de fabrica: "+nfi.getCodfabrica());
                        dialog.setContentText("Fator de conversão:");
                        Optional<String> fator = dialog.showAndWait();

                        if(fator.isPresent()){
                            produtoFator = new ProdutoFator();
                            produtoFator.setIdproduto(Integer.parseInt(codProd.get()));
                            produtoFator.setCnpjfornecedor(notaFiscal.getCnpj_cpf_emitente());
                            produtoFator.setFator(Double.parseDouble(fator.get()));
                            produtoFator.setCodfabrica(nfi.getCodfabrica());
                            produtoFator.insert();

                            produto = new Produto().get(produtoFator.getIdproduto());
                            nfi.setIdproduto(produtoFator.getIdproduto());
                            nfi.setDescricao(produto.getDescricao());
                            nfi.setUnidade(produto.getUnidade());

                            Double qtdPrd = nfi.getQtdprod() * produtoFator.getFator();
                            Double qtd = qtdPrd / produto.getQtdundcx();
                            Double peso = qtdPrd * produto.getPesoun();
                            BigDecimal valor = new BigDecimal(peso).setScale(3, RoundingMode.HALF_UP);

                            nfi.setQtdprod(qtdPrd);
                            nfi.setQtdcx(qtd.intValue());
                            nfi.setPeso(valor.doubleValue());

                            newListaItem.add(nfi);
                        }else{
                            msgBox.setMensage("Importação cancelada!");
                            msgBox.setMsgError(true);
                            return;
                        }
                    }else{
                        msgBox.setMensage("Importação cancelada!");
                        msgBox.setMsgError(true);
                        return;
                    }
                }
            }
            notaFiscal.setListaItem(newListaItem);

            setTblNotas(notaFiscal);
            onSalvarCarga();
            int iddep = listaDepartamento.get(cbDepartamento.getSelectionModel().getSelectedIndex()).getIddepartamento();
            notaFiscal.setIddepartamento(iddep);
            notaFiscal.setIdcarga(Integer.parseInt(txtIdCarga.getText()));
            notaFiscal.insert();
            int idnota = notaFiscal.getIdnota();

            for(NotaFiscalItem item : notaFiscal.getListaItem()){
                item.setIdnota(idnota);
                item.insert();
            }
            onAtualizaDados();
            msgBox.setMensage("Arquivo importado com sucesso!");
            msgBox.setMsgSucces(true);
        }catch(Exception ex){
            msgBox.setMensage("Falha ao importar arquivo!");
            msgBox.setMsgError(true);
            System.out.println("Erro onImportarXml: "+ex.toString());
        }
    }

    /**
     * Abri janela para selecionar arquivo.
     * @return Arquivo xml selecionado.
     * @throws Exception
     */
    private File onSelecionarXml() throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar arquivo xml");
        File arquivoXml = fileChooser.showOpenDialog(new Stage());
        return arquivoXml;
    }

    /**
     * Efetua leitura do xml informado.
     * @param arquivoXml
     * @return Retorna NotaFiscal
     * @throws Exception
     */
    private NotaFiscal onLerXml(File arquivoXml) throws Exception{
        NotaFiscal notaFiscal = new NotaFiscal();
        if (arquivoXml != null) {
            Nfe xml = new Nfe();
            if (xml.LerXml(arquivoXml)) {
                notaFiscal = xml.getNotaFiscal();
                if(notaFiscal.getTipo() == 1) {
                    return notaFiscal;
                }
                return null;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * Chama função que faz a impressão do relatorio,
     * e ajusta status da carga.
     */
    @FXML
    private void onBtnImprimirMapa(){
        Carga carga = tblRecebimentos.getSelectionModel().getSelectedItem();
        if(carga.getStatus().equals("Agendada")){
            tblRecebimentos.getSelectionModel().getSelectedItem().setStatus("Em recebimento");
            tblRecebimentos.getSelectionModel().getSelectedItem().setVerifica("Em recebimento");
            tblRecebimentos.getSelectionModel().getSelectedItem().setDtinicio("GETDATE()");
            String msg = tblRecebimentos.getSelectionModel().getSelectedItem().update();
            cargaForm = tblRecebimentos.getSelectionModel().getSelectedItem();
            if(msg == null){
                new FormCarga(this).onStageEmrecebimento(carga);
                new FormNota(this).onStageEmrecebimento();
                new FormConferencia(this).onStageEmrecebimento(carga);
            }
        }
        onImprimir();
    }

    /**
     * Gera relatorio e exibe na tela.
     */
    private void onImprimir() {
        try {
            //PAGA CAMINHO DO RELATÓRIO
            String local = new File(".").getCanonicalPath();
            String relatorio = local + "\\Relatorios\\Mapa descarga.jrxml";

            ObservableList<NotaFiscalItem> lista = new NotaFiscalItem().getListaSintetica(cargaForm.getIdcarga());
            Map parameters = new HashMap();
            parameters.put("data", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            parameters.put("placa", cargaForm.getPlaca());
            parameters.put("motorista", cargaForm.getMotorista());
            parameters.put("carga", cargaForm.getDescricao());
            JasperReport report = JasperCompileManager.compileReport(relatorio);
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(lista, false));

            JasperViewer view = new JasperViewer(print, false);
            view.setTitle("Logística - Mapa de Descarga");
            view.setVisible(true);
        }catch (Exception ex){
            System.out.println("Error: "+ex.toString());
        }
    }

    private void onImprimirRec(){
        try {
            //PEGA CAMINHO DO RELATÓRIO
            String local = new File(".").getCanonicalPath();
            String relatorio = local + "\\Relatorios\\Mapa descarga - Re.jrxml";

            ObservableList<NotaFiscalItemLote> lista = new NotaFiscalItemLote().getListaSinteticaDivergencia(cargaForm.getIdcarga());

            Map parameters = new HashMap();
            parameters.put("data", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            parameters.put("placa", cargaForm.getPlaca());
            parameters.put("motorista", cargaForm.getMotorista());
            parameters.put("carga", cargaForm.getDescricao());
            JasperReport report = JasperCompileManager.compileReport(relatorio);
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(lista, false));

            JasperViewer view = new JasperViewer(print, false);
            view.setTitle("Logística - Mapa de Descarga");
            view.setVisible(true);
        }catch (Exception ex){
            System.out.println("Error: "+ex.toString());
        }
    }

    /**
     * Habilita formulario para alteração do item da nota.
     */
    @FXML
    private void onBtnAlterarItem(){
        NotaFiscalItem item = tblItens.getSelectionModel().getSelectedItem();
        btnAlterarItem.setVisible(false);
        btnSalvarItem.setVisible(true);
        btnCancelarItem.setVisible(true);
        txtProdutoItem.setText(Integer.toString(item.getIdproduto()));
        txtQtdCxItem.setText(Integer.toString(item.getQtdcx()));
        txtPesoItem.setText(Double.toString(item.getPeso()));
        txtPrUnitItem.setText(String.format("%.2f",item.getPrunit()));
        txtValorTotalItem.setText(String.format("%.2f",item.getValorliq()));
        tblItens.setDisable(true);
        txtQtdCxItem.requestFocus();
    }

    /**
     * Salva no banco de dados alterações do item da nota.
     */
    @FXML
    private void onBtnSalvarItem(){
        if(tblItens.getSelectionModel().getSelectedItem().getPesovariavel()){
            tblItens.getSelectionModel().getSelectedItem().setQtdprod(Double.parseDouble(txtPesoItem.getText()));
        }else{
            Double qtd = Double.parseDouble(txtPesoItem.getText()) * tblItens.getSelectionModel().getSelectedItem().getQtdundcx();
            tblItens.getSelectionModel().getSelectedItem().setQtdprod(qtd);
        }
        tblItens.getSelectionModel().getSelectedItem().setQtdcx(Integer.parseInt(txtQtdCxItem.getText()));
        tblItens.getSelectionModel().getSelectedItem().setPeso(Double.parseDouble(txtPesoItem.getText()));
        String msg = tblItens.getSelectionModel().getSelectedItem().update();
        if(msg == null){
            new MsgBox(anchorPane,"Item alterado com sucesso!").setMsgSucces(true);
        }else{
            new MsgBox(anchorPane,msg).setMsgError(true);
        }
        tblNotas.getSelectionModel().getSelectedItem().setVolumetotal(0);
        txtVolumeTotal.setText(Integer.toString(tblNotas.getSelectionModel().getSelectedItem().getVolumetotal()));
        onBtnCancelarrItem();
    }

    /**
     * Desabilita formulatio para alteração do item da nota.
     */
    @FXML
    private void onBtnCancelarrItem(){
        btnAlterarItem.setVisible(true);
        btnSalvarItem.setVisible(false);
        btnCancelarItem.setVisible(false);
        txtProdutoItem.setText("");
        txtQtdCxItem.setText("");
        txtPesoItem.setText("");
        txtPrUnitItem.setText("");
        txtValorTotalItem.setText("");
        tblItens.setDisable(false);
    }

    /**
     * Inclui um novo lote do produto na carga,
     *  especificando as divergencias quando houver.
     */
    @FXML
    private void onBtnIncluirLote(){
        onSalvarCarga();
        onAtualizaDados();
        int idproduto = Integer.parseInt(txtProdutoLote.getText());
        int qtdDig = Integer.parseInt(txtQtdCxLote.getText());
        int idnota = 0;
        String msg = new NotaFiscalItemLote().deleteFaltas(cargaForm.getIdcarga(),idproduto);
        if(msg == null) {
            for (NotaFiscal nota : tblNotas.getItems()) {
                for (NotaFiscalItem item : new NotaFiscalItem().getLista(nota.getIdnota())) {
                    if (item.getIdproduto() == idproduto) {
                        int qtdProd = new NotaFiscalItemLote().getSumQtdCx(cargaForm.getIdcarga(),idproduto,nota.getIdnota());
                        qtdProd = item.getQtdcx() - qtdProd;
                        if (qtdDig == qtdProd) {
                            onIncluirLote(nota.getIdnota(), qtdDig, false, false);
                            qtdDig = qtdDig - qtdProd;
                            idnota = 0;
                            break;
                        }
                        //qtdDig MENOR qtdProd
                        else if (qtdDig <qtdProd && qtdDig > 0){
                            onIncluirLote(nota.getIdnota(), qtdDig, false, false);
                            qtdDig = qtdDig - qtdProd;
                            idnota = nota.getIdnota();
                        }
                        //qtdDig MAIOR qtdProd
                        else if (qtdDig > qtdProd && qtdProd > 0){
                            onIncluirLote(nota.getIdnota(), qtdProd, false, false);
                            qtdDig = qtdDig - qtdProd;
                            idnota = nota.getIdnota();
                        }
                        else if(qtdDig == 0){
                            qtdDig = qtdDig - qtdProd;
                            idnota = nota.getIdnota();
                        }else{
                            idnota = nota.getIdnota();
                        }
                    }
                }
            }
            if (qtdDig > 0.0 && idnota != 0) {
                onIncluirLote(idnota, qtdDig, true, false);
            }
            if (qtdDig < 0.0 && idnota != 0) {
                onIncluirLote(idnota, qtdDig*-1, false, true);
            }
            txtProdutoLote.setText("");
            txtQtdCxLote.setText("");
            txtQtdProdLote.setText("");
            txtNumlote.setText("");
            dtFabLote.setValue(null);
            dtVencLote.setValue(null);
            checkAvariado.setSelected(false);
            txtObsLote.setText("");
            txtProdutoLote.requestFocus();
        }
    }

    private void onIncluirLote(int idnota,int qtdCx, Boolean sobra, Boolean falta){
        NotaFiscalItemLote lote = new NotaFiscalItemLote();
        Double pesoMed;
        pesoMed = Double.parseDouble(txtQtdProdLote.getText().replaceAll(",", ".")) / Integer.parseInt(txtQtdCxLote.getText());
        lote.setDescricao(produto.getDescricao());
        lote.setUnidade(produto.getUnidade());
        lote.setCodfabrica(produto.getCodfabrica());
        lote.setIdcarga(Integer.parseInt(txtIdCarga.getText()));
        lote.setIdnota(idnota);
        lote.setIdproduto(Integer.parseInt(txtProdutoLote.getText()));
        lote.setNum_lote(txtNumlote.getText());
        lote.setDtfabricacao(dtFabLote.getValue().toString());
        lote.setDtvencimento(dtVencLote.getValue().toString());
        if(produto.getPeso_variavel()){
            lote.setQtdprod(qtdCx*pesoMed);
        }else{
            Double qtdProd = qtdCx * produto.getQtdundcx();
            lote.setQtdprod(qtdProd);
        }
        lote.setQtdcx(qtdCx);
        if (txtQtdProdLote.getText().equals("0.0")) {
            lote.setPeso(qtdCx*produto.getPesocx());
        }else{
            lote.setPeso(qtdCx*pesoMed);
        }
        lote.setFalta(falta);
        lote.setSobra(sobra);
        lote.setAvariado(checkAvariado.isSelected());
        lote.setObs(txtObsLote.getText());
        lote.setTipo(1);

        String msg = lote.insert();
        MsgBox msgBox = new MsgBox(anchorPane,"");
        if(msg == null){
            checkItensFaltantes.setSelected(false);
            ObservableList<NotaFiscalItemLote> lista = tblLotes.getItems();
            if(lista == null){
                lista = FXCollections.observableArrayList();
            }
            lista.add(lote);
            tblLotes.setItems(lista);
            listaLotes = lista;
            msgBox.setMensage("Lote incluido com sucesso!");
            msgBox.setMsgSucces(true);
        }else{
            msgBox.setMensage(msg);
            msgBox.setMsgError(true);
        }
    }

    /**
     * Exclui todos os lotes do produto especificado da carga.
     */
    @FXML
    private void onBtnExcluirLote(){
        MsgBox msgBox = new MsgBox(anchorPane,"");
        NotaFiscalItemLote lote = tblLotes.getSelectionModel().getSelectedItem();
        String msg = lote.delete();
        if (msg == null){
            onRemoveLote(lote.getIdproduto());
            msgBox.setMsgSucces(true);
            msgBox.setMensage("Lote excluido com sucesso!");
        }else{
            msgBox.setMsgError(true);
            msgBox.setMensage(msg);
        }
    }

    /**
     * Verifica se existe faltas ou sobras na carga.
     */
    @FXML
    private void onBtnValidarLotes(){
        Boolean divergencia = false;

        //VERIFICA SE TODOS OS ITENS FORAM DIGITADOS NA CONFERENCIA
        for (NotaFiscal nota : tblNotas.getItems()){
            for (NotaFiscalItem item : nota.getListaItem()){
                Boolean conferido = false;
                for(NotaFiscalItemLote lote : listaLotes){
                    if(lote.getIdproduto() == item.getIdproduto()){
                        conferido = true;
                    }
                }
                if (!conferido){
                    divergencia = true;
                    MsgBox msgBox = new MsgBox(anchorPane,"Existem produtos que não foram conferidos!");
                    msgBox.setMsgError(true);
                    return;
                }
            }
        }

        //VERIFICA SE TEVE ITEM COM SOBRA OU FALTA
        for(NotaFiscalItemLote lote : listaLotes){
            if(lote.getStatus().equals("Sobra") || lote.getStatus().equals("Falta")){
                divergencia = true;
            }
        }

        if (divergencia && cargaForm.getDtreconferencia() == null){
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setTitle("KARDEX");
            dialog.setHeaderText("Deseja re-imprimir mapa?");
            dialog.setContentText("Existem divergencia na conferencia, será necessario conferir novamente.");
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não",ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getButtonTypes().setAll(btnSim, btnNao);
            dialog.showAndWait().ifPresent(b -> {
                if(b == btnSim){
                    onImprimirRec();
                    cargaForm.updateReconferencia();
//                    onExcluirLotesDivergentes();
                }
            });
        }else if (divergencia && cargaForm.getDtreconferencia() != null){
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setTitle("KARDEX");
            dialog.setHeaderText("Deseja reconferir?");
            dialog.setContentText("Ainda existem divergencia na conferencia, caso não seja reconferido a carga será encerrada com as divergencias.");
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não",ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getButtonTypes().setAll(btnSim, btnNao);
            dialog.showAndWait().ifPresent(b -> {
                if(b == btnSim){
                    onImprimirRec();
                    System.out.println("Mapa impresso!");
                }else{
                    dialog.setTitle("KARDEX");
                    dialog.setHeaderText("Deseja encerrar a carga?");
                    dialog.setContentText("A carga possui divergencias, dejesa encerrar mesmo assim?");
                    dialog.showAndWait().ifPresent(c -> {
                        if(c == btnSim){
                            String msg = cargaForm.encerrar();
                            if(msg == null){
                                tblRecebimentos.getSelectionModel().getSelectedItem().setStatus("Recebida");
                                tblRecebimentos.getSelectionModel().getSelectedItem().setVerifica("Recebida");
                                new FormCarga(this).onStageRecebido(cargaForm);
                                new FormNota(this).onStageRecebido();
                                btnIncluirLote.disableProperty().unbind();
                                btnIncluirLote.setDisable(true);
                                btnExcluirLote.disableProperty().unbind();
                                btnExcluirLote.setDisable(true);
                                btnValidar.disableProperty().unbind();
                                btnValidar.setDisable(true);
                                new MsgBox(anchorPane,"Carga encerrada com sucesso!").setMsgSucces(true);
                            }else{
                                new MsgBox(anchorPane,msg).setMsgError(true);
                            }
                        }else{
                            new MsgBox(anchorPane,"Encerramento cancelado!").setMsgError(true);
                        }
                    });
                }
            });
        }else{
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setTitle("KARDEX");
            dialog.setHeaderText("Deseja encerrar a carga?");
            dialog.setContentText("Nenhuma divergencia foi encontrada!");
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não",ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getButtonTypes().setAll(btnSim, btnNao);
            dialog.showAndWait().ifPresent(c -> {
                if(c == btnSim){
                    String msg = cargaForm.encerrar();
                    if(msg == null){
                        tblRecebimentos.getSelectionModel().getSelectedItem().setStatus("Recebida");
                        tblRecebimentos.getSelectionModel().getSelectedItem().setVerifica("Recebida");
                        new FormCarga(this).onStageRecebido(cargaForm);
                        new FormNota(this).onStageRecebido();
                        btnIncluirLote.disableProperty().unbind();
                        btnIncluirLote.setDisable(true);
                        btnExcluirLote.disableProperty().unbind();
                        btnExcluirLote.setDisable(true);
                        btnValidar.disableProperty().unbind();
                        btnValidar.setDisable(true);
                        new MsgBox(anchorPane,"Carga encerrada com sucesso!").setMsgSucces(true);
                    }else{
                        new MsgBox(anchorPane,msg).setMsgError(true);
                    }
                }
            });
        }

    }

    private void onValidaFaltantes(){
        checkItensFaltantes.selectedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue){
                onItensRestantes();
            }else{
                tblLotes.setItems(listaLotes);
            }
        });
    }

    private void onItensRestantes(){
        ObservableList<NotaFiscalItemLote> listaRestantes = FXCollections.observableArrayList();

        for (NotaFiscal nota : tblNotas.getItems()){
            for (NotaFiscalItem item : nota.getListaItem()){
                Boolean conferido = false;
                for(NotaFiscalItemLote lote : listaLotes){
                    if(lote.getIdproduto() == item.getIdproduto()){
                        conferido = true;
                    }
                }
                if (!conferido){
                    NotaFiscalItemLote newLote = new NotaFiscalItemLote();
                    newLote.setIdproduto(item.getIdproduto());
                    newLote.setDescricao(item.getDescricao());
                    newLote.setUnidade(item.getUnidade());
                    newLote.setCodfabrica(item.getCodfabrica());
                    newLote.setFalta(true);
                    newLote.setQtdcx(item.getQtdcx());
                    newLote.setPeso(item.getPeso());
                    newLote.setSobra(false);
                    listaRestantes.add(newLote);
                }
            }
        }
        tblLotes.setItems(listaRestantes);
    }

    /**
     * Atualiza
     * @param notaFiscal
     */
    private void setTblNotas(NotaFiscal notaFiscal){
        ObservableList<NotaFiscal> listaNota = FXCollections.observableArrayList();
        if(tblNotas.getItems() == null){
            notaFiscal.setVolumetotal(0);
            listaNota.add(notaFiscal);
        }else{
            notaFiscal.setVolumetotal(0);
            listaNota = tblNotas.getItems();
            listaNota.add(notaFiscal);
        }
        tblNotas.setItems(listaNota);
    }

    private void onRemoveLote(int idproduto){
        listaLotes = FXCollections.observableArrayList();
        for (NotaFiscalItemLote newLote : tblLotes.getItems()){
            if (newLote.getIdproduto() != idproduto){
                listaLotes.add(newLote);
            }
        }
        if(listaLotes.size() > 0) {
            tblLotes.setItems(listaLotes);
        }else{
            tblLotes.setItems(null);
        }
        listaLotesSintetico = new NotaFiscalItemLote().getListaSintetica(cargaForm.getIdcarga());
    }

    private void onExcluirLotesDivergentes(){
        ObservableList<NotaFiscalItemLote> lista = FXCollections.observableArrayList();
        for(NotaFiscalItemLote lote : listaLotes){
            if(lote.getStatus().equals("Sobra") || lote.getStatus().equals("Falta")){
                String msg = lote.delete();
                onRemoveLote(lote.getIdproduto());
            }
        }
        tblLotes.setItems(lista);
    }

    private void onValidaFabricacao(){
        dtFabLote.valueProperty().addListener((observable, oldValue, newValue)->{
            if(newValue != null){
                LocalDate venc = newValue.plusDays(produto.getShelf());
                dtVencLote.setValue(venc);
            }
        });
    }

    private void onValidaQtdCx(){
        txtQtdCxLote.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue && !txtQtdCxLote.getText().isEmpty()){
                Double valor = produto.getPesocx() * Integer.parseInt(txtQtdCxLote.getText());
                txtQtdProdLote.setText(Double.toString(valor));
            }else{
                txtQtdProdLote.setText("");
            }
        });
    }

    private void onValidaProduto(){
        txtProdutoLote.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue && !txtProdutoLote.getText().isEmpty()){
                Boolean exist = false;
                for(NotaFiscal nota : tblNotas.getItems()){
                    for(NotaFiscalItem item : nota.getListaItem()){
                        if(item.getIdproduto() == Integer.parseInt(txtProdutoLote.getText())){
                            exist = true;
                            produto = new Produto().get(item.getIdproduto());
                            if(produto.getPeso_variavel()){
                                txtQtdProdLote.setEditable(true);
                            }else{
                                txtQtdProdLote.setEditable(false);
                            }
                            break;
                        }
                    }
                }
                if(!exist){
                    MsgBox msgBox = new MsgBox(anchorPane,"Produto invalido");
                    msgBox.setMsgError(true);
                    txtProdutoLote.setText("");
                }
            }
        });
    }


}
