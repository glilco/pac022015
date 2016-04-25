package br.ufg.inf.fabrica.pac.persistencia;

import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.Pesquisa;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author danilloguimaraes
 */
public interface IDaoPacote extends IDao<Pacote>{
    public List pesquisar(Pesquisa pesquisa) throws SQLException;
}
