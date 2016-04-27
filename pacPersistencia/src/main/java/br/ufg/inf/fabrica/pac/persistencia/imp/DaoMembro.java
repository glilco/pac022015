package br.ufg.inf.fabrica.pac.persistencia.imp;

import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
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
public class DaoMembro implements IDaoMembro {

    @Override
    public Membro salvar(Membro entity, Transacao transacao) {
        return null;
    }

    @Override
    public Membro excluir(Membro entity, Transacao transacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Membro buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Membro> buscar(Projeto projeto, String papel) 
            throws SQLException {
        String sql = "select * from MEMBRO where idProjeto=?, papel=?";
        List<Membro> membro;
        try (Connection con = Conexao.getConnection();) {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setLong(1, projeto.getId());
            pst.setString(2, papel);
            ResultSet rs = pst.executeQuery();
            membro = new ArrayList<>();
            while (rs.next()) {
                Membro mP = new Membro();
                mP.setIdProjeto(rs.getLong("idProjeto"));
                mP.setIdUsuario(rs.getLong("idUsuario"));
                mP.setPapel(rs.getString("papel"));
                membro.add(mP);
            }
        }
        return membro;
    }

    @Override
    public List<Membro> buscar(Projeto projeto, Usuario usuario) {
        String sql = "select * from MEMBRO where idProjeto=? and idUsuario=?";
        PreparedStatement pst = null;
        try {
            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setLong(1, projeto.getId());
            pst.setLong(2, usuario.getId());
            ResultSet rs = pst.executeQuery();
            List<Membro> membro = new ArrayList<>();
            Membro mP = null;

            while (rs.next()) {
                mP = new Membro();
                mP.setIdProjeto(rs.getLong("idProjeto"));
                mP.setIdUsuario(rs.getLong("idUsuario"));
                mP.setPapel(rs.getString("papel"));
                membro.add(mP);
            }
            return membro;

        } catch (SQLException ex) {
            Logger.getLogger(DaoPacote.class
                    .getName()).log(Level.SEVERE, null, ex);

            return null;
        } finally {
            try {
                if (pst != null) {
                    pst.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoMembro.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Membro> buscar(String papel, Usuario usuario) {
        String sql = "select * from MEMBRO where papel=?, idUsuario=?";
        PreparedStatement pst = null;
        try {

            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setString(1, papel);
            pst.setLong(2, usuario.getId());
            ResultSet rs = pst.executeQuery();
            List<Membro> membro = new ArrayList<>();
            Membro mP = null;

            while (rs.next()) {
                mP = new Membro();
                mP.setIdProjeto(rs.getLong("idProjeto"));
                mP.setIdUsuario(rs.getLong("idUsuario"));
                mP.setPapel(rs.getString("papel"));
                membro.add(mP);
            }
            return membro;

        } catch (SQLException ex) {
            Logger.getLogger(DaoPacote.class
                    .getName()).log(Level.SEVERE, null, ex);

            return null;
        } finally {
            try {
                if (pst != null) {
                    pst.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoMembro.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Resposta<List<Usuario>> buscarUsuarios() {
        StringBuilder sb = new StringBuilder();
        sb.append("select U.*, M.* from USUARIO U ").
                append("left join MEMBRO M on U.ID = M.IDUSUARIO ").
                append("order by U.ID");
        Resposta resposta = new Resposta();
        PreparedStatement pst = null;
        try {

            pst = Conexao.getConnection().prepareStatement(sb.toString());
            ResultSet rs = pst.executeQuery();
            List<Usuario> usuarios = new ArrayList();

            while (rs.next()) {
                Usuario usuario = Util.populaObjeto(Usuario.class, rs);
                Membro membro = Util.populaObjeto(Membro.class,
                        rs);

                if (membro.getIdUsuario()
                        > 0) {
                    membro.setUsuario(usuario);
                }

                usuarios.add(usuario);
            }
            resposta.setChave(usuarios);

        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuario.class
                    .getName()).log(Level.SEVERE, null,
                            ex);
            resposta.setChave(
                    null);
            resposta.addItemLaudo(ex.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoMembro.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resposta;
    }

    @Override
    public Resposta<List<Usuario>> buscarUsuariosNaoMembrosPorProjeto(
            long idProjeto, String usuarioPesquisado) {
        StringBuilder sql = new StringBuilder();
        sql.append("select u.* from Usuario u ").
                append("where u.nome like ? and u.id not in ").
                append("(select m.idUsuario from Membro m ").
                append("where m.idProjeto = ?)");
        Resposta resposta = new Resposta();
        PreparedStatement pst = null;
        try {
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
            Logger.getLogger(DaoUsuario.class
                    .getName()).log(Level.SEVERE, null,
                            ex);
            resposta.setChave(
                    null);
            resposta.addItemLaudo(ex.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoMembro.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resposta;
    }

    @Override
    public Resposta<List<Membro>> buscarMembrosPorProjeto(long idProjeto) {
        StringBuilder sql = new StringBuilder();
        sql.append("select m.*, u.* from MEMBRO m ").
                append("inner join USUARIO u ").
                append("on m.IDUSUARIO=u.ID ").
                append("where m.IDPROJETO = ? ORDER by m.IDUSUARIO");

        Resposta resposta = new Resposta();
        PreparedStatement pst = null;
        try {
            pst = Conexao.getConnection().prepareStatement(sql.toString());
            pst.setLong(1, idProjeto);
            ResultSet rs = pst.executeQuery();
            List<Membro> membros = new ArrayList();

            while (rs.next()) {
                Usuario usuario = Util.populaObjeto(Usuario.class, rs);
                Membro membro = Util.populaObjeto(Membro.class,
                        rs);

                membro.setUsuario(usuario);

                membros.add(membro);
            }
            resposta.setChave(membros);

        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuario.class
                    .getName()).log(Level.SEVERE, null,
                            ex);
            resposta.setChave(
                    null);
            resposta.addItemLaudo(ex.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoMembro.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resposta;
    }

    @Override
    public List<Membro> adicionarMembrosProjeto(
            List<Membro> membros) throws SQLException {
        Connection con = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("insert into MEMBRO ").
                    append("(IDUSUARIO, IDPROJETO, PAPEL) ").
                    append("values (?, ?, ?)");

            con = Conexao.getConnection();
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql.toString());
            for (Membro membro : membros) {
                pst.setLong(1, membro.getIdUsuario());
                pst.setLong(2, membro.getIdProjeto());
                pst.setString(3, membro.getPapel());
                pst.execute();
            }
            con.commit();
        } finally {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        return null;
    }

    @Override
    public void atualizarPapeisDeUsuarioEmUmProjeto(
            List<Membro> papeisRemovidos,
            List<Membro> papeisAdicionados) throws SQLException {
        StringBuilder sqlInsercao = new StringBuilder();
        StringBuilder sqlDelecao = new StringBuilder();
        sqlInsercao.append("insert into MEMBRO").
                append(" (IDUSUARIO, IDPROJETO, PAPEL) ").
                append(" VALUES (?,?,?)");
        sqlDelecao.append("delete from MEMBRO ").
                append("where IDUSUARIO = ? and ").
                append("IDPROJETO = ? and ").
                append("PAPEL like ? ");

        Connection con = null;
        try {
            con = Conexao.getConnection(true);

            PreparedStatement pstmt;
            for (Membro papelAdicionado : papeisAdicionados) {
                pstmt = con.prepareStatement(sqlInsercao.toString());
                pstmt.setLong(1, papelAdicionado.getIdUsuario());
                pstmt.setLong(2, papelAdicionado.getIdProjeto());
                pstmt.setString(3, papelAdicionado.getPapel());
                pstmt.execute();
            }
            for (Membro papelRemovido : papeisRemovidos) {
                pstmt = con.prepareStatement(sqlDelecao.toString());
                pstmt.setLong(1, papelRemovido.getIdUsuario());
                pstmt.setLong(2, papelRemovido.getIdProjeto());
                pstmt.setString(3, papelRemovido.getPapel());
                pstmt.execute();
            }

            con.commit();
        } finally {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
    }

    @Override
    public List<Membro> buscarPapeis(long idUsuario) throws SQLException {
        String sql = "select * from MEMBRO where idUsuario=?";

        PreparedStatement pst = Conexao.getConnection().prepareStatement(sql);
        pst.setLong(1, idUsuario);
        ResultSet rs = pst.executeQuery();
        List<Membro> membro = new ArrayList<>();

        while (rs.next()) {
            Membro mP = new Membro();
            mP.setIdProjeto(rs.getLong("idProjeto"));
            mP.setIdUsuario(rs.getLong("idUsuario"));
            mP.setPapel(rs.getString("papel"));
            membro.add(mP);
        }
        pst.getConnection().close();
        return membro;

    }

}
