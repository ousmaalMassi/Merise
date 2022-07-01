package com.MeriseGUI.mcd;

import com.MeriseGUI.MPanel;
import com.MeriseGUI.ddd.DDPanel;
import com.graphics.mcd.GAssociation;
import com.graphics.mcd.GEntity;
import com.graphics.mcd.GMCDLink;
import com.graphics.mcd.GMCDNode;
import com.models.mcd.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

public class MCDPanel extends MPanel<MCDGraphController, GMCDNode, GMCDLink> implements MouseListener, MouseMotionListener {

    private GAssociation associationToLink;
    private GEntity entityToLink;
    private boolean creatingLink;
    private Vector<String> dictionaryData;
    private final JList<Object> jListAttribute;

    public MCDPanel() {
        super(new MCDGraphController());
        createPanelPopupMenu();
        createNodePopupMenu();
        createLinkPopupMenu();

        addMouseListener(this);
        addMouseMotionListener(this);

        this.graphController.setGraph(new MCDGraph());
        this.creatingLink = false;
        this.jListAttribute = new JList<>();
    }

    @Override
    protected void createNodePopupMenu() {
        this.nodePopupMenu = new JPopupMenu();

        JMenuItem renameNodeMenuItem = new JMenuItem("Renommer");
        this.nodePopupMenu.add(renameNodeMenuItem);
        renameNodeMenuItem.addActionListener((action) -> {
            String newName = JOptionPane.showInputDialog(this, "Veuillez entrer le nouveau nom");
            if (newName == null || newName.trim().isEmpty())
                return;
            for (String attributeName : nodeUnderCursor.getAttributes())
                DDPanel.setUsedInMCD(attributeName, newName);
            graphController.rename(nodeUnderCursor, newName);
            repaint();
        });

        JMenuItem removeNodeMenuItem = new JMenuItem("Supprimer");
        this.nodePopupMenu.add(removeNodeMenuItem);
        removeNodeMenuItem.addActionListener((action) -> {
            for (String attributeName : nodeUnderCursor.getAttributes())
                DDPanel.setUsedInMCD(attributeName, "");
            graphController.remove(nodeUnderCursor);
            repaint();
        });

        this.nodePopupMenu.addSeparator();
        /**/
        JMenuItem editAttributeMenuItem = new JMenuItem("Ajouter des attributs");
        this.nodePopupMenu.add(editAttributeMenuItem);
        editAttributeMenuItem.addActionListener((action) -> {

            dictionaryData = DDPanel.getDataForMCD();
            jListAttribute.setListData(dictionaryData);
            JOptionPane.showMessageDialog(null, new JScrollPane(jListAttribute));
            List<Object> selectedValuesList = jListAttribute.getSelectedValuesList();

            for (Object o : selectedValuesList) {
                String attributeName = o.toString();
                DDPanel.setUsedInMCD(attributeName, nodeUnderCursor.getName());
                graphController.addProperty(attributeName, nodeUnderCursor);
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
                graphController.removeProperty(attributeName, nodeUnderCursor);
            }
            repaint();
        });
    }

    @Override
    protected void createPanelPopupMenu() {
        this.panelPopupMenu = new JPopupMenu();

        JMenuItem addEntityMenuItem = new JMenuItem("Ajouter une Entité");
        this.panelPopupMenu.add(addEntityMenuItem);
        addEntityMenuItem.addActionListener((action) -> {
            GEntity entity = createEntity();
            graphController.addNode(entity);
            setNodeAsSelected(entity);
            repaint();
        });

        JMenuItem addAssociationMenuItem = new JMenuItem("Ajouter une Association");
        this.panelPopupMenu.add(addAssociationMenuItem);
        addAssociationMenuItem.addActionListener((action) -> {
            GAssociation association = createAssociation();
            graphController.addNode(association);
            setNodeAsSelected(association);
            repaint();
        });

        JMenuItem addLinkMenuItem = new JMenuItem("Ajouter un lien");
        this.panelPopupMenu.add(addLinkMenuItem);
        addLinkMenuItem.addActionListener((action) -> this.creatingLink = true);

    }

    @Override
    protected void createLinkPopupMenu() {
        this.linkPopupMenu = new JPopupMenu();

        JMenuItem editCardMenuItem = new JMenuItem("Modifier");
        this.linkPopupMenu.add(editCardMenuItem);
        editCardMenuItem.addActionListener((action) -> {
            JComboBox<Cardinality> card = new JComboBox<>(Cardinality.values());
            JOptionPane.showMessageDialog(null, new JScrollPane(card));
            graphController.editCard(linkUnderCursor, card.getSelectedIndex());
            repaint();
        });

        JMenuItem removeLinkMenuItem = new JMenuItem("Supprimer");
        this.linkPopupMenu.add(removeLinkMenuItem);
        removeLinkMenuItem.addActionListener((action) -> {
            graphController.removeLink(linkUnderCursor);
            repaint();
        });
    }

    private void editCard() {
            JComboBox<Cardinality> card = new JComboBox<>(Cardinality.values());
            JOptionPane.showMessageDialog(null, new JScrollPane(card));
            graphController.editCard(linkUnderCursor, card.getSelectedIndex());
            repaint();
    }

    private GEntity createEntity() {
        int x = (int) this.getMousePosition().getX();
        int y = (int) this.getMousePosition().getY();
        return new GEntity(x, y, "");
    }

    private GAssociation createAssociation() {
        int x = (int) this.getMousePosition().getX();
        int y = (int) this.getMousePosition().getY();
        return new GAssociation(x, y, "");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        nodeUnderCursor = graphController.contains(e.getX(), e.getY());
        linkUnderCursor = graphController.containsLink(e.getX(), e.getY());

        setNodeAsSelected(nodeUnderCursor);

        if (e.getClickCount() == 2 || e.getButton() == MouseEvent.BUTTON3) {
            if (nodeUnderCursor != null) {
                this.nodePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            } else if (linkUnderCursor != null) {
                if(linkUnderCursor.underText(e.getX(), e.getY()))
                    this.editCard();
                else
                    this.linkPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            } else
                this.panelPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }

        if (creatingLink) {
            if (nodeUnderCursor instanceof GEntity GEntity)
                entityToLink = GEntity;
            else if (nodeUnderCursor instanceof GAssociation GAssociation)
                associationToLink = GAssociation;
            if (entityToLink != null && associationToLink != null) {
                graphController.addLink(entityToLink, associationToLink);
                repaint();
                entityToLink = null;
                associationToLink = null;
                creatingLink = false;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        nodeUnderCursor = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

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
        nodeUnderCursor = graphController.contains(e.getX(), e.getY());
    }

    public MCDGraph getGraph() {
        return this.graphController.getGraph();
    }

    public void setGraph(MCDGraph gdfGraph) {
        this.graphController.setGraph(gdfGraph);
    }
}
