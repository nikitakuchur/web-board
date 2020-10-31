package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import com.github.nikitakuchur.webboard.models.Board;

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
     */
    void update(Board board);

    /**
     * Removes the given board.
     *
     * @param board the board
     */
    void remove(Board board);
}
