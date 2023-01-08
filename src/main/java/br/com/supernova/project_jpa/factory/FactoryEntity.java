package br.com.supernova.project_jpa.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryEntity {
    /* Nome de Unidade de Persistência (persistence-unit) definido no persistence.xml */
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("jpa_implement");

    public static EntityManager builderEntityManager(){
        return FACTORY.createEntityManager();

    }
}
