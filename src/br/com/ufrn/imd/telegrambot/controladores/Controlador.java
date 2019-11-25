package br.com.ufrn.imd.telegrambot.controladores;

import java.io.IOException;
import java.util.*;

public abstract class Controlador {
    private final String operacao;
    private int passo;
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
    public int getPassosTotal() {
        return passosTotal;
    }

    protected void setPasso(int passo) {
        this.passo = passo;
    }

    protected void incrementarPasso() { setPasso(getPasso() + 1); }

    public abstract List<String> chat(String mensagemRecebida) throws IOException;

    protected abstract String finalizarOperacao();

    public abstract void reset();

}
