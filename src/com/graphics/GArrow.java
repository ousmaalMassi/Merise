package com.graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GArrow extends GLink {

    private int[] xHeadPoints = {0, -5, 5};
    private int[] yHeadPoints = {0, -10, -10};
    public GArrow(GNode a, GNode b) {
        super(a, b);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        drawHeadArrow(g);
    }

    private void drawHeadArrow(Graphics2D g) {

        int endX = nodeB.x;
        int endY = nodeB.y;

        int x = nodeA.x;
        int y = nodeA.y;

        double angle = Math.atan2(endY - y, endX - x);

        AffineTransform tx1 = g.getTransform();

        AffineTransform tx2 = (AffineTransform) tx1.clone();

        tx2.translate(endX, endY);
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
