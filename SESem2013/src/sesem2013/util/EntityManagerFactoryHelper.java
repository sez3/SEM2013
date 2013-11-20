package sesem2013.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryHelper {

    private static EntityManagerFactory emf;

    static {
        try {
    		emf=Persistence.createEntityManagerFactory("SESemPersistenceUnit");

        } catch(ExceptionInInitializerError e) {
            throw e;
        }
    }

    public static EntityManagerFactory getFactory() {
        return emf;
    }

}