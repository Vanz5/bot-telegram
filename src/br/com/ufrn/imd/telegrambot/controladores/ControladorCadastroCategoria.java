package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Categoria;

import java.io.*;
import java.util.*;

public class ControladorCadastroCategoria extends Controlador {

    Categoria categoria;

    public ControladorCadastroCategoria() {
        super("/addcategoria", 6);
        categoria = new Categoria();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<String> mensagem = new ArrayList<String>();
        switch (getPassoAtual()){
            case 1:
                mensagem.add("Qual é o nome da categoria que vai ser cadastrada?");
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 2:
                categoria.setNome(mensagemRecebida);
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 3:
                mensagem.add("Escreva uma pequena descrição dessa categoria.");
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 4:
                categoria.setDescricao(mensagemRecebida);
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 5:
                mensagem.add(finalizarOperacao());
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 6:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Gerando codigo para categoria
                    // TODO - definir como o codigo deve ser gerado por função
                    categoria.setCodigo(gerarCodigo());
                    
                    //Armazenando em arquivo
                    BufferedWriter file = new BufferedWriter(new FileWriter("categoria.txt",true));
                    file.write(categoria.getCodigo() + "\n" + categoria.getNome() + "\n" + categoria.getDescricao() + "\n------");
                    file.newLine();
                    file.close();

                    //TODO - Deve ser informado o codigo gerado nesse momento?
                    mensagem.add("Categoria cadastrada com sucesso!");
                    setPassoAtual(getPassoAtual() + 1);
                }
                else if(mensagemRecebida.toLowerCase().equals("n")){
                    mensagem.add("Operação cancelada");
                    setPassoAtual(getPassoAtual() + 1);
                }
                else{
                    mensagem.add("Resposta inválida");
                }
                break;
            default:
                mensagem.add("Passo desconhecido");
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        String mensagem = "Confirme se os dados abaixo estão certos: \nNome: " + categoria.getNome() + "\nDescrição: " + categoria.getDescricao() +"\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        categoria = new Categoria();
        setPassoAtual(1);
    }

    //Verifica quantidade de linhas no arquivo para determinar o código
    //TODO - tratar erro ao tentar gerar arquivo pela primeira vez? - caso não consiga forçar primeira categoria com cod = 0
    //TODO - revisitar codigo parar gerar um numero não utilizado - tratar erros quando puder apagar categorias
    private int gerarCodigo() throws IOException {
        File arquivoCodigo = new File("categoria.txt");
        LineNumberReader linha = new LineNumberReader((new FileReader(arquivoCodigo)));
        linha.skip(arquivoCodigo.length());
        return linha.getLineNumber()/4;
    }
}
