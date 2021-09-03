package br.com.app.kardex.model.kardex;

import br.com.app.kardex.model.cadastros.Produto;
import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NotaFiscalItemLote {
    private int idlote;
    private int idnotaitem;
    private int idcarga;
    private int idnota;
    private int idproduto;
    private int idestoque;
    private String codauxiliar;
    private String codfabrica;
    private String descricao;
    private String unidade;
    private String num_lote;
    private String dtfabricacao;
    private String dtvencimento;
    private Double qtdprod;
    private int qtdcx;
    private Double peso;
    private Boolean falta;
    private Boolean sobra;
    private Boolean avariado;
    private String obs;
    private int tipo;
    private String desccarga;
    private String placa;
    private String numauxiliar;
    private Double qtd_falta;
    private Double qtd_sobra;
    private String ocorrencia;
    private Produto produto;

    public String getUnidade() {
        return unidade;
    }
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    public Double getQtd_falta() {
        return qtd_falta;
    }
    public void setQtd_falta(Double qtd_falta) {
        this.qtd_falta = qtd_falta;
    }
    public Double getQtd_sobra() {
        return qtd_sobra;
    }
    public void setQtd_sobra(Double qtd_sobra) {
        this.qtd_sobra = qtd_sobra;
    }
    public String getOcorrencia() {
        return ocorrencia;
    }
    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }
    public int getIdlote(){return idlote; }
    public void  setIdlote(int idlote){this.idlote = idlote; }
    public int getIdnotaitem() {
        return idnotaitem;
    }
    public void setIdnotaitem(int idnotaitem) {
        this.idnotaitem = idnotaitem;
    }
    public int getIdcarga(){return idcarga;}
    public void setIdcarga(int idcarga){this.idcarga = idcarga;}
    public int getIdnota(){return idnota; }
    public void  setIdnota(int idnota){this.idnota = idnota; }
    public int getIdproduto(){return idproduto; }
    public void  setIdproduto(int idproduto){this.idproduto = idproduto; }
    public int getIdestoque(){return idestoque;}
    public void setIdestoque(int idestoque){this.idestoque = idestoque;}
    public String getCodauxiliar(){return codauxiliar;}
    public void setCodauxiliar(String codauxiliar){this.codauxiliar = codauxiliar;}
    public String getDescricao(){return descricao; }
    public void  setDescricao(String descricao){this.descricao = descricao; }
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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Boolean getFalta(){return falta; }
    public void  setFalta(Boolean falta){this.falta = falta; }
    public Boolean getSobra(){return sobra; }
    public void  setSobra(Boolean sobra){this.sobra = sobra; }
    public Boolean getAvariado(){return avariado; }
    public void  setAvariado(Boolean avariado){this.avariado = avariado; }
    public String getObs(){return obs; }
    public void  setObs(String obs){this.obs = obs; }
    public int getTipo(){return tipo;}
    public void setTipo(int tipo){this.tipo = tipo;}
    public String getStatus() {
        if (falta)
            return "Falta";
        if (sobra)
            return "Sobra";
        else
            return "Ok";
    }
    public void setStatus(String status){}
    public String getAvariadoDesc(){
        if(avariado){
            return "Sim";
        }else{
            return "Não";
        }
    }
    public void setAvariadoDesc(){ }
    public String getDesccarga(){return desccarga;}
    public void setDesccarga(String desccarga){this.desccarga = desccarga;}
    public String getPlaca(){return placa;}
    public void setPlaca(String placa){this.placa = placa;}
    public String getNumauxiliar(){return numauxiliar;}
    public void setNumauxiliar(String numauxiliar){this.numauxiliar = numauxiliar;}
    public String getCodfabrica() {
        return codfabrica;
    }
    public void setCodfabrica(String codfabrica) {
        this.codfabrica = codfabrica;
    }
    public Produto getProduto(){return produto;}
    public void setProduto(Produto produto){this.produto = produto;}

    public NotaFiscalItemLote(){ }

    public NotaFiscalItemLote(int idlote,int idnotaitem, int idcarga, int idnota, int idproduto, String codfabrica, int idestoque, String descricao, String num_lote, String dtfabricacao, String dtvencimento, Double qtdprod, int qtdcx, Double peso, Boolean falta, Boolean sobra, Boolean avariado, String obs,int tipo,String placa,String desccarga,String numauxiliar, Produto produto){
        this.idlote = idlote;
        this.idnotaitem = idnotaitem;
        this.idcarga = idcarga;
        this.idnota = idnota;
        this.idproduto = idproduto;
        this.codfabrica = codfabrica;
        this.idestoque = idestoque;
        this.descricao = descricao;
        this.num_lote = num_lote;
        this.dtfabricacao = dtfabricacao;
        this.dtvencimento = dtvencimento;
        this.qtdprod = qtdprod;
        this.qtdcx = qtdcx;
        this.peso = peso;
        this.falta = falta;
        this.sobra = sobra;
        this.avariado = avariado;
        this.obs = obs;
        this.tipo = tipo;
        this.placa = placa;
        this.desccarga = desccarga;
        this.numauxiliar = numauxiliar;
        this.qtd_falta = null;
        this.qtd_sobra = null;
        this.ocorrencia = null;
        this.unidade = null;
        this.produto = produto;
    }
    //Lista sintetica
    public NotaFiscalItemLote(int idlote,int idnotaitem, int idcarga, int idnota, int idproduto, String codfabrica, int idestoque, String descricao, String num_lote, String dtfabricacao, String dtvencimento, Double qtdprod, int qtdcx,Double peso, Boolean falta, Boolean sobra, Boolean avariado, String obs,int tipo,String placa,String desccarga,String numauxiliar,Double qtd_falta,Double qtd_sobra,String ocorrencia,String unidade){
        this.idlote = idlote;
        this.idnotaitem = idnotaitem;
        this.idcarga = idcarga;
        this.idnota = idnota;
        this.idproduto = idproduto;
        this.codfabrica = codfabrica;
        this.idestoque = idestoque;
        this.descricao = descricao;
        this.num_lote = num_lote;
        this.dtfabricacao = dtfabricacao;
        this.dtvencimento = dtvencimento;
        this.qtdprod = qtdprod;
        this.qtdcx = qtdcx;
        this.peso = peso;
        this.falta = falta;
        this.sobra = sobra;
        this.avariado = avariado;
        this.obs = obs;
        this.tipo = tipo;
        this.placa = placa;
        this.desccarga = desccarga;
        this.numauxiliar = numauxiliar;
        this.qtd_falta = qtd_falta;
        this.qtd_sobra = qtd_sobra;
        this.ocorrencia = ocorrencia;
        this.unidade = unidade;
        this.produto = null;
    }

    public ObservableList<NotaFiscalItemLote> getLista(){
        ObservableList<NotaFiscalItemLote> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT L.*,DESCRICAO,P.CODFABRICA FROM TBL_NOTA_ITEM_LOTE AS L INNER JOIN TBL_PRODUTO AS P ON P.IDPRODUTO = L.IDPRODUTO";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                Produto newProduto = new Produto().get(sql.rs.getInt("IDPRODUTO"));
                lista.add(new NotaFiscalItemLote(sql.rs.getInt("IDLOTE"),sql.rs.getInt("IDNOTAITEM"),sql.rs.getInt("IDCARGA"),sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDPRODUTO"),null,sql.rs.getInt("IDESTOQUE"),sql.rs.getString("DESCRICAO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("FALTA"),sql.rs.getBoolean("SOBRA"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS"),sql.rs.getInt("TIPO"),null,null,null,newProduto));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: Lote.getLista."+ex.toString());
            return null;
        }
    }

    public ObservableList<NotaFiscalItemLote> getLista(int idcarga){
        ObservableList<NotaFiscalItemLote> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT L.*,P.DESCRICAO,P.CODFABRICA FROM TBL_NOTA_ITEM_LOTE AS L INNER JOIN TBL_PRODUTO AS P ON P.IDPRODUTO = L.IDPRODUTO WHERE IDCARGA = '"+idcarga+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                Produto newProduto = new Produto().get(sql.rs.getInt("IDPRODUTO"));
                lista.add(new NotaFiscalItemLote(sql.rs.getInt("IDLOTE"),sql.rs.getInt("IDNOTAITEM"),sql.rs.getInt("IDCARGA"),sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getInt("IDESTOQUE"),sql.rs.getString("DESCRICAO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("FALTA"),sql.rs.getBoolean("SOBRA"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS"),sql.rs.getInt("TIPO"),null,null,null,newProduto));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: Lote.getLista."+ex.toString());
            return null;
        }
    }

    public ObservableList<NotaFiscalItemLote> getListaSintetica(int idcarga){
        ObservableList<NotaFiscalItemLote> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT *,CAST(CODFABRICA AS INT) OR2 FROM VW_NOTA_ITEM_LOTE WHERE IDCARGA = '"+idcarga+"' ORDER BY NUMAUXILIAR,OR2";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new NotaFiscalItemLote(sql.rs.getInt("IDLOTE"),sql.rs.getInt("IDNOTAITEM"),sql.rs.getInt("IDCARGA"),sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getInt("IDESTOQUE"),sql.rs.getString("DESCRICAO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("FALTA"),sql.rs.getBoolean("SOBRA"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS"),sql.rs.getInt("TIPO"),sql.rs.getString("PLACA"),sql.rs.getString("DESCCARGA"),sql.rs.getString("NUMAUXILIAR"),sql.rs.getDouble("QTD_FALTA"),sql.rs.getDouble("QTD_SOBRA"),sql.rs.getString("OCORRENCIA"),sql.rs.getString("UNIDADE")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return null;
        }
    }

    public ObservableList<NotaFiscalItemLote> getListaSinteticaDivergencia(int idcarga){
        ObservableList<NotaFiscalItemLote> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT *,CAST(CODFABRICA AS INT) OR2 FROM VW_NOTA_ITEM_LOTE WHERE IDCARGA = '"+idcarga+"' AND (QTD_FALTA <> 0 OR QTD_SOBRA <> 0) ORDER BY NUMAUXILIAR,OR2";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new NotaFiscalItemLote(sql.rs.getInt("IDLOTE"),sql.rs.getInt("IDNOTAITEM"),sql.rs.getInt("IDCARGA"),sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getInt("IDESTOQUE"),sql.rs.getString("DESCRICAO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("FALTA"),sql.rs.getBoolean("SOBRA"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS"),sql.rs.getInt("TIPO"),sql.rs.getString("PLACA"),sql.rs.getString("DESCCARGA"),sql.rs.getString("NUMAUXILIAR"),sql.rs.getDouble("QTD_FALTA"),sql.rs.getDouble("QTD_SOBRA"),sql.rs.getString("OCORRENCIA"),sql.rs.getString("UNIDADE")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return null;
        }
    }

    public NotaFiscalItemLote get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT L.*,DESCRICAO FROM TBL_NOTA_ITEM_LOTE AS L INNER JOIN TBL_PRODUTO AS P ON P.IDPRODUTO = L.IDPRODUTO WHERE IDLOTE = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            Produto newProduto = new Produto().get(sql.rs.getInt("IDPRODUTO"));
            return new NotaFiscalItemLote(sql.rs.getInt("IDLOTE"),sql.rs.getInt("IDNOTAITEM"),sql.rs.getInt("IDCARGA"),sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDPRODUTO"),null,sql.rs.getInt("IDESTOQUE"),sql.rs.getString("DESCRICAO"),sql.rs.getString("NUM_LOTE"),sql.rs.getString("DTFABRICACAO"),sql.rs.getString("DTVENCIMENTO"),sql.rs.getDouble("QTDPROD"),sql.rs.getInt("QTDCX"),sql.rs.getDouble("PESO"),sql.rs.getBoolean("FALTA"),sql.rs.getBoolean("SOBRA"),sql.rs.getBoolean("AVARIADO"),sql.rs.getString("OBS"),sql.rs.getInt("TIPO"),null,null,null,newProduto);
        }catch(Exception ex){
            return null;
        }
    }

    public String insert(){
        try{

            SqlServer sql = new SqlServer();
            idlote = sql.SqlUpdateId("INSERT INTO TBL_NOTA_ITEM_LOTE (IDNOTA,IDNOTAITEM,IDCARGA,IDPRODUTO,IDESTOQUE,NUM_LOTE,DTFABRICACAO,DTVENCIMENTO,QTDPROD,QTDCX,FALTA,SOBRA,AVARIADO,OBS,TIPO,PESO) VALUES ("
                    + "'"+ idnota +"',"
                    + "'"+ idnotaitem +"',"
                    + "'"+ idcarga +"',"
                    + "'"+ idproduto +"',"
                    + "'"+ idestoque +"',"
                    + "'"+ num_lote +"',"
                    + "'"+ dtfabricacao +"',"
                    + "'"+ dtvencimento +"',"
                    + "'"+ qtdprod +"',"
                    + "'"+ qtdcx +"',"
                    + "'"+ falta +"',"
                    + "'"+ sobra +"',"
                    + "'"+ avariado +"',"
                    + "'"+ obs +"',"
                    + "'"+ tipo +"',"
                    + "'"+ peso +"')" );
            return null;
        }catch(Exception ex){
            System.out.println("Error: Lote.insert."+ex.toString());
            return "Não foi possivel cadastrar NotaFiscalItemLote!";
        }
    }

    public String update(){return null;}

    public String delete2(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_NOTA_ITEM_LOTE WHERE IDCARGA = '"+ idcarga +"' AND IDPRODUTO = '"+ idproduto +"' ");
            return null;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return "Não foi possivel excluir NotaFiscalItemLote!";
        }
    }

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_NOTA_ITEM_LOTE WHERE IDCARGA = '"+ idcarga +"' AND IDPRODUTO = '"+ idproduto +"' ");
            return null;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return "Não foi possivel excluir NotaFiscalItemLote!";
        }
    }

    public String deleteoOne(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_NOTA_ITEM_LOTE WHERE IDLOTE = '"+ idlote +"' ");
            return null;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return "Não foi possivel excluir NotaFiscalItemLote!";
        }
    }

    public String deleteFaltas(int idcarga, int idproduto){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_NOTA_ITEM_LOTE WHERE IDCARGA = '"+ idcarga +"' AND FALTA = 1 AND IDPRODUTO = '"+idproduto+"' ");
            return null;
        }catch(Exception ex){
            System.out.println("Error: Lote.deleteFaltas."+ex.toString());
            return "Não foi possivel excluir NotaFiscalItemLote!";
        }
    }

    public Double getSumQtdProd(int idcarga,int idproduto){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT SUM(QTDPROD)AS QTDPROD FROM TBL_NOTA_ITEM_LOTE WHERE IDCARGA = '"+idcarga+"' AND IDPRODUTO = '"+idproduto+"' ";
            sql.SqlQuery(query);
            sql.rs.next();
            return sql.rs.getDouble("QTDPROD");
        }catch(Exception ex){
            return null;
        }
    }

    public int getSumQtdCx(int idcarga,int idproduto,int idnota){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT SUM(QTDCX)AS QTDCX FROM TBL_NOTA_ITEM_LOTE WHERE IDCARGA = '"+idcarga+"' AND IDPRODUTO = '"+idproduto+"' AND IDNOTA = '"+idnota+"' ";
            sql.SqlQuery(query);
            sql.rs.next();
            return sql.rs.getInt("QTDCX");
        }catch(Exception ex){
            return 0;
        }
    }
}


