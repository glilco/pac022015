package br.ufg.inf.fabrica.pac.negocio.imp.stubs;



import br.ufg.inf.fabrica.pac.negocio.AutenticacaoException;
import br.ufg.inf.fabrica.pac.negocio.IAutenticador;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.stub.DaoUsuarioStub;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import br.ufg.inf.fabrica.pac.seguranca.ILdapAutenticador;
import br.ufg.inf.fabrica.pac.seguranca.imp.LdapAutenticadorStub;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            try {
                Transacao transacao = Transacao.getInstance();
                daoUsuario.salvar(u, transacao);
                transacao.confirmar();
            } catch (SQLException ex) {
                Logger.getLogger(AutenticadorStub.class.getName()).log(Level.SEVERE, null, ex);
            }
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
