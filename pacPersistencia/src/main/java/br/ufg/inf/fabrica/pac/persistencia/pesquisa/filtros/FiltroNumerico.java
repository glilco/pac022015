package br.ufg.inf.fabrica.pac.persistencia.pesquisa.filtros;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.Filtro;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroNumerico;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas.Diferente;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas.Igual;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas.MaiorOuIgualQue;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas.MaiorQue;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas.MenorOuIgualQue;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.numericas.MenorQue;

/**
 *
 * @author Danillo
 */
public class FiltroNumerico extends Filtro{

    private final OperacaoFiltroNumerico operacao;
    private final String nomeCampo;

    public String getNomeCampo(){
        return nomeCampo;
    }
    
    public OperacaoFiltroNumerico getOperacao() {
        return operacao;
    }
    
    public FiltroNumerico(String nomeCampo, int operacao, Number valor){
        this.nomeCampo = nomeCampo;
        
        if(operacao==OperacaoFiltroNumerico.IGUAL){
            this.operacao = new Igual(valor);
        } else if(operacao==OperacaoFiltroNumerico.DIFERENTE){
            this.operacao = new Diferente(valor);
        } else if(operacao==OperacaoFiltroNumerico.MAIOR_QUE){
            this.operacao = new MaiorQue(valor);
        } else if(operacao==OperacaoFiltroNumerico.MAIOR_OU_IGUAL_QUE){
            this.operacao = new MaiorOuIgualQue(valor);
        } else if(operacao==OperacaoFiltroNumerico.MENOR_QUE){
            this.operacao = new MenorQue(valor);
        } else if(operacao==OperacaoFiltroNumerico.MENOR_OU_IGUAL_QUE){
            this.operacao = new MenorOuIgualQue(valor);
        } else {
            this.operacao = null;
            throw new IllegalArgumentException(
                    "Operação de filtro numérico inválido");
        }
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
