package com.github.nikitakuchur;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/board-servlet")
public class BoardServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final List<Stroke> strokes = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String strokesJson = gson.toJson(strokes);
        resp.getWriter().write(strokesJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameterMap().containsKey("clear")) {
            strokes.clear();
        }
        if (req.getParameterMap().containsKey("stroke")) {
            String strokeJson = req.getParameter("stroke");
            Stroke stroke = gson.fromJson(strokeJson, new TypeToken<Stroke>() {
            }.getType());
            if (stroke != null) {
                strokes.add(stroke);
            }
        }
    }

    private static class Stroke {
        private String color = "#000000";
        private double size = 10;
        private Point[] points;
    }

    private class Point {
        private double x;
        private double y;
    }
}
