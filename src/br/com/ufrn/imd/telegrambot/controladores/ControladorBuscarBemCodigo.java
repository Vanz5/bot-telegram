package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;

import java.io.*;
import java.util.*;

public class ControladorBuscarBemCodigo extends Controlador {

    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorBuscarBemCodigo(){
        super("/buscarbemcodigo", 2);
    }
    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Bem> bens = aux.listaBens();
        List<String> mensagem = new ArrayList<String>();

        switch (getPasso()){
            case 1:
                mensagem.add("Informe o código do bem que você está procurando");
                setPasso(getPasso() + 1);
                break;
            case 2:
                String codigo = mensagemRecebida;
                Bem procurado = buscarBemCodigo(bens,codigo);

                if(procurado == null){
                    mensagem.add("Não foi possivel encontrar o código inserido.\n Tente novamente ou escreva '/cancelar' para finalizar essa opração");
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

    public Bem buscarBemCodigo(List<Bem> bens, String codigo){
        Bem bem = null;
        for(Bem x : bens){
            if(codigo == x.getCodigo()){
                bem = x;
                break;
            }
        }
        return bem;
    }
}
