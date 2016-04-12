package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.negocio.AutenticacaoException;
import br.ufg.inf.fabrica.pac.negocio.IAutenticador;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import br.ufg.inf.fabrica.pac.seguranca.ILdapAutenticador;
import br.ufg.inf.fabrica.pac.seguranca.imp.LdapAutenticadorStub;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auf
 */
public class Autenticador implements IAutenticador {

    @Override
    public Usuario solicitarAutenticacao(Usuario usuario) throws AutenticacaoException {
        if (usuario == null) {
            return null;
        }
        if (Utils.stringVaziaOuNula(usuario.getLogin()) || Utils.stringVaziaOuNula(usuario.getSenha())) {
            throw new AutenticacaoException("Informe um usuário e uma senha para solicitar autenticação");
        }
        //Busca usuário no ldap
        ILdapAutenticador ldapAutenticador = new LdapAutenticadorStub();
        Usuario u = ldapAutenticador.autenticar(usuario);

        if (u == null) {
            return null;
        }

        Usuario usuarioBanco = new Usuario();
        //Verifica na persistencia se o usuário esta ativo
        IDaoUsuario daoUsuario = new DaoUsuario();
        try {
            usuarioBanco = daoUsuario.buscar(u.getId());
        } catch (SQLException ex) {
            Logger.getLogger(Autenticador.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (usuarioBanco != null) {
            u.setAtivo(usuarioBanco.isAtivo());
        }

        if (usuarioBanco == null) {
            try {
                Transacao transacao = Transacao.getInstance();
                daoUsuario.salvar(u, transacao);
                transacao.confirmar();
                u.setAtivo(true);
            } catch (SQLException ex) {
                Logger.getLogger(Autenticador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!u.isAtivo()) {
            return null;
        }
        return u;
    }
}