package br.ufg.inf.fabrica.pac.view.beans;

import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.view.apoio.util.UtilVisao;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Danillo
 */
public class BeanListagemProjetos {
    List<Projeto> projetos;
    
    public String dateToStr(Date date){
        return UtilVisao.converterDataParaString(date);
    }
}
