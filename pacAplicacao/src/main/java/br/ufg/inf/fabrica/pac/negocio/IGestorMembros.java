package br.ufg.inf.fabrica.pac.negocio;

import br.ufg.inf.fabrica.pac.dominio.MembroProjeto;
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
    public Resposta<List<MembroProjeto>> buscarMembros(Usuario autor, Projeto projeto);
    
    /**
     * 
     * @param autor
     * @param membro
     * @return 
     */
    public Resposta<MembroProjeto> adicionarMembroProjeto(Usuario autor, MembroProjeto membro);
    
    /**
     * 
     * @param autor
     * @param membro
     * @return 
     */
    public Resposta<MembroProjeto> removerMembroProjeto(Usuario autor, MembroProjeto membro);

    /**
     * 
     * @param autor
     * @param membros 
     * @return  
     */
    public Resposta<List<MembroProjeto>> adicionarMembrosProjeto(Usuario autor, List<MembroProjeto> membros);

    /**
     * 
     * @param autor
     * @param papeisRemovidos
     * @param papeisAdicionados
     * @return 
     */
    public Resposta<String> atualizarPapeisDeUsuarioEmUmProjeto(Usuario autor, 
            List<MembroProjeto> papeisRemovidos, List<MembroProjeto> papeisAdicionados);
}
