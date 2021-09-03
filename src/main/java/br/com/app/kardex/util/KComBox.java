package br.com.app.kardex.util;


import javafx.scene.control.ComboBox;
import java.util.ArrayList;

public class KComBox extends ComboBox {

    private ArrayList<Object> lista;

    public void setLista(ArrayList<Object> lista){
        this.lista = lista;
    }

    public ArrayList<Object> getLista(){
        return this.lista;
    }

}
