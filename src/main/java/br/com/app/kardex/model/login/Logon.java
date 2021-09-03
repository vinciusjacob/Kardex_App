package br.com.app.kardex.model.login;

public class Logon {

    private static int idempresa;
    private static Usuario usuario;
    private static String dataLogin;

    public Logon() {

    }

    public static int getIdempresa() {
        return idempresa;
    }

    public static void setIdempresa(int idempresa) {
        Logon.idempresa = idempresa;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Logon.usuario = usuario;
    }

    public static String getDataLogin() {
        return dataLogin;
    }

    public static void setDataLogin(String dataLogin) {
        Logon.dataLogin = dataLogin;
    }
}
