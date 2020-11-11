package com.github.nikitakuchur.webboard.backend.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import com.github.nikitakuchur.webboard.backend.models.Stroke;

import static com.github.nikitakuchur.webboard.backend.utils.Transactions.executeInTransaction;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class StrokeDaoImpl implements StrokeDao {

    @Inject
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @Inject
    private Logger logger;

    @Override
    public void save(Stroke stroke) {
        executeInTransaction(userTransaction, () -> {
            entityManager.persist(stroke);
            logger.log(Level.INFO, "Added a new stroke {0}.", new Object[]{stroke});
        });
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
        executeInTransaction(userTransaction, () -> {
            entityManager.merge(stroke);
            logger.log(Level.INFO, "Updated the stroke {0}.", new Object[]{stroke});
        });
    }

    @Override
    public void remove(Stroke stroke) {
        executeInTransaction(userTransaction, () -> {
            Stroke managedStroke = entityManager.merge(stroke);
            entityManager.remove(managedStroke);
            logger.log(Level.INFO, "Removed the stroke {0}.", new Object[]{stroke});
        });
    }
}
