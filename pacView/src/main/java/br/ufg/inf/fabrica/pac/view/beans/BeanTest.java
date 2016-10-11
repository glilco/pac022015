package br.ufg.inf.fabrica.pac.view.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Danillo
 */
@Named(value = "beanTest")
@SessionScoped
public class BeanTest implements Serializable {

    private String nome;
    /**
     * Creates a new instance of BeanTest
     */
    public BeanTest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String processar(){
        return "resposta";
    }
}
