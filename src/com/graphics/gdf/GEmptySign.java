package com.graphics.gdf;

import java.awt.*;

public class GEmptySign extends GEmptyNode {

    public GEmptySign(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.drawLine(shiftedX, shiftedY,shiftedX+ovalRadius, shiftedY+ovalRadius);
    }
}
