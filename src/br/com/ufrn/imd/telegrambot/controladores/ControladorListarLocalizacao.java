package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Localizacao;

import java.io.IOException;
import java.util.*;

public class ControladorListarLocalizacao extends Controlador {

    Localizacao localizacao;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorListarLocalizacao() {
        super("/listarlocalizacao", 1);
        localizacao = new Localizacao();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Localizacao> localizacoes = aux.listaLocalizacoes();
        List<String> mensagem = new ArrayList<String>();

        switch (getPasso()){
            case 1:
                if(localizacoes.isEmpty()){ //Checa se existem localizacoes cadastradas
                    mensagem.add("Não existem localizações cadastradas, insira '/cancelar' para sair desta operação.");
                }
                else { //Mostra no chat todas as localizações cadastradas em 'localizacoes.txt'
                    mensagem.add("Localizações cadastradas:\n");
                    for(Localizacao x : localizacoes) { mensagem.add(x.toString()); }
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
