package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.github.nikitakuchur.webboard.models.Board;

@ApplicationScoped
public class BoardDaoImpl implements BoardDao {

    @Inject
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Board board) {
        if (board == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(board);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Board findById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Board board = entityManager.find(Board.class, id);
        entityManager.close();
        return board;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Board> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Board> boards = entityManager.createQuery("From Board").getResultList();
        entityManager.close();
        return boards;
    }

    @Override
    public void update(Board board) {
        if (board == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(board);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Board board) {
        if (board == null) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        board = entityManager.merge(board);
        entityManager.remove(board);
        entityManager.getTransaction().commit();
    }
}
