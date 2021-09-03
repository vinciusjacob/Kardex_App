package br.com.app.kardex.model.cadastros;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Posicao {
    private String idposicao;
    private int camara;
    private int rua;
    private int bloco;
    private int nivel;
    private int apartamento;
    private String temperatura;
    private String tipo_posicao;
    private String estrutura;
    private int idproprietario;

    public String getIdposicao(){return idposicao; }
    public void  setIdposicao(String idposicao){this.idposicao = idposicao; }
    public int getCamara(){return camara; }
    public void  setCamara(int camara){this.camara = camara; }
    public int getRua(){return rua; }
    public void  setRua(int rua){this.rua = rua; }
    public int getBloco(){return bloco; }
    public void  setBloco(int bloco){this.bloco = bloco; }
    public int getNivel(){return nivel; }
    public void  setNivel(int nivel){this.nivel = nivel; }
    public int getApartamento(){return apartamento; }
    public void  setApartamento(int apartamento){this.apartamento = apartamento; }
    public String getTemperatura(){return temperatura; }
    public void  setTemperatura(String temperatura){this.temperatura = temperatura; }
    public String getTipo_posicao(){return tipo_posicao; }
    public void  setTipo_posicao(String tipo_posicao){this.tipo_posicao = tipo_posicao; }
    public String getEstrutura(){return estrutura; }
    public void  setEstrutura(String estrutura){this.estrutura = estrutura; }
    public int getIdproprietario(){return idproprietario; }
    public void  setIdproprietario(int idproprietario){this.idproprietario = idproprietario; }

    public Posicao(){ }
    public Posicao(String idposicao, int camara, int rua, int bloco, int nivel, int apartamento, String temperatura, String tipo_posicao, String estrutura, int idproprietario){
        this.idposicao = idposicao;
        this.camara = camara;
        this.rua = rua;
        this.bloco = bloco;
        this.nivel = nivel;
        this.apartamento = apartamento;
        this.temperatura = temperatura;
        this.tipo_posicao = tipo_posicao;
        this.estrutura = estrutura;
        this.idproprietario = idproprietario;
    }

    public ObservableList<Posicao> getLista(){
        ObservableList<Posicao> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_POSICAO ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Posicao(sql.rs.getString("IDPOSICAO"),sql.rs.getInt("CAMARA"),sql.rs.getInt("RUA"),sql.rs.getInt("BLOCO"),sql.rs.getInt("NIVEL"),sql.rs.getInt("APARTAMENTO"),sql.rs.getString("TEMPERATURA"),sql.rs.getString("TIPO_POSICAO"),sql.rs.getString("ESTRUTURA"),sql.rs.getInt("IDPROPRIETARIO")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public Posicao get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_POSICAO WHERE IDPOSICAO = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new Posicao(sql.rs.getString("IDPOSICAO"),sql.rs.getInt("CAMARA"),sql.rs.getInt("RUA"),sql.rs.getInt("BLOCO"),sql.rs.getInt("NIVEL"),sql.rs.getInt("APARTAMENTO"),sql.rs.getString("TEMPERATURA"),sql.rs.getString("TIPO_POSICAO"),sql.rs.getString("ESTRUTURA"),sql.rs.getInt("IDPROPRIETARIO"));
        }catch(Exception ex){
            return null;
        }
    }

    public String update(){return null;}

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_POSICAO WHERE IDPOSICAO = '"+ idposicao +"'");
            return null;
        }catch(Exception ex){
            return "Não foi possivel excluir Posicao!";
        }
    }

}

