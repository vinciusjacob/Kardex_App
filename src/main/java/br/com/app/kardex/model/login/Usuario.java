package br.com.app.kardex.model.login;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Usuario {
    private int idusuario;
    private int idperfil;
    private String nome;
    private String login;
    private String senha;
    private Boolean master;
    private Boolean conferente;

    public int getIdusuario(){return idusuario; }
    public void  setIdusuario(int idusuario){this.idusuario = idusuario; }
    public int getIdperfil(){return idperfil; }
    public void  setIdperfil(int idperfil){this.idperfil = idperfil; }
    public String getNome(){return nome; }
    public void  setNome(String nome){this.nome = nome; }
    public String getLogin(){return login; }
    public void  setLogin(String login){this.login = login; }
    public String getSenha(){return senha; }
    public void  setSenha(String senha){this.senha = senha; }
    public Boolean getMaster(){return master; }
    public void  setMaster(Boolean master){this.master = master; }
    public Boolean getConferente(){return conferente; }
    public void  setConferente(Boolean conferente){this.conferente = conferente; }

    public Usuario(){ }
    public Usuario(int idusuario, int idperfil, String nome, String login, String senha, Boolean master, Boolean conferente){
        this.idusuario = idusuario;
        this.idperfil = idperfil;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.master = master;
        this.conferente = conferente;
    }

    public ObservableList<Usuario> getLista(){
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_USUARIO ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Usuario(sql.rs.getInt("IDUSUARIO"),sql.rs.getInt("IDPERFIL"),sql.rs.getString("NOME"),sql.rs.getString("LOGIN"),sql.rs.getString("SENHA"),sql.rs.getBoolean("MASTER"),sql.rs.getBoolean("CONFERENTE")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public Usuario get(String login){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_USUARIO WHERE LOGIN = '"+login+"' ";
            sql.SqlQuery(query);
            sql.rs.next();
            return new Usuario(sql.rs.getInt("IDUSUARIO"),sql.rs.getInt("IDPERFIL"),sql.rs.getString("NOME"),sql.rs.getString("LOGIN"),sql.rs.getString("SENHA"),sql.rs.getBoolean("MASTER"),sql.rs.getBoolean("CONFERENTE"));
        }catch(Exception ex){
            return null;
        }
    }

    public ObservableList<Usuario> getListaConferente(){
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_USUARIO WHERE CONFERENTE = '"+1+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Usuario(sql.rs.getInt("IDUSUARIO"),sql.rs.getInt("IDPERFIL"),sql.rs.getString("NOME"),sql.rs.getString("LOGIN"),sql.rs.getString("SENHA"),sql.rs.getBoolean("MASTER"),sql.rs.getBoolean("CONFERENTE")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

}

