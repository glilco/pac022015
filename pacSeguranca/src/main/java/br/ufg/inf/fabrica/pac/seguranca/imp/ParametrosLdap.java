package br.ufg.inf.fabrica.pac.seguranca.imp;

import br.ufg.inf.fabrica.pac.seguranca.excecoes.VariavelAmbienteNaoDefinidaException;
import br.ufg.inf.fabrica.pac.seguranca.utils.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author auf
 */
public class ParametrosLdap {

    private final String PATH_ADMIN;
    private final String KEY_ADMIN;
    private final String USER_ADMIN;
    private final String INITIAL_CTX;
    private final String SERVIDOR_LDAP;
    private final String SEARCHBASE;
    private final String BASE_DN;

    public ParametrosLdap(Properties prop){
        this.PATH_ADMIN = prop.getProperty("PATH_ADMIN");
        this.KEY_ADMIN = prop.getProperty("KEY_ADMIN");
        this.USER_ADMIN = prop.getProperty("USER_ADMIN");
        this.INITIAL_CTX = prop.getProperty("INITIAL_CTX");
        this.SERVIDOR_LDAP = prop.getProperty("SERVIDOR_LDAP");
        this.SEARCHBASE = prop.getProperty("SEARCHBASE");
        this.BASE_DN = prop.getProperty("BASE_DN");
    }

    public String getPATH_ADMIN() {
        return PATH_ADMIN;
    }

    public String getKEY_ADMIN() {
        return KEY_ADMIN;
    }

    public String getUSER_ADMIN() {
        return USER_ADMIN;
    }

    public String getINITIAL_CTX() {
        return INITIAL_CTX;
    }

    public String getSERVIDOR_LDAP() {
        return SERVIDOR_LDAP;
    }

    public String getSEARCHBASE() {
        return SEARCHBASE;
    }

    public String getBASE_DN() {
        return BASE_DN;
    }

}
