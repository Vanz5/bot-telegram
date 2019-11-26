package br.com.ufrn.imd.telegrambot.util;

public class Categoria {
    private String codigo; //Inicialmente foi pensado como INTEIRO mas como código de itens de patrimonio normalmente são misturas de numeros e letras, foi modificado para String
    private String nome;
    private String descricao;

    public Categoria() {
        this.codigo = "";
        this.nome = "";
        this.descricao = "";
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

    public String toString() {
        String categoria = "Código: " + this.getCodigo()
                         + "\nNome: " + this.getNome()
                         + "\nDescrição: " + this.getDescricao() + "\n";
        return categoria;
    }
}
