package br.com.app.kardex.model.cadastros;

import br.com.app.kardex.util.SqlServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Produto {
    private int idproduto;
    private String codfabrica;
    private String codauxiliar;
    private String sigla;
    private String descricao;
    private String embalagem;
    private String unidade;
    private Double pesoun;
    private Double pesocx;
    private Double pesoembalagem;
    private Double qtdundcx;
    private int iddepartamento;
    private int idcategoria;
    private int shelf;
    private int desativado;
    private int direcionadodias;
    private int idfamilia;
    private int idfornecedor;
    private String codigo_barras_dun;
    private String codigo_barras_ean;
    private String ncm;
    private Boolean envia_palm;
    private Boolean peso_variavel;

    public int getIdproduto(){return idproduto; }
    public void  setIdproduto(int idproduto){this.idproduto = idproduto; }
    public String getCodfabrica(){return codfabrica; }
    public void  setCodfabrica(String codfabrica){this.codfabrica = codfabrica; }
    public String getCodauxiliar(){return codauxiliar; }
    public void  setCodauxiliar(String codauxiliar){this.codauxiliar = codauxiliar; }
    public String getSigla(){return sigla; }
    public void  setSigla(String sigla){this.sigla = sigla; }
    public String getDescricao(){return descricao; }
    public void  setDescricao(String descricao){this.descricao = descricao; }
    public String getEmbalagem(){return embalagem; }
    public void  setEmbalagem(String embalagem){this.embalagem = embalagem; }
    public String getUnidade(){return unidade; }
    public void  setUnidade(String unidade){this.unidade = unidade; }
    public Double getPesoun(){return pesoun; }
    public void  setPesoun(Double pesoun){this.pesoun = pesoun; }
    public Double getPesocx(){return pesocx; }
    public void  setPesocx(Double pesocx){this.pesocx = pesocx; }
    public Double getPesoembalagem(){return pesoembalagem; }
    public void  setPesoembalagem(Double pesoembalagem){this.pesoembalagem = pesoembalagem; }
    public Double getQtdundcx(){return qtdundcx; }
    public void  setQtdundcx(Double qtdundcx){this.qtdundcx = qtdundcx; }
    public int getIddepartamento(){return iddepartamento; }
    public void  setIddepartamento(int iddepartamento){this.iddepartamento = iddepartamento; }
    public int getIdcategoria(){return idcategoria; }
    public void  setIdcategoria(int idcategoria){this.idcategoria = idcategoria; }
    public int getShelf(){return shelf; }
    public void  setShelf(int shelf){this.shelf = shelf; }
    public int getDesativado(){return desativado; }
    public void  setDesativado(int desativado){this.desativado = desativado; }
    public int getDirecionadodias(){return direcionadodias; }
    public void  setDirecionadodias(int direcionadodias){this.direcionadodias = direcionadodias; }
    public int getIdfamilia(){return idfamilia; }
    public void  setIdfamilia(int idfamilia){this.idfamilia = idfamilia; }
    public int getIdfornecedor(){return idfornecedor; }
    public void  setIdfornecedor(int idfornecedor){this.idfornecedor = idfornecedor; }
    public String getCodigo_barras_dun(){return codigo_barras_dun; }
    public void  setCodigo_barras_dun(String codigo_barras_dun){this.codigo_barras_dun = codigo_barras_dun; }
    public String getCodigo_barras_ean(){return codigo_barras_ean; }
    public void  setCodigo_barras_ean(String codigo_barras_ean){this.codigo_barras_ean = codigo_barras_ean; }
    public String getNcm(){return ncm; }
    public void  setNcm(String ncm){this.ncm = ncm; }
    public Boolean getEnvia_palm(){return envia_palm; }
    public void  setEnvia_palm(Boolean envia_palm){this.envia_palm = envia_palm; }
    public Boolean getPeso_variavel(){return peso_variavel; }
    public void  setPeso_variavel(Boolean peso_variavel){this.peso_variavel = peso_variavel; }

    public Produto(){ }
    public Produto(int idproduto, String codfabrica, String codauxiliar, String sigla, String descricao, String embalagem, String unidade, Double pesoun, Double pesocx, Double pesoembalagem, Double qtdundcx, int iddepartamento, int idcategoria, int shelf, int desativado, int direcionadodias, int idfamilia, int idfornecedor, String codigo_barras_dun, String codigo_barras_ean, String ncm, Boolean envia_palm, Boolean peso_variavel){
        this.idproduto = idproduto;
        this.codfabrica = codfabrica;
        this.codauxiliar = codauxiliar;
        this.sigla = sigla;
        this.descricao = descricao;
        this.embalagem = embalagem;
        this.unidade = unidade;
        this.pesoun = pesoun;
        this.pesocx = pesocx;
        this.pesoembalagem = pesoembalagem;
        this.qtdundcx = qtdundcx;
        this.iddepartamento = iddepartamento;
        this.idcategoria = idcategoria;
        this.shelf = shelf;
        this.desativado = desativado;
        this.direcionadodias = direcionadodias;
        this.idfamilia = idfamilia;
        this.idfornecedor = idfornecedor;
        this.codigo_barras_dun = codigo_barras_dun;
        this.codigo_barras_ean = codigo_barras_ean;
        this.ncm = ncm;
        this.envia_palm = envia_palm;
        this.peso_variavel = peso_variavel;
    }

    public ObservableList<Produto> getLista(){
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_PRODUTO ";
            sql.SqlQuery(query);
            while(sql.rs.next()){
                lista.add(new Produto(sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("CODAUXILIAR"),sql.rs.getString("SIGLA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("EMBALAGEM"),sql.rs.getString("UNIDADE"),sql.rs.getDouble("PESOUN"),sql.rs.getDouble("PESOCX"),sql.rs.getDouble("PESOEMBALAGEM"),sql.rs.getDouble("QTDUNDCX"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getInt("IDCATEGORIA"),sql.rs.getInt("SHELF"),sql.rs.getInt("DESATIVADO"),sql.rs.getInt("DIRECIONADODIAS"),sql.rs.getInt("IDFAMILIA"),sql.rs.getInt("IDFORNECEDOR"),sql.rs.getString("CODIGO_BARRAS_DUN"),sql.rs.getString("CODIGO_BARRAS_EAN"),sql.rs.getString("NCM"),sql.rs.getBoolean("ENVIA_PALM"),sql.rs.getBoolean("PESO_VARIAVEL")));
            }
            sql.desconecta();
            return lista;
        }catch(Exception ex){
            return null;
        }
    }

    public Produto get(int id){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_PRODUTO WHERE IDPRODUTO = '"+id+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new Produto(sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("CODAUXILIAR"),sql.rs.getString("SIGLA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("EMBALAGEM"),sql.rs.getString("UNIDADE"),sql.rs.getDouble("PESOUN"),sql.rs.getDouble("PESOCX"),sql.rs.getDouble("PESOEMBALAGEM"),sql.rs.getDouble("QTDUNDCX"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getInt("IDCATEGORIA"),sql.rs.getInt("SHELF"),sql.rs.getInt("DESATIVADO"),sql.rs.getInt("DIRECIONADODIAS"),sql.rs.getInt("IDFAMILIA"),sql.rs.getInt("IDFORNECEDOR"),sql.rs.getString("CODIGO_BARRAS_DUN"),sql.rs.getString("CODIGO_BARRAS_EAN"),sql.rs.getString("NCM"),sql.rs.getBoolean("ENVIA_PALM"),sql.rs.getBoolean("PESO_VARIAVEL"));
        }catch(Exception ex){
            return null;
        }
    }

    public String insert(){
        try{
            SqlServer sql = new SqlServer();
            idproduto = sql.SqlUpdateId("INSERT INTO TBL_PRODUTO (CODFABRICA,CODAUXILIAR,SIGLA,DESCRICAO,EMBALAGEM,UNIDADE,PESOUN,PESOCX,PESOEMBALAGEM,QTDUNDCX,IDDEPARTAMENTO,IDCATEGORIA,SHELF,DESATIVADO,DIRECIONADODIAS,IDFAMILIA,IDFORNECEDOR,CODIGO_BARRAS_DUN,CODIGO_BARRAS_EAN,NCM,ENVIA_PALM,PESO_VARIAVEL) VALUES ("
                    + "'"+ codfabrica +"',"
                    + "'"+ codauxiliar +"',"
                    + "'"+ sigla +"',"
                    + "'"+ descricao +"',"
                    + "'"+ embalagem +"',"
                    + "'"+ unidade +"',"
                    + "'"+ pesoun +"',"
                    + "'"+ pesocx +"',"
                    + "'"+ pesoembalagem +"',"
                    + "'"+ qtdundcx +"',"
                    + "'"+ iddepartamento +"',"
                    + "'"+ idcategoria +"',"
                    + "'"+ shelf +"',"
                    + "'"+ desativado +"',"
                    + "'"+ direcionadodias +"',"
                    + "'"+ idfamilia +"',"
                    + "'"+ idfornecedor +"',"
                    + "'"+ codigo_barras_dun +"',"
                    + "'"+ codigo_barras_ean +"',"
                    + "'"+ ncm +"',"
                    + "'"+ envia_palm +"',"
                    + "'"+ peso_variavel +"')" );
            return null;
        }catch(Exception ex){
            return "Não foi possivel cadastrar Produto!";
        }
    }

    public String update(){return null;}

    public String delete(){
        try{
            SqlServer sql = new SqlServer();
            sql.SqlUpdate("DELETE TBL_PRODUTO WHERE IDPRODUTO = '"+ idproduto +"'");
            return null;
        }catch(Exception ex){
            return "Não foi possivel excluir Produto!";
        }
    }

    /**
     * Busca produto deacordo com o codigo de fabrica
     * @param codfabrica
     * @return Produto
     */
    public Produto get(String codfabrica,int iddep){
        try{
            SqlServer sql = new SqlServer();
            String query = "SELECT * FROM TBL_PRODUTO WHERE CODFABRICA = '"+codfabrica+"' AND IDDEPARTAMENTO = '"+iddep+"'";
            sql.SqlQuery(query);
            sql.rs.next();
            return new Produto(sql.rs.getInt("IDPRODUTO"),sql.rs.getString("CODFABRICA"),sql.rs.getString("CODAUXILIAR"),sql.rs.getString("SIGLA"),sql.rs.getString("DESCRICAO"),sql.rs.getString("EMBALAGEM"),sql.rs.getString("UNIDADE"),sql.rs.getDouble("PESOUN"),sql.rs.getDouble("PESOCX"),sql.rs.getDouble("PESOEMBALAGEM"),sql.rs.getDouble("QTDUNDCX"),sql.rs.getInt("IDDEPARTAMENTO"),sql.rs.getInt("IDCATEGORIA"),sql.rs.getInt("SHELF"),sql.rs.getInt("DESATIVADO"),sql.rs.getInt("DIRECIONADODIAS"),sql.rs.getInt("IDFAMILIA"),sql.rs.getInt("IDFORNECEDOR"),sql.rs.getString("CODIGO_BARRAS_DUN"),sql.rs.getString("CODIGO_BARRAS_EAN"),sql.rs.getString("NCM"),sql.rs.getBoolean("ENVIA_PALM"),sql.rs.getBoolean("PESO_VARIAVEL"));
        }catch(Exception ex){
            System.out.println("Error: Produto.get."+ex.toString());
            return null;
        }
    }

}

