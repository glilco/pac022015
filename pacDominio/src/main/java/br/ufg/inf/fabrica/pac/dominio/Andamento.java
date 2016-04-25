package br.ufg.inf.fabrica.pac.dominio;

import br.ufg.inf.fabrica.pac.dominio.enums.Estado;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author danilloguimaraes
 */
public class Andamento implements Validavel {

    private long id;
    private Date dataModificacao;
    private Date dataPrevistaConclusao;
    private String descricao;

    private long idPacote;
    private String nomeEstado;
    private long idUsuarioRemetente;    // Usuario responsável pela ação que criou o andamento
    private long idUsuarioDestinatario; // Usuário que ficará responsável pelo pacote após a ação 

    //transient
    private Pacote pacote;
    private Estado estado;
    private Usuario usuarioRemetente;
    private Usuario usuarioDestinatario;

    public Andamento() {

    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        if (estado != null) {
            this.nomeEstado = estado.getNome();
        }
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public long getIdPacote() {
        return idPacote;
    }

    public void setIdPacote(long idPacote) {
        this.idPacote = idPacote;
    }

    public Date getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public Date getDataPrevistaConclusao() {
        return dataPrevistaConclusao;
    }

    public void setDataPrevistaConclusao(Date dataPrevistaConclusao) {
        this.dataPrevistaConclusao = dataPrevistaConclusao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public long getIdUsuarioRemetente() {
        return idUsuarioRemetente;
    }

    public void setIdUsuarioRemetente(long idUsuarioRemetente) {
        this.idUsuarioRemetente = idUsuarioRemetente;
    }

    public long getIdUsuarioDestinatario() {
        return idUsuarioDestinatario;
    }

    public void setIdUsuarioDestinatario(long idUsuarioDestinatario) {
        this.idUsuarioDestinatario = idUsuarioDestinatario;
    }

    public Usuario getUsuarioRemetente() {
        return usuarioRemetente;
    }

    public void setUsuarioRemetente(Usuario usuarioRemetente) {
        this.usuarioRemetente = usuarioRemetente;
        if (usuarioRemetente != null) {
            this.idUsuarioRemetente = usuarioRemetente.getId();
        }
    }

    public Usuario getUsuarioDestinatario() {
        return usuarioDestinatario;
    }

    public void setUsuarioDestinatario(Usuario usuarioDestinatario) {
        this.usuarioDestinatario = usuarioDestinatario;
        if(usuarioDestinatario!=null){
            this.idUsuarioDestinatario=usuarioDestinatario.getId();
        }
    }

    @Override
    public List<String> validar() {
        List<String> inconsistencias = new ArrayList<>();
        if (nomeEstado == null) {
            inconsistencias.add("Estado não informado");
        }
        if (pacote == null) {
            inconsistencias.add("Pacote não informado");
        }
        if (usuarioRemetente == null) {
            inconsistencias.add("Autor não informado");
        }
        return inconsistencias;
    }

}
