/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeriseGUI;

import java.awt.*;

public class GraphicalLink {

    protected static final int TOLERANCE = 7;

    protected static BasicStroke UNSELECTED_STROKE = new BasicStroke(1.2f);
    protected static BasicStroke SELECTED_STROKE = new BasicStroke(2);

    protected GraphicalNode nodeA;
    protected GraphicalNode nodeB;
    private Color strokeColor;
    private Stroke stroke;
    public int xa;
    public int ya;
    public int xb;
    public int yb;

    public GraphicalLink(GraphicalNode nodeA, GraphicalNode nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        
        this.strokeColor = Color.black;
        this.stroke = UNSELECTED_STROKE;
    }

    public boolean contains(double mx, double my) {

        int nodeAX = nodeA.getX();
        int nodeAY = nodeA.getY();
        int nodeBX = nodeB.getX();
        int nodeBY = nodeB.getY();

        if (mx < Math.min(nodeAX, nodeBX) ||
                mx > Math.max(nodeAX, nodeBX)) {
            return false;
        }

        int A = nodeBY - nodeAY;
        int B = nodeBX - nodeAX;

        return Math.abs(A * mx - B * my + nodeBX * nodeAY - nodeBY * nodeAX) / Math.sqrt(A * A + B * B) <= TOLERANCE;
    }

    public GraphicalNode getNodeA() {
        return this.nodeA;
    }

    public GraphicalNode getNodeB() {
        return this.nodeB;
    }

    public void draw(Graphics2D g) {
        xa = this.nodeA.getX();
        ya = this.nodeA.getY();
        xb = this.nodeB.getX();
        yb = this.nodeB.getY();
            
        g.setColor(strokeColor);
        g.setStroke(stroke);
        g.drawLine(xa, ya, xb, yb);
    }

    protected void move(int dx, int dy) {
        nodeA.move(dx, dy);
        nodeB.move(dx, dy);
    }

    public void setSelected(boolean selected) {
        if (selected) {
            strokeColor = Color.red;
            stroke = SELECTED_STROKE;
        }
        else{
            strokeColor = Color.black;
            stroke = UNSELECTED_STROKE;
        }
    }
}
