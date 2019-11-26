package br.com.ufrn.imd.telegrambot.controladores;

import java.io.IOException;
import java.util.*;

public abstract class Controlador {
    // String utilizada para identificar qual operação vai ser executada.
    private final String operacao;
    /* Variavel responsável por determinar passo a ser executado 
    dentro do switch do controlador. */ 
    private int passo;
    // Variavel que recebe a quantidade de passos existente no controlador. 
    private int passosTotal; 
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

    // Função responsavél por executar a operação.
    public abstract List<String> chat(String mensagemRecebida) throws IOException;
    // Função utilizada para confirmar operação quando necessario. 
    protected abstract String finalizarOperacao();
    //Função utilizada para retonar o controlador ao estado inicial. 
    public abstract void reset(); 
}
