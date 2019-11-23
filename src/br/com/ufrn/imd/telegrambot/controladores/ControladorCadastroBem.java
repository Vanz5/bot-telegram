package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;
import br.com.ufrn.imd.telegrambot.util.Categoria;
import br.com.ufrn.imd.telegrambot.util.Localizacao;

import java.io.*;
import java.util.*;

public class ControladorCadastroBem extends Controlador {

    Bem bem;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorCadastroBem() {
        super("/addbem", 12);
        bem = new Bem();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("Qual é o código do bem que vai ser cadastrado?");
                setPasso(getPasso() + 1);
                break;
            case 2:
                //TODO - Tratamento para conferir se o código está livre para uso
                bem.setCodigo(mensagemRecebida);
                setPasso(getPasso() + 1);
                break;
            case 3:
                mensagem.add("Qual é o nome do bem que vai ser cadastrado?");
                setPasso(getPasso() + 1);
                break;
            case 4:
                bem.setNome(mensagemRecebida);
                setPasso(getPasso() + 1);
                break;
            case 5:
                mensagem.add("Escreva uma pequena descrição desse bem.");
                setPasso(getPasso() + 1);
                break;
            case 6:
                bem.setDescricao(mensagemRecebida);
                setPasso(getPasso() + 1);
                break;
            case 7:
                //TODO - listar opções na hora de inserir categorias e localizações
                mensagem.add("Qual é a localização desse bem?\n Abaixo estão todas categorias cadastradas");
                //mensagem.add(adicionar uma lista só com o nome das localizaçõoes numa lista);
                setPasso(getPasso() + 1);
                break;
            case 8:
                List<Localizacao> localizacoes = aux.listaLocalizacoes();
                Localizacao localizacao = aux.buscaLocalizacao(localizacoes,mensagemRecebida);
                if (localizacao == null){
                    mensagem.add("A localização informada não foi encontrada no sistema, para cadastrar uma localização " +
                            "primeiro insira '/cancelar' para sair desta operação e '/addlocalizacao' para iniciar o " +
                            "cadastro da localização.");
                }
                else {
                    bem.setLocalizacao(localizacao);
                    setPasso(getPasso() + 1);
                    //mensagem = chat(mensagemRecebida); //TODO - descobrir o que essa linha faz
                }
                break;
            case 9:
                mensagem.add("Qual é a categoria desse bem?");
                //mensagem.add(adicionar uma lista só com o nome das categorias numa lista);
                setPasso(getPasso() + 1);
                break;
            case 10:
                List<Categoria> categorias = aux.listaCategorias();
                Categoria categoria = aux.buscaCategoria(categorias,mensagemRecebida);
                if(categoria == null){
                    mensagem.add("A categoria informada não foi encontrada no sistema, para cadastrar uma nova categoria" +
                            " primeiro insira '/cancelar' para sair desta operação e '/addcategoria' para iniciar o cadastro" +
                            " da categoria.");
                }
                else{
                    bem.setCategoria(categoria);
                    setPasso(getPasso() + 1);
                    //mensagem = chat(mensagemRecebida);
                }
                break;
            case 11:
                mensagem.add(finalizarOperacao());
                setPasso(getPasso() + 1);
                break;
            case 12:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Armazenando em arquivo
                    BufferedWriter file = new BufferedWriter(new FileWriter("bem.txt",true));
                    file.write(bem.getCodigo() + "\n" + bem.getNome() + "\n" + bem.getDescricao() + "\n" + bem.getLocalizacao().getNome() + "\n" + bem.getCategoria().getNome() + "\n------");
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
        String mensagem = "Confirme se os dados abaixo estão certos: \nNome: " + bem.getNome() + "\nDescrição: " + bem.getDescricao() +"\nLocalização: " + bem.getLocalizacao().getNome() + "\nCategoria: " + bem.getCategoria().getNome() + "\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        bem = new Bem();
        setPasso(1);
    }
}
