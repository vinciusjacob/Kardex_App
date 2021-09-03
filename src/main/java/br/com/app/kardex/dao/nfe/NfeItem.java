/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.dao.nfe;

/**
 *
 * @author Vinicius Jacob
 */
public class NfeItem {
    private String id;
    private String cod;
    private String ean;
    private String ncm;
    private String cfop;
    private String und;
    private String qtd;
    private String valorUnit;
    private String valorTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public String getUnd() {
        return und;
    }

    public void setUnd(String und) {
        this.und = und;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(String valorUnit) {
        this.valorUnit = valorUnit;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }
    public NfeItem(){
        
    }
    public NfeItem(String id, String cod, String ean, String ncm, String cfop, String und, String qtd, String valorUnit, String valorTotal) {
        this.id = id;
        this.cod = cod;
        this.ean = ean;
        this.ncm = ncm;
        this.cfop = cfop;
        this.und = und;
        this.qtd = qtd;
        this.valorUnit = valorUnit;
        this.valorTotal = valorTotal;
    }
}
