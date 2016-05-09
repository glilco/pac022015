package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.negocio.IAutenticador;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.utils.UtilsNegocio;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import br.ufg.inf.fabrica.pac.seguranca.ILdapAutenticador;
import br.ufg.inf.fabrica.pac.seguranca.excecoes.VariavelAmbienteNaoDefinidaException;
import br.ufg.inf.fabrica.pac.seguranca.imp.LdapAutenticador;
import br.ufg.inf.fabrica.pac.seguranca.utils.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auf
 */
public class AutenticadorPac implements IAutenticador {

    @Override
    public Resposta<Usuario> solicitarAutenticacao(Usuario credencial) {
        List<String> inconsistencias = validarUsuario(credencial);
        if (!inconsistencias.isEmpty()) {
            return UtilsNegocio.criarRespostaComErro(inconsistencias);
        }

        String pathSistema
                = System.getenv(Constantes.VARIAVEL_AMBIENTE_ARQUIVO_LDAP_PROPERTIES);

        if (pathSistema == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Variável de ambiente ").
                    append(Constantes.VARIAVEL_AMBIENTE_ARQUIVO_LDAP_PROPERTIES).
                    append(" não definida");
            UtilsNegocio.registrarLog(this.getClass(), Level.SEVERE,
                    "Variável de ambiente não definida");
            return UtilsNegocio.criarRespostaComErro(
                    Constantes.VARIAVEL_AMBIENTE_ARQUIVO_LDAP_PROPERTIES);
        }

        Properties prop;
        try {
            File file = new File(pathSistema);
            InputStream input;
            input = new FileInputStream(file);
            prop = new Properties();
            prop.load(input);
        } catch (IOException ex) {
            UtilsNegocio.registrarLog(this.getClass(), Level.SEVERE, ex);
            return UtilsNegocio.criarRespostaComErro("Falha no sistema");
        }

        ILdapAutenticador ldapAutenticador;
        try {
            ldapAutenticador = new LdapAutenticador(prop, credencial.getLogin(),
                    credencial.getSenha());
            if (!ldapAutenticador.isCredencialValida()) {
                return UtilsNegocio.criarRespostaComErro("Login ou senha inválido");
            }

            Usuario usuario = new Usuario();
            usuario.setLogin(credencial.getLogin());
            usuario.setSenha(credencial.getSenha());
            usuario.setId(ldapAutenticador.getId());
            usuario.setEmail(ldapAutenticador.getEmail());
            usuario.setNome(ldapAutenticador.getNome());

            IDaoUsuario daoUsuario = new DaoUsuario();
            Usuario usuarioPac = daoUsuario.buscar(usuario.getId());
            registrarUsuarioNoSistema(usuarioPac, usuario, daoUsuario);

            if (usuario.isAtivo()) {
                return UtilsNegocio.criarRespostaValida(usuario);
            } else {
                return UtilsNegocio.criarRespostaComErro(
                        "Usuário desativado pelo administrador do sistema");
            }
        } catch (Exception ex) {
            Logger.getLogger(AutenticadorPac.class.getName()).
                    log(Level.SEVERE, null, ex);
            return UtilsNegocio.criarRespostaComErro("Falha no sistema");
        }

    }

    private void registrarUsuarioNoSistema(Usuario usuarioPac, Usuario usuario, IDaoUsuario daoUsuario) throws SQLException {
        if (usuarioPac == null) {
            Transacao transacao = Transacao.getInstance();
            usuario.setAtivo(true);
            daoUsuario.salvar(usuario, transacao);
            transacao.confirmar();
        } else {
            usuario.setAtivo(usuarioPac.isAtivo());
        }
    }

    private List<String> validarUsuario(Usuario usuario) {
        List<String> inconsistencias = new ArrayList<>();
        if (usuario == null) {
            inconsistencias.add("Usuário de autenticação não informado");
        } else {
            inconsistencias = usuario.validar();
        }
        return inconsistencias;
    }
}
