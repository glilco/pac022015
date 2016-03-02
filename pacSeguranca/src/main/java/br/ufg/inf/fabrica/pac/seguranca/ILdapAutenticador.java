package br.ufg.inf.fabrica.pac.seguranca;

import br.ufg.inf.fabrica.pac.dominio.Usuario;


/**
 *
 * @author danilloguimaraes
 */
public interface ILdapAutenticador {

    /**
     * 
     * @param usuario 
     */
    public Usuario autenticar(Usuario usuario);
}
