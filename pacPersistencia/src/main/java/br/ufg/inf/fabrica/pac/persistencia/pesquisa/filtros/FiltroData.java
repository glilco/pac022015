package br.ufg.inf.fabrica.pac.persistencia.pesquisa.filtros;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.Filtro;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroData;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data.Diferente;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data.Igual;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data.MaiorOuIgualQue;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data.MaiorQue;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data.MenorOuIgualQue;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.data.MenorQue;
import java.util.Date;

/**
 *
 * @author Danillo
 */
public class FiltroData implements Filtro {

    private final OperacaoFiltroData operacao;
    private final String nomeCampo;

    public FiltroData(String nomeCampo, int operacao, Date valor) {
        this.nomeCampo = nomeCampo;

        if (operacao == OperacaoFiltroData.IGUAL) {
            this.operacao = new Igual(valor);
        } else if (operacao == OperacaoFiltroData.DIFERENTE) {
            this.operacao = new Diferente(valor);
        } else if (operacao == OperacaoFiltroData.MAIOR_QUE) {
            this.operacao = new MaiorQue(valor);
        } else if (operacao == OperacaoFiltroData.MAIOR_OU_IGUAL_QUE) {
            this.operacao = new MaiorOuIgualQue(valor);
        } else if (operacao == OperacaoFiltroData.MENOR_QUE) {
            this.operacao = new MenorQue(valor);
        } else if (operacao == OperacaoFiltroData.MENOR_OU_IGUAL_QUE) {
            this.operacao = new MenorOuIgualQue(valor);
        } else {
            this.operacao = null;
            throw new IllegalArgumentException(
                    "Operação de filtro numérico inválido");
        }
    }

    public String getNomeCampo() {
        return nomeCampo;
    }

    public OperacaoFiltroData getOperacao() {
        return operacao;
    }

    @Override
    public String getConsultaFiltro() {
        StringBuilder sb = new StringBuilder();
        sb.append("e.").
                append(this.nomeCampo).
                append(operacao.getOperadorEValor());
        return sb.toString();
    }

}
