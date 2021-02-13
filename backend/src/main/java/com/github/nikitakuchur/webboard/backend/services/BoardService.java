package com.github.nikitakuchur.webboard.backend.services;

import java.util.List;

import com.github.nikitakuchur.webboard.backend.models.Board;
import com.github.nikitakuchur.webboard.backend.models.Stroke;

public interface BoardService {

    /**
     * Saves the given board.
     *
     * @param board the board
     */
    void save(Board board);

    /**
     * Updates the given board.
     *
     * @param board the board
     */
    void update(Board board);

    /**
     * Removes the given board.
     *
     * @param board the board
     */
    void remove(Board board);

    /**
     * Finds the stroke by the id.
     *
     * @param id the id
     * @return the found stroke
     */
    Stroke getStroke(int id);

    /**
     * Clears the board by the id.
     *
     * @param id the id
     */
    void clear(int id);

    /**
     * Finds the board by the id.
     *
     * @param id the id
     * @return the found board
     */
    Board get(int id);

    /**
     * Finds all the boards.
     *
     * @return the boards
     */
    List<Board> getAll();
}
