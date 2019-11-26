package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;
import br.com.ufrn.imd.telegrambot.util.Localizacao;

import java.io.*;
import java.util.*;

public class ControladorMovimentarBem extends Controlador {

    FuncoesAuxiliares aux = new FuncoesAuxiliares();
    Bem bem;
    public ControladorMovimentarBem() {
        super("/movimentarbem", 6);
        bem = new Bem();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Bem> bens = aux.listaBens();
        List<Localizacao> localizacoes = aux.listaLocalizacoes();
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()) {
            case 1:
                mensagem.add("Qual é o código do bem a ser movido?");
                incrementarPasso();
                break;
            case 2:
                Bem encontrado = aux.buscarBemCodigo(bens, mensagemRecebida);
                if (encontrado == null) {
                    mensagem.add("O código informado não está sendo utilizado no momento, informe um código diferente.");

                } else {
                    mensagem.add("O código informado está sendo utilizado para o bem:\n" + encontrado.toString());
                    bem = encontrado;
                    incrementarPasso();
                    break;
                }
            case 3:
                mensagem.add("Informe a localização para qual o bem deve ser movido.\nAbaixo estão todas localizações cadastradas.");
                List<String> nomesLocalizacoes = aux.ImprimirNomeLocalizacoes(localizacoes);
                for(String x : nomesLocalizacoes){
                    mensagem.add(x);
                }
                incrementarPasso();
                break;
            case 4:
                Localizacao localizacao = aux.buscaLocalizacao(localizacoes,mensagemRecebida);
                if (localizacao == null){
                    mensagem.add("A localização informada não foi encontrada no sistema, para cadastrar uma localização, " +
                            "primeiro insira '/cancelar' para sair desta operação e '/addlocalizacao' para iniciar o " +
                            "cadastro da localização.");
                }
                else {
                    bem.setLocalizacao(localizacao);
                    incrementarPasso();
                    mensagem = chat(mensagemRecebida);
                    break;
                }
            case 5:
                mensagem.add(finalizarOperacao());
                incrementarPasso();
                break;
            case 6:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    bens = aux.removerBem(bens, bem.getCodigo());
                    bens.add(bem);
                    //Apagando os dados do arquivo
                    PrintWriter writer = new PrintWriter("bem.txt");
                    writer.print("");
                    writer.close();

                    //Reescrevendo dados do arquivo com a lista de bens atualizada
                    BufferedWriter file = new BufferedWriter(new FileWriter("bem.txt",true));
                    for(Bem x : bens){
                        file.write(x.getCodigo() + "\n" + x.getNome() + "\n" + x.getDescricao() + "\n"
                                + x.getLocalizacao().getNome() + "\n" + x.getCategoria().getNome() + "\n------");
                        file.newLine();
                    }
                    file.close();

                    mensagem.add("Bem movido com sucesso!");
                    incrementarPasso();
                }
                else if(mensagemRecebida.toLowerCase().equals("n")){
                    mensagem.add("Operação cancelada.");
                    incrementarPasso();
                }
                else{
                    mensagem.add("Resposta inválida.");
                }
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        String mensagem = "Confirme se os dados abaixo estão corretos: \nCódigo: "+ bem.getCodigo() +"\nNome: " + bem.getNome() +
                "\nDescrição: " + bem.getDescricao() +"\nLocalização: " + bem.getLocalizacao().getNome() + "\nCategoria: " + bem.getCategoria().getNome() +
                "\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        setPasso(1);
    }

}
