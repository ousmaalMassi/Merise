package com.graphics.flow;

import com.graphics.GraphicalNode;

import java.awt.*;

public class Actor extends GraphicalNode{

    protected static BasicStroke DASHED_STROKE = new BasicStroke(
            1.5f,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            10.0f,
            new float[]{10.0f},
            0.0f);
    protected static BasicStroke DEFAULT_STROKE = new BasicStroke(1.5f);
    
    protected int totalPadding;
    protected static int actorNbr = 1;


    public Actor(int x, int y, String name) {
        super(x, y, name);
        actorNbr++;
        totalPadding = 60;
    }

    @Override
    public void draw(Graphics2D g) {
        FontMetrics fm = g.getFontMetrics();
        width = fm.stringWidth(name) + totalPadding;
        height = width/10 + fm.getHeight();

        shiftedX = x - width/2;
        shiftedY = y - height/2;
        int textX = shiftedX + totalPadding/2;
        int textY = shiftedY + height/2 + fm.getHeight()/4;

        g.setColor(Color.white);
        g.fillOval(shiftedX, shiftedY, width, height);
        
        g.setColor(strokeColor);
        
        g.drawOval(shiftedX, shiftedY, width, height);
        g.drawString(name, textX, textY);
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
        return (Math.pow((x- shiftedX)/width-0.5, 2) + Math.pow((y- shiftedY)/height-0.5, 2)) < 0.25;
    }
     
    @Override
    public String toString() {
        return "[" + name + "]: (" + x + ", " + y + ") ";

    }
}
