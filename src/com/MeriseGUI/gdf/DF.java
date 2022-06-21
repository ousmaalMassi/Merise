package com.MeriseGUI.gdf;

import com.MeriseGUI.GraphicalLink;
import com.MeriseGUI.GraphicalNode;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class DF extends GraphicalLink {

    private static final long serialVersionUID = -6972652167790425200L;

    public DF(GraphicalNode a, GraphicalNode b) {
        super(a, b);
    }
    
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        int[] xPoints = {0, -5, 5};
        int[] yPoints = {0, -10, -10};

        int endY = nodeB.getY();
        int endX = nodeB.getX();

        int x = nodeA.getX();
        int y = nodeA.getY();

        double angle = Math.atan2(endY - y, endX - x);

        //g.drawLine(x, y, (int) (endX - 10 * Math.cos(angle)), (int) (endY - 10 * Math.sin(angle)));
        AffineTransform tx1 = g.getTransform();

        AffineTransform tx2 = (AffineTransform) tx1.clone();

        tx2.translate(endX, endY);
        tx2.rotate(angle - Math.PI / 2);

        g.setTransform(tx2);
        g.fill(new Polygon(xPoints, yPoints, xPoints.length));

        g.setTransform(tx1);
    }
	
    @Override
    public String toString() {
        return "[ Flow]: (" + nodeA.toString() + ") ===> (" + nodeB.toString() + ") ";
    }
}