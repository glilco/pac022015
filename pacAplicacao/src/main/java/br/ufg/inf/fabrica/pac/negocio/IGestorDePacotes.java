package br.ufg.inf.fabrica.pac.negocio;

import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import java.util.List;

/**
 *
 * @author auf
 */
public interface IGestorDePacotes {

    public Resposta<Pacote> criar(Usuario autor, Pacote pacote, 
            Projeto projetoSelecionado);
    
    public Resposta<List<Pacote>> pesquisarPacotesNovos(Usuario autor,
            long projetoSelecionado);

}