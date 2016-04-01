package br.ufg.inf.fabrica.pac.negocio.imp.stubs;



import br.ufg.inf.fabrica.pac.negocio.AutenticacaoException;
import br.ufg.inf.fabrica.pac.negocio.IAutenticador;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.stub.DaoUsuarioStub;
import br.ufg.inf.fabrica.pac.seguranca.ILdapAutenticador;
import br.ufg.inf.fabrica.pac.seguranca.imp.LdapAutenticadorStub;

/**
 *
 * @author Danillo
 */
public class AutenticadorStub implements IAutenticador{

    @Override
    public Usuario solicitarAutenticacao(Usuario usuario) 
            throws AutenticacaoException {
        if(usuario==null)
            return null;
        if(stringVaziaOuNula(usuario.getLogin()) || stringVaziaOuNula(usuario.getSenha())){
            throw new AutenticacaoException("Informe um usuário e uma senha para solicitar autenticação");
        }
        //Busca usuário no ldap
        ILdapAutenticador ldapAutenticador = new LdapAutenticadorStub();
        Usuario u = ldapAutenticador.autenticar(usuario);
        if(u==null){
            return null;
        }
        
        //Verifica na persistencia se o usuário esta ativo
        IDaoUsuario daoUsuario = new DaoUsuarioStub();
        u = daoUsuario.buscar(u.getId());
        if(u==null){
            daoUsuario.salvar(u);
        }
        if(!u.isAtivo()){
            return null;
        }
        return u;
    }
    
    private boolean stringVaziaOuNula(String value){
        return value==null || value.isEmpty();
    }

    
}
