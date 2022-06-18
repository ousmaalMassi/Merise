package com.MeriseGUI.flow;

import com.MeriseGUI.GraphicalNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class FlowPanel extends JPanel implements MouseListener, MouseMotionListener {
    private JPopupMenu panelPopupMenu;
    private JPopupMenu nodePopupMenu;
    private JPopupMenu linkPopupMenu;
    private final FlowGraphDrawer graphDrawer;
    private InternalActor internalActor;
    private ExternalActor externalActor;
    private GraphicalNode nodeUnderCursor;
    private Flow linkUnderCursor;
    private boolean creatingLink;

    public FlowPanel() {
        createPanelPopupMenu();
        createNodePopupMenu();
        createLinkPopupMenu();

        addMouseListener(this);
        addMouseMotionListener(this);
        this.graphDrawer = new FlowGraphDrawer();
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

    private void createNodePopupMenu() {
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

    private void createPanelPopupMenu() {
        this.panelPopupMenu = new JPopupMenu();

        JMenuItem addInternalActorMenuItem = new JMenuItem("Ajouter une acteur interne");
        this.panelPopupMenu.add(addInternalActorMenuItem);
        addInternalActorMenuItem.addActionListener((action) -> {
            InternalActor internalActor = createInternalActor();
            nodeUnderCursor = internalActor;
            graphDrawer.addInternalActor(internalActor);
            repaint();
        });

        JMenuItem addExternalActorMenuItem = new JMenuItem("Ajouter une acteur externe");
        this.panelPopupMenu.add(addExternalActorMenuItem);
        addExternalActorMenuItem.addActionListener((action) -> {
            ExternalActor externalActor = createExternalActor();
            nodeUnderCursor = externalActor;
            graphDrawer.addExternalActor(externalActor);
            repaint();
        });

        JMenuItem addDomainMenuItem = new JMenuItem("Ajouter une domaine");
        this.panelPopupMenu.add(addDomainMenuItem);
        addDomainMenuItem.addActionListener((action) -> {
            Domain domain = createDomain();
            nodeUnderCursor = domain;
            graphDrawer.addDomain(domain);
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
        nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
        linkUnderCursor = graphDrawer.containsLink(e.getX(), e.getY());

        if (e.getButton() == MouseEvent.BUTTON3) {
            if (nodeUnderCursor != null)
                this.nodePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            else if (linkUnderCursor != null)
                this.linkPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            else
                this.panelPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }

        if (creatingLink) {
            if (nodeUnderCursor instanceof InternalActor internalActor)
                this.internalActor = internalActor;
            else if (nodeUnderCursor instanceof ExternalActor externalActor)
                this.externalActor = externalActor;
            if (this.externalActor != null && this.internalActor != null) {
                graphDrawer.addLink(this.externalActor, this.internalActor);
                repaint();
                this.externalActor = null;
                this.internalActor = null;
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
        if (nodeUnderCursor == null) {
            nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());
        }
        else if (nodeUnderCursor instanceof Domain domain && domain.inCorner(e.getX(), e.getY())) {
            domain.resize(e.getX(), e.getY());
            repaint();
        }
        else
            this.moveNodeUnderCursor(e.getX(), e.getY());
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

    private void moveNodeUnderCursor(int x, int y) {
        nodeUnderCursor.move(x, y);
        repaint();
    }
}
