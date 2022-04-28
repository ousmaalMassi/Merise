package com.MeriseGUI.mcd;

import com.MeriseGUI.GraphicalLink;

import java.awt.*;


public class GraphicalMCDLink extends GraphicalLink {

    private static final long serialVersionUID = -6972652167790425200L;

    private String card;
    //public static int linkNbr = 0;

    public GraphicalMCDLink(EntityView a, AssociationView b) {
        super(a,b);
        //linkNbr++;
    }

    public GraphicalMCDLink(EntityView a, AssociationView b, String card) {
        super(a,b);
        this.card = card;
        //linkNbr++;
    }
    
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        FontMetrics metrics = g.getFontMetrics();

        int xt = xa+(xb-xa)/2;
        int yt = ya+(yb-ya+ metrics.getAscent())/2 ;

        g.setColor(Color.white);
        g.fillRect(xt-5, yt-metrics.getAscent(), metrics.stringWidth(card)+5, metrics.getAscent());

        g.setColor(Color.black);
        g.drawString(card, xt, yt);
    }

    public String getCard() {
        return card;
    }
    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public String toString() {
            return "(" + nodeA.toString() + ") ===> (" + nodeB.toString() + ") ";
    }

}