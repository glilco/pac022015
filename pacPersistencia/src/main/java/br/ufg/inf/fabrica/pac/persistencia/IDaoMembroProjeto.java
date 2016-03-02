/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.pac.persistencia;

import br.ufg.inf.fabrica.pac.dominio.MembroProjeto;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author auf
 */
public interface IDaoMembroProjeto extends IDao<MembroProjeto> {

    /**
     *
     * @param idProjeto
     * @param usuarioPesquisado
     * @return
     */
    public Resposta<List<Usuario>> buscarUsuariosNaoMembrosPorProjeto(
            long idProjeto, String usuarioPesquisado);

    /**
     *
     * @param idProjeto
     * @return
     */
    public Resposta<List<MembroProjeto>> buscarMembrosPorProjeto(
            long idProjeto);

    /**
     *
     * @param membros
     * @return
     * @throws SQLException
     */
    public List<MembroProjeto> adicionarMembrosProjeto(
            List<MembroProjeto> membros) throws SQLException;

    /**
     *
     * @param papeisRemovidos
     * @param papeisAdicionados
     */
    public void atualizarPapeisDeUsuarioEmUmProjeto(
            List<MembroProjeto> papeisRemovidos,
            List<MembroProjeto> papeisAdicionados) throws SQLException;

    /**
     *
     * @param projeto
     * @param papel
     * @return
     */
    public List<MembroProjeto> buscar(Projeto projeto, String papel);

    /**
     *
     * @param projeto
     * @param usuario
     * @return
     */
    public List<MembroProjeto> buscar(Projeto projeto, Usuario usuario);

    /**
     *
     * @param papel
     * @param usuario
     * @return
     */
    public List<MembroProjeto> buscar(String papel, Usuario usuario);

    /**
     *
     * @return
     */
    public Resposta<List<Usuario>> buscarUsuarios();

}
