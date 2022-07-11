package com.MeriseGUI.gdf;

import com.MeriseGUI.MPanel;
import com.MeriseGUI.ddd.DDPanel;
import com.graphics.gdf.GNodeGDF;
import com.graphics.gdf.GSimpleDF;
import com.graphics.gdf.GDFAttribute;
import com.models.gdf.GDFGraph;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

public class GDFPanel extends MPanel<GDFGraphController, GNodeGDF, GSimpleDF> implements MouseListener, MouseMotionListener {
    private final JList<Object> jListAttribute;
    private GNodeGDF gNodeGDF1;
    private GNodeGDF gNodeGDF2;
    private boolean creatingLink;
    private Vector<String> dictionaryData;
    private String dfType;

    public GDFPanel() {
        super(new GDFGraphController());
        createPanelPopupMenu();
        createNodePopupMenu();
        createLinkPopupMenu();

        addMouseListener(this);
        addMouseMotionListener(this);

        this.graphController.setGraph(new GDFGraph());
        this.creatingLink = false;
        this.jListAttribute = new JList<>();
    }

    @Override
    protected void createNodePopupMenu() {
        this.nodePopupMenu = new JPopupMenu();

        JMenuItem removeNodeMenuItem = new JMenuItem("Supprimer");
        this.nodePopupMenu.add(removeNodeMenuItem);
        removeNodeMenuItem.addActionListener((action) -> {
            graphController.remove(nodeUnderCursor);
            DDPanel.setUsedInGDF(nodeUnderCursor.getName(), false);
            repaint();
        });

        JMenuItem addSimpleDFMenuItem = new JMenuItem("Ajouter une DF simple");
        this.nodePopupMenu.add(addSimpleDFMenuItem);
        addSimpleDFMenuItem.addActionListener((action) -> {
            this.dfType = "GSimpleDF";
            this.creatingLink = true;
        });

        JMenuItem addComposedDFMenuItem = new JMenuItem("Ajouter une DF composée");
        this.nodePopupMenu.add(addComposedDFMenuItem);
        addComposedDFMenuItem.addActionListener((action) -> {
            this.dfType = "GComposedDF";
            this.creatingLink = true;
        });

        JMenuItem addTrivialDFMenuItem = new JMenuItem("Ajouter une DF trivial");
        this.nodePopupMenu.add(addTrivialDFMenuItem);
        addTrivialDFMenuItem.addActionListener((action) -> {
            this.dfType = "GTrivialDF";
            this.creatingLink = true;
        });
    }
    @Override
    protected void createPanelPopupMenu() {
        this.panelPopupMenu = new JPopupMenu();

        JMenuItem addAttributeMenuItem = new JMenuItem("Ajouter un attribut");
        this.panelPopupMenu.add(addAttributeMenuItem);
        addAttributeMenuItem.addActionListener((action) -> {
            double MousePositionX = this.getMousePosition().getX();
            double MousePositionY = this.getMousePosition().getY();

            dictionaryData = DDPanel.getDataForGDF();
            jListAttribute.setListData(dictionaryData);
            JOptionPane.showMessageDialog(null, new JScrollPane(jListAttribute));
            List<Object> selectedValuesList = jListAttribute.getSelectedValuesList();

            for (Object o : selectedValuesList) {
                String attributeName = o.toString();
                DDPanel.setUsedInGDF(attributeName, true);
                GDFAttribute gdfAttribute = createAttribute(MousePositionX, MousePositionY, attributeName);
                graphController.addNode(gdfAttribute);
                setNodeAsSelected(gdfAttribute);

            }
            repaint();
        });
    }

    @Override
    protected void createLinkPopupMenu() {
        this.linkPopupMenu = new JPopupMenu();

        JMenuItem removeLinkMenuItem = new JMenuItem("Supprimer");
        this.linkPopupMenu.add(removeLinkMenuItem);
        removeLinkMenuItem.addActionListener((action) -> {
            graphController.removeLink(linkUnderCursor);
            repaint();
        });
    }

    private GDFAttribute createAttribute(double MousePositionX, double MousePositionY, String name) {
        int x = (int) MousePositionX;
        int y = (int) MousePositionY;
        return new GDFAttribute(x, y, name);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if (creatingLink) {
            if (gNodeGDF1 == null) {
                gNodeGDF1 = nodeUnderCursor;
            } else if (gNodeGDF2 == null) {
                gNodeGDF2 = nodeUnderCursor;
            }
            if (gNodeGDF2 != null && gNodeGDF1 != null) {
                switch (dfType){
                    case "GSimpleDF" -> graphController.addLink(gNodeGDF1, gNodeGDF2);
                    case "GTrivialDF" -> graphController.addComposedTrivialDF(gNodeGDF1,gNodeGDF2);
//                    case "GComposedDF" -> graphController.addGComposedDF(gdfAttribute1, gdfAttribute2);
                }
                repaint();
                gNodeGDF2 = null;
                gNodeGDF1 = null;
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
            nodeUnderCursor = graphController.contains(e.getX(), e.getY());
        else
            this.moveNodeUnderCursor(e.getX(), e.getY());

        setNodeAsSelected(nodeUnderCursor);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public GDFGraph getGraph() {
        return this.graphController.getGraph();
    }

    public void setGraph(GDFGraph gdfGraph) {
        this.graphController.setGraph(gdfGraph);
    }
}
