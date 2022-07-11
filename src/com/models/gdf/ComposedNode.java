package com.models.gdf;

import java.util.ArrayList;
import java.util.List;

public class ComposedNode extends GDFNode {

    private List<String> sources;

    public ComposedNode(String name) {
        super(name);
        sources = new ArrayList<>();
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public void addSource(String target) {
        if (!this.sources.contains(target))
            this.sources.add(target);
    }

    public void removeSource(String target) {
        this.sources.remove(target);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", sources=" + sources +
                ", targets=" + targets +
                '}';
    }
}
