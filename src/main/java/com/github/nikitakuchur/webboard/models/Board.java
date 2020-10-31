package com.github.nikitakuchur.webboard.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    private final Set<Stroke> strokes = new HashSet<>();

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
        return new ArrayList<>(strokes);
    }

    /**
     * Sets the board strokes.
     *
     * @param strokes the board strokes
     */
    public void setStrokes(List<Stroke> strokes) {
        if (strokes == null) return;
        this.strokes.forEach(stroke -> stroke.setBoard(this));
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
