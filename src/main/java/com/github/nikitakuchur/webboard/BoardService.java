package com.github.nikitakuchur.webboard;

import java.util.Collection;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.github.nikitakuchur.webboard.dao.StrokeDao;
import com.github.nikitakuchur.webboard.models.Stroke;

@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class BoardService {

    @Inject
    private StrokeDao strokeDao;

    public void addStroke(Stroke stroke) {
        strokeDao.save(stroke);
    }

    public void addStrokes(Collection<Stroke> strokes) {
        strokeDao.saveAll(strokes);
    }

    public void removeStroke(int id) {
        Stroke stroke = strokeDao.findById(id);
        if (stroke != null) {
            strokeDao.delete(stroke);
        }
    }

    public void clear() {
        strokeDao.findAll().forEach(strokeDao::delete);
    }

    public List<Stroke> getStrokes() {
        return strokeDao.findAll();
    }
}
