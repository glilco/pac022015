package br.ufg.inf.fabrica.pac.persistencia.imp;

import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.persistencia.IDaoProjeto;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
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
public class DaoProjeto implements IDaoProjeto {

    @Override
    public Projeto salvar(Projeto entity, Transacao transacao) throws SQLException {

        String sqlUpdate = "update PROJETO set DATAINICIO=?, DATATERMINO=?, "
                + "DESCRICAO=?, NOME=?, PATROCINADOR=?, STAKEHOLDERS=? "
                + "where ID=?";
        String sqlInsert = "insert into PROJETO (DATAINICIO, DATATERMINO, "
                + "DESCRICAO, NOME, PATROCINADOR, STAKEHOLDERS) "
                + "values (?, ?, ?, ?, ?, ?)";

        PreparedStatement pst;
        if (entity.getId() == 0) {
            pst = transacao.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);
        } else {
            pst = transacao.getConnection().prepareStatement(sqlUpdate);
            pst.setLong(7, entity.getId());
        }
        pst.setDate(1, Utils.convertUtilDateToSqlDate(
                entity.getDataInicio()));
        pst.setDate(2, Utils.convertUtilDateToSqlDate(
                entity.getDataTermino()));
        pst.setString(3, entity.getDescricao());
        pst.setString(4, entity.getNome());
        pst.setString(5, entity.getPatrocinador());
        pst.setString(6, entity.getStakeholders());

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
    public Projeto excluir(Projeto entity, Transacao transacao)
            throws SQLException {
        String sql = "delete from PROJETO where id=?";

        PreparedStatement pst;
        pst = transacao.getConnection().prepareStatement(sql);
        pst.setLong(1, entity.getId());
        pst.execute();
        return entity;
    }

    @Override
    public Projeto buscar(long id) throws SQLException {
        String sql = "select P.* from PROJETO as P where id=?";

        PreparedStatement pst;
        pst = Conexao.getConnection().prepareStatement(sql);
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        Projeto projeto = null;
        if (rs.next()) {
            projeto = new Projeto();
            projeto.setId(rs.getLong("id"));
            projeto.setDataInicio(Utils.convertSqlDateToUtilDate(
                    rs.getDate("dataInicio")));
            projeto.setDataTermino(Utils.convertSqlDateToUtilDate(
                    rs.getDate("dataTermino")));
            projeto.setDescricao(rs.getString("descricao"));
            projeto.setNome(rs.getString("nome"));
            projeto.setPatrocinador(rs.getString("patrocinador"));
            projeto.setStakeholders(rs.getString("stakeholders"));
        }
        pst.close();
        return projeto;
    }

    @Override
    public List<Projeto> buscarTodos() throws SQLException {
        String sql = "select P.* from PROJETO as P";
        PreparedStatement pst;
        pst = Conexao.getConnection().prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        Projeto projeto = null;
        List<Projeto> projetos = new ArrayList<>();
        while (rs.next()) {
            projeto = new Projeto();
            projeto.setId(rs.getLong("id"));
            projeto.setDataInicio(Utils.convertSqlDateToUtilDate(
                    rs.getDate("dataInicio")));
            projeto.setDataTermino(Utils.convertSqlDateToUtilDate(
                    rs.getDate("dataTermino")));
            projeto.setDescricao(rs.getString("descricao"));
            projeto.setNome(rs.getString("nome"));
            projeto.setPatrocinador(rs.getString("patrocinador"));
            projeto.setStakeholders(rs.getString("stakeholders"));
            projetos.add(projeto);
        }
        pst.close();
        return projetos;
    }

}
