package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Localizacao;

import java.io.BufferedWriter;
import java.io.*;
import java.util.*;


public class ControladorCadastroLocalizacao extends Controlador {

    Localizacao localizacao;

    public ControladorCadastroLocalizacao() {
        super("/addlocalizacao", 6);
        localizacao = new Localizacao();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("Qual é o nome da localização que vai ser cadastrada?");
                incrementarPasso();
                break;
            case 2:
                localizacao.setNome(mensagemRecebida);
                incrementarPasso();
                break;
            case 3:
                mensagem.add("Escreva uma pequena descrição dessa localização.");
                incrementarPasso();
                break;
            case 4:
                localizacao.setDescricao(mensagemRecebida);
                incrementarPasso();
                break;
            case 5:
                mensagem.add(finalizarOperacao());
                incrementarPasso();
                break;
            case 6:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Armazenando em arquivo
                    BufferedWriter file = new BufferedWriter(new FileWriter("localizacao.txt",true));
                    file.write(localizacao.getNome() + "\n" + localizacao.getDescricao() + "\n------");
                    file.newLine();
                    file.close();

                    mensagem.add("Localização cadastrada com sucesso!");
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
        String mensagem = "Confirme se os dados abaixo estão certos: \nNome: " + localizacao.getNome() + "\nDescrição: " + localizacao.getDescricao() +"\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        localizacao = new Localizacao();
        setPasso(1);
    }
}
