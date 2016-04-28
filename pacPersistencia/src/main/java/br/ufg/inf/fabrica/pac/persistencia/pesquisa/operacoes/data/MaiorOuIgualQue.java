package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroData;
import java.util.Date;

/**
 *
 * @author Danillo
 */
public class MaiorOuIgualQue extends OperacaoFiltroData{
    private static final String operador = ">=";
    private final Date valor;

    public MaiorOuIgualQue(Date valor) {
        this.valor = valor;
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(operador).
                append(" '").
                append(valor).
                append("'");
        return sb.toString();
    }
}
