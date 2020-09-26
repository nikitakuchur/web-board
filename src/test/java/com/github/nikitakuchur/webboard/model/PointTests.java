package com.github.nikitakuchur.webboard.model;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTests {

    @Test
    @DisplayName("JSON-B test")
    void jsonbTest() throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        Point point1 = new Point(4, 3);
        String json = jsonb.toJson(point1);
        Point point2 = jsonb.fromJson(json, Point.class);
        jsonb.close();
        assertEquals(point1, point2);
    }
}
