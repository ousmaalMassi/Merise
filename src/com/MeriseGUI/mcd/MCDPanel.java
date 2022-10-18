package com.MeriseGUI.mcd;

import com.MeriseGUI.MPanel;
import com.MeriseGUI.ddd.DDPanel;
import com.graphics.mcd.GAssociation;
import com.graphics.mcd.GEntity;
import com.graphics.mcd.GMCDLink;
import com.graphics.mcd.GMCDNode;
import com.models.mcd.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
    private JToolBar toolBar;
    private JButton btnEntity;
    private JButton btnAssociation;
    private JButton btnLink;

    public MCDPanel() {
        super(new MCDGraphController());
        createPanelPopupMenu();
        createNodePopupMenu();
        createLinkPopupMenu();
        setLayout(new BorderLayout());
        createToolBar();
        add(toolBar, BorderLayout.NORTH);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.graphController.setGraph(new MCDGraph());
        this.creatingLink = false;
        this.jListAttribute = new JList<>();
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        btnEntity = createToolBarBtn("entity", "Ajouter une entité");
        btnAssociation = createToolBarBtn("association", "Ajouter une association");
        btnLink = createToolBarBtn("link", "Ajouter un lien");
        toolBar.setOrientation(SwingConstants.HORIZONTAL);
        AddButtonActionListeners();
    }

    private JButton createToolBarBtn(String icon, String toolTip) {
        JButton btn = new JButton(new ImageIcon(new ImageIcon("icons/" + icon + ".png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        btn.setFocusable(false);
        btn.setToolTipText(toolTip);
        toolBar.add(btn);
        return btn;
    }

    private void AddButtonActionListeners() {
        btnEntity.addActionListener((ActionEvent e) -> createMcdObject("GEntity"));
        btnAssociation.addActionListener((ActionEvent e) -> createMcdObject("GAssociation"));
        btnLink.addActionListener((ActionEvent e) -> creatingLink());
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
            graphController.removeNode(nodeUnderCursor);
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
        addEntityMenuItem.addActionListener((action) -> createMcdObject("GEntity"));

        JMenuItem addAssociationMenuItem = new JMenuItem("Ajouter une Association");
        this.panelPopupMenu.add(addAssociationMenuItem);
        addAssociationMenuItem.addActionListener((action) -> createMcdObject("GAssociation"));

        JMenuItem addLinkMenuItem = new JMenuItem("Ajouter un lien");
        this.panelPopupMenu.add(addLinkMenuItem);
        addLinkMenuItem.addActionListener((action) -> creatingLink());

    }

    private void creatingLink() {
        this.creatingLink = true;
    }

    public void createMcdObject(String type) {

        int x = (int) this.getMousePosition().getX();
        int y = (int) this.getMousePosition().getY();
        GMCDNode gmcdNode = null;
        switch (type) {
            case "GEntity" -> gmcdNode = new GEntity(x, y, "");
            case "GAssociation" -> gmcdNode = new GAssociation(x, y, "");
        }
        nodeUnderCursor = gmcdNode;
        assert gmcdNode != null;
        graphController.addNode(gmcdNode);
        setNodeAsSelected(gmcdNode);
        repaint();
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

    @Override
    public void mouseClicked(MouseEvent e) {
        nodeUnderCursor = graphController.containsNode(e.getX(), e.getY());
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
            nodeUnderCursor = graphController.containsNode(e.getX(), e.getY());
        else
            this.moveNodeUnderCursor(e.getX(), e.getY());

        setNodeAsSelected(nodeUnderCursor);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        nodeUnderCursor = graphController.containsNode(e.getX(), e.getY());
    }

    public MCDGraph getGraph() {
        return this.graphController.getGraph();
    }

    public void setGraph(MCDGraph gdfGraph) {
        this.graphController.setGraph(gdfGraph);
    }
}
