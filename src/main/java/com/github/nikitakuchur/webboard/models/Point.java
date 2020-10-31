package com.github.nikitakuchur.webboard.models;

import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The point model class.
 */
@Entity
@Table(name = "points")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double x;
    private double y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stroke_id")
    @JsonbTransient
    private Stroke stroke;

    /**
     * Default constructor.
     */
    public Point() {
    }

    /**
     * The point constructor.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the point id.
     *
     * @return the point id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the point id.
     *
     * @param id the point id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the x-coordinate of the point.
     *
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the point.
     *
     * @param x the x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the point.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the point.
     *
     * @param y the y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns the point stroke.
     *
     * @return the stroke
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * Sets the point stroke.
     *
     * @param stroke the stroke
     */
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return id.equals(point.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}