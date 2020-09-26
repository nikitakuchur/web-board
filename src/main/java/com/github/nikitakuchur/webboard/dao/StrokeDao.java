package com.github.nikitakuchur.webboard.dao;

import java.util.Collection;
import java.util.List;

import com.github.nikitakuchur.webboard.model.Stroke;

public interface StrokeDao {
    void save(Stroke stroke);
    void saveAll(Collection<Stroke> strokes);

    Stroke findById(int id);
    List<Stroke> findAll();

    void update(Stroke stroke);

    void delete(Stroke stroke);
}
