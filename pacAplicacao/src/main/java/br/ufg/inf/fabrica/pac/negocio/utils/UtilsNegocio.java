/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.pac.negocio.utils;

import br.ufg.inf.fabrica.pac.dominio.Membro;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoMembro;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoMembro;
import java.util.Date;
import java.util.List;

/**
 *
 * @author auf
 */
public class UtilsNegocio {

    public static Resposta criarRespostaComErro(String mensagemErro) {
        Resposta resposta = new Resposta();
        resposta.addItemLaudo(mensagemErro);
        return resposta;
    }

    public static Resposta criarRespostaComErro(List<String> mensagemsErro) {
        Resposta resposta = new Resposta();
        resposta.addItemLaudo(mensagemsErro);
        return resposta;
    }

    public static <T> Resposta<T> criarRespostaValida(T value) {
        Resposta resposta = new Resposta();
        resposta.setChave(value);
        return resposta;
    }

    public static boolean UsuarioLogadoPossuiPapel(Usuario user, Projeto proj, 
            String papel) {
        List<Membro> membroProjeto;
        IDaoMembro dao = new DaoMembro();
        membroProjeto = dao.buscar(proj, user);

        for (Membro p : membroProjeto) {
            if (p.getPapel().equals(papel)) {
                return true;
            }
        }
        return false;
    }

    public static Date buscarDataAtual() {
        return new Date();
    }

}
