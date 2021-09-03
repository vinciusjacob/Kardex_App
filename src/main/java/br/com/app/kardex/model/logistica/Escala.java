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
public class Escala {
    
    SqlServer sql = new SqlServer();
    private final StringProperty placa;
    private final StringProperty data;
    private final IntegerProperty qtdnf;
    private final DoubleProperty peso;
    private final IntegerProperty volume;
    
    public Escala(){
        placa = new SimpleStringProperty();
        data = new SimpleStringProperty();
        qtdnf = new SimpleIntegerProperty();
        peso = new SimpleDoubleProperty();
        volume = new SimpleIntegerProperty();
    }
    public Escala(String placa,String data,Integer qtdnf,Double peso,Integer volume){
        this.placa = new SimpleStringProperty(placa);
        this.data = new SimpleStringProperty(data);
        this.qtdnf = new SimpleIntegerProperty(qtdnf);
        this.peso = new SimpleDoubleProperty(peso);
        this.volume = new SimpleIntegerProperty(volume);
    }
    public void setPlaca(String valor){
       this.placa.set(valor);
    }
    public void setData(String valor){
       this.data.set(valor);
    }
    public void setQtdnf(Integer valor){
       this.qtdnf.set(valor);
    }
    public void setPeso(Double valor){
       this.peso.set(valor);
    }
    public void setVolume(Integer valor){
       this.volume.set(valor);
    }
    public String getPlaca(){
        return placa.get();
    }
    public String getData(){
        return data.get();
    }
    public Integer getQtdnf(){
        return qtdnf.get();
    }
    public Double getPeso(){
        return peso.get();
    }
    public Integer getVolume(){
        return volume.get();
    }
    
    public ObservableList<Escala> getLista(String data){
        ObservableList<Escala> lista = FXCollections.observableArrayList();
        try{
            String query = "select ESC_PLACA,ESC_DATA,COUNT(ESC_NOTA) AS NOTA,SUM(ESC_VOLUME) AS QTD, SUM(ESC_PESO)AS PESO from LOG_ESCALA " +
                "WHERE ESC_DATA = '"+data+"' " +
                "GROUP BY ESC_PLACA,ESC_DATA";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Escala(
                    sql.rs.getString("ESC_PLACA"),
                    getDataFormat(sql.rs.getDate("ESC_DATA")),
                    sql.rs.getInt("NOTA"),
                    sql.rs.getDouble("PESO"),
                    sql.rs.getInt("QTD")));
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
    public void Excluir(String placa,String data) throws Exception{
        String query = "DELETE FROM LOG_ESCALA WHERE ESC_PLACA = '"+placa+"' AND ESC_DATA = '"+data+"'  ";
        sql.SqlUpdate(query);
    }
}
