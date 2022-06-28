package com.MeriseGUI.flow;

import com.MeriseGUI.GraphicalNode;
import com.MeriseGUI.NodeSizeControl;

import java.awt.*;

/**
 *
 * @author rpc
 */
public class Domain extends GraphicalNode implements NodeSizeControl {
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
        g.setStroke(stroke);
        
        g.drawRect(shiftedX, shiftedY, width, height);
        g.drawString(name, shiftedX, shiftedY -5);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            strokeColor = Color.red;
            stroke = SELECTED_STROKE;
            font = FONT_SELECT;
        }
        else{
            strokeColor = Color.black;
            stroke = UNSELECTED_STROKE;
            font = FONT_UNSELECT;
        }
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
