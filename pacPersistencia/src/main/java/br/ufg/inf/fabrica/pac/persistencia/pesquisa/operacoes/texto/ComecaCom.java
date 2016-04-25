package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.texto;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroTexto;

/**
 *
 * @author Danillo
 */
public class ComecaCom extends OperacaoFiltroTexto{
    private final String operador = "like";
    private final String valor;
    
    public ComecaCom(String valor){
        this.valor = valor;
    }

    @Override
    public String getOperadorEValor() {
        StringBuilder sb = new StringBuilder();
        sb.append(operador).append(" '").append(valor).append("%'");
        return sb.toString();
    }
}
