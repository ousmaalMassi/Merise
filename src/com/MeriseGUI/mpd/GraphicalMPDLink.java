package com.MeriseGUI.mpd;

import com.MeriseGUI.GraphicalLink;

import java.awt.*;

public class GraphicalMPDLink extends GraphicalLink {

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