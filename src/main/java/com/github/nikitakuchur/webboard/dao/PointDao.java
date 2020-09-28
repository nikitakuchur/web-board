package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import com.github.nikitakuchur.webboard.models.Point;

public interface PointDao {
    void save(Point point);

    Point findById(int id);
    List<Point> findAll();

    void update(Point point);

    void delete(Point point);
}
