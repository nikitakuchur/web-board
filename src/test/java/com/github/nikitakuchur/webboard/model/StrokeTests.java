package com.github.nikitakuchur.webboard.model;

import java.util.Arrays;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrokeTests {

    @Test
    @DisplayName("JSON-B test")
    void jsonbTest() throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        Stroke stroke1 = new Stroke();
        stroke1.setColor("#FFFF00");
        stroke1.setSize(16);
        stroke1.setPoints(Arrays.asList(
                new Point(1, 2),
                new Point(3, 4),
                new Point(4, 5)
        ));
        String json = jsonb.toJson(stroke1);
        Stroke stroke2 = jsonb.fromJson(json, Stroke.class);
        jsonb.close();
        assertEquals(stroke1, stroke2);
    }
}
