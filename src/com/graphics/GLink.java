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
    protected double angle;

    public GLink(GNode nodeA, GNode nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;

        this.strokeColor = Color.black;
    }

    public boolean contains(double mx, double my) {

        if (mx < Math.min(xa, xb) ||
                mx > Math.max(xa, xb)) {
            return false;
        }

        int A = yb - ya;
        int B = xb - xa;

        return Math.abs(A * mx - B * my + xb * ya - yb * xa) / Math.sqrt(A * A + B * B) <= TOLERANCE;
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
        angle = Math.atan2(yb - ya, xb - xa );
        xb += (int) (nodeB.width/2 * -Math.cos(angle));
        yb += (int) (nodeB.height/2 * -Math.sin(angle));

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
