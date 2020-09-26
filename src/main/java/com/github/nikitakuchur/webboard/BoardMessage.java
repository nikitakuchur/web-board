package com.github.nikitakuchur.webboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.github.nikitakuchur.webboard.model.Stroke;

public class BoardMessage {
    private final List<Stroke> strokes;
    private final int deleted;
    private final boolean clear;

    private static final BoardMessage CLEAR_MESSAGE = new BoardMessage(Collections.emptyList(), -1, true);

    @JsonbCreator
    public BoardMessage(@JsonbProperty("strokes") Collection<Stroke> strokes, @JsonbProperty("deleted") int deleted,
            @JsonbProperty("clear") boolean clear) {
        this.strokes = strokes != null ? new ArrayList<>(strokes) : Collections.emptyList();
        this.deleted = deleted;
        this.clear = clear;
    }

    public static BoardMessage strokesMessage(Collection<Stroke> strokes) {
        return new BoardMessage(strokes, -1, false);
    }

    public static BoardMessage deleteMessage(int deleted) {
        return new BoardMessage(null, deleted, false);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardMessage message = (BoardMessage) o;
        return deleted == message.deleted &&
                clear == message.clear &&
                Objects.equals(strokes, message.strokes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strokes, deleted, clear);
    }
}
