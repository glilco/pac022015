package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.negocio.AutenticacaoException;
import br.ufg.inf.fabrica.pac.negocio.IAutenticador;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.utils.UtilsNegocio;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import br.ufg.inf.fabrica.pac.seguranca.ILdapAutenticador;
import br.ufg.inf.fabrica.pac.seguranca.excecoes.VariavelAmbienteNaoDefinidaException;
import br.ufg.inf.fabrica.pac.seguranca.imp.LdapAutenticador;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author auf
 */
public class AutenticadorPac implements IAutenticador {

    @Override
    public Resposta<Usuario> solicitarAutenticacao(Usuario usuario) {

        if (usuario == null) {
            return UtilsNegocio.
                    criarRespostaComErro(
                            "Usuário de autenticação não informado");
        }

        List<String> inconsistencias = usuario.validar();
        if (!inconsistencias.isEmpty()) {
            return UtilsNegocio.criarRespostaComErro(inconsistencias);
        }

        ILdapAutenticador ldapAutenticador;
        try {
            ldapAutenticador = new LdapAutenticador(usuario.getLogin(),
                    usuario.getSenha());
        } catch (VariavelAmbienteNaoDefinidaException | IOException |
                NamingException ex) {
            Logger.getLogger(AutenticadorPac.class.getName()).
                    log(Level.SEVERE, null, ex);
            return UtilsNegocio.criarRespostaComErro("Falha no sistema");
        }

        if (!ldapAutenticador.isCredencialValida()) {
            return UtilsNegocio.criarRespostaComErro("Login ou senha inválido");
        } else {
            usuario.setId(ldapAutenticador.getId());
            usuario.setEmail(ldapAutenticador.getEmail());
            usuario.setNome(ldapAutenticador.getNome());
        }

        try {
            IDaoUsuario daoUsuario = new DaoUsuario();
            Usuario usuarioPac = daoUsuario.buscar(usuario.getId());
            if (usuarioPac == null) {
                Transacao transacao = Transacao.getInstance();
                usuario.setAtivo(true);
                daoUsuario.salvar(usuario, transacao);
                transacao.confirmar();
            } else {
                usuario = usuarioPac;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AutenticadorPac.class.getName()).
                    log(Level.SEVERE, null, "Falha no sistema");
            return UtilsNegocio.criarRespostaComErro(ex.getMessage());
        }

        if(usuario.isAtivo()){
            return UtilsNegocio.criarRespostaValida(usuario);
        } else {
            return UtilsNegocio.criarRespostaComErro(
                            "Usuário desativado pelo administrador do sistema");
        }
    }
}
