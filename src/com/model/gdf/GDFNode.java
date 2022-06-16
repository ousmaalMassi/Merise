package com.model.gdf;

import java.util.ArrayList;
import java.util.List;

public class GDFNode {

    private String name;
    private List<String> targets;

    public GDFNode(String name) {
        this.name = name;
        this.targets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public void addTarget(String target) {
        this.targets.add(target);
    }

    public void removeTarget(String target) {
        this.targets.remove(target);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", targets=" + targets +
                '}';
    }
}