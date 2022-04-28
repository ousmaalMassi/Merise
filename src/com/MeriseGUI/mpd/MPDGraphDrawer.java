package com.MeriseGUI.mpd;

import java.awt.*;
import java.util.List;

public class MPDGraphDrawer {

    private List<GraphicalMPDTable> graphicalMPDTables;
    private List<GraphicalMPDLink> graphicalMPDLinks;


    public MPDGraphDrawer() {
    }

    public void draw(Graphics2D graphics2D) {
        this.graphicalMPDLinks.forEach(edge -> edge.draw(graphics2D));
        this.graphicalMPDTables.forEach(node -> node.draw(graphics2D));
    }
}
