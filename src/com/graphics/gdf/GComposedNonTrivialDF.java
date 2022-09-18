package com.graphics.gdf;

import java.awt.*;

public class GComposedNonTrivialDF extends GNodeGDF {
    private final GEmptyNode gEmptyNode;

    public GComposedNonTrivialDF(int x, int y, String name) {
        super(x, y, name);
        gEmptyNode = new GEmptyNode(x, y);
    }

    @Override
    public void draw(Graphics2D g) {
        gEmptyNode.move(x, y);
        gEmptyNode.draw(g);
    }

    @Override
    public boolean contains(double x, double y) {
        return gEmptyNode.contains(x, y);
    }
}
