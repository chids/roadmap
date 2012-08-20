package com.github.chids.roadmap;

import static com.google.common.collect.Maps.newHashMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.kohsuke.graphviz.Attribute;
import org.kohsuke.graphviz.Graph;
import org.kohsuke.graphviz.Node;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sun.istack.internal.NotNull;

public class Project extends Graph {
    @Valid
    @NotNull
    private String name;

    enum Layout {
        LR, TB
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Layout direction = Layout.LR;

    @Valid
    @NotNull
    private List<SectionContainer> sections;

    private final Map<String, Node> nodes = newHashMap();
    private final Multimap<String, String> dependencies = ArrayListMultimap.create();

    public void setSections(final List<SectionContainer> sections) {
        for(final SectionContainer container : sections) {
            container.addTo(this);
        }
        this.sections = sections;
    }

    public void setName(final String name) {
        attr(Attribute.LABEL, name + " as of " + dateFormat.format(new Date()));
        this.name = name;
    }

    public void setDirection(final String direction) {
        this.direction = Layout.valueOf(direction);
    }

    @Override
    public String toString() {
        return this.name + '[' + this.sections + ']';
    }

    @Override
    public void writeTo(final OutputStream out) {
        linkDependencies();
        // RankDir.LR -> "lr" and it needs to be "LR"
        // at least for GraphViz on OS X. Boo. Hoo.
        attr("rankdir", this.direction.name());
        try {
            // Commence butt ugly subgraph cluster hack
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            super.writeTo(buffer);
            final String string = new String(buffer.toByteArray(), Charsets.UTF_8);
            out.write(string.replaceAll("(subgraph) (n)", "$1 cluster_$2").getBytes());
        }
        catch(final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void linkDependencies() {
        for(final Entry<String, Collection<String>> e : this.dependencies.asMap().entrySet()) {
            final Node declarer = this.nodes.get(e.getKey());
            for(final String dependency : e.getValue()) {
                edge(this.nodes.get(dependency), declarer);
            }
        }
    }

    public void addSection(final Section section,
                           final Map<String, Node> nodes,
                           final Multimap<String, String> dependencies) {
        subGraph(section);
        this.nodes.putAll(nodes);
        this.dependencies.putAll(dependencies);
    }
}