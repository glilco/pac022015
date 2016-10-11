package br.ufg.inf.fabrica.pac.dominio;

/**
 *
 * @author Danillo
 */
public class Estado {

    private long id;
    private String nome;
    private boolean estadoFinal;
    private String descricao;
    private boolean permiteDelegacao;

    public Estado() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(boolean estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPermiteDelegacao() {
        return permiteDelegacao;
    }

    public void setPermiteDelegacao(boolean permiteDelegacao) {
        this.permiteDelegacao = permiteDelegacao;
    }
    
}
