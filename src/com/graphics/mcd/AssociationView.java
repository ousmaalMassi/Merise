package com.graphics.mcd;

import java.awt.*;

public class AssociationView extends GraphicalMCDNode {
    
    public static int associationNbr = 1;
    
    public AssociationView(int x, int y, String text) {
        super(x, y, text);
        super.arc = 30;
        associationNbr++;
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
