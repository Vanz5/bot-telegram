package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ControladorListarBem extends Controlador {

    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorListarBem() {
        super("/listarbem", 2);
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Bem> bens = aux.listaBens();
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("De qual das localizações abaixos eu devo listar os bens?");
                ControladorListarLocalizacao lista = new ControladorListarLocalizacao();
                mensagem.add(lista.chat("listagem de locais").get(0)); //TODO - não entendi essa linha aqui -> Testar com ela comentada
                setPasso(getPasso() + 1);
                break;
            case 2:
                String localizacao = mensagemRecebida;
                List<Bem> encontrados = buscaBemLocalizacao(bens,localizacao);
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

    public List<Bem> buscaBemLocalizacao(List<Bem> bens, String localizacao){
        List<Bem> resultado = new ArrayList<>();
        for(Bem x: bens){
            if(localizacao.equalsIgnoreCase(x.getLocalizacao().getNome())){
                resultado.add(x);
            }
        }
        return resultado;
    }
}

