package com.github.nikitakuchur.webboard.backend.dao;

import java.util.List;

import com.github.nikitakuchur.webboard.backend.models.Board;

/**
 * The data access object class for board entity.
 */
public interface BoardDao {

    /**
     * Saves the board.
     *
     * @param board the board
     */
    void save(Board board);

    /**
     * Finds the board by id.
     *
     * @param id the id
     * @return the found board
     */
    Board findById(int id);

    /**
     * Finds all the boards.
     *
     * @return the found boards
     */
    List<Board> findAll();

    /**
     * Updates the given board.
     *
     * @param board the board
     * @return the updated board
     */
    Board update(Board board);

    /**
     * Removes the given board.
     *
     * @param board the board
     */
    void remove(Board board);
}
