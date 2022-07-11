package com.graphics.gdf;

import com.graphics.GArrow;

import java.awt.*;

public class GComposedDF extends GNodeGDF {

    private GArrow gArrow;
    private GEmptyNode gEmptyNode;
    private GTrivialDF gTrivialDF;

    public GComposedDF(int x, int y, String name) {
        super(x, y, name);
        gEmptyNode = new GEmptyNode(x, y);
        gTrivialDF = new GTrivialDF(x, y);
        gArrow = new GArrow(this, gEmptyNode);
    }

    @Override
    public void draw(Graphics2D g) {
        gTrivialDF.move(x, y);
        gTrivialDF.draw(g);
        gEmptyNode.move(x, y+30);
        gEmptyNode.draw(g);
        gArrow.draw(g);
    }

    @Override
    public boolean contains(double x, double y) {
        return gEmptyNode.contains(x, y) || gTrivialDF.contains(x, y);
    }
}
