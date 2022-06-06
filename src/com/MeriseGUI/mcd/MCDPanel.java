package com.MeriseGUI.mcd;

import com.MeriseGUI.ddd.DDPanel;
import com.mcd.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

public class MCDPanel extends JPanel implements MouseListener, MouseMotionListener {
    private final MCDGraph mcdGraph;
    private final MCDGraphDrawer graphDrawer;
    private JPopupMenu panelPopupMenu;
    private JPopupMenu nodePopupMenu;
    private JPopupMenu linkPopupMenu;
    private AssociationView associationToLink;
    private EntityView entityToLink;
    private boolean creatingLink;
    private GraphicalMCDNode nodeUnderCursor;
    private GraphicalMCDLink linkUnderCursor;
    private Vector<String> dictionaryData;
    private final JList<Object> jListAttribute;

    public MCDPanel() {
        createPanelPopupMenu();
        createMCDObjectPopupMenu();
        createLinkPopupMenu();

        addMouseListener(this);
        addMouseMotionListener(this);

        this.graphDrawer = new MCDGraphDrawer();
        this.mcdGraph = new MCDGraph();
        this.graphDrawer.setMcdGraph(this.mcdGraph);
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

    private void createMCDObjectPopupMenu() {
        this.nodePopupMenu = new JPopupMenu();

        JMenuItem renameNodeMenuItem = new JMenuItem("Renommer");
        this.nodePopupMenu.add(renameNodeMenuItem);
        renameNodeMenuItem.addActionListener((action) -> {
            String newName = JOptionPane.showInputDialog(this, "Veuillez entrer le nouveau nom");
            if (newName == null || newName.trim().isEmpty())
                return;
            graphDrawer.rename(nodeUnderCursor, newName);
            repaint();
        });

        JMenuItem removeNodeMenuItem = new JMenuItem("Supprimer");
        this.nodePopupMenu.add(removeNodeMenuItem);
        removeNodeMenuItem.addActionListener((action) -> {
            graphDrawer.remove(nodeUnderCursor);
            repaint();
        });

        this.nodePopupMenu.addSeparator();
        /**/
        JMenuItem editAttributeMenuItem = new JMenuItem("Ajouter des attributs");
        this.nodePopupMenu.add(editAttributeMenuItem);
        editAttributeMenuItem.addActionListener((action) -> {

            dictionaryData = DDPanel.getMCDAttributes();
            jListAttribute.setListData(dictionaryData);
            JOptionPane.showMessageDialog(null, new JScrollPane(jListAttribute));
            List<Object> selectedValuesList = jListAttribute.getSelectedValuesList();

            for (Object o : selectedValuesList) {
                String attributeName = o.toString();
                DDPanel.setUsedInMCD(attributeName, nodeUnderCursor.getName());
                graphDrawer.addProperty(attributeName, nodeUnderCursor);
            }
            repaint();
        });

        JMenuItem deleteAttributeMenuItem = new JMenuItem("Supprimer des attributs");
        this.nodePopupMenu.add(deleteAttributeMenuItem);
        deleteAttributeMenuItem.addActionListener((action) -> {

            Object[] array = nodeUnderCursor.getAttributes().toArray();
            jListAttribute.setListData(array);
            JOptionPane.showMessageDialog(null, new JScrollPane(jListAttribute));
            List<Object> selectedValuesList = jListAttribute.getSelectedValuesList();

            for (Object o : selectedValuesList) {
                String attributeName = o.toString();
                DDPanel.setUsedInMCD(attributeName, "");
                graphDrawer.removeProperty(attributeName, nodeUnderCursor);
            }
            repaint();
        });
    }

    private void createPanelPopupMenu() {
        this.panelPopupMenu = new JPopupMenu();

        JMenuItem addEntityMenuItem = new JMenuItem("Ajouter une Entité");
        this.panelPopupMenu.add(addEntityMenuItem);
        addEntityMenuItem.addActionListener((action) -> {
            graphDrawer.addEntity(createEntity());
            repaint();
        });

        JMenuItem addAssociationMenuItem = new JMenuItem("Ajouter une Association");
        this.panelPopupMenu.add(addAssociationMenuItem);
        addAssociationMenuItem.addActionListener((action) -> {
            graphDrawer.addAssociation(createAssociation());
            repaint();
        });

        JMenuItem addLinkMenuItem = new JMenuItem("Ajouter un lien");
        this.panelPopupMenu.add(addLinkMenuItem);
        addLinkMenuItem.addActionListener((action) -> this.creatingLink = true);

    }

    private void createLinkPopupMenu() {
        this.linkPopupMenu = new JPopupMenu();

        JMenuItem editCardMenuItem = new JMenuItem("Modifier");
        this.linkPopupMenu.add(editCardMenuItem);
        editCardMenuItem.addActionListener((action) -> {
            JComboBox<Cardinality> card = new JComboBox<>(Cardinality.values());
            JOptionPane.showMessageDialog(null, new JScrollPane(card));
            graphDrawer.editCard(linkUnderCursor, card.getSelectedIndex());
            repaint();
        });

        JMenuItem removeLinkMenuItem = new JMenuItem("Supprimer");
        this.linkPopupMenu.add(removeLinkMenuItem);
        removeLinkMenuItem.addActionListener((action) -> {
            graphDrawer.removeLink(linkUnderCursor);
            repaint();
        });
    }

    private EntityView createEntity() {
        int x = (int) this.getMousePosition().getX();
        int y = (int) this.getMousePosition().getY();
        return new EntityView(x, y, "");
    }

    private AssociationView createAssociation() {
        int x = (int) this.getMousePosition().getX();
        int y = (int) this.getMousePosition().getY();
        return new AssociationView(x, y, "");
    }

    public MCDGraph getMcdGraph() {
        return mcdGraph;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
        linkUnderCursor = graphDrawer.containsLink(e.getX(), e.getY());

        if (e.getButton() == MouseEvent.BUTTON3) {
            if (nodeUnderCursor != null) {
                this.nodePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            } else if (linkUnderCursor != null) {
                this.linkPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            } else
                this.panelPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }

        if (creatingLink) {
            if (nodeUnderCursor instanceof EntityView entityView)
                entityToLink = entityView;
            else if (nodeUnderCursor instanceof AssociationView associationView)
                associationToLink = associationView;
            if (entityToLink != null && associationToLink != null) {
                graphDrawer.addLink(entityToLink, associationToLink);
                repaint();
                entityToLink = null;
                associationToLink = null;
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
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
    }

    private void moveNodeUnderCursor(int x, int y) {
        nodeUnderCursor.move(x, y);
        repaint();
    }
}
