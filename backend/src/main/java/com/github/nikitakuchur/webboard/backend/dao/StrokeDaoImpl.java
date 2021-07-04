package com.github.nikitakuchur.webboard.backend.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.nikitakuchur.webboard.backend.models.Stroke;

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
