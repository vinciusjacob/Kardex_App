package br.com.app.kardex.model.expedicao;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Corte {

    private int idcarga;
    private String carga;
    private String placa;
    private String numauxiliar;
    private int idproduto;
    private String codfabrica;
    private String descricao;
    private String unidade;
    private int qtdcxcorte;
    private String obs;

    public int getIdcarga() {
        return idcarga;
    }

    public void setIdcarga(int idcarga) {
        this.idcarga = idcarga;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumauxiliar() {
        return numauxiliar;
    }

    public void setNumauxiliar(String numauxiliar) {
        this.numauxiliar = numauxiliar;
    }

    public int getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }

    public String getCodfabrica() {
        return codfabrica;
    }

    public void setCodfabrica(String codfabrica) {
        this.codfabrica = codfabrica;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getQtdcxcorte() {
        return qtdcxcorte;
    }

    public void setQtdcxcorte(int qtdcxcorte) {
        this.qtdcxcorte = qtdcxcorte;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    public Corte(){

    }
    public Corte(int idcarga,String carga, String placa,String numauxiliar,int idproduto,String codfabrica,String descricao, String unidade, int qtdcxcorte, String obs) {
        this.idcarga = idcarga;
        this.carga = carga;
        this.placa = placa;
        this.numauxiliar = numauxiliar;
        this.idproduto = idproduto;
        this.codfabrica = codfabrica;
        this.descricao = descricao;
        this.unidade = unidade;
        this.qtdcxcorte = qtdcxcorte;
        this.obs = obs;
    }

    public ObservableList<Corte> getLista(String dtcarga){
        ObservableList<Corte> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM VW_CORTE WHERE DTCARGA = '"+dtcarga+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Corte(sql.rs.getInt("IDCARGA"),sql.rs.getString("CARGA"),sql.rs.getString("PLACA"),sql.rs.getString("NUMAUXILIAR"),sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("UNIDADE"),sql.rs.getInt("QTDCX_CORTE"),sql.rs.getString("OBS")));
            }
            sql.desconecta();
            return lista;
        }catch (Exception ex){
            System.out.println("Error: Corte.getLista."+ex.toString());
            return null;
        }
    }
}
