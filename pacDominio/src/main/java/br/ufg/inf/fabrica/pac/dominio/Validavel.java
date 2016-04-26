package br.ufg.inf.fabrica.pac.dominio;

import java.util.List;

/**
 *
 * @author Danillo
 */
@FunctionalInterface
public interface Validavel {
    
    /**
     * Confrontar objeto com regras semânticas definidas nos conceitos dos negócios.
     * Se não houver inconsistências semânticas, a lista deve ser retornada vazia.
     * @return 
     */
    public List<String> validar();
    
}
