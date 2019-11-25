package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ControladorGerarRelatorioArquivo extends Controlador {

    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorGerarRelatorioArquivo() {
        super("/relatorioarquivo", 1);
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<String> mensagem = new ArrayList<>();
        List<Bem> bens = aux.listaBens();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter dtfArquivo = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        LocalDateTime data = LocalDateTime.now();

        switch (getPasso()){
            case 1:
                BemLocalizacaoComparator bemLocalizacaoComparator = new BemLocalizacaoComparator();
                BemCategoriaComparator bemCategoriaComparator = new BemCategoriaComparator();
                BemNomeComparator bemNomeComparator = new BemNomeComparator();

                BufferedWriter file = new BufferedWriter(new FileWriter("relatorio" + dtfArquivo.format(data) + ".txt"));

                file.write("Relatório criado em: " + dtf.format(data) + "\nListagem de bens agrupados por localização:\n");
                Collections.sort(bens,bemLocalizacaoComparator);
                for(Bem x : bens){
                    mensagem.add(x.toString()+"\n------\n");
                }

                file.write(" Listagem de bens agrupados por categoria:\n");
                Collections.sort(bens,bemCategoriaComparator);
                for(Bem x : bens){
                    file.write(x.toString()+"\n------\n");
                }

                file.write(" Listagem de bens agrupados por nome:\n");
                Collections.sort(bens,bemNomeComparator);
                for(Bem x : bens){
                    file.write(x.toString()+"\n------\n");
                }

                file.write("Relatório finalizado");
                file.close();
                mensagem.add("O relatorio foi gerado no arquivo: 'relatorio"+ dtfArquivo.format(data) + ".txt'");
                setPasso(getPasso() + 1);
                break;

        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        return null;
    }

    @Override
    public void reset() {
        setPasso(1);
    }
}

