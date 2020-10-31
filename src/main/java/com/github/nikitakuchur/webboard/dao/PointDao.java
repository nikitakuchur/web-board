package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import com.github.nikitakuchur.webboard.models.Point;

/**
 * The data access object class for point entity.
 */
public interface PointDao {

    /**
     * Saves the given point.
     *
     * @param point the point
     */
    void save(Point point);

    /**
     * Finds the point by the id.
     *
     * @param id the id
     * @return the found point
     */
    Point findById(int id);

    /**
     * Finds all the points.
     *
     * @return the found points
     */
    List<Point> findAll();

    /**
     * Updates the given point.
     *
     * @param point the point
     */
    void update(Point point);

    /**
     * Removes the given point.
     *
     * @param point the point
     */
    void remove(Point point);
}
