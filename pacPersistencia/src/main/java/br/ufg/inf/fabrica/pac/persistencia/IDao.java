package br.ufg.inf.fabrica.pac.persistencia;

import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import java.sql.SQLException;

/**
 *
 * @author Danillo
 * @param <T>
 */
public interface IDao<T> {
    
    public T salvar(T entity, Transacao transacao)throws SQLException;
    
    public T excluir(T entity, Transacao transacao)throws SQLException;
    
    public T buscar(long id) throws SQLException;
}
