package com.MeriseGUI.flow;

import com.MeriseGUI.MPanel;
import com.graphics.GArrow;
import com.graphics.GNode;
import com.graphics.flow.Actor;
import com.graphics.flow.ActorType;
import com.graphics.flow.Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class FlowPanel extends MPanel<FlowGraphController, GNode, GArrow> implements MouseListener, MouseMotionListener {
    private JToolBar toolBar;
    private JButton btnInternalActor;
    private JButton btnExternalActor;
    private JButton btnDomain;
    private JButton btnFlow;

    public FlowPanel() {
        super(new FlowGraphController());
        createPanelPopupMenu();
        createNodePopupMenu();
        createLinkPopupMenu();
        setLayout(new BorderLayout());
        createToolBar();
        add(toolBar, BorderLayout.NORTH);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.graphController = new FlowGraphController();
        creatingLink = false;
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        btnInternalActor = createToolBarBtn("intern2", "Ajouter un acteur interne");
        btnExternalActor = createToolBarBtn("extern", "Ajouter un acteur externe");
        btnDomain = createToolBarBtn("domain", "Ajouter un domaine");
        btnFlow = createToolBarBtn("flow", "Ajouter un flux");
        toolBar.setOrientation(SwingConstants.HORIZONTAL);
        AddButtonActionListeners();
    }

    private JButton createToolBarBtn(String icon, String toolTip) {
        JButton btn = new JButton(new ImageIcon(new ImageIcon("icons/" + icon + ".png").getImage().getScaledInstance(100, 35, Image.SCALE_SMOOTH)));
        btn.setFocusable(false);
        btn.setToolTipText(toolTip);
        toolBar.add(btn);
        return btn;
    }

    private void AddButtonActionListeners() {
        btnInternalActor.addActionListener((ActionEvent e) -> createActor(ActorType.INTERNAL_ACTOR));
        btnExternalActor.addActionListener((ActionEvent e) -> createActor(ActorType.EXTERNAL_ACTOR));
        btnDomain.addActionListener((ActionEvent e) -> createDomain());
        btnFlow.addActionListener((ActionEvent e) -> createFlow());
    }

    public void createNodePopupMenu() {
        this.nodePopupMenu = new JPopupMenu();

        JMenuItem renameNodeMenuItem = new JMenuItem("Renommer");
        this.nodePopupMenu.add(renameNodeMenuItem);
        renameNodeMenuItem.addActionListener((action) -> {
            String newName = JOptionPane.showInputDialog(this, "Veuillez entrer le nouveau nom");
            if (newName == null || newName.trim().isEmpty())
                return;
            graphController.rename(nodeUnderCursor, newName);
            repaint();
        });

        JMenuItem removeNodeMenuItem = new JMenuItem("Supprimer");
        this.nodePopupMenu.add(removeNodeMenuItem);
        removeNodeMenuItem.addActionListener((action) -> {
            graphController.removeNode(nodeUnderCursor);
            repaint();
        });
    }

    @Override
    protected void createPanelPopupMenu() {
        this.panelPopupMenu = new JPopupMenu();

        JMenuItem addInternalActorMenuItem = new JMenuItem("Ajouter un acteur interne");
        this.panelPopupMenu.add(addInternalActorMenuItem);
        addInternalActorMenuItem.addActionListener((action) -> createActor(ActorType.INTERNAL_ACTOR));

        JMenuItem addExternalActorMenuItem = new JMenuItem("Ajouter un acteur externe");
        this.panelPopupMenu.add(addExternalActorMenuItem);
        addExternalActorMenuItem.addActionListener((action) -> createActor(ActorType.EXTERNAL_ACTOR));

        JMenuItem addDomainMenuItem = new JMenuItem("Ajouter un domaine");
        this.panelPopupMenu.add(addDomainMenuItem);
        addDomainMenuItem.addActionListener((action) -> createDomain());

        JMenuItem addLinkMenuItem = new JMenuItem("Ajouter un lien");
        this.panelPopupMenu.add(addLinkMenuItem);
        addLinkMenuItem.addActionListener((action) -> createFlow());
    }

    public void createActor(ActorType type) {
        Actor actor = new Actor(getMouseCoordinates().x, getMouseCoordinates().y, "Actor", type);
        afterCreation(actor);
    }

    public void createDomain() {
        Domain domain = new Domain(getMouseCoordinates().x, getMouseCoordinates().y, "Domain");
        afterCreation(domain);
    }

    public void afterCreation(GNode gNode) {
        nodeUnderCursor = gNode;
        assert gNode != null;
        graphController.addNode(gNode);
        setNodeAsSelected(gNode);
        repaint();
    }

    public Point getMouseCoordinates() {
        return new Point((int) this.getMousePosition().getX(), (int) this.getMousePosition().getY());
    }

    public void createFlow() {
        this.creatingLink = true;
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

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if (!creatingLink || nodeUnderCursor instanceof Domain)
            return;

        if (sourceNode == null) {
            sourceNode = nodeUnderCursor;
            tmpLink.setLine(sourceNode.getX(), sourceNode.getY(), e.getX(), e.getY());
            linkCreationStarted = true;
        } else if (targetNode == null)
            targetNode = nodeUnderCursor;

        if (sourceNode == null || targetNode == null)
            return;

        linkCreationStarted = false;

//        if (((Actor) sourceNode.getType() == targetNode.getType()) && (targetNode.getType().equals(ActorType.EXTERNAL_ACTOR))) {
//            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas attacher deux acteurs externes entre eux");
//        } else {
        graphController.addLink(sourceNode, targetNode);
        repaint();
//        }
        this.reset();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        nodeUnderCursor = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {

        if (nodeUnderCursor == null) {
            nodeUnderCursor = graphController.containsNode(e.getX(), e.getY());
            return;
        } else if (nodeUnderCursor instanceof Domain domain && domain.inCorner(e.getX(), e.getY())) {
            domain.resize(e.getX(), e.getY());
            repaint();
        } else
            this.moveNodeUnderCursor(e.getX(), e.getY());
        
        draggingFinished();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (linkCreationStarted) {
            tmpLink.setLine(sourceNode.getX(), sourceNode.getY(), e.getX(), e.getY());
            repaint();
            return;
        }

        if (graphController.containsNode(e.getX(), e.getY()) instanceof Domain domain && domain.inCorner(e.getX(), e.getY())) {
            setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
            return;
        }

        nodeUnderCursor = null;
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
