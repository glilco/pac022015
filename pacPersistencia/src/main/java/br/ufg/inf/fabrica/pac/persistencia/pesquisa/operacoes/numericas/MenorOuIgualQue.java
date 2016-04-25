package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroNumerico;

/**
 *
 * @author Danillo
 */
public class MenorOuIgualQue extends OperacaoFiltroNumerico{
    private final String operador = "<=";
    private final Number valor;

    public MenorOuIgualQue(Number valor) {
        this.valor = valor;
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(operador).append(valor);
        return sb.toString();
    }
}
