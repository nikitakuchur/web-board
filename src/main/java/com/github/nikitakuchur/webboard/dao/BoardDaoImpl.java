package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import com.github.nikitakuchur.webboard.models.Board;

import static com.github.nikitakuchur.webboard.utils.Transactions.executeInTransaction;

@ApplicationScoped
public class BoardDaoImpl implements BoardDao {

    @Inject
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @Override
    public void save(Board board) {
        executeInTransaction(userTransaction, () -> entityManager.persist(board));
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
        executeInTransaction(userTransaction, () -> entityManager.merge(board));
    }

    @Override
    public void remove(Board board) {
        executeInTransaction(userTransaction, () -> {
            Board managedBoard = entityManager.merge(board);
            entityManager.remove(managedBoard);
        });
    }
}
