package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.IGestorDeProjeto;
import br.ufg.inf.fabrica.pac.negocio.utils.Constantes;
import br.ufg.inf.fabrica.pac.negocio.utils.UtilsNegocio;
import br.ufg.inf.fabrica.pac.persistencia.IDaoProjeto;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoProjeto;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import java.sql.SQLException;
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
        String recursoId = "recursoId-criar";
        AutorizadorDeAcesso autorizador = 
                new AutorizadorDeAcesso(recursoId, autor, projeto);
        if(!autorizador.isAutorizado()){
            return UtilsNegocio.criarRespostaComErro(autorizador.getDetalhes());
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
            UtilsNegocio.criarRespostaComErro(Constantes.FALHA_NO_SISTEMA);
        }
        return UtilsNegocio.criarRespostaValida(projeto);
    }

    @Override
    public Resposta<List<Projeto>> buscarTodos(Usuario autor) {
        String recursoId = "recursoId-buscarTodos";
        AutorizadorDeAcesso autorizador = 
                new AutorizadorDeAcesso(recursoId, autor, null);
        if(!autorizador.isAutorizado()){
            return UtilsNegocio.criarRespostaComErro(autorizador.getDetalhes());
        }

        IDaoProjeto daoProjeto = new DaoProjeto();
        List<Projeto> todos;
        try {
            todos = daoProjeto.buscarTodos();
            return UtilsNegocio.criarRespostaValida(todos);
        } catch (Exception ex) {
            UtilsNegocio.registrarLog(getClass(), Level.SEVERE, ex);
            return UtilsNegocio.criarRespostaComErro(Constantes.FALHA_NO_SISTEMA);
        }
    }   
}