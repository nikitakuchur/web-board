package com.github.nikitakuchur.webboard;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.github.nikitakuchur.webboard.model.Point;
import com.github.nikitakuchur.webboard.model.Stroke;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@ApplicationScoped
public class SessionFactoryProducer {

    private SessionFactoryProducer() {
    }

    @Produces
    @ApplicationScoped
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Point.class);
        configuration.addAnnotatedClass(Stroke.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }
}
