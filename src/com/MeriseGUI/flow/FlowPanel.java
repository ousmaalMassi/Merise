package com.MeriseGUI.flow;

import com.MeriseGUI.GArrow;
import com.MeriseGUI.GraphicalNode;
import com.MeriseGUI.MPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class FlowPanel extends MPanel<FlowGraphController, GraphicalNode, GArrow> implements MouseListener, MouseMotionListener {
    private Actor sourceActor;
    private Actor targetActor;
    private boolean creatingLink;

    public FlowPanel() {
        super(new FlowGraphController());
        createPanelPopupMenu();
        createNodePopupMenu();
        createLinkPopupMenu();

        addMouseListener(this);
        addMouseMotionListener(this);
        this.graphDrawer= new FlowGraphController();
        creatingLink = false;
    }

    public void createNodePopupMenu() {
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
    }

    @Override
    protected void createPanelPopupMenu() {
        this.panelPopupMenu = new JPopupMenu();

        JMenuItem addInternalActorMenuItem = new JMenuItem("Ajouter une acteur interne");
        this.panelPopupMenu.add(addInternalActorMenuItem);
        addInternalActorMenuItem.addActionListener((action) -> {
            InternalActor internalActor = createInternalActor();
            nodeUnderCursor = internalActor;
            graphDrawer.addNode(internalActor);
            setNodeAsSelected(internalActor);
            repaint();
        });

        JMenuItem addExternalActorMenuItem = new JMenuItem("Ajouter une acteur externe");
        this.panelPopupMenu.add(addExternalActorMenuItem);
        addExternalActorMenuItem.addActionListener((action) -> {
            ExternalActor externalActor = createExternalActor();
            nodeUnderCursor = externalActor;
            graphDrawer.addNode(externalActor);
            setNodeAsSelected(externalActor);
            repaint();
        });

        JMenuItem addDomainMenuItem = new JMenuItem("Ajouter une domaine");
        this.panelPopupMenu.add(addDomainMenuItem);
        addDomainMenuItem.addActionListener((action) -> {
            Domain domain = createDomain();
            nodeUnderCursor = domain;
            graphDrawer.addNode(domain);
            setNodeAsSelected(domain);
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

    private InternalActor createInternalActor() {
        int x = (int) this.getMousePosition().getX();
        int y = (int) this.getMousePosition().getY();
        return new InternalActor(x, y, "InternalActor");
    }

    private ExternalActor createExternalActor() {
        int x = (int) this.getMousePosition().getX();
        int y = (int) this.getMousePosition().getY();
        return new ExternalActor(x, y, "ExternalActor");
    }

    private Domain createDomain() {
        int x = (int) this.getMousePosition().getX();
        int y = (int) this.getMousePosition().getY();
        return new Domain(x, y,"Domain");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if (!creatingLink || nodeUnderCursor instanceof Domain)
            return;

        if (sourceActor == null)
            sourceActor = (Actor) nodeUnderCursor;
        else if (targetActor == null)
            targetActor = (Actor) nodeUnderCursor;

        if (sourceActor == null || targetActor == null)
            return;

        if ((sourceActor.getClass() == targetActor.getClass()) && (targetActor instanceof ExternalActor)) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas attacher deux acteurs externes entre eux");
        } else {
            graphDrawer.addLink(sourceActor, targetActor);
            repaint();
        }
        targetActor = null;
        sourceActor = null;
        nodeUnderCursor = null;
        creatingLink = false;
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
        if (nodeUnderCursor == null) {
            nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
        }
        else if (nodeUnderCursor instanceof Domain domain && domain.inCorner(e.getX(), e.getY())) {
            domain.resize(e.getX(), e.getY());
            repaint();
        }
        else
            this.moveNodeUnderCursor(e.getX(), e.getY());

        setNodeAsSelected(nodeUnderCursor);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (nodeUnderCursor == null) {
            nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
        }
        if (nodeUnderCursor instanceof Domain domain) {
            if ( domain.inCorner(e.getX(), e.getY()))
                setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
            else
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        nodeUnderCursor = null;
    }
}
