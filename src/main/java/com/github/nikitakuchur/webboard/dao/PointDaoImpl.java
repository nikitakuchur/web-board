package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import com.github.nikitakuchur.webboard.models.Point;

import static com.github.nikitakuchur.webboard.utils.Transactions.*;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PointDaoImpl implements PointDao {

    @Inject
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @Override
    public void save(Point point) {
        executeInTransaction(userTransaction, () -> entityManager.persist(point));
    }

    @Override
    public Point findById(int id) {
        return entityManager.find(Point.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Point> findAll() {
        return entityManager.createQuery("From Point").getResultList();
    }

    @Override
    public void update(Point point) {
        executeInTransaction(userTransaction, () -> entityManager.merge(point));
    }

    @Override
    public void remove(Point point) {
        executeInTransaction(userTransaction, () -> {
            Point managedPoint = entityManager.merge(point);
            entityManager.remove(managedPoint);
        });
    }
}
