/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.pac.negocio.imp;

import br.ufg.inf.fabrica.pac.negocio.AutenticacaoException;
import br.ufg.inf.fabrica.pac.negocio.IAutenticador;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoUsuario;
import br.ufg.inf.fabrica.pac.seguranca.ILdapAutenticador;
import br.ufg.inf.fabrica.pac.seguranca.imp.LdapAutenticador;

/**
 *
 * @author auf
 */
public class Autenticador implements IAutenticador {

    @Override
    public Usuario solicitarAutenticacao(Usuario usuario) throws AutenticacaoException {
        if (usuario == null) {
            return null;
        }
        if (Utils.stringVaziaOuNula(usuario.getLogin()) || Utils.stringVaziaOuNula(usuario.getSenha())) {
            throw new AutenticacaoException("Informe um usuário e uma senha para solicitar autenticação");
        }
        //Busca usuário no ldap
        ILdapAutenticador ldapAutenticador = new LdapAutenticador();
        Usuario u = ldapAutenticador.autenticar(usuario);

        if (u == null) {
            return null;
        }

        Usuario usuarioBanco = new Usuario();
        //Verifica na persistencia se o usuário esta ativo
        IDaoUsuario daoUsuario = new DaoUsuario();
        usuarioBanco = daoUsuario.buscar(u.getId());

        if (usuarioBanco != null) {
            u.setAtivo(usuarioBanco.isAtivo());
        }

        if (usuarioBanco == null) {
            daoUsuario.salvar(u);
            u.setAtivo(true);
        }
        if (!u.isAtivo()) {
            return null;
        }
        return u;
    }
}