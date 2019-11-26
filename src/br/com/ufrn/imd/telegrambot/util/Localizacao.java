package br.com.ufrn.imd.telegrambot.util;

public class Localizacao {
    private String nome;
    private String descricao;

    public Localizacao() {
        this.nome = null;
        this.descricao = null;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String toString(){
        String localizacao = "Nome: " + this.getNome()
                           + "\nDescrição: " + this.getDescricao() + "\n";
        return localizacao;
    }

}
