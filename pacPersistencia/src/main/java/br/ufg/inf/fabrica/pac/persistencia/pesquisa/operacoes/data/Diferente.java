package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroData;
import java.util.Date;

/**
 *
 * @author Danillo
 */
public class Diferente extends OperacaoFiltroData {

    private static final String OPERADOR = "!=";

    public Diferente(Date valor) {
        this.setValor(valor);
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(OPERADOR).
                append(" '").
                append(getSqlDate()).
                append("'");
        return sb.toString();
    }
}
