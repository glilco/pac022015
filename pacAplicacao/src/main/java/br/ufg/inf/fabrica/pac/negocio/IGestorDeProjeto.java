package br.ufg.inf.fabrica.pac.negocio;

import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import java.util.List;

/**
 *
 * @author Danillo
 */
public interface IGestorDeProjeto {
    public Resposta<Projeto> criar(Usuario autor, Projeto projeto);
    
    public Resposta<List<Projeto>> buscarTodos();
}
