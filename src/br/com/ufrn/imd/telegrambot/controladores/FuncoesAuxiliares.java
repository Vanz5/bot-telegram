package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.*;

import java.io.*;
import java.util.*;

public class FuncoesAuxiliares {

    public List<Localizacao> listaLocalizacoes() throws IOException {
        int numLinha = 1;
        List<Localizacao> localizacoes = new ArrayList<>();
        Localizacao atual = new Localizacao();

        BufferedReader buffer = new BufferedReader(new FileReader("localizacao.txt"));
        while(buffer.ready()) {
            if(numLinha % 3 == 0) {
                localizacoes.add(atual);
                atual = new Localizacao();
                buffer.readLine();
            }
            else if(numLinha % 3 == 1) { atual.setNome(buffer.readLine()); }
            else if(numLinha % 3 == 2) { atual.setDescricao(buffer.readLine()); }
            numLinha++;
        }
        return localizacoes;
    }

    public List<String> ImprimirNomeLocalizacoes(List<Localizacao> localizacoes) throws IOException{
        List<String> nomes = new ArrayList<>();
        for (Localizacao x: localizacoes) {
            nomes.add(x.getNome() + "\n");
        }
        return nomes;
    }

    public List<Categoria> listaCategorias() throws  IOException{
        int numLinha = 1;
        List<Categoria> categorias = new ArrayList<>();
        Categoria atual = new Categoria();

        BufferedReader buffer = new BufferedReader(new FileReader("categoria.txt"));
        while(buffer.ready()) {
            if(numLinha % 4 == 0) {
                categorias.add(atual);
                atual = new Categoria();
                buffer.readLine();
            }
            else if(numLinha % 4 == 1) { atual.setCodigo(buffer.readLine()); }
            else if(numLinha % 4 == 2) { atual.setNome(buffer.readLine()); }
            else if(numLinha % 4 == 3) { atual.setDescricao(buffer.readLine()); }
            numLinha++;
        }
        return categorias;
    }

    public List<String> ImprimirNomeCategorias(List<Categoria> categorias) throws IOException{
        List<String> nomes = new ArrayList<>();
        for (Categoria c: categorias) {
            nomes.add(c.getNome() + "\n");
        }
        return nomes;
    }

    public List<Bem> listaBens() throws IOException{
        int numLinha = 1;
        List<Localizacao> localizacoes = listaLocalizacoes();
        List<Categoria> categorias = listaCategorias();
        List<Bem> bens = new ArrayList<>();
        Bem atual = new Bem();

        BufferedReader buffer = new BufferedReader(new FileReader("bem.txt"));
        while(buffer.ready()) {
            if(numLinha % 6 == 0) {
                bens.add(atual);
                atual = new Bem();
                buffer.readLine();
            }
            else if(numLinha % 6 == 1) { atual.setCodigo(buffer.readLine()); }
            else if(numLinha % 6 == 2) { atual.setNome(buffer.readLine()); }
            else if(numLinha % 6 == 3) { atual.setDescricao(buffer.readLine()); }
            else if(numLinha % 6 == 4) { atual.setLocalizacao(buscaLocalizacao(localizacoes, buffer.readLine())); }
            else if(numLinha % 6 == 5) { atual.setCategoria(buscaCategoria(categorias, buffer.readLine())); }

            numLinha++;
        }
        return bens;
    }

    public Localizacao buscaLocalizacao(List<Localizacao> localizacoes, String nome){
        Localizacao localizacao = null;
        for(Localizacao x : localizacoes){
            if(nome.equalsIgnoreCase(x.getNome())) {
                localizacao = x;
                break;
            }
        }
        return localizacao;
    }

    public Categoria buscaCategoria(List<Categoria> categorias, String nome){
        Categoria categoria = null;
        for(Categoria x : categorias){
            if(nome.equalsIgnoreCase(x.getNome())) {
                categoria = x;
                break;
            }
        }
        return categoria;
    }

    public List<Bem> removerBem(List<Bem> bens, String codigo){
        for(Bem x : new ArrayList<Bem>(bens)) {
            if(codigo.equalsIgnoreCase(x.getCodigo())) {
                bens.remove(x);
            }
        }
        return bens;
    }

    public List<Localizacao> removerLocalizacao(List<Localizacao> localizacoes, String nome){
        for(Localizacao x : new ArrayList<Localizacao>(localizacoes)) {
            if(nome.equalsIgnoreCase(x.getNome())) {
                localizacoes.remove(x);
            }
        }
        return localizacoes;
    }

    public List<Categoria> removerCategoria(List<Categoria> categorias, String nome){
        for(Categoria x : new ArrayList<Categoria>(categorias)) {
            if(nome.equalsIgnoreCase(x.getNome())) {
                categorias.remove(x);
            }
        }
        return categorias;
    }

    public Bem buscarBemCodigo(List<Bem> bens, String codigo){
        Bem bem = null;
        for(Bem x : bens) {
            if(codigo.equalsIgnoreCase(x.getCodigo())) {
                bem = x;
                break;
            }
        }
        return bem;
    }

    public Bem buscarBemNome(List<Bem> bens, String nome){
        Bem bem = null;
        for(Bem x : bens) {
            if (nome.equalsIgnoreCase(x.getNome())) {
                bem = x;
                break;
            }
        }
        return bem;
    }


    public List<Bem> buscarBemLocalizacao(List<Bem> bens, String nome){
        List<Bem> encontrados = new ArrayList<>();
        for(Bem x: bens) {
            if(nome.equalsIgnoreCase(x.getLocalizacao().getNome())) {
                encontrados.add(x);
            }
        }
        return encontrados;
    }

    public Categoria buscarCategoriaCodigo(List<Categoria> categorias, String codigo){
        Categoria categoria = null;
        for(Categoria x : categorias) {
            if(codigo.equalsIgnoreCase(x.getCodigo())) {
                categoria = x;
                break;
            }
        }
        return categoria;
    }

    public Bem buscaBemDescricao(List<Bem> bens, String descricao){
        Bem bem = null;
        for(Bem x : bens){
            if(descricao.equalsIgnoreCase(x.getDescricao())){
                bem = x;
                break;
            }
        }
        return bem;
    }
}
