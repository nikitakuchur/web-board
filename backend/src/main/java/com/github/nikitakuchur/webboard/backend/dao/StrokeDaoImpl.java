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

    @Override
    public Stroke findById(int id) {
        return entityManager.find(Stroke.class, id);
    }
}
