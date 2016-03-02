package br.ufg.inf.fabrica.pac.negocio.imp;
 
import br.ufg.inf.fabrica.pac.negocio.IGestorMembros;
import br.ufg.inf.fabrica.pac.dominio.MembroProjeto;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembroProjeto;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoMembroProjeto;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GestorMembrosImpl implements IGestorMembros {

    @Override
    public Resposta<MembroProjeto> adicionarMembroProjeto(Usuario autor, 
            MembroProjeto membro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Resposta<MembroProjeto> removerMembroProjeto(Usuario autor, 
            MembroProjeto membro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Resposta<List<Usuario>> buscarUsuariosNaoMembros(Usuario autor, 
            Projeto projeto, String usuarioPesquisado){
        Resposta<List<Usuario>> resposta = new Resposta();
        if(projeto==null)
            resposta.addItemLaudo("Projeto não informado");
        if(usuarioPesquisado==null)
            usuarioPesquisado="";
        IDaoMembroProjeto dao = new DaoMembroProjeto();
        List<Usuario> usuarios;
        usuarios = dao.buscarUsuariosNaoMembrosPorProjeto(projeto.getId(), usuarioPesquisado).getChave();
        resposta.setChave(usuarios);
        return resposta;
    }

    @Override
    public Resposta<List<MembroProjeto>> buscarMembros(Usuario autor, 
            Projeto projeto) {
        Resposta<List<MembroProjeto>> resposta = new Resposta();
        if(projeto==null)
            resposta.addItemLaudo("Projeto não informado");
        IDaoMembroProjeto dao = new DaoMembroProjeto();
        List<MembroProjeto> membros = dao.buscarMembrosPorProjeto(projeto.getId()).getChave();
        resposta.setChave(membros);
        return resposta;
    }

    @Override
    public Resposta<List<MembroProjeto>> adicionarMembrosProjeto(
            Usuario autor, List<MembroProjeto> membros){
        Resposta<List<MembroProjeto>> resposta = new Resposta<>();
        for (MembroProjeto membro : membros) {
            if(membro.getIdProjeto()<=0)
                resposta.addItemLaudo("Informe o projeto do membro");
            if(membro.getIdUsuario()<=0)
                resposta.addItemLaudo("Informe o usuário do membro");
            if(membro.getPapel()==null || membro.getPapel().isEmpty())
                resposta.addItemLaudo("Informe o papel do membro");
        }
        IDaoMembroProjeto dao = new DaoMembroProjeto();
        try {
            resposta.setChave(dao.adicionarMembrosProjeto(membros));
        } catch (SQLException ex) {
            Logger.getLogger(GestorMembrosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resposta;
    }

    @Override
    public Resposta<String> atualizarPapeisDeUsuarioEmUmProjeto(Usuario autor, 
            List<MembroProjeto> papeisRemovidos, List<MembroProjeto> papeisAdicionados){
        Resposta<String> resposta = new Resposta<>();
        if( autor==null || autor.getId()==0)
            resposta.addItemLaudo("Informe autor da solicitação"); 
        
        if((papeisRemovidos==null || papeisRemovidos.isEmpty())&&
                ((papeisAdicionados==null)||papeisAdicionados.isEmpty())){
            resposta.addItemLaudo("Nenhum papel foi informado para atualização");
            return resposta;
        }
        long idUsuario = 0;
        long idProjeto = 0;
        for (MembroProjeto papelRemovido : papeisRemovidos) {
            if(idUsuario==0)
                idUsuario = papelRemovido.getIdUsuario();
            if(idProjeto==0)
                idProjeto = papelRemovido.getIdProjeto();
            if(idUsuario!=papelRemovido.getIdUsuario())
                resposta.addItemLaudo("A atualização deve conter papéis de somente um usuário em somente um projeto");
        }
        for (MembroProjeto papelAdicionado : papeisAdicionados) {
            if(idUsuario==0)
                idUsuario = papelAdicionado.getIdUsuario();
            if(idProjeto==0)
                idProjeto = papelAdicionado.getIdProjeto();
            if(idUsuario!=papelAdicionado.getIdUsuario())
                resposta.addItemLaudo("A atualização deve conter papéis de somente um usuário em somente um projeto");
        }
        if(resposta.isSucesso()){
            IDaoMembroProjeto dao = new DaoMembroProjeto();
            try {
                dao.atualizarPapeisDeUsuarioEmUmProjeto(papeisRemovidos, papeisAdicionados);
            } catch (SQLException ex) {
                Logger.getLogger(GestorMembrosImpl.class.getName()).log(Level.SEVERE, null, ex);
                resposta.addItemLaudo("Falha no sistema");
            }
            return resposta;
        } else {
            return resposta;
        }
    }
}