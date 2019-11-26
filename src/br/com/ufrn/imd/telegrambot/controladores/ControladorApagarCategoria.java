package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.*;

import java.io.*;
import java.util.*;

public class ControladorApagarCategoria extends Controlador{

    Categoria categoria;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorApagarCategoria() {
        super("/apagarcategoria", 4);
        categoria = new Categoria();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        
        List<Categoria> categorias = aux.listaCategorias();
        List<Bem> bens = aux.listaBens();
        List<String> mensagem = new ArrayList<String>();
        
        switch (getPasso()){
            case 1:
                mensagem.add("Qual é o nome da categoria a ser excluida?\nAbaixo estão todas categorias cadastradas.");
                List<String> nomesCategoria = aux.ImprimirNomeCategorias(categorias); //Cria uma lista com apenas os nomes das categorias presentes em 'categoria.txt'
                for(String x : nomesCategoria){ // TODO - modificar para mostar codigo e nome e receber codigo(que é unico) no lugar do nome
                    mensagem.add(x);
                }
                incrementarPasso();
                break;
            case 2:
                categoria = aux.buscaCategoria(categorias,mensagemRecebida);
                if (categoria == null){
                    mensagem.add("A categoria informada não foi encontrada no sistema.");
                }
                else {
                    for(Bem x : bens){
                        if(x.getCategoria().getNome().equalsIgnoreCase(categoria.getNome())){
                            mensagem.add("A categoria inserida ainda tem bens associados, antes de apagar uma categoria" +
                                    " é necessário remover todos os seus bens.");
                            // TODO - tratar situação em que mesmo recebendo essa msg o usuario continua no fluxo do controlador normalmente
                            break;
                        }
                    }
                    incrementarPasso();
                    break;
                }
            case 3:
                mensagem.add(finalizarOperacao());
                incrementarPasso();
                break;
            case 4:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    // Removendo categoria selecionada da lista de categorias.
                    categorias = aux.removerCategoria(categorias, categoria.getNome());
                    // Apagando os dados do arquivo.
                    PrintWriter writer = new PrintWriter("categoria.txt");
                    writer.print("");
                    writer.close();

                    // Reescrevendo dados do arquivo com a lista de bens atualizada.
                    BufferedWriter file = new BufferedWriter(new FileWriter("categoria.txt",true));
                    for(Categoria x : categorias){
                        file.write(x.getCodigo() + "\n" + x.getNome() + "\n" + x.getDescricao() + "\n------");
                        file.newLine();
                    }
                    file.close();

                    mensagem.add("Categoria apagada com sucesso!");
                    incrementarPasso();
                }
                else if(mensagemRecebida.toLowerCase().equals("n")){
                    mensagem.add("Operação cancelada.");
                    incrementarPasso();
                }
                else{
                    mensagem.add("Resposta inválida.");
                }
                break;
            default:
                mensagem.add("Passo desconhecido.");
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        String mensagem = "A categoria que você deseja apagar é: \nCódigo: "+ categoria.getCodigo() +"\nNome: " + categoria.getNome() +
                "\nDescrição: " + categoria.getDescricao() +"\nPosso apagar esta categoria? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        setPasso(1);
    }
}

