package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;
import br.com.ufrn.imd.telegrambot.util.Localizacao;

import java.io.IOException;
import java.util.*;

public class ControladorListarBem extends Controlador {

    Bem bem;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorListarBem() {
        super("/listarbem", 2);
        bem = new Bem();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Bem> bens = aux.listaBens(); //Lista com os bens listadas em 'bem.txt'
        List<Localizacao> localizacoes = aux.listaLocalizacoes(); //Lista com as looalizações listadas em 'localizacao.txt'
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("De qual das localizações abaixo eu devo listar os bens?");
                List<String> nomesLocalizacoes = aux.ImprimirNomeLocalizacoes(localizacoes);
                for(String x : nomesLocalizacoes){ //Imprime no chat o nome das localizações cadastradas
                    mensagem.add(x);
                }
                incrementarPasso();
                break;
            case 2:
                String local = mensagemRecebida;
                Localizacao localizacao = aux.buscaLocalizacao(localizacoes,local); //Procurar localização inserida nas localizações cadastradas
                if (localizacao == null){
                    mensagem.add("A localização informada não foi encontrada no sistema, para cadastrar uma localização, " +
                            "primeiro insira '/cancelar' para sair desta operação e '/addlocalizacao' para iniciar o " +
                            "cadastro da localização.");
                }
                else {
                    List<Bem> encontrados = aux.buscarBemLocalizacao(bens,local); //Gera uma lista com os bens da localização selecionada

                    if(encontrados.isEmpty()){ //Quando a lista não possui nenhum bem
                        mensagem.add("Não existe nenhum bem nesta localização, insira outra localização ou '/cancelar' para sair desta operação.");
                    }
                    else{
                        mensagem.add("Bens encontrados em "+ localizacao.getNome() + ":");
                        for(Bem x : encontrados){
                            mensagem.add(x.toString());
                        }
                        mensagem.add(finalizarOperacao());
                        incrementarPasso();
                    }
                    break;
                }
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

