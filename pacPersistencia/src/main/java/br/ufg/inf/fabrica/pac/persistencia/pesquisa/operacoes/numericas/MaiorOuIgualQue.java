package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroNumerico;

/**
 *
 * @author Danillo
 */
public class MaiorOuIgualQue extends OperacaoFiltroNumerico{
    private final String operador = ">=";
    private final Number valor;

    public MaiorOuIgualQue(Number valor) {
        this.valor = valor;
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(operador).append(valor);
        return sb.toString();
    }
}
