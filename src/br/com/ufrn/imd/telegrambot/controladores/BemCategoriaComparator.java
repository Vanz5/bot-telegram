package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;
import java.util.*;

public class BemCategoriaComparator implements Comparator<Bem> {

    @Override
    public int compare(Bem o1, Bem o2) {
        return o1.getCategoria().getNome().compareTo(o2.getCategoria().getNome());
    }
}
