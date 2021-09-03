/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.model.logistica;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import br.com.app.kardex.util.SqlServer;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 *
 * @author Vinicius
 */
public class Depara {
    
    private final SqlServer sql = new SqlServer();
    private final StringProperty placa;
    private final IntegerProperty viagem;
    private final IntegerProperty produto;
    private final IntegerProperty qtdfat;
    private final IntegerProperty qtdcar;
    private final IntegerProperty dif;
    public final ObservableList<Depara> listaNew = FXCollections.observableArrayList();
    
    public Depara(){
        viagem = new SimpleIntegerProperty();
        produto = new SimpleIntegerProperty();
        qtdfat = new SimpleIntegerProperty();
        qtdcar = new SimpleIntegerProperty();
        dif = new SimpleIntegerProperty();
        placa = new SimpleStringProperty();
    }
    public Depara(String placa,Integer viagem,Integer pro,Integer qtdfat,Integer qtdcar,Integer dif ){
        this.viagem = new SimpleIntegerProperty(viagem);
        this.produto = new SimpleIntegerProperty(pro);
        this.qtdfat = new SimpleIntegerProperty(qtdfat);
        this.qtdcar = new SimpleIntegerProperty(qtdcar);
        this.dif = new SimpleIntegerProperty(dif);
        this.placa = new SimpleStringProperty(placa);
    }
    public void setPlaca(String placa){
        this.placa.set(placa);
    }
    public void setViagem(Integer valor){
        this.viagem.set(valor);
    }
    public void setProduto(Integer valor){
        this.produto.set(valor);
    }
    public void setQtdfat(Integer valor){
        this.qtdfat.set(valor);
    }
    public void setQtdcar(Integer valor){
        this.qtdcar.set(valor);
    }
    public void setDif(Integer valor){
        this.dif.set(valor);
    }
    public String getPlaca(){
        return placa.get();
    }
    public Integer getViagem(){
        return viagem.get();
    }
    public Integer getProduto(){
        return produto.get();
    }
    public Integer getQtdfat(){
        return qtdfat.get();
    }
    public Integer getQtdcar(){
        return qtdcar.get();
    }
    public Integer getDif(){
        return dif.get();
    }
    
    public ObservableList<Depara> getLista(){
        ObservableList<Depara> lista = FXCollections.observableArrayList();
        try{
            String query = "SELECT EXP_PLACA,EXP_VIAGEM,EXP_CODPRO,SUM(EXP_QTD) AS FAT," 
                + "(SELECT SUM(CAR_QTD) FROM LOG_CARREGAMENTO WHERE CAR_NUMERO = EXP_VIAGEM AND CAR_CODPROD = EXP_CODPRO AND CAR_CORTE = 0)AS CAR "
                + "FROM LOG_EXPEDICAO GROUP BY EXP_PLACA,EXP_VIAGEM,EXP_CODPRO";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Depara(
                    sql.rs.getString("EXP_PLACA"),
                    sql.rs.getInt("EXP_VIAGEM"),
                    sql.rs.getInt("EXP_CODPRO"),
                    sql.rs.getInt("FAT"),
                    sql.rs.getInt("CAR"),
                    sql.rs.getInt("FAT")-sql.rs.getInt("CAR")));
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
    public ObservableList<Depara> getListaDep(Integer viagem) throws Exception{
        ArrayList lista = getListaPro(viagem);
        //ObservableList<Depara> listaNew = FXCollections.observableArrayList();
        for(int i =0;i < lista.size();i++){
            String placa = "";
            Integer produto = parseInt(lista.get(i).toString());
            Integer qtdCar = 0;
            Integer qtdFat = 0;
            Integer dif;
            String query = "SELECT SUM(CAR_QTD) AS CAR FROM LOG_CARREGAMENTO WHERE CAR_NUMERO = '"+viagem+"' AND CAR_CODPROD = '"+produto+"' AND CAR_CORTE = 0";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                placa = "";
                qtdCar = sql.rs.getInt("CAR");
            }
            String query2 = "SELECT SUM(EXP_QTD) AS FAT FROM LOG_EXPEDICAO WHERE EXP_VIAGEM = '"+viagem+"' AND EXP_CODPRO = '"+produto+"' ";
            sql.SqlQuery(query2);
            while(sql.rs.next()){
                qtdFat = sql.rs.getInt("FAT");
            }
            sql.desconecta();
            dif = qtdFat - qtdCar;
            listaNew.add(new Depara("",viagem,produto,qtdFat,qtdCar,dif));
        }
        return listaNew;
    }
    public ArrayList getListaProFat(Integer viagem){
        ArrayList lista = new ArrayList();
        try{
            String query = "SELECT EXP_CODPRO FROM LOG_EXPEDICAO WHERE EXP_VIAGEM = '"+viagem+"' GROUP BY EXP_CODPRO";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(sql.rs.getString("EXP_CODPRO"));
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
    public ArrayList getListaProCar(Integer viagem){
        ArrayList lista = new ArrayList();
        try{
            String query = "SELECT CAR_CODPROD FROM LOG_CARREGAMENTO WHERE CAR_NUMERO = '"+viagem+"' AND CAR_CORTE = 0 GROUP BY CAR_CODPROD";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(sql.rs.getString("CAR_CODPROD"));
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
    public ArrayList getListaPro(Integer viagem){
        ArrayList listaFat = new ArrayList();
        ArrayList listaNew = new ArrayList();
        listaFat = getListaProFat(viagem);
        listaNew = getListaProCar(viagem);
        
        for(int f = 0;f > listaFat.size();f++){
            Boolean possui = false;
            for(int c = 0;c < listaNew.size();c++){
                if(listaFat.get(f).toString().equals(listaNew.get(c).toString())){
                    possui = true;
                    break;
                }
            }
            if(!possui){
                listaNew.add(listaFat.get(f));
            }
        }
        return listaNew;
    }
}
