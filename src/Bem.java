public class Bem {
    //3 - cadastrar bem (cadeira, mesa, computador, sabão em pó, etc.)
    //a. Realiza o cadastro de um bem. Os dados deverão ser salvos em alguma memória não volátil,
    // por exemplo, um arquivo. Um  bem deverá conter no mínimo os atributos codig, nome, descricao, localização e categoria

    int codigo;
    String nome, descricao;
    Localizacao localizacao;
    Categoria categoria;

    public Bem(int codigo, String nome, String descricao, Localizacao localizacao, Categoria categoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.categoria = categoria;
        // TODO - adicionar a lista apos a criação (evento?)
        // TODO - tratar caso o codigo seja o mesmo
    }

}
