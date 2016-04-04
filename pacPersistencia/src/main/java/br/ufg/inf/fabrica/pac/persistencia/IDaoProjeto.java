package br.ufg.inf.fabrica.pac.persistencia;

import br.ufg.inf.fabrica.pac.dominio.Projeto;
import java.util.List;

/**
 *
 * @author Danillo
 */
public interface IDaoProjeto extends IDao<Projeto>{
    
        public List<Projeto> buscarTodos();
        
}
