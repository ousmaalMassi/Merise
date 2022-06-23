package com.MeriseGUI.gdf;

import com.MeriseGUI.GraphicalNode;
import com.MeriseGUI.ddd.DDPanel;
import com.model.gdf.GDFGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

public class GDFPanel extends JPanel implements MouseListener, MouseMotionListener {
    private final GDFGraph gdfGraph;
    private final GDFGraphDrawer graphDrawer;
    private final JList<Object> jListAttribute;
    private JPopupMenu panelPopupMenu;
    private JPopupMenu nodePopupMenu;
    private JPopupMenu linkPopupMenu;
    private GDFAttribute gdfAttribute1;
    private GDFAttribute gdfAttribute2;
    private boolean creatingLink;
    private GDFAttribute nodeUnderCursor;
    private GDFAttribute lastSelectedNode;
    private DF linkUnderCursor;
    private Vector<String> dictionaryData;

    public GDFPanel() {
        createPanelPopupMenu();
        createNodePopupMenu();
        createLinkPopupMenu();
        addMouseListener(this);
        addMouseMotionListener(this);

        this.graphDrawer = new GDFGraphDrawer();
        this.gdfGraph = new GDFGraph();
        this.graphDrawer.setGraph(this.gdfGraph);
        this.creatingLink = false;
        this.jListAttribute = new JList<>();
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

    private void createNodePopupMenu() {
        this.nodePopupMenu = new JPopupMenu();

        JMenuItem renameNodeMenuItem = new JMenuItem("Renommer");
        this.nodePopupMenu.add(renameNodeMenuItem);
        renameNodeMenuItem.addActionListener((action) -> {
            graphDrawer.rename(nodeUnderCursor, "new Name");
            repaint();
        });

        JMenuItem removeNodeMenuItem = new JMenuItem("Supprimer");
        this.nodePopupMenu.add(removeNodeMenuItem);
        removeNodeMenuItem.addActionListener((action) -> {
            graphDrawer.remove(nodeUnderCursor);
            DDPanel.setUsedInGDF(nodeUnderCursor.getName(), false);
            repaint();
        });
    }

    private void createPanelPopupMenu() {
        this.panelPopupMenu = new JPopupMenu();

        JMenuItem addEntityMenuItem = new JMenuItem("Ajouter un nœud");
        this.panelPopupMenu.add(addEntityMenuItem);
        addEntityMenuItem.addActionListener((action) -> {
            double MousePositionX = this.getMousePosition().getX();
            double MousePositionY = this.getMousePosition().getY();

            dictionaryData = DDPanel.getGDFAttributes();
            jListAttribute.setListData(dictionaryData);
            JOptionPane.showMessageDialog(null, new JScrollPane(jListAttribute));
            List<Object> selectedValuesList = jListAttribute.getSelectedValuesList();

            for (Object o : selectedValuesList) {
                String attributeName = o.toString();
                DDPanel.setUsedInGDF(attributeName, true);
                GDFAttribute gdfAttribute = createAttribute(MousePositionX, MousePositionY, attributeName);
                graphDrawer.addAttribute(gdfAttribute);
                setNodeAsSelected(gdfAttribute);

            }
            repaint();
        });

        JMenuItem addLinkMenuItem = new JMenuItem("Ajouter un lien");
        this.panelPopupMenu.add(addLinkMenuItem);
        addLinkMenuItem.addActionListener((action) -> this.creatingLink = true);

    }

    private void createLinkPopupMenu() {
        this.linkPopupMenu = new JPopupMenu();

        JMenuItem removeLinkMenuItem = new JMenuItem("Supprimer");
        this.linkPopupMenu.add(removeLinkMenuItem);
        removeLinkMenuItem.addActionListener((action) -> {
            graphDrawer.removeLink(linkUnderCursor);
            repaint();
        });
    }

    private GDFAttribute createAttribute(double MousePositionX, double MousePositionY, String name) {
        int x = (int) MousePositionX;
        int y = (int) MousePositionY;
        return new GDFAttribute(x, y, name);
    }

    public GDFGraph getGraph() {
        return gdfGraph;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
        linkUnderCursor = graphDrawer.containsLink(e.getX(), e.getY());

        setNodeAsSelected(nodeUnderCursor);

        if (e.getButton() == MouseEvent.BUTTON3 || e.getClickCount() == 2) {
            if (nodeUnderCursor != null)
                this.nodePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            else if (linkUnderCursor != null)
                this.linkPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            else
                this.panelPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }

        if (creatingLink) {
            if (gdfAttribute1 == null) {
                gdfAttribute1 = nodeUnderCursor;
            } else if (gdfAttribute2 == null) {
                gdfAttribute2 = nodeUnderCursor;
            }
            if (gdfAttribute2 != null && gdfAttribute1 != null) {
                graphDrawer.addLink(gdfAttribute1, gdfAttribute2);
                repaint();
                gdfAttribute2 = null;
                gdfAttribute1 = null;
                nodeUnderCursor = null;
                creatingLink = false;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        nodeUnderCursor = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (nodeUnderCursor == null)
            nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
        else
            this.moveNodeUnderCursor(e.getX(), e.getY());

        setNodeAsSelected(nodeUnderCursor);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private void setNodeAsSelected(GDFAttribute nodeUnderCursor) {

        if (nodeUnderCursor != null && lastSelectedNode == null) {
            lastSelectedNode = nodeUnderCursor;
            lastSelectedNode.setSelected(true);
            repaint();
        } else if (nodeUnderCursor != null) {
            lastSelectedNode.setSelected(false);
            lastSelectedNode = nodeUnderCursor;
            lastSelectedNode.setSelected(true);
            repaint();
        } else if (lastSelectedNode != null) {
            lastSelectedNode.setSelected(false);
        }

    }

    private void moveNodeUnderCursor(int x, int y) {
        nodeUnderCursor.move(x, y);
        repaint();
    }
}
