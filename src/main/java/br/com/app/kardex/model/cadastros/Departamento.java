package br.com.app.kardex.model.cadastros;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Departamento {
    private int iddepartamento;
    private String descricao;

    public int getIddepartamento(){return iddepartamento; }
    public void  setIddepartamento(int iddepartamento){this.iddepartamento = iddepartamento; }
    public String getDescricao(){return descricao; }
    public void  setDescricao(String descricao){this.descricao = descricao; }

    public Departamento(){ }
    public Departamento(int iddepartamento, String descricao){
        this.iddepartamento = iddepartamento;
        this.descricao = descricao;
    }

    public ObservableList<Departamento> getLista(){
        ObservableList<Departamento> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_DEPARTAMENTO ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Departamento(sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getString("DESCRICAO")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public Departamento get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_DEPARTAMENTO WHERE IDDEPARTAMENTO = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new Departamento(sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getString("DESCRICAO"));
        }catch(Exception ex){
            return null;
        }
    }

    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            iddepartamento = sql.SqlUpdateId("INSERT INTO TBL_DEPARTAMENTO (DESCRICAO) VALUES ("
                    + "'"+ descricao +"')" );
            return null;
        }catch(Exception ex){
            return "Não foi possivel cadastrar Departamento!";
        }
    }

    public String update(){return null;}

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_DEPARTAMENTO WHERE IDDEPARTAMENTO = '"+ iddepartamento +"'");
            return null;
        }catch(Exception ex){
            return "Não foi possivel excluir Departamento!";
        }
    }

}


