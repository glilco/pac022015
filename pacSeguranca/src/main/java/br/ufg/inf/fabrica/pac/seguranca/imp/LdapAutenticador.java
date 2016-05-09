package br.ufg.inf.fabrica.pac.seguranca.imp;

import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.seguranca.ILdapAutenticador;
import br.ufg.inf.fabrica.pac.seguranca.excecoes.VariavelAmbienteNaoDefinidaException;
import br.ufg.inf.fabrica.pac.seguranca.utils.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapAutenticador implements ILdapAutenticador {

    private DirContext ctxt = null;
    private final long id;
    private final String nome;
    private final String email;
    private final boolean credencialValida;

    private Attributes atributos = null;

    public LdapAutenticador(Properties prop, String login, String senha)
            throws VariavelAmbienteNaoDefinidaException, IOException,
            NamingException {
        if (Utils.stringVaziaOuNula(login)) {
            throw new IllegalArgumentException("Login não informado");
        }
        if (Utils.stringVaziaOuNula(senha)) {
            throw new IllegalArgumentException("Senha não informada");
        }

        ParametrosLdap parametros = new ParametrosLdap(prop);
        if (ctxt == null) {
            ctxt = this.getContext(parametros, login, senha);
        }
        boolean sucesso = confereDadosInformados(parametros, login, ctxt);
        if (sucesso) {
            this.id = Long.parseLong(getValorAtributo(atributos, "uidNumber"));
            this.nome = getValorAtributo(atributos, "cn");
            this.email = getValorAtributo(atributos, "mail");
            this.credencialValida = true;
        } else {
            this.id = 0;
            this.nome = null;
            this.email = null;
            this.credencialValida = false;
        }
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean isCredencialValida() {
        return this.credencialValida;
    }

    private DirContext getContext(ParametrosLdap parametros, String login, String senha)
            throws NamingException {
        Hashtable env = new Hashtable(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, parametros.getINITIAL_CTX());
        env.put(Context.PROVIDER_URL, parametros.getSERVIDOR_LDAP());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.SECURITY_PRINCIPAL, "uid=" + login
                + ", ou=Users, dc=inf, dc=ufg, dc=br");
        env.put(Context.SECURITY_CREDENTIALS, senha);

        return new InitialDirContext(env);
    }

    private boolean confereDadosInformados(ParametrosLdap parametros, String login, DirContext ctx) throws NamingException {
        String[] atributosRetorno
                = new String[]{"uidNumber", "mail", "cn", "shadowExpire"};
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(atributosRetorno);

        String searchFilter = "(&(objectClass=*)(uid=" + login + "))";

        NamingEnumeration resultado = ctx.search(
                parametros.getSEARCHBASE(), searchFilter, searchCtls);

        if (resultado.hasMore()) {
            SearchResult sr = (SearchResult) resultado.next();
            this.atributos = sr.getAttributes();

            //verifica se a conta do usuario expirou ( Valor 16302 DEFINIDO PELA EQUIPE DE REDES )
            if (atributos.get("shadowExpire") == null
                    || !atributos.get("shadowExpire").get().toString().
                    equals("16302")) {
                return true;
            }
        }
        return false;
    }

    private String getValorAtributo(Attributes atributos, String nomeAtributo) throws NamingException {
        return atributos.get(nomeAtributo) == null ? null : atributos.get(nomeAtributo).get().toString();
    }
}
