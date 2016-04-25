package br.ufg.inf.fabrica.pac.persistencia.pesquisa;

import br.ufg.inf.fabrica.pac.persistencia.pesquisa.filtros.FiltroData;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.filtros.FiltroNumerico;
import br.ufg.inf.fabrica.pac.persistencia.pesquisa.filtros.FiltroTexto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Danillo
 */
public class Pesquisa {
    private final Class classe;
    private final List<Filtro> filtros;
    
    public Pesquisa(Class classe){
        this.classe = classe;
        this.filtros = new ArrayList<>();
    }
    
    public String construirConsulta(){
        StringBuilder sqlMain = new StringBuilder();
        sqlMain.append("select e.* from ").
                append(classe.getSimpleName()).
                append(" e");
        StringBuilder sqlFiltros = new StringBuilder();
        for (Filtro filtro : filtros) {
            adicionarFiltro(sqlFiltros, filtro);
        }
        
        StringBuilder sqlFinal = new StringBuilder();
        sqlFinal.append(sqlMain);
        if(sqlFiltros.length()>0){
            sqlFinal.append(" where ");
            sqlFinal.append(sqlFiltros);
        }
        return sqlFinal.toString();
    }
    
    private void adicionarFiltro(StringBuilder sqlFiltro, Filtro filtro){
        if(sqlFiltro.length()>0){
            sqlFiltro.append(" and ");
        }
        sqlFiltro.append(filtro.getConsultaFiltro());
    }

    public void adicionarFiltroTexto(String nomeCampo, 
            int operacaoTexto, String valor) {
        filtros.add(new FiltroTexto(nomeCampo, operacaoTexto, valor));
    }
    
    public void adicionarFiltroNumerico(String nomeCampo, 
            int operacaoNumerica, Number valor){
        filtros.add(new FiltroNumerico(nomeCampo, operacaoNumerica, valor));
    }
    
    public void adicionarFiltroData(String nomeCampo, 
            int operacaoNumerica, Date valor){
        filtros.add(new FiltroData(nomeCampo, operacaoNumerica, valor));
    }
}
