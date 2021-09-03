package br.com.app.kardex.model.kardex;

import br.com.app.kardex.util.SqlServer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NotaFiscal {
    private int idnota;
    private int idcarga;
    private int tipo;
    private int numero;
    private int numpedido;
    private String numauxiliar;
    private String serie;
    private String modelo;
    private String dtemissao;
    private String dtsaida;
    private String dtlancamento;
    private String chave;
    private int idparceiro;
    private int idempresa;
    private String obs;
    private String cnpj_cpf_destinatario;
    private String cnpj_cpf_emitente;
    private String nfreferencia;
    private Boolean faturado;
    private int iddepartamento;
    private ObservableList<NotaFiscalItem> listaItem;
    private IntegerProperty volumetotal;
    private DoubleProperty pesototal;

    public int getIdnota(){return idnota; }
    public void  setIdnota(int idnota){this.idnota = idnota; }
    public int getIdcarga(){return idcarga; }
    public void  setIdcarga(int idcarga){this.idcarga = idcarga; }
    public int getTipo(){return tipo; }
    public void  setTipo(int tipo){this.tipo = tipo; }
    public int getNumero(){return numero; }
    public void  setNumero(int numero){this.numero = numero; }
    public int getNumpedido(){return numpedido; }
    public void  setNumpedido(int numpedido){this.numpedido = numpedido; }
    public String getNumauxiliar(){return numauxiliar;}
    public void setNumauxiliar(String numauxiliar){this.numauxiliar = numauxiliar;}
    public String getSerie(){return serie; }
    public void  setSerie(String serie){this.serie = serie; }
    public String getModelo(){return modelo; }
    public void  setModelo(String modelo){this.modelo = modelo; }
    public String getDtemissao(){return dtemissao; }
    public void  setDtemissao(String dtemissao){this.dtemissao = dtemissao; }
    public String getDtsaida(){return dtsaida; }
    public void  setDtsaida(String dtsaida){this.dtsaida = dtsaida; }
    public String getDtlancamento(){return dtlancamento; }
    public void  setDtlancamento(String dtlancamento){this.dtlancamento = dtlancamento; }
    public String getChave(){return chave; }
    public void  setChave(String chave){this.chave = chave; }
    public int getIdparceiro(){return idparceiro; }
    public void  setIdparceiro(int idparceiro){this.idparceiro = idparceiro; }
    public int getIdempresa(){return idempresa; }
    public void  setIdempresa(int idempresa){this.idempresa = idempresa; }
    public String getObs(){return obs; }
    public void  setObs(String obs){this.obs = obs; }
    public String getCnpj_cpf_destinatario(){return cnpj_cpf_destinatario; }
    public void  setCnpj_cpf_destinatario(String cnpj_cpf_destinatario){this.cnpj_cpf_destinatario = cnpj_cpf_destinatario; }
    public String getCnpj_cpf_emitente(){return cnpj_cpf_emitente; }
    public void  setCnpj_cpf_emitente(String cnpj_cpf_emitente){this.cnpj_cpf_emitente = cnpj_cpf_emitente; }
    public String getNfreferencia(){return nfreferencia; }
    public void  setNfreferencia(String nfreferencia){this.nfreferencia = nfreferencia; }
    public Boolean getFaturado(){return faturado; }
    public void  setFaturado(Boolean faturado){this.faturado = faturado; }
    public int getIddepartamento(){return iddepartamento; }
    public void  setIddepartamento(int iddepartamento){this.iddepartamento = iddepartamento; }


    public ObservableList<NotaFiscalItem> getListaItem() {
        return listaItem;
    }

    public void setListaItem(ObservableList<NotaFiscalItem> listaItem) {
        this.listaItem = listaItem;
    }

    public NotaFiscal(){
        this.volumetotal = new SimpleIntegerProperty(0);
        this.pesototal = new SimpleDoubleProperty(0.0);
    }
    public NotaFiscal(int idnota, int idcarga, int tipo, int numero, int numpedido, String numauxiliar, String serie, String modelo, String dtemissao, String dtsaida, String dtlancamento, String chave, int idparceiro, int idempresa, String obs, String cnpj_cpf_destinatario, String cnpj_cpf_emitente, String nfreferencia, Boolean faturado, int iddepartamento,ObservableList<NotaFiscalItem> listaItem){
        this.idnota = idnota;
        this.idcarga = idcarga;
        this.tipo = tipo;
        this.numero = numero;
        this.numpedido = numpedido;
        this.numauxiliar = numauxiliar;
        this.serie = serie;
        this.modelo = modelo;
        this.dtemissao = dtemissao;
        this.dtsaida = dtsaida;
        this.dtlancamento = dtlancamento;
        this.chave = chave;
        this.idparceiro = idparceiro;
        this.idempresa = idempresa;
        this.obs = obs;
        this.cnpj_cpf_destinatario = cnpj_cpf_destinatario;
        this.cnpj_cpf_emitente = cnpj_cpf_emitente;
        this.nfreferencia = nfreferencia;
        this.faturado = faturado;
        this.iddepartamento = iddepartamento;
        this.listaItem = listaItem;
        this.volumetotal = new SimpleIntegerProperty(0);
        this.pesototal = new SimpleDoubleProperty(0.0);
        setVolumetotal(0);
        setPesototal(0.0);
    }

    public ObservableList<NotaFiscal> getLista(){
        ObservableList<NotaFiscal> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_NOTA ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new NotaFiscal(sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDCARGA"),sql.rs.getInt("TIPO"),sql.rs.getInt("NUMERO"),sql.rs.getInt("NUMPEDIDO"),sql.rs.getString("NUMAUXILIAR"),sql.rs.getString("SERIE"),sql.rs.getString("MODELO"),sql.rs.getString("DTEMISSAO"),sql.rs.getString("DTSAIDA"),sql.rs.getString("DTLANCAMENTO"),sql.rs.getString("CHAVE"),sql.rs.getInt("IDPARCEIRO"),sql.rs.getInt("IDEMPRESA"),sql.rs.getString("OBS"),sql.rs.getString("CNPJ_CPF_DESTINATARIO"),sql.rs.getString("CNPJ_CPF_EMITENTE"),sql.rs.getString("NFREFERENCIA"),sql.rs.getBoolean("FATURADO"),sql.rs.getInt("IDDEPARTAMENTO"),null));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return null;
        }
    }

    public NotaFiscal get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_NOTA WHERE IDNOTA = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new NotaFiscal(sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDCARGA"),sql.rs.getInt("TIPO"),sql.rs.getInt("NUMERO"),sql.rs.getInt("NUMPEDIDO"),sql.rs.getString("NUMAUXILIAR"),sql.rs.getString("SERIE"),sql.rs.getString("MODELO"),sql.rs.getString("DTEMISSAO"),sql.rs.getString("DTSAIDA"),sql.rs.getString("DTLANCAMENTO"),sql.rs.getString("CHAVE"),sql.rs.getInt("IDPARCEIRO"),sql.rs.getInt("IDEMPRESA"),sql.rs.getString("OBS"),sql.rs.getString("CNPJ_CPF_DESTINATARIO"),sql.rs.getString("CNPJ_CPF_EMITENTE"),sql.rs.getString("NFREFERENCIA"),sql.rs.getBoolean("FATURADO"),sql.rs.getInt("IDDEPARTAMENTO"),null);
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return null;
        }
    }

    public int getVolumetotal(){
        return volumetotal.get();
    }
    public void setVolumetotal(int volumeTotal){
        int volume = 0;
        for(NotaFiscalItem item : listaItem){
            volume = volume + item.getQtdcx();
        }
        this.volumetotal.set(volume);

    }
    public IntegerProperty volumetotalProperty(){
        return volumetotal;
    }
    public Double getPesototal(){
        return this.pesototal.get();
    }
    private void setPesototal(Double pesoTotal){
        Double peso = 0.0;
        for(NotaFiscalItem item : listaItem){
            peso = peso + item.getPeso();
        }
        this.pesototal.set(peso);
    }
    public DoubleProperty pesototalProperty(){
        return pesototal;
    }


    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            idnota = sql.SqlUpdateId("INSERT INTO TBL_NOTA (IDCARGA,TIPO,NUMERO,NUMAUXILIAR,SERIE,MODELO,DTEMISSAO,DTSAIDA,DTLANCAMENTO,CHAVE,IDPARCEIRO,IDEMPRESA,OBS,CNPJ_CPF_DESTINATARIO,CNPJ_CPF_EMITENTE,NFREFERENCIA,FATURADO,IDDEPARTAMENTO) VALUES ("
                    + "'"+ idcarga +"',"
                    + "'"+ tipo +"',"
                    + "'"+ numero +"',"
                    + "'"+ numauxiliar +"',"
                    + "'"+ serie +"',"
                    + "'"+ modelo +"',"
                    + "'"+ dtemissao +"',"
                    + "'"+ dtsaida +"',"
                    + "GETDATE(),"
                    + "'"+ chave +"',"
                    + "'"+ idparceiro +"',"
                    + "'"+ idempresa +"',"
                    + "'"+ obs +"',"
                    + "'"+ cnpj_cpf_destinatario +"',"
                    + "'"+ cnpj_cpf_emitente +"',"
                    + "'"+ nfreferencia +"',"
                    + "'"+ faturado +"',"
                    + "'"+ iddepartamento +"')" );
            return null;
        }catch(Exception ex){
            System.out.println("Error: insertNota."+ex.toString());
            return "Não foi possivel cadastrar NotaFiscal!";
        }
    }

    public String update(){return null;}

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_NOTA WHERE IDNOTA = '"+ idnota +"'");
            return null;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return "Não foi possivel excluir NotaFiscal!";
        }
    }

    /**
     * Verifica se existe pedido em nfs de determinado departamento
     * @param numpedido
     * @param iddepartamento
     * @return
     */
    public Boolean isExiste(String numpedido,int iddepartamento){
        try{
            SqlServer sql = new SqlServer();
            int rows = sql.SqlNumRows("SELECT * FROM TBL_NOTA WHERE NUMPEDIDO = '"+numpedido+"' AND IDDEPARTAMENTO = '"+iddepartamento+"' ");
            if(rows > 0){
                return true;
            }else{
                return false;
            }
        }catch (Exception ex){
            System.out.println("Falha ao consultar Nota");
            return false;
        }
    }

    /**
     * RETORNA LISTA DE NOTAS DE UM DETERMINADO DEPARTAMENTO
     * @param iddepartamento
     * @return LISTA DE NOTAS
     */
    public ObservableList<NotaFiscal> getLista(int iddepartamento,int tipo){
        ObservableList<NotaFiscal> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_NOTA WHERE IDDEPARTAMENTO = '"+iddepartamento+"' AND TIPO = '"+tipo+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new NotaFiscal(sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDCARGA"),sql.rs.getInt("TIPO"),sql.rs.getInt("NUMERO"),sql.rs.getInt("NUMPEDIDO"),sql.rs.getString("NUMAUXILIAR"),sql.rs.getString("SERIE"),sql.rs.getString("MODELO"),sql.rs.getString("DTEMISSAO"),sql.rs.getString("DTSAIDA"),sql.rs.getString("DTLANCAMENTO"),sql.rs.getString("CHAVE"),sql.rs.getInt("IDPARCEIRO"),sql.rs.getInt("IDEMPRESA"),sql.rs.getString("OBS"),sql.rs.getString("CNPJ_CPF_DESTINATARIO"),sql.rs.getString("CNPJ_CPF_EMITENTE"),sql.rs.getString("NFREFERENCIA"),sql.rs.getBoolean("FATURADO"),sql.rs.getInt("IDDEPARTAMENTO"),null));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return null;
        }
    }

    public ObservableList<NotaFiscal> getLista(int idcarga){
        ObservableList<NotaFiscal> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_NOTA WHERE IDCARGA = '"+idcarga+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                ObservableList<NotaFiscalItem> listaItem = new NotaFiscalItem().getLista(sql.rs.getInt("IDNOTA"));
                lista.add(new NotaFiscal(sql.rs.getInt("IDNOTA"),sql.rs.getInt("IDCARGA"),sql.rs.getInt("TIPO"),sql.rs.getInt("NUMERO"),sql.rs.getInt("NUMPEDIDO"),sql.rs.getString("NUMAUXILIAR"),sql.rs.getString("SERIE"),sql.rs.getString("MODELO"),sql.rs.getString("DTEMISSAO"),sql.rs.getString("DTSAIDA"),sql.rs.getString("DTLANCAMENTO"),sql.rs.getString("CHAVE"),sql.rs.getInt("IDPARCEIRO"),sql.rs.getInt("IDEMPRESA"),sql.rs.getString("OBS"),sql.rs.getString("CNPJ_CPF_DESTINATARIO"),sql.rs.getString("CNPJ_CPF_EMITENTE"),sql.rs.getString("NFREFERENCIA"),sql.rs.getBoolean("FATURADO"),sql.rs.getInt("IDDEPARTAMENTO"),listaItem));
            }
            sql.desconecta();

            return lista;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return null;
        }
    }

}



