package com.graphics.mcd;

import java.awt.*;

public class GAssociation extends GMCDNode {
    
    public GAssociation(int x, int y, String text) {
        super(x, y, text);
        this.borderRadius = 30;
    }
    
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
    
    @Override
    public String toString() {
            return super.toString()  ;
    }

    
}
