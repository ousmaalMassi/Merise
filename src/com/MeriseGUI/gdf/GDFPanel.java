package com.MeriseGUI.gdf;

import com.MeriseGUI.MPanel;
import com.MeriseGUI.ddd.DDPanel;
import com.graphics.gdf.DF;
import com.graphics.gdf.GDFAttribute;
import com.models.gdf.GDFGraph;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

public class GDFPanel extends MPanel<GDFGraphController, GDFAttribute, DF> implements MouseListener, MouseMotionListener {
    private final GDFGraph gdfGraph;
    private final JList<Object> jListAttribute;
    private GDFAttribute gdfAttribute1;
    private GDFAttribute gdfAttribute2;
    private boolean creatingLink;

    private Vector<String> dictionaryData;

    public GDFPanel() {
        super(new GDFGraphController());
        createPanelPopupMenu();
        createNodePopupMenu();
        createLinkPopupMenu();

        addMouseListener(this);
        addMouseMotionListener(this);

        this.gdfGraph = new GDFGraph();
        this.graphDrawer.setGraph(this.gdfGraph);
        this.creatingLink = false;
        this.jListAttribute = new JList<>();
    }

    @Override
    protected void createNodePopupMenu() {
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
    @Override
    protected void createPanelPopupMenu() {
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
                graphDrawer.addNode(gdfAttribute);
                setNodeAsSelected(gdfAttribute);

            }
            repaint();
        });

        JMenuItem addLinkMenuItem = new JMenuItem("Ajouter un lien");
        this.panelPopupMenu.add(addLinkMenuItem);
        addLinkMenuItem.addActionListener((action) -> this.creatingLink = true);

    }

    @Override
    protected void createLinkPopupMenu() {
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
        super.mouseClicked(e);

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

}
