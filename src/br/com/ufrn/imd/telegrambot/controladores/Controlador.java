package br.com.ufrn.imd.telegrambot.controladores;

import java.io.IOException;
import java.util.*;

public abstract class Controlador {
    private final String operacao;
    private int passoAtual;
    private int passosTotal;

    public Controlador(String operacao, int passosTotal) {
        this.operacao = operacao;
        this.passoAtual = 1;
        this.passosTotal = passosTotal;
    }

    public String getOperacao() {
        return operacao;
    }
    public int getPassoAtual() {
        return passoAtual;
    }
    public int getPassosTotal() {
        return passosTotal;
    }

    protected void setPassoAtual(int passoAtual) {
        this.passoAtual = passoAtual;
    }

    public abstract List<String> chat(String mensagemRecebida) throws IOException;

    protected abstract String finalizarOperacao();

    public abstract void reset();

}
