/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.model.logistica;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;


/**
 *
 * @author Vinicius
 */
public class PlacaViagem{
    
    private final CheckBox placa;
    private SqlServer sql = new SqlServer();
           
    public PlacaViagem(){
        placa = new CheckBox();
    }
    public PlacaViagem(String placa){
        this.placa = new CheckBox(placa);
    }
    public CheckBox getPlaca(){
        return placa;
    }
    public String getString(){
        return placa.getText();
    }
    public Boolean isPlaca(){
        return placa.isSelected();
    }
    public void setPlaca(Boolean valor){
        this.placa.setSelected(valor);
    }
    public ObservableList getLista(String numero){
        ObservableList lista = FXCollections.observableArrayList();
        try{
            String query = "SELECT CAR_PLACA FROM LOG_CARREGAMENTO WHERE CAR_NUMERO = '"+numero+"' GROUP BY CAR_PLACA ";
            if(sql.SqlNumRows(query) <= 1){
                return null;
            }
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new PlacaViagem(sql.rs.getString("CAR_PLACA")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
            return lista;
        }
    }
}
