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
                incrementarPasso();
                break;
            case 2:
                String codigo = mensagemRecebida;
                Bem procurado = aux.buscarBemCodigo(bens,codigo);

                if(procurado == null){ // Caso o código não for encontrado
                    mensagem.add("Não foi possivel encontrar o código inserido.\nTente novamente ou escreva '/cancelar' para finalizar essa operação");
                    break;
                }
                mensagem.add(procurado.toString()); //Imprime no chat o bem com o código inserido
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
