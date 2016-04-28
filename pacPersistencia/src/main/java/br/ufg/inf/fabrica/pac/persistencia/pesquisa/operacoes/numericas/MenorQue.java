package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroNumerico;

/**
 *
 * @author Danillo
 */
public class MenorQue extends OperacaoFiltroNumerico {

    private final String operador = "<";

    public MenorQue(Number valor) {
        super(valor);
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(operador).append(getValor());
        return sb.toString();
    }
}
