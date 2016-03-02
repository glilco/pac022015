package br.ufg.inf.fabrica.pac.dominio.utils;

/**
 *
 * @author Danillo
 */
public class Utils {

    public static java.sql.Date convertUtilDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.util.Date convertSqlDateToUtilDate(java.sql.Date date) {
        return new java.util.Date(date.getTime());
    }
    
    //auf
    public static boolean stringVaziaOuNula(String value) {
        return value == null || value.isEmpty();
    }
}