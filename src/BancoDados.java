import java.util.*;

public class BancoDados {
    List <Localizacao> localizacoes= new ArrayList<>();
    List <Categoria> categorias = new ArrayList<>();
    List <Bem> bens = new ArrayList<>();

    public void addLocalizacao(Localizacao l) {
        localizacoes.add(l);
    }
    public void addCategoria(Categoria c) {
        categorias.add(c);
    }
    public void addBem(Bem b) {
        bens.add(b);
    }

    public List<Localizacao> getLocalizacoes() {
        return localizacoes;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Bem> getBens() {
        return bens;
    }


}
