/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.util;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Vinicius Jacob
 */
public class KTextField extends TextField{

    
    private Boolean required = false;
    private Boolean upperCase = false;
    private int maxLenght = 0;
    private String requiredText = "Campo obrigatorio!";
    private Boolean valido = true;
            
    /**
     * Metodo construtor.
     */
    public KTextField() {
        onRequired();
        onText();
    }
    
    /**
     * Aplica UpperCase e MaxLenght ao TextField.
     */
    private void onText(){
        this.textProperty().addListener((obss,oldVal,newVal) -> {
            if(!newVal.isEmpty()){
                if(maxLenght == 0 && upperCase){
                    this.setText(newVal.toUpperCase());
                }else if(maxLenght > 0 && newVal.length() > maxLenght && upperCase){
                    this.setText(oldVal.toUpperCase());
                }else if(maxLenght > 0 && newVal.length() > maxLenght && !upperCase){
                    this.setText(oldVal);
                }else if(upperCase && maxLenght != 0){
                    this.setText(newVal.toUpperCase());
                }else{
                    this.setText(newVal);
                }
            }
        });
    }
    
    /**
     * Verifica se todas as exigencias do TextFiel são validas, 
     * caso não seja retorna uma exceção.
     */
    public void onValida(){
        if(!valido){
            setStyleInvalido();
            throw new IllegalArgumentException("TextFild não preenchido!");
        }
    }
    
    /**
     * Verifica se algum valor foi atribuido ao TextField,
     * caso não ele destaca o textfield de vermelhor e atribui um tooltip
     * Essa verificação é feita no evento focusout.
     * A verificação só acontece se o atributo required for verdadeiro.
     */
    private void onRequired(){
        this.focusedProperty().addListener((obss,oldVal,newVal) -> {
            if(!newVal && required && this.getText().isEmpty()){
                setStyleInvalido();
                valido = false;
            }else{
                valido = true;
                this.setStyle("");
                this.setTooltip(null);
            }
        });
    }
    
    /**
     * Aplica novo estilo ao TextField indicando que 
     * o mesmo contém informações invalidas.
     */
    private void setStyleInvalido(){
        String style = "-fx-border-color: #B22222; "
                    + " -fx-border-width: 1;"
                    + " -fx-border-insets: -1;"
                    + " -fx-border-radius: 3;";
        this.setStyle(style);
        Tooltip t = new Tooltip();
        t.setText(requiredText);
        this.setTooltip(t);
    }
            
    /**
     * Retorna valor da propriedade requiredText.
     * @return Um valor de texto.
     */
    public String getRequiredText() {
        return requiredText;
    }

    /**
     * Seta um valor para a propriedade requeridText.
     * @param requiredText Valor a ser atribuido.
     */
    public void setRequiredText(String requiredText) {
        this.requiredText = requiredText;
    }
    
    /**
     * Retorna valor da propriedade required.
     * @return Um valor Booleano.
     */
    public Boolean getRequired() {
        return required;
    }
    /**
     * Seta um valor para a propriedade required.
     * @param required Valor a ser atribuido.
     */
    public void setRequired(Boolean required) {
        this.valido = !required;
        this.required = required;
    }
    
    /**
     * Retorna valor da propriedade upperCase.
     * @return 
     */
    public Boolean getUpperCase() {
        return upperCase;
    }
    
    /**
     * Seta um valor para a propriedade upperCase.
     * @param upperCase 
     */
    public void setUpperCase(Boolean upperCase) {
        this.upperCase = upperCase;
    }
    
    /**
     * Retorna valor da propriedade maxLenght.
     * @return 
     */
    public int getMaxLenght() {
        return maxLenght;
    }
    
    /**
     * Seta um valor para a propriedade maxLenght.
     * @param maxLenght 
     */
    public void setMaxLenght(int maxLenght) {
        this.maxLenght = maxLenght;
    }

}
