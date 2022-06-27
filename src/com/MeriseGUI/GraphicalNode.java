/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeriseGUI;

import java.awt.*;
import java.io.Serializable;

/**
 *
 * @author rpc
 */
public abstract class GraphicalNode implements Serializable{
    
    protected static BasicStroke UNSELECTED_STROKE = new BasicStroke(1.2f);
    protected static BasicStroke SELECTED_STROKE = new BasicStroke(2);
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int pulledX;
    protected int pulledY;
    protected String name;
    protected boolean selected;
    protected Color strokeColor;
    protected Stroke strokeWidth;
    
    public GraphicalNode(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.setName(name);
        this.strokeColor = Color.black;
        this.strokeWidth = UNSELECTED_STROKE;
    }

    public boolean contains(double x, double y) {
        return false;
    }

    public void draw(Graphics2D g) {}

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

    public int getPulledX() {
        return pulledX;
    }

    public int getPulledY() {
        return pulledY;
    }

    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.equals("") ?  this.name : name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        
        if (selected) {
            strokeColor = Color.red;
            strokeWidth = SELECTED_STROKE;
        }else{
            strokeColor = Color.black;
            strokeWidth = UNSELECTED_STROKE;
        }
    }

    public abstract boolean inCorner(int x, int y);

    public abstract void resize(int x, int y);

}
