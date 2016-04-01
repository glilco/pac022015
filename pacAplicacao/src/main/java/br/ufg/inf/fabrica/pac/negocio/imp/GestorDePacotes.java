package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.negocio.ICriarPacote;
import br.ufg.inf.fabrica.pac.dominio.Estado;
import br.ufg.inf.fabrica.pac.dominio.MembroProjeto;
import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.PapelProjeto;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.negocio.utils.UtilsNegocio;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoEstado;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoPacote;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auf
 */
public class GestorDePacotes implements ICriarPacote {
    
    private static GestorDePacotes gestor;
    
    private GestorDePacotes(){
        
    }
    
    public static GestorDePacotes getInstance(){
        if(gestor==null){
            gestor = new GestorDePacotes();
        }
        return gestor;
    }

    @Override
    public Resposta<Pacote> criarPacote(Usuario autor, Pacote pacote, Projeto projetoSelecionado) {
        IDaoMembro daoMembro = new DaoMembro();
        try {
            List<MembroProjeto> papeis = daoMembro.buscarPapeis(autor.getId());
            System.out.println(papeis);
        } catch (SQLException ex) {
            Logger.getLogger(GestorDePacotes.class.getName()).log(Level.SEVERE, null, ex);
            Resposta resposta = new Resposta();
            resposta.addItemLaudo("Falha no sistema");
            return resposta;
        }
        
        
        Resposta<Pacote> resp = new Resposta<Pacote>();
        //Verificar se o usuario pertence ao projeto e neste tem o perfil GPR .
        List<MembroProjeto> membroProjeto;

        IDaoMembro dao = new DaoMembro();

        membroProjeto = dao.buscar(projetoSelecionado, autor);

        if (membroProjeto != null && membroProjeto.size() > 0) {
            for (MembroProjeto mP : membroProjeto) {
                if (!UtilsNegocio.UsuarioLogadoPossuiPapel(autor, projetoSelecionado, PapelProjeto.GPR.name())) {
                    resp.setChave(null);
                    resp.addItemLaudo("Usuario logado não possui permissão para criar pacotes nesse projeto!");
                    return resp;
                }
            }
        } else {
            resp.setChave(null);
            resp.addItemLaudo("Usuario logado não possui permissão para criar pacotes nesse projeto!");
            return resp;
        }

        String hojeString = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        Date hoje = null;
        try {
            hoje = new SimpleDateFormat("dd/MM/yyyy").parse(hojeString);
        } catch (ParseException ex) {
            Logger.getLogger(GestorDePacotes.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (pacoteTemCampoVazio(pacote)) {
            resp.setChave(null);
            resp.addItemLaudo("Pacote com campos vazios!");
            return resp;
        }
        if (hoje == null || pacote.getDataPrevistaRealizacao().compareTo(hoje) < 0) {
            resp.setChave(null);
            resp.addItemLaudo("Data Prevista Realização deve ser maior ou igual a data atual!");
            return resp;
        }

        pacote.setAbandonado(false);
        pacote.setDataCriacao(hoje);
        pacote.setIdProjeto(projetoSelecionado.getId());

        Estado estado = DaoEstado.buscarPorNome("novo");
        pacote.setIdEstado(estado.getId());
        pacote.setIdUsuario(autor.getId());
        DaoPacote pacoteDao = new DaoPacote();
        try {
            pacote = pacoteDao.salvar(pacote);
            //criar o andamento
            if (UtilsNegocio.criarAndamentoPacote(pacote, autor, estado, projetoSelecionado, autor) == null) {
                //Caso não seja possível criar o Andamento, o pacote será apagado, pois não pode existir pacote sem andamento
                pacoteDao.excluir(pacote);
                resp.setChave(null);
                resp.addItemLaudo("Erro de Sistema: Pacote não podê ser criado no banco!");
            } else {
                resp.setChave(pacote);
                resp.addItemLaudo("Pacote criado com sucesso!");
            }
            return resp;
        } catch (Exception ex) {
            Logger.getLogger(DaoPacote.class.getName()).log(Level.SEVERE, null, ex);
            resp.setChave(null);
            resp.addItemLaudo("Erro de Sistema: Pacote não podê ser criado no banco!");
            return resp;
        }
    }

    public boolean pacoteTemCampoVazio(Pacote pacote) {
        if (Utils.stringVaziaOuNula(pacote.getNome()) || Utils.stringVaziaOuNula(pacote.getDescricao())
                || Utils.stringVaziaOuNula(pacote.getDocumento())) {
            return true;
        }
        return false;
    }

}
