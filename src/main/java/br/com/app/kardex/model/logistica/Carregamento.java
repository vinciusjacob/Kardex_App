/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.model.logistica;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import br.com.app.kardex.util.SqlServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 *
 * @author Vinicius
 */
public class Carregamento {
    
    private SqlServer sql = new SqlServer();
    private final StringProperty placa;
    private final IntegerProperty cod;
    private final IntegerProperty numero;
    private final StringProperty data;
    private final IntegerProperty produto;
    private final StringProperty descricao;
    private final IntegerProperty qtd;
    private final StringProperty fabricacao;
    private final IntegerProperty corte;
    private final IntegerProperty codtxt;
    private final StringProperty reentrega;
    private final StringProperty status;
    private final IntegerProperty nota;
    private final StringProperty ocorrencia;
    private final StringProperty obs;
    
    public void setNumero(Integer numero){
     this.numero.set(numero);
    }
    public void setData(String data){
     this.data.set(data);
    }
    public String getPlaca(){
        return placa.get();
    }
    public Integer getNumero(){
        return numero.get();
    }
    public String getData(){
        return data.get();
    }
    public Integer getProduto(){
        return produto.get();
    }
    public String getDescricao(){
        return descricao.get();
    }
    public Integer getQtd(){
        return qtd.get();
    }
    public String getFabricacao(){
        return fabricacao.get();
    }
    public String getReentrega(){
        return reentrega.get();
    }
    public String getStatus(){
        return status.get();
    }
    public Integer getNota(){
        return nota.get();
    }
    public String getOcorrencia(){
        return ocorrencia.get();
    }
    public String getObs(){
        return obs.get();
    }
    public Carregamento(){
        placa = new SimpleStringProperty();
        cod = new SimpleIntegerProperty();
        numero = new SimpleIntegerProperty();
        data = new SimpleStringProperty();
        produto = new SimpleIntegerProperty();
        descricao = new SimpleStringProperty();
        qtd = new SimpleIntegerProperty(0);
        fabricacao = new SimpleStringProperty();
        corte = new SimpleIntegerProperty(0);
        codtxt = new SimpleIntegerProperty(0);
        reentrega = new SimpleStringProperty();
        status = new SimpleStringProperty();
        nota = new SimpleIntegerProperty();
        ocorrencia = new SimpleStringProperty();
        obs = new SimpleStringProperty();
    }
    public Carregamento(String placa,Integer cod,Integer numero, String data, Integer codprod, String descrprod, Integer qtd, String fabr, Integer corte, Integer codtxt, String reentrega, String status,Integer nota, String ocorrencia,String obs){
        this.placa = new SimpleStringProperty(placa);
        this.cod = new SimpleIntegerProperty(cod);
        this.numero = new SimpleIntegerProperty(numero);
        this.data = new SimpleStringProperty(data);
        this.produto = new SimpleIntegerProperty(codprod);
        this.descricao = new SimpleStringProperty(descrprod);
        this.qtd = new SimpleIntegerProperty(qtd);
        this.fabricacao = new SimpleStringProperty(fabr);
        this.corte = new SimpleIntegerProperty(corte);
        this.codtxt = new SimpleIntegerProperty(codtxt);
        this.reentrega = new SimpleStringProperty(reentrega);
        this.status = new SimpleStringProperty(status);
        this.nota = new SimpleIntegerProperty(nota);
        this.ocorrencia = new SimpleStringProperty(ocorrencia);
        this.obs = new SimpleStringProperty(obs);
    }
    public ObservableList<Carregamento> getLista(String data){
        ObservableList<Carregamento> lista = FXCollections.observableArrayList();
        try{
            String query = "SELECT * FROM LOG_CARREGAMENTO WHERE CAR_DATA = '"+data+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Carregamento(
                    sql.rs.getString("CAR_PLACA"),
                    sql.rs.getInt("CAR_COD"),
                    sql.rs.getInt("CAR_NUMERO"),
                    getDataFormat(sql.rs.getDate("CAR_DATA")),
                    sql.rs.getInt("CAR_CODPROD"),
                    sql.rs.getString("CAR_DESCRPROD"),
                    sql.rs.getInt("CAR_QTD"),
                    getDataFormat(sql.rs.getDate("CAR_FABRICACAO")),
                    sql.rs.getInt("CAR_CORTE"),
                    sql.rs.getInt("CAR_CODTXT"),
                    getDescrReent(sql.rs.getInt("CAR_REENTREGA")),
                    getDescrStatus(sql.rs.getInt("CAR_CORTE")),
                    sql.rs.getInt("CAR_REENTREGA"),
                    "",
                    ""));
            }
            sql.desconecta();
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
        }
        return lista;
    }
    public ObservableList<Carregamento> getLista(String viagem,String order,Double param,ArrayList<String> placas){
        ObservableList<Carregamento> lista = FXCollections.observableArrayList();
        try{
            String query = "SELECT CAR_PLACA,CAR_NUMERO,CAR_DATA,CAR_CODPROD,CAR_DESCRPROD,SUM(CAR_QTD) AS CAR_QTD,PRO_SHELF,PRO_IMPPESO"
            + " FROM LOG_CARREGAMENTO LEFT JOIN LOG_PRODUTO ON PRO_COD = CAR_CODPROD"
            + " WHERE CAR_NUMERO= '"+viagem+"' AND CAR_CORTE = 0 GROUP BY CAR_PLACA,CAR_NUMERO,CAR_DATA,CAR_CODPROD,CAR_DESCRPROD,PRO_SHELF,PRO_IMPPESO"
            + " ORDER BY CAR_PLACA ASC, '"+order+"' ASC";
            
            if(sql.SqlNumRows(query) == 0)
                return null;
            sql.SqlQuery(query);
            while(sql.rs.next()){
                String ocorrencia = "";
                String obs = null;
                if(null != param && placas == null){
                    ocorrencia = getOcorrencia(sql.rs.getInt("PRO_SHELF"),param,sql.rs.getDate("CAR_DATA"));
                    obs = "Data Específica"; 
                }
                else if(null != param){
                    Integer row = placas.size();
                    Boolean validador = false;
                    for(String placa:placas){
                        if(placa.equals(sql.rs.getString("CAR_PLACA"))){
                            validador = true;
                        }
                    }
                    if(validador){
                        ocorrencia = getOcorrencia(sql.rs.getInt("PRO_SHELF"),param,sql.rs.getDate("CAR_DATA"));
                        obs = "Data Específica";  
                    }
                }
                if(sql.rs.getInt("PRO_IMPPESO") == 1 && null != param)
                    ocorrencia = ocorrencia+" / Peso:";
                else if(sql.rs.getInt("PRO_IMPPESO") == 1)
                    ocorrencia = ocorrencia+"Peso:";
                lista.add(new Carregamento(
                    sql.rs.getString("CAR_PLACA"),
                    0,
                    sql.rs.getInt("CAR_NUMERO"),
                    getDataFormat(sql.rs.getDate("CAR_DATA")),
                    sql.rs.getInt("CAR_CODPROD"),
                    sql.rs.getString("CAR_DESCRPROD"),
                    sql.rs.getInt("CAR_QTD"),
                    "",
                    0,
                    0,
                    "",
                    "",
                    0,
                    ocorrencia,
                    obs));
            }
            sql.desconecta();
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
        }
        return lista;
    }
    private String getOcorrencia(Integer shelf,Double param,Date data) throws ParseException{
        if(shelf == 0)
            return "Produto não cadastrado!";
        Double dia = shelf * param;
        int dias = dia.intValue();
        Calendar cData = Calendar.getInstance();
        cData.setTime(data);
        cData.add(Calendar.DAY_OF_MONTH, (dias+1));
        String ocorrencia = "Separar a partir de "+getDataFormat(cData.getTime());
        return ocorrencia;
    }
    public Integer getQtdCorte(String viagem){
        Integer qtdCorte = 0;
        try{
            String query = "SELECT SUM(CAR_QTD) AS QTD FROM LOG_CARREGAMENTO WHERE CAR_CORTE = 1 AND CAR_NUMERO = '"+viagem+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                qtdCorte = sql.rs.getInt("QTD");
            }
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
        }
        return qtdCorte;
    }
    private String getDataFormat(Date data) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dt = sdf.format(data);
        return dt;
    }
    public void Importar(File arquivo, String titulo, String data) throws Exception{
        if(arquivo.exists()){
            String codtxt = cadTXT(titulo,data);
            FileReader leitorArquivo = new FileReader(arquivo.toString());
            BufferedReader bufferArquivo = new BufferedReader(leitorArquivo);
            String linha;
            while(true){
                linha = bufferArquivo.readLine();
                if(linha == null){
                    break;
                }
                String dados[] = linha.split(Pattern.quote(";"));
                if(!dados[0].equals("PLACA") && !dados[1].equals("NUM_CARREGAMENTO")){
                    Integer qtd = parseInt(dados[5]);
                    Integer codP = parseInt(dados[3]);
                    String reent = "";
                    String placa = "";
                    dados[1] = dados[1].substring(0, 7);
                    if(dados.length == 8)
                        placa = dados[7];
                    if(dados.length >= 7)
                        reent = dados[6];
                    if(isNumero(reent) && !reent.equals("")){
                        Cadastrar(placa,dados[1],dados[2],dados[3],dados[4],qtd.toString(),"","0",codtxt,reent);
                    }else{
                        String query = "SELECT * FROM LOG_ESTOQUE WHERE EST_CODPROP = '"+codP+"' ORDER BY EST_FABRICACAO ASC ";
                        sql.SqlQuery(query);
                        while(sql.rs.next()){
                            Integer cod = sql.rs.getInt("EST_COD");
                            Integer qtdE = sql.rs.getInt("EST_QTD");
                            String fabE = sql.rs.getString("EST_FABRICACAO");
                            
                            if(cod.toString().equals("") || cod == null){
                                Cadastrar(placa,dados[1],dados[2],dados[3],dados[4],qtd.toString(),"","1",codtxt,reent);
                                qtd = 0;
                            }else if(Objects.equals(qtd, qtdE)){
                                Cadastrar(placa,dados[1],dados[2],dados[3],dados[4],qtd.toString(),fabE,"0",codtxt,reent);
                                ExcluirEst(cod);
                                qtd = 0;
                            }else if(qtd < qtdE){
                                qtdE = qtdE - qtd;
                                Cadastrar(placa,dados[1],dados[2],dados[3],dados[4],qtd.toString(),fabE,"0",codtxt,reent);
                                BaixarEst(cod,qtdE);
                                qtd = 0;
                            }else if(qtd > qtdE && qtdE != 0){
                                qtd = qtd - qtdE;
                                Cadastrar(placa,dados[1],dados[2],dados[3],dados[4],qtdE.toString(),fabE,"0",codtxt,reent);
                                ExcluirEst(cod);
                            }
                            
                            if(qtdE == 0){
                                ExcluirEst(cod);
                            }
                            if(qtd == 0){
                                break;
                            }
                        }
                        if(qtd > 0){
                            Cadastrar(placa,dados[1],dados[2],dados[3],dados[4],qtd.toString(),"","1",codtxt,reent);
                        }
                    }
                }
            }
        }
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
    private void Cadastrar(String placa,String numero, String data, String codprod, String descrprod,String qtd, String fabricacao, String corte, String codtxt, String reentrega) throws Exception{
        String query = "INSERT INTO LOG_CARREGAMENTO (CAR_PLACA,CAR_NUMERO, CAR_DATA, CAR_CODPROD, CAR_DESCRPROD, CAR_QTD, CAR_FABRICACAO, CAR_CORTE, CAR_CODTXT, CAR_REENTREGA) "
                + "VALUES ('"+placa+"' , "+numero+",'"+data+"', "+codprod+",'"+descrprod+"',"+qtd+", '"+fabricacao+"',"+corte+","+codtxt+", '"+reentrega+"' )";
        sql.SqlUpdate(query);
    }
    private void BaixarEst(Integer cod, Integer qtd) throws Exception{
        String query = " UPDATE LOG_ESTOQUE SET EST_QTD = '"+qtd+"' WHERE EST_COD = '"+cod+"' ";
        sql.SqlUpdate(query);
    }
    private void ExcluirEst(Integer cod) throws Exception{
        String query = " DELETE FROM LOG_ESTOQUE WHERE EST_COD = '"+cod+"' ";
        sql.SqlUpdate(query);
    }
           
    private String cadTXT(String titulo, String data) throws Exception{
        String query = "INSERT INTO LOG_TXT (TXT_TITULO, TXT_DATA) "
                + "VALUES ('"+titulo+"', '"+data+"' )";
        sql.SqlUpdate(query);
        query = "SELECT * FROM LOG_TXT WHERE TXT_TITULO = '"+titulo+"' AND TXT_DATA = '"+data+"' ";
        sql.SqlQuery(query);
        String cod = "";
        while(sql.rs.next()){
            cod = sql.rs.getString("TXT_CODIGO");
        }
        sql.desconecta();
        return cod;
    }
    private String getDescrReent(Integer nf){
        if(nf == null || nf == 0){
            return "Distribuição";
        }else{
            return "Reentrega";
        }
    }
    private String getDescrStatus(Integer bit){
        if(bit == 0){
            return "Carregar";
        }else{
            return "Corte";
        }
    }
    
}
