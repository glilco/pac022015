package br.ufg.inf.fabrica.pac.persistencia.imp;

import br.ufg.inf.fabrica.pac.dominio.Estado;
import br.ufg.inf.fabrica.pac.persistencia.IDaoEstado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danillo
 */
public class DaoEstado implements IDaoEstado {

    @Override
    public Estado salvar(Estado entity) {
        throw new UnsupportedOperationException("Não existe entidade estado no Banco."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Estado excluir(Estado entity) {
        throw new UnsupportedOperationException("Não existe entidade estado no Banco."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Estado buscar(long id) {
        throw new UnsupportedOperationException("Não existe entidade estado no Banco."); //To change body of generated methods, choose Tools | Templates.
    }

    public static Estado buscarPorNome(String nome) {
        String sql = "select E.* from ESTADO as E where nome = ?";
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setString(1, nome);
            ResultSet rs = pst.executeQuery();
            Estado estado = null;
            if (rs.next()) {
                estado = new Estado();
                estado.setId(rs.getLong("id"));
                estado.setDescricao(rs.getString("descricao"));
                estado.setEstadoFinal(rs.getBoolean("estadoFinal"));
                estado.setNome(rs.getString("nome"));
            }
            return estado;
        } catch (SQLException ex) {
            Logger.getLogger(DaoEstado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
