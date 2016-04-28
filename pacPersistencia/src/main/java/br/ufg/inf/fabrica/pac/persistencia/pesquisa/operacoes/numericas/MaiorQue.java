package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroNumerico;

/**
 *
 * @author Danillo
 */
public class MaiorQue extends OperacaoFiltroNumerico {

    private static final String operador = ">";

    public MaiorQue(Number valor) {
        super(valor);
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(operador).append(getValor());
        return sb.toString();
    }
}
