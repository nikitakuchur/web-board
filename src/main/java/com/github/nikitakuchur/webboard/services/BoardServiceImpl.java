package com.github.nikitakuchur.webboard.services;

import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.github.nikitakuchur.webboard.dao.BoardDao;
import com.github.nikitakuchur.webboard.dao.StrokeDao;
import com.github.nikitakuchur.webboard.models.Board;
import com.github.nikitakuchur.webboard.models.Stroke;

@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class BoardServiceImpl implements BoardService {

    @Inject
    private BoardDao boardDao;

    @Inject
    private StrokeDao strokeDao;

    @Override
    public void save(Board board) {
        boardDao.save(board);
    }

    @Override
    public void delete(Board board) {
        boardDao.delete(board);
    }

    @Override
    public void addStroke(Board board, Stroke stroke) {
        if (board == null || stroke == null) return;
        stroke.setBoard(board);
        strokeDao.save(stroke);
    }

    @Override
    public void addStrokes(Board board, List<Stroke> strokes) {
        if (board == null || strokes == null) return;
        strokes.forEach(stroke -> addStroke(board, stroke));
    }

    @Override
    public Stroke getStroke(int id) {
        return strokeDao.findById(id);
    }

    @Override
    public void deleteStroke(Stroke stroke) {
        strokeDao.delete(stroke);
    }

    @Override
    public void clear(Board board) {
        if (board == null) return;
        List<Stroke> strokes = boardDao.findById(board.getId()).getStrokes();
        strokes.forEach(stroke -> strokeDao.delete(stroke));
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
