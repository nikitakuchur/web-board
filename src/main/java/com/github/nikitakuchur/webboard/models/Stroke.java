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

@Entity
@Table(name = "strokes")
public class Stroke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String color = "#000000";
    private double size = 10;

    @OneToMany(mappedBy = "stroke", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Point> points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonbTransient
    private Board board;

    public Stroke() {
    }

    public Stroke(String color, double size, List<Point> points) {
        this.color = color;
        this.size = size;
        setPoints(points);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public List<Point> getPoints() {
        if (points == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(points);
    }

    public void setPoints(List<Point> points) {
        this.points = points != null ? new ArrayList<>(points) : Collections.emptyList();
        this.points.forEach(point -> point.setStroke(this));
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stroke stroke = (Stroke) o;
        return id == stroke.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}