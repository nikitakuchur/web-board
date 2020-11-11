package com.github.nikitakuchur.webboard.backend.dao;

import java.util.List;

import com.github.nikitakuchur.webboard.backend.models.Stroke;

/**
 * The data access object class for stroke entity.
 */
public interface StrokeDao {

    /**
     * Saves the given stroke.
     *
     * @param stroke the stroke
     */
    void save(Stroke stroke);

    /**
     * Finds the stroke by the id.
     *
     * @param id the id
     * @return the found stroke
     */
    Stroke findById(int id);

    /**
     * Finds all the strokes.
     *
     * @return the found strokes
     */
    List<Stroke> findAll();

    /**
     * Updates the given stroke.
     *
     * @param stroke the stroke
     */
    void update(Stroke stroke);

    /**
     * Removes the given stroke.
     *
     * @param stroke the stroke
     */
    void remove(Stroke stroke);
}
