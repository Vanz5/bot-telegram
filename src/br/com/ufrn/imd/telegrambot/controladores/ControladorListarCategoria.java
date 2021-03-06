package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Categoria;

import java.io.IOException;
import java.util.*;

public class ControladorListarCategoria extends Controlador {

    Categoria categoria;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorListarCategoria() {
        super("/listarcategoria", 1);
        categoria = new Categoria();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Categoria> categorias = aux.listaCategorias();
        List<String> mensagem = new ArrayList<String>();

        switch (getPasso()){
            case 1:
                if(categorias.isEmpty()){
                    mensagem.add("Não existem categorias cadastradas, insira '/cancelar' para sair desta operação.");
                }
                else {
                    mensagem.add("Categorias cadastradas:\n");
                    for(Categoria x : categorias) { mensagem.add(x.toString()); }
                    mensagem.add(finalizarOperacao());
                    incrementarPasso();
                }
                break;
            default:
                mensagem.add("Passo desconhecido, saindo da operação.");
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        return "Fim da lista.";
    }

    @Override
    public void reset() {
        setPasso(1);
    }
}
