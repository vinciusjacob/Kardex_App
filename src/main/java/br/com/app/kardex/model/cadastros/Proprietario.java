package br.com.app.kardex.model.cadastros;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Proprietario {
    private int idproprietario;
    private String descricao;

    public int getIdproprietario(){return idproprietario; }
    public void  setIdproprietario(int idproprietario){this.idproprietario = idproprietario; }
    public String getDescricao(){return descricao; }
    public void  setDescricao(String descricao){this.descricao = descricao; }

    public Proprietario(){ }
    public Proprietario(int idproprietario, String descricao){
        this.idproprietario = idproprietario;
        this.descricao = descricao;
    }

    public ObservableList<Proprietario> getLista(){
        ObservableList<Proprietario> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_PROPRIETARIO ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Proprietario(sql.rs.getInt("IDPROPRIETARIO"),sql.rs.getString("DESCRICAO")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public Proprietario get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_PROPRIETARIO WHERE IDPROPRIETARIO = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new Proprietario(sql.rs.getInt("IDPROPRIETARIO"),sql.rs.getString("DESCRICAO"));
        }catch(Exception ex){
            return null;
        }
    }

    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            idproprietario = sql.SqlUpdateId("INSERT INTO TBL_PROPRIETARIO (DESCRICAO) VALUES ("
                    + "'"+ descricao +"')" );
            return null;
        }catch(Exception ex){
            return "Não foi possivel cadastrar Proprietario!";
        }
    }

    public String update(){return null;}

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_PROPRIETARIO WHERE IDPROPRIETARIO = '"+ idproprietario +"'");
            return null;
        }catch(Exception ex){
            return "Não foi possivel excluir Proprietario!";
        }
    }

}

