package br.com.app.kardex.model.cadastros;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutoFator {
    private int idfator;
    private String codfabrica;
    private String cnpjfornecedor;
    private int idproduto;
    private Double fator;

    public int getIdfator(){return idfator; }
    public void  setIdfator(int idfator){this.idfator = idfator; }
    public String getCodfabrica(){return codfabrica; }
    public void  setCodfabrica(String codfabrica){this.codfabrica = codfabrica; }
    public String getCnpjfornecedor(){return cnpjfornecedor; }
    public void  setCnpjfornecedor(String cnpjfornecedor){this.cnpjfornecedor = cnpjfornecedor; }
    public int getIdproduto(){return idproduto; }
    public void  setIdproduto(int idproduto){this.idproduto = idproduto; }
    public Double getFator(){return fator; }
    public void  setFator(Double fator){this.fator = fator; }

    public ProdutoFator(){ }
    public ProdutoFator(int idfator, String codfabrica, String cnpjfornecedor, int idproduto, Double fator){
        this.idfator = idfator;
        this.codfabrica = codfabrica;
        this.cnpjfornecedor = cnpjfornecedor;
        this.idproduto = idproduto;
        this.fator = fator;
    }

    public ObservableList<ProdutoFator> getLista(){
        ObservableList<ProdutoFator> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_PRODUTO_FATOR ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new ProdutoFator(sql.rs.getInt("IDFATOR"),sql.rs.getString("CODFABRICA"),sql.rs.getString("CNPJFORNECEDOR"),sql.rs.getInt("IDPRODUTO"),sql.rs.getDouble("FATOR")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public ProdutoFator get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_PRODUTO_FATOR WHERE IDFATOR = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new ProdutoFator(sql.rs.getInt("IDFATOR"),sql.rs.getString("CODFABRICA"),sql.rs.getString("CNPJFORNECEDOR"),sql.rs.getInt("IDPRODUTO"),sql.rs.getDouble("FATOR"));
        }catch(Exception ex){
            return null;
        }
    }

    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            idfator = sql.SqlUpdateId("INSERT INTO TBL_PRODUTO_FATOR (CODFABRICA,CNPJFORNECEDOR,IDPRODUTO,FATOR) VALUES ("
                    + "'"+ codfabrica +"',"
                    + "'"+ cnpjfornecedor +"',"
                    + "'"+ idproduto +"',"
                    + "'"+ fator +"')" );
            return null;
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return "Não foi possivel cadastrar ProdutoFator!";
        }
    }

    public String update(){return null;}

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_PRODUTO_FATOR WHERE IDFATOR = '"+ idfator +"'");
            return null;
        }catch(Exception ex){
            return "Não foi possivel excluir ProdutoFator!";
        }
    }

    public ProdutoFator get(String codfabrica,String cnpjfornecedor){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_PRODUTO_FATOR WHERE CODFABRICA = '"+codfabrica+"' AND CNPJFORNECEDOR = '"+cnpjfornecedor+"' ";
            sql.SqlQuery(query);
            sql.rs.next();
            return new ProdutoFator(sql.rs.getInt("IDFATOR"),sql.rs.getString("CODFABRICA"),sql.rs.getString("CNPJFORNECEDOR"),sql.rs.getInt("IDPRODUTO"),sql.rs.getDouble("FATOR"));
        }catch(Exception ex){
            return null;
        }
    }

}
