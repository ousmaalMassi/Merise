package com.MeriseGUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TestNode extends Node{

    public TestNode(int x, int y, String name) {
        super(x, y, name);

    }

    public void draw(Graphics2D graphics2D) {

        Rectangle2D rect = new Rectangle2D.Double(x, y, 120, 80);
        graphics2D.draw(rect);
        graphics2D.drawString("massi", x, y);
    }

    @Override
    public boolean inCorner(int x, int y) {
        return false;
    }

    @Override
    public void resize(int x, int y) {

    }
}
