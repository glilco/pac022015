package br.ufg.inf.fabrica.pac.negocio;

/**
 *
 * @author Danillo
 */
public class AutenticacaoException extends Exception {

    /**
     * Creates a new instance of <code>AutenticacaoException</code> without
     * detail message.
     */
    public AutenticacaoException() {
    }

    /**
     * Constructs an instance of <code>AutenticacaoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AutenticacaoException(String msg) {
        super(msg);
    }
}
