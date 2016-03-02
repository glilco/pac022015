package br.ufg.inf.fabrica.pac.persistencia.imp;

import br.ufg.inf.fabrica.pac.dominio.Andamento;
import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.persistencia.IDaoAndamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danillo
 */
public class DaoAndamento implements IDaoAndamento{

    @Override
    public List<Andamento> andamentosPorPacote(Pacote pacote) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Andamento salvar(Andamento entity) {
        String sqlUpdate = "update ANDAMENTO set DataModificacao=?, DataPrevistaConclusao=?," +
                                " Descricao=?, IdEstado=?, IdPacote=?, idUsuarioRemetente=? , idUsuarioDestinatario=?" +
                                " where id=?";
        String sqlInsert = "insert into ANDAMENTO (DataModificacao, DataPrevistaConclusao, Descricao," +
                               "IdEstado, IdPacote, idUsuarioRemetente, idUsuarioDestinatario) values (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst;
            if(entity.getId()==0){
                pst = Conexao.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            } else {
                pst = Conexao.getConnection().prepareStatement(sqlUpdate);
                pst.setLong(7, entity.getId());
            }
            
            pst.setDate(1, Utils.convertUtilDateToSqlDate(entity.getDataModificacao()));
            pst.setDate(2, Utils.convertUtilDateToSqlDate(entity.getDataPrevistaConclusao()));
            pst.setString(3, entity.getDescricao());
            pst.setLong(4, entity.getIdEstado());
            pst.setLong(5, entity.getIdPacote());
            pst.setLong(6, entity.getIdUsuarioRemetente());
            pst.setLong(7, entity.getIdUsuarioDestinatario());
            pst.execute();
            if(entity.getId()==0){
                ResultSet keys = pst.getGeneratedKeys();
                if(keys.next()){
                    entity.setId(keys.getInt(1));
                }
            }
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(DaoAndamento.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Andamento excluir(Andamento entity) {
        String sql = "delete from ANDAMENTO where id=?";
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setLong(1, entity.getId());
            pst.execute();
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(DaoAndamento.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Andamento buscar(long id) {
        
        String sql = "select a.* from ANDAMENTO a where a.id=?";
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            Andamento andamento = null;
            if (rs.next()){
                andamento = new Andamento();
                andamento.setDataModificacao(Utils.convertSqlDateToUtilDate(
                        rs.getDate("dataModificacao")));
                andamento.setDataPrevistaConclusao(Utils.convertSqlDateToUtilDate(
                        rs.getDate("dataPrevistaConclusao")));
                andamento.setDescricao(rs.getString("descricao"));
                andamento.setId(id);
                andamento.setIdEstado(rs.getLong("idEstado"));
                andamento.setIdPacote(rs.getLong("idPacote"));
                andamento.setIdUsuarioRemetente(rs.getLong("idUsuarioRemetente"));
                andamento.setIdUsuarioDestinatario(rs.getLong("idUsuarioDestinatario"));
            }
            return andamento;
        } catch (SQLException ex) {
            Logger.getLogger(DaoAndamento.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
