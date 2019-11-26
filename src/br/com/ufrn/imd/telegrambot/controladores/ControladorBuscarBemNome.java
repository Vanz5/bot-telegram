package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;

import java.io.*;
import java.util.*;

public class ControladorBuscarBemNome extends Controlador {

    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorBuscarBemNome(){
        super("/buscarbemnome", 2);
    }
    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        
        List<Bem> bens = aux.listaBens();
        List<String> mensagem = new ArrayList<String>();

        switch (getPasso()){
            case 1:
                mensagem.add("Informe o nome do bem que você está procurando.");
                incrementarPasso();
                break;
            case 2:
                String nome = mensagemRecebida;
                List<Bem> procurados = aux.buscarBemNome(bens,nome);

                if(procurados == null){
                    mensagem.add("Não foi possível encontrar o nome inserido.\nTente novamente ou escreva '/cancelar' para finalizar essa operação.");
                    break;
                }
                mensagem.add(procurados.toString());
                incrementarPasso();
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        return null;
    }

    @Override
    public void reset() {
        setPasso(1);
    }
}

