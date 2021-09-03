package br.com.app.kardex.model.kardex;

import br.com.app.kardex.model.cadastros.Produto;
import br.com.app.kardex.model.login.Logon;
import br.com.app.kardex.model.logistica.Estoque;
import br.com.app.kardex.util.MsgBox;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.time.LocalDate;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class ImportarPedidos {

    public String ImportarCencosud(File arquivo,String dtFat){
        return "";
    }

    public String ImportarVigor(File arquivo, String dtFat, MsgBox msgBox){
        try{
            if(arquivo.exists()){

                //CRIAR ARQUIVO DE PROPRIEDADE
                String local = new File(".").getCanonicalPath();

                //QTD DE LINHAS DO ARQUIVO
                Platform.runLater(() -> msgBox.setMensage("[0%]Verificando tamenho do arquivo..."));
                File arquivoLeitura = arquivo;
                LineNumberReader linhaLeitura = new LineNumberReader(new FileReader(arquivoLeitura));
                linhaLeitura.skip(arquivoLeitura.length());
                int qtdLinha = linhaLeitura.getLineNumber();
                int numLinha = 1;


                //LER ARQUIVO DE TEXTO
                Platform.runLater(() -> msgBox.setMensage("[0%]Iniciando leitura do arquivo..."));
                FileReader leitorArquivo = new FileReader(arquivo.toString());
                BufferedReader bufferArquivo = new BufferedReader(leitorArquivo);
                String linha;

                Carga carga = new Carga(); //CLASSE CARGA
                String numCarga = "";
                String numPedido = "";
                String newPedido = "";
                NotaFiscal nota = new NotaFiscal(); //CLASSE NOTAFISCAL
                NotaFiscalItem notaItem = new NotaFiscalItem(); //CLASSE NOTAFISCALITEM
                NotaFiscalItemLote lote;
                int iddepartamento = 9; //DEPARTAMENTO VIGOR
                String modelo = "55"; //MODELO PADRÃO
                String serie = "1"; //SERIE PADRÃO
                int idcfop = 5949; // CFOP PADRÃO
                int tipo = 2; //TIPO PADRÃO[SAIDA]


                while(true){
                    numLinha++;
                    Double perc = ((double)numLinha / (double)qtdLinha) * 100.0;
                    System.out.println("["+perc.intValue()+"%] Importando arquivo...");
                    Platform.runLater(() -> msgBox.setMensage("["+perc.intValue()+"%] Importando arquivo..."));
                    linha = bufferArquivo.readLine();
                    if(linha == null){
                        break;
                    }
                    String dados[] = linha.split(Pattern.quote(";"));
                    String shelf = "";

                    //INSERIR CARGA
                    if(!dados[0].equals("PLACA") && !dados[1].equals("NUM_CARREGAMENTO")) {
                        if(!numCarga.equals(dados[1].substring(0, 7))){
                            numCarga = dados[1].substring(0, 7);
                            numPedido = "new";
                            carga = new Carga();
                            carga.setTipo(2);
                            carga.setDescricao(numCarga);
                            carga.setTransportadora("");
                            carga.setMotorista("");
                            carga.setPlaca("");
                            carga.setIdusuario(Logon.getUsuario().getIdusuario());
                            carga.setIdconferente(0);
                            carga.setIdempresa(Logon.getIdempresa());
                            carga.setIddepartamento(iddepartamento);
                            carga.setEncerrada(false);
                            carga.setNum_lacre("");
                            carga.setStatus("Pendente");
                            carga.setQtdpallet(0);
                            carga.setTemperaturabau(0.0);
                            carga.setPesoinicial(0.0);
                            carga.setPesofinal(0.0);
                            carga.setDtcarga(dados[2]);
                            carga.setDtmovimento(dados[2]);
                            carga.insert();
                        }
                        //VERIFICA SE É REENTREGA
                        String reentrega = "";
                        if(dados.length >= 7){
                            if(null != dados[6] && !dados[6].isEmpty()){
                                reentrega = " Reentrega NF:"+dados[6];
                            }
                        }

                        //VERIFICA SE TEM SUBPLACA
                        if(dados.length >= 8){
                            newPedido = dados[7];
                        }else{
                            newPedido = "";
                        }

                        //INSERIR NOTA
                        if(!numPedido.equals(newPedido)){
                            numPedido = newPedido;
                            nota = new NotaFiscal();
                            nota.setIdcarga(carga.getIdcarga());
                            nota.setTipo(2);
                            nota.setNumero(0);
                            nota.setNumauxiliar(numPedido);
                            nota.setSerie(serie);
                            nota.setModelo(modelo);
                            nota.setDtsaida(dtFat);
                            nota.setDtemissao(dtFat);
                            nota.setChave("");
                            nota.setIdparceiro(0);
                            nota.setIdempresa(Logon.getIdempresa());
                            nota.setObs("");
                            nota.setCnpj_cpf_destinatario("");
                            nota.setCnpj_cpf_emitente("");
                            nota.setNfreferencia("");
                            nota.setFaturado(false);
                            nota.setIddepartamento(iddepartamento);
                            nota.insert();
                        }


                        Produto produto = new Produto().get(dados[3],iddepartamento);
                        String obs;

                        //VERIFICA SE É DATA ESPECIFICA
                        if(dados.length >= 9 && dados[8].equals("0,66")){
                            Double percShelf = Double.parseDouble(dados[8].replace(",","."));
                            Double shelfRestante = produto.getShelf()*percShelf;
                            LocalDate date = LocalDate.now();
                            String vencimento = date.plusDays(shelfRestante.intValue()).toString();
                            shelf = vencimento;
                            obs = "Data especifica;";
                        }else{
                            LocalDate date = LocalDate.now();
                            shelf = date.plusDays(produto.getDirecionadodias()).toString();
                            obs = "";
                        }

                        //VERIFICA SE É AVARIA
                        int avaria = 0;
                        if(dados.length >= 10){
                            obs = "Avaria";
                            avaria  = 1;
                        }

//                        int qtdcx = new Estoque().getSumQtdCx(produto.getIdproduto(),iddepartamento,shelf);


                        //INSERRIR ITENS
                        notaItem = new NotaFiscalItem();
                        notaItem.setIdnota(nota.getIdnota());
                        notaItem.setIdproduto(produto.getIdproduto());
                        notaItem.setUnidade(produto.getUnidade());
                        notaItem.setQtdcx(0);
                        notaItem.setQtdprod(0.0);
                        notaItem.setPeso(0.0);
                        notaItem.setQtdcx_corte(parseInt(dados[5]));
                        notaItem.setQtdprod_corte(parseInt(dados[5])* produto.getQtdundcx());
                        notaItem.setPeso_corte(parseInt(dados[5]) * produto.getPesocx());
                        notaItem.setPrunit(0.0);
                        notaItem.setPrunitliq(0.0);
                        notaItem.setValorliq(0.0);
                        notaItem.setDesconto(0.0);
                        notaItem.setValorbruto(0.0);
                        notaItem.setNcm("");
                        notaItem.setCst("");
                        notaItem.setIdcfop(idcfop);
                        notaItem.setObs(obs+reentrega);
                        notaItem.insert();

                        if(notaItem.getQtdcx() == 0) {

                            //INSERIR LOTES
                            lote = new NotaFiscalItemLote();
                            lote.setIdcarga(carga.getIdcarga());
                            lote.setIdnota(nota.getIdnota());
                            lote.setIdproduto(produto.getIdproduto());
                            lote.setTipo(2);
                            lote.setObs(obs);
                            lote.setFalta(false);
                            lote.setSobra(false);
                            lote.setIdnotaitem(notaItem.getIdnotaitem());

                            ObservableList<Estoque> lista = new Estoque().getLista(produto.getIdproduto(), iddepartamento,shelf, avaria);
                            int qtdcx = parseInt(dados[5]);
                            for (Estoque est : lista) {
                                if (est.getQtdcx() >= qtdcx) {
                                    if (est.getNum_lote() == null) {
                                        lote.setNum_lote("");
                                    } else {
                                        lote.setNum_lote(est.getNum_lote());
                                    }
                                    lote.setAvariado(est.getAvariado());
                                    lote.setIdestoque(est.getIdestoque());
                                    lote.setDtfabricacao(est.getDtfabricacao());
                                    lote.setDtvencimento(est.getDtvencimento());
                                    lote.setQtdcx(qtdcx);
                                    lote.setQtdprod(qtdcx * produto.getQtdundcx());
                                    lote.setPeso(qtdcx * produto.getPesocx());
                                    lote.insert();
                                    break;
                                } else if (est.getQtdcx() < qtdcx) {
                                    if (est.getNum_lote() == null) {
                                        lote.setNum_lote("");
                                    } else {
                                        lote.setNum_lote(est.getNum_lote());
                                    }
                                    lote.setAvariado(est.getAvariado());
                                    lote.setIdestoque(est.getIdestoque());
                                    lote.setDtfabricacao(est.getDtfabricacao());
                                    lote.setDtvencimento(est.getDtvencimento());
                                    lote.setQtdcx(est.getQtdcx());
                                    lote.setQtdprod(est.getQtdcx() * produto.getQtdundcx());
                                    lote.setPeso(qtdcx * produto.getPesocx());
                                    lote.insert();
                                    qtdcx = qtdcx - est.getQtdcx();
                                }
                            }
                        }
                    }
                }
                return null;
            }else{
                return "Arquivo invalido!";
            }

        }catch (Exception ex){
            System.out.println("Error: "+ex.toString());
            return "Falha ao ler arquivo!";
        }
    }

    public String ImportarMarba(File arquivo){
        try{
            if(arquivo.exists()){

                //LER ARQUIVO DE TEXTO
                FileReader leitorArquivo = new FileReader(arquivo.toString());
                BufferedReader bufferArquivo = new BufferedReader(leitorArquivo);
                String linha;

                Carga carga = new Carga(); //CLASSE CARGA
                NotaFiscal nota = new NotaFiscal(); //CLASSE NOTAFISCAL
                NotaFiscalItem notaItem = new NotaFiscalItem(); //CLASSE NOTAFISCALITEM
                int iddepartamento = 10; //DEPARTAMENTO MARBA
                String modelo = "55"; //MODELO PADRÃO
                String serie = "1"; //SERIE PADRÃO
                int idcfop = 5949; // CFOP PADRÃO
                int tipo = 2; //TIPO PADRÃO[SAIDA]

                while(true){
                    linha = bufferArquivo.readLine();
                    if(linha == null){
                        break;
                    }
                    String identificador = linha.substring(0,1);
                    String cnpj = linha.substring(1,15);

                    if(identificador.equals("1")) {
                        if (!cnpj.equals("61270393000652")) {
                            return "Formato do arquivo invalido!";
                        }
                        System.out.println(identificador);
                        System.out.println(cnpj);

                        carga = new Carga();
                        carga.setTipo(2);
                        carga.setDescricao("CARREGAMENTO MARBA");
                        carga.setTransportadora("");
                        carga.setMotorista("");
                        carga.setPlaca("");
                        carga.setIdusuario(0);
                        carga.setIdconferente(3);
                        carga.setEncerrada(false);
                        carga.setIdempresa(1);
                        carga.setIddepartamento(iddepartamento);
                        carga.setDoca(0);
                        carga.setNum_lacre("");
                        carga.setStatus("Pendente");
                        carga.insert();
                    }else if(identificador.equals("2")){
                        nota = new NotaFiscal();
                        nota.setIdcarga(carga.getIdcarga());
                        nota.setTipo(2);
                        nota.setNumero(parseInt(linha.substring(167,177).trim()));
                        nota.setNumauxiliar(linha.substring(167,177).trim());
                        nota.setSerie("1");
                        nota.setModelo("55");
                        nota.setDtemissao(linha.substring(177,179)+"/"+linha.substring(179,181)+"/"+linha.substring(181,183));
                        nota.setDtsaida(linha.substring(177,179)+"/"+linha.substring(179,181)+"/"+linha.substring(181,183));
                        nota.setChave("");
                        nota.setIdempresa(1);
                        nota.setIdparceiro(0);
                        nota.setObs("");
                        nota.setCnpj_cpf_destinatario(linha.substring(250,264));
                        nota.setCnpj_cpf_emitente("");
                        nota.setFaturado(false);
                        nota.setIddepartamento(iddepartamento);
                        nota.setNfreferencia("");
                        nota.insert();
                    }else if(identificador.equals("3")){
                        notaItem = new NotaFiscalItem();
                        notaItem.setIdnota(nota.getIdnota());
                        Produto produto = new Produto().get(linha.substring(109,129).trim(),10);
                        notaItem.setIdproduto(produto.getIdproduto());
                        notaItem.setUnidade(produto.getUnidade());
                        notaItem.setQtdprod(0.0);
                        notaItem.setQtdcx(0);
                        notaItem.setPeso(0.0);

                        int qtdCx = parseInt(linha.substring(61,65));
                        Double qtdProd = qtdCx * produto.getQtdundcx();

                        notaItem.setQtdcx_corte(qtdCx);
                        notaItem.setQtdprod_corte(qtdProd);
                        notaItem.setPeso_corte(Double.parseDouble(linha.substring(78,85)+"."+linha.substring(85,88)));
                        notaItem.setPrunit(Double.parseDouble(linha.substring(65,73)+"."+linha.substring(73,78)));
                        notaItem.setPrunitliq(Double.parseDouble(linha.substring(65,73)+"."+linha.substring(73,78)));
                        notaItem.setValorliq(0.0);
                        notaItem.setDesconto(0.0);
                        notaItem.setValorbruto(0.0);
                        notaItem.setNcm("0");
                        notaItem.setCst("0");
                        notaItem.setIdcfop(5949);
                        notaItem.setObs("");
                        notaItem.insert();
                    }
                }
                return null;
            }else{
                return "Arquivo invalido!";
            }
        }catch(Exception ex){
            System.out.println("Error: "+ex.toString());
            return "Falha ao ler arquivo!";
        }


    }

}
