package br.com.app.kardex.model.login;

import br.com.app.kardex.util.SqlServer;
import javafx.scene.control.Alert;

public class Login {

    private String Login;
    public static String Nome;
    private String Senha;
    public String msg;

    private SqlServer sql = new SqlServer();

    public Boolean isValida(String login,String senha){
        try{
            String query = "SELECT * FROM USUARIO WHERE USU_LOGIN = '"+login+"' ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                Login = sql.rs.getString("USU_LOGIN");
                Nome = sql.rs.getString("USU_NOME");
                Senha = sql.rs.getString("USU_SENHA");
            }
            sql.desconecta();
            if(Login == null){
                msg = "Usuario invalido!";
                return false;
            }
            else if(Login.equals(login) && Senha.equals(senha)){

                return true;
            }else{
                msg = "Senha Invalida!";
                return false;
            }
        }catch(Exception ex){
            System.out.println("Passei aqui:"+ ex.toString());
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Error");
            dialogoInfo.setHeaderText(null);
            dialogoInfo.setContentText(ex.toString());
            dialogoInfo.showAndWait();
            msg = "Erro ao conectar com o banco de dados!";
            return false;
        }
    }
}
