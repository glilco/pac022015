package br.ufg.inf.fabrica.pac.view.apoio.lifecycle;

import br.ufg.inf.fabrica.pac.view.apoio.AtributosConfiguracao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Danillo
 */
@WebListener
public class ServletContextListenerPropertiesLoader 
    implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String pathProperties = System.getenv(
                AtributosConfiguracao.VARIAVEL_CAMINHO_PROPRIEDADES);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(pathProperties);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServletContextListenerPropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(fis!=null){
            Properties properties = new Properties();
            try {
                properties.load(fis);
                sce.getServletContext().setAttribute(
                        AtributosConfiguracao.VARIAVEL_PROPRIEDADES, properties);
            } catch (IOException ex) {
                Logger.getLogger(ServletContextListenerPropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
