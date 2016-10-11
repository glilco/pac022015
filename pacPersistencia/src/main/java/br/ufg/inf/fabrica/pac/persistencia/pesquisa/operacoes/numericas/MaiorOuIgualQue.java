package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroNumerico;

/**
 *
 * @author Danillo
 */
public class MaiorOuIgualQue extends OperacaoFiltroNumerico {

    private static final String OPERADOR = ">=";

    public MaiorOuIgualQue(Number valor) {
        super(valor);
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(OPERADOR).append(getValor());
        return sb.toString();
    }
}
