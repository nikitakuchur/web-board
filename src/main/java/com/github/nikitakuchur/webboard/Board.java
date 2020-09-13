package com.github.nikitakuchur.webboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;

@Singleton
public class Board {
    private final Map<Integer, Stroke> strokeMap = new LinkedHashMap<>();

    public void addStroke(Stroke stroke) {
        strokeMap.put(stroke.getId(), stroke);
    }

    public void addStrokes(Collection<Stroke> strokes) {
        strokes.forEach(this::addStroke);
    }

    public void removeStroke(int id) {
        strokeMap.remove(id);
    }

    public void clear() {
        strokeMap.clear();
    }

    public List<Stroke> getStrokes() {
        return new ArrayList<>(strokeMap.values());
    }
}
