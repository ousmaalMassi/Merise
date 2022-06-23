package com.MeriseGUI.gdf;

import com.MeriseGUI.GraphicalNode;
import java.awt.*;
import java.io.Serial;

public class GDFAttribute extends GraphicalNode{

    @Serial
    private static final long serialVersionUID = -7357466511459361679L;

    protected int width;
    protected int height;
    protected int totalPadding;
    protected int shiftedX;
    protected int shiftedY;
    static int ActorNbr;
    private final Font font;


    public GDFAttribute(int x, int y, String name) {
        super(x, y, name);
        ActorNbr++;
        totalPadding = 20;
        font = new Font(Font.DIALOG, Font.PLAIN, 14);
    }
    
    public int getTotalPadding() {
        return totalPadding;
    }

    public void setTotalPadding(int totalPadding) {
        this.totalPadding = totalPadding;
    }
    
    @Override
    public void draw(Graphics2D g) {
        FontMetrics fm = g.getFontMetrics();
        width = fm.stringWidth(name);
        height = width/10 + fm.getHeight();

        shiftedX = x - width/2;
        shiftedY = y - height/2;
        int textX = shiftedX;
        int textY = shiftedY + height/2 + fm.getHeight()/4;
        g.setFont(font);
        g.setColor(strokeColor);
        g.drawString(name, textX, textY);
    }

    @Override
     public boolean contains(double x, double y) {
        if (width <= 0 || height <= 0) return false;
        return (Math.pow((x-shiftedX)/width-0.5, 2) + Math.pow((y-shiftedY)/height-0.5, 2)) < 1;
    }
     
    @Override
    public String toString() {
        return "(" + (x) + ", " + (y) + ") ";

    }

    @Override
    public boolean inCorner(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resize(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
