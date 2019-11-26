package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Categoria;

import java.io.*;
import java.util.*;

public class ControladorCadastroCategoria extends Controlador {

    FuncoesAuxiliares aux = new FuncoesAuxiliares();
    Categoria categoria;

    public ControladorCadastroCategoria() {
        super("/addcategoria", 8);
        categoria = new Categoria();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Categoria> categorias = aux.listaCategorias(); //Lista com as categorias listadas em 'categoria.txt'
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("Qual é o código da categoria a ser cadastrada?");
                incrementarPasso();
                break;
            case 2:
                String codigo = (mensagemRecebida);
                Categoria encontrada = aux.buscarCategoriaCodigo(categorias,codigo);
                if(encontrada == null){ //Tratamento para checar se o código já esta sendo utilizado
                    categoria.setCodigo(codigo);
                    incrementarPasso();
                    mensagem = chat(mensagemRecebida);
                    break;
                }
                else{
                    mensagem.add("O código informado já está sendo utilizado para a categoria: \n"+ encontrada.toString()+"\n Informe um código diferente.");
                }
            case 3:
                mensagem.add("Qual é o nome da categoria a ser cadastrada?");
                incrementarPasso();
                break;
            case 4:
                categoria.setNome(mensagemRecebida);
                incrementarPasso();
                mensagem = chat(mensagemRecebida);
                break;
            case 5:
                mensagem.add("Escreva uma pequena descrição dessa categoria.");
                incrementarPasso();
                break;
            case 6:
                categoria.setDescricao(mensagemRecebida);
                incrementarPasso();
                mensagem = chat(mensagemRecebida);
                break;
            case 7:
                mensagem.add(finalizarOperacao());
                incrementarPasso();
                break;
            case 8:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Armazenando em arquivo
                    BufferedWriter file = new BufferedWriter(new FileWriter("categoria.txt",true));
                    file.write(categoria.getCodigo() + "\n" + categoria.getNome() + "\n" + categoria.getDescricao() + "\n------");
                    file.newLine();
                    file.close();

                    mensagem.add("Categoria cadastrada com sucesso!");
                    incrementarPasso();
                    break;
                }
                else if(mensagemRecebida.toLowerCase().equals("n")){
                    mensagem.add("Operação cancelada.");
                    incrementarPasso();
                    break;
                }
                else{
                    mensagem.add("Resposta inválida.");
                }
            default:
                mensagem.add("Passo desconhecido, saindo da operação.");
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        String mensagem = "Confirme se os dados abaixo estão corretos: \nCódigo: "+ categoria.getCodigo() +"\nNome: " + categoria.getNome() +
                "\nDescrição: " + categoria.getDescricao() +"\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        categoria = new Categoria();
        setPasso(1);
    }
}
