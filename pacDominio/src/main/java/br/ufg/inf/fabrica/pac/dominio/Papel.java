package br.ufg.inf.fabrica.pac.dominio;

/**
 *
 * @author Danillo
 */
public class Papel {

    private String nome;

    private String descricao;

    public Papel(){
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
