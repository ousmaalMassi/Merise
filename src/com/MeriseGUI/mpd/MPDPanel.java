package com.MeriseGUI.mpd;

import com.mpd.MPDGraph;

import javax.swing.*;
import java.awt.*;

public class MPDPanel extends JPanel {
    private MPDGraph mpdGraph;
    MPDGraphDrawer graphDrawer;
    public MPDPanel() {
        mpdGraph = new MPDGraph();
        graphDrawer = new MPDGraphDrawer();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1.2f));

        if (graphDrawer != null)
            graphDrawer.draw(g2d);
    }

    public void setMpdGraph(MPDGraph mpdGraph) {
        this.mpdGraph = mpdGraph;

    }
}
