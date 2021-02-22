package com.github.nikitakuchur.webboard.backend.services;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

import com.github.nikitakuchur.webboard.backend.dao.BoardDao;
import com.github.nikitakuchur.webboard.backend.dao.StrokeDao;
import com.github.nikitakuchur.webboard.backend.models.Board;
import com.github.nikitakuchur.webboard.backend.models.Stroke;

@ApplicationScoped
public class BoardServiceImpl implements BoardService {

    @EJB
    private BoardDao boardDao;

    @EJB
    private StrokeDao strokeDao;

    @Override
    public void save(Board board) {
        boardDao.save(board);
    }

    @Override
    public Board update(Board board) {
        return boardDao.update(board);
    }

    @Override
    public void remove(Board board) {
        boardDao.remove(board);
    }

    @Override
    public Stroke getStroke(int id) {
        return strokeDao.findById(id);
    }

    @Override
    public void clear(int id) {
        Board foundBoard = boardDao.findById(id);
        foundBoard.clear();
        boardDao.update(foundBoard);
    }

    @Override
    public Board get(int id) {
        return boardDao.findById(id);
    }

    @Override
    public List<Board> getAll() {
        return boardDao.findAll();
    }
}
