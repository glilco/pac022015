package br.ufg.inf.fabrica.pac.dominio;

/**
 *
 * @author Danillo
 */
public class MembroProjeto {

    private long idUsuario;
    private long idProjeto;
    private String papel;

    //Transient
    private Usuario usuario;
    private Projeto projeto;
    private PapelProjeto papel_entity;

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

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public PapelProjeto getPapel_entity() {
        return papel_entity;
    }

    public void setPapel_entity(PapelProjeto papel_entity) {
        this.papel_entity = papel_entity;
    }

}