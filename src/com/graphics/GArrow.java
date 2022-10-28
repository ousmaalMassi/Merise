package com.graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GArrow extends GLink {

    private final int[] xHeadPoints = {0, -5, 5};
    private final int[] yHeadPoints = {0, -10, -10};
    public GArrow(GNode a, GNode b) {
        super(a, b);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        drawHeadArrow(g);
    }

    private void drawHeadArrow(Graphics2D g) {

        AffineTransform tx1 = g.getTransform();

        AffineTransform tx2 = (AffineTransform) tx1.clone();

        tx2.translate(xb, yb);
        tx2.rotate(angle - Math.PI / 2);

        g.setTransform(tx2);
        g.fill(new Polygon(xHeadPoints, yHeadPoints, xHeadPoints.length));

        g.setTransform(tx1);
    }

    @Override
    public String toString() {
        return "[ Arrow]: (" + nodeA.toString() + ") ===> (" + nodeB.toString() + ") ";
    }
}
