package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.*;

import java.io.*;
import java.util.*;

public class FuncoesAuxiliares {

    public List<Localizacao> listaLocalizacoes() throws IOException {
        int numLinha = 0;
        List<Localizacao> localizacoes = new ArrayList<>();
        Localizacao atual = new Localizacao();

        BufferedReader buffer = new BufferedReader(new FileReader("localizacao.txt"));
        while(buffer.ready()){
            if((numLinha == 0)||(numLinha % 3 == 0)){
                localizacoes.add(atual);
                atual = new Localizacao();
                buffer.readLine();
            }
            else if(numLinha % 3 == 1){
                atual.setNome(buffer.readLine());
            }
            else if(numLinha % 3 == 2){
                atual.setDescricao(buffer.readLine());
            }
            numLinha++;
        }
        return localizacoes;
    }

    public List<Categoria> listaCategorias() throws  IOException{
        int numLinha = 1;
        List<Categoria> categorias = new ArrayList<>();
        Categoria atual = new Categoria();

        BufferedReader buffer = new BufferedReader(new FileReader("categoria.txt"));
        while(buffer.ready()){
            if(numLinha % 4 == 0){
                categorias.add(atual);
                atual = new Categoria();
                buffer.readLine();
            }
            else if(numLinha % 4 == 1){
                atual.setCodigo(buffer.readLine());
            }
            else if(numLinha % 4 == 2){
                atual.setNome(buffer.readLine());
            }
            else if(numLinha % 4 == 3){
                atual.setDescricao(buffer.readLine());
            }
            numLinha++;
        }
        return categorias;
    }

    public List<Bem> listaBens() throws IOException {
        int numLinha = 1;
        List<Localizacao> localizacoes = listaLocalizacoes();
        List<Categoria> categorias = listaCategorias();
        List<Bem> bens = new ArrayList<>();
        Bem atual = new Bem();

        BufferedReader buffer = new BufferedReader(new FileReader("bem.txt"));
        while(buffer.ready()){
            if (numLinha % 6 == 0){
                bens.add(atual);
                atual = new Bem();
                buffer.readLine();
            }
            else if(numLinha % 6 == 1){
                atual.setCodigo(buffer.readLine());
            }
            else if(numLinha % 6 == 2){
                atual.setNome(buffer.readLine());
            }
            else if(numLinha % 6 == 3){
                atual.setDescricao(buffer.readLine());
            }
            else if(numLinha % 6 == 4){
                atual.setLocalizacao(buscaLocalizacao(localizacoes, buffer.readLine()));
            }
            else if(numLinha % 6 == 5){
                atual.setCategoria(buscaCategoria(categorias, buffer.readLine()));
            }

            numLinha++;
        }

        return bens;
    }

    public Localizacao buscaLocalizacao(List<Localizacao> localizacoes, String nome) {
        Localizacao localizacao = null;
        for (Localizacao x : localizacoes){
            if(nome.equalsIgnoreCase(x.getNome())){
                localizacao = x;
                break;
            }
        }
        return localizacao;
    }

    public Categoria buscaCategoria(List<Categoria> categorias, String nome) {
        Categoria categoria = null;
        for (Categoria x : categorias){
            if(nome.equalsIgnoreCase(x.getNome())){
                categoria = x;
            }
        }
        return categoria;
    }
}
