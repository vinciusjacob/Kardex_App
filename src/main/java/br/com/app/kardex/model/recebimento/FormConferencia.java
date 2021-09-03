package br.com.app.kardex.model.recebimento;

import br.com.app.kardex.controller.recebimento.fxml_recebimentoController;
import br.com.app.kardex.model.kardex.Carga;
import br.com.app.kardex.model.kardex.NotaFiscalItemLote;
import br.com.app.kardex.model.login.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FormConferencia {

    private fxml_recebimentoController controller;

    public fxml_recebimentoController getController() {
        return controller;
    }

    public void setController(fxml_recebimentoController controller) {
        this.controller = controller;
    }

    public FormConferencia(fxml_recebimentoController controller) {
        this.controller = controller;
    }

    private void setCbConferente(){
        ObservableList<String> listaConf = FXCollections.observableArrayList();
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
    public void onStageAgendado(){
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
     * Configura todos os componentes do fomulario para o estagio EM RECEBIMENTO.
     */
    public void onStageEmrecebimento(Carga carga){
        controller.tabConferencia.setDisable(false);
        if(carga.getDoca() != 0){controller.txtDoca.setText(Integer.toString(carga.getDoca()));}else{controller.txtDoca.setText("");}
        controller.txtLacre.setText(carga.getNum_lacre());
        setCbConferente();
        if(carga.getIdconferente() != 0) {
            for (int x = 0; x <= controller.listaConferente.size(); x++) {
                Usuario usuario = controller.listaConferente.get(x);
                if (usuario.getIdusuario() == carga.getIdconferente()) {
                    controller.cbConferente.getSelectionModel().select(x);
                    break;
                }
            }
        }else{
            controller.cbConferente.getSelectionModel().clearSelection();
        }
        if(carga.getQtdpallet() != 0){controller.txtQtdPallet.setText(Integer.toString(carga.getQtdpallet()));}else{controller.txtQtdPallet.setText("");}
        if(carga.getTemperaturabau() != 0.0){controller.txtTempBau.setText(Double.toString(carga.getTemperaturabau()));}else{ controller.txtTempBau.setText("");}
        if(carga.getPesoinicial() != 0.0){controller.txtPesoIni.setText(Double.toString(carga.getPesoinicial()));}else{ controller.txtPesoIni.setText("");}
        if(carga.getPesofinal() != 0.0){controller.txtPesoFim.setText(Double.toString(carga.getPesofinal()));}else{ controller.txtPesoFim.setText("");}
        controller.txtProdutoLote.setText("");
        controller.txtProdutoLote.disableProperty().bind(controller.txtDoca.textProperty().isEmpty().or(
                controller.txtLacre.textProperty().isEmpty().or(controller.cbConferente.getSelectionModel().selectedItemProperty().isNull().or(
                        controller.txtQtdPallet.textProperty().isEmpty()
                ))
        ));
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

        controller.btnIncluirLote.disableProperty().bind(controller.txtQtdCxLote.textProperty().isEmpty().or(
                controller.txtQtdProdLote.textProperty().isEmpty().or(controller.txtNumlote.textProperty().isEmpty().or(
                        controller.dtFabLote.valueProperty().isNull().or(controller.dtVencLote.valueProperty().isNull())
                ))
        ));
        controller.btnExcluirLote.disableProperty().bind(controller.tblLotes.getSelectionModel().selectedItemProperty().isNull());
        controller.btnValidar.disableProperty().bind(controller.tblLotes.itemsProperty().isNull());
        controller.listaLotes = new NotaFiscalItemLote().getLista(carga.getIdcarga());
        carga.setListaLote(controller.listaLotes);

        if(carga.getListaLoteSintetica().size() > 0) {
            controller.tblLotes.setItems(carga.getListaLote());
        }else{
            controller.tblLotes.setItems(null);
        }
    }

    /**
     * Configura todos os componentes do fomulario para o estagio RECEBIDO.
     */
    public void onStageRecebido(Carga carga){
        controller.tabConferencia.setDisable(false);
        if(carga.getDoca() != 0){controller.txtDoca.setText(Integer.toString(carga.getDoca()));}else{controller.txtDoca.setText("");}
        controller.txtLacre.setText(carga.getNum_lacre());
        setCbConferente();
        if(carga.getIdconferente() != 0) {
            for (int x = 0; x <= controller.listaConferente.size(); x++) {
                Usuario usuario = controller.listaConferente.get(x);
                if (usuario.getIdusuario() == carga.getIdconferente()) {
                    controller.cbConferente.getSelectionModel().select(x);
                    break;
                }
            }
        }else{
            controller.cbConferente.getSelectionModel().clearSelection();
        }
        if(carga.getQtdpallet() != 0){controller.txtQtdPallet.setText(Integer.toString(carga.getQtdpallet()));}else{controller.txtQtdPallet.setText("");}
        if(carga.getTemperaturabau() != 0.0){controller.txtTempBau.setText(Double.toString(carga.getTemperaturabau()));}else{ controller.txtTempBau.setText("");}
        if(carga.getPesoinicial() != 0.0){controller.txtPesoIni.setText(Double.toString(carga.getPesoinicial()));}else{ controller.txtPesoIni.setText("");}
        if(carga.getPesofinal() != 0.0){controller.txtPesoFim.setText(Double.toString(carga.getPesofinal()));}else{ controller.txtPesoFim.setText("");}
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
        controller.btnValidar.disableProperty().unbind();
        controller.btnValidar.setDisable(true);

        controller.listaLotes = new NotaFiscalItemLote().getLista(carga.getIdcarga());
        carga.setListaLote(controller.listaLotes);

        if(carga.getListaLote().size() > 0) {
            controller.tblLotes.setItems(carga.getListaLote());
        }else{
            controller.tblLotes.setItems(null);
        }
    }

}
