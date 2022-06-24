package com.MeriseGUI.mcd;

import com.MeriseGUI.GraphicalLink;
import com.MeriseGUI.GraphicalNode;

import java.awt.*;


public class GraphicalMCDLink extends GraphicalLink {

    private static final int TOLERANCE = 7;
    private String card;
    private int cardWidth;
    private int cardHeight;
    private int cardX;
    private int cardY;
    //public static int linkNbr = 0;

    public GraphicalMCDLink(EntityView a, AssociationView b) {
        super(a, b);
        //linkNbr++;
    }

    public GraphicalNode getEntityView() {
        return this.nodeA;
    }

    public GraphicalNode getAssociationView() {
        return this.nodeB;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        FontMetrics metrics = g.getFontMetrics();

        int xt = xa + (xb - xa) / 2;
        int yt = ya + (yb - ya + metrics.getAscent()) / 2;

        cardWidth = metrics.stringWidth(card) + 5;
        cardHeight = metrics.getAscent();
        cardX = xt - 5;
        cardY = yt - metrics.getAscent();
        g.setColor(Color.white);
        g.fillRect(cardX, cardY, cardWidth, cardHeight);

        g.setColor(Color.black);
        g.drawString(card, xt, yt);
    }

    @Override
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

    public boolean underCard(double mx, double my) {
        return ( Math.abs(cardX - mx) <= cardWidth + TOLERANCE ) && ( Math.abs(cardY -my) <= cardHeight + TOLERANCE );
    }

    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "(" + nodeA.toString() + ") ===> (" + nodeB.toString() + ") ";
    }
}