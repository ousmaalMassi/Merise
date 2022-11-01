package com.graphics.gdf;

import java.awt.*;

public class GComposedDF extends GNodeGDF {

    private final DFType type;
    int ovalRadius = 20;
    public GComposedDF(int x, int y, String name, DFType type) {
        super(x, y, name);
        this.type = type;
    }

    public DFType getType() {
        return type;
    }

    @Override
    public void draw(Graphics2D g) {
        shiftedX = x - ovalRadius/2;
        shiftedY = y - ovalRadius/2;
        g.setColor(strokeColor);

        if (this.type.equals(DFType.TRIVIAL)) {
            g.drawString(String.valueOf((char) 8615), x - 6, y + 20);
            g.drawString("{" + (char) 8709 + "}", x - 15, y + 35);
        }

        g.setColor(Color.WHITE);
        g.fillOval(shiftedX, shiftedY, ovalRadius, ovalRadius);
        g.setColor(strokeColor);
        g.drawOval(shiftedX, shiftedY, ovalRadius, ovalRadius);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            strokeColor = Color.red;
            font = FONT_SELECT;
            return;
        }
        strokeColor = Color.black;
        font = FONT_UNSELECT;
    }

    @Override
    public boolean contains(double x, double y) {
        return (Math.pow((x- shiftedX)/ovalRadius-0.5, 2) + Math.pow((y- shiftedY)/ovalRadius-0.5, 2)) < 0.25;
    }
}

