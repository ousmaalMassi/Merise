package com.MeriseGUI.mcd;

import java.awt.*;
import java.io.Serial;


public class EntityView extends GraphicalMCDNode {

    @Serial
    private static final long serialVersionUID = 5396347452110584370L;

    public static int entityNbr = 1;
    public EntityView(int x, int y, String text) {
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
