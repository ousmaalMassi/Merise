package com.graphics.gdf;

import com.graphics.GArrow;
import com.graphics.GLinkText;
import com.graphics.GNode;

import java.awt.*;

public class GSimpleDF extends GArrow implements GLinkText {

    public GSimpleDF(GNode a, GNode b) {
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