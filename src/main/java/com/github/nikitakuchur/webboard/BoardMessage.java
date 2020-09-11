package com.github.nikitakuchur.webboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BoardMessage {
    private final List<Stroke> strokes;
    private final int deleted;
    private final boolean clear;

    private static final BoardMessage CLEAR_MESSAGE = new BoardMessage(Collections.emptyList(), -1, true);

    @JsonbCreator
    public BoardMessage(@JsonbProperty("strokes") Collection<Stroke> strokes, @JsonbProperty("deleted") int deleted, @JsonbProperty("clear") boolean clear) {
        this.strokes = new ArrayList<>(strokes);
        this.deleted = deleted;
        this.clear = clear;
    }

    public static BoardMessage strokesMessage(Collection<Stroke> strokes) {
        return new BoardMessage(new ArrayList<>(strokes), -1, false);
    }

    public static BoardMessage deleteMessage(int deleted) {
        return new BoardMessage(Collections.emptyList(), deleted, false);
    }

    public static BoardMessage clearMessage() {
        return CLEAR_MESSAGE;
    }

    public List<Stroke> getStrokes() {
        return new ArrayList<>(strokes);
    }

    public int getDeleted() {
        return deleted;
    }

    public boolean isClear() {
        return clear;
    }
}
