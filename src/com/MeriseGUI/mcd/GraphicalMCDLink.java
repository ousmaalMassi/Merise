package com.MeriseGUI.mcd;

import com.MeriseGUI.GLinkText;
import com.MeriseGUI.GraphicalLink;
import com.MeriseGUI.GraphicalNode;

import java.awt.*;

public class GraphicalMCDLink extends GraphicalLink implements GLinkText {
    private String card;
    private int cardWidth;
    private int cardHeight;
    private int cardX;
    private int cardY;

    public GraphicalMCDLink(EntityView a, AssociationView b) {
        super(a, b);
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
        drawText(g);
    }

    public void setText(String card) {
        this.card = card;
    }

    @Override
    public void drawText(Graphics2D g) {
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

    public boolean underText(double mx, double my) {
        return ( Math.abs(cardX - mx) <= cardWidth + TOLERANCE ) && ( Math.abs(cardY -my) <= cardHeight + TOLERANCE );
    }

    @Override
    public String toString() {
        return "(" + nodeA.toString() + ") ===> (" + nodeB.toString() + ") ";
    }
}