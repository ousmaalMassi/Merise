package com.MeriseGUI.flow;

import com.MeriseGUI.GraphicalNode;

import java.awt.*;

/**
 *
 * @author rpc
 */
public class Domain extends GraphicalNode {
    private static final int DEFAULT_RESIZE = 20;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;
    protected static int domainNbr = 1;
    
    public Domain(int x, int y, int width, int height, String name) {
        super(x, y, name);
        this.width = width;
        this.height = height;
        domainNbr++;
    }

    public Domain(int x, int y, String name) {
        super(x, y, name);
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        domainNbr++;
    }
    @Override
    public boolean contains(double x, double y) {
        return ( Math.abs(this.x-x) <= width/2 ) && ( Math.abs(this.y-y) <= height/2 );
    }
    
    
    @Override
    public boolean inCorner(int x, int y) {
        return ( Math.abs(x-pulledX-width) <= DEFAULT_RESIZE ) && ( Math.abs(y-pulledY-height) <= DEFAULT_RESIZE );
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
        width = Math.abs(pulledX-x);
        height = Math.abs(pulledY-y);
        
        this.x = pulledX+width/2;
        this.y = pulledY+height/2;
    }
    
}
