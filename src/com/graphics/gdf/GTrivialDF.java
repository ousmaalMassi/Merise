package com.graphics.gdf;

import java.awt.*;

public class GTrivialDF extends GNodeGDF {

    int ovalRadius = 20;
    public GTrivialDF(int x, int y) {
        super(x, y, "");
    }

    @Override
    public void draw(Graphics2D g) {
        shiftedX = x - ovalRadius/2;
        shiftedY = y - ovalRadius/2;
        g.setColor(Color.WHITE);
        g.fillOval(shiftedX, shiftedY, ovalRadius, ovalRadius);
        g.setColor(Color.BLACK);
        g.drawOval(shiftedX, shiftedY, ovalRadius, ovalRadius);
    }

    @Override
    public boolean contains(double x, double y) {
        return (Math.pow((x- shiftedX)/ovalRadius-0.5, 2) + Math.pow((y- shiftedY)/ovalRadius-0.5, 2)) < 0.25;
    }
}

