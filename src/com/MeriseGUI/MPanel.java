package com.MeriseGUI;

import com.graphics.GLink;
import com.graphics.GNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class MPanel<T extends GraphController, N extends GNode, L extends GLink>  extends JPanel {
    protected JPopupMenu panelPopupMenu;
    protected JPopupMenu nodePopupMenu;
    protected JPopupMenu linkPopupMenu;
    protected N nodeUnderCursor;
    protected N lastSelectedNode;
    protected L linkUnderCursor;
    protected L lastSelectedLink;
    protected T graphDrawer;

    public MPanel(T graphDrawer) {
        createPanelPopupMenu();
        createNodePopupMenu();

        this.graphDrawer = graphDrawer;
    }

    protected abstract void createPanelPopupMenu();

    protected abstract void createNodePopupMenu();

    protected abstract void createLinkPopupMenu();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1.2f));

        if (graphDrawer != null)
            graphDrawer.draw(g2d);
    }

    public void mouseClicked(MouseEvent e) {
        nodeUnderCursor = (N) graphDrawer.contains(e.getX(), e.getY());
        linkUnderCursor = (L) graphDrawer.containsLink(e.getX(), e.getY());

        setNodeAsSelected(nodeUnderCursor);
        setLinkAsSelected(linkUnderCursor);

        if (e.getButton() == MouseEvent.BUTTON3 || e.getClickCount() == 2) {
            if (nodeUnderCursor != null)
                this.nodePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            else if (linkUnderCursor != null)
                this.linkPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            else
                this.panelPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    protected void moveNodeUnderCursor(int x, int y) {
        nodeUnderCursor.move(x, y);
        repaint();
    }

    protected void setNodeAsSelected(N nodeUnderCursor) {

        if (nodeUnderCursor != null && lastSelectedNode == null) {
            lastSelectedNode = nodeUnderCursor;
            lastSelectedNode.setSelected(true);
            repaint();
        }
        if (nodeUnderCursor == null && lastSelectedNode != null) {
            lastSelectedNode.setSelected(false);
            repaint();
        } else if (nodeUnderCursor != null) {
            lastSelectedNode.setSelected(false);
            lastSelectedNode = nodeUnderCursor;
            lastSelectedNode.setSelected(true);
            repaint();
        }

    }

    private void setLinkAsSelected(L linkUnderCursor) {
        if (linkUnderCursor != null && lastSelectedLink == null) {
            lastSelectedLink = linkUnderCursor;
            lastSelectedLink.setSelected(true);
            repaint();
        }
        if (linkUnderCursor == null && lastSelectedLink != null) {
            lastSelectedLink.setSelected(false);
            repaint();
        } else if (linkUnderCursor != null) {
            lastSelectedLink.setSelected(false);
            lastSelectedLink = linkUnderCursor;
            lastSelectedLink.setSelected(true);
            repaint();
        }
    }
}
