/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.model.logistica;

import br.com.app.kardex.util.SqlServer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vinicius
 */
public class Importacao {
    
     private final IntegerProperty cod;
     private final StringProperty descr;
     private final StringProperty data;
     private SqlServer sql = new SqlServer();
     
     public void setCod(Integer cod){
         this.cod.set(cod);
     }
     public void setDescr(String descr){
         this.descr.set(descr);
     }
     public void setData(String data){
         this.data.set(data);
     }
     public Integer getCod(){
         return cod.get();
     }
     public String getDescr(){
         return descr.get();
     }public String getData(){
         return data.get();
     }
     public Importacao(){
         cod = new SimpleIntegerProperty(0);
         descr = new SimpleStringProperty("");
         data = new SimpleStringProperty("");
     }
     public Importacao(Integer cod,String descr, String data){
         this.cod = new SimpleIntegerProperty(cod);
         this.descr = new SimpleStringProperty(descr);
         this.data = new SimpleStringProperty(data);
     }
     public ObservableList<Importacao> getLista() throws Exception{
        ObservableList<Importacao> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM LOG_TXT ORDER BY TXT_DATA DESC ";
        sql.SqlQuery(query);
        while(sql.rs.next()){
            lista.add(new Importacao(
                sql.rs.getInt("TXT_CODIGO"),
                sql.rs.getString("TXT_TITULO"),
                getDataFormat(sql.rs.getDate("TXT_DATA"))));
        }
        sql.desconecta();
        
        return lista;
    }
    private String getDataFormat(Date data) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dt = sdf.format(data);
        return dt;
    }
    
    public Boolean isTxtRep(String nome) throws Exception{
        String query = "Select * From LOG_TXT where TXT_TITULO = '"+nome+"' ";
        int rows = sql.SqlNumRows(query);
        if(rows > 0){
            return true;
        }else{
            return false;
        }
    }
    public void Excluir(Integer cod) throws Exception{
        String query = "DELETE FROM LOG_TXT WHERE TXT_CODIGO = '"+cod+"' ";
        sql.SqlUpdate(query);
        query = "DELETE FROM LOG_EXPEDICAO WHERE EXP_CODTXT = '"+cod+"' ";
        sql.SqlUpdate(query);
        query = "DELETE FROM LOG_CARREGAMENTO WHERE CAR_CODTXT = '"+cod+"' ";
        sql.SqlUpdate(query);
    }
}
