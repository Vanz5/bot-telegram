package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;

import java.io.*;
import java.util.*;

public class ControladorBuscarBemDescricao extends Controlador {

    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorBuscarBemDescricao(){
        super("/buscarbemdescricao", 2);
    }
    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        
        List<Bem> bens = aux.listaBens();
        List<String> mensagem = new ArrayList<String>();

        switch (getPasso()){
            case 1:
                mensagem.add("Informe a descrição do bem que você está procurando.");
                incrementarPasso();
                break;
            case 2:
                String descricao = mensagemRecebida;
                List<Bem> procurados = aux.buscaBemDescricao(bens,descricao);

                if(procurados == null){
                    mensagem.add("Não foi possível encontrar a descrição inserida.\nTente novamente ou escreva '/cancelar' para finalizar essa operação.");
                    break;
                }
                mensagem.add(procurados.toString());
                incrementarPasso();
                break;
            default:
                mensagem.add("Passo desconhecido, saindo da operação.");
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