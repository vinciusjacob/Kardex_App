package br.com.app.kardex.model.coletor;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventarioItem {
    private int idinventario_item;
    private int idinventario;
    private int idposicao;
    private int idproduto;
    private int qtdcx;
    private Double qtdprod;
    private Double peso;
    private String dtfabricacao;
    private String dtvencimento;
    private String idconferente;
    private String dtcontagem;
    private int qtdcx_estoque;
    private Double qtdprod_estoque;
    private Double peso_estoque;
    private String dtfabricacao_estoque;
    private String dtvencimento_estoque;
    private Double recontar;
    private Double ajustado;

    public int getIdinventario_item(){return idinventario_item; }
    public void  setIdinventario_item(int idinventario_item){this.idinventario_item = idinventario_item; }
    public int getIdinventario(){return idinventario; }
    public void  setIdinventario(int idinventario){this.idinventario = idinventario; }
    public int getIdposicao(){return idposicao; }
    public void  setIdposicao(int idposicao){this.idposicao = idposicao; }
    public int getIdproduto(){return idproduto; }
    public void  setIdproduto(int idproduto){this.idproduto = idproduto; }
    public int getQtdcx(){return qtdcx; }
    public void  setQtdcx(int qtdcx){this.qtdcx = qtdcx; }
    public Double getQtdprod(){return qtdprod; }
    public void  setQtdprod(Double qtdprod){this.qtdprod = qtdprod; }
    public Double getPeso(){return peso; }
    public void  setPeso(Double peso){this.peso = peso; }
    public String getDtfabricacao(){return dtfabricacao; }
    public void  setDtfabricacao(String dtfabricacao){this.dtfabricacao = dtfabricacao; }
    public String getDtvencimento(){return dtvencimento; }
    public void  setDtvencimento(String dtvencimento){this.dtvencimento = dtvencimento; }
    public String getIdconferente(){return idconferente; }
    public void  setIdconferente(String idconferente){this.idconferente = idconferente; }
    public String getDtcontagem(){return dtcontagem; }
    public void  setDtcontagem(String dtcontagem){this.dtcontagem = dtcontagem; }
    public int getQtdcx_estoque(){return qtdcx_estoque; }
    public void  setQtdcx_estoque(int qtdcx_estoque){this.qtdcx_estoque = qtdcx_estoque; }
    public Double getQtdprod_estoque(){return qtdprod_estoque; }
    public void  setQtdprod_estoque(Double qtdprod_estoque){this.qtdprod_estoque = qtdprod_estoque; }
    public Double getPeso_estoque(){return peso_estoque; }
    public void  setPeso_estoque(Double peso_estoque){this.peso_estoque = peso_estoque; }
    public String getDtfabricacao_estoque(){return dtfabricacao_estoque; }
    public void  setDtfabricacao_estoque(String dtfabricacao_estoque){this.dtfabricacao_estoque = dtfabricacao_estoque; }
    public String getDtvencimento_estoque(){return dtvencimento_estoque; }
    public void  setDtvencimento_estoque(String dtvencimento_estoque){this.dtvencimento_estoque = dtvencimento_estoque; }
    public Double getRecontar(){return recontar; }
    public void  setRecontar(Double recontar){this.recontar = recontar; }
    public Double getAjustado(){return ajustado; }
    public void  setAjustado(Double ajustado){this.ajustado = ajustado; }

    public InventarioItem(){ }
    public InventarioItem(int idinventario_item, int idinventario, int idposicao, int idproduto, int qtdcx, Double qtdprod, Double peso, String dtfabricacao, String dtvencimento, String idconferente, String dtcontagem, int qtdcx_estoque, Double qtdprod_estoque, Double peso_estoque, String dtfabricacao_estoque, String dtvencimento_estoque, Double recontar, Double ajustado){
        this.idinventario_item = idinventario_item;
        this.idinventario = idinventario;
        this.idposicao = idposicao;
        this.idproduto = idproduto;
        this.qtdcx = qtdcx;
        this.qtdprod = qtdprod;
        this.peso = peso;
        this.dtfabricacao = dtfabricacao;
        this.dtvencimento = dtvencimento;
        this.idconferente = idconferente;
        this.dtcontagem = dtcontagem;
        this.qtdcx_estoque = qtdcx_estoque;
        this.qtdprod_estoque = qtdprod_estoque;
        this.peso_estoque = peso_estoque;
        this.dtfabricacao_estoque = dtfabricacao_estoque;
        this.dtvencimento_estoque = dtvencimento_estoque;
        this.recontar = recontar;
        this.ajustado = ajustado;
    }

    public ObservableList<InventarioItem> getLista(){
        ObservableList<InventarioItem> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_INVENTARIO_ITEM ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new InventarioItem(sql.rs.getInt("IDINVENTARIO_ITEM"),sql.rs.getInt("IDINVENTARIO"),sql.rs.getInt("IDPOSICAO"),sql.rs.getInt("IDPRODUTO"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("QTDPROD"),sql.rs.getDouble("PESO"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getString("IDCONFERENTE"),sql.rs.getString("DTCONTAGEM"),sql.rs.getInt("QTDCX_ESTOQUE"),sql.rs.getDouble("QTDPROD_ESTOQUE"),sql.rs.getDouble("PESO_ESTOQUE"),sql.rs.getString("DTFABRICACAO_ESTOQUE"),sql.rs.getString("DTVENCIMENTO_ESTOQUE"),sql.rs.getDouble("RECONTAR"),sql.rs.getDouble("AJUSTADO")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public InventarioItem get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_INVENTARIO_ITEM WHERE IDINVENTARIO_ITEM = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new InventarioItem(sql.rs.getInt("IDINVENTARIO_ITEM"),sql.rs.getInt("IDINVENTARIO"),sql.rs.getInt("IDPOSICAO"),sql.rs.getInt("IDPRODUTO"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("QTDPROD"),sql.rs.getDouble("PESO"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getString("IDCONFERENTE"),sql.rs.getString("DTCONTAGEM"),sql.rs.getInt("QTDCX_ESTOQUE"),sql.rs.getDouble("QTDPROD_ESTOQUE"),sql.rs.getDouble("PESO_ESTOQUE"),sql.rs.getString("DTFABRICACAO_ESTOQUE"),sql.rs.getString("DTVENCIMENTO_ESTOQUE"),sql.rs.getDouble("RECONTAR"),sql.rs.getDouble("AJUSTADO"));
        }catch(Exception ex){
            return null;
        }
    }

    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            idinventario_item = sql.SqlUpdateId("INSERT INTO TBL_INVENTARIO_ITEM (IDINVENTARIO,IDPOSICAO,IDPRODUTO,QTDCX,QTDPROD,PESO,DTFABRICACAO,DTVENCIMENTO,IDCONFERENTE,DTCONTAGEM,QTDCX_ESTOQUE,QTDPROD_ESTOQUE,PESO_ESTOQUE,DTFABRICACAO_ESTOQUE,DTVENCIMENTO_ESTOQUE,RECONTAR,AJUSTADO) VALUES ("
                    + "'"+ idinventario +"',"
                    + "'"+ idposicao +"',"
                    + "'"+ idproduto +"',"
                    + "'"+ qtdcx +"',"
                    + "'"+ qtdprod +"',"
                    + "'"+ peso +"',"
                    + "'"+ dtfabricacao +"',"
                    + "'"+ dtvencimento +"',"
                    + "'"+ idconferente +"',"
                    + "'"+ dtcontagem +"',"
                    + "'"+ qtdcx_estoque +"',"
                    + "'"+ qtdprod_estoque +"',"
                    + "'"+ peso_estoque +"',"
                    + "'"+ dtfabricacao_estoque +"',"
                    + "'"+ dtvencimento_estoque +"',"
                    + "'"+ recontar +"',"
                    + "'"+ ajustado +"')" );
            return null;
        }catch(Exception ex){
            return "Não foi possivel cadastrar InventarioItem!";
        }
    }

    public String update(){return null;}

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_INVENTARIO_ITEM WHERE IDINVENTARIO_ITEM = '"+ idinventario_item +"'");
            return null;
        }catch(Exception ex){
            return "Não foi possivel excluir InventarioItem!";
        }
    }

}


