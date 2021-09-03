package br.com.app.kardex.util;

import java.sql.*;

public class SqlServer {
//    public final String bd = "KARDEX";
    public final String bd = "KARDEX_TESTE";
    private String usuario = "sa";
    private String senha = "#abc123#";
    private String servidor = "SRV01008";
    private Connection con;
    public Statement st;
    public ResultSet rs;


    public void Conecta() throws SQLException{
        con = DriverManager.getConnection("jdbc:sqlserver://"+servidor+";DatabaseName="+bd+";", usuario, senha);
        st = con.createStatement();
    }

    public void desconecta() throws SQLException {
        if(con!=null){
            con.close();
        }
    }
    /**
     * Execulta comandos sql: inserir/alterar/excluir
     * @param query Script sql server.
     * @throws java.lang.Exception
     * @return Retorna id
     */
    public int SqlUpdateId(String query) throws Exception{
        Conecta();
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        int lastId = 0;
        if(rs.next()){
            lastId = rs.getInt(1);
        }
        desconecta();
        return lastId;

    }
    /**
     * Execulta comandos sql: inserir/alterar/excluir
     * @param sql
     * @throws java.lang.Exception
     */
    public void SqlUpdate(String sql) throws Exception{
        Conecta();
        st.executeUpdate(sql);
        desconecta();
    }
    /**
     * Execulta consultas sql, e retorna um ResultSet
     * @param sql
     * @throws java.lang.Exception
     */
    public void SqlQuery(String sql) throws Exception{
        Conecta();
        rs = st.executeQuery(sql);
    }
    /**
     * Obtém o número de linhas da um resultado
     * @param sql
     * @return
     */
    public int SqlNumRows(String sql) throws Exception{
        int linha = 0;
        Conecta();
        rs = st.executeQuery(sql);
        while (rs.next()){
            linha++;
        }
        desconecta();
        return linha;
    }

    public void Latency(){
//        Socket t = new Socket();
//        DataInputStream dis= new DataInputStream(t.getInputStream());
//        PrintStream ps = new PrintStream(t.getOutputStream());
//        Date before = new Date(System.currentTimeMillis());
//        ps.println();
//        dis.read();
//        Date after = new Date(System.currentTimeMillis());
//        long tempo = (after.getTime() - before.getTime());
//        System.out.println("Tempo de resposta: " + tempo + " ms");
//        t.close();
//        Thread.sleep(1000);
    }

    /** OPERÇÕES ADAPTADAS PARA USAR ROLLBACK **/
    public Savepoint ConectaR() throws SQLException{
        con.setAutoCommit(false);
        con = DriverManager.getConnection("jdbc:sqlserver://"+servidor+";DatabaseName="+bd+";", usuario, senha);
        st = con.createStatement();
        return con.setSavepoint();
    }

    public void Commit()throws SQLException{
        con.commit();
    }

    public void RollBack(Savepoint save1) throws SQLException{
        con.rollback(save1);
    }
}
