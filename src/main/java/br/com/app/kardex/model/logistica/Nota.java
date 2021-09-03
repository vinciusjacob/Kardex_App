/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.model.logistica;

import br.com.app.kardex.util.SqlServer;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;


/**
 *
 * @author Vinicius
 */
public class Nota {
    
    private SqlServer sql = new SqlServer();
    private final IntegerProperty cod;
    private final StringProperty placa;
    private final StringProperty data;
    private final IntegerProperty nota;
    private final DoubleProperty peso;
    private final IntegerProperty volume;
    private final StringProperty status;
    private final CheckBox newdata;
    
    public Nota(){
        cod = new SimpleIntegerProperty();
        placa = new SimpleStringProperty();
        data = new SimpleStringProperty();
        nota = new SimpleIntegerProperty();
        peso = new SimpleDoubleProperty();
        volume = new SimpleIntegerProperty();
        status = new SimpleStringProperty();
        newdata = new CheckBox();
    }
    public Nota(Integer cod,String placa,String data,Integer nota,Double peso,Integer volume,String status,Boolean newdata){
        this.cod = new SimpleIntegerProperty(cod);
        this.placa = new SimpleStringProperty(placa);
        this.data = new SimpleStringProperty(data);
        this.nota = new SimpleIntegerProperty(nota);
        this.peso = new SimpleDoubleProperty(peso);
        this.volume = new SimpleIntegerProperty(volume);
        this.status = new SimpleStringProperty(status);
        this.newdata = new CheckBox();
        this.newdata.setSelected(newdata);
    }
    public void setPlaca(String valor){
        this.placa.set(valor);
    }
    public void setData(String valor){
        this.data.set(valor);
    }
    public void setNota(Integer valor){
        this.nota.set(valor);
    }
    public void setPeso(Double valor){
        this.peso.set(valor);
    }
    public void setVolume(Integer valor){
        this.volume.set(valor);
    }
    public void setStatus(String valor){
        this.status.set(valor);
    }
    public void setNewdata(Boolean value){
        this.newdata.setSelected(value);
    }
    public Integer getCod(){
        return cod.get();
    }
    public String getPlaca(){
        return placa.get();
    }
    public String getData(){
        return data.get();
    }
    public Integer getNota(){
        return nota.get();
    }
    public Double getPeso(){
        return peso.get();
    }
    public Integer getVolume(){
        return volume.get();
    }
    public String getStatus(){
        return status.get();
    }
    public CheckBox getNewdata(){
        return newdata;
    }
    public Boolean getNewdataValue(){
        return newdata.isSelected();
    }
    public void Alterar(String newdata, Integer cod) throws Exception{
        String query = "UPDATE LOG_ESCALA SET ESC_NEWDATA = '"+newdata+"' WHERE ESC_CODIGO = '"+cod+"'";
        sql.SqlUpdate(query);
    }
    public ObservableList<Nota> getLista(String placa,String data){
        ObservableList<Nota> lista = FXCollections.observableArrayList();
        try{
            String query = "Select * From LOG_ESCALA"
                +" Where ESC_PLACA = '"+placa+"' and ESC_DATA = '"+data+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Nota(
                    sql.rs.getInt("ESC_CODIGO"),
                    sql.rs.getString("ESC_PLACA"),
                    sql.rs.getString("ESC_DATA"),
                    sql.rs.getInt("ESC_NOTA"),
                    sql.rs.getDouble("ESC_PESO"),
                    sql.rs.getInt("ESC_VOLUME"),
                    sql.rs.getString("ESC_STATUS"),
                    sql.rs.getBoolean("ESC_NEWDATA")));
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
}
