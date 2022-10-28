package com.graphics;

import java.awt.*;

public abstract class GNode extends GObject {

    protected int width;
    protected int height;
    protected int shiftedX;
    protected int shiftedY;
    protected String name;
    protected Color strokeColor;
    protected Font font;
    protected int x;
    protected int y;
    protected int diffX = 0;
    protected int diffY = 0;

    public GNode(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.setName(name);
        this.strokeColor = Color.black;
        this.font = FONT_SELECT;
    }

    public abstract boolean contains(double x, double y);

    public void move(int x, int y) {
        if (this.diffX == 0 || this.diffY == 0) {
            this.diffX = x - this.x;
            this.diffY = y - this.y;
        }
        this.x = x-diffX;
        this.y = y-diffY;
    }

    public void resetDiff() {
        this.diffX = 0;
        this.diffY = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            strokeColor = Color.red;
            stroke = SELECTED_STROKE;
            font = FONT_SELECT;
            return;
        }
        strokeColor = Color.black;
        stroke = DEFAULT_STROKE;
        font = FONT_UNSELECT;
    }
}
