package com.MeriseGUI.flow;

import com.MeriseGUI.GraphicalNode;

import java.awt.*;

/**
 *
 * @author rpc
 */
public class Domain extends GraphicalNode {

    private static final int MAX_RESIZE = 100;
    private static final int DEFAULT_RESIZE = 7;
    protected static int domainNbr = 1;
    private int resizeMargin;
    
    public Domain(int x, int y, int width, int height, String name) {
        super(x, y, name);
        this.width = width;
        this.height = height;
        domainNbr++;
        resizeMargin = DEFAULT_RESIZE;
    }
    @Override
    public boolean contains(double x, double y) {
        return ( Math.abs(this.x-x) <= width/2 ) && ( Math.abs(this.y-y) <= height/2 );
    }
    
    
    @Override
    public boolean inCorner(int x, int y) {
        return ( Math.abs(x-pulledX-width) <= resizeMargin ) && ( Math.abs(y-pulledY-height) <= resizeMargin );
    }
    
    @Override
    public void draw(Graphics2D g){
        pulledX = x-width/2;
        pulledY = y-height/2;

        g.setColor(Color.white);
        g.fillRect(pulledX, pulledY, width, height);
        
        g.setColor(strokeColor);
        g.setStroke(strokeWidth);
        
        g.drawRect(pulledX, pulledY, width, height);
        g.drawString(name, pulledX, pulledY-5);
    }

    @Override
    public void resize(int x, int y) {
        resizeMargin = MAX_RESIZE;
        width = Math.abs(pulledX-x);
        height = Math.abs(pulledY-y);
        
        this.x = pulledX+width/2;
        this.y = pulledY+height/2;
    }

    public void resetResizeMargin() {
        resizeMargin = DEFAULT_RESIZE;
    }
    
}
