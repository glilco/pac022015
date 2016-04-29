package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
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

    private final boolean autorizado;

    private final String detalhes;

    private AutorizadorDeAcesso() {
        this.autorizado = false;
        this.detalhes = null;
    }

    public AutorizadorDeAcesso(String recurso, Usuario autor) {
        String recursoId = "recursoIdCriarProjeto";
        if (autor == null) {
            this.autorizado = false;
            this.detalhes = "Autor da solicitação não informado";
            return;
        }

        List<String> nomesPapeis;
        try {
            nomesPapeis = buscarListaPapeis(autor);
        } catch (SQLException ex) {
            Logger.getLogger(GestorDeProjetos.class.getName()).
                    log(Level.SEVERE, null, ex);
            this.autorizado = false;
            this.detalhes = "Falha no sistema";
            return;
        }
        Seguranca seguranca = SegurancaStub.getInstance();
        if (!seguranca.autorizar(recursoId, nomesPapeis)) {
            String menssagemErro = 
                    "Usuário não possui permissão para acessar recurso";
            Logger.getLogger(GestorDePacotes.class.getName()).
                    log(Level.SEVERE, null, menssagemErro);
            this.autorizado = false;
            this.detalhes = menssagemErro;
        } else {
            this.autorizado = true;
            this.detalhes = "";
        }

    }

    public AutorizadorDeAcesso(String recurso, Usuario autor, 
            Projeto projeto) {
        String recursoId = "recursoIdCriarProjeto";
        if (autor == null) {
            this.autorizado = false;
            this.detalhes = "Autor da solicitação não informado";
            return;
        }

        List<String> nomesPapeis;
        try {
            nomesPapeis = buscarListaPapeis(autor);
        } catch (SQLException ex) {
            Logger.getLogger(GestorDeProjetos.class.getName()).
                    log(Level.SEVERE, null, ex);
            this.autorizado = false;
            this.detalhes = "Falha no sistema";
            return;
        }
        Seguranca seguranca = SegurancaStub.getInstance();
        if (!seguranca.autorizar(recursoId, nomesPapeis, 
                Long.toString(projeto.getId()))) {
            String menssagemErro = 
                    "Usuário não possui permissão para acessar recurso";
            Logger.getLogger(GestorDePacotes.class.getName()).
                    log(Level.SEVERE, null, menssagemErro);
            this.autorizado = false;
            this.detalhes = menssagemErro;
        } else {
            this.autorizado = true;
            this.detalhes = "";
        }

    }

    private List<String> buscarListaPapeis(Usuario autor) 
            throws SQLException {
        if (autor == null) {
            return null;
        }
        IDaoMembro daoMembro = new DaoMembro();
        List<Membro> papeis;
        List<String> nomesPapeis = new ArrayList<>();
        papeis = daoMembro.buscarPapeis(autor.getId());
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
}