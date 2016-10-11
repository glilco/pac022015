package br.ufg.inf.fabrica.pac.dominio;

/**
 *
 * @author Danillo
 */
public class Membro {

    private long idUsuario;
    private long idProjeto;
    private long idPapel;

    //Transient
    private Usuario usuario;
    private Projeto projeto;
    private Papel papel;

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public long getIdPapel() {
        return idPapel;
    }

    public void setIdPapel(long idPapel) {
        this.idPapel = idPapel;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }
}