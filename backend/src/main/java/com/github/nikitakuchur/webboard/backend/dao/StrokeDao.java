package com.github.nikitakuchur.webboard.backend.dao;

import java.util.List;

import com.github.nikitakuchur.webboard.backend.models.Stroke;

/**
 * The data access object class for stroke entity.
 */
public interface StrokeDao {

    /**
     * Finds the stroke by the id.
     *
     * @param id the id
     * @return the found stroke
     */
    Stroke findById(int id);
}
