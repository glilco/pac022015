/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.pac.persistencia;

import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author auf
 */
public interface IDaoMembro extends IDao<Membro> {

    /**
     *
     * @param idProjeto
     * @param usuarioPesquisado
     * @return
     */
    public Resposta<List<Usuario>> buscarUsuariosNaoMembrosPorProjeto(
            long idProjeto, String usuarioPesquisado) throws SQLException;

    /**
     *
     * @param idProjeto
     * @return
     */
    public Resposta<List<Membro>> buscarMembrosPorProjeto(
            long idProjeto) throws SQLException;

    /**
     *
     * @param membros
     * @return
     * @throws SQLException
     */
    public void adicionarMembrosProjeto(
            List<Membro> membros) throws SQLException;

    /**
     *
     * @param papeisRemovidos
     * @param papeisAdicionados
     */
    public void atualizarPapeisDeUsuarioEmUmProjeto(
            List<Membro> papeisRemovidos,
            List<Membro> papeisAdicionados) throws SQLException;

    /**
     *
     * @param projeto
     * @param papel
     * @return
     */
    public List<Membro> buscar(Projeto projeto, String papel) throws SQLException;

    /**
     *
     * @param projeto
     * @param usuario
     * @return
     */
    public List<Membro> buscar(Projeto projeto, Usuario usuario) 
            throws SQLException;

    /**
     *
     * @param papel
     * @param usuario
     * @return
     */
    public List<Membro> buscar(String papel, Usuario usuario) 
            throws SQLException;
    
    /**
     *
     * @return
     */
    public Resposta<List<Usuario>> buscarUsuarios() throws SQLException;

    /**
     * 
     * @param idUsuario
     * @return 
     */
    public List<Membro> buscarPapeis(long idUsuario) throws SQLException;
}
