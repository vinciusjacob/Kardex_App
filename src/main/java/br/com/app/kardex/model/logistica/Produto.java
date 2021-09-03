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


/**
 *
 * @author Vinicius
 */
public class Produto {
    
    private SqlServer sql = new SqlServer();
    private final IntegerProperty Cod;
    private final StringProperty Sigla;
    private final StringProperty Descricao;
    private final DoubleProperty PesoBruto;
    private final StringProperty Camara;
    private final IntegerProperty Shelf;
    private final StringProperty Lastro;
    private final StringProperty Altura;
    private final StringProperty CxsPallet;
    private final DoubleProperty PesoLiq;
    private final BooleanProperty ImpPeso;
    
    public Integer getCod(){
        return Cod.get();
    }
    public IntegerProperty getCodProperty(){
        return Cod;
    } 
    public String getSigla(){
        return Sigla.get();
    }
    public StringProperty getSiglaProperty(){
        return Sigla;
    }
    public String getDescricao(){
        return Descricao.get();
    }
    public StringProperty getDescrProperty(){
        return Descricao;
    } 
    public Double getPesobruto(){
        return PesoBruto.get();
    }
    public DoubleProperty getPesobrutoProperty(){
        return PesoBruto;
    }
    public String getCamara(){
        return Camara.get();
    }
    public StringProperty getCamaraProperty(){
        return Camara;
    }
    public Integer getShelf(){
        return Shelf.get();
    }
    public IntegerProperty getShelfProperty(){
        return Shelf;
    }
    public String getLastro(){
        return Lastro.get();
    }
    public StringProperty getLastroProperty(){
        return Lastro;
    }
    public String getAltura(){
        return Altura.get();
    }
    public StringProperty getAlturaProperty(){
        return Altura;
    }
    public String getCxspallet(){
        return CxsPallet.get();
    }
    public StringProperty getCxspalletProperty(){
        return CxsPallet;
    }
    public Double getPesoliq(){
        return PesoLiq.get();
    }
    public DoubleProperty getPesoliqProperty(){
        return PesoLiq;
    }
    public String getImppeso(){
        if(ImpPeso.get()){
            return "Sim";
        }else{
            return "Não";
        }
    }
    public Integer getImppesoCad(){
        if(ImpPeso.get()){
            return 1;
        }else{
            return 0;
        }
    }
    public Boolean isImppeso(){
        return ImpPeso.get();
    }
    public Produto(){
        Cod = new SimpleIntegerProperty();
        Sigla = new SimpleStringProperty();
        Descricao = new SimpleStringProperty();
        PesoBruto = new SimpleDoubleProperty();
        Camara = new SimpleStringProperty();
        Shelf = new SimpleIntegerProperty();
        Lastro = new SimpleStringProperty();
        Altura = new SimpleStringProperty();
        CxsPallet = new SimpleStringProperty();
        PesoLiq = new SimpleDoubleProperty();
        ImpPeso = new SimpleBooleanProperty();
    }
    public Produto(Integer cod,String sigla,String descr,Double pesobruto,String camara,Integer shelf,String lastro,String altura,String cxspallet,Double pesoliq,Boolean imppeso){
        Cod = new SimpleIntegerProperty(cod);
        Sigla = new SimpleStringProperty(sigla);
        Descricao = new SimpleStringProperty(descr);
        PesoBruto = new SimpleDoubleProperty(pesobruto);
        Camara = new SimpleStringProperty(camara);
        Shelf = new SimpleIntegerProperty(shelf);
        Lastro = new SimpleStringProperty(lastro);
        Altura = new SimpleStringProperty(altura);
        CxsPallet = new SimpleStringProperty(cxspallet);
        PesoLiq = new SimpleDoubleProperty(pesoliq);
        ImpPeso = new SimpleBooleanProperty(imppeso);
    }
    public Boolean isExiste(){
        try{
            String query = "SELECT * FROM LOG_PRODUTO WHERE PRO_COD = '"+getCod()+"' ";
            if(sql.SqlNumRows(query) >= 1){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
            return false;
        }
    }
    public String Cadastrar(){
        try{
            String query = "INSERT INTO LOG_PRODUTO "
                    + "(PRO_COD, "
                    + "PRO_SIGLA, "
                    + "PRO_DESCRICAO, "
                    + "PRO_PESOBRUTO, "
                    + "PRO_CAMARA, "
                    + "PRO_SHELF, "
                    + "PRO_LASTRO, "
                    + "PRO_ALTURA, "
                    + "PRO_CXSPALET, "
                    + "PRO_PESOLIQ, "
                    + "PRO_IMPPESO) "
                    + "VALUES ('"+getCod()+"',"
                    + "'"+getSigla()+"',"
                    + "'"+getDescricao()+"',"
                    + "'"+getPesobruto()+"',"
                    + "'"+getCamara()+"',"
                    + "'"+getShelf()+"',"
                    + "'"+getLastro()+"',"
                    + "'"+getAltura()+"', "
                    + "'"+getCxspallet()+"',"
                    + "'"+getPesoliq()+"',"
                    + "'"+getImppesoCad()+"' )";

            sql.SqlUpdate(query);
            sql.desconecta();
            return "Cadastrado com Sucesso!"; 
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
            return "Não foi possivel conectar como servidor";
        }
    }
    public String Alterar(){
        try{
            String query = "UPDATE LOG_PRODUTO SET "
                    + "PRO_SIGLA = '"+getSigla()+"',"
                    + "PRO_DESCRICAO = '"+getDescricao()+"', "
                    + "PRO_PESOBRUTO = '"+getPesobruto()+"', "
                    + "PRO_CAMARA = '"+getCamara()+"', "
                    + "PRO_SHELF = '"+getShelf()+"', "
                    + "PRO_LASTRO = '"+getLastro()+"', "
                    + "PRO_ALTURA = '"+getAltura()+"', "
                    + "PRO_CXSPALET = '"+getCxspallet()+"', "
                    + "PRO_PESOLIQ = '"+getPesoliq()+"', "
                    + "PRO_IMPPESO = '"+getImppesoCad()+"' "
                    + "WHERE PRO_COD = '"+getCod()+"'";
            sql.SqlUpdate(query);
            sql.desconecta();
            return "Alterado com Sucesso!"; 
        }catch(Exception ex){
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText("Erro ao conectar com o servidor.");
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
            return "Não foi possivel conectar como servidor";
        }
    }
    public void Excluir(Integer cod) throws Exception{
        String query = "DELETE FROM LOG_PRODUTO WHERE PRO_COD = '"+cod+"' ";
        sql.SqlUpdate(query);
        sql.desconecta();
    }
    public ObservableList<Produto> getLista(){
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try{
            String query = "SELECT * FROM LOG_PRODUTO";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                Boolean imppeso = false;
                if (sql.rs.getInt("PRO_IMPPESO") == 1)
                    imppeso = true;
                lista.add(new Produto(
                    sql.rs.getInt("PRO_COD"),
                    sql.rs.getString("PRO_SIGLA"),
                    sql.rs.getString("PRO_DESCRICAO"),
                    sql.rs.getDouble("PRO_PESOBRUTO"),
                    sql.rs.getString("PRO_CAMARA"),
                    sql.rs.getInt("PRO_SHELF"),
                    sql.rs.getString("PRO_LASTRO"),
                    sql.rs.getString("PRO_ALTURA"),
                    sql.rs.getString("PRO_CXSPALET"),
                    sql.rs.getDouble("PRO_PESOLIQ"),
                    imppeso));
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
