/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.pac.negocio.utils;

import br.ufg.inf.fabrica.pac.dominio.Andamento;
import br.ufg.inf.fabrica.pac.dominio.Estado;
import br.ufg.inf.fabrica.pac.dominio.MembroProjeto;
import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembroProjeto;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoMembroProjeto;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auf
 */
public class UtilsNegocio {

    public static boolean UsuarioLogadoPossuiPapel(Usuario user, Projeto proj, String papel) {
        List<MembroProjeto> membroProjeto;
        IDaoMembroProjeto dao = new DaoMembroProjeto();
        membroProjeto = dao.buscar(proj, user);

        for (MembroProjeto p : membroProjeto) {
            if (p.getPapel().equals(papel)) {
                return true;
            }
        }
        return false;
    }

    public static Andamento criarAndamentoPacote(Pacote pacote, Usuario usuarioRemetente, Estado estado, Projeto projeto, Usuario usuarioDestinatario) {
        try {
            Andamento andamento = new Andamento(new Date(), pacote.getDataPrevistaRealizacao(), pacote.getDescricao(), pacote.getId(), estado.getId(), usuarioRemetente.getId(), usuarioDestinatario.getId());
            IDaoAndamento andamentoDao = new DaoAndamento();

            return andamentoDao.salvar(andamento);

        } catch (Exception ex) {
            Logger.getLogger(UtilsNegocio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}