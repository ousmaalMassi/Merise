package com.graphics.mcd;

import java.awt.*;
import java.io.Serial;


public class GEntity extends GMCDNode {

    public static int entityNbr = 1;

    public GEntity(int x, int y, String text) {
        super(x, y, text);
        entityNbr++;
    }
    
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }


    @Override
    public String toString() {
        return super.toString();
    }

}
