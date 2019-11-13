package br.com.ufrn.imd.telegrambot.util;

public class Localizacao {

    //1 - cadastrar localização (ex.: sala, laboratório, auditório, etc.)
    // a. Realiza o cadastro de uma localização. Os dados deverão ser salvos em alguma memória não volátil,
    // por exemplo, um arquivo. Uma localização deverá conter no mínimo os atributos nome e descrição.

    private String nome;
    private String descricao;

    public Localizacao() {
        this.nome = null;
        this.descricao = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
