/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.dao.nfe;

import br.com.app.kardex.model.kardex.NotaFiscal;
import br.com.app.kardex.model.kardex.NotaFiscalItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 *
 * @author Vinicius Jacob
 */
public class Nfe {
    private ObservableList<NotaFiscalItem> listaItem;
    private NotaFiscal notaFiscal;


    public NotaFiscal getNotaFiscal(){
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal){
        this.notaFiscal = notaFiscal;
    }

    public Boolean LerXml(File xml){
        try{

            notaFiscal = new NotaFiscal();
            listaItem = FXCollections.observableArrayList();

            notaFiscal.setFaturado(true);
            notaFiscal.setIdempresa(1);
            notaFiscal.setNumauxiliar("");
            notaFiscal.setNfreferencia("");
            notaFiscal.setObs("");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);

            //Dados chave de acesso
            NodeList nodeInf = doc.getDocumentElement().getElementsByTagName("infNFe");
            Element elementInf = (Element)nodeInf.item(0);
            notaFiscal.setChave(elementInf.getAttribute("Id"));
            
            //Dados identificação
            NodeList nodeIde = doc.getDocumentElement().getElementsByTagName("ide");
            Element elementIde = (Element)nodeIde.item(0);

            NodeList nodeIdeFilhos = elementIde.getChildNodes();
            for (int i = 0; i < nodeIdeFilhos.getLength(); i++) {
                Element elementIdeFilho = (Element)nodeIdeFilhos.item(i);
                
                switch(elementIdeFilho.getTagName()){
                    case "mod":
                        notaFiscal.setModelo(elementIdeFilho.getTextContent());
                        break;
                    case "nNF":
                        notaFiscal.setNumero(Integer.parseInt(elementIdeFilho.getTextContent()));
                        break;
                    case "tpNF":
                        notaFiscal.setTipo(Integer.parseInt(elementIdeFilho.getTextContent()));
                        break;
                    case "serie":
                        notaFiscal.setSerie(elementIdeFilho.getTextContent());
                        break;
                    case "dhEmi":
                        notaFiscal.setDtemissao(elementIdeFilho.getTextContent().substring(0,10));
                        break;
                    case "dhSaiEnt":
                        notaFiscal.setDtsaida(elementIdeFilho.getTextContent().substring(0,10));
                        break;
                }
            }

            //Dados emitente
            NodeList nodeEmit = doc.getDocumentElement().getElementsByTagName("emit");
            Element elementEmit = (Element)nodeEmit.item(0);

            NodeList nodeCnpjEmit = elementEmit.getChildNodes();
            for (int i = 0; i < nodeCnpjEmit.getLength(); i++) {
                Element elementCnpj = (Element)nodeCnpjEmit.item(i);
                switch(elementCnpj.getTagName()){
                    case "CNPJ":
                        notaFiscal.setCnpj_cpf_emitente(elementCnpj.getTextContent());
                        break;
                }
            }

            //Dados destinatario
            NodeList nodeDest = doc.getDocumentElement().getElementsByTagName("dest");
            Element elementDest = (Element)nodeDest.item(0);

            NodeList nodeCnpjDest = elementDest.getChildNodes();
            for (int i = 0; i < nodeCnpjDest.getLength(); i++) {
                Element elementCnpj = (Element)nodeCnpjDest.item(i);
                if(elementCnpj.getTagName().equals("CNPJ")){
                    notaFiscal.setCnpj_cpf_destinatario(elementCnpj.getTextContent());
                    break;
                }
            }
            
            //Dados itens da nota
            NodeList nodeDet = doc.getDocumentElement().getElementsByTagName("det");
            for (int i = 0; i < nodeDet.getLength(); i++) {
                NotaFiscalItem notaFiscalItem = new NotaFiscalItem();

                Element elementDet = (Element)nodeDet.item(i);
                NodeList nodeItens = elementDet.getChildNodes();
                for (int j = 0; j < nodeItens.getLength(); j++) {
                    Element elementIten = (Element)nodeItens.item(j);
                    if(elementIten.getTagName().equals("prod")){
                        NodeList nodeProd = elementIten.getChildNodes();
                        for (int k = 0; k < nodeProd.getLength(); k++) {
                            Element elementProd = (Element)nodeProd.item(k);

                            notaFiscalItem.setQtdcx_corte(0);
                            notaFiscalItem.setQtdprod_corte(0.0);
                            notaFiscalItem.setPeso_corte(0.0);
                            notaFiscalItem.setCst("");
                            notaFiscalItem.setObs("");

                            switch(elementProd.getTagName()){
                                case "cProd":
                                    notaFiscalItem.setCodfabrica(elementProd.getTextContent());
                                    break;
                                case "cEAN":
//                                    nfeItem.setEan(elementProd.getTextContent());
                                    break;
                                case "NCM":
                                    notaFiscalItem.setNcm(elementProd.getTextContent());
                                    break;
                                case "CFOP":
                                    notaFiscalItem.setIdcfop(Integer.parseInt(elementProd.getTextContent()));
                                    break;
                                case "uCom":
                                    notaFiscalItem.setUnidade("");
                                    break;
                                case "qCom":
                                    notaFiscalItem.setQtdprod(Double.parseDouble(elementProd.getTextContent()));
                                    notaFiscalItem.setQtdcx(0);
                                    notaFiscalItem.setPeso(0.0);
                                    break;
                                case "vUnCom":
                                    notaFiscalItem.setPrunitliq(Double.parseDouble(elementProd.getTextContent()));
                                    notaFiscalItem.setPrunit(Double.parseDouble(elementProd.getTextContent()));
                                    break;
                                case "vProd":
                                    notaFiscalItem.setValorliq(Double.parseDouble(elementProd.getTextContent()));
                                    notaFiscalItem.setValorbruto(Double.parseDouble(elementProd.getTextContent()));
                                    notaFiscalItem.setDesconto(0.0);
                                    break;
                            }
                        }
                            
                    }
                    
                }
                listaItem.add(notaFiscalItem);
            }
            notaFiscal.setListaItem(listaItem);
            
            //Dados veicculo
//            NodeList nodeVeic = doc.getDocumentElement().getElementsByTagName("veicTransp");
//            Element elementVeic = (Element)nodeVeic.item(0);
//
//            NodeList nodePlaca = elementVeic.getChildNodes();
//
//            for (int i = 0; i < nodePlaca.getLength(); i++) {
//                Element elementPlaca = (Element)nodePlaca.item(i);
//                if(elementPlaca.getTagName().equals("placa")){
//                    placa = elementPlaca.getTextContent();
//                    break;
//                }
//            }
//            System.out.println("Passei aqui VEICULO");
            
            return true;
        }catch(Exception ex){
            System.out.println("Error XML: "+ex.toString());
            return false;
        }
    }   
}

