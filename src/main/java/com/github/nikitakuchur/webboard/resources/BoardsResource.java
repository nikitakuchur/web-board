package com.github.nikitakuchur.webboard.resources;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.nikitakuchur.webboard.models.Board;
import com.github.nikitakuchur.webboard.services.BoardService;

@Path("/boards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardsResource {

    @Inject
    private BoardService boardService;

    @GET
    public List<Board> getAll() {
        List<Board> boards = boardService.getAll();
        return boards == null ? Collections.emptyList() : boards;
    }

    @GET
    @Path("{id}")
    public Board get(@PathParam("id") int id) {
        return boardService.get(id);
    }

    @POST
    public void add(Board board) {
        boardService.save(board);
    }

    @DELETE
    public void remove(Board board) {
        boardService.remove(board);
    }
}
