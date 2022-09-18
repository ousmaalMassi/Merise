package com.graphics.gdf;

import com.graphics.GArrow;

import java.awt.*;

public class GComposedTrivialDF extends GNodeGDF {

    private final GArrow gArrow;
    private final GEmptySign gEmptySign;
    private final GEmptyNode gEmptyNode;

    public GComposedTrivialDF(int x, int y, String name) {
        super(x, y, name);
        gEmptySign = new GEmptySign(x, y);
        gEmptyNode = new GEmptyNode(x, y);
        gArrow = new GArrow(this, gEmptySign);
    }

    @Override
    public void draw(Graphics2D g) {
        gEmptyNode.move(x, y);
        gEmptyNode.draw(g);
        gEmptySign.move(x, y+30);
        gEmptySign.draw(g);
        gArrow.draw(g);
    }

    @Override
    public boolean contains(double x, double y) {
        return gEmptySign.contains(x, y) || gEmptyNode.contains(x, y);
    }
}
