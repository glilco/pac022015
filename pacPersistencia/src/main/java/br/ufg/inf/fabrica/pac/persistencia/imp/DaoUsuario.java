package br.ufg.inf.fabrica.pac.persistencia.imp;

import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danillo
 */
public class DaoUsuario implements IDaoUsuario{

    @Override
    public Usuario salvar(Usuario entity) {
        String sqlUpdate = "update USUARIO set ativo=? where id=?";
        String sqlInsert = "insert into USUARIO (ativo, id, nome, email) values (?, ?, ?, ? )";
        try {
            PreparedStatement pst;
            Usuario u = buscar(entity.getId());
            if(u==null){
                pst = Conexao.getConnection().prepareStatement(sqlInsert);
            } else {
                pst = Conexao.getConnection().prepareStatement(sqlUpdate);
            }
            pst.setBoolean(1, true);
            pst.setLong(2, entity.getId());
            pst.setString(3, entity.getNome());
            pst.setString(4, entity.getEmail());
            pst.execute();
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Usuario excluir(Usuario entity) {
        String sql = "delete from USUARIO where id=?";
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setLong(1, entity.getId());
            pst.execute();
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Usuario buscar(long id) {
        String sql = "select U.* from USUARIO as U where id=?";
        try {
            PreparedStatement pst;
            pst = Conexao.getConnection().prepareStatement(sql);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            Usuario usuario = null;
            if (rs.next()){
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setAtivo(rs.getBoolean("ativo"));
            }
            return usuario;
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
