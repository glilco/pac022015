package br.ufg.inf.fabrica.pac.dominio;

import br.ufg.inf.fabrica.pac.dominio.enums.Papel;

/**
 *
 * @author Danillo
 */
public class Membro {

    private long idUsuario;
    private long idProjeto;
    private String papel;

    //Transient
    private Usuario usuario;
    private Projeto projeto;
    private Papel papelProjeto;

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

    public Papel getPapelProjeto() {
        return papelProjeto;
    }

    public void setPapelProjeto(Papel papelProjeto) {
        this.papelProjeto = papelProjeto;
    }

}