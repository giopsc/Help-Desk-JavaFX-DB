package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.data.ChamadoDao;
import com.example.model.Chamado;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class PrimaryController {

    @FXML TextField txtUser;
    @FXML TextField txtCodEquip;
    @FXML RadioButton computer;
    @FXML RadioButton printer;
    @FXML RadioButton net;
    @FXML ToggleGroup equips;
    @FXML CheckBox firstContact;
    @FXML CheckBox support;
    @FXML CheckBox finished;
    // @FXMl diz que não sera instanciada um textfield, pois já foi em primary
    // Alt + Shift + O -> organizar imports
    @FXML ListView<Chamado> lista;
    ArrayList<Chamado> chamados = new ArrayList<>();
    private ChamadoDao chamadoDao = new ChamadoDao();

    @FXML
    private void adicionar() throws SQLException{

        String nome = txtUser.getText();
        String codEquip = txtCodEquip.getText();
        String tipoEquip;
        if (computer.isSelected()){
            tipoEquip = "Computador";
        } else if (printer.isSelected()){
            tipoEquip = "Impressora";
        } else if (net.isSelected()){
            tipoEquip = "Rede";
        } else {
            tipoEquip = "Não definido";
        }
        
        String tipoAtividade;
        if (firstContact.isSelected()){
            tipoAtividade = "Primeiro Contato";
        } else if (support.isSelected()){
            tipoAtividade = "Suporte";
        } else if (finished.isSelected()){
            tipoAtividade = "Finalizado";
        } else {
            tipoAtividade = "Não definido";
        }

        var contLista = chamadoDao.listarTodos().size();

        var chamado = new Chamado(contLista + 1, nome, codEquip, tipoEquip, tipoAtividade);
        
        try {
            chamadoDao.inserir(chamado);
            mostrarMensagem(AlertType.INFORMATION, "Sucesso!", "Chamado registrado com sucesso.");
        } catch (Exception erro) {
            mostrarMensagem(AlertType.ERROR, "Erro", erro.getLocalizedMessage());
        }
        chamados.add(chamado);

        txtUser.clear();
        txtCodEquip.clear();

        listarChamados();
    }
 
    private void listarChamados() {
        try {
            chamadoDao.listarTodos().forEach(chamado -> lista.getItems().add(chamado));
        } catch (SQLException e) {
            mostrarMensagem(AlertType.ERROR, "Erro", "Erro ao buscar chamados." + e.getMessage());
        }
    }
    
    public void apagar(){
        var selected = lista.getSelectionModel().getSelectedItem();
        chamados.remove(selected);
        try {
            chamadoDao.apagar(selected);
        } catch (Exception e) {
            mostrarMensagem(AlertType.ERROR, "Erro", "Erro ao apagar chamado." + e.getMessage());
        }
    }


    private void mostrarMensagem(AlertType tipo, String titulo, String conteudo) {
        Alert alertaErro = new Alert (tipo);
        alertaErro.setTitle(titulo);
        alertaErro.setContentText(conteudo);
        alertaErro.show();
    }


    // var aluno = new Chamado(nome, "1J", 23012);
    // nomes.add(aluno);
    //anonymous class
    //função anonima - lambda expression 
    // mostrarAlunos();
    
    // public void mostrarChamados(){
        //     lista.getItems().clear();
        //     for(var nome : nomes){
            //         lista.getItems().add(nome);
            //     }
            // }
            
            // public void apagar(){
                //     var aluno = lista.getSelectionModel().getSelectedItem();
                //     nomes.remove(aluno);
                //     mostrarAlunos();
                
                //     Alert msg = new Alert(AlertType.INFORMATION);
                //     msg.setHeaderText("Sucesso");
                //     msg.setContentText("Aluno apagado com sucesso");
                //     msg.show();
                // }
                
                //inner class
                // class ComparadorDeAluno implements Comparator<String>{
                    //     @Override
                    //     public int compare(String o1, String o2) {
                        //         return o1.compareTo(o2);
                        //     }  
                        // }
}