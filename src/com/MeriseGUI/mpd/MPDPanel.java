package com.MeriseGUI.mpd;

import com.models.mpd.MPDGraph;
import com.graphics.mpd.GraphicalMPDTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MPDPanel extends JPanel implements MouseMotionListener {
    MPDGraphController graphDrawer;
    private GraphicalMPDTable nodeUnderCursor;

    public MPDPanel() {
        graphDrawer = new MPDGraphController();
        this.addMouseMotionListener(this);
        System.out.println("\uD83D\uDE00");
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
        graphDrawer.printMPD(mpdGraph);
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (nodeUnderCursor == null)
            nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
        else
            this.moveNodeUnderCursor(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
    }

    private void moveNodeUnderCursor(int x, int y) {
        this.nodeUnderCursor.move(x, y);
        repaint();
    }
}
