package br.ufg.inf.fabrica.pac.persistencia.pesquisa.operacoes;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.Operacao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Danillo
 */
public abstract class OperacaoFiltroData implements Operacao{
    public static final int IGUAL = 0;
    public static final int DIFERENTE = 1;
    public static final int MAIOR_QUE = 2;
    public static final int MAIOR_OU_IGUAL_QUE = 3;
    public static final int MENOR_QUE = 4;
    public static final int MENOR_OU_IGUAL_QUE = 5;
    
    private Date valor;

    public void setValor(Date valor) {
        this.valor = valor;
    }
    
    public String getSqlDate(){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(valor);
    }
}
