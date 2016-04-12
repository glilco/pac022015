package br.ufg.inf.fabrica.pac.view.apoio;

import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import java.util.List;

/**
 *
 * @author Danillo
 */
public class UsuarioView extends Usuario{
    private Projeto projetoSelecionado;
    private String papeis;
    private List<Membro> membros;
    
    public UsuarioView(Usuario usuario, List<Membro> membros){
        this.setId(usuario.getId());
        this.setAtivo(usuario.isAtivo());
        this.setEmail(usuario.getEmail());
        this.setLogin(usuario.getLogin());
        this.setNome(usuario.getNome());
        this.setSenha(usuario.getSenha());
        this.papeis = "";
        for (Membro membro : membros) {
            if(papeis.length()>0)
                papeis+=",";
            papeis+=membro.getPapel();
        }
        this.membros = membros;
    }

    public Projeto getProjetoSelecionado() {
        return projetoSelecionado;
    }

    public void setProjetoSelecionado(Projeto projetoSelecionado) {
        this.projetoSelecionado = projetoSelecionado;
    }

    public String getPapeis() {
        return papeis;
    }

    public void setPapeis(String papeis) {
        this.papeis = papeis;
    }

    public List<Membro> getMembros() {
        return membros;
    }

    public void setMembros(List<Membro> membros) {
        this.membros = membros;
    }

    public boolean isGPP(){
        return this.papeis.contains("GPP");
    }
    public boolean isGPR(){
        return this.papeis.contains("GPR");
    }
    public boolean isMEG(){
        return this.papeis.contains("MEG");
    }
    public boolean isMEM(){
        return this.papeis.contains("MEM");
    }
}
