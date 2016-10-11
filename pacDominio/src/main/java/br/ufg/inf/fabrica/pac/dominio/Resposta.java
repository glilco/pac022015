package br.ufg.inf.fabrica.pac.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T>
 */
public class Resposta <T> {
    private T chave;
    private final List<String> laudo;
    
    public Resposta(){
        this.chave = null;
        this.laudo = new ArrayList<>();
    }
    
    public T getChave() {
        return chave;
    }

    public void setChave(T chave) {
        this.chave = chave;
    }

    public List<String> getLaudo() {
        return laudo;
    }
    
    public void addItemLaudo(String item){
        this.laudo.add(item);
    }
    
    public void addItemLaudo(List laudo){
        this.laudo.addAll(laudo);
    }
    
    public boolean isSucesso(){
        return this.laudo.isEmpty();
    }
}