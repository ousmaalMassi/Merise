package com.graphics.gdf;

import com.graphics.GNode;

import java.awt.*;

public class GNodeGDF extends GNode {

    public GNodeGDF(int x, int y, String name) {
        super(x, y, name);
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public void draw(Graphics2D g) {

    }
}
