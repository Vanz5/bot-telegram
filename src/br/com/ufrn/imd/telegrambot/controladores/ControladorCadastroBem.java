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
        List<Bem> bens = aux.listaBens();
        List<Localizacao> localizacoes = aux.listaLocalizacoes();
        List<Categoria> categorias = aux.listaCategorias();
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("Qual é o código do bem que vai ser cadastrado?");
                incrementarPasso();
                break;
            case 2:
                String codigo = (mensagemRecebida);
                ControladorBuscarBemCodigo buscarBemCodigo = new ControladorBuscarBemCodigo();
                Bem encontrado = aux.buscarBemCodigo(bens,codigo);
                if(encontrado == null){
                    bem.setCodigo(codigo);
                    incrementarPasso();
                    mensagem = chat(mensagemRecebida);
                }
                else{
                    mensagem.add("O codigo informado já está sendo utilizado para o bem: "+ encontrado.getNome()+"\n Informe um código diferente");
                }
                break;
            case 3:
                mensagem.add("Qual é o nome do bem que vai ser cadastrado?");
                incrementarPasso();
                break;
            case 4:
                bem.setNome(mensagemRecebida);
                incrementarPasso();
                break;
            case 5:
                mensagem.add("Escreva uma pequena descrição desse bem.");
                incrementarPasso();
                break;
            case 6:
                bem.setDescricao(mensagemRecebida);
                incrementarPasso();
                break;
            case 7:
                mensagem.add("Qual é a localização desse bem?\nAbaixo estão todas localizações cadastradas");
                List<String> nomesLocalizacoes = aux.ImprimirNomeLocalizacoes(localizacoes);
                for(String x : nomesLocalizacoes){
                    mensagem.add(x);
                }
                incrementarPasso();
                break;
            case 8:
                Localizacao localizacao = aux.buscaLocalizacao(localizacoes,mensagemRecebida);
                if (localizacao == null){
                    mensagem.add("A localização informada não foi encontrada no sistema, para cadastrar uma localização " +
                            "primeiro insira '/cancelar' para sair desta operação e '/addlocalizacao' para iniciar o " +
                            "cadastro da localização.");
                }
                else {
                    bem.setLocalizacao(localizacao);
                    incrementarPasso();
                    mensagem = chat(mensagemRecebida); //TODO - descobrir o que essa linha faz
                }
                break;
            case 9:
                mensagem.add("Qual é a categoria desse bem?\nAbaixo estão todas categorias cadastradas");
                List<String> nomesCategoria = aux.ImprimirNomeCategorias(categorias);
                for(String x : nomesCategoria){
                    mensagem.add(x);
                }
                incrementarPasso();
                break;
            case 10:
                Categoria categoria = aux.buscaCategoria(categorias,mensagemRecebida);
                if(categoria == null){
                    mensagem.add("A categoria informada não foi encontrada no sistema, para cadastrar uma nova categoria" +
                            " primeiro insira '/cancelar' para sair desta operação e '/addcategoria' para iniciar o cadastro" +
                            " da categoria.");
                }
                else{
                    bem.setCategoria(categoria);
                    incrementarPasso();
                    mensagem = chat(mensagemRecebida);
                }
                break;
            case 11:
                mensagem.add(finalizarOperacao());
                incrementarPasso();
                break;
            case 12:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Armazenando em arquivo
                    BufferedWriter file = new BufferedWriter(new FileWriter("bem.txt",true));
                    file.write(bem.getCodigo() + "\n" + bem.getNome() + "\n" + bem.getDescricao() + "\n" + bem.getLocalizacao().getNome() + "\n" + bem.getCategoria().getNome() + "\n------");
                    file.newLine();
                    file.close();

                    mensagem.add("Bem cadastrado com sucesso!");
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
        String mensagem = "Confirme se os dados abaixo estão certos: \nCódigo: "+ bem.getCodigo() +"\nNome: " + bem.getNome() +
                "\nDescrição: " + bem.getDescricao() +"\nLocalização: " + bem.getLocalizacao().getNome() + "\nCategoria: " + bem.getCategoria().getNome() +
                "\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        bem = new Bem();
        setPasso(1);
    }
}
