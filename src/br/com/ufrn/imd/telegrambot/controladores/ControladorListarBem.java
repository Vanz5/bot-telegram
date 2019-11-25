package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;
import br.com.ufrn.imd.telegrambot.util.Localizacao;

import java.io.IOException;
import java.nio.file.*;
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
        List<Bem> bens = aux.listaBens();
        List<Localizacao> localizacoes = aux.listaLocalizacoes();
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("De qual das localizações abaixos eu devo listar os bens?");
                List<String> nomesLocalizacoes = aux.ImprimirNomeLocalizacoes(localizacoes);
                for(String x : nomesLocalizacoes){
                    mensagem.add(x);
                }
                setPasso(getPasso() + 1);
                break;
            case 2:
                //String localizacao = mensagemRecebida;
                Localizacao localizacao = aux.buscaLocalizacao(localizacoes,mensagemRecebida);
                if (localizacao == null){
                    mensagem.add("A localização informada não foi encontrada no sistema, para cadastrar uma localização " +
                            "primeiro insira '/cancelar' para sair desta operação e '/addlocalizacao' para iniciar o " +
                            "cadastro da localização.");
                }
                else {
                    List<Bem> encontrados = aux.buscaBemLocalizacao(bens,mensagemRecebida);

                    if(encontrados.isEmpty()){
                        mensagem.add("Não existem nenhum bem nesta localização, insira outra localização ou '/cancelar' para sair dessa operação");
                    }
                    else{
                        mensagem.add("Bens encontrados em "+ localizacao.getNome() + ":");
                        for(Bem x : encontrados){
                            mensagem.add(x.toString());
                        }
                        setPasso(getPasso() + 1);
                    }
                    break;
                }
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
}

