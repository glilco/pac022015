package br.ufg.inf.fabrica.pac.persistencia.stub;

import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;

/**
 *
 * @author Danillo
 */
public class DaoUsuarioStub implements IDaoUsuario{

    public Usuario buscar(Usuario usuario) {
        if(!usuario.getEmail().isEmpty()){
            usuario.setAtivo(true);
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        usuario.setAtivo(true);
        return usuario;
    }

    @Override
    public Usuario excluir(Usuario entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
