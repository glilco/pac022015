package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.dominio.Andamento;
import br.ufg.inf.fabrica.pac.negocio.IGestorDePacotes;
import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.dominio.enums.Estado;
import br.ufg.inf.fabrica.pac.negocio.utils.UtilsNegocio;
import br.ufg.inf.fabrica.pac.persistencia.IDaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.IDaoPacote;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoPacote;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.Pesquisa;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroNumerico;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroTexto;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auf
 */
public class GestorDePacotes implements IGestorDePacotes {

    private static GestorDePacotes gestor;

    private GestorDePacotes() {

    }

    public static GestorDePacotes getInstance() {
        if (gestor == null) {
            gestor = new GestorDePacotes();
        }
        return gestor;
    }

    @Override
    public Resposta<Pacote> criar(Usuario autor, Pacote pacote, 
            Projeto projetoSelecionado) {
        if (pacote == null) {
            return UtilsNegocio.criarRespostaComErro("Pacote não informado");
        }
        if (autor == null || autor.getId() < 1) {
            return UtilsNegocio.criarRespostaComErro("Usuário não informado");
        }
        if (projetoSelecionado == null
                || projetoSelecionado.getId() < 1) {
            return UtilsNegocio.criarRespostaComErro("Projeto não informado");
        }

        String recursoId = "recursoId-criar";
        AutorizadorDeAcesso autorizador = 
                new AutorizadorDeAcesso(recursoId, autor, projetoSelecionado);
        if(!autorizador.isAutorizado()){
            return UtilsNegocio.criarRespostaComErro(autorizador.getDetalhes());
        }

        pacote.setAbandonado(false);
        pacote.setDataCriacao(UtilsNegocio.buscarDataAtual());
        pacote.setEstado(Estado.NOVO);
        pacote.setProjeto(projetoSelecionado);
        pacote.setIdProjeto(projetoSelecionado.getId());

        List<String> inconsistencias
                = pacote.validar();
        if (!inconsistencias.isEmpty()) {
            String menssagemErro = "Pacote inconsistente";
            Logger.getLogger(GestorDePacotes.class.getName()).
                    log(Level.SEVERE, null, menssagemErro);
            return UtilsNegocio.
                    criarRespostaComErro(inconsistencias);
        }

        Andamento andamento = new Andamento();
        andamento.setDataModificacao(pacote.getDataCriacao());
        andamento.setDataPrevistaConclusao(pacote.getDataPrevistaRealizacao());
        andamento.setDescricao("Criação do pacote");
        andamento.setEstado(pacote.getEstado());
        andamento.setPacote(pacote);
        andamento.setUsuarioDestinatario(null);
        andamento.setUsuarioRemetente(autor);
        inconsistencias = andamento.validar();
        if (!inconsistencias.isEmpty()) {
            String menssagemErro = "Pacote inconsistente";
            Logger.getLogger(GestorDePacotes.class.getName()).
                    log(Level.SEVERE, null, menssagemErro);
            return UtilsNegocio.
                    criarRespostaComErro(inconsistencias);
        }

        IDaoAndamento daoAndamento = new DaoAndamento();
        IDaoPacote daoPacote = new DaoPacote();

        Transacao transacao = null;
        try {
            transacao = Transacao.getInstance();
            daoPacote.salvar(pacote, transacao);
            andamento.setIdPacote(pacote.getId());
            daoAndamento.salvar(andamento, transacao);
            transacao.confirmar();
        } catch (SQLException ex) {
            try {
                if(transacao!=null){
                    transacao.cancelar();
                }
            } catch (SQLException ex2) {
                Logger.getLogger(this.getClass().getName()).
                        log(Level.SEVERE, ex2.getMessage());
            }
            Logger.getLogger(GestorDePacotes.class.getName()).
                    log(Level.SEVERE, null, ex.getMessage());
            return UtilsNegocio.criarRespostaComErro("Falha de transação");
        }
        return UtilsNegocio.criarRespostaValida(pacote);
    }

    @Override
    public Resposta<List<Pacote>> pesquisarPacotesNovos(Usuario autor, 
            long projetoSelecionado) {
        if (autor == null || autor.getId() < 1) {
            return UtilsNegocio.criarRespostaComErro("Usuário não informado");
        }
        if (projetoSelecionado < 1) {
            return UtilsNegocio.criarRespostaComErro("Projeto não informado");
        }
        
        Pesquisa pesquisa = new Pesquisa(Pacote.class);
        pesquisa.adicionarFiltroTexto("nomeEstado", OperacaoFiltroTexto.IGUAL, 
                Estado.NOVO.getNome());
        pesquisa.adicionarFiltroNumerico("idProjeto", 
                OperacaoFiltroNumerico.IGUAL, projetoSelecionado);
        
        Resposta<List<Pacote>> resposta;
        IDaoPacote daoPacote = new DaoPacote();
        try {
            List<Pacote> pacotes = daoPacote.pesquisar(pesquisa);
            resposta = UtilsNegocio.criarRespostaValida(pacotes);
        } catch (SQLException ex) {
            Logger.getLogger(GestorDePacotes.class.getName()).
                    log(Level.SEVERE, null, ex);
            resposta = UtilsNegocio.criarRespostaComErro("Falha no sistema");
        }
        return resposta;
    }
}
