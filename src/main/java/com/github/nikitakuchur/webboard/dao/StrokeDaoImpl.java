package com.github.nikitakuchur.webboard.dao;

import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.github.nikitakuchur.webboard.models.Stroke;

@ApplicationScoped
public class StrokeDaoImpl implements StrokeDao {

    @Inject
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Stroke stroke) {
        if (stroke == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(stroke);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void saveAll(Collection<Stroke> strokes) {
        if (strokes == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        strokes.forEach(entityManager::persist);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Stroke findById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Stroke stroke = entityManager.find(Stroke.class, id);
        entityManager.close();
        return stroke;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Stroke> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Stroke> strokes = entityManager.createQuery("From Stroke").getResultList();
        entityManager.close();
        return strokes;
    }

    @Override
    public void update(Stroke stroke) {
        if (stroke == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(stroke);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Stroke stroke) {
        if (stroke == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        stroke = entityManager.merge(stroke);
        entityManager.remove(stroke);
        entityManager.getTransaction().commit();
    }
}
