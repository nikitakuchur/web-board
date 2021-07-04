package com.github.nikitakuchur.webboard.backend.models;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;

/**
 * The board model class.
 */
@Entity
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Stroke> strokes = new HashSet<>();

    /**
     * Returns the board id.
     *
     * @return the board id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the board id.
     *
     * @param id the board id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the board name.
     *
     * @return the board name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the board name.
     *
     * @param name the board name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the board description.
     *
     * @return the board description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the board description.
     *
     * @param description the board description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the board strokes.
     *
     * @return the board strokes
     */
    public List<Stroke> getStrokes() {
        return strokes.stream()
                .sorted(Comparator.comparing(Stroke::getId))
                .collect(Collectors.toList());
    }

    /**
     * Sets the board strokes.
     *
     * @param strokes the board strokes
     */
    public void setStrokes(Set<Stroke> strokes) {
        this.strokes = strokes;
    }

    /**
     * Adds the given stroke to the board.
     *
     * @param stroke the stroke
     */
    public void addStroke(Stroke stroke) {
        strokes.add(stroke);
        stroke.setBoard(this);
    }

    /**
     * Removes the given stroke from board strokes.
     *
     * @param stroke the stroke
     */
    public void removeStroke(Stroke stroke) {
        strokes.remove(stroke);
    }

    /**
     * Clear the board.
     */
    public void clear() {
        strokes.forEach(stroke -> stroke.setBoard(null));
        strokes.clear();
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", strokes=" + strokes +
                '}';
    }
}
