/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.model.logistica;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import br.com.app.kardex.util.SqlServer;

/**
 *
 * @author Vinicius
 */
public class DeparaLista {
    
    private final IntegerProperty viagem;
    private final  CheckBox exportar = new CheckBox();
    private SqlServer sql = new SqlServer();
    
    
    
    public DeparaLista(){
        viagem = new SimpleIntegerProperty();
    }
    
    public DeparaLista(Integer viagem,Boolean exportar){
        this.viagem = new SimpleIntegerProperty(viagem);
        this.exportar.setSelected(exportar);
    }
    
    public void setViagem(Integer viagem){
        this.viagem.set(viagem);
    }
    public void setExportar(Boolean exportar){
        this.exportar.setSelected(exportar);
    }
    public Integer getViagem(){
        return viagem.get();
    }
    public CheckBox getExportar(){
        return exportar;
    }
    public Boolean isExportar(){
        return exportar.isSelected();
    }
    public ObservableList<DeparaLista> getLista(String data){
        ObservableList<DeparaLista> lista = FXCollections.observableArrayList();
        try{
            String query = "SELECT CAR_NUMERO FROM LOG_CARREGAMENTO  WHERE CAR_DATA = '"+data+"' GROUP BY CAR_NUMERO";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new DeparaLista(
                    sql.rs.getInt("CAR_NUMERO"),
                    false));
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
