package br.ufg.inf.fabrica.pac.persistencia.imp;

import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.persistencia.IDaoPacote;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.Pesquisa;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danillo
 */
public class DaoPacote implements IDaoPacote {

    @Override
    public Pacote salvar(Pacote entity, Transacao transacao) throws SQLException {

        String sqlUpdate = "update PACOTE set Abandonado=?, DataCriacao=?, DataPrevistaRealizacao=?"
                + ", Descricao=?, Documento=?, idEstado=?, IdProjeto=?, IdUsuario=?"
                + ", Nome=? where id = ?";
        String sqlInsert = "insert into PACOTE (Abandonado, DataCriacao, "
                + "DataPrevistaRealizacao, Descricao, Documento, idEstado, "
                + "IdProjeto, IdUsuario, Nome) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst;
        if (entity.getId() == 0) {
            pst = transacao.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        } else {
            pst = transacao.getConnection().prepareStatement(sqlUpdate);
            pst.setLong(10, entity.getId());
        }

        pst.setBoolean(1, entity.isAbandonado());
        if (entity.getDataCriacao() != null) {
            pst.setDate(2, Utils.convertUtilDateToSqlDate(entity.getDataCriacao()));
        } else {
            pst.setDate(2, null);
        }
        if (entity.getDataPrevistaRealizacao() != null) {
            pst.setDate(3, Utils.convertUtilDateToSqlDate(entity.getDataPrevistaRealizacao()));
        } else {
            pst.setDate(3, null);
        }
        pst.setString(4, entity.getDescricao());
        pst.setString(5, entity.getDocumento());
        pst.setLong(6, entity.getIdEstado());
        pst.setLong(7, entity.getIdProjeto());
        pst.setLong(8, entity.getIdUsuario());
        pst.setString(9, entity.getNome());

        pst.execute();
        if (entity.getId() == 0) {
            ResultSet keys = pst.getGeneratedKeys();
            if (keys.next()) {
                entity.setId(keys.getInt(1));
            }
        }
        return entity;

    }

    @Override
    public Pacote excluir(Pacote entity, Transacao transacao) throws SQLException {
        String sql = "delete from PACOTE where id=?";
        PreparedStatement pst;
        pst = transacao.getConnection().prepareStatement(sql);
        pst.setLong(1, entity.getId());
        pst.execute();
        return entity;
    }

    @Override
    public Pacote buscar(long id) throws SQLException {
        String sql = "select P.* from PACOTE as P where id=?";

        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery();) {
                Pacote pacote = null;
                if (rs.next()) {
                    pacote = construirPacote(rs);
                }
                pst.close();
                return pacote;
            }
        }
    }

    @Override
    public List pesquisar(Pesquisa pesquisa) throws SQLException {
        String sql = pesquisa.construirConsulta();
        try (Connection con = Conexao.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();) {
            List<Pacote> pacotes = new ArrayList<>();
            while (rs.next()) {
                Pacote pacote = construirPacote(rs);
                pacotes.add(pacote);
            }
            pst.close();
            return pacotes;
        }
    }

    private Pacote construirPacote(final ResultSet rs) throws SQLException {
        Pacote pacote = new Pacote();
        pacote.setId(rs.getLong("id"));
        pacote.setAbandonado(rs.getBoolean("abandonado"));
        pacote.setDataCriacao(rs.getDate("dataCriacao"));
        pacote.setDataPrevistaRealizacao(rs.getDate("dataPrevistaRealizacao"));
        pacote.setDescricao(rs.getString("descricao"));
        pacote.setDocumento(rs.getString("documento"));
        pacote.setId(rs.getLong("id"));
        pacote.setIdEstado(rs.getLong("idEstado"));
        pacote.setIdProjeto(rs.getLong("idProjeto"));
        pacote.setIdUsuario(rs.getLong("idUsuario"));
        pacote.setNome(rs.getString("nome"));
        return pacote;
    }
}
