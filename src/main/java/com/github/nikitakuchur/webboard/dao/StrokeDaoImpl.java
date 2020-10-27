package com.github.nikitakuchur.webboard.dao;

import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.nikitakuchur.webboard.models.Stroke;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ApplicationScoped
public class StrokeDaoImpl implements StrokeDao {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void save(Stroke stroke) {
        if (stroke == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(stroke);
            transaction.commit();
        }
    }

    @Override
    public void saveAll(Collection<Stroke> strokes) {
        if (strokes == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            strokes.forEach(session::save);
            transaction.commit();
        }
    }

    @Override
    public Stroke findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Stroke.class, id);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Stroke> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Stroke").list();
        }
    }

    @Override
    public void update(Stroke stroke) {
        if (stroke == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(stroke);
            transaction.commit();
        }
    }

    @Override
    public void delete(Stroke stroke) {
        if (stroke == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(stroke);
            transaction.commit();
        }
    }
}
