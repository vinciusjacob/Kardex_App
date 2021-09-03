/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.controller.logistica;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import br.com.app.kardex.util.SqlServer;

/**
 *
 * @author Vinicius
 */
public class fx_proprietarioClass {
    
    private final IntegerProperty cod;
    private final StringProperty razao;
    private final StringProperty fantasia;
    private final StringProperty cnpj;
    private final BooleanProperty desativado;
    private SqlServer sql = new SqlServer();
    
    
    public fx_proprietarioClass(){
        cod = new SimpleIntegerProperty(0);
        razao = new SimpleStringProperty("");
        fantasia = new SimpleStringProperty("");
        cnpj = new SimpleStringProperty("");
        desativado = new SimpleBooleanProperty(false);
    }
    public fx_proprietarioClass(Integer cod,String razao, String fantasia, String cnpj, Boolean desativado){
        this.cod = new SimpleIntegerProperty(cod);
        this.razao = new SimpleStringProperty(razao);
        this.fantasia = new SimpleStringProperty(fantasia);
        this.cnpj = new SimpleStringProperty(cnpj);
        this.desativado = new SimpleBooleanProperty(desativado);
    }
    
    public void setCod(Integer cod){
        this.cod.set(cod);
    }
    public void setRazao(String razao){
        this.razao.set(razao);
    }
    public void setFantasia(String fantasia){
        this.fantasia.set(fantasia);
    }
    public void setCnpj(String cnpj){
        this.cnpj.set(cnpj);
    }
    public void setDesativado(Boolean desativado){
        this.desativado.set(desativado);
    }
    public Integer getCod(){
        return cod.get();
    }
    public String getRazao(){
        return razao.get();
    }
    public String getFantasia(){
        return fantasia.get();
    }
    public String getCnpj(){
        return cnpj.get();
    }
    public Boolean getDesativado(){
        return desativado.get();
    }
    public ObservableList<fx_proprietarioClass> getLista(){
        ObservableList<fx_proprietarioClass> lista = FXCollections.observableArrayList();
        try{
            String query = "SELECT * FROM LOG_PROPRIETARIO";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new fx_proprietarioClass(
                    sql.rs.getInt("PROP_CODIGO"),
                    sql.rs.getString("PROP_RAZAO"),
                    sql.rs.getString("PROP_APELIDO"),
                    sql.rs.getString("PROP_CNPJ"),
                    getStatus(sql.rs.getInt("PROP_DESATIVADO"))));
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
    private Boolean getStatus(Integer bit){
        if(bit == 0){
            return false;
        }else{
            return true;
        }
    }
}
