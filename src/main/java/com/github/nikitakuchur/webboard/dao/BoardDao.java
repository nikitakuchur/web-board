package com.github.nikitakuchur.webboard.dao;

import java.util.List;

import com.github.nikitakuchur.webboard.models.Board;

public interface BoardDao {

    /**
     * Save the board.
     *
     * @param board the board
     */
    void save(Board board);

    /**
     * Find the board by id.
     *
     * @param id id
     * @return the found board
     */
    Board findById(int id);

    /**
     * Find all boards.
     *
     * @return the found boards
     */
    List<Board> findAll();

    /**
     * Update the given board.
     *
     * @param board the board
     */
    void update(Board board);

    /**
     * Delete the given board.
     *
     * @param board the board
     */
    void delete(Board board);
}
