package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.texto;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroTexto;

/**
 *
 * @author Danillo
 */
public class NaoContem extends OperacaoFiltroTexto {

    private static final String operador = "not like ";

    public NaoContem(String valor) {
        super(valor);
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(operador).append(" '%").append(getValor()).append("%'");
        return sb.toString();
    }
}
