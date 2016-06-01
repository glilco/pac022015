package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.negocio.IGestorMembros;
import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.utils.Constantes;
import br.ufg.inf.fabrica.pac.negocio.utils.UtilsNegocio;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoMembro;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorMembrosImpl implements IGestorMembros {

    @Override
    public Resposta<Membro> adicionarMembroProjeto(Usuario autor,
            Membro membro) {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Resposta<Membro> removerMembroProjeto(Usuario autor,
            Membro membro) {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Resposta<List<Usuario>> buscarUsuariosNaoMembros(Usuario autor,
            Projeto projeto, String usuarioPesquisado) {
        Resposta<List<Usuario>> resposta = new Resposta();
        if (projeto == null) {
            return UtilsNegocio.criarRespostaComErro("Projeto não informado");
        } else if (usuarioPesquisado == null) {
            return UtilsNegocio.criarRespostaComErro("Usuário não informado");
        }
        IDaoMembro dao = new DaoMembro();
        List<Usuario> usuarios = null;
        try {
            usuarios = dao.buscarUsuariosNaoMembrosPorProjeto(projeto.getId(),
                    usuarioPesquisado).getChave();
        } catch (SQLException ex) {
            Logger.getLogger(GestorMembrosImpl.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        resposta.setChave(usuarios);
        return resposta;
    }

    @Override
    public Resposta<List<Membro>> buscarMembros(Usuario autor,
            Projeto projeto) {
        Resposta<List<Membro>> resposta = new Resposta();
        if (projeto == null) {
            resposta.addItemLaudo("Projeto não informado");
            return resposta;
        }
        IDaoMembro dao = new DaoMembro();
        List<Membro> membros = null;
        try {
            membros = dao.buscarMembrosPorProjeto(projeto.getId()).getChave();
        } catch (SQLException ex) {
            Logger.getLogger(GestorMembrosImpl.class.getName()).
                    log(Level.SEVERE, null, ex);
            return UtilsNegocio.criarRespostaComErro(
                    Constantes.FALHA_NO_SISTEMA);
        }
        resposta.setChave(membros);
        return resposta;
    }

    @Override
    public Resposta<Boolean> adicionarMembrosProjeto(
            Usuario autor, List<Membro> membros) {
        Resposta<Boolean> resposta = new Resposta<>();
        for (Membro membro : membros) {
            if (membro.getIdProjeto() <= 0) {
                resposta.addItemLaudo("Informe o projeto do membro");
            }
            if (membro.getIdUsuario() <= 0) {
                resposta.addItemLaudo("Informe o usuário do membro");
            }
            if (membro.getIdPapel() <= 0) {
                resposta.addItemLaudo("Informe o papel do membro");
            }
        }
        IDaoMembro dao = new DaoMembro();
        try {
            dao.adicionarMembrosProjeto(membros);
            resposta.setChave(Boolean.TRUE);
        } catch (SQLException ex) {
            Logger.getLogger(GestorMembrosImpl.class.getName()).
                    log(Level.SEVERE, null, ex);
            resposta = UtilsNegocio.
                    criarRespostaComErro(Constantes.FALHA_NO_SISTEMA);
        }
        return resposta;
    }

    @Override
    public Resposta<String> atualizarPapeisDeUsuarioEmUmProjeto(Usuario autor,
            int idUsuario, Projeto projetoSelecionado,
            List<Membro> papeisRemovidos, List<Membro> papeisAdicionados) {
        List<String> inconsistencias
                = validarEntradasAtualizacaoPapeisUsuarioEmProjeto(autor,
                        papeisRemovidos, papeisAdicionados);
        if (!inconsistencias.isEmpty()) {
            return UtilsNegocio.criarRespostaComErro(inconsistencias);
        }

        long idProjeto = projetoSelecionado.getId();
        boolean papeisRemovidosConsistentes
                = verificarConsistenciaDasPermissoesComParametros(
                        papeisRemovidos, idUsuario, idProjeto);
        boolean papeisAdicionadosConsistentes
                = verificarConsistenciaDasPermissoesComParametros(
                        papeisAdicionados, idUsuario, idProjeto);

        if (papeisRemovidosConsistentes && papeisAdicionadosConsistentes) {
            IDaoMembro dao = new DaoMembro();
            try {
                dao.atualizarPapeisDeUsuarioEmUmProjeto(papeisRemovidos,
                        papeisAdicionados);
                return UtilsNegocio.criarRespostaValida("ok");
            } catch (SQLException ex) {
                UtilsNegocio.registrarLog(getClass(), Level.SEVERE, ex);
                return UtilsNegocio.
                        criarRespostaComErro(Constantes.FALHA_NO_SISTEMA);
            }
        } else {
            return UtilsNegocio.criarRespostaComErro("Permissões solicitadas "
                    + "inconsistentes com o projeto e usuário informado");
        }

    }

    private boolean verificarConsistenciaDasPermissoesComParametros(
            List<Membro> papeis, long idUsuario, long idProjeto) {
        for (Membro papelRemovido : papeis) {
            if (verificarConsistenciaDeUsuarioEProjetoEmPermissao(idUsuario,
                    papelRemovido, idProjeto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean verificarConsistenciaDeUsuarioEProjetoEmPermissao(
            long idUsuario, Membro papelRemovido, long idProjeto) {
        return idUsuario != papelRemovido.getIdUsuario()
                || idProjeto != papelRemovido.getIdProjeto();
    }

    private List<String> validarEntradasAtualizacaoPapeisUsuarioEmProjeto(
            Usuario autor, List<Membro> papeisRemovidos, 
            List<Membro> papeisAdicionados) {
        List<String> inconsistencias = new ArrayList<>();
        if (autor == null || autor.getId() == 0) {
            inconsistencias.add("Informe autor da solicitação");
        }
        if ((papeisRemovidos == null || papeisRemovidos.isEmpty())
                && ((papeisAdicionados == null) || 
                papeisAdicionados.isEmpty())) {
            inconsistencias.add("Nenhum papel foi informado para atualização");
        }
        return inconsistencias;
    }
}
