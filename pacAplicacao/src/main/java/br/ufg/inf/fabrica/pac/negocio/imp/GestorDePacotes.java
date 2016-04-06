package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.dominio.Andamento;
import br.ufg.inf.fabrica.pac.negocio.ICriarPacote;
import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.dominio.enums.Estado;
import br.ufg.inf.fabrica.pac.negocio.utils.UtilsNegocio;
import br.ufg.inf.fabrica.pac.persistencia.IDaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.IDaoPacote;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoPacote;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import br.ufg.inf.fabrica.pac.seguranca.Seguranca;
import br.ufg.inf.fabrica.pac.seguranca.imp.SegurancaStub;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auf
 */
public class GestorDePacotes implements ICriarPacote {

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
    public Resposta<Pacote> criarPacote(Usuario autor, Pacote pacote, Projeto projetoSelecionado) {
        IDaoMembro daoMembro = new DaoMembro();
        List<Membro> papeis;
        List<String> nomePapeis = new ArrayList<>();
        String recursoId = null;
        try {
            papeis = daoMembro.buscarPapeis(autor.getId());
            for (Membro membro : papeis) {
                nomePapeis.add(membro.getPapel());
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestorDePacotes.class.getName()).log(Level.SEVERE, null, ex);
            Resposta resposta = UtilsNegocio.criarRespostaComErro("Falha no sistema");
            return resposta;
        }
        Seguranca seguranca = SegurancaStub.getInstance();
        if (!seguranca.autorizar(recursoId, nomePapeis)) {
            String menssagemErro = "Usuário não possui permissão para acessar recurso";
            Logger.getLogger(GestorDePacotes.class.getName()).log(Level.SEVERE, null, menssagemErro);
            Resposta resposta = UtilsNegocio.criarRespostaComErro(menssagemErro);
            return resposta;
        }

        pacote.setAbandonado(false);
        pacote.setDataCriacao(UtilsNegocio.buscarDataAtual());
        pacote.setEstado(Estado.NOVO);

        List<String> inconsistencias
                = pacote.validar();
        if (!inconsistencias.isEmpty()) {
            String menssagemErro = "Pacote inconsistente";
            Logger.getLogger(GestorDePacotes.class.getName()).log(Level.SEVERE, null, menssagemErro);
            Resposta resposta = UtilsNegocio.criarRespostaComErro(inconsistencias);
            return resposta;
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
            Logger.getLogger(GestorDePacotes.class.getName()).log(Level.SEVERE, null, menssagemErro);
            Resposta resposta = UtilsNegocio.criarRespostaComErro(inconsistencias);
            return resposta;
        }

        IDaoAndamento daoAndamento = new DaoAndamento();
        IDaoPacote daoPacote = new DaoPacote();

        try {
            Transacao transacao;
            transacao = Transacao.getInstance();
            daoPacote.salvar(pacote, transacao);
            andamento.setIdPacote(pacote.getId());
            daoAndamento.salvar(andamento, transacao);
            transacao.confirmar();
        } catch (SQLException ex) {
            Logger.getLogger(GestorDePacotes.class.getName()).log(Level.SEVERE, null, ex);
            return UtilsNegocio.criarRespostaComErro("Falha de transação");
        }
        return UtilsNegocio.criarRespostaValida(pacote);
    }
}
