package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Localizacao;

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
            case 1: //Enviando mensagem para o chat do telegram
                mensagem.add("Qual é o nome da localização a ser cadastrada?");
                incrementarPasso();
                break;
            case 2: //Recebendo o nome da localização
                localizacao.setNome(mensagemRecebida);
                incrementarPasso();
                mensagem = chat(mensagemRecebida); //Faz com que o proximo passo seja chamado
                break;
            case 3: //Enviando mensagem para o chat do telegram
                mensagem.add("Escreva uma pequena descrição dessa localização.");
                incrementarPasso();
                break;
            case 4: //Recebendo a descrição da localização
                localizacao.setDescricao(mensagemRecebida);
                incrementarPasso();
                mensagem = chat(mensagemRecebida);
                break;
            case 5: //Chamando operação finalizar operação para confirmar dados recebidos
                mensagem.add(finalizarOperacao());
                incrementarPasso();
                break;
            case 6: //Ao confirmar os dados, salva em arquivo
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
                    mensagem.add("Operação cancelada.");
                    incrementarPasso();
                }
                else{
                    mensagem.add("Resposta inválida.");
                }
                break;
            default:
                mensagem.add("Passo desconhecido, saindo da operação");
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        String mensagem = "Confirme se os dados abaixo estão corretos: \nNome: " + localizacao.getNome() + "\nDescrição: " + localizacao.getDescricao() +"\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        localizacao = new Localizacao();
        setPasso(1);
    }
}
