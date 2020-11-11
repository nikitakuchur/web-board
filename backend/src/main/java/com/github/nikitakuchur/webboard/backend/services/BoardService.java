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
     * Adds the stroke to the given board.
     *
     * @param board  the board
     * @param stroke the stroke to add
     */
    void addStroke(Board board, Stroke stroke);

    /**
     * Finds the stroke by the id.
     *
     * @param id the id
     * @return the found stroke
     */
    Stroke getStroke(int id);

    /**
     * Removes the given stroke from the board.
     *
     * @param board the board
     * @param stroke the stroke
     */
    void removeStroke(Board board, Stroke stroke);

    /**
     * Clears the given board.
     *
     * @param board the board
     */
    void clear(Board board);

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
