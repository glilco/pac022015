/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.pac.negocio;

import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;

/**
 *
 * @author auf
 */
public interface ICriarPacote {

    public Resposta<Pacote> criarPacote(Usuario autor, Pacote pacote, Projeto projetoSelecionado);

}