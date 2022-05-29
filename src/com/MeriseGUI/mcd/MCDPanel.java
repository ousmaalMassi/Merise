package com.MeriseGUI.mcd;

import com.MeriseGUI.ddd.DDPanel;
import com.exception.DuplicateMeriseObject;
import com.mcd.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MCDPanel extends JPanel implements MouseListener, MouseMotionListener {
    private final MCDGraph mcdGraph;
    private final MCDGraphDrawer graphDrawer;
    private JPopupMenu panelPopupMenu;
    private JPopupMenu nodePopupMenu;
    private AssociationView associationToLink;
    private EntityView entityToLink;
    private boolean creatingLink;
    private GraphicalMCDNode nodeUnderCursor;
    private Vector<String> dictionaryData;
    private final JList<Object> jListAttribute;

    public MCDPanel() {
        createPanelPopupMenu();
        createMCDObjectPopupMenu();
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
            graphDrawer.rename(nodeUnderCursor);
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
                graphDrawer.addProperty(nodeUnderCursor, attributeName);
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

    /**
     *
     */
    public MCDGraph showExampleGraph() {
        MCDGraph graph = new MCDGraph();

        Entity entity1 = new Entity("Client");
        entity1.setPropertyList(
                Stream.of(
                        new Property("id", Property.Types.DIGITAL, 11, List.of(Property.Constraints.AUTO_INCREMENT)),
                        new Property("nom", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("prénom", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("adresse", Property.Types.ALPHANUMERIC, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );

        Entity entity2 = new Entity("Article");
        entity2.setPropertyList(
                Stream.of(
                        new Property("id article", Property.Types.DIGITAL, 11, List.of(Property.Constraints.AUTO_INCREMENT)),
                        new Property("prix d'achat", Property.Types.ALPHANUMERIC, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("prix de vente", Property.Types.ALPHANUMERIC, 30, List.of(Property.Constraints.NOT_NULL)),
                        new Property("designation", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );

        Association association = new Association("Commander");
        /*association.setPropertyList(
                Stream.of(
                        new Property("quantity", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );*/

        try {
            graph.addEntity(entity1);
            graph.addEntity(entity2);
            graph.addAssociation(association);
        } catch (DuplicateMeriseObject e) {
            e.printStackTrace();
        }

        association.getLinks().put(entity1.getName(), Cardinalities.ONE_MANY);
        association.getLinks().put(entity2.getName(), Cardinalities.ONE_MANY);

        /*Entity entity3 = new Entity("Client");
        entity3.setPropertyList(
                Stream.of(
                        new Property("identifier", Property.Types.DIGITAL, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENT)),
                        new Property("name", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("phone", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("email", Property.Types.ALPHANUMERIC, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );*/

        return graph;
    }

    public MCDGraph getMcdGraph() {
        return mcdGraph;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        nodeUnderCursor = graphDrawer.contains(e.getX(), e.getY());

        if (e.getButton() == MouseEvent.BUTTON3 && nodeUnderCursor != null) {
            this.nodePopupMenu.show(e.getComponent(), e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            this.panelPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }

        if (creatingLink) {
            if (nodeUnderCursor instanceof EntityView entityView)
                entityToLink = entityView;
            else if (nodeUnderCursor instanceof AssociationView associationView)
                associationToLink = associationView;

//            System.out.println("entityToLink: "+entityToLink);
//            System.out.println("associationToLink: "+associationToLink);
//            System.out.println();
//            if ((entityToLink == null && associationToLink != null) || (entityToLink != null && associationToLink == null)) {
            if (entityToLink != null && associationToLink != null) {
                graphDrawer.addLink(entityToLink, associationToLink);
                repaint();
                entityToLink = null;
                associationToLink = null;
                creatingLink = false;

            }
        }
//        else if (creatingLink && associationToLink != null && entityToLink != null)


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
