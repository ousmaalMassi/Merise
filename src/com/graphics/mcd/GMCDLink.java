package com.graphics.mcd;

import com.graphics.GLinkText;
import com.graphics.GLink;
import com.graphics.GNode;

import java.awt.*;

public class GMCDLink extends GLink implements GLinkText {
    private String card;
    private int cardWidth;
    private int cardHeight;
    private int cardX;
    private int cardY;

    public GMCDLink(GEntity a, GAssociation b) {
        super(a, b);
    }

    public GNode getEntityView() {
        return this.nodeA;
    }

    public GNode getAssociationView() {
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