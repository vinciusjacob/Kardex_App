package br.com.app.kardex.model.expedicao;

import br.com.app.kardex.controller.expedicao.fxml_expedicaoController;
import br.com.app.kardex.model.kardex.NotaFiscal;
import javafx.scene.control.cell.TextFieldTableCell;

public class FormNota {

    private fxml_expedicaoController controller;

    public fxml_expedicaoController getController() {
        return controller;
    }

    public void setController(fxml_expedicaoController controller) {
        this.controller = controller;
    }

    public FormNota(fxml_expedicaoController controller) {
        this.controller = controller;
    }

    /**
     * Configura todos os componentes do fomulario para o estagio PADRÃO.
     */
    public void onReset(){
        controller.tabNota.disableProperty().unbind();
        controller.tabNota.setDisable(true);
        controller.txtNumero.setText("");
    }

    /**
     * Configura todos os componentes do fomulario para o estagio AGENDADO.
     */
    public void onStageAgendado(){
        controller.tabNota.disableProperty().bind(controller.tblNotas.getSelectionModel().selectedItemProperty().isNull());
        controller.tabNota.selectedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue){
                NotaFiscal notaFiscal = controller.tblNotas.getSelectionModel().getSelectedItem();
                controller.colQtdProd.setCellFactory(TextFieldTableCell.forTableColumn());
                controller.tblItens.setItems(notaFiscal.getListaItem());
                controller.txtNumero.setText(Integer.toString(notaFiscal.getNumero()));
            }
        });
    }

    /**
     * Configura todos os componentes do fomulario para o estagio EM RECEBIMENTO.
     */
    public void onStageEmrecebimento(){
        controller.tabNota.disableProperty().bind(controller.tblNotas.getSelectionModel().selectedItemProperty().isNull());
        controller.tabNota.selectedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue){
                NotaFiscal notaFiscal = controller.tblNotas.getSelectionModel().getSelectedItem();
                controller.tblItens.setItems(notaFiscal.getListaItem());
                controller.txtNumero.setText(Integer.toString(notaFiscal.getNumero()));
            }
        });
    }

    /**
     * Configura todos os componentes do fomulario para o estagio RECEBIDO.
     */
    public void onStageRecebido(){
        controller.tabNota.disableProperty().bind(controller.tblNotas.getSelectionModel().selectedItemProperty().isNull());
        controller.tabNota.selectedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue){
                NotaFiscal notaFiscal = controller.tblNotas.getSelectionModel().getSelectedItem();
                controller.tblItens.setItems(notaFiscal.getListaItem());
                controller.txtNumero.setText(Integer.toString(notaFiscal.getNumero()));
            }
        });
    }
}
