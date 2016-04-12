package br.ufg.inf.fabrica.pac.persistencia.stub;

import br.ufg.inf.fabrica.pac.dominio.Andamento;
import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.persistencia.IDaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.transacao.Transacao;
import java.util.List;

/**
 *
 * @author Danillo
 */
public class DaoAndamentoStub implements IDaoAndamento{

    @Override
    public Andamento salvar(Andamento andamento, Transacao transacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Andamento> andamentosPorPacote(Pacote pacote) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Andamento excluir(Andamento entity, Transacao transacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Andamento buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
