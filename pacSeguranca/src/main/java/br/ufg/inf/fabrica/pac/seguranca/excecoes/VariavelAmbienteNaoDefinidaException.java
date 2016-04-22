package br.ufg.inf.fabrica.pac.seguranca.excecoes;

/**
 *
 * @author Danillo
 */
public class VariavelAmbienteNaoDefinidaException extends Exception {

    /**
     * Creates a new instance of
     * <code>VariavelAmbienteNaoDefinidaException</code> without detail message.
     */
    public VariavelAmbienteNaoDefinidaException() {
    }

    /**
     * Constructs an instance of
     * <code>VariavelAmbienteNaoDefinidaException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public VariavelAmbienteNaoDefinidaException(String msg) {
        super(msg);
    }
}
