package br.com.app.kardex.model.expedicao;

import br.com.app.kardex.controller.expedicao.fxml_expedicaoController;
import br.com.app.kardex.model.kardex.Carga;
import br.com.app.kardex.model.kardex.NotaFiscalItemLote;
import br.com.app.kardex.model.login.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FormConferencia {

    private fxml_expedicaoController controller;

    public fxml_expedicaoController getController() {
        return controller;
    }

    public void setController(fxml_expedicaoController controller) {
        this.controller = controller;
    }

    public FormConferencia(fxml_expedicaoController controller) {
        this.controller = controller;
    }

    private void setCbConferente(){
        ObservableList<String> listaConf = FXCollections.observableArrayList();
        controller.listaConferente = new Usuario().getListaConferente();
        for (Usuario usuario : controller.listaConferente){
            listaConf.add(usuario.getNome());
        }
        controller.cbConferente.setItems(listaConf);
    }

    /**
     * Configura todos os componentes do fomulario para o estagio PADRÃO.
     */
    public void onReset(){
        controller.tabConferencia.setDisable(true);
        controller.txtDoca.setText("");
        controller.txtLacre.setText("");
        controller.cbConferente.getSelectionModel().clearSelection();
        controller.txtQtdPallet.setText("");
        controller.txtTempBau.setText("");
        controller.txtPesoIni.setText("");
        controller.txtPesoFim.setText("");
        controller.txtProdutoLote.setText("");
        controller.txtQtdCxLote.setText("");
        controller.txtQtdProdLote.setText("");
        controller.txtNumlote.setText("");
        controller.dtFabLote.setValue(null);
        controller.dtVencLote.setValue(null);
        controller.txtObsLote.setText("");
        controller.checkAvariado.setSelected(false);
    }

    /**
     * Configura todos os componentes do fomulario para o estagio AGENDADO.
     */
    public void onStagePendente(Carga carga){
        controller.tabConferencia.setDisable(true);
        controller.txtProdutoLote.setText("");
        controller.txtQtdCxLote.setText("");
        controller.txtQtdProdLote.setText("");
        controller.txtNumlote.setText("");
        controller.dtFabLote.setValue(null);
        controller.dtVencLote.setValue(null);
        controller.txtObsLote.setText("");
        controller.checkAvariado.setSelected(false);
    }

    /**
     * Configura todos os componentes do fomulario para o estagio EM RECEBIMENTO.
     */
    public void onStageEmandamento(Carga carga){
        controller.tabConferencia.setDisable(false);

        controller.txtProdutoLote.setText("");
        controller.txtProdutoLote.setDisable(true);
        controller.txtQtdCxLote.setText("");
        controller.txtQtdCxLote.setDisable(true);
        controller.txtQtdProdLote.setText("");
        controller.txtQtdProdLote.setDisable(true);
        controller.txtNumlote.setText("");
        controller.txtNumlote.setDisable(true);
        controller.dtFabLote.setValue(null);
        controller.dtFabLote.setDisable(true);
        controller.dtVencLote.setValue(null);
        controller.dtVencLote.setDisable(true);
        controller.txtObsLote.setText("");
        controller.checkAvariado.setSelected(false);

        controller.btnIncluirLote.disableProperty().bind(controller.tblLotes.getSelectionModel().selectedItemProperty().isNull());
        controller.btnExcluirLote.disableProperty().bind(controller.tblLotes.getSelectionModel().selectedItemProperty().isNull());
        controller.btnSalvarLote.setVisible(false);
        controller.btnCancelar.setVisible(false);

        carga.setListaLote(new NotaFiscalItemLote().getLista(carga.getIdcarga()));
        controller.listaLote = carga.getListaLote();
        controller.listaLoteSintetica = carga.getListaLoteSintetica();
        if(carga.getListaLote().size() > 0) {
            controller.tblLotes.setItems(carga.getListaLote());
        }else{
            controller.tblLotes.setItems(null);
        }
    }

    /**
     * Configura todos os componentes do fomulario para o estagio RECEBIDO.
     */
    public void onStageConcluido(Carga carga){
        controller.tabConferencia.setDisable(false);
        controller.txtProdutoLote.setText("");
        controller.txtProdutoLote.disableProperty().unbind();
        controller.txtProdutoLote.setDisable(true);
        controller.txtQtdCxLote.setText("");
        controller.txtQtdCxLote.disableProperty().bind(controller.txtProdutoLote.textProperty().isEmpty());
        controller.txtQtdProdLote.setText("");
        controller.txtQtdProdLote.disableProperty().bind(controller.txtProdutoLote.textProperty().isEmpty());
        controller.txtNumlote.setText("");
        controller.txtNumlote.disableProperty().bind(controller.txtProdutoLote.textProperty().isEmpty());
        controller.dtFabLote.setValue(null);
        controller.dtFabLote.disableProperty().bind(controller.txtProdutoLote.textProperty().isEmpty());
        controller.dtVencLote.setValue(null);
        controller.dtVencLote.disableProperty().bind(controller.txtProdutoLote.textProperty().isEmpty());
        controller.txtObsLote.setText("");
        controller.checkAvariado.setSelected(false);

        controller.btnIncluirLote.disableProperty().unbind();
        controller.btnIncluirLote.setDisable(true);
        controller.btnExcluirLote.disableProperty().unbind();
        controller.btnExcluirLote.setDisable(true);
        controller.btnSalvarLote.setVisible(false);
        controller.btnCancelar.setVisible(false);

        carga.setListaLote(new NotaFiscalItemLote().getLista(carga.getIdcarga()));
        controller.listaLote = carga.getListaLote();
        controller.listaLoteSintetica = carga.getListaLoteSintetica();
        if(carga.getListaLote().size() > 0) {
            controller.tblLotes.setItems(carga.getListaLote());
        }else{
            controller.tblLotes.setItems(null);
        }
    }

}
