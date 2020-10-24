package com.github.nikitakuchur.webboard.services;

import java.util.List;

import com.github.nikitakuchur.webboard.models.Board;
import com.github.nikitakuchur.webboard.models.Stroke;

public interface BoardService {

    /**
     * Save the given board.
     *
     * @param board the board
     * @return the saved board
     */
    Board save(Board board);

    /**
     * Delete the given board.
     *
     * @param board the board
     */
    void delete(Board board);

    /**
     * Add the stroke to the given board.
     *
     * @param board  the board
     * @param stroke the stroke to add
     */
    void addStroke(Board board, Stroke stroke);

    /**
     * Add the strokes to the given board.
     *
     * @param board   the board
     * @param strokes the strokes to add
     */
    void addStrokes(Board board, List<Stroke> strokes);

    /**
     * Get the stroke by id.
     *
     * @param id id
     * @return the found stroke
     */
    Stroke getStroke(int id);

    /**
     * Delete the stroke.
     *
     * @param stroke the stroke
     */
    void deleteStroke(Stroke stroke);

    /**
     * Clear the given board.
     *
     * @param board the board
     */
    void clear(Board board);

    /**
     * Get the board by id.
     *
     * @param id id
     * @return the found board
     */
    Board get(int id);

    /**
     * Get all boards.
     *
     * @return boards
     */
    List<Board> getAll();
}
