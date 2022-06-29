package com.graphics;

import java.awt.*;

public class GLink extends GObject {

    protected static final int TOLERANCE = 7;
    protected GNode nodeA;
    protected GNode nodeB;
    private Color strokeColor;
    protected int xa;
    protected int ya;
    protected int xb;
    protected int yb;

    public GLink(GNode nodeA, GNode nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;

        this.strokeColor = Color.black;
    }

    public boolean contains(double mx, double my) {

        int nodeAX = nodeA.x;
        int nodeAY = nodeA.y;
        int nodeBX = nodeB.x;
        int nodeBY = nodeB.y;

        if (mx < Math.min(nodeAX, nodeBX) ||
                mx > Math.max(nodeAX, nodeBX)) {
            return false;
        }

        int A = nodeBY - nodeAY;
        int B = nodeBX - nodeAX;

        return Math.abs(A * mx - B * my + nodeBX * nodeAY - nodeBY * nodeAX) / Math.sqrt(A * A + B * B) <= TOLERANCE;
    }

    public GNode getNodeA() {
        return this.nodeA;
    }

    public GNode getNodeB() {
        return this.nodeB;
    }

    @Override
    public void draw(Graphics2D g) {
        xa = this.nodeA.x;
        ya = this.nodeA.y;
        xb = this.nodeB.x;
        yb = this.nodeB.y;

        g.setColor(strokeColor);
//        g.setStroke(stroke);
        g.drawLine(xa, ya, xb, yb);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            strokeColor = Color.red;
            stroke = SELECTED_STROKE;
        }
        else{
            strokeColor = Color.black;
            stroke = DEFAULT_STROKE;
        }
    }
}
