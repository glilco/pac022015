package br.ufg.inf.fabrica.pac.dominio;

import java.util.Date;

/**
 *
 * @author danilloguimaraes
 */
public class Andamento {
    private long id;
    private Date dataModificacao;
    private Date dataPrevistaConclusao;
    private String descricao;
    
    private long idPacote;
    private long idEstado;
    private long idUsuarioRemetente;    // Usuario responsável pela ação que criou o andamento
    private long idUsuarioDestinatario; // Usuário que ficará responsável pelo pacote após a ação 

    //transient
    private Pacote pacote;
    private Estado estado;
    private Usuario usuarioRemetente;
    private Usuario usuarioDestinatario;

    public Andamento(){
        
    }
    
    public Andamento(Date dataModificacao, Date dataPrevistaConclusao, String descricao, long idPacote, long idEstado, long idUsuarioRemetente, long idUsuarioDestinatario) {
        this.dataModificacao = dataModificacao;
        this.dataPrevistaConclusao = dataPrevistaConclusao;
        this.descricao = descricao;
        this.idPacote = idPacote;
        this.idEstado = idEstado;
        this.idUsuarioRemetente = idUsuarioRemetente;
        this.idUsuarioDestinatario = idUsuarioDestinatario;
     }
    
    

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

        
    public long getIdPacote() {
        return idPacote;
    }

    public void setIdPacote(long idPacote) {
        this.idPacote = idPacote;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
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
    
    public void setId(long id){
        this.id = id;
    }
    
    public long getId(){
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
    }

    public Usuario getUsuarioDestinatario() {
        return usuarioDestinatario;
    }

    public void setUsuarioDestinatario(Usuario usuarioDestinatario) {
        this.usuarioDestinatario = usuarioDestinatario;
    }

        public static void main(String[] args) {
        
        System.out.println(PapelProjeto.GPR.name());
        
        }
    
}
