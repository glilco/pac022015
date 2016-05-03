package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoMembro;
import br.ufg.inf.fabrica.pac.seguranca.Seguranca;
import br.ufg.inf.fabrica.pac.seguranca.imp.SegurancaStub;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danillo
 */
public class AutorizadorDeAcesso {

    private boolean autorizado;

    private String detalhes;

    private AutorizadorDeAcesso() {
        this.autorizado = false;
        this.detalhes = null;
    }

    public AutorizadorDeAcesso(String recurso, Usuario autor, Projeto projeto) {

        if (Utils.stringVaziaOuNula(recurso)) {
            rejeitar("Recurso da solicitação não informado");
        } else if (autor == null) {
            rejeitar("Autor da solicitação não informado");
        } else if (autor.getId() < 1) {
            rejeitar("Usuário inválido");
        } else if (projeto != null && projeto.getId() < 1) {
            rejeitar("Projeto inválido");
            return;
        }

        List<String> nomesPapeis;
        try {
            if (projeto == null) {
                nomesPapeis = buscarListaPapeis(autor, null);
            } else {
                nomesPapeis = buscarListaPapeis(autor, projeto);
            }
            Seguranca seguranca = SegurancaStub.getInstance();
            if (!seguranca.autorizar(recurso, nomesPapeis)) {
                String menssagemErro
                        = "Usuário não possui permissão para acessar recurso";
                Logger.getLogger(GestorDePacotes.class.getName()).
                        log(Level.SEVERE, null, menssagemErro);
                rejeitar(menssagemErro);
            } else {
                autorizar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestorDeProjetos.class.getName()).
                    log(Level.SEVERE, null, ex);
            rejeitar("Falha no sistema");
        }

    }

    private List<String> buscarListaPapeis(Usuario autor, Projeto projeto)
            throws SQLException {
        if (autor == null) {
            return new ArrayList<>();
        }
        IDaoMembro daoMembro = new DaoMembro();
        List<Membro> papeis;
        List<String> nomesPapeis = new ArrayList<>();
        if (projeto == null) {
            papeis = daoMembro.buscarPapeis(autor.getId());
        } else {
            papeis = daoMembro.buscar(projeto, autor);
        }
        for (Membro papel : papeis) {
            nomesPapeis.add(papel.getPapel());
        }
        return nomesPapeis;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public String getDetalhes() {
        return detalhes;
    }

    private void rejeitar(String detalhes) {
        this.autorizado = false;
        this.detalhes = detalhes;
    }

    private void autorizar() {
        this.autorizado = true;
        this.detalhes = "";
    }
}
