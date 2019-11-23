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
                mensagem.add("Informe o nome do bem que você está procurando");
                setPasso(getPasso() + 1);
                break;
            case 2:
                String nome = mensagemRecebida;
                Bem procurado = buscarBemCodigo(bens,nome);

                if(procurado == null){
                    mensagem.add("Não foi possivel encontrar o nome inserido.\n Tente novamente ou escreva '/cancelar' para finalizar essa opração");
                    break;
                }
                mensagem.add(procurado.toString());
                setPasso(getPasso() + 1);
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

    public Bem buscarBemCodigo(List<Bem> bens, String nome){
        Bem bem = null;
        for(Bem x : bens){
            if(nome.equalsIgnoreCase(x.getNome())){
                bem = x;
                break;
            }
        }
        return bem;
    }
}

