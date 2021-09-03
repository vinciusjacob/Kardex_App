package br.com.app.kardex.model.kardex;

import br.com.app.kardex.util.SqlServer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NotaFiscalItem {
    private int idnotaitem;
    private int idnota;
    private int idproduto;
    private String codfabrica;
    private String descricao;
    private String unidade;
    private Double qtdprod;
    private IntegerProperty qtdcx;
    private Double peso;
    private Double qtdprod_corte;
    private int qtdcx_corte;
    private Double peso_corte;
    private Double prunit;
    private Double prunitliq;
    private Double valorliq;
    private Double desconto;
    private Double valorbruto;
    private String ncm;
    private String cst;
    private int idcfop;
    private String obs;
    private Boolean pesovariavel;
    private Double qtdundcx;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdnotaitem(){return idnotaitem; }
    public void  setIdnotaitem(int idnotaitem){this.idnotaitem = idnotaitem; }
    public int getIdnota(){return idnota; }
    public void  setIdnota(int idnota){this.idnota = idnota; }
    public int getIdproduto(){return idproduto; }
    public void  setIdproduto(int idproduto){this.idproduto = idproduto; }
    public String getCodfabrica(){return codfabrica;}
    public void setCodfabrica(String codfabrica){this.codfabrica = codfabrica;}
    public String getUnidade(){return unidade; }
    public void  setUnidade(String unidade){this.unidade = unidade; }
    public Double getQtdprod(){return qtdprod; }
    public void  setQtdprod(Double qtdprod){this.qtdprod = qtdprod; }
    public String getQtdprod2(){return Double.toString(this.qtdprod);}
    public void setQtdprod2(String qtdProd2){this.qtdprod = Double.parseDouble(qtdProd2);}
    public int getQtdcx(){return qtdcx.get(); }
    public void  setQtdcx(int qtdcx){this.qtdcx.set(qtdcx); }
    public IntegerProperty qtdcxProperty(){return qtdcx;}
    public Double getPeso(){return peso; }
    public void  setPeso(Double peso){this.peso = peso; }
    public Double getQtdprod_corte(){return qtdprod_corte; }
    public void  setQtdprod_corte(Double qtdprod_corte){this.qtdprod_corte = qtdprod_corte; }
    public int getQtdcx_corte(){return qtdcx_corte; }
    public void  setQtdcx_corte(int qtdcx_corte){this.qtdcx_corte = qtdcx_corte; }
    public Double getPeso_corte(){return peso_corte; }
    public void  setPeso_corte(Double peso_corte){this.peso_corte = peso_corte; }
    public Double getPrunit(){return prunit; }
    public void  setPrunit(Double prunit){this.prunit = prunit; }
    public Double getPrunitliq(){return prunitliq; }
    public void  setPrunitliq(Double prunitliq){this.prunitliq = prunitliq; }
    public Double getValorliq(){return valorliq; }
    public void  setValorliq(Double valorliq){this.valorliq = valorliq; }
    public Double getDesconto(){return desconto; }
    public void  setDesconto(Double desconto){this.desconto = desconto; }
    public Double getValorbruto(){return valorbruto; }
    public void  setValorbruto(Double valorbruto){this.valorbruto = valorbruto; }
    public String getNcm(){return ncm; }
    public void  setNcm(String ncm){this.ncm = ncm; }
    public String getCst(){return cst; }
    public void  setCst(String cst){this.cst = cst; }
    public int getIdcfop(){return idcfop; }
    public void  setIdcfop(int idcfop){this.idcfop = idcfop; }
    public String getObs(){return obs; }
    public void  setObs(String obs){this.obs = obs; }
    public Boolean getPesovariavel(){return pesovariavel;}
    public void setPesovariavel(Boolean pesovariavel){this.pesovariavel = pesovariavel;}
    public Double getQtdundcx(){return qtdundcx;}
    public void setQtdundcx(Double qtdundcx){this.qtdundcx = qtdundcx;}

    public NotaFiscalItem(){
        this.qtdcx = new SimpleIntegerProperty(0);
    }

    public NotaFiscalItem(int idnotaitem, int idnota, int idproduto, String codfabrica, String descricao, String unidade, Double qtdprod, int qtdcx, Double peso, Double qtdprod_corte, int qtdcx_corte, Double peso_corte, Double prunit, Double prunitliq, Double valorliq, Double desconto, Double valorbruto, String ncm, String cst, int idcfop, String obs,Boolean pesovariavel,Double qtdundcx){
        this.idnotaitem = idnotaitem;
        this.idnota = idnota;
        this.idproduto = idproduto;
        this.codfabrica = codfabrica;
        this.descricao = descricao;
        this.unidade = unidade;
        this.qtdprod = qtdprod;
        this.qtdcx = new SimpleIntegerProperty(qtdcx);
        this.peso = peso;
        this.qtdprod_corte = qtdprod_corte;
        this.qtdcx_corte = qtdcx_corte;
        this.peso_corte = peso_corte;
        this.prunit = prunit;
        this.prunitliq = prunitliq;
        this.valorliq = valorliq;
        this.desconto = desconto;
        this.valorbruto = valorbruto;
        this.ncm = ncm;
        this.cst = cst;
        this.idcfop = idcfop;
        this.obs = obs;
        this.pesovariavel = pesovariavel;
        this.qtdundcx = qtdundcx;
    }

    public ObservableList<NotaFiscalItem> getLista(){
        ObservableList<NotaFiscalItem> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT ITEM.*,P.DESCRICAO,P.CODFABRICA,P.PESO_VARIAVEL,P.QTDUNDCX FROM TBL_NOTA_ITEM AS ITEM INNER JOIN TBL_PRODUTO AS P ON P.IDPRODUTO = ITEM.IDPRODUTO ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new NotaFiscalItem(sql.rs.getInt("IDNOTAITEM"),sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getDouble("QTDPROD_CORTE"),sql.rs.getInt("QTDCX_CORTE"),sql.rs.getDouble("PESO_CORTE"),sql.rs.getDouble("PRUNIT"),sql.rs.getDouble("PRUNITLIQ"),sql.rs.getDouble("VALORLIQ"),sql.rs.getDouble("DESCONTO"),sql.rs.getDouble("VALORBRUTO"),sql.rs.getString("NCM"),sql.rs.getString("CST"),sql.rs.getInt("IDCFOP"),sql.rs.getString("OBS"),sql.rs.getBoolean("PESO_VARIAVEL"),sql.rs.getDouble("QTDUNDCX")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return null;
        }
    }

    public NotaFiscalItem get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT ITEM.*,P.DESCRICAO,P.PESO_VARIAVEL,,P.QTDUNDCX FROM TBL_NOTA_ITEM AS ITEM INNER JOIN TBL_PRODUTO AS P ON P.IDPRODUTO = ITEM.IDPRODUTO WHERE IDNOTAITEM = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new NotaFiscalItem(sql.rs.getInt("IDNOTAITEM"),sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getDouble("QTDPROD_CORTE"),sql.rs.getInt("QTDCX_CORTE"),sql.rs.getDouble("PESO_CORTE"),sql.rs.getDouble("PRUNIT"),sql.rs.getDouble("PRUNITLIQ"),sql.rs.getDouble("VALORLIQ"),sql.rs.getDouble("DESCONTO"),sql.rs.getDouble("VALORBRUTO"),sql.rs.getString("NCM"),sql.rs.getString("CST"),sql.rs.getInt("IDCFOP"),sql.rs.getString("OBS"),sql.rs.getBoolean("PESO_VARIAVEL"),sql.rs.getDouble("QTDUNDCX"));
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return null;
        }
    }

    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            idnotaitem = sql.SqlUpdateId("INSERT INTO TBL_NOTA_ITEM (IDNOTA,IDPRODUTO,UNIDADE,QTDPROD,QTDCX,PESO,QTDPROD_CORTE,QTDCX_CORTE,PESO_CORTE,PRUNIT,PRUNITLIQ,VALORLIQ,DESCONTO,VALORBRUTO,NCM,CST,IDCFOP,OBS) VALUES ("
                    + "'"+ idnota +"',"
                    + "'"+ idproduto +"',"
                    + "'"+ unidade +"',"
                    + "'"+ qtdprod +"',"
                    + "'"+ getQtdcx() +"',"
                    + "'"+ peso +"',"
                    + "'"+ qtdprod_corte +"',"
                    + "'"+ qtdcx_corte +"',"
                    + "'"+ peso_corte +"',"
                    + "'"+ prunit +"',"
                    + "'"+ prunitliq +"',"
                    + "'"+ valorliq +"',"
                    + "'"+ desconto +"',"
                    + "'"+ valorbruto +"',"
                    + "'"+ ncm +"',"
                    + "'"+ cst +"',"
                    + "'"+ idcfop +"',"
                    + "'"+ obs +"')" );
            return null;
        }catch(Exception ex){
            System.out.println("Erro: insertItem."+ex.toString());
            return "Não foi possivel cadastrar NotaFiscalItem!";
        }
    }

    public String update(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("UPDATE TBL_NOTA_ITEM SET "
                    + " QTDCX = '"+ getQtdcx() +"',"
                    + " QTDPROD = '"+ getQtdprod() +"',"
                    + " PESO = '"+ getPeso() +"'"
                    + " WHERE IDNOTAITEM = '"+getIdnotaitem()+"' ");
            return null;
        }catch(Exception ex){
            System.out.println("Error: Item.update."+ex.toString());
            return "Não foi possivel alterar Item!";
        }
    }

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_NOTA_ITEM WHERE IDNOTAITEM = '"+ idnotaitem +"'");
            return null;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return "Não foi possivel excluir NotaFiscalItem!";
        }
    }
    public String deleteAll(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_NOTA_ITEM WHERE IDNOTA = '"+ idnota +"'");
            return null;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return "Não foi possivel excluir NotaFiscalItem!";
        }
    }

    public ObservableList<NotaFiscalItem> getLista(int idnota){
        ObservableList<NotaFiscalItem> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT ITEM.*,P.DESCRICAO,P.CODFABRICA,P.PESO_VARIAVEL,P.QTDUNDCX FROM TBL_NOTA_ITEM AS ITEM INNER JOIN TBL_PRODUTO AS P ON P.IDPRODUTO = ITEM.IDPRODUTO WHERE IDNOTA = '"+idnota+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new NotaFiscalItem(sql.rs.getInt("IDNOTAITEM"),sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getDouble("QTDPROD_CORTE"),sql.rs.getInt("QTDCX_CORTE"),sql.rs.getDouble("PESO_CORTE"),sql.rs.getDouble("PRUNIT"),sql.rs.getDouble("PRUNITLIQ"),sql.rs.getDouble("VALORLIQ"),sql.rs.getDouble("DESCONTO"),sql.rs.getDouble("VALORBRUTO"),sql.rs.getString("NCM"),sql.rs.getString("CST"),sql.rs.getInt("IDCFOP"),sql.rs.getString("OBS"),sql.rs.getBoolean("PESO_VARIAVEL"),sql.rs.getDouble("QTDUNDCX")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return null;
        }
    }

    public ObservableList<NotaFiscalItem> getListaSintetica(int idcarga){
        ObservableList<NotaFiscalItem> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM VW_NOTA_ITEM WHERE IDCARGA = '"+idcarga+"' ORDER BY CODFABRICA ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new NotaFiscalItem(sql.rs.getInt("IDNOTAITEM"),sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getDouble("QTDPROD_CORTE"),sql.rs.getInt("QTDCX_CORTE"),sql.rs.getDouble("PESO_CORTE"),sql.rs.getDouble("PRUNIT"),sql.rs.getDouble("PRUNITLIQ"),sql.rs.getDouble("VALORLIQ"),sql.rs.getDouble("DESCONTO"),sql.rs.getDouble("VALORBRUTO"),sql.rs.getString("NCM"),sql.rs.getString("CST"),sql.rs.getInt("IDCFOP"),sql.rs.getString("OBS"),null,null));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: NotaItem.getListaSintetica."+ex.toString());
            return null;
        }
    }

}




