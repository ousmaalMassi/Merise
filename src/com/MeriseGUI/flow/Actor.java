package com.MeriseGUI.flow;

import com.MeriseGUI.GraphicalNode;

import java.awt.*;
import java.io.Serial;


enum NodeType {
    INTERNAL_NODE("Internal node"),
    EXTERNAL_NODE("External node");

    final String nodeType;

    NodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public String toString() {
        return nodeType;
    }
}

public class Actor extends GraphicalNode {

    @Serial
    private static final long serialVersionUID = -7357466511459361679L;
    
    protected static BasicStroke DASHED_STROKE = new BasicStroke(
            2,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER, 
            10.0f, 
            new float[]{10.0f}, 
            0.0f);
    
    protected int totalPadding;
    protected NodeType nodeType;
    protected static int actorNbr = 1;


    public Actor(int x, int y, String name) {
        super(x, y, name);
        actorNbr++;
        totalPadding = 60;
    }

    public int getTotalPadding() {
        return totalPadding;
    }

    public void setTotalPadding(int totalPadding) {
        this.totalPadding = totalPadding;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public void draw(Graphics2D g) {
        FontMetrics fm = g.getFontMetrics();
        width = fm.stringWidth(name) + totalPadding;
        height = width/10 + fm.getHeight();

        pulledX = x - width/2;
        pulledY = y - height/2;
        int textX = pulledX + totalPadding/2;
        int textY = pulledY + height/2 + fm.getHeight()/4;

        g.setColor(Color.white);
        g.fillOval(pulledX, pulledY, width, height);
        
        g.setColor(strokeColor);
        g.setStroke(nodeType == NodeType.EXTERNAL_NODE? DASHED_STROKE : strokeWidth);
        
        g.drawOval(pulledX, pulledY, width, height);
        g.drawString(name, textX, textY);
    }

    @Override
     public boolean contains(double x, double y) {
        if (width <= 0 || height <= 0) return false;
        return (Math.pow((x-pulledX)/width-0.5, 2) + Math.pow((y-pulledY)/height-0.5, 2)) < 0.25;
    }
     
    @Override
    public String toString() {
        return "[" + nodeType.toString() + "]: (" + x + ", " + y + ") ";

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
