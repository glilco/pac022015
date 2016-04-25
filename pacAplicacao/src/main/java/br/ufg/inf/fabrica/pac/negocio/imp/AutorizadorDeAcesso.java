package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoMembro;
import br.ufg.inf.fabrica.pac.seguranca.Seguranca;
import br.ufg.inf.fabrica.pac.seguranca.imp.SegurancaStub;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danillo
 */
public class AutorizadorDeAcesso {

    private AutorizadorDeAcesso() {

    }

    public static boolean autorizar(String recurso, Usuario usuario, Projeto projeto) 
            throws SQLException {
        IDaoMembro daoMembro = new DaoMembro();
        List<Membro> papeis;
        List<String> nomePapeis = new ArrayList<>();
        papeis = daoMembro.buscar(projeto, usuario);
        
        for (Membro membro : papeis) {
            nomePapeis.add(membro.getPapel());
        }
        Seguranca seguranca = SegurancaStub.getInstance();
        if (!seguranca.autorizar(recurso, nomePapeis)) {
            return false;
        }
        return true;
    }
}
