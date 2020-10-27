package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.github.nikitakuchur.webboard.models.Point;

@ApplicationScoped
public class PointDaoImpl implements PointDao {

    @Inject
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Point point) {
        if (point == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(point);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Point findById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Point point = entityManager.find(Point.class, id);
        entityManager.close();
        return point;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Point> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Point> point = entityManager.createQuery("From Point").getResultList();
        entityManager.close();
        return point;
    }

    @Override
    public void update(Point point) {
        if (point == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(point);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Point point) {
        if (point == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        point = entityManager.merge(point);
        entityManager.remove(point);
        entityManager.getTransaction().commit();
    }
}
