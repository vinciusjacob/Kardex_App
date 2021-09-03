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
public class MontarCarga {
    
    SqlServer sql = new SqlServer();
    
    private CheckBox selec;
    private final BooleanProperty selecionar;
    private final IntegerProperty nota;
    private final DoubleProperty peso;
    private final IntegerProperty qtd;
    private final StringProperty status;
    
    public CheckBox getSelec(){
        return selec;
    }
    
    public MontarCarga(Boolean selec,Integer nota,Double peso,Integer qtd,String status){
        this.selec = new CheckBox();
        this.selec.setSelected(selec);
        this.selecionar = new SimpleBooleanProperty(selec);
        this.nota = new SimpleIntegerProperty(nota);
        this.peso = new SimpleDoubleProperty(peso);
        this.qtd = new SimpleIntegerProperty(qtd);
        this.status = new SimpleStringProperty(status);
    }
    public MontarCarga(){
        selecionar = new SimpleBooleanProperty();
        nota = new SimpleIntegerProperty();
        peso = new SimpleDoubleProperty();
        qtd = new SimpleIntegerProperty();
        status = new SimpleStringProperty();
    }
    public void setSelecionar(Boolean selecionar){
        this.selecionar.set(selecionar);
    }
    public void setNota(Integer nota){
        this.nota.set(nota);
    }
    public void setPeso(Double peso){
        this.peso.set(peso);
    }
    public void setQtd(Integer qtd){
        this.qtd.set(qtd);
    }
    public void setStatus(String status){
        this.status.set(status);
    }
    public IntegerProperty NotaProperty(){
        return nota;
    }
    public StringProperty StatusProperty(){
        return status;
    }
    public Boolean isSelecionar(){
        return selecionar.get();
    }
    public BooleanProperty SelecionarProperty(){
        return selecionar;
    }
    public Integer getNota(){
        return nota.get();
    }
    public Double getPeso(){
        return peso.get();
    }
    public Integer getQtd(){
        return qtd.get();
    }
    public String getStatus(){
        return status.get();
    }
    
    public Boolean isSelc(){
        return selec.isSelected();
    }
    public void setSelec(Boolean selec){
        this.selec.setSelected(selec);
    }
    
    public ObservableList<MontarCarga> getLista(){
        ObservableList<MontarCarga> lista = FXCollections.observableArrayList();
        try{
            String query = "Select EXP_NF,EXP_STATUS, sum(EXP_QTD) as QTD,sum(EXP_PESOBRUTO)as PESO"
                + " From LOG_EXPEDICAO group by EXP_NF,EXP_STATUS  ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new MontarCarga(
                    false,
                    sql.rs.getInt("EXP_NF"),
                    sql.rs.getDouble("PESO"),
                    sql.rs.getInt("QTD"),
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
    
    public void Cadastar(Integer nota,String placa, String data,String status,Integer volume,Double peso) throws Exception{
        String query = "Insert into LOG_ESCALA (ESC_NOTA, ESC_PLACA, ESC_DATA, ESC_CODTXT, ESC_STATUS, ESC_VOLUME, ESC_PESO, ESC_NEWDATA) "
            +"values ("+nota+", '"+placa+"','"+data+"', 0,'"+status+"',"+volume+","+peso+",'false' )";
        sql.SqlUpdate(query);
    }
    
    public Boolean isEscalaRep(String placa, String data) throws Exception{
        String query = "Select * From LOG_ESCALA where ESC_PLACA = '"+placa+"' and  ESC_DATA = '"+data+"' ";
        int rows = sql.SqlNumRows(query);
        if(rows > 0){
            return true;
        }else{
            return false;
        }
    }
    
}
