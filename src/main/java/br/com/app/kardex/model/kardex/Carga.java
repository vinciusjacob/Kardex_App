package br.com.app.kardex.model.kardex;

import br.com.app.kardex.util.SqlServer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Carga {
    private int idcarga;
    private int tipo;
    private StringProperty descricao;
    private StringProperty transportadora;
    private StringProperty motorista;
    private StringProperty placa;
    private int idusuario;
    private int idconferente;
    private int idempresa;
    private int iddepartamento;
    private Boolean encerrada;
    private int doca;
    private String num_lacre;
    private String status;
    private int qtdpallet;
    private Double temperaturabau;
    private Double pesoinicial;
    private Double pesofinal;
    private String dtcarga;
    private String dtlancamento;
    private String dtmovimento;
    private String dtinicio;
    private String dtfim;
    private String dtreconferencia;
    private int volume;
    private int volume_corte;
    private ObservableList<NotaFiscal> listaNota;
    private ObservableList<NotaFiscalItemLote> listaLote;
    private ObservableList<NotaFiscalItemLote> listaLoteSintetica;
    private StringProperty verifica;


    public ObservableList<NotaFiscalItemLote> getListaLoteSintetica() {
        return listaLoteSintetica;
    }

    public void setListaLoteSintetica(ObservableList<NotaFiscalItemLote> listaLoteSintetica) {
        this.listaLoteSintetica = listaLoteSintetica;
    }

    public StringProperty verificaProperty(){
        return verifica;
    }
    public StringProperty placaProperty(){return placa;}
    public StringProperty descricaoProperty(){return descricao;}
    public StringProperty motoristaProperty(){return motorista;}
    public StringProperty transportadoraProperty(){return transportadora;}

    public String getVerifica() {
        return verifica.get();
    }
    public void setVerifica(String verifica) {
        this.verifica.set(verifica);
    }
    public int getIdcarga(){return idcarga; }
    public void  setIdcarga(int idcarga){this.idcarga = idcarga; }
    public int getTipo(){return tipo; }
    public void  setTipo(int tipo){this.tipo = tipo; }
    public String getDescricao(){return descricao.get(); }
    public void  setDescricao(String descricao){this.descricao.set(descricao); }
    public String getTransportadora(){return transportadora.get(); }
    public void  setTransportadora(String transportadora){this.transportadora.set(transportadora); }
    public String getMotorista(){return motorista.get(); }
    public void  setMotorista(String motorista){this.motorista.set(motorista); }
    public String getPlaca(){return placa.get(); }
    public void  setPlaca(String placa){this.placa.set(placa); }
    public int getIdusuario(){return idusuario; }
    public void  setIdusuario(int idusuario){this.idusuario = idusuario; }
    public int getIdconferente(){return idconferente; }
    public void  setIdconferente(int idconferente){this.idconferente = idconferente; }
    public int getIdempresa(){return idempresa; }
    public void  setIdempresa(int idempresa){this.idempresa = idempresa; }
    public int getIddepartamento(){return iddepartamento; }
    public void  setIddepartamento(int iddepartamento){this.iddepartamento = iddepartamento; }
    public Boolean getEncerrada(){return encerrada; }
    public void  setEncerrada(Boolean encerrada){this.encerrada = encerrada;}
    public int getDoca(){return doca; }
    public void  setDoca(int doca){this.doca = doca; }
    public String getNum_lacre(){return num_lacre; }
    public void  setNum_lacre(String num_lacre){this.num_lacre = num_lacre; }
    public String getStatus(){return status;}
    public void  setStatus(String status){this.status = status; }
    public int getQtdpallet(){return qtdpallet; }
    public void  setQtdpallet(int qtdpallet){this.qtdpallet = qtdpallet; }
    public Double getTemperaturabau(){return temperaturabau; }
    public void  setTemperaturabau(Double temperaturabau){this.temperaturabau = temperaturabau; }
    public Double getPesoinicial(){return pesoinicial; }
    public void  setPesoinicial(Double pesoinicial){this.pesoinicial = pesoinicial; }
    public Double getPesofinal(){return pesofinal; }
    public void  setPesofinal(Double pesofinal){this.pesofinal = pesofinal; }
    public String getDtcarga(){
        if(dtcarga == null){return null;}
        LocalDate date = LocalDate.parse(dtcarga);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formato);
    }
    public void  setDtcarga(String dtcarga){this.dtcarga = dtcarga; }
    public String getDtlancamento(){
        if(dtlancamento == null){return null;}
        LocalDateTime date = LocalDateTime.parse(dtlancamento.substring(0,19),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return date.format(formato);
    }
    public void  setDtlancamento(String dtlancamento){this.dtlancamento = dtlancamento; }
    public String getDtmovimento(){return dtmovimento; }
    public void  setDtmovimento(String dtmovimento){this.dtmovimento = dtmovimento; }
    public String getDtinicio(){
        if(dtinicio == null){return null;}
        LocalDateTime date = LocalDateTime.parse(dtinicio.substring(0,19),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return date.format(formato);
    }
    private String getDtinicioIn(){
        if(dtinicio == null || dtinicio.equals("GETDATE()")){
            return dtinicio;
        }else{
            return "'"+getDtinicio()+"'";
        }
    }
    public void  setDtinicio(String dtinicio){this.dtinicio = dtinicio; }
    public String getDtfim(){
        if(dtfim == null){return null;}
        LocalDateTime date = LocalDateTime.parse(dtfim.substring(0,19),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return date.format(formato);
    }
    private String getDtfimIn(){
        if(dtfim == null || dtfim.equals("GETDATE()")){
            return dtfim;
        }else{
            return "'"+getDtfim()+"'";
        }
    }
    public void  setDtfim(String dtfim){this.dtfim = dtfim; }
    public String getDtreconferencia(){return dtreconferencia;}
    public void setDtreconferencia(String dtreconferencia){this.dtreconferencia = dtreconferencia;}
    public String getEncerradaDesc(){
        if(encerrada){
            return "Sim";
        }else {
            return "Não";
        }
    }
    public void setEncerradaDesc(){

    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVolume_corte() {
        return volume_corte;
    }

    public void setVolume_corte(int volume_corte) {
        this.volume_corte = volume_corte;
    }

    public ObservableList<NotaFiscal> getListaNota() {
        return listaNota;
    }

    public void setListaNota(ObservableList<NotaFiscal> listaNota) {
        this.listaNota = listaNota;
    }

    public ObservableList<NotaFiscalItemLote> getListaLote() {
        return listaLote;
    }

    public void setListaLote(ObservableList<NotaFiscalItemLote> listaLote) {
        this.listaLote = listaLote;
    }

    public Carga(){
        this.verifica = new SimpleStringProperty("...");
        this.placa = new SimpleStringProperty("");
        this.descricao = new SimpleStringProperty("");
        this.transportadora = new SimpleStringProperty("");
        this.motorista = new SimpleStringProperty("");
    }
    public Carga(int idcarga, int tipo, String descricao, String transportadora, String motorista, String placa, int idusuario, int idconferente, int idempresa, int iddepartamento, Boolean encerrada, int doca, String num_lacre, String status, int qtdpallet, Double temperaturabau, Double pesoinicial, Double pesofinal, String dtcarga, String dtlancamento, String dtmovimento, String dtinicio, String dtfim, String dtreconferencia,int volume, int volume_corte){
        this.idcarga = idcarga;
        this.tipo = tipo;
        this.descricao = new SimpleStringProperty(descricao);
        this.transportadora = new SimpleStringProperty(transportadora);
        this.motorista = new SimpleStringProperty(motorista);
        this.placa = new SimpleStringProperty(placa);
        this.idusuario = idusuario;
        this.idconferente = idconferente;
        this.idempresa = idempresa;
        this.iddepartamento = iddepartamento;
        this.encerrada = encerrada;
        this.doca = doca;
        this.num_lacre = num_lacre;
        this.status = status;
        this.qtdpallet = qtdpallet;
        this.temperaturabau = temperaturabau;
        this.pesoinicial = pesoinicial;
        this.pesofinal = pesofinal;
        this.dtcarga = dtcarga;
        this.dtlancamento = dtlancamento;
        this.dtmovimento = dtmovimento;
        this.dtinicio = dtinicio;
        this.dtfim = dtfim;
        this.dtreconferencia = dtreconferencia;
        this.volume = volume;
        this.volume_corte = volume_corte;
        this.verifica = new SimpleStringProperty("...");
    }

    public ObservableList<Carga> getLista(){
        ObservableList<Carga> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_CARGA ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Carga(sql.rs.getInt("IDCARGA"),sql.rs.getInt("TIPO"),sql.rs.getString("DESCRICAO"),sql.rs.getString("TRANSPORTADORA"),sql.rs.getString("MOTORISTA"),sql.rs.getString("PLACA"),sql.rs.getInt("IDUSUARIO"),sql.rs.getInt("IDCONFERENTE"),sql.rs.getInt("IDEMPRESA"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getBoolean("ENCERRADA"),sql.rs.getInt("DOCA"),sql.rs.getString("NUM_LACRE"),sql.rs.getString("STATUS"),sql.rs.getInt("QTDPALLET"),sql.rs.getDouble("TEMPERATURABAU"),sql.rs.getDouble("PESOINICIAL"),sql.rs.getDouble("PESOFINAL"),sql.rs.getString("DTCARGA"),sql.rs.getString("DTLANCAMENTO"),sql.rs.getString("DTMOVIMENTO"),sql.rs.getString("DTINICIO"),sql.rs.getString("DTFIM"),sql.rs.getString("DTRECONFERENCIA"),0,0));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public Carga get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_CARGA WHERE IDCARGA = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new Carga(sql.rs.getInt("IDCARGA"),sql.rs.getInt("TIPO"),sql.rs.getString("DESCRICAO"),sql.rs.getString("TRANSPORTADORA"),sql.rs.getString("MOTORISTA"),sql.rs.getString("PLACA"),sql.rs.getInt("IDUSUARIO"),sql.rs.getInt("IDCONFERENTE"),sql.rs.getInt("IDEMPRESA"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getBoolean("ENCERRADA"),sql.rs.getInt("DOCA"),sql.rs.getString("NUM_LACRE"),sql.rs.getString("STATUS"),sql.rs.getInt("QTDPALLET"),sql.rs.getDouble("TEMPERATURABAU"),sql.rs.getDouble("PESOINICIAL"),sql.rs.getDouble("PESOFINAL"),sql.rs.getString("DTCARGA"),sql.rs.getString("DTLANCAMENTO"),sql.rs.getString("DTMOVIMENTO"),sql.rs.getString("DTINICIO"),sql.rs.getString("DTFIM"),sql.rs.getString("DTRECONFERENCIA"),0,0);
        }catch(Exception ex){
            return null;
        }
    }

    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            idcarga = sql.SqlUpdateId("INSERT INTO TBL_CARGA (TIPO,DESCRICAO,TRANSPORTADORA,MOTORISTA,PLACA,IDUSUARIO,IDCONFERENTE,IDEMPRESA,IDDEPARTAMENTO,ENCERRADA,DOCA,NUM_LACRE,STATUS,QTDPALLET,TEMPERATURABAU,PESOINICIAL,PESOFINAL,DTCARGA,DTLANCAMENTO,DTMOVIMENTO,DTINICIO,DTFIM) VALUES ("
                    + "'"+ tipo +"',"
                    + "'"+ getDescricao() +"',"
                    + "'"+ getTransportadora() +"',"
                    + "'"+ getMotorista() +"',"
                    + "'"+ getPlaca() +"',"
                    + "'"+ idusuario +"',"
                    + "'"+ idconferente +"',"
                    + "'"+ idempresa +"',"
                    + "'"+ iddepartamento +"',"
                    + "'"+ encerrada +"',"
                    + "null,"
                    + "'"+ num_lacre +"',"
                    + "'"+ status +"',"
                    + "'"+ qtdpallet +"',"
                    + "'"+ temperaturabau +"',"
                    + "'"+ pesoinicial +"',"
                    + "'"+ pesofinal +"',"
                    + "'"+ dtcarga +"',"
                    + "GETDATE(),"
                    + "'"+ dtmovimento +"',"
                    + " null,"
                    + " null)" );
            return null;
        }catch(Exception ex){
            System.out.println("Error: Carga.insert."+ex.toString());
            return "Não foi possivel cadastrar Carga!";
        }
    }

    public String update(){
        try{
            SqlServer sql = new SqlServer();

            sql.SqlUpdate("UPDATE TBL_CARGA SET "
                    + " TIPO = '"+ getTipo() +"',"
                    + " DESCRICAO = '"+ getDescricao() +"',"
                    + " TRANSPORTADORA = '"+ getTransportadora() +"',"
                    + " MOTORISTA = '"+ getMotorista() +"',"
                    + " PLACA = '"+ getPlaca() +"',"
                    + " IDUSUARIO = '"+ idusuario +"',"
                    + " IDCONFERENTE = '"+ idconferente +"',"
                    + " IDEMPRESA = '"+ idempresa +"',"
                    + " IDDEPARTAMENTO = '"+ iddepartamento +"',"
                    + " ENCERRADA = '"+ encerrada +"',"
                    + " DOCA = '"+ doca +"',"
                    + " NUM_LACRE = '"+ num_lacre +"',"
                    + " STATUS = '"+ status +"',"
                    + " QTDPALLET = '"+ qtdpallet +"',"
                    + " TEMPERATURABAU = '"+ temperaturabau +"',"
                    + " PESOINICIAL = '"+ pesoinicial +"',"
                    + " PESOFINAL = '"+ pesofinal +"',"
                    + " DTCARGA = '"+ dtcarga +"',"
                    + " DTMOVIMENTO = '"+ dtmovimento +"',"
                    + " DTINICIO = "+ getDtinicioIn() +","
                    + " DTFIM = "+ getDtfimIn() +" "
                    + " WHERE IDCARGA = '"+idcarga+"' ");
            return null;
        }catch(Exception ex){
            System.out.println("Error: Carga.update."+ex.toString());
            return "Não foi possivel alterar Carga!";
        }
    }

    public String updateReconferencia(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlQuery("SELECT GETDATE() AS DATA");
            sql.rs.next();
            dtreconferencia = sql.rs.getString("DATA");

            sql = new SqlServer();
            sql.SqlUpdate("UPDATE TBL_CARGA SET "
                    + " DTRECONFERENCIA = GETDATE() "
                    + " WHERE IDCARGA = '"+idcarga+"' ");
            return null;
        }catch(Exception ex){
            System.out.println("Error: Carga.updateReconferencia."+ex.toString());
            return "Não foi possivel alterar Carga!";
        }
    }

    public String encerrar(){
        try{
            SqlServer sql = new SqlServer();
            setEncerrada(true);
            setStatus("Recebida");
            sql.SqlUpdate("UPDATE TBL_CARGA SET "
                    + " ENCERRADA = '"+encerrada+"', "
                    + " STATUS = '"+status+"' "
                    + " WHERE IDCARGA = '"+idcarga+"' ");
            return null;
        }catch(Exception ex){
            System.out.println("Error: Carga.update."+ex.toString());
            return "Não foi possivel alterar Carga!";
        }
    }

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_CARGA WHERE IDCARGA = '"+ idcarga +"'");
            return null;
        }catch(Exception ex){
            return "Não foi possivel excluir Carga!";
        }
    }


    /**
     * Retorna lista de cargas de acordo com o tipo informado.
     * @param tipo
     * @return
     */
    public ObservableList<Carga> getLista(int tipo,String date){
        ObservableList<Carga> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT C.*" +
                    " ,(SELECT SUM(QTDCX) FROM TBL_NOTA_ITEM AS NI INNER JOIN TBL_NOTA AS N2 ON N2.IDNOTA = NI.IDNOTA WHERE N2.IDCARGA = C.IDCARGA)AS VOLUME " +
                    " ,(SELECT SUM(QTDCX_CORTE) FROM TBL_NOTA_ITEM AS NI INNER JOIN TBL_NOTA AS N2 ON N2.IDNOTA = NI.IDNOTA WHERE N2.IDCARGA = C.IDCARGA)AS VOLUME_CORTE " +
                    " FROM TBL_CARGA AS C WHERE TIPO = '"+tipo+"' AND DTCARGA = '"+date+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Carga(sql.rs.getInt("IDCARGA"),sql.rs.getInt("TIPO"),sql.rs.getString("DESCRICAO"),sql.rs.getString("TRANSPORTADORA"),sql.rs.getString("MOTORISTA"),sql.rs.getString("PLACA"),sql.rs.getInt("IDUSUARIO"),sql.rs.getInt("IDCONFERENTE"),sql.rs.getInt("IDEMPRESA"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getBoolean("ENCERRADA"),sql.rs.getInt("DOCA"),sql.rs.getString("NUM_LACRE"),sql.rs.getString("STATUS"),sql.rs.getInt("QTDPALLET"),sql.rs.getDouble("TEMPERATURABAU"),sql.rs.getDouble("PESOINICIAL"),sql.rs.getDouble("PESOFINAL"),sql.rs.getString("DTCARGA"),sql.rs.getString("DTLANCAMENTO"),sql.rs.getString("DTMOVIMENTO"),sql.rs.getString("DTINICIO"),sql.rs.getString("DTFIM"),sql.rs.getString("DTRECONFERENCIA"),sql.rs.getInt("VOLUME"),sql.rs.getInt("VOLUME_CORTE")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            System.out.println("Error: Carga.getLista."+ex.toString());
            return null;
        }
    }

}


