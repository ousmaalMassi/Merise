package com.graphics.flow;

import com.graphics.GNode;
import com.graphics.NodeSizeController;

import java.awt.*;

/**
 *
 * @author rpc
 */
public class Domain extends GNode implements NodeSizeController {
    private static final int DEFAULT_RESIZE = 20;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    public Domain(int x, int y, String name) {
        super(x, y, name);
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }
    
    @Override
    public void draw(Graphics2D g){
        shiftedX = x-width/2;
        shiftedY = y-height/2;

        g.setColor(Color.white);
        g.fillRect(shiftedX, shiftedY, width, height);
        
        g.setColor(strokeColor);
//        g.setStroke(stroke);
        
        g.drawRect(shiftedX, shiftedY, width, height);
        g.drawString(name, shiftedX, shiftedY -5);
    }

    @Override
    public boolean contains(double x, double y) {
        if (this.shiftedX > x || this.shiftedY > y) return false;
        return ( Math.abs(this.x-x) <= width/2 ) && ( Math.abs(this.y-y) <= height/2 );
    }

    public boolean inCorner(int x, int y) {
        return ( Math.abs(x- shiftedX -width) <= DEFAULT_RESIZE ) && ( Math.abs(y- shiftedY -height) <= DEFAULT_RESIZE );
    }

    public void resize(int x, int y) {
        width = Math.abs(shiftedX -x);
        height = Math.abs(shiftedY -y);
        
        this.x = shiftedX +width/2;
        this.y = shiftedY +height/2;
    }
    
}
