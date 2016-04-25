package br.ufg.inf.fabrica.pac.persistencia.transacao;

import br.ufg.inf.fabrica.pac.persistencia.imp.Conexao;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Danillo
 */
public class Transacao {

    private Connection connection;
    
    private Transacao(){
        
    }
    
    public static Transacao getInstance() throws SQLException{
        Transacao transacao = new Transacao();
        transacao.connection = Conexao.getConnection(true);
        return transacao;
    }
    
    public void confirmar() throws SQLException{
        connection.commit();
    }
    
    public void cancelar() throws SQLException{
        connection.rollback();
        connection.close();
    }
}
