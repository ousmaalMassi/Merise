package com.MeriseGUI.gdf;

import com.MeriseGUI.MPanel;
import com.MeriseGUI.ddd.DDPanel;
import com.graphics.gdf.GDFAttribute;
import com.graphics.gdf.GNodeGDF;
import com.graphics.gdf.GSimpleDF;
import com.models.gdf.GDFGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

public class GDFPanel extends MPanel<GDFGraphController, GNodeGDF, GSimpleDF> implements MouseListener, MouseMotionListener {
    private final JList<Object> jListAttribute;
    private String dfType;
    private JToolBar toolBar;
    private JButton btnSimpleDF;
    private JButton btnTrivialDF;
    private JButton btnNonTrivialDF;
    private JButton btnAttribute;

    public GDFPanel() {
        super(new GDFGraphController());
        createPanelPopupMenu();
        createNodePopupMenu();
        createLinkPopupMenu();
        setLayout(new BorderLayout());
        createToolBar();
        add(toolBar, BorderLayout.NORTH);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.graphController.setGraph(new GDFGraph());
        this.creatingLink = false;
        this.jListAttribute = new JList<>();
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        btnSimpleDF = createToolBarBtn("simple", "DF Simple");
        btnTrivialDF = createToolBarBtn("trivial", "DF Composé triviale");
        btnNonTrivialDF = createToolBarBtn("non_trivial", "DF Composé non triviale");
        btnAttribute = createToolBarBtn("attribute", "Ajouter un attribut");
        toolBar.setOrientation(SwingConstants.HORIZONTAL);
        AddButtonActionListeners();
    }

    private JButton createToolBarBtn(String icon, String toolTip) {
        JButton btn = new JButton(new ImageIcon(new ImageIcon("icons/" + icon + ".png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        btn.setFocusable(false);
        btn.setToolTipText(toolTip);
        toolBar.add(btn);
        return btn;
    }

    private void AddButtonActionListeners() {
        btnSimpleDF.addActionListener((ActionEvent e) -> createLink("GSimpleDF"));
        btnTrivialDF.addActionListener((ActionEvent e) -> createLink("GComposedTrivialDF"));
        btnNonTrivialDF.addActionListener((ActionEvent e) -> createLink("GComposedNonTrivialDF"));
        btnAttribute.addActionListener((ActionEvent e) -> addAttribute());
    }

    @Override
    protected void createNodePopupMenu() {
        this.nodePopupMenu = new JPopupMenu();

        JMenuItem removeNodeMenuItem = new JMenuItem("Supprimer");
        this.nodePopupMenu.add(removeNodeMenuItem);
        removeNodeMenuItem.addActionListener((action) -> {
            graphController.removeNode(nodeUnderCursor);
            DDPanel.setUsedInGDF(nodeUnderCursor.getName(), false);
            repaint();
        });

        JMenuItem addSimpleDFMenuItem = new JMenuItem("Ajouter une DF simple");
        this.nodePopupMenu.add(addSimpleDFMenuItem);
        addSimpleDFMenuItem.addActionListener((action) -> createLink("GSimpleDF"));

        JMenuItem addTrivialDFMenuItem = new JMenuItem("Ajouter une DF composée trivial");
        this.nodePopupMenu.add(addTrivialDFMenuItem);
        addTrivialDFMenuItem.addActionListener((action) -> createLink("GComposedTrivialDF"));

        JMenuItem addNonTrivialDFMenuItem = new JMenuItem("Ajouter une DF composée non-trivial");
        this.nodePopupMenu.add(addNonTrivialDFMenuItem);
        addNonTrivialDFMenuItem.addActionListener((action) -> createLink("GComposedNonTrivialDF"));
    }

    public void createLink(String type) {
        this.dfType = type;
        this.creatingLink = true;
    }

    @Override
    protected void createPanelPopupMenu() {
        this.panelPopupMenu = new JPopupMenu();

        JMenuItem addAttributeMenuItem = new JMenuItem("Ajouter un attribut");
        this.panelPopupMenu.add(addAttributeMenuItem);
        addAttributeMenuItem.addActionListener((action) -> addAttribute());
    }

    private void addAttribute() {
        double MousePositionX = this.getMousePosition().getX();
        double MousePositionY = this.getMousePosition().getY();

        Vector<String> dictionaryData = DDPanel.getDataForGDF();
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
//        if (gNodeGDF1 == null) gNodeGDF1 = nodeUnderCursor;

        if (!creatingLink)
            return;

        if (sourceNode == null) {
            sourceNode = nodeUnderCursor;
            tmpLink.setLine(sourceNode.getX(), sourceNode.getY(), e.getX(), e.getY());
            linkCreationStarted = true;
        } else
        if (targetNode == null) {
            targetNode = nodeUnderCursor;
        }
        if (targetNode != null && sourceNode != null) {
            switch (dfType){
                case "GSimpleDF" -> graphController.addLink(sourceNode, targetNode);
                case "GComposedTrivialDF" -> graphController.addComposedTrivialDF(sourceNode, targetNode, "Trivial");
                case "GComposedNonTrivialDF" -> graphController.addComposedTrivialDF(sourceNode, targetNode, "Non_trivial");
            }
            repaint();
            this.reset();
        }
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
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (nodeUnderCursor == null) {
            nodeUnderCursor = graphController.containsNode(e.getX(), e.getY());
            return;
        } else
            this.moveNodeUnderCursor(e.getX(), e.getY());
        
        draggingFinished();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (linkCreationStarted) {
            tmpLink.setLine(sourceNode.getX(),sourceNode.getY(),e.getX(),e.getY());
            repaint();
        }
    }

    public GDFGraph getGraph() {
        return this.graphController.getGraph();
    }

    public void setGraph(GDFGraph gdfGraph) {
        this.graphController.setGraph(gdfGraph);
    }
}
