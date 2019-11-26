package br.com.ufrn.imd.telegrambot.controladores;

import java.io.IOException;
import java.util.*;

public abstract class Controlador {
    private final String operacao; //String utilizada para identificar qual operação vai ser executada
    private int passo; //Variavel responsável por determinar passo a ser executado dentro do switch do controlador
    private int passosTotal; //variavel que recebe a quantidade de passos existente no controlador

    public Controlador(String operacao, int passosTotal) {
        this.operacao = operacao;
        this.passo = 1;
        this.passosTotal = passosTotal;
    }

    public String getOperacao() {
        return operacao;
    }

    public int getPasso() {
        return passo;
    }
    protected void setPasso(int passo) {
        this.passo = passo;
    }
    protected void incrementarPasso() { setPasso(getPasso() + 1); }

    public int getPassosTotal() {
        return passosTotal;
    }

    public abstract List<String> chat(String mensagemRecebida) throws IOException; //Função responsavél por executar a operação
    protected abstract String finalizarOperacao(); //Função utilizada para confirmar operação quando necessario
    public abstract void reset(); //Função utilizada para retonar o controlador ao estado inicial

}
