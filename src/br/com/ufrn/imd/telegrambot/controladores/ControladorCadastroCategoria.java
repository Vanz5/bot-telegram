package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Categoria;

import java.io.*;
import java.util.*;

public class ControladorCadastroCategoria extends Controlador {

    Categoria categoria;

    public ControladorCadastroCategoria() {
        super("/addcategoria", 8);
        categoria = new Categoria();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("Qual é o código da categoria que vai ser cadastrada?");
                setPasso(getPasso() + 1);
                break;
            case 2:
                //TODO - Tratamento para conferir se o código está livre para uso
                categoria.setCodigo(mensagemRecebida);
                setPasso(getPasso() + 1);
                break;
            case 3:
                mensagem.add("Qual é o nome da categoria que vai ser cadastrada?");
                setPasso(getPasso() + 1);
                break;
            case 4:
                categoria.setNome(mensagemRecebida);
                setPasso(getPasso() + 1);
                break;
            case 5:
                mensagem.add("Escreva uma pequena descrição dessa categoria.");
                setPasso(getPasso() + 1);
                break;
            case 6:
                categoria.setDescricao(mensagemRecebida);
                setPasso(getPasso() + 1);
                break;
            case 7:
                mensagem.add(finalizarOperacao());
                setPasso(getPasso() + 1);
                break;
            case 8:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Armazenando em arquivo
                    BufferedWriter file = new BufferedWriter(new FileWriter("categoria.txt",true));
                    file.write(categoria.getCodigo() + "\n" + categoria.getNome() + "\n" + categoria.getDescricao() + "\n------");
                    file.newLine();
                    file.close();

                    mensagem.add("Categoria cadastrada com sucesso!");
                    setPasso(getPasso() + 1);
                }
                else if(mensagemRecebida.toLowerCase().equals("n")){
                    mensagem.add("Operação cancelada");
                    setPasso(getPasso() + 1);
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
        String mensagem = "Confirme se os dados abaixo estão certos: \nCódigo: "+ categoria.getCodigo() +"\nNome: " + categoria.getNome() +
                "\nDescrição: " + categoria.getDescricao() +"\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        categoria = new Categoria();
        setPasso(1);
    }
}
