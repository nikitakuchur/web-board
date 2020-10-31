package com.github.nikitakuchur.webboard.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The stroke model class.
 */
@Entity
@Table(name = "strokes")
public class Stroke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String color = "#000000";
    private double size = 10;

    @OneToMany(mappedBy = "stroke", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Point> points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonbTransient
    private Board board;

    /**
     * Default constructor.
     */
    public Stroke() {
    }

    /**
     * The stroke constructor.
     *
     * @param color  the color
     * @param size   the size
     * @param points the points
     */
    public Stroke(String color, double size, List<Point> points) {
        this.color = color;
        this.size = size;
        setPoints(points);
    }

    /**
     * Returns the stroke id.
     *
     * @return the stroke id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the stroke id.
     *
     * @param id the stroke id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the stroke color.
     *
     * @return the stroke color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the stroke color.
     *
     * @param color the stroke color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Returns the stroke size.
     *
     * @return the stroke size
     */
    public double getSize() {
        return size;
    }

    /**
     * Sets the stroke size.
     *
     * @param size the stroke size
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Returns the stroke points.
     *
     * @return the stroke points
     */
    public List<Point> getPoints() {
        if (points == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(points);
    }

    /**
     * Sets the stroke points.
     *
     * @param points the stroke points
     */
    public void setPoints(List<Point> points) {
        this.points = points != null ? new ArrayList<>(points) : Collections.emptyList();
        this.points.forEach(point -> point.setStroke(this));
    }

    /**
     * Returns the stroke board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the stroke board.
     *
     * @param board the board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stroke stroke = (Stroke) o;
        return id.equals(stroke.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Stroke{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", points=" + points +
                '}';
    }
}
