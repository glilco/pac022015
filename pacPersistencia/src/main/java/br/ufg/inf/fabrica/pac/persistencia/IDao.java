package br.ufg.inf.fabrica.pac.persistencia;

import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;

/**
 *
 * @author Danillo
 * @param <T>
 */
public interface IDao<T> {
    
    public T salvar(T entity, Transacao transacao);
    
    public T excluir(T entity, Transacao transacao);
    
    public T buscar(long id);
}
