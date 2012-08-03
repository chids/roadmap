package com.github.chids.roadmap;

import java.awt.Color;
import java.util.Arrays;

import org.kohsuke.graphviz.Attribute;
import org.kohsuke.graphviz.Node;
import org.kohsuke.graphviz.Shape;
import org.kohsuke.graphviz.StyleAttr;

public class Item extends Node {

    private final String name;
    private String[] dependencies = new String[0];

    public Item(final String item) {
        attr(Attribute.FILLCOLOR, Color.RED);
        String name = item;
        if(item.contains(" -> ")) {
            final String[] segments = item.split(" -> ");
            name = segments[0];
            this.dependencies = Arrays.copyOfRange(segments, 1, segments.length);
        }
        if(name.contains(":")) {
            final String[] segments = name.split(":");
            name = segments[0];
            attr(Attribute.FILLCOLOR, "x".equals(segments[1]) ? Color.GREEN : Color.YELLOW);
        }
        attr(Attribute.LABEL, name);
        attr(Attribute.STYLE, StyleAttr.FILLED);
        attr(Attribute.SHAPE, Shape.BOX);
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void register(final Section section) {
        section.addDependencies(this, this.dependencies);
    }
}