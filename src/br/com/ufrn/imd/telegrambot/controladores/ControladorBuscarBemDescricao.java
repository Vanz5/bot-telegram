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
                mensagem.add("Informe a descrição do bem que você está procurando");
                incrementarPasso();
                break;
            case 2:
                String descricao = mensagemRecebida;
                Bem procurado = aux.buscaBemDescricao(bens,descricao); //TODO - alterar para uma lista de bens ja que a descrição pode ser repetida

                if(procurado == null){
                    mensagem.add("Não foi possivel encontrar a descrição inserida.\nTente novamente ou escreva '/cancelar' para finalizar essa opração");
                    break;
                }
                mensagem.add(procurado.toString());
                incrementarPasso();
                break;
            default:
                mensagem.add("Passo desconhecido, saindo da operação");
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