package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroData;
import java.util.Date;

/**
 *
 * @author Danillo
 */
public class Diferente extends OperacaoFiltroData {

    private final String operador = "!=";

    public Diferente(Date valor) {
        this.setValor(valor);
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(operador).
                append(" '").
                append(getSqlDate()).
                append("'");
        return sb.toString();
    }
}
