/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.controller.logistica;

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
import br.com.app.kardex.model.logistica.Produto;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Vinicius
 */
public class fx_produtoController implements Initializable {

    private final Produto produto = new Produto();
    private Boolean novo;
    @FXML private BorderPane pane;
    @FXML private GridPane paneCad;
    @FXML private TextField txtCod;
    @FXML private TextField txtSigla;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtPesoBruto;
    @FXML private TextField txtCamara;
    @FXML private TextField txtShelf;
    @FXML private TextField txtLastro;
    @FXML private TextField txtAltura;
    @FXML private TextField txtCxsPallet;
    @FXML private TextField txtPesoLiq;
    @FXML private CheckBox txtImpPeso;
    @FXML private HBox msgProgressBox;
    @FXML private HBox msgErrorBox;
    @FXML private HBox msgConfBox;
    @FXML private Label msgProgress;
    @FXML private Label msgError;
    @FXML private Label msgConf;
    @FXML private TextField txtPesquisar;
    @FXML private TableView<Produto> tabela;
    @FXML private TableColumn colCod;
    @FXML private TableColumn colSigla;
    @FXML private TableColumn colDescricao;
    @FXML private TableColumn colPesoBruto;
    @FXML private TableColumn colCamara;
    @FXML private TableColumn colShelf;
    @FXML private TableColumn colLastro;
    @FXML private TableColumn colAltura;
    @FXML private TableColumn colCxsPallet;
    @FXML private TableColumn colPesoLiq;
    @FXML private TableColumn colImpPeso;
    @FXML private Button btnAlt;
    @FXML private Button btnExc;
    
    @FXML private void Cadastro(){
        pane.setRight(paneCad);
        txtCod.requestFocus();
        novo = true;
    }
    @FXML private void Alterar(){
        pane.setRight(paneCad);
        txtCod.setEditable(false);
        txtSigla.requestFocus();
        txtCod.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getCod().toString());
        txtSigla.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getSigla());
        txtDescricao.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getDescricao());
        txtPesoBruto.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getPesobruto().toString().replaceAll("\\.", ","));
        txtCamara.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getCamara());
        txtShelf.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getShelf().toString());
        txtLastro.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getLastro());
        txtAltura.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getAltura());
        txtCxsPallet.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getCxspallet());
        txtPesoLiq.setText(tabela.getSelectionModel().selectedItemProperty().getValue().getPesoliq().toString().replaceAll("\\.", ","));
        txtImpPeso.setSelected(tabela.getSelectionModel().selectedItemProperty().getValue().isImppeso());
        novo = false;
    }
    @FXML private void Excluir(){
        try{
            Integer cod = tabela.getSelectionModel().getSelectedItem().getCod();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirmar exclusão");
            alert.setContentText("Tem certeza que deseja excluir produto '"+cod+"'?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                produto.Excluir(cod);
                setTable();
            }
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error!");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor!");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
        }
    }
    @FXML private void Cancelar(){
        novo = true;
        pane.setRight(null);
        txtCod.setEditable(true);
        txtCod.setText("");
        txtSigla.setText("");
        txtDescricao.setText("");
        txtPesoBruto.setText("");
        txtCamara.setText("");
        txtShelf.setText("");
        txtLastro.setText("");
        txtAltura.setText("");
        txtCxsPallet.setText("");
        txtPesoLiq.setText("");
        txtImpPeso.setSelected(false);
        msgProgressBox.setVisible(false);
        msgErrorBox.setVisible(false);
        msgConfBox.setVisible(false);
    }
    @FXML private void Limpar(){
        novo = true;
        txtCod.requestFocus();
        txtCod.setEditable(true);
        txtCod.setText("");
        txtSigla.setText("");
        txtDescricao.setText("");
        txtPesoBruto.setText("");
        txtCamara.setText("");
        txtShelf.setText("");
        txtLastro.setText("");
        txtAltura.setText("");
        txtCxsPallet.setText("");
        txtPesoLiq.setText("");
        txtImpPeso.setSelected(false);
        msgProgressBox.setVisible(false);
        msgErrorBox.setVisible(false);
        msgConfBox.setVisible(false);
    }
    @FXML private void Gravar(){
        msgErrorBox.setVisible(false);
        msgConfBox.setVisible(false);
        msgProgressBox.setVisible(true);
        Task<Object[]> tarefa = new Task<Object[]>(){
            @Override
            protected Object[] call() throws Exception {
                if(txtCod.getText().isEmpty() || !isNumero(txtCod.getText())){
                    return new Object[]{false,"Código inválido.",txtCod};
                }else if(txtSigla.getText().equals("")){
                    return new Object[]{false,"Sigla não pode ficar em branco.",txtSigla};
                }else if(txtDescricao.getText().equals("")){
                    return new Object[]{false,"Descrição não pode ficar em branco.",txtDescricao};
                }else if(txtPesoBruto.getText().equals("") || !isNumero(txtPesoBruto.getText().replaceAll(",", ""))){
                    return new Object[]{false,"Peso Bruto inválido.",txtPesoBruto};
                }else if(txtCamara.getText().equals("")){
                    return new Object[]{false,"Câmara não pode ficar em branco.",txtCamara};
                }else if(txtShelf.getText().equals("") || !isNumero(txtShelf.getText())){
                    return new Object[]{false,"Shelf inválido.",txtShelf};
                }else if(txtPesoLiq.getText().equals("") || !isNumero(txtPesoLiq.getText().replaceAll(",", ""))){
                    return new Object[]{false,"Peso líquido inválido.",txtPesoLiq};
                }else{
                    Produto produto = new Produto(
                            Integer.parseInt(txtCod.getText()),
                            txtSigla.getText(),
                            txtDescricao.getText(),
                            Double.parseDouble(txtPesoBruto.getText().replaceAll(",", "\\.")),
                            txtCamara.getText(),
                            Integer.parseInt(txtShelf.getText()),
                            txtLastro.getText(),
                            txtAltura.getText(),
                            txtCxsPallet.getText(),
                            Double.parseDouble(txtPesoLiq.getText().replaceAll(",", "\\.")),
                            txtImpPeso.isSelected());
                    String msg;
                    if(novo){
                        if(!produto.isExiste()){
                            msg = produto.Cadastrar();
                        }else{
                            return new Object[]{false,"Produto já Existe!",txtCod};
                        }
                    }else{
                        msg = produto.Alterar();
                    }
                    setTable();
                    return new Object[]{true,msg};
                }
            }
            @Override
            protected void succeeded(){
                if(!(Boolean)getValue()[0]){
                    msgProgressBox.setVisible(false);
                    msgErrorBox.setVisible(true);
                    msgError.setText(getValue()[1].toString());
                    TextField txt = (TextField)getValue()[2];
                    txt.requestFocus();
                }else{
                    Limpar();
                    msgConfBox.setVisible(true);
                    msgConf.setText(getValue()[1].toString());
                    novo = true;
                }
            }
            @Override
            protected void failed(){
                msgProgressBox.setVisible(false);
                msgErrorBox.setVisible(true);
                msgError.setText("Error ao gravar informações.");
            }
            
        };
        new Thread(tarefa).start();
    }
    private Boolean isNumero(String string){
        char[] c = string.toCharArray();
        Boolean d = true;
        for (int i = 0; i < c.length; i++){
            if ( !Character.isDigit( c[ i ] ) ){
                d = false;
                break;
            }
        }
        return d;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.setRight(null);
        tblSelection();
        setTable();
    }    
    
    private void setTable(){
        colCod.setCellValueFactory(new PropertyValueFactory<>("cod"));
        colSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPesoBruto.setCellValueFactory(new PropertyValueFactory<>("pesobruto"));
        colCamara.setCellValueFactory(new PropertyValueFactory<>("camara"));
        colShelf.setCellValueFactory(new PropertyValueFactory<>("shelf"));
        colLastro.setCellValueFactory(new PropertyValueFactory<>("lastro"));
        colAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
        colCxsPallet.setCellValueFactory(new PropertyValueFactory<>("cxspallet"));
        colPesoLiq.setCellValueFactory(new PropertyValueFactory<>("pesoliq"));
        colImpPeso.setCellValueFactory(new PropertyValueFactory<>("imppeso"));
        FilteredList<Produto> filteredData = new FilteredList<>(produto.getLista());
        txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pro->{
                if ((newValue == null || newValue.isEmpty())) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (pro.getCod().toString().contains(lowerCaseFilter)) {
                    return true;
                }
                if(pro.getDescricao().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                if(pro.getSigla().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        SortedList<Produto> sortedData = new SortedList(filteredData);
        sortedData.comparatorProperty().bind(tabela.comparatorProperty());
        tabela.setItems(sortedData);
    }
    private void tblSelection(){
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnAlt.setDisable(false);
                btnExc.setDisable(false);
            }else{
                btnAlt.setDisable(true);
                btnExc.setDisable(true);
            }
        });
    }
    
}
