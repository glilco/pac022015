package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.Operacao;

/**
 *
 * @author Danillo
 */
public abstract class OperacaoFiltroTexto implements Operacao {

    public final static int COMECA_COM = 0;
    public final static int CONTEM = 1;
    public final static int IGUAL = 2;
    public final static int NAO_CONTEM = 3;
    public final static int TERMINA_COM = 4;

    private final String valor;

    public OperacaoFiltroTexto(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

}
