package br.com.app.kardex.model.coletor;


import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventario {
    private int idinventario;
    private int idusuario;
    private String descricao;
    private String dtinicio;
    private String dtfim;
    private Boolean finalizado;

    public int getIdinventario(){return idinventario; }
    public void  setIdinventario(int idinventario){this.idinventario = idinventario; }
    public int getIdusuario(){return idusuario; }
    public void  setIdusuario(int idusuario){this.idusuario = idusuario; }
    public String getDescricao(){return descricao; }
    public void  setDescricao(String descricao){this.descricao = descricao; }
    public String getDtinicio(){return dtinicio; }
    public void  setDtinicio(String dtinicio){this.dtinicio = dtinicio; }
    public String getDtfim(){return dtfim; }
    public void  setDtfim(String dtfim){this.dtfim = dtfim; }
    public Boolean getFinalizado(){return finalizado; }
    public void  setFinalizado(Boolean finalizado){this.finalizado = finalizado; }

    public Inventario(){ }
    public Inventario(int idinventario, int idusuario, String descricao, String dtinicio, String dtfim, Boolean finalizado){
        this.idinventario = idinventario;
        this.idusuario = idusuario;
        this.descricao = descricao;
        this.dtinicio = dtinicio;
        this.dtfim = dtfim;
        this.finalizado = finalizado;
    }

    public ObservableList<Inventario> getLista(){
        ObservableList<Inventario> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_INVENTARIO ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Inventario(sql.rs.getInt("IDINVENTARIO"),sql.rs.getInt("IDUSUARIO"),sql.rs.getString("DESCRICAO"),sql.rs.getString("DTINICIO"),sql.rs.getString("DTFIM"),sql.rs.getBoolean("FINALIZADO")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public Inventario get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_INVENTARIO WHERE IDINVENTARIO = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new Inventario(sql.rs.getInt("IDINVENTARIO"),sql.rs.getInt("IDUSUARIO"),sql.rs.getString("DESCRICAO"),sql.rs.getString("DTINICIO"),sql.rs.getString("DTFIM"),sql.rs.getBoolean("FINALIZADO"));
        }catch(Exception ex){
            return null;
        }
    }

    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            idinventario = sql.SqlUpdateId("INSERT INTO TBL_INVENTARIO (IDUSUARIO,DESCRICAO,DTINICIO,FINALIZADO) VALUES ("
                    + "'"+ idusuario +"',"
                    + "'"+ descricao +"',"
                    + " GETDATE(),"
                    + "'"+ finalizado +"')" );
            return null;
        }catch(Exception ex){
            System.out.println("Erro: "+ex.toString());
            return "Não foi possivel cadastrar Inventario!";
        }
    }

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_INVENTARIO WHERE IDINVENTARIO = '"+ idinventario +"'");
            return null;
        }catch(Exception ex){
            return "Não foi possivel excluir Inventario!";
        }
    }

}
