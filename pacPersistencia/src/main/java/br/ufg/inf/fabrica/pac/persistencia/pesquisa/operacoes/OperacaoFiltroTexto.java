package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.Operacao;

/**
 *
 * @author Danillo
 */
public abstract class OperacaoFiltroTexto implements Operacao {

    public static final int COMECA_COM = 0;
    public static final int CONTEM = 1;
    public static final int IGUAL = 2;
    public static final int NAO_CONTEM = 3;
    public static final int TERMINA_COM = 4;

    private final String valor;

    public OperacaoFiltroTexto(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

}
