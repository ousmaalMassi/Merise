package com.MeriseGUI.flow;

import com.graphics.GArrow;
import com.graphics.GNode;
import com.MeriseGUI.MPanel;
import com.graphics.flow.Actor;
import com.graphics.flow.Domain;
import com.graphics.flow.ExternalActor;
import com.graphics.flow.InternalActor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class FlowPanel extends MPanel<FlowGraphController, GNode, GArrow> implements MouseListener, MouseMotionListener {
    private Actor sourceActor;
    private Actor targetActor;
    private boolean creatingLink;
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
        btnInternalActor = createToolBarBtn("Screenshot", "nouveau fichier (CTRL+N)");
        btnExternalActor = createToolBarBtn("extern", "Ouvrir un nouveau projet (CTRL+O)");
        btnDomain = createToolBarBtn("domain", "Enregistrer sous (CTRL+SHIFT+S)");
        btnFlow = createToolBarBtn("flow", "Générer MCD à partir de GDF");
        toolBar.setOrientation(SwingConstants.HORIZONTAL);
        AddButtonActionListeners();
    }

    private JButton createToolBarBtn(String icon, String toolTip) {
        JButton btn = new JButton(new ImageIcon("icons/" + icon + ".png"));
        btn.setFocusable(false);
        btn.setToolTipText(toolTip);
        toolBar.add(btn);
        return btn;
    }

    private void AddButtonActionListeners() {
        btnInternalActor.addActionListener((ActionEvent e) -> createActor("INTERNAL_ACTOR"));
        btnExternalActor.addActionListener((ActionEvent e) -> createActor("EXTERNAL_ACTOR"));
        btnDomain.addActionListener((ActionEvent e) -> createActor("DOMAIN"));
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
            graphController.remove(nodeUnderCursor);
            repaint();
        });
    }

    @Override
    protected void createPanelPopupMenu() {
        this.panelPopupMenu = new JPopupMenu();

        JMenuItem addInternalActorMenuItem = new JMenuItem("Ajouter un acteur interne");
        this.panelPopupMenu.add(addInternalActorMenuItem);
        addInternalActorMenuItem.addActionListener((action) -> createActor("INTERNAL_ACTOR"));

        JMenuItem addExternalActorMenuItem = new JMenuItem("Ajouter un acteur externe");
        this.panelPopupMenu.add(addExternalActorMenuItem);
        addExternalActorMenuItem.addActionListener((action) -> createActor("EXTERNAL_ACTOR"));

        JMenuItem addDomainMenuItem = new JMenuItem("Ajouter un domaine");
        this.panelPopupMenu.add(addDomainMenuItem);
        addDomainMenuItem.addActionListener((action) -> createActor("DOMAIN"));

        JMenuItem addLinkMenuItem = new JMenuItem("Ajouter un lien");
        this.panelPopupMenu.add(addLinkMenuItem);
        addLinkMenuItem.addActionListener((action) -> createFlow());
    }

    public void createActor(String type) {
        int x = (int) this.getMousePosition().getX();
        int y = (int) this.getMousePosition().getY();
        GNode gNode = null;
        switch (type) {
            case "INTERNAL_ACTOR" -> gNode = new InternalActor(x, y, "InternalActor");
            case "EXTERNAL_ACTOR" -> gNode = new ExternalActor(x, y, "ExternalActor");
            case "DOMAIN" -> gNode = new Domain(x, y, "Domain");
        }
        nodeUnderCursor = gNode;
        assert gNode != null;
        graphController.addNode(gNode);
        setNodeAsSelected(gNode);
        repaint();
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

        if (sourceActor == null)
            sourceActor = (Actor) nodeUnderCursor;
        else if (targetActor == null)
            targetActor = (Actor) nodeUnderCursor;

        if (sourceActor == null || targetActor == null)
            return;

        if ((sourceActor.getClass() == targetActor.getClass()) && (targetActor instanceof ExternalActor)) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas attacher deux acteurs externes entre eux");
        } else {
            graphController.addLink(sourceActor, targetActor);
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
            nodeUnderCursor = graphController.contains(e.getX(), e.getY());
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
            nodeUnderCursor = graphController.contains(e.getX(), e.getY());
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
