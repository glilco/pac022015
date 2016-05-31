package br.ufg.inf.fabrica.pac.persistencia.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danillo
 */
public class UtilPersistencia {

    private UtilPersistencia() {

    }

    public static <T> List<T> populaObjetos(Class k, ResultSet rs) {
        List resposta = new ArrayList<>();
        try {
            while (rs.next()) {
                Constructor c = k.getConstructor();
                Object objeto = c.newInstance();
                for (Field field : k.getDeclaredFields()) {
                    Class classParam = field.getType();
                    String attrName = field.getName();
                    String capitular = attrName.substring(0, 1).toUpperCase() + 
                            attrName.substring(1, attrName.length());
                    String methodName = "set" + capitular;
                    Method method = k.getMethod(methodName, classParam);
                    getAttrValue(method, classParam, objeto, rs, attrName);

                }
                resposta.add(objeto);
            }
            return resposta;
        } catch (NoSuchMethodException | SQLException | 
                IllegalArgumentException | IllegalAccessException | InvocationTargetException |
                InstantiationException ex) {
            registraLogException(ex);
        }
        return new ArrayList<>();
    }

    public static <T> T populaObjeto(Class k, ResultSet rs) {
        try {
            Constructor c = k.getConstructor();
            Object objeto = c.newInstance();
            for (Field field : k.getDeclaredFields()) {
                Class classParam = field.getType();
                String attrName = field.getName();
                String capitular = attrName.substring(0, 1).toUpperCase() + 
                        attrName.substring(1, attrName.length());
                String methodName = "set" + capitular;
                Method method = k.getMethod(methodName, classParam);
                getAttrValue(method, classParam, objeto, rs, attrName);
            }
            return (T) objeto;
        } catch (NoSuchMethodException | IllegalArgumentException |
                IllegalAccessException | InvocationTargetException |
                InstantiationException ex) {
            registraLogException(ex);
        }
        return null;
    }

    private static void getAttrValue(Method method, Class classParam,
            Object objeto, ResultSet rs, String attrName)
            throws NoSuchMethodException, InvocationTargetException, 
            IllegalAccessException {
        try {
            if (classParam == String.class) {
                method.invoke(objeto, rs.getString(attrName));
            } else if (classParam == long.class) {
                method.invoke(objeto, rs.getLong(attrName));
            } else if (classParam == Date.class) {
                method.invoke(objeto, rs.getDate(attrName));
            } else if (classParam == boolean.class) {
                method.invoke(objeto, rs.getBoolean(attrName));
            } else if (classParam == int.class) {
                method.invoke(objeto, rs.getInt(attrName));
            }
        } catch (SQLException ex) {
            registraLogException(ex);
        }
    }
    
    public static void registraLogException(Exception ex){
        Logger.getLogger(UtilPersistencia.class.getName()).
                    log(Level.INFO, ex.getMessage());
    }
}
