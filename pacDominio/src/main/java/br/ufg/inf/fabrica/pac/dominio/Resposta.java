package br.ufg.inf.fabrica.pac.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <Chave>
 */
public class Resposta <Chave> {
    private Chave chave;
    private final List<String> laudo;
    
    public Chave getChave() {
        return chave;
    }

    public void setChave(Chave chave) {
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
    
    public Resposta(){
        this.chave = null;
        this.laudo = new ArrayList<>();
    }
}