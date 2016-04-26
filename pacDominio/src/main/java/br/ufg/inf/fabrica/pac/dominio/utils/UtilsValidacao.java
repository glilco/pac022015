package br.ufg.inf.fabrica.pac.dominio.utils;

/**
 *
 * @author Danillo
 */
public class UtilsValidacao {
    
    private UtilsValidacao(){
        
    }
    
    public static boolean isNullOrEmpty(String value){
        return value==null || value.isEmpty();
    }
}
