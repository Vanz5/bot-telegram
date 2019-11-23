package br.com.ufrn.imd.telegrambot.util;

public class Categoria {
    //2 - cadastrar categoria de bem (ex.: móvel, eletrônico, material de limpeza, etc.)
    //a. Realiza o cadastro da categoria de um bem. Os dados deverão ser salvos em alguma memória não volátil,
    // por exemplo, um arquivo. Uma categoria deverá conter no mínimo os atributos codigo, nome e descricao

    private String codigo;
    private String nome, descricao;

    public Categoria() {
        this.codigo = null;
        this.nome = null;
        this.descricao = null;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
