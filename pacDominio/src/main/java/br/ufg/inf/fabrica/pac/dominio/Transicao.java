package br.ufg.inf.fabrica.pac.dominio;

/**
 *
 * @author Danillo
 */
public class Transicao {

    private String descricao;
    private String regra;
    private long idEstadoOrigem;
    private long idEstadoDestino;

    private Estado estadoOrigem;
    private Estado estadoDestino;

    public Transicao() {

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRegra() {
        return regra;
    }

    public void setRegra(String regra) {
        this.regra = regra;
    }

    public long getIdEstadoOrigem() {
        return idEstadoOrigem;
    }

    public void setIdEstadoOrigem(long idEstadoOrigem) {
        this.idEstadoOrigem = idEstadoOrigem;
    }

    public long getIdEstadoDestino() {
        return idEstadoDestino;
    }

    public void setIdEstadoDestino(long idEstadoDestino) {
        this.idEstadoDestino = idEstadoDestino;
    }

    public Estado getEstadoOrigem() {
        return estadoOrigem;
    }

    public void setEstadoOrigem(Estado estadoOrigem) {
        this.estadoOrigem = estadoOrigem;
    }

    public Estado getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(Estado estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

}
