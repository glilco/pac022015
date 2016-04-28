package br.ufg.inf.fabrica.pac.persistencia.pesquisa.filtros;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.Filtro;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.OperacaoFiltroTexto;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.texto.ComecaCom;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.texto.Contem;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.texto.Igual;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.texto.NaoContem;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes.texto.TerminaCom;

/**
 *
 * @author Danillo
 */
public class FiltroTexto implements Filtro{

    private final OperacaoFiltroTexto operacao;
    private final String nomeCampo;

    public String getNomeCampo(){
        return nomeCampo;
    }
    
    public OperacaoFiltroTexto getOperacao() {
        return operacao;
    }
    
    public FiltroTexto(String nomeCampo, int operacao, String valor){
        if(nomeCampo==null || nomeCampo.trim().isEmpty()){
            throw new IllegalArgumentException("Valor vazio ou não informado");
        }
        this.nomeCampo = nomeCampo;
        
        if(operacao==OperacaoFiltroTexto.COMECA_COM){
            this.operacao = new ComecaCom(valor);
        } else if(operacao==OperacaoFiltroTexto.CONTEM){
            this.operacao = new Contem(valor);
        } else if(operacao==OperacaoFiltroTexto.IGUAL){
            this.operacao = new Igual(valor);
        } else if(operacao==OperacaoFiltroTexto.NAO_CONTEM){
            this.operacao = new NaoContem(valor);
        } else if(operacao==OperacaoFiltroTexto.TERMINA_COM){
            this.operacao = new TerminaCom(valor);
        } else {
            this.operacao = null;
            throw new IllegalArgumentException(
                    "Operação de filtro de texto inválido");
        }
    }
    
    @Override
    public String getConsultaFiltro() {
        StringBuilder sb = new StringBuilder();
        sb.append("e.").
                append(this.nomeCampo).
                append(" ").
                append(operacao.getOperadorEValor());
        return sb.toString();
    }
    
}
