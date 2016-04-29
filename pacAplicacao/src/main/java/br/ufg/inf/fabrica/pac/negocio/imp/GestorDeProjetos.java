package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.IGestorDeProjeto;
import br.ufg.inf.fabrica.pac.negocio.utils.UtilsNegocio;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.IDaoProjeto;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoProjeto;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import br.ufg.inf.fabrica.pac.seguranca.Seguranca;
import br.ufg.inf.fabrica.pac.seguranca.imp.SegurancaStub;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorDeProjetos implements IGestorDeProjeto {

    private static IGestorDeProjeto gestor;

    private GestorDeProjetos() {

    }

    public static IGestorDeProjeto getInstance() {
        if (gestor == null) {
            gestor = new GestorDeProjetos();
        }
        return gestor;
    }

    @Override
    public Resposta<Projeto> criar(Usuario autor, Projeto projeto) {
        Resposta resposta = autorizarAcesso(autor);
        if(resposta!=null){
            return resposta;
        }

        List<String> inconsistencias = projeto.validar();
        if (!inconsistencias.isEmpty()) {
            return UtilsNegocio.criarRespostaComErro(inconsistencias);
        }

        try {
            Transacao transacao = Transacao.getInstance();
            DaoProjeto daoProjeto = new DaoProjeto();
            daoProjeto.salvar(projeto, transacao);
            transacao.confirmar();
        } catch (SQLException ex) {
            Logger.getLogger(GestorDeProjetos.class.getName()).log(Level.SEVERE,
                    null, ex);
            UtilsNegocio.criarRespostaComErro("Falha na transação");
        }
        return UtilsNegocio.criarRespostaValida(projeto);
    }

    @Override
    public Resposta<List<Projeto>> buscarTodos(Usuario autor) {
        Resposta resposta = autorizarAcesso(autor);
        if(resposta!=null){
            return resposta;
        }

        IDaoProjeto daoProjeto = new DaoProjeto();
        List<Projeto> todos;
        try {
            todos = daoProjeto.buscarTodos();
            return UtilsNegocio.criarRespostaValida(todos);
        } catch (Exception ex) {
            return UtilsNegocio.criarRespostaComErro("Falha no sistema");
        }
    }

    private List<String> buscarListaPapeis(Usuario autor) throws SQLException {
        if(autor==null)
            return null;
        IDaoMembro daoMembro = new DaoMembro();
        List<Membro> papeis;
        List<String> nomesPapeis = new ArrayList<>();
        papeis = daoMembro.buscarPapeis(autor.getId());
        for (Membro papel : papeis) {
            nomesPapeis.add(papel.getPapel());
        }
        return nomesPapeis;
    }
    
    private Resposta<Projeto> autorizarAcesso(Usuario autor){
        if(autor==null){
            return UtilsNegocio.criarRespostaComErro("Autor da solicitação não informado");
        }
        String recursoId = "recursoIdCriarProjeto";
        List<String> nomesPapeis;
        try {
            nomesPapeis = buscarListaPapeis(autor);
        } catch (SQLException ex) {
            Logger.getLogger(GestorDeProjetos.class.getName()).
                    log(Level.SEVERE, null, ex);
            return UtilsNegocio.criarRespostaComErro("Falha no sistema");
        }

        Seguranca seguranca = SegurancaStub.getInstance();
        if (!seguranca.autorizar(recursoId, nomesPapeis)) {
            String menssagemErro = "Usuário não possui permissão para acessar recurso";
            Logger.getLogger(GestorDePacotes.class.getName()).log(Level.SEVERE, null, menssagemErro);
            return UtilsNegocio.criarRespostaComErro(menssagemErro);
        }
        return null;
    }
}
