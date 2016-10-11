package br.ufg.inf.fabrica.pac.dominio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Danillo
 */
public class NewClass {

    public static void main(String[] args) {
        Entidade e = new Entidade();
        e.setNome("entidade");
        
        String pu = "PU-PAC";
        EntityManagerFactory factory = 
                Persistence.createEntityManagerFactory(pu);
        EntityManager em = factory.createEntityManager();
    }
}
