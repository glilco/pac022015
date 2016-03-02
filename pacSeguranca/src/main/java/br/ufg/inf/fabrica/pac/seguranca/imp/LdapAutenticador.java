package br.ufg.inf.fabrica.pac.seguranca.imp;

import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.seguranca.ILdapAutenticador;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapAutenticador implements ILdapAutenticador {

    @Override
    public Usuario autenticar(Usuario user) {
        String login = user.getLogin();
        String senha = user.getSenha();

        return efetuaLogin(login, senha);
    }

    private Usuario efetuaLogin(String login, String senha) {
        String pathUser = "uid=" + login + ", ou=Users, dc=inf, dc=ufg, dc=br";
        Usuario usuarioRetorno = new Usuario();
        try {
            DirContext dir = this.getContext(pathUser, senha);
            usuarioRetorno = buscaDadosDoUsuario(login, dir);
        } catch (CommunicationException cex) {
            Logger.getLogger(LdapAutenticador.class.getName()).log(Level.SEVERE, null, cex);
            return null;
        } catch (NamingException nex) {
            Logger.getLogger(LdapAutenticador.class.getName()).log(Level.SEVERE, null, nex);
            return null;
        }
        return usuarioRetorno;
    }
//

    public DirContext getContext(String login, String senha) throws NamingException {
        Hashtable env = new Hashtable(11);
        ConexoesLdap.lerParametros();
        System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk1.7.0_25\\lib\\security\\cacerts.jks");
        env.put(Context.INITIAL_CONTEXT_FACTORY, ConexoesLdap.getINITIAL_CTX());
        env.put(Context.PROVIDER_URL, ConexoesLdap.getSERVIDOR_LDAP());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.SECURITY_PRINCIPAL, login);
        env.put(Context.SECURITY_CREDENTIALS, senha);

        return new InitialDirContext(env);
    }

    private Usuario buscaDadosDoUsuario(String login, DirContext ctx) {
        Usuario usuario = null;
        ConexoesLdap.lerParametros();
        String[] atributosRetorno = new String[]{"uidNumber", "mail", "cn", "shadowExpire"};
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(atributosRetorno);

        String searchFilter = "(&(objectClass=*)(uid=" + login + "))";

        try {
            NamingEnumeration resultado = ctx.search(
                    ConexoesLdap.getSEARCHBASE(), searchFilter, searchCtls);

            if (resultado.hasMore()) {
                SearchResult sr = (SearchResult) resultado.next();
                Attributes atributos = sr.getAttributes();

                //verifica se a conta do usuario expirou ( Valor 16302 DEFINIDO PELA EQUIPE DE REDES )
                if (atributos.get("shadowExpire") != null && atributos.get("shadowExpire").get().toString().equals("16302")) {
                    atributos.get("mail").set(1, "Usuario conta expirada!");
                    return null;
                }

                return constroiUsuario(atributos);

            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private Usuario constroiUsuario(Attributes atributos) throws NamingException {
        return new Usuario( Long.parseLong( getValorAtributo(atributos, "uidNumber")),
                getValorAtributo(atributos, "cn"),
                getValorAtributo(atributos, "mail")
        );
    }

    private String getValorAtributo(Attributes atributos, String nomeAtributo) throws NamingException {
        return atributos.get(nomeAtributo) == null ? null : atributos.get(nomeAtributo).get().toString();
    }
}
