package br.ufg.inf.fabrica.pac.view.beans;


import br.ufg.inf.fabrica.pac.negocio.IGestorMembros;
import br.ufg.inf.fabrica.pac.dominio.MembroProjeto;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.imp.GestorMembrosImpl;
import br.ufg.inf.fabrica.pac.view.apoio.UsuarioView;
import java.util.ArrayList;
import java.util.List;

public class BeanAtribuirEquipe {

    private IGestorMembros gestor;
    
    private String usuarioPesquisado = "";
    
    private int usuarioEmAlteracao = 0;
    
    private Usuario usuarioLogado;
    
    private Projeto projetoSelecionado;
    
    List<UsuarioView> usuarios;

    public String getUsuarioPesquisado() {
        return usuarioPesquisado;
    }

    public void setUsuarioPesquisado(String usuarioPesquisado) {
        this.usuarioPesquisado = usuarioPesquisado;
    }

    public IGestorMembros getGestor() {
        return gestor;
    }

    public void setGestor(IGestorMembros gestor) {
        this.gestor = gestor;
    }

    public Usuario getUsuarioLogado() {
        return this.usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Projeto getProjetoSelecionado() {
        return this.projetoSelecionado;
    }

    public void setProjetoSelecionado(Projeto projetoSelecionado) {
        this.projetoSelecionado = projetoSelecionado;
    }
    
    public BeanAtribuirEquipe(){
        this.gestor = new GestorMembrosImpl();
        Usuario usuario = new Usuario();
        usuario.setId(1);
        this.usuarioLogado = usuario;
        Projeto projeto = new Projeto();
        projeto.setId(1);
        this.projetoSelecionado = projeto;
    }
    
    private List<MembroProjeto> getMembros(){
        List<MembroProjeto> membros = new ArrayList();
        Resposta resposta;
        resposta = gestor.buscarMembros(usuarioLogado, projetoSelecionado);
        if(resposta.isSucesso()){
            membros = (List<MembroProjeto>) resposta.getChave();
        }
        
        
        
        return membros;
    }
    
    public List<UsuarioView> getUsuariosMembros(){
        List<MembroProjeto> membros = getMembros();
        usuarios = new ArrayList<>();
        List<MembroProjeto> papeis = new ArrayList<>();
        Usuario usuarioAtual = null;
        
        for (MembroProjeto membro : membros) {
            if(usuarioAtual==null)
                usuarioAtual = membro.getUsuario();
            if(membro.getIdUsuario()==usuarioAtual.getId()){
                papeis.add(membro);
            } else {
                usuarios.add(new UsuarioView(usuarioAtual, papeis));
                usuarioAtual = membro.getUsuario();
                papeis = new ArrayList<>();
                papeis.add(membro);
            }
        }
        if(!membros.isEmpty()){
            usuarios.add(new UsuarioView(usuarioAtual, papeis));
        }
        return usuarios;
    }
    
    public List<Usuario> getNaoMembros(){
        List<Usuario> naoMembros = new ArrayList<>();
        Resposta resposta;
        resposta = gestor.buscarUsuariosNaoMembros(usuarioLogado, projetoSelecionado, usuarioPesquisado);
        setUsuarioPesquisado("");
        if(resposta.isSucesso()){
            naoMembros = (List<Usuario>) resposta.getChave();
        }
        return naoMembros;
    } 

    public int getUsuarioEmAlteracao() {
        return usuarioEmAlteracao;
    }

    public void setUsuarioEmAlteracao(int usuarioEmAlteracao) {
        this.usuarioEmAlteracao = usuarioEmAlteracao;
    }

    public List<UsuarioView> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioView> usuarios) {
        this.usuarios = usuarios;
    }

}