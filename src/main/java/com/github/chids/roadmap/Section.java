package com.github.chids.roadmap;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.kohsuke.graphviz.Attribute;
import org.kohsuke.graphviz.Graph;
import org.kohsuke.graphviz.Node;
import org.kohsuke.graphviz.StyleAttr;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multimap;
import com.sun.istack.internal.NotNull;

public class Section extends Graph {

    private static final Iterator<Color> colors = Iterators.cycle(
            Color.ORANGE,
            Color.LIGHT_GRAY,
            Color.MAGENTA,
            Color.DARK_GRAY);

    @Valid
    @NotNull
    private String name;

    private String alias;
    private final Map<String, Node> nodes = new HashMap<String, Node>();
    private final Multimap<String, String> dependencies = ArrayListMultimap.create();

    @Valid
    @NotNull
    private List<String> items;

    public Section() {
        attr(Attribute.STYLE, StyleAttr.FILLED);
        attr(Attribute.COLOR, colors.next());
    }

    public void setName(final String name) {
        this.name = this.alias = name;
        if(name.contains(":")) {
            final String[] segments = name.split(":");
            this.alias = segments[0];
            this.name = segments[1];
        }
        attr(Attribute.LABEL, this.name);
    }

    public void setItems(final List<String> items) {
        this.items = items;
        Node previous = null;
        for(final String nodeName : this.items) {
            final Item node = new Item(nodeName);
            node.register(this);
            node(node);
            if(previous != null) {
                edge(previous, node);
            }
            previous = node;
        }
    }

    @Override
    public String toString() {
        return this.name + '[' + this.items + ']';
    }

    public void addTo(final Project project) {
        project.addSection(this, this.nodes, this.dependencies);
    }

    public void addDependencies(final Item node, final String[] dependencies) {
        this.nodes.put(this.alias + ':' + node.toString(), node);
        for(final String dependency : dependencies) {
            this.dependencies.put(this.alias + ':' + node, dependency);
        }
    }
}