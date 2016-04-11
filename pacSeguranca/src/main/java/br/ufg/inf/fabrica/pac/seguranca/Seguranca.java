package br.ufg.inf.fabrica.pac.seguranca;

import java.util.List;

/**
 *
 * @author Danillo
 */
public interface Seguranca {
    
    public boolean autorizar(String recurso, List<String> papeis);
}
