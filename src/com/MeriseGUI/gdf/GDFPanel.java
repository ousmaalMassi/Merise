package com.MeriseGUI.gdf;

import com.MeriseGUI.ddd.DDPanel;
import com.gdf.GDFGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

public class GDFPanel extends JPanel implements MouseListener, MouseMotionListener {
    private final GDFGraph gdfGraph;
    private JPopupMenu panelPopupMenu;
    private JPopupMenu nodePopupMenu;
    private final GDFGraphDrawer graphDrawer;
    private GDFAttribute gdfAttribute1;
    private GDFAttribute gdfAttribute2;
    private boolean creatingLink;
    private GDFAttribute nodeUnderCursor;
    private Vector<String> dictionaryData;
    JList<Object> jListAttribute = new JList<>();

    public GDFPanel() {
        createPanelPopupMenu();
        createMCDObjectPopupMenu();
//        setBackground(Color.GRAY);
        addMouseListener(this);
        addMouseMotionListener(this);
        graphDrawer = new GDFGraphDrawer();
        gdfGraph = new GDFGraph();
        graphDrawer.setGraph(this.gdfGraph);
        creatingLink = false;
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

    private void createMCDObjectPopupMenu() {
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
                graphDrawer.addAttribute(createAttribute(MousePositionX, MousePositionY, attributeName));
            }
            repaint();
        });

        JMenuItem addLinkMenuItem = new JMenuItem("Ajouter un lien");
        this.panelPopupMenu.add(addLinkMenuItem);
        addLinkMenuItem.addActionListener((action) -> this.creatingLink = true);

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

        if (e.getButton() == MouseEvent.BUTTON3 && nodeUnderCursor != null) {
            this.nodePopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
            this.panelPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }

        if (creatingLink) {
            if (gdfAttribute1 == null) {
                gdfAttribute1 = nodeUnderCursor;
            }
            else if (gdfAttribute2 == null) {
                gdfAttribute2 = nodeUnderCursor;
            }
            if (gdfAttribute2 != null && gdfAttribute1 != null) {
                graphDrawer.addLink(gdfAttribute1, gdfAttribute2);
                repaint();
                gdfAttribute2 = null;
                gdfAttribute1 = null;
                creatingLink = false;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
        if (nodeUnderCursor == null)
            return;
        this.moveNodeUnderCursor(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private void moveNodeUnderCursor(int x, int y) {
        nodeUnderCursor.move(x, y);
        repaint();
    }
}
