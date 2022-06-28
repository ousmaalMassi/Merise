/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeriseGUI;

import java.awt.*;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author rpc
 */
public abstract class GraphicalNode implements Serializable{

    protected static final Font FONT_SELECT = new Font(Font.DIALOG, Font.BOLD, 16);
    protected static final Font FONT_UNSELECT = new Font(Font.DIALOG, Font.PLAIN, 16);
    protected static BasicStroke UNSELECTED_STROKE = new BasicStroke(1.5f);
    protected static BasicStroke SELECTED_STROKE = new BasicStroke(2);
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int shiftedX;
    protected int shiftedY;
    protected String name;
    protected Color strokeColor;
    protected Stroke stroke;
    protected Font font;
    
    public GraphicalNode(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.setName(name);
        this.strokeColor = Color.black;
        this.stroke = UNSELECTED_STROKE;
        this.font = FONT_SELECT;
    }

    public abstract boolean contains(double x, double y);

    public abstract void draw(Graphics2D g);

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.equals("") ?  this.name : name;
    }

    public abstract void setSelected(boolean selected);
}
