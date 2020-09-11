package com.github.nikitakuchur.webboard;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Stroke {
    private final int id;
    private String color = "#000000";
    private double size = 10;
    private Point[] points;

    @JsonbCreator
    public Stroke(@JsonbProperty("id") int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }
}