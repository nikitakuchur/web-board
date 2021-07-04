package com.github.nikitakuchur.webboard.backend.dao;

import com.github.nikitakuchur.webboard.backend.models.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.github.nikitakuchur.webboard.backend.utils.Transactions.executeInTransaction;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserDaoImpl implements UserDao {

    @Inject
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @Inject
    private Logger logger;

    @Override
    public void save(User user) {
        executeInTransaction(userTransaction, () -> {
            entityManager.persist(user);
            logger.log(Level.INFO, "Added a new user {0}.", new Object[]{user});
        });
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User findByName(String name) {
        return entityManager.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultStream()
                .findAny()
                .orElse(null);
    }
}
