public class Categoria {
    //2 - cadastrar categoria de bem (ex.: móvel, eletrônico, material de limpeza, etc.)
    //a. Realiza o cadastro da categoria de um bem. Os dados deverão ser salvos em alguma memória não volátil,
    // por exemplo, um arquivo. Uma categoria deverá conter no mínimo os atributos codigo, nome e descricao

    int codigo;
    String nome, descricao;

    public Categoria(int codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        // TODO - adicionar a lista apos a criação (evento?)
        // TODO - tratar caso o codigo seja o mesmo
    }
}
