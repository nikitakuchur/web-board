package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.nikitakuchur.webboard.models.Board;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ApplicationScoped
public class BoardDaoImpl implements BoardDao {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void save(Board board) {
        if (board == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(board);
            transaction.commit();
        }
    }

    @Override
    public Board findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Board.class, id);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Board> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Board").list();
        }
    }

    @Override
    public void update(Board board) {
        if (board == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(board);
            transaction.commit();
        }
    }

    @Override
    public void delete(Board board) {
        if (board == null) return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(board);
            transaction.commit();
        }
    }
}
