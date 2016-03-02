/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.pac.persistencia.imp;

import br.ufg.inf.fabrica.pac.dominio.MembroProjeto;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembroProjeto;
import br.ufg.inf.fabrica.pac.persistencia.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auf
 */
public class DaoMembroProjeto implements IDaoMembroProjeto {

    @Override
    public MembroProjeto salvar(MembroProjeto entity) {
        return null;
    }

    @Override
    public MembroProjeto excluir(MembroProjeto entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MembroProjeto buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MembroProjeto> buscar(Projeto projeto, String papel) {
        String sql = "select * from MEMBROPROJETO where idProjeto=?, papel=?";
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setLong(1, projeto.getId());
            pst.setString(2, papel);
            ResultSet rs = pst.executeQuery();
            List<MembroProjeto> membroProjeto = new ArrayList<>();
            MembroProjeto mP = null;

            while (rs.next()) {
                mP = new MembroProjeto();
                mP.setIdProjeto(rs.getLong("idProjeto"));
                mP.setIdUsuario(rs.getLong("idUsuario"));
                mP.setPapel(rs.getString("papel"));
                membroProjeto.add(mP);
            }
            return membroProjeto;
        } catch (SQLException ex) {
            Logger.getLogger(DaoPacote.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<MembroProjeto> buscar(Projeto projeto, Usuario usuario) {
        String sql = "select * from MEMBROPROJETO where idProjeto=? and idUsuario=?";
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setLong(1, projeto.getId());
            pst.setLong(2, usuario.getId());
            ResultSet rs = pst.executeQuery();
            List<MembroProjeto> membroProjeto = new ArrayList<>();
            MembroProjeto mP = null;

            while (rs.next()) {
                mP = new MembroProjeto();
                mP.setIdProjeto(rs.getLong("idProjeto"));
                mP.setIdUsuario(rs.getLong("idUsuario"));
                mP.setPapel(rs.getString("papel"));
                membroProjeto.add(mP);
            }
            return membroProjeto;
        } catch (SQLException ex) {
            Logger.getLogger(DaoPacote.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<MembroProjeto> buscar(String papel, Usuario usuario) {
        String sql = "select * from MEMBROPROJETO where papel=?, idUsuario=?";
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setString(1, papel);
            pst.setLong(2, usuario.getId());
            ResultSet rs = pst.executeQuery();
            List<MembroProjeto> membroProjeto = new ArrayList<>();
            MembroProjeto mP = null;

            while (rs.next()) {
                mP = new MembroProjeto();
                mP.setIdProjeto(rs.getLong("idProjeto"));
                mP.setIdUsuario(rs.getLong("idUsuario"));
                mP.setPapel(rs.getString("papel"));
                membroProjeto.add(mP);
            }
            return membroProjeto;
        } catch (SQLException ex) {
            Logger.getLogger(DaoPacote.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Resposta<List<Usuario>> buscarUsuarios() {
        StringBuilder sb = new StringBuilder();
        sb.append("select U.*, M.* from USUARIO U ").
                append("left join MEMBROPROJETO M on U.ID = M.IDUSUARIO ").
                append("order by U.ID");
        Resposta resposta = new Resposta();
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sb.toString());
            ResultSet rs = pst.executeQuery();
            List<Usuario> usuarios = new ArrayList();
            while (rs.next()) {
                Usuario usuario = Util.populaObjeto(Usuario.class, rs);
                MembroProjeto membro = Util.populaObjeto(MembroProjeto.class,
                        rs);
                if (membro.getIdUsuario() > 0) {
                    membro.setUsuario(usuario);
                }
                usuarios.add(usuario);
            }
            resposta.setChave(usuarios);
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null,
                    ex);
            resposta.setChave(null);
            resposta.addItemLaudo(ex.getMessage());
        }
        return resposta;
    }

    @Override
    public Resposta<List<Usuario>> buscarUsuariosNaoMembrosPorProjeto(
            long idProjeto, String usuarioPesquisado) {
        StringBuilder sql = new StringBuilder();
        sql.append("select u.* from Usuario u ").
                append("where u.nome like ? and u.id not in ").
                append("(select m.idUsuario from MembroProjeto m ").
                append("where m.idProjeto = ?)");
        Resposta resposta = new Resposta();
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql.toString());
            pst.setString(1, usuarioPesquisado + "%");
            pst.setLong(2, idProjeto);
            ResultSet rs = pst.executeQuery();
            List<Usuario> usuarios = new ArrayList();
            while (rs.next()) {
                Usuario usuario = Util.populaObjeto(Usuario.class, rs);
                usuarios.add(usuario);
            }
            resposta.setChave(usuarios);
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null,
                    ex);
            resposta.setChave(null);
            resposta.addItemLaudo(ex.getMessage());
        }
        return resposta;
    }

    @Override
    public Resposta<List<MembroProjeto>> buscarMembrosPorProjeto(long idProjeto) {
        StringBuilder sql = new StringBuilder();
        sql.append("select m.*, u.* from MEMBROPROJETO m ").
                append("inner join USUARIO u ").
                append("on m.IDUSUARIO=u.ID ").
                append("where m.IDPROJETO = ? ORDER by m.IDUSUARIO");

        Resposta resposta = new Resposta();
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql.toString());
            pst.setLong(1, idProjeto);
            ResultSet rs = pst.executeQuery();
            List<MembroProjeto> membros = new ArrayList();
            while (rs.next()) {
                Usuario usuario = Util.populaObjeto(Usuario.class, rs);
                MembroProjeto membro = Util.populaObjeto(MembroProjeto.class,
                        rs);
                membro.setUsuario(usuario);
                membros.add(membro);
            }
            resposta.setChave(membros);
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null,
                    ex);
            resposta.setChave(null);
            resposta.addItemLaudo(ex.getMessage());
        }
        return resposta;
    }

    @Override
    public List<MembroProjeto> adicionarMembrosProjeto(
            List<MembroProjeto> membros) throws SQLException {
        Connection con = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("insert into MEMBROPROJETO ").
                    append("(IDUSUARIO, IDPROJETO, PAPEL) ").
                    append("values (?, ?, ?)");

            con = Conexao.getConnection();
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql.toString());
            for (MembroProjeto membro : membros) {
                pst.setLong(1, membro.getIdUsuario());
                pst.setLong(2, membro.getIdProjeto());
                pst.setString(3, membro.getPapel());
                pst.execute();
            }
            con.commit();
        } finally {
            if (!con.isClosed()) {
                con.close();
            }
        }
        return null;
    }

    @Override
    public void atualizarPapeisDeUsuarioEmUmProjeto(
            List<MembroProjeto> papeisRemovidos,
            List<MembroProjeto> papeisAdicionados) throws SQLException {
        StringBuilder sqlInsercao = new StringBuilder();
        StringBuilder sqlDelecao = new StringBuilder();
        sqlInsercao.append("insert into MEMBROPROJETO").
                append(" (IDUSUARIO, IDPROJETO, PAPEL) ").
                append(" VALUES (?,?,?)");
        sqlDelecao.append("delete from MEMBROPROJETO ").
                append("where IDUSUARIO = ? and ").
                append("IDPROJETO = ? and ").
                append("PAPEL like ? ");

        Connection con = null;
        try {
            con = Conexao.getConnection(true);

            PreparedStatement pstmt;
            for (MembroProjeto papelAdicionado : papeisAdicionados) {
                pstmt = con.prepareStatement(sqlInsercao.toString());
                pstmt.setLong(1, papelAdicionado.getIdUsuario());
                pstmt.setLong(2, papelAdicionado.getIdProjeto());
                pstmt.setString(3, papelAdicionado.getPapel());
                pstmt.execute();
            }
            for (MembroProjeto papelRemovido : papeisRemovidos) {
                pstmt = con.prepareStatement(sqlDelecao.toString());
                pstmt.setLong(1, papelRemovido.getIdUsuario());
                pstmt.setLong(2, papelRemovido.getIdProjeto());
                pstmt.setString(3, papelRemovido.getPapel());
                pstmt.execute();
            }

            con.commit();
        } finally {
            if (!con.isClosed()) {
                con.close();
            }
        }
    }

}
