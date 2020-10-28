package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import com.github.nikitakuchur.webboard.models.Stroke;

import static com.github.nikitakuchur.webboard.utils.Transactions.executeInTransaction;

@ApplicationScoped
public class StrokeDaoImpl implements StrokeDao {

    @Inject
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @Override
    public void save(Stroke stroke) {
        executeInTransaction(userTransaction, () -> entityManager.persist(stroke));
    }

    @Override
    public Stroke findById(int id) {
        return entityManager.find(Stroke.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Stroke> findAll() {
        return entityManager.createQuery("From Stroke").getResultList();
    }

    @Override
    public void update(Stroke stroke) {
        executeInTransaction(userTransaction, () -> entityManager.merge(stroke));
    }

    @Override
    public void remove(Stroke stroke) {
        executeInTransaction(userTransaction, () -> {
            Stroke managedStroke = entityManager.merge(stroke);
            entityManager.remove(managedStroke);
        });
    }
}
