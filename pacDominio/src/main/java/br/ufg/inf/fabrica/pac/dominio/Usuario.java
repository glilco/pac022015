package br.ufg.inf.fabrica.pac.dominio;

import br.ufg.inf.fabrica.pac.dominio.utils.Utils;
import br.ufg.inf.fabrica.pac.dominio.utils.UtilsValidacao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danilloguimaraes
 */
public class Usuario implements Validavel{

    //Id não é gerado automaticamente, ele é gerado pelo ldap
    private long id;
    private boolean ativo;

    //Transient
    private String login;
    private String senha;
    private String nome;
    private String email;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public void setAtivo(boolean value) {
        this.ativo = value;
    }

    @Override
    public List<String> validar() {
        List<String> inconsistencias = new ArrayList<>();
        return inconsistencias;
    }
}
