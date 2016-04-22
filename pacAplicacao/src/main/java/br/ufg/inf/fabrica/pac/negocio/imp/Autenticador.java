package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.negocio.AutenticacaoException;
import br.ufg.inf.fabrica.pac.negocio.IAutenticador;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import br.ufg.inf.fabrica.pac.seguranca.ILdapAutenticador;
import br.ufg.inf.fabrica.pac.seguranca.imp.LdapAutenticador;
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
        validarUsuarioValidoInformado(usuario);
        Usuario usuarioLDAP = new LdapAutenticador().autenticar(usuario);

        if (usuarioLDAP == null) {
            return null;
        }

        Usuario usuarioPac = buscarUsuarioPac(usuarioLDAP.getId());
        if (usuarioPac == null) {
            cadastrarNovoUsuarioPac(usuarioLDAP);
            usuarioPac = usuarioLDAP;
        }
        
        if (!usuarioPac.isAtivo()) {
            return null;
        }
        return usuarioPac;
    }

    private void validarUsuarioValidoInformado(Usuario usuario) throws AutenticacaoException {
        if (usuario == null) {
            throw new AutenticacaoException("Informe um usuário para solicitar autenticação");
        }
        if (Utils.stringVaziaOuNula(usuario.getLogin()) || Utils.stringVaziaOuNula(usuario.getSenha())) {
            throw new AutenticacaoException("Informe um usuário e uma senha para solicitar autenticação");
        }
    }

    private Usuario buscarUsuarioPac(long idUsuario) throws AutenticacaoException {
        return new DaoUsuario().buscar(idUsuario);
    }

    private void cadastrarNovoUsuarioPac(Usuario u) {
        try {
            Transacao transacao = Transacao.getInstance();
            new DaoUsuario().salvar(u, transacao);
            transacao.confirmar();
            u.setAtivo(true);
        } catch (SQLException ex) {
            Logger.getLogger(Autenticador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}