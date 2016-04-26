package br.ufg.inf.fabrica.pac.persistencia.imp;

import br.ufg.inf.fabrica.pac.dominio.Andamento;
import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.persistencia.IDaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

/**
 *
 * @author Danillo
 */
public class DaoAndamento implements IDaoAndamento {

    @Override
    public List<Andamento> andamentosPorPacote(Pacote pacote) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Andamento salvar(Andamento entity, Transacao transacao) throws SQLException {
        String sqlUpdate = "update ANDAMENTO set DataModificacao=?, DataPrevistaConclusao=?,"
                + " Descricao=?, nomeEstado=?, IdPacote=?, idUsuarioRemetente=? , idUsuarioDestinatario=?"
                + " where id=?";
        String sqlInsert = "insert into ANDAMENTO (DataModificacao, DataPrevistaConclusao, Descricao,"
                + "nomeEstado, IdPacote, idUsuarioRemetente, idUsuarioDestinatario) values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst;
        if (entity.getId() == 0) {
            pst = transacao.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        } else {
            pst = transacao.getConnection().prepareStatement(sqlUpdate);
            pst.setLong(7, entity.getId());
        }
        if (entity.getDataModificacao()!=null) {
            pst.setDate(1, Utils.convertUtilDateToSqlDate(entity.getDataModificacao()));
        } else {
            pst.setDate(1, null);
        }
        if (entity.getDataPrevistaConclusao()!=null) {
            pst.setDate(2, Utils.convertUtilDateToSqlDate(entity.getDataPrevistaConclusao()));
        } else {
            pst.setDate(2, null);
        }
        pst.setString(3, entity.getDescricao());
        pst.setString(4, entity.getNomeEstado());
        pst.setLong(5, entity.getIdPacote());
        pst.setLong(6, entity.getIdUsuarioRemetente());
        if(entity.getIdUsuarioDestinatario()==0){
            pst.setNull(7, Types.BIGINT);
        } else {
            pst.setLong(7, entity.getIdUsuarioDestinatario());
        }
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
    public Andamento excluir(Andamento entity, Transacao transacao) throws SQLException {
        String sql = "delete from ANDAMENTO where id=?";
        PreparedStatement pst;
        pst = transacao.getConnection().prepareStatement(sql);
        pst.setLong(1, entity.getId());
        pst.execute();
        return entity;
    }

    @Override
    public Andamento buscar(long id) throws SQLException {
        String sql = "select a.* from ANDAMENTO a where a.id=?";
        PreparedStatement pst;
        pst = Conexao.getConnection().prepareStatement(sql);
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        Andamento andamento = null;
        if (rs.next()) {
            andamento = new Andamento();
            andamento.setDataModificacao(Utils.convertSqlDateToUtilDate(
                    rs.getDate("dataModificacao")));
            andamento.setDataPrevistaConclusao(Utils.convertSqlDateToUtilDate(
                    rs.getDate("dataPrevistaConclusao")));
            andamento.setDescricao(rs.getString("descricao"));
            andamento.setId(id);
            andamento.setNomeEstado(rs.getString("nomeEstado"));
            andamento.setIdPacote(rs.getLong("idPacote"));
            andamento.setIdUsuarioRemetente(rs.getLong("idUsuarioRemetente"));
            andamento.setIdUsuarioDestinatario(rs.getLong("idUsuarioDestinatario"));
        }
        pst.getConnection().close();
        return andamento;
    }

}
