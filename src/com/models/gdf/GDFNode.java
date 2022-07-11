package com.models.gdf;

import com.Merise;
import com.exceptions.DuplicateMeriseObject;
import com.models.MeriseObject;

import java.util.ArrayList;
import java.util.List;

public class GDFNode extends MeriseObject {

    protected List<String> targets;

    public GDFNode(String name) {
        this.name = name;
        this.targets = new ArrayList<>();
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
