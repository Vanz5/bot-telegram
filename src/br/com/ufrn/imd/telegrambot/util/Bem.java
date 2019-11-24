package br.com.ufrn.imd.telegrambot.util;

public class Bem {
    private String codigo; //Inicialmente foi pensando como INTEIRO mas como código de itens de patrimonio normalmente são misturas de numeros e letras, foi modificado para String
    private String nome, descricao;
    private Localizacao localizacao;
    private Categoria categoria;

    public Bem() {
        this.codigo = null;
        this.nome = null;
        this.descricao = null;
        this.localizacao = new Localizacao();
        this.categoria = new Categoria();
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
    public Localizacao getLocalizacao() {
        return localizacao;
    }
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
