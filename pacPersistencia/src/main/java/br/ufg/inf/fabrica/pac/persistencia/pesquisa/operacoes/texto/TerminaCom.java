package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.texto;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroTexto;

/**
 *
 * @author Danillo
 */
public class TerminaCom extends OperacaoFiltroTexto {

    private static final String OPERADOR = "like ";

    public TerminaCom(String valor) {
        super(valor);
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(OPERADOR).append(" '%").append(getValor()).append("'");
        return sb.toString();
    }
}
