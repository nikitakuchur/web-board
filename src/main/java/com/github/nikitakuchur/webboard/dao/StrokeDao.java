package com.github.nikitakuchur.webboard.dao;

import java.util.Collection;
import java.util.List;

import com.github.nikitakuchur.webboard.models.Stroke;

public interface StrokeDao {
    void save(Stroke stroke);

    Stroke findById(int id);
    List<Stroke> findAll();

    void update(Stroke stroke);

    void remove(Stroke stroke);
}
