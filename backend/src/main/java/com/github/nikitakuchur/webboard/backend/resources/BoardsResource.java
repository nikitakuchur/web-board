package com.github.nikitakuchur.webboard.backend.resources;

import java.util.Collections;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.nikitakuchur.webboard.backend.models.Board;
import com.github.nikitakuchur.webboard.backend.services.BoardService;

/**
 * The board resource class.
 */
@Path("/boards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("user")
public class BoardsResource {

    @Inject
    private BoardService boardService;

    /**
     * Returns all the boards.
     *
     * @return the list of the boards
     */
    @GET
    public List<Board> getAll() {
        List<Board> boards = boardService.getAll();
        return boards == null ? Collections.emptyList() : boards;
    }

    /**
     * Finds the board by the id.
     *
     * @param id the id
     * @return the found board
     */
    @GET
    @Path("{id}")
    public Board get(@PathParam("id") int id) {
        return boardService.get(id);
    }

    /**
     * Adds the given board.
     *
     * @param board the board
     */
    @POST
    public void add(Board board) {
        boardService.save(board);
    }

    /**
     * Removes the given board.
     *
     * @param board the board
     */
    @DELETE
    public void remove(Board board) {
        boardService.remove(board);
    }
}
