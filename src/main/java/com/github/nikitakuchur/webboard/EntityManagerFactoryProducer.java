package com.github.nikitakuchur.webboard;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerFactoryProducer {

    private EntityManagerFactoryProducer() {
    }

    @Produces
    @ApplicationScoped
    public static EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("WebBoardPersistence");
    }
}
