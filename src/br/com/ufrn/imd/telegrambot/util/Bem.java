package br.com.ufrn.imd.telegrambot.util;

public class Bem {
    //3 - cadastrar bem (cadeira, mesa, computador, sabão em pó, etc.)
    //a. Realiza o cadastro de um bem. Os dados deverão ser salvos em alguma memória não volátil,
    // por exemplo, um arquivo. Um  bem deverá conter no mínimo os atributos codig, nome, descricao, localização e categoria

    private int codigo;
    private String nome, descricao;
    private Localizacao localizacao;
    private Categoria categoria;

    public Bem() {
        this.codigo = 0;
        this.nome = null;
        this.descricao = null;
        this.localizacao = new Localizacao();
        this.categoria = new Categoria();
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
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
