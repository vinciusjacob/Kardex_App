/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.model.logistica;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import br.com.app.kardex.util.SqlServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vinicius
 */
public class Expedicao {
    
    private final SqlServer sql = new SqlServer();
    private final IntegerProperty viagem;
    private final StringProperty placa;
    private final IntegerProperty produto;
    private final StringProperty descricao;
    private final IntegerProperty qtd;
    private final StringProperty unidade;
    private final DoubleProperty peso;
    private final IntegerProperty nota;
    private final StringProperty data;
    private final StringProperty status;
    
    public Expedicao(){
        viagem = new SimpleIntegerProperty();
        placa  = new SimpleStringProperty();
        produto  = new SimpleIntegerProperty();
        descricao  = new SimpleStringProperty();
        qtd  = new SimpleIntegerProperty();
        unidade  = new SimpleStringProperty();
        peso = new SimpleDoubleProperty();
        nota = new SimpleIntegerProperty();
        data  = new SimpleStringProperty();
        status  = new SimpleStringProperty();
    }
    public Expedicao(Integer viagem,String placa, Integer codprod,String descrprod,Integer qtd,String und,Double peso,Integer nota,String data,String status){
        this.viagem = new SimpleIntegerProperty(viagem);
        this.placa  = new SimpleStringProperty(placa);
        this.produto  = new SimpleIntegerProperty(codprod);
        this.descricao  = new SimpleStringProperty(descrprod);
        this.qtd  = new SimpleIntegerProperty(qtd);
        this.unidade  = new SimpleStringProperty(und);
        this.peso = new SimpleDoubleProperty(peso);
        this.nota = new SimpleIntegerProperty(nota);
        this.data  = new SimpleStringProperty(data);
        this.status  = new SimpleStringProperty(status);
    }
    public void setViagem(Integer viagem){
        this.viagem.set(viagem);
    }
    public void setPlaca(String placa){
        this.placa.set(placa);
    }
    public void setproduto(Integer cod){
        this.produto.set(cod);
    }
    public void setDescricao(String descr){
        this.descricao.set(descr);
    }
    public void setQtd(Integer qtd){
        this.qtd.set(qtd);
    }
    public void setUnidade(String und){
        this.unidade.set(und);
    }
    public void setPeso(Double peso){
        this.peso.set(peso);
    }
    public void setNota(Integer nota){
        this.nota.set(nota);
    }
    public void setData(String data){
        this.data.set(data);
    }
    public void setStatus(String status){
        this.status.set(status);
    }
    public Integer getViagem(){
        return viagem.get();
    }
    public String getPlaca(){
        return placa.get();
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
    public String getUnidade(){
        return unidade.get();
    }
    public Double getPeso(){
        return peso.get();
    }
    public Integer getNota(){
        return nota.get();
    }
    public String getData(){
        return data.get();
    }
    public String getStatus(){
        return status.get();
    }
    public ObservableList<Expedicao> getLista(){
        ObservableList<Expedicao> lista = FXCollections.observableArrayList();
        try{
            String query = "Select * From LOG_EXPEDICAO LEFT JOIN LOG_ESCALA on ESC_NOTA = EXP_NF";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Expedicao(
                    sql.rs.getInt("EXP_VIAGEM"),
                    sql.rs.getString("ESC_PLACA"),
                    sql.rs.getInt("EXP_CODPRO"),
                    sql.rs.getString("EXP_DESCRPRO"),
                    sql.rs.getInt("EXP_QTD"),
                    sql.rs.getString("EXP_UND"),
                    sql.rs.getDouble("EXP_PESOBRUTO"),
                    sql.rs.getInt("EXP_NF"),
                    sql.rs.getString("ESC_DATA"),
                    sql.rs.getString("EXP_STATUS")));
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
    private String getDataFormat(Date data) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dt = sdf.format(data);
        return dt;
    }
}
