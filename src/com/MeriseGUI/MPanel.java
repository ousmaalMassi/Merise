package com.MeriseGUI;

import com.graphics.GLink;
import com.graphics.GNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.List;

public abstract class MPanel<T extends GraphController, N extends GNode, L extends GLink> extends JPanel {

    protected N sourceNode;
    protected N targetNode;
    protected JPopupMenu panelPopupMenu;
    protected JPopupMenu nodePopupMenu;
    protected JPopupMenu linkPopupMenu;
    protected transient N nodeUnderCursor;
    protected transient N lastSelectedNode;
    protected transient L linkUnderCursor;
    protected transient L lastSelectedLink;
    protected T graphController;
    protected Line2D tmpLink = new Line2D.Double(0, 0, 0, 0);
    protected boolean linkCreationStarted = false;
    protected boolean creatingLink;

    public MPanel(T graphController) {
        createPanelPopupMenu();
        createNodePopupMenu();

        this.graphController = graphController;
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

        if (linkCreationStarted)
            g2d.draw(tmpLink);

        if (graphController != null)
            graphController.printGraph(g2d);
    }

    public void mouseClicked(MouseEvent e) {
        nodeUnderCursor = (N) graphController.containsNode(e.getX(), e.getY());
        linkUnderCursor = (L) graphController.containsLink(e.getX(), e.getY());

        setNodeAsSelected(nodeUnderCursor);
        setLinkAsSelected(linkUnderCursor);

        if (e.getButton() == MouseEvent.BUTTON3 || e.getClickCount() == 2) {
            if (nodeUnderCursor != null) {
                this.nodePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            } else if (linkUnderCursor != null)
                this.linkPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            else
                this.panelPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    protected void reset () {
        linkCreationStarted = false;
        targetNode = null;
        sourceNode = null;
        nodeUnderCursor = null;
        creatingLink = false;
    }

    public void mouseReleased(MouseEvent e) {
        if (nodeUnderCursor != null)
            nodeUnderCursor.resetDiff();
    }

    public void draggingFinished() {
        if (!nodeUnderCursor.isSelected())
            setNodeAsSelected(nodeUnderCursor);
    }

    protected void moveNodeUnderCursor(int x, int y) {
        nodeUnderCursor.move(x, y);
        repaint();
    }

    protected void setNodeAsSelected(N nodeUnderCursor) {

        if (nodeUnderCursor != null && lastSelectedNode == null) {
            lastSelectedNode = nodeUnderCursor;
            lastSelectedNode.setSelected(true);
        } else if (nodeUnderCursor == null && lastSelectedNode != null) {
            lastSelectedNode.setSelected(false);
        } else if (nodeUnderCursor != null) {
            lastSelectedNode.setSelected(false);
            lastSelectedNode = nodeUnderCursor;
            lastSelectedNode.setSelected(true);
        }
        repaint();

    }

    private void setLinkAsSelected(L linkUnderCursor) {
        if (linkUnderCursor != null && lastSelectedLink == null) {
            lastSelectedLink = linkUnderCursor;
            lastSelectedLink.setSelected(true);
        } else if (linkUnderCursor == null && lastSelectedLink != null) {
            lastSelectedLink.setSelected(false);
        } else if (linkUnderCursor != null) {
            lastSelectedLink.setSelected(false);
            lastSelectedLink = linkUnderCursor;
            lastSelectedLink.setSelected(true);
        }
        repaint();
    }

    public List getNodes() {
        return graphController.getNodes();
    }

    public void setNodes(List<N> flowNodes) {
        this.graphController.setNodes(flowNodes);
    }

    public List<L> getLinks() {
        return graphController.getLinks();
    }

    public void setLinks(List<L> flowLinks) {
        this.graphController.setLinks(flowLinks);
    }
}
