package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Localizacao;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ControladorListarLocalizacao extends Controlador {

    Localizacao localizacao;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorListarLocalizacao() {
        super("/listarlocalizacao", 2);
        localizacao = new Localizacao();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Localizacao> localizacoes = aux.listaLocalizacoes();
        List<String> mensagem = new ArrayList<String>();

        switch (getPasso()){
            case 1:
                if(localizacoes.isEmpty()){
                    mensagem.add("Não existem localizações cadastradas, insira '/cancelar' para sair dessa operação");
                }
                else {
                    mensagem.add("Localizações cadastradas:\n");
                    for(Localizacao x : localizacoes) { mensagem.add(x.toString()); }
                    incrementarPasso();
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
