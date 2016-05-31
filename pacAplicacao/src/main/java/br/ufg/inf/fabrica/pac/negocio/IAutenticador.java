package br.ufg.inf.fabrica.pac.negocio;

import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;

/**
 *
 * @author Danillo 
 */
@FunctionalInterface
public interface IAutenticador {
    
    /**
     * Solicita autenticação apartir de um usuário informado, e em caso de sucesso,
     * retorna usuário com nome e email. Em caso de inconsistência de usuário e senha a função retorna null.
     * Interface para realizar autenticação
     * @param usuario 
     * @return  
     */
    public Resposta<Usuario> solicitarAutenticacao(Usuario usuario);
}
