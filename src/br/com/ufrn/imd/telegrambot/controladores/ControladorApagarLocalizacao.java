package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.*;

import java.io.*;
import java.util.*;

public class ControladorApagarLocalizacao extends Controlador{

    Localizacao localizacao;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorApagarLocalizacao() {
        super("/apagarlocalizacao", 4);
        localizacao = new Localizacao();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Localizacao> localizacoes = aux.listaLocalizacoes();
        List<Bem> bens = aux.listaBens();
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("Qual é o nome da localização que vai ser excluida?\nAbaixo estão todas localizações cadastradas");
                List<String> nomesLocalizacoes = aux.ImprimirNomeLocalizacoes(localizacoes); //Cria uma lista com os nomes das localizações presentes em 'localizacao.txt'
                for(String x : nomesLocalizacoes){
                    mensagem.add(x);
                }
                incrementarPasso();
                break;
            case 2:
                localizacao = aux.buscaLocalizacao(localizacoes,mensagemRecebida);
                if (localizacao == null){
                    mensagem.add("A localização informada não foi encontrada no sistema");
                }
                else {
                    for(Bem x : bens){
                        if(x.getLocalizacao().getNome().equalsIgnoreCase(localizacao.getNome())){
                            mensagem.add("A localização inserida ainda tem bens associados, antes de apagar uma localização" +
                                    " é preciso remover ou movimentar todos os seus bens");
                            break;
                        }
                    }
                    incrementarPasso();
                    break;
                }
            case 3:
                mensagem.add(finalizarOperacao());
                incrementarPasso();
                break;
            case 4:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Removendo localização selecionada da lista de localizações
                    localizacoes = aux.removerLocalizacao(localizacoes, localizacao.getNome());
                    //Apagando os dados do arquivo
                    PrintWriter writer = new PrintWriter("localizacao.txt");
                    writer.print("");
                    writer.close();

                    //Reescrevendo dados do arquivo com a lista de bens atualizada
                    BufferedWriter file = new BufferedWriter(new FileWriter("localizacao.txt",true));
                    for(Localizacao x : localizacoes){
                        file.write(x.getNome() + "\n" + x.getDescricao() + "\n------");
                        file.newLine();
                    }
                    file.close();

                    mensagem.add("Localização apagada com sucesso!");
                    incrementarPasso();
                }
                else if(mensagemRecebida.toLowerCase().equals("n")){
                    mensagem.add("Operação cancelada");
                    incrementarPasso();
                }
                else{
                    mensagem.add("Resposta inválida");
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
        String mensagem = "A localização que você deseja apagar é:\nNome: " + localizacao.getNome() +
                "\nDescrição: " + localizacao.getDescricao() +"\nPosso Apagar a localização? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        setPasso(1);
    }
}
