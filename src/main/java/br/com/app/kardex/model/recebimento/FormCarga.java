package br.com.app.kardex.model.recebimento;

import br.com.app.kardex.controller.recebimento.fxml_recebimentoController;
import br.com.app.kardex.model.cadastros.Departamento;
import br.com.app.kardex.model.kardex.Carga;
import br.com.app.kardex.model.kardex.NotaFiscal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormCarga {

    private fxml_recebimentoController controller;

    public fxml_recebimentoController getController() {
        return controller;
    }

    public void setController(fxml_recebimentoController controller) {
        this.controller = controller;
    }

    public FormCarga(fxml_recebimentoController controller) {
        this.controller = controller;
    }

    private void setCbFilial(){
        ObservableList<String> listaFilial = FXCollections.observableArrayList();
        listaFilial.add("RDN");
        controller.cbFilial.setItems(listaFilial);
        controller.cbFilial.getSelectionModel().select(0);
    }

    private void setCbDepartamento(){
        ObservableList<String> listaDep = FXCollections.observableArrayList();
        for(Departamento dep : controller.listaDepartamento){
            listaDep.add(dep.getDescricao());
        }
        controller.cbDepartamento.setItems(listaDep);
    }

    /**
     * Configura todos os componentes do fomulario para o estagio PADRÃO.
     */
    public void onReset(){
        controller.tabCarga.getTabPane().getSelectionModel().select(0);
        controller.txtIdCarga.setText("");
        controller.dtDescarga.setValue(LocalDate.now());
        setCbFilial();
        controller.txtDescricao.setText("");
        controller.txtDescricao.requestFocus();
        setCbDepartamento();
        controller.cbDepartamento.getSelectionModel().clearSelection();
        controller.txtPlaca.setText("");
        controller.txtTransportadora.setText("");
        controller.txtMotorista.setText("");
        controller.btnIncluirNota.setVisible(false);
        controller.btnImportarXml.setVisible(false);
        controller.btnImprimirMapa.setVisible(false);
        controller.tblNotas.setVisible(false);
        controller.btnSalvar.disableProperty().bind(controller.txtDescricao.textProperty().isEmpty().or(
                controller.cbDepartamento.getSelectionModel().selectedItemProperty().isNull()));

    }

    /**
     * Configura todos os componentes do fomulario para o estagio AGENDADO.
     */
    public void onStageAgendado(Carga carga){
        controller.tabCarga.getTabPane().getSelectionModel().select(0);
        controller.txtIdCarga.setText(Integer.toString(carga.getIdcarga()));
        controller.dtDescarga.setValue(LocalDate.parse(carga.getDtcarga(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        setCbFilial();
        controller.txtDescricao.setText(carga.getDescricao());
        controller.txtDescricao.requestFocus();
        setCbDepartamento();
        controller.cbDepartamento.getSelectionModel().clearSelection();
        for(int x = 0; x <= controller.listaDepartamento.size();x++){
            Departamento dep = controller.listaDepartamento.get(x);
            if(dep.getIddepartamento() == carga.getIddepartamento()){
                controller.cbDepartamento.getSelectionModel().select(x);
                break;
            }
        }
        controller.txtPlaca.setText(carga.getPlaca());
        controller.txtTransportadora.setText(carga.getTransportadora());
        controller.txtMotorista.setText(carga.getMotorista());
        controller.btnIncluirNota.setVisible(true);
        controller.btnIncluirNota.setDisable(false);
        controller.btnImportarXml.setVisible(true);
        controller.btnImportarXml.setDisable(false);
        controller.btnImprimirMapa.setVisible(true);
        controller.btnImprimirMapa.disableProperty().bind(controller.tblNotas.itemsProperty().isNull());
        controller.tblNotas.setVisible(true);

        if(carga.getListaNota().size() > 0){
            controller.tblNotas.setItems(carga.getListaNota());
        }else{
            controller.tblNotas.setItems(null);
        }
        controller.btnSalvar.disableProperty().bind(controller.txtDescricao.textProperty().isEmpty().or(
                controller.cbDepartamento.getSelectionModel().selectedItemProperty().isNull()));
    }

    /**
     * Configura todos os componentes do fomulario para o estagio EM RECEBIMENTO.
     */
    public void onStageEmrecebimento(Carga carga){
        controller.tabCarga.getTabPane().getSelectionModel().select(0);
        controller.txtIdCarga.setText(Integer.toString(carga.getIdcarga()));
        controller.dtDescarga.setValue(LocalDate.parse(carga.getDtcarga(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        setCbFilial();
        controller.txtDescricao.setText(carga.getDescricao());
        controller.txtDescricao.requestFocus();
        setCbDepartamento();
        for(int x = 0; x <= controller.listaDepartamento.size();x++){
            Departamento dep = controller.listaDepartamento.get(x);
            if(dep.getIddepartamento() == carga.getIddepartamento()){
                controller.cbDepartamento.getSelectionModel().select(x);
                break;
            }
        }
        controller.txtPlaca.setText(carga.getPlaca());
        controller.txtTransportadora.setText(carga.getTransportadora());
        controller.txtMotorista.setText(carga.getMotorista());
        controller.btnIncluirNota.setVisible(true);
        controller.btnIncluirNota.setDisable(true);
        controller.btnImportarXml.setVisible(true);
        controller.btnImportarXml.setDisable(true);
        controller.btnImprimirMapa.setVisible(true);
        controller.btnImprimirMapa.disableProperty().bind(controller.tblNotas.itemsProperty().isNull());
        controller.tblNotas.setVisible(true);

        if(carga.getListaNota().size() > 0){
            controller.tblNotas.setItems(carga.getListaNota());
        }else{
            controller.tblNotas.setItems(null);
        }
        controller.btnSalvar.disableProperty().bind(controller.txtDescricao.textProperty().isEmpty().or(
                controller.cbDepartamento.getSelectionModel().selectedItemProperty().isNull()));
    }

    /**
     * Configura todos os componentes do fomulario para o estagio RECEBIDO.
     */
    public void onStageRecebido(Carga carga){
        controller.tabCarga.getTabPane().getSelectionModel().select(0);
        controller.txtIdCarga.setText(Integer.toString(carga.getIdcarga()));
        controller.dtDescarga.setValue(LocalDate.parse(carga.getDtcarga(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        setCbFilial();
        controller.txtDescricao.setText(carga.getDescricao());
        controller.txtDescricao.requestFocus();
        setCbDepartamento();
        for(int x = 0; x <= controller.listaDepartamento.size();x++){
            Departamento dep = controller.listaDepartamento.get(x);
            if(dep.getIddepartamento() == carga.getIddepartamento()){
                controller.cbDepartamento.getSelectionModel().select(x);
                break;
            }
        }
        if(carga.getPlaca() != null){controller.txtPlaca.setText(carga.getPlaca());}else{controller.txtPlaca.setText("");}
        if(carga.getTransportadora() != null){controller.txtTransportadora.setText(carga.getTransportadora());}else{controller.txtTransportadora.setText("");}
        if(carga.getMotorista() != null){controller.txtMotorista.setText(carga.getMotorista());}else{controller.txtMotorista.setText("");}
        controller.btnIncluirNota.setVisible(true);
        controller.btnIncluirNota.setDisable(true);
        controller.btnImportarXml.setVisible(true);
        controller.btnImportarXml.setDisable(true);
        controller.btnImprimirMapa.setVisible(true);
        controller.btnImprimirMapa.disableProperty().unbind();
        controller.btnImprimirMapa.setDisable(true);
        controller.tblNotas.setVisible(true);

        if(carga.getListaNota().size() > 0){
            controller.tblNotas.setItems(carga.getListaNota());
        }else{
            controller.tblNotas.setItems(null);
        }
        controller.btnSalvar.disableProperty().unbind();
        controller.btnSalvar.setDisable(true);
    }


}
