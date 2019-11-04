public class Localizacao {

    //1 - cadastrar localização (ex.: sala, laboratório, auditório, etc.)
    // a. Realiza o cadastro de uma localização. Os dados deverão ser salvos em alguma memória não volátil,
    // por exemplo, um arquivo. Uma localização deverá conter no mínimo os atributos nome e descrição.

    String nome, descricao;

    public Localizacao(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        // TODO - adicionar a lista apos a criação (evento?)
    }

}
