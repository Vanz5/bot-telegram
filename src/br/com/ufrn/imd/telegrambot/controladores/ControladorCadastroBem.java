package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;

import java.io.*;
import java.util.*;

public class ControladorCadastroBem extends Controlador {

    Bem bem;

    public ControladorCadastroBem() {
        super("/addbem", 10);
        bem = new Bem();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<String> mensagem = new ArrayList<String>();
        switch (getPassoAtual()){
            case 1:
                mensagem.add("Qual é o nome do bem que vai ser cadastrado?");
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 2:
                bem.setNome(mensagemRecebida);
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 3:
                mensagem.add("Escreva uma pequena descrição desse bem.");
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 4:
                bem.setDescricao(mensagemRecebida);
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 5:
                mensagem.add("Qual é a localização desse bem?");
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 6:
                //TODO - fazer tratamento para receber o nome da categoria e localização
                //TODO - Tratamento fazendo teste se a categoria e localização existe
                bem.setLocalizacao(mensagemRecebida);
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 7:
                mensagem.add("Qual é a categoria desse bem?");
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 8:
                bem.setCategoria(mensagemRecebida);
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 9:
                mensagem.add(finalizarOperacao());
                setPassoAtual(getPassoAtual() + 1);
                break;
            case 10:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Gerando codigo para categoria
                    // TODO - definir como o codigo deve ser gerado por função
                    bem.setCodigo(gerarCodigo());

                    //Armazenando em arquivo
                    BufferedWriter file = new BufferedWriter(new FileWriter("bem.txt",true));
                    file.write(bem.getCodigo() + "\n" + bem.getNome() + "\n" + bem.getDescricao() + "\n" + bem.getLocalizacao().getNome() + "\n" + bem.getCategoria().getNome() + "\n------");
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
        String mensagem = "Confirme se os dados abaixo estão certos: \nNome: " + bem.getNome() + "\nDescrição: " + bem.getDescricao() +"\nLocalização: " + bem.getLocalizacao().getNome() + "\nCategoria: " + bem.getCategoria().getNome() + "\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        bem = new Bem();
        setPassoAtual(1);
    }

    //Verifica quantidade de linhas no arquivo para determinar o código
    //TODO - tratar erro ao tentar gerar arquivo pela primeira vez? - caso não consiga forçar primeira categoria com cod = 0
    //TODO - revisitar codigo parar gerar um numero não utilizado - tratar erros quando puder apagar categorias
    private int gerarCodigo() throws IOException {
        File arquivoCodigo = new File("bem.txt");
        LineNumberReader linha = new LineNumberReader((new FileReader(arquivoCodigo)));
        linha.skip(arquivoCodigo.length());
        return linha.getLineNumber()/6;
    }
}
