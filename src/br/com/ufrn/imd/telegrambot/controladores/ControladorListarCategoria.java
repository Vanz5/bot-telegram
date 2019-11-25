package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Categoria;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ControladorListarCategoria extends Controlador {

    Categoria categoria;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorListarCategoria() {
        super("/listarcategoria", 2);
        categoria = new Categoria();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Categoria> categorias = aux.listaCategorias();
        List<String> mensagem = new ArrayList<String>();

        switch (getPasso()){
            case 1:
                if(categorias.isEmpty()){
                    mensagem.add("Não existem categorias cadastradas, insira '/cancelar' para sair dessa operação");
                }
                else {
                    mensagem.add("Categorias cadastradas:\n");
                    for(Categoria x : categorias) { mensagem.add(x.toString()); }
                    setPasso(getPasso() + 1);
                }
                break;
            default:
                mensagem.add("Passo desconhecido");
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        return "";
    }

    @Override
    public void reset() {
        setPasso(1);
    }

    public String imprimir(String arquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivo)));
    }
}
