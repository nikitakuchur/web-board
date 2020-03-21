package com.github.nikitakuchur.webboard;

import java.util.List;

public class BoardMessage {
    private List<Stroke> strokes;
    private int deleted;
    private boolean clear;

    private static final BoardMessage CLEAR_MESSAGE = new BoardMessage(null, -1, true);

    private BoardMessage(List<Stroke> strokes, int deleted, boolean clear) {
        this.strokes = strokes;
        this.deleted = deleted;
        this.clear = clear;
    }

    public static BoardMessage strokesMessage(List<Stroke> strokes) {
        return new BoardMessage(strokes, -1, false);
    }

    public static BoardMessage deleteMessage(int deleted) {
        return new BoardMessage(null, deleted, false);
    }

    public static BoardMessage clearMessage() {
        return CLEAR_MESSAGE;
    }

    public List<Stroke> getStrokes() {
        return strokes;
    }

    public int getDeleted() {
        return deleted;
    }

    public boolean isClear() {
        return clear;
    }
}
