package br.com.app.kardex.model.recebimento;

import br.com.app.kardex.controller.recebimento.fxml_recebimentoController;
import br.com.app.kardex.model.kardex.NotaFiscal;

public class FormNota {

    private fxml_recebimentoController controller;

    public fxml_recebimentoController getController() {
        return controller;
    }

    public void setController(fxml_recebimentoController controller) {
        this.controller = controller;
    }

    public FormNota(fxml_recebimentoController controller) {
        this.controller = controller;
    }

    /**
     * Configura todos os componentes do fomulario para o estagio PADRÃO.
     */
    public void onReset(){
        controller.tabNota.disableProperty().unbind();
        controller.tabNota.setDisable(true);
        controller.txtNumero.setText("");
        controller.txtVolumeTotal.setText("");
        controller.txtPesoTotal.setText("");
        controller.txtProdutoItem.setText("");
        controller.txtQtdCxItem.setText("");
        controller.txtPesoItem.setText("");
        controller.btnAlterarItem.setVisible(true);
        controller.btnSalvarItem.setVisible(false);
        controller.btnCancelarItem.setVisible(false);
        controller.tblItens.setDisable(false);
        controller.txtPrUnitItem.setText("");
        controller.txtValorTotalItem.setText("");
    }

    /**
     * Configura todos os componentes do fomulario para o estagio AGENDADO.
     */
    public void onStageAgendado(){
        controller.tabNota.disableProperty().bind(controller.tblNotas.getSelectionModel().selectedItemProperty().isNull());
        controller.tabNota.selectedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue){
                NotaFiscal notaFiscal = controller.tblNotas.getSelectionModel().getSelectedItem();
                controller.tblItens.setItems(notaFiscal.getListaItem());
                controller.txtNumero.setText(Integer.toString(notaFiscal.getNumero()));
                controller.txtVolumeTotal.setText(Integer.toString(notaFiscal.getVolumetotal()));
                controller.txtPesoTotal.setText(String.format("%.2f",notaFiscal.getPesototal()));
            }
        });
        controller.txtVolumeTotal.setText("");
        controller.txtPesoTotal.setText("");
        controller.txtProdutoItem.setText("");
        controller.txtQtdCxItem.setText("");
        controller.txtPesoItem.setText("");
        controller.txtPrUnitItem.setText("");
        controller.txtValorTotalItem.setText("");
        controller.btnAlterarItem.disableProperty().bind(controller.tblItens.getSelectionModel().selectedItemProperty().isNull());
        controller.btnAlterarItem.setVisible(true);
        controller.btnSalvarItem.setVisible(false);
        controller.btnCancelarItem.setVisible(false);
        controller.tblItens.setDisable(false);
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
                controller.txtVolumeTotal.setText(Integer.toString(notaFiscal.getVolumetotal()));
                controller.txtPesoTotal.setText(String.format("%.2f",notaFiscal.getPesototal()));
            }
        });
        controller.txtVolumeTotal.setText("");
        controller.txtPesoTotal.setText("");
        controller.txtProdutoItem.setText("");
        controller.txtQtdCxItem.setText("");
        controller.txtPesoItem.setText("");
        controller.txtPrUnitItem.setText("");
        controller.txtValorTotalItem.setText("");
        controller.btnAlterarItem.disableProperty().bind(controller.tblItens.getSelectionModel().selectedItemProperty().isNull());
        controller.btnAlterarItem.setVisible(true);
        controller.btnSalvarItem.setVisible(false);
        controller.btnCancelarItem.setVisible(false);
        controller.tblItens.setDisable(false);
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
                controller.txtVolumeTotal.setText(Integer.toString(notaFiscal.getVolumetotal()));
                controller.txtPesoTotal.setText(String.format("%.2f",notaFiscal.getPesototal()));
            }
        });
        controller.txtVolumeTotal.setText("");
        controller.txtPesoTotal.setText("");
        controller.txtProdutoItem.setText("");
        controller.txtQtdCxItem.setText("");
        controller.txtPesoItem.setText("");
        controller.txtPrUnitItem.setText("");
        controller.txtValorTotalItem.setText("");
        controller.btnAlterarItem.setVisible(false);
        controller.btnSalvarItem.setVisible(false);
        controller.btnCancelarItem.setVisible(false);
        controller.tblItens.setDisable(false);
    }
}
