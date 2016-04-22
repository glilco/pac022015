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
public class ConexaoLdap {

    private static String PATH_ADMIN;
    private static String KEY_ADMIN;
    private static String USER_ADMIN;
    private static String INITIAL_CTX;
    private static String SERVIDOR_LDAP;
    private static String SEARCHBASE;
    private static String BASE_DN;

    public static void lerParametros()
            throws VariavelAmbienteNaoDefinidaException, FileNotFoundException, IOException {
        String pathSistema
                = System.getenv(Constantes.VARIAVEL_AMBIENTE_ARQUIVO_LDAP_PROPERTIES);

        if (pathSistema == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Variável de ambiente ").
                    append(Constantes.VARIAVEL_AMBIENTE_ARQUIVO_LDAP_PROPERTIES).
                    append(" não definida");
            throw new VariavelAmbienteNaoDefinidaException(sb.toString());
        }

        File file = new File(pathSistema);
        InputStream input = null;
        input = new FileInputStream(file);
        Properties prop = new Properties();
        prop.load(input);

        ConexaoLdap.setPATH_ADMIN(prop.getProperty("PATH_ADMIN"));
        ConexaoLdap.setKEY_ADMIN(prop.getProperty("KEY_ADMIN"));
        ConexaoLdap.setUSER_ADMIN(prop.getProperty("USER_ADMIN"));
        ConexaoLdap.setINITIAL_CTX(prop.getProperty("INITIAL_CTX"));
        ConexaoLdap.setSERVIDOR_LDAP(prop.getProperty("SERVIDOR_LDAP"));
        ConexaoLdap.setSEARCHBASE(prop.getProperty("SEARCHBASE"));
        ConexaoLdap.setBASE_DN(prop.getProperty("BASE_DN"));

    }

    public static void setPATH_ADMIN(String PATH_ADMIN) {
        ConexaoLdap.PATH_ADMIN = PATH_ADMIN;
    }

    public static void setKEY_ADMIN(String KEY_ADMIN) {
        ConexaoLdap.KEY_ADMIN = KEY_ADMIN;
    }

    public static void setUSER_ADMIN(String USER_ADMIN) {
        ConexaoLdap.USER_ADMIN = USER_ADMIN;
    }

    public static void setINITIAL_CTX(String INITIAL_CTX) {
        ConexaoLdap.INITIAL_CTX = INITIAL_CTX;
    }

    public static void setSERVIDOR_LDAP(String SERVIDOR_LDAP) {
        ConexaoLdap.SERVIDOR_LDAP = SERVIDOR_LDAP;
    }

    public static void setSEARCHBASE(String SEARCHBASE) {
        ConexaoLdap.SEARCHBASE = SEARCHBASE;
    }

    public static void setBASE_DN(String BASE_DN) {
        ConexaoLdap.BASE_DN = BASE_DN;
    }

    public static String getPATH_ADMIN() {
        return PATH_ADMIN;
    }

    public static String getKEY_ADMIN() {
        return KEY_ADMIN;
    }

    public static String getUSER_ADMIN() {
        return USER_ADMIN;
    }

    public static String getINITIAL_CTX() {
        return INITIAL_CTX;
    }

    public static String getSERVIDOR_LDAP() {
        return SERVIDOR_LDAP;
    }

    public static String getSEARCHBASE() {
        return SEARCHBASE;
    }

    public static String getBASE_DN() {
        return BASE_DN;
    }

}
