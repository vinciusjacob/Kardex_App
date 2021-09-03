/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.app.kardex.util;

import javafx.animation.FadeTransition;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * 
 * @author Vinicius Jacob
 */
public class MsgBox {
    
    private Label label;
    private ProgressIndicator indicator;
    private HBox hbox;
    private AnchorPane anchorPane;
    
    /**
     * Defini valor da mensagem do box
     * @param mensagem Mensagem a ser exibida no box.
     */
    public void setMensage(String mensagem) {
        this.label.setText(mensagem);
    }
    
    /**
     * NÃO USAR // Será descontinuada
     * @return 
     */
    public Label getLabel() {
        return label;
    }
    
    /**
     * Retorna uma StringProperty referente a mensagem exibida no box.
     * @return uma StringProperty
     */
    public StringProperty mensagemProperty(){
        return label.textProperty();
    }
    
    /**
     * Cria um novo box de mensagem.
     * @param anchorPane Onde será posicionado o box.
     * @param msg Mensagem que irá aparecer no box.
     */
    public MsgBox(AnchorPane anchorPane,String msg) {
        this.anchorPane = anchorPane;
        indicator = new ProgressIndicator();
        indicator.setPrefHeight(40.0);
        indicator.setPrefWidth(40.0);

        label = new Label(msg);
        label.setMaxWidth(170);
        label.setWrapText(true);
        
        hbox = new HBox(10);
        hbox.setPrefHeight(70);
        hbox.setAlignment(Pos.CENTER);
    }
    
    /**
     * Mostra/Oculta um box para mensagem de progresso.
     * @param var Defini se será mostrado ou ocultado.
     */
    public void setMsgProgress(Boolean var){
        
        if(var){
            
            hbox.getStyleClass().add("msgProgress");
            hbox.getChildren().clear();
            hbox.getChildren().addAll(indicator, label);

            AnchorPane.setRightAnchor(hbox, 5.0);
            AnchorPane.setBottomAnchor(hbox, 10.0);
            anchorPane.getChildren().addAll(hbox);
            setFadeAdd(hbox);
        }else{
            anchorPane.getChildren().remove(hbox);
        }
    }
    
    /**
     * Mostra/Oculta um box para uma mensagem de sucesso.
     * @param var Defini se será mostrado ou ocultado.
     */
    public void setMsgSucces(Boolean var){
        if(var){
            Image i = new Image("/br.com.app.kardex/icons/Confirmado.png");
            ImageView iv = new ImageView();
            iv.setImage(i);

            hbox.getStyleClass().add("msgSucces");
            hbox.getChildren().clear();
            hbox.getChildren().addAll(iv, label);

            AnchorPane.setRightAnchor(hbox, 5.0);
            AnchorPane.setBottomAnchor(hbox, 10.0);
            anchorPane.getChildren().addAll(hbox);
            setFadeAdd(hbox);
            setFadeRemov(hbox);
            
        }else{
           anchorPane.getChildren().remove(hbox);
        }  
    }
    
    /**
     * Mostra/Oculta um box para uma mensagem de erro.
     * @param var Defini se será mostrado ou ocultado.
     */
    public void setMsgError(Boolean var){
        if(var){
            Image i = new Image("/br.com.app.kardex/icons/error.png");
            ImageView iv = new ImageView();
            iv.setImage(i);

            hbox.getStyleClass().add("msgError");
            hbox.getChildren().clear();
            hbox.getChildren().addAll(iv, label);

            AnchorPane.setRightAnchor(hbox, 5.0);
            AnchorPane.setBottomAnchor(hbox, 10.0);
            anchorPane.getChildren().addAll(hbox);
            setFadeAdd(hbox);
            setFadeRemov(hbox);
            
        }else{
           anchorPane.getChildren().remove(hbox);
        }  
    }

    /**
     * Define a transação do box que contem a mensagem.
     * @param nd Box a ser aplicado a transação.
     */
    private void setFadeAdd(Node nd){
        FadeTransition ft = new FadeTransition(Duration.seconds(1.5),nd);
        ft.setFromValue(0.0);
        ft.setToValue(0.9);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    
    /**
     * Remove a transação do box que contem a mensagem.
     * @param nd Box a ser removido a transação.
     */
    private void setFadeRemov(Node nd){
        FadeTransition ft = new FadeTransition(Duration.seconds(7.0),nd);
        ft.setFromValue(0.9);
        ft.setToValue(0.3);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                anchorPane.getChildren().remove(hbox);
            }
        });
        ft.play();
    }
}
