package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.nikitakuchur.webboard.models.Point;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ApplicationScoped
public class PointDaoImpl implements PointDao {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void save(Point point) {
        if (point == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(point);
            transaction.commit();
        }
    }

    @Override
    public Point findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Point.class, id);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Point> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Point").list();
        }
    }

    @Override
    public void update(Point point) {
        if (point == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(point);
            transaction.commit();
        }
    }

    @Override
    public void delete(Point point) {
        if (point == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(point);
            transaction.commit();
        }
    }
}
