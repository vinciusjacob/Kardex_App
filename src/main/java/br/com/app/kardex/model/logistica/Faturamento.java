package br.com.app.kardex.model.logistica;


import br.com.app.kardex.util.SqlServer;

import java.io.*;

/**
 *
 * @author Vinicius
 */
public class Faturamento {
    
    private SqlServer sql = new SqlServer();
    
    public void Importar(File arquivo, String titulo, String data) throws Exception{
        
        if(arquivo.exists()){
            String codtxt = cadTXT(titulo,data);
            FileReader leitorArquivo = new FileReader(arquivo.toString());
            BufferedReader bufferArquivo = new BufferedReader(leitorArquivo);
            String linha;
            while(true){
                linha = bufferArquivo.readLine();
                if(linha == null){
                    break;
                }
                String placa = linha.substring(485, 492);
                String viagem = linha.substring(492, 499);
                Integer codpro = Integer.parseInt(linha.substring(182, 197));
                String descrpro = linha.substring(197, 227);
                Integer Qtd = Integer.parseInt(linha.substring(227, 235));
                Qtd = Qtd/100;
                Double PesoBruto = Double.parseDouble(linha.substring(243, 253));
                PesoBruto = PesoBruto/1000;
                String Cnpj = (linha.substring(40, 55));
                String Razao = linha.substring(55, 90);
                String Endereco = linha.substring(90, 125);
                Integer Cep = Integer.parseInt(linha.substring(165, 173));
                String Cidade = linha.substring(145, 165);
                String Uf = linha.substring(283, 285);
                Integer Nf = Integer.parseInt(linha.substring(512, 521));
                if(!isNfExist(Nf,codpro,Qtd)){
                    Cadastrar(placa,viagem,codtxt,codpro,descrpro,Qtd,PesoBruto,Cnpj,Razao,Endereco,Cep,Cidade,Uf,Nf);
                }
                    
            }
        }
    }
    private void Cadastrar(String placa,String viagem,String CodTxt,Integer codpro,String descrpro, Integer qtd, Double PesoBruto, String Cnpj,String Razao,String Endereco,Integer Cep, String Cidade, String Uf, Integer Nf ) throws Exception{
        String query = "Insert into LOG_EXPEDICAO (EXP_PLACA,EXP_VIAGEM,EXP_CODTXT, EXP_CODPRO, EXP_DESCRPRO, EXP_QTD, EXP_UND, EXP_PESOBRUTO, EXP_CNPJ, EXP_RAZAO, EXP_ENDEREC, EXP_CEP,EXP_CIDADE, EXP_UF, EXP_NF, EXP_STATUS)"
            + "values ('"+placa+"', '"+viagem+"','"+CodTxt+"',"+codpro+",'"+descrpro+"',"+qtd+",'CX',"+PesoBruto+","+Cnpj+",'"+Razao+"','"+Endereco+"',"+Cep+",'"+Cidade+"','"+Uf+"',"+Nf+",'Pendente')";
        sql.SqlUpdate(query);
    }
    private Boolean isNfExist(Integer nota,Integer prod,Integer qtd) throws Exception{
        Boolean exist = false;
        String query = "SELECT * FROM LOG_EXPEDICAO WHERE EXP_NF = '"+nota+"' AND EXP_CODPRO = '"+prod+"' AND EXP_QTD = '"+qtd+"' ";
        if(sql.SqlNumRows(query) >= 1){
            return true;
        }
        return false;
    }
    private String cadTXT(String titulo, String data) throws Exception{
        String query = "INSERT INTO LOG_TXT (TXT_TITULO, TXT_DATA) "
                + "VALUES ('"+titulo+"', '"+data+"' )";
        sql.SqlUpdate(query);
        query = "SELECT * FROM LOG_TXT WHERE TXT_TITULO = '"+titulo+"' AND TXT_DATA = '"+data+"' ";
        sql.SqlQuery(query);
        String cod = "";
        while(sql.rs.next()){
            cod = sql.rs.getString("TXT_CODIGO");
        }
        sql.desconecta();
        return cod;
    }
    public void Exportar(File arquivo,String data) throws Exception{
        FileWriter arq = new FileWriter(arquivo.getPath());
        PrintWriter gravarArq = new PrintWriter(arq);
        String query = "Select * From LOG_ESCALA INNER JOIN LOG_EXPEDICAO on ESC_NOTA = EXP_NF where ESC_DATA = '"+data+"' ORDER BY EXP_VIAGEM ASC ";
        sql.SqlQuery(query);
        String txt;
        
        while (sql.rs.next()){
            int cod = sql.rs.getInt("EXP_COD");
            txt = sql.rs.getString("EXP_VIAGEM");
            txt = txt+";"+sql.rs.getString("ESC_PLACA");
            txt = txt+";"+sql.rs.getString("EXP_CODPRO");
            txt = txt+";"+sql.rs.getString("EXP_QTD");
            txt = txt+";"+sql.rs.getString("EXP_UND");
            txt = txt+";"+sql.rs.getString("EXP_CNPJ");
            txt = txt+";"+sql.rs.getString("EXP_RAZAO");
            txt = txt+";"+sql.rs.getString("EXP_ENDEREC");
            txt = txt+";"+sql.rs.getString("EXP_CEP");
            txt = txt+";"+sql.rs.getString("EXP_CIDADE");
            txt = txt+";"+sql.rs.getString("EXP_UF");
            txt = txt+";"+sql.rs.getString("EXP_NF");
            txt = txt+";"+sql.rs.getString("EXP_PESOBRUTO").replaceAll("\\.",",");
            txt = txt+";"+sql.rs.getString("ESC_NEWDATA");
            if (sql.rs.getString("ESC_PLACA") != null){
                gravarArq.println(txt);
                AltExpedicao(cod,"Atendido");
            }
        }
        sql.desconecta();
        arq.close();    
    }
    public void AltExpedicao(int Codigo,String Status) throws Exception{
        SqlServer sql = new SqlServer();
        String query = "Update LOG_EXPEDICAO set EXP_STATUS = '"+Status+"'  where EXP_COD = "+Codigo;
        sql.SqlUpdate(query);
    }
    
}
