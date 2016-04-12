package br.ufg.inf.fabrica.pac.dominio;

/**
 * Foi definido que: 1 - Toda entidade possui um único atributo identificador,
 * ficando vetado a identificação por composição de atributos. 2 - Toda
 * referência ao objeto associado deve ser realizada feita identificando o
 * atributo como: "id" + nome da entidade (capitular). 3 - Todo objeto no módulo
 * domínio que não deve ser persistido, deve ser comentado acima com a palavra
 * "Transient"
 */
import br.ufg.inf.fabrica.pac.dominio.enums.Estado;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author danilloguimaraes
 */
public class Pacote implements Validavel{

    private long id;
    private String nome;
    private String descricao;
    private Date dataCriacao;
    private boolean abandonado;
    private String documento;
    private Date dataPrevistaRealizacao;
    private String nomeEstado;
    private long idProjeto;
    private long idUsuario;

    //transient
    private Estado estado;
    private Usuario usuario;
    private Projeto projeto;
    private List<Long> andamentos;

    public Pacote() {
        andamentos = new ArrayList<>();
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isAbandonado() {
        return abandonado;
    }

    public void setAbandonado(boolean abandonado) {
        this.abandonado = abandonado;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getDataPrevistaRealizacao() {
        return dataPrevistaRealizacao;
    }

    public void setDataPrevistaRealizacao(Date dataPrevistaRealizacao) {
        this.dataPrevistaRealizacao = dataPrevistaRealizacao;
    }

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

    public List<Long> getAndamentos() {
        return andamentos;
    }

    public void setAndamentos(List<Long> andamentos) {
        this.andamentos = andamentos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        if(estado!=null)
            this.nomeEstado = estado.getNome();
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

    @Override
    public List<String> validar() {
        List<String> inconsistencias = new ArrayList<>();
        if(nome==null || nome.isEmpty())
            inconsistencias.add("Nome não informado");
        if(descricao==null || descricao.isEmpty())
            inconsistencias.add("Descrição não informada");
        if(dataCriacao==null)
            inconsistencias.add("Data de criação não informada");
        if(documento==null || documento.isEmpty())
            inconsistencias.add("Documento não informado");
        if(estado==null)
            inconsistencias.add("Estado não informado");
        if(projeto==null)
            inconsistencias.add("Projeto não informado");
        if(dataCriacao.after(dataPrevistaRealizacao)){
            inconsistencias.add("Data de criação maior que previsão de "
                    + "realização do pacote");
        }
        return inconsistencias;
    }

}
