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

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }    
    
    public Projeto getProjetoSelecionado(long idProjeto){
        if(idProjeto==0 || projetos==null || projetos.isEmpty())
            return null;
        for (Projeto projeto : projetos) {
            if(projeto.getId()==idProjeto)
                return projeto;
        }
        return null;
    }
    
    public String dateToStr(Date date){
        return UtilVisao.converterDataParaString(date);
    }
}
