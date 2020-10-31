package com.github.nikitakuchur.webboard;

import java.util.Arrays;
import java.util.Collections;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.github.nikitakuchur.webboard.endpoints.BoardMessage;
import com.github.nikitakuchur.webboard.models.Point;
import com.github.nikitakuchur.webboard.models.Stroke;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardMessageTests {

    @Test
    @DisplayName("JSON-B test")
    void jsonbTest() throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        BoardMessage message1 = BoardMessage.strokesMessage(Arrays.asList(
                new Stroke("#FF00FF", 8, Arrays.asList(new Point(1, 2), new Point(3, 4))),
                new Stroke("#0000FF", 14, Collections.singletonList(new Point(5, 6)))
        ));
        String json = jsonb.toJson(message1);
        BoardMessage message2 = jsonb.fromJson(json, BoardMessage.class);
        jsonb.close();
        assertEquals(message1, message2);
    }
}
