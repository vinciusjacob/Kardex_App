package br.com.app.kardex.controller.logistica;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.stage.FileChooser.ExtensionFilter;
import br.com.app.kardex.model.logistica.Carregamento;
import br.com.app.kardex.model.logistica.Estoque;
import br.com.app.kardex.model.logistica.Importacao;
import br.com.app.kardex.model.logistica.PlacaViagem;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Double.parseDouble;

/**
 * FXML Controller class
 *
 * @author Vinicius
 */
public class fx_carregamentoController implements Initializable {

    @FXML private BorderPane pane;
    @FXML private GridPane paneImportar;
    @FXML private GridPane paneImprimir;
    @FXML private ProgressIndicator progress;
    @FXML private Label progressText;
    @FXML private ComboBox cbTxt;
    @FXML private ComboBox cbOrder;
    @FXML private ComboBox cbTipo;
    @FXML private TextField txtArquivo;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtViagem;
    @FXML private Label lbTitulo;
    @FXML private Button btnImportar;
    @FXML private Button btnCancelar;
    @FXML private TableView<Carregamento> tabela;
    @FXML private TableColumn colPlaca;
    @FXML private TableColumn colNumero;
    @FXML private TableColumn colData;
    @FXML private TableColumn colCodProd;
    @FXML private TableColumn colDescrProd;
    @FXML private TableColumn colQtd;
    @FXML private TableColumn colFab;
    @FXML private TableColumn colReent;
    @FXML private TableColumn colStatus;
    @FXML private TableColumn colNota;
    @FXML private Button btnSeleciona;
    @FXML private DatePicker dpData;
    @FXML private TextField pesquisar;
    @FXML private HBox ImpHbosError;
    @FXML private Label ImpLabelError;
    @FXML private CheckBox chbData;
    @FXML private Label lbParametro;
    @FXML private TextField txtParametro;
    @FXML private TextField txtPlaca;
    @FXML private TableView<PlacaViagem> tabelaImp;
    @FXML private TableColumn colPlacaImp;
    @FXML private CheckBox cbImp;
    private Carregamento carregamento = new Carregamento();
    private File arquivo;

    @FXML
    private void btImprimir(){
        try{
            Imprimir(false);
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error!");
            dialogoInfo.setHeaderText("");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
        }
    }
    @FXML
    private void btVisualizar() throws JRException, ParseException{
        try{
            Imprimir(true);
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error!");
            dialogoInfo.setHeaderText("");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
        }
    }
    private void cbSelectionImpDt(){
        chbData.selectedProperty().addListener((ChangeListener) (observable, oldValue, newValue) -> {
            if((Boolean)newValue){
                lbParametro.setVisible(true);
                txtParametro.setVisible(true);
                PlacaViagem pv = new PlacaViagem();
                if(!txtViagem.getText().isEmpty() && pv.getLista(txtViagem.getText()) != null){
                    colPlacaImp.setCellValueFactory(new PropertyValueFactory<>("placa"));
                    tabelaImp.setItems(pv.getLista(txtViagem.getText()));
                    tabelaImp.setVisible(true);
                }else{
                    tabelaImp.setVisible(false);
                }
            }else{
                lbParametro.setVisible(false);
                txtParametro.setVisible(false);
                tabelaImp.setVisible(false);
            }
        });
        cbImp.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Integer row = tabelaImp.getItems().size();
                for(int i = 0;i < row;i++){
                    tabelaImp.getItems().get(i).setPlaca(newValue);
                }
            }
        });
    }
    private void Imprimir(Boolean visualizar) throws ParseException, JRException, IOException{

        if(txtViagem.getText().equals("")){
            ImpHbosError.setVisible(true);
            ImpLabelError.setText("Informe uma viagem para impressão!");
            txtViagem.requestFocus();
        }else if(chbData.isSelected() && txtParametro.getText().isEmpty()){
            ImpHbosError.setVisible(true);
            ImpLabelError.setText("Informe um Paramentro para impressão!");
            txtParametro.requestFocus();
        }else{

            String local = new File(".").getCanonicalPath();
            String order = "";
            String viagem = txtViagem.getText();
            Double param = parseDouble(txtParametro.getText().replaceAll(",", "\\."));
            String relatorio = local+"\\Relatorios\\Mapa.jrxml";
            if(!chbData.isSelected()){
                relatorio = local+"\\Relatorios\\MapaDt.jrxml";
                param = null;
            }

            relatorio = local+"\\Relatorios\\Novo_Mapa.jrxml";


            if(cbOrder.getSelectionModel().getSelectedIndex() == 0){
                order = "CAR_CODPROD";
            }else{
                order = "CAR_QTD";
            }
            Integer row = tabelaImp.getItems().size();
            ArrayList placas = new ArrayList();
            if(tabelaImp.isVisible()){
                for(int i =0;i < row;i++){
                    if(tabelaImp.getItems().get(i).isPlaca())
                        placas.add(tabelaImp.getItems().get(i).getString());
                }
            }else{
                placas = null;
            }
            if(null == carregamento.getLista(viagem, order,param,placas)){
                ImpHbosError.setVisible(true);
                ImpLabelError.setText("Nenhuma informação encontrada! Verifique a viagem infomarda.");
            }else{
                ImpHbosError.setVisible(false);
                ObservableList<Carregamento> lista = carregamento.getLista(viagem, order,param,placas);
                Integer qtdCorte = carregamento.getQtdCorte(viagem);
                Integer volume = getQtdTotal(lista);
                Integer totalCarga = qtdCorte + volume;
                String placa = txtPlaca.getText();
                Date data= new Date();
                Map parameters = new HashMap();
                parameters.put("total", volume.toString());
                parameters.put("data", getDataFormat(data));
                parameters.put("corte", qtdCorte.toString());
                parameters.put("totalcarga", totalCarga.toString());
                parameters.put("placa", placa);
                JasperReport report = JasperCompileManager.compileReport(relatorio);
                JasperPrint print = JasperFillManager.fillReport(report, parameters,new JRBeanCollectionDataSource(lista,false));
                if(visualizar){
                    JasperViewer view = new JasperViewer(print,false);
                    view.setTitle("Logística - Mapa de Separação");
                    view.setVisible(true);
                }else{
                    JasperPrintManager.printPage(print, 0, true);
                }
                //JasperExportManager.exportReportToPdfFile(print, "C:/Users/Vinicius/Desktop/RelatorioCarregamento.pdf");
            }
        }
    }
    private String getDataFormat(Date data) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("EEEEEE ', ' dd 'de' MMMM 'de' yyyy");
        String dt = sdf.format(data);
        return dt;
    }
    private Integer getQtdTotal(List<Carregamento> lista){
        Integer total = 0;
        for(Carregamento car:lista){
            total = total+car.getQtd();
        }
        return total;
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
                String titulo = "PLACA;GARREGAMENTO;DATA;PRODUTO;DESCRICAO;QTD;FABRICACAO;REENT/DIST;STATUS;NOTA \n";
                writer.write(titulo);
                for(int i=0;i<rows;i++){
                    String text = ""
                            +tabela.getItems().get(i).getPlaca()+";"
                            +tabela.getItems().get(i).getNumero()+ ";"
                            +tabela.getItems().get(i).getData()+ ";"
                            +tabela.getItems().get(i).getProduto()+ ";"
                            +tabela.getItems().get(i).getDescricao()+ ";"
                            +tabela.getItems().get(i).getQtd()+ ";"
                            +tabela.getItems().get(i).getFabricacao()+ ";"
                            +tabela.getItems().get(i).getReentrega()+ ";"
                            +tabela.getItems().get(i).getStatus()+ ";"
                            +tabela.getItems().get(i).getNota()+ "\n";
                    writer.write(text);
                }
                writer.flush();
                writer.close();
            }catch(Exception ex){

            }
        }
    }
    @FXML
    private void pImprimir(){
        pane.setRight(paneImprimir);
    }
    @FXML
    private void pImportar(){
        pane.setRight(paneImportar);
    }

    @FXML
    private void Cancelar(){
        progress.setVisible(false);
        progressText.setText("");
        txtArquivo.setText("");
        lbTitulo.setVisible(false);
        txtTitulo.setVisible(false);
        txtTitulo.setText("");
        cbTxt.getSelectionModel().clearSelection();
        pane.setRight(null);
        ImpHbosError.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbSelection();
        pane.setRight(null);
        dpData.setValue(LocalDate.now());
        setCbTxt();
        setCbTipo();
        setTable();
        cbSelectionData();
        setCbOrder();
        cbSelectionImpDt();
    }

    @FXML
    private void selectFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o arquivo de texto");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Arquivo de texto", "*.txt","*.TXT","*.csv"));
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
    private void btImportar(){
        importStatus(true,"Importando...");
        Task importar = new Task(){
            @Override
            protected String call() throws Exception {
                return Importar();
            }
            @Override
            protected void succeeded() {
                String msg = getValue().toString();
                setTable();
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

    private String Importar() throws Exception{
        String msg = "";
        if(arquivo == null){
            msg = ("Selecione um arquivo de texto.");
        }else if(cbTxt.getSelectionModel().isEmpty()){
            msg = ("Selecione um Modelo de TXT!");
        }else{
            String cb = cbTxt.getSelectionModel().getSelectedItem().toString();
            if(cb.equals("Estoque Temporario")){
                Estoque est = new Estoque();
//                est.Importar(arquivo);
                msg = ("Estoque temporario importado com sucesso!");
            }else if(cb.equals("Pré-Carregamento")){
                Importacao imp = new Importacao();
                if(txtTitulo.getText().equals("")){
                    msg = ("Defina um titulo!");
                }else if(imp.isTxtRep(txtTitulo.getText())){
                    msg = ("Titulo já existe!");
                }else{
                    String titulo = txtTitulo.getText();
                    String data = getDataAtual();
                    Carregamento car = new Carregamento();
                    car.Importar(arquivo, titulo, data);
                    msg = ("Pré-carregamento importado com sucesso!");
                }
            }
        }
        return msg;
    }
    private void setTable(){
        String data = dpData.getValue().format(DateTimeFormatter.ISO_DATE);
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colCodProd.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colDescrProd.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        colFab.setCellValueFactory(new PropertyValueFactory<>("fabricacao"));
        colReent.setCellValueFactory(new PropertyValueFactory<>("reentrega"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        FilteredList<Carregamento> filteredData = new FilteredList<>(carregamento.getLista(data));

        pesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(car->{

                String tipo = cbTipo.getSelectionModel().getSelectedItem().toString();
                if ((newValue == null || newValue.isEmpty())&& tipo.equals("Todos") ) {
                    return true;
                }
                if(tipo.equals("Todos")){
                    tipo = "C";
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (car.getStatus().contains(tipo) && car.getNumero().toString().contains(lowerCaseFilter)) {
                    return true;
                }
                if(car.getStatus().contains(tipo) && car.getPlaca().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                if(car.getStatus().contains(tipo) && car.getNota().toString().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });


        cbTipo.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(car -> {
                String txt = pesquisar.getText();
                if ((txt == null || txt.isEmpty()) && newValue.equals("Todos")) {
                    return true;
                }
                String lowerCaseFilter = txt.toLowerCase();
                String tipo = newValue.toString();
                if(tipo.equals("Todos")){
                    tipo = "C";
                }
                if (car.getStatus().contains(tipo) && car.getNumero().toString().contains(lowerCaseFilter)) {
                    return true;
                }
                if(car.getStatus().contains(tipo) && car.getPlaca().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                if(car.getStatus().contains(tipo) && car.getNota().toString().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        SortedList<Carregamento> sortedData = new SortedList(filteredData);
        sortedData.comparatorProperty().bind(tabela.comparatorProperty());
        tabela.setItems(sortedData);
    }
    private void setCbTxt(){
        ObservableList<String> dados = FXCollections.observableArrayList(
                "Estoque Temporario",
                "Pré-Carregamento"
        );
        cbTxt.setItems(dados);
    }
    private void setCbTipo(){
        ObservableList<String> dados = FXCollections.observableArrayList(
                "Carregar",
                "Corte",
                "Todos"
        );
        cbTipo.setItems(dados);
        cbTipo.getSelectionModel().select(2);
    }
    private void setCbOrder(){
        ObservableList<String> dados = FXCollections.observableArrayList(
                "Produto",
                "Volume"
        );
        cbOrder.setItems(dados);
        cbOrder.getSelectionModel().selectFirst();
    }
    private void importStatus(Boolean imp, String msg){
        progress.setVisible(imp);
        progressText.setText(msg);
        btnImportar.setDisable(imp);
        btnCancelar.setDisable(imp);
        btnSeleciona.setDisable(imp);
        cbTxt.setDisable(imp);
        if(imp){
            txtTitulo.setEditable(false);
        }else{
            txtTitulo.setEditable(true);
            txtTitulo.setText("");
            txtArquivo.setText("");
            lbTitulo.setVisible(false);
            txtTitulo.setVisible(false);
        }
    }
    private void cbSelection(){
        cbTxt.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue.equals("Estoque Temporario")){
                    lbTitulo.setVisible(false);
                    txtTitulo.setVisible(false);
                    txtTitulo.setText("");
                }else{
                    lbTitulo.setVisible(true);
                    txtTitulo.setVisible(true);
                }
            }
        });
    }
    private void cbSelectionData(){
        dpData.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setTable();
                cbTipo.getSelectionModel().select(2);
            }
        });
    }
    private String getDataAtual(){
        Calendar data = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataSt = sdf.format(data.getTime());
        return dataSt;
    }
}
