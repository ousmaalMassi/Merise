package com.MeriseGUI.gdf;

import com.MeriseGUI.GArrow;
import com.MeriseGUI.GLinkText;
import com.MeriseGUI.GraphicalNode;

import java.awt.*;

public class DF extends GArrow implements GLinkText {

    public DF(GraphicalNode a, GraphicalNode b) {
        super(a, b);
    }

    @Override
    public void drawText(Graphics2D g) {

    }

    @Override
    public boolean underText(double mx, double my) {
        return false;
    }

    @Override
    public void setText(String card) {

    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}