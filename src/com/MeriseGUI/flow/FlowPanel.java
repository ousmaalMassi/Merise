package com.MeriseGUI.flow;

import com.MeriseGUI.GraphicalNode;
import com.MeriseGUI.mcd.AssociationView;
import com.MeriseGUI.mcd.EntityView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class FlowPanel extends JPanel implements MouseListener, MouseMotionListener {
    private JPopupMenu panelPopupMenu;
    private JPopupMenu nodePopupMenu;
    private final FlowGraphDrawer graphDrawer;
    private InternalActor internalActor;
    private ExternalActor externalActor;
    private GraphicalNode nodeUnderCursor;
    private boolean creatingLink;

    public FlowPanel() {
        createPanelPopupMenu();
        createMCDObjectPopupMenu();
        setBackground(Color.GRAY);
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

    private void createMCDObjectPopupMenu() {
        this.nodePopupMenu = new JPopupMenu();

        JMenuItem renameNodeMenuItem = new JMenuItem("Renommer");
        this.nodePopupMenu.add(renameNodeMenuItem);
        renameNodeMenuItem.addActionListener((action) -> {
            graphDrawer.rename(nodeUnderCursor);
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
            graphDrawer.addInternalActor(createInternalActor());
            repaint();
        });

        JMenuItem addExternalActorMenuItem = new JMenuItem("Ajouter une acteur externe");
        this.panelPopupMenu.add(addExternalActorMenuItem);
        addExternalActorMenuItem.addActionListener((action) -> {
            graphDrawer.addExternalActor(createExternalActor());
            repaint();
        });

        JMenuItem addDomainMenuItem = new JMenuItem("Ajouter une domaine");
        this.panelPopupMenu.add(addDomainMenuItem);
        addDomainMenuItem.addActionListener((action) -> {
            graphDrawer.addDomain(createDomain());
            repaint();
        });

        JMenuItem addLinkMenuItem = new JMenuItem("Ajouter un lien");
        this.panelPopupMenu.add(addLinkMenuItem);
        addLinkMenuItem.addActionListener((action) -> this.creatingLink = true);

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
        return new Domain(x, y, 50, 50,"Domain");
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
            if (nodeUnderCursor instanceof InternalActor internalActor)
                this.internalActor = internalActor;
            else if (nodeUnderCursor instanceof ExternalActor externalActor)
                this.externalActor = externalActor;
            if (this.externalActor != null && this.internalActor != null) {
                graphDrawer.addLink(this.externalActor, this.internalActor);
                repaint();
                this.externalActor = null;
                this.internalActor = null;
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
