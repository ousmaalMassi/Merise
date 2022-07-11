package com.graphics.gdf;

import java.awt.*;

public class GDFAttribute extends GNodeGDF {
    protected int width;
    protected int height;

    public GDFAttribute(int x, int y, String name) {
        super(x, y, name);
    }
    
    @Override
    public void draw(Graphics2D g) {
        FontMetrics fm = g.getFontMetrics();
        width = fm.stringWidth(name);
        height = width/10 + fm.getHeight();

        shiftedX = x - width/2;
        shiftedY = y - height/2;
        int textX = shiftedX;
        int textY = shiftedY + height/2 + fm.getHeight()/4;
        g.setFont(font);
        g.setColor(strokeColor);
        g.drawString(name, textX, textY);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            strokeColor = Color.red;
            font = FONT_SELECT;
        }
        else{
            strokeColor = Color.black;
            font = FONT_UNSELECT;
        }
    }

    @Override
     public boolean contains(double x, double y) {
        if (this.shiftedX > x || this.shiftedY > y) return false;
        return ( Math.abs(this.x-x) <= width/2 ) && ( Math.abs(this.y-y) <= height/2 );
//        if (width <= 0 || height <= 0) return false;
//        return (Math.pow((x-shiftedX)/width-0.5, 2) + Math.pow((y-shiftedY)/height-0.5, 2)) < 1;
    }
     
    @Override
    public String toString() {
        return "(" + (x) + ", " + (y) + ") ";

    }
}
