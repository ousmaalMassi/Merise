package com.MeriseGUI.mpd;

import com.MeriseGUI.GraphicalLink;

import java.awt.*;

public class GraphicalMPDLink extends GraphicalLink {

    private static final long serialVersionUID = -6972652167790425200L;

    public GraphicalMPDLink(GraphicalMPDTable a, GraphicalMPDTable b) {
        super(a,b);
    }
    
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
	
    @Override
    public String toString() {
            return "(" + nodeA.toString() + ") ===> (" + nodeB.toString() + ") ";
    }
}