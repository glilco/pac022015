package br.ufg.inf.fabrica.pac.negocio;

import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import java.util.List;

/**
 *
 * @author Danillo
 */
public interface IGestorMembros {
    
    /**
     *
     * @param autor
     * @param projeto
     * @param usuarioPesquisado
     * @return
     */
    public Resposta<List<Usuario>> buscarUsuariosNaoMembros(Usuario autor, Projeto projeto, String usuarioPesquisado);
    
    /**
     * 
     * @param autor
     * @param projeto
     * @return 
     */
    public Resposta<List<Membro>> buscarMembros(Usuario autor, Projeto projeto);
    
    /**
     * 
     * @param autor
     * @param membro
     * @return 
     */
    public Resposta<Membro> adicionarMembroProjeto(Usuario autor, Membro membro);
    
    /**
     * 
     * @param autor
     * @param membro
     * @return 
     */
    public Resposta<Membro> removerMembroProjeto(Usuario autor, Membro membro);

    /**
     * 
     * @param autor
     * @param membros 
     * @return  
     */
    public Resposta<List<Membro>> adicionarMembrosProjeto(Usuario autor, List<Membro> membros);

    /**
     * 
     * @param autor
     * @param papeisRemovidos
     * @param papeisAdicionados
     * @return 
     */
    public Resposta<String> atualizarPapeisDeUsuarioEmUmProjeto(Usuario autor, 
            List<Membro> papeisRemovidos, List<Membro> papeisAdicionados);
}
