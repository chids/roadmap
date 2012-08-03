package com.github.chids.roadmap;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sun.istack.internal.NotNull;

public class SectionContainer {
    @Valid
    @NotNull
    @JsonProperty
    private Section section;

    @Override
    public String toString() {
        return this.section.toString();
    }

    public void addTo(final Project project) {
        this.section.addTo(project);
    }
}