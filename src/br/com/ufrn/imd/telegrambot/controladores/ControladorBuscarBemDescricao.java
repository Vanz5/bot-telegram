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
                Bem procurado = buscarBemCodigo(bens,descricao);

                if(procurado == null){
                    mensagem.add("Não foi possivel encontrar a descrição inserida.\n Tente novamente ou escreva '/cancelar' para finalizar essa opração");
                    break;
                }
                mensagem.add(procurado.toString());
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

    public Bem buscarBemCodigo(List<Bem> bens, String descricao){
        Bem bem = null;
        for(Bem x : bens){
            if(descricao.equalsIgnoreCase(x.getDescricao())){
                bem = x;
                break;
            }
        }
        return bem;
    }
}