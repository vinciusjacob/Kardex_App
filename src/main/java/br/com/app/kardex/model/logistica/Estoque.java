/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.model.logistica;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Vinicius
 */
public class Estoque {
    private int idestoque;
    private String idposicao;
    private int idproduto;
    private String codfabrica;
    private String descricao;
    private String unidade;
    private int iddepartamento;
    private String num_lote;
    private String dtfabricacao;
    private String dtvencimento;
    private Double qtdprod;
    private int qtdcx;
    private Double peso;
    private Boolean avariado;
    private String obs;

    public int getIdestoque(){return idestoque; }
    public void  setIdestoque(int idestoque){this.idestoque = idestoque; }
    public String getIdposicao(){return idposicao; }
    public void  setIdposicao(String idposicao){this.idposicao = idposicao; }
    public int getIdproduto(){return idproduto; }
    public void  setIdproduto(int idproduto){this.idproduto = idproduto; }
    public String getCodfabrica(){return codfabrica;}
    public void setCodfabrica(String codfabrica){this.codfabrica = codfabrica;}
    public String getDescricao(){return descricao;}
    public void setDescricao(String descricao){this.descricao = descricao;}
    public String getUnidade(){return unidade;}
    public void setUnidade(String unidade){this.unidade = unidade;}
    public int getIddepartamento(){return iddepartamento; }
    public void  setIddepartamento(int iddepartamento){this.iddepartamento = iddepartamento; }
    public String getNum_lote(){return num_lote; }
    public void  setNum_lote(String num_lote){this.num_lote = num_lote; }
    public String getDtfabricacao(){
        if(dtfabricacao == null){return null;}
        LocalDate date = LocalDate.parse(dtfabricacao);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formato);
    }
    public void  setDtfabricacao(String dtfabricacao){this.dtfabricacao = dtfabricacao; }
    public String getDtvencimento(){
        if(dtvencimento == null){return null;}
        LocalDate date = LocalDate.parse(dtvencimento);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formato);
    }
    public void  setDtvencimento(String dtvencimento){this.dtvencimento = dtvencimento; }
    public Double getQtdprod(){return qtdprod; }
    public void  setQtdprod(Double qtdprod){this.qtdprod = qtdprod; }
    public int getQtdcx(){return qtdcx; }
    public void  setQtdcx(int qtdcx){this.qtdcx = qtdcx; }
    public Double getPeso(){return peso; }
    public void  setPeso(Double peso){this.peso = peso; }
    public Boolean getAvariado(){return avariado; }
    public void  setAvariado(Boolean avariado){this.avariado = avariado; }
    public String getObs(){return obs; }
    public void  setObs(String obs){this.obs = obs; }
    public String getAvariadoText(){
        if(avariado){
            return "Sim";
        }else{
            return "Não";
        }
    }
    public void setAvariadoText(){

    }

    public Estoque(){ }
    public Estoque(int idestoque, String idposicao, int idproduto,String codfabrica,String descricao, String unidade, int iddepartamento, String num_lote, String dtfabricacao, String dtvencimento, Double qtdprod, int qtdcx, Double peso, Boolean avariado, String obs){
        this.idestoque = idestoque;
        this.idposicao = idposicao;
        this.idproduto = idproduto;
        this.codfabrica = codfabrica;
        this.descricao = descricao;
        this.unidade = unidade;
        this.iddepartamento = iddepartamento;
        this.num_lote = num_lote;
        this.dtfabricacao = dtfabricacao;
        this.dtvencimento = dtvencimento;
        this.qtdprod = qtdprod;
        this.qtdcx = qtdcx;
        this.peso = peso;
        this.avariado = avariado;
        this.obs = obs;
    }

    public ObservableList<Estoque> getLista(){
        ObservableList<Estoque> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM VW_ESTOQUE WHERE QTDCX <> 0";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Estoque(sql.rs.getInt("IDESTOQUE"),sql.rs.getString("IDPOSICAO"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public Estoque get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM VW_ESTOQUE WHERE IDESTOQUE = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new Estoque(sql.rs.getInt("IDESTOQUE"),sql.rs.getString("IDPOSICAO"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS"));
        }catch(Exception ex){
            return null;
        }
    }

    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            idestoque = sql.SqlUpdateId("INSERT INTO TBL_ESTOQUE (IDPOSICAO,IDPRODUTO,IDDEPARTAMENTO,NUM_LOTE,DTFABRICACAO,DTVENCIMENTO,QTDPROD,QTDCX,PESO,AVARIADO,OBS) VALUES ("
                    + " NULL ,"
                    + "'"+ idproduto +"',"
                    + "'"+ iddepartamento +"',"
                    + "'"+ num_lote +"',"
                    + "'"+ dtfabricacao +"',"
                    + "'"+ dtvencimento +"',"
                    + "'"+ qtdprod +"',"
                    + "'"+ qtdcx +"',"
                    + "'"+peso+"' ,"
                    + "'"+ avariado +"',"
                    + "'"+ obs +"')" );
            return null;
        }catch(Exception ex){
            System.out.println("Error: Estoque.insert."+ex.toString());
            return "Não foi possivel cadastrar Estoque!";
        }
    }

    public String update(){return null;}

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_ESTOQUE WHERE IDESTOQUE = '"+ idestoque +"'");
            return null;
        }catch(Exception ex){
            return "Não foi possivel excluir Estoque!";
        }
    }

    public int getSumQtdCx(int idproduto, int iddepartamento, String venc){
        try{
            if(venc == ""){
                venc = LocalDate.now().toString();
            }
            SqlServer sql = new SqlServer();
            String query = "SELECT SUM(QTDCX) AS QTDCX FROM TBL_ESTOQUE WHERE IDPRODUTO = '"+idproduto+"' AND IDDEPARTAMENTO = '"+iddepartamento+"' AND DTVENCIMENTO >= '"+venc+"' ";
            sql.SqlQuery(query);
            sql.rs.next();
            return sql.rs.getInt("QTDCX");
        }catch(Exception ex){
            System.out.println("Error: Estoque.getSumQtdCx."+ex.toString());
            return 0;
        }
    }

    public ObservableList<Estoque> getLista(int iddepartamento){
        ObservableList<Estoque> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM VW_ESTOQUE WHERE IDDEPARTAMENTO = '"+iddepartamento+"' AND QTDCX <> 0 ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Estoque(sql.rs.getInt("IDESTOQUE"),sql.rs.getString("IDPOSICAO"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    /**
     * Retorna lista do estoque de acordo com os parametros informados na ordem de vencimento
     * @param idproduto
     * @param iddepartamento
     * @return
     */
    public ObservableList<Estoque> getLista(int idproduto, int iddepartamento, String venc){
        ObservableList<Estoque> lista = FXCollections.observableArrayList();
        try{
            if(venc == ""){
                venc = LocalDate.now().toString();
            }
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM VW_ESTOQUE WHERE IDPRODUTO = '"+idproduto+"' AND IDDEPARTAMENTO = '"+iddepartamento+"' AND AVARIADO = 0 AND QTDCX > 0 AND DTVENCIMENTO >= '"+venc+"' ORDER BY DTVENCIMENTO";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Estoque(sql.rs.getInt("IDESTOQUE"),sql.rs.getString("IDPOSICAO"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: Estoque.getLista"+ex.toString());
            return null;
        }
    }

    /**
     * Retorna lista do estoque de acordo com os parametros informados na ordem de vencimento
     * @param idproduto
     * @param iddepartamento
     * @return
     */
    public ObservableList<Estoque> getLista(int idproduto, int iddepartamento, String venc, int avariado){
        ObservableList<Estoque> lista = FXCollections.observableArrayList();
        try{
            if(venc == ""){
                venc = LocalDate.now().toString();
            }
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM VW_ESTOQUE WHERE IDPRODUTO = '"+idproduto+"' AND IDDEPARTAMENTO = '"+iddepartamento+"' AND AVARIADO = '"+avariado+"' AND QTDCX > 0 AND DTVENCIMENTO >= '"+venc+"' ORDER BY DTVENCIMENTO";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Estoque(sql.rs.getInt("IDESTOQUE"),sql.rs.getString("IDPOSICAO"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: Estoque.getLista"+ex.toString());
            return null;
        }
    }

    public String updateR(){
        try{
            SqlServer sql = new SqlServer();
            String query = "UPDATE TBL_ESTOQUE SET QTDCX = '"+qtdcx+"', QTDPROD = '"+qtdprod+"' WHERE IDESTOQUE = '"+idestoque+"' ";
            sql.SqlUpdate(query);
            return null;
        }catch(Exception ex){
            System.out.println("Error: Estoque.updateR"+ex.toString());
            return "Não foi possivel realizar transação.";
        }
    }
}
