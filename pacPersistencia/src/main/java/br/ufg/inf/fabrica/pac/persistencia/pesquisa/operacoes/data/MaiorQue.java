package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroData;
import java.util.Date;

/**
 *
 * @author Danillo
 */
public class MaiorQue extends OperacaoFiltroData {

    private static final String OPERADOR = ">";
    private final Date valor;

    public MaiorQue(Date valor) {
        this.valor = valor;
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(OPERADOR).
                append(" '").
                append(valor).
                append("'");
        return sb.toString();
    }
}
