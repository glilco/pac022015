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
        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);) {
            pst.setLong(1, projeto.getId());
            pst.setString(2, papel);
            try (ResultSet rs = pst.executeQuery();) {
                membro = new ArrayList<>();
                while (rs.next()) {
                    Membro mP = new Membro();
                    mP.setIdProjeto(rs.getLong("idProjeto"));
                    mP.setIdUsuario(rs.getLong("idUsuario"));
                    mP.setPapel(rs.getString("papel"));
                    membro.add(mP);
                }
            }
        }
        return membro;
    }

    @Override
    public List<Membro> buscar(Projeto projeto, Usuario usuario)
            throws SQLException {
        String sql = "select * from MEMBRO where idProjeto=? and idUsuario=?";
        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);) {
            pst.setLong(1, projeto.getId());
            pst.setLong(2, usuario.getId());
            try (ResultSet rs = pst.executeQuery();) {
                List<Membro> membro = new ArrayList<>();
                while (rs.next()) {
                    Membro mP = new Membro();
                    mP.setIdProjeto(rs.getLong("idProjeto"));
                    mP.setIdUsuario(rs.getLong("idUsuario"));
                    mP.setPapel(rs.getString("papel"));
                    membro.add(mP);
                }
                return membro;
            }
        }
    }

    @Override
    public List<Membro> buscar(String papel, Usuario usuario)
            throws SQLException {
        String sql = "select * from MEMBRO where papel=?, idUsuario=?";
        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);) {
            pst.setString(1, papel);
            pst.setLong(2, usuario.getId());
            try (ResultSet rs = pst.executeQuery();) {
                List<Membro> membro = new ArrayList<>();
                while (rs.next()) {
                    Membro mP = new Membro();
                    mP.setIdProjeto(rs.getLong("idProjeto"));
                    mP.setIdUsuario(rs.getLong("idUsuario"));
                    mP.setPapel(rs.getString("papel"));
                    membro.add(mP);
                }
                return membro;
            }
        }
    }

    @Override
    public Resposta<List<Usuario>> buscarUsuarios() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("select U.*, M.* from USUARIO U ").
                append("left join MEMBRO M on U.ID = M.IDUSUARIO ").
                append("order by U.ID");
        Resposta resposta = new Resposta();
        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sb.toString());
                ResultSet rs = pst.executeQuery();) {
            List<Usuario> usuarios = new ArrayList();
            while (rs.next()) {
                Usuario usuario = Util.populaObjeto(Usuario.class, rs);
                Membro membro = Util.populaObjeto(Membro.class, rs);
                if (membro.getIdUsuario() > 0) {
                    membro.setUsuario(usuario);
                }
                usuarios.add(usuario);
            }
            resposta.setChave(usuarios);
        }
        return resposta;
    }

    @Override
    public Resposta<List<Usuario>> buscarUsuariosNaoMembrosPorProjeto(
            long idProjeto, String usuarioPesquisado) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select u.* from Usuario u ").
                append("where u.nome like ? and u.id not in ").
                append("(select m.idUsuario from Membro m ").
                append("where m.idProjeto = ?)");
        Resposta resposta = new Resposta();
        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sql.toString());) {
            pst.setString(1, usuarioPesquisado + "%");
            pst.setLong(2, idProjeto);
            try (ResultSet rs = pst.executeQuery();) {
                List<Usuario> usuarios = new ArrayList();
                while (rs.next()) {
                    Usuario usuario = Util.populaObjeto(Usuario.class, rs);
                    usuarios.add(usuario);
                }
                resposta.setChave(usuarios);
            }
        }
        return resposta;
    }

    @Override
    public Resposta<List<Membro>> buscarMembrosPorProjeto(long idProjeto)
            throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select m.*, u.* from MEMBRO m ").
                append("inner join USUARIO u ").
                append("on m.IDUSUARIO=u.ID ").
                append("where m.IDPROJETO = ? ORDER by m.IDUSUARIO");
        Resposta resposta = new Resposta();
        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sql.toString());) {
            pst.setLong(1, idProjeto);
            try (ResultSet rs = pst.executeQuery();) {
                List<Membro> membros = new ArrayList();
                while (rs.next()) {
                    Usuario usuario = Util.populaObjeto(Usuario.class, rs);
                    Membro membro = Util.populaObjeto(Membro.class, rs);
                    membro.setUsuario(usuario);
                    membros.add(membro);
                }
                resposta.setChave(membros);
            }
        }
        return resposta;
    }

    @Override
    public void adicionarMembrosProjeto(
            List<Membro> membros) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into MEMBRO ").
                append("(IDUSUARIO, IDPROJETO, PAPEL) ").
                append("values (?, ?, ?)");
        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sql.toString());) {
            for (Membro membro : membros) {
                pst.setLong(1, membro.getIdUsuario());
                pst.setLong(2, membro.getIdProjeto());
                pst.setString(3, membro.getPapel());
                pst.execute();
            }
            con.commit();
        }
    }

    @Override
    public void atualizarPapeisDeUsuarioEmUmProjeto(List<Membro> papeisRemovidos,
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

        try (Connection con = Conexao.getConnection(true)) {
            for (Membro papelAdicionado : papeisAdicionados) {
                try (PreparedStatement pstmt
                        = con.prepareStatement(sqlInsercao.toString());) {
                    pstmt.setLong(1, papelAdicionado.getIdUsuario());
                    pstmt.setLong(2, papelAdicionado.getIdProjeto());
                    pstmt.setString(3, papelAdicionado.getPapel());
                    pstmt.execute();
                }
            }
            for (Membro papelRemovido : papeisRemovidos) {
                try (PreparedStatement pstmt
                        = con.prepareStatement(sqlDelecao.toString());) {
                    pstmt.setLong(1, papelRemovido.getIdUsuario());
                    pstmt.setLong(2, papelRemovido.getIdProjeto());
                    pstmt.setString(3, papelRemovido.getPapel());
                    pstmt.execute();
                }
            }

            con.commit();
        }
    }

    @Override
    public List<Membro> buscarPapeis(long idUsuario) throws SQLException {
        String sql = "select * from MEMBRO where idUsuario=?";
        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);) {
            pst.setLong(1, idUsuario);
            try (ResultSet rs = pst.executeQuery();) {
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
    }
}
