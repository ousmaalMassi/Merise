package com.MeriseGUI.mpd;

import com.MeriseGUI.Node;

import java.awt.*;


public class GraphicalMPDTable extends Node {

    private static final long serialVersionUID = 5396347452110584370L;

    public GraphicalMPDTable(int x, int y, String text) {
        super(x, y, text);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public boolean inCorner(int x, int y) {
        return false;
    }

    @Override
    public void resize(int x, int y) {

    }

    @Override
    public String toString() {
        return super.toString() + ", t: " + name + "}";
    }

}
