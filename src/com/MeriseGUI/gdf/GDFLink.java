package com.MeriseGUI.gdf;

import com.MeriseGUI.GraphicalLink;
import com.MeriseGUI.GraphicalNode;

import java.awt.*;

public class GDFLink extends GraphicalLink {

    private static final long serialVersionUID = -6972652167790425200L;

    public GDFLink(GraphicalNode a, GraphicalNode b) {
        super(a, b);
    }
    
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
	
    @Override
    public String toString() {
        return "[ Flow]: (" + nodeA.toString() + ") ===> (" + nodeB.toString() + ") ";
    }
}