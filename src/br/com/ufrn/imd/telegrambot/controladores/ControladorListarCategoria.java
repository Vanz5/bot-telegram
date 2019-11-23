package br.com.ufrn.imd.telegrambot.controladores;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ControladorListarCategoria extends Controlador {

    public ControladorListarCategoria() {
        super("/listarcategoria", 1);
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add(imprimir("categoria.txt"));
                setPasso(getPasso() + 1);
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
