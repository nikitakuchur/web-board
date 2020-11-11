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

import com.github.nikitakuchur.webboard.backend.models.Board;

import static com.github.nikitakuchur.webboard.backend.utils.Transactions.executeInTransaction;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BoardDaoImpl implements BoardDao {

    @Inject
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @Inject
    private Logger logger;

    @Override
    public void save(Board board) {
        executeInTransaction(userTransaction, () -> {
            entityManager.persist(board);
            logger.log(Level.INFO, "Added a new board {0}.", new Object[]{board});
        });
    }

    @Override
    public Board findById(int id) {
        return entityManager.find(Board.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Board> findAll() {
        return entityManager.createQuery("From Board").getResultList();
    }

    @Override
    public void update(Board board) {
        executeInTransaction(userTransaction, () -> {
            entityManager.merge(board);
            logger.log(Level.INFO, "Updated the board {0}.", new Object[]{board});
        });
    }

    @Override
    public void remove(Board board) {
        executeInTransaction(userTransaction, () -> {
            Board managedBoard = entityManager.merge(board);
            entityManager.remove(managedBoard);
            logger.log(Level.INFO, "Removed the board {0}.", new Object[]{board});
        });
    }
}
