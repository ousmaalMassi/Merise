package com.MeriseGUI.mcd;

import com.MeriseGUI.GraphicalNode;
import com.MeriseGUI.ddd.DDPanel;
import com.MeriseObject;
import com.exception.DuplicateMeriseObject;
import com.mcd.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MCDGraphDrawer {

    private final List<GraphicalMCDNode> nodes;
    private final List<GraphicalMCDLink> edges;
    private MCDGraph mcdGraph;

    public MCDGraphDrawer() {
        this.nodes = new LinkedList<>();
        this.edges = new LinkedList<>();
    }

    public void setMcdGraph(MCDGraph mcdGraph) {
        this.mcdGraph = mcdGraph;
    }

    public void draw(Graphics2D graphics2D) {
        this.edges.forEach(edge -> edge.draw(graphics2D));
        this.nodes.forEach(node -> node.draw(graphics2D));
    }

    public void addEntity(EntityView entityGUI) {
        int entityNo = mcdGraph.getEntities().size();
        String entityName = "Entity "+entityNo;
        Entity entity = new Entity(entityName);
        entityGUI.setName(entityName);
        try {
            mcdGraph.addEntity(entity);
            this.nodes.add(entityGUI);
        } catch (DuplicateMeriseObject e) {
            throw new RuntimeException(e);
        }
        System.out.println(mcdGraph);
    }

    public void addProperty(String propertyName, GraphicalMCDNode mcdNodeView) {

//        long currentTimeMillis = System.currentTimeMillis();
        MeriseObject meriseObject;
        if (mcdNodeView instanceof EntityView)
            meriseObject = mcdGraph.containsEntity(mcdNodeView.getName());
        else
            meriseObject = mcdGraph.containsAssociation(mcdNodeView.getName());

        Map<String, String> map = DDPanel.getProperty(propertyName);
        String name = map.get("name");
        Property.Types type = Property.Types.valueOf(map.get("type"));
        int length = Integer.parseInt(map.get("length"));
        ArrayList<Property.Constraints> constraints = new ArrayList<>();

        if (meriseObject.getPropertyList().isEmpty()) {
            constraints.addAll(Arrays.asList(
                    Property.Constraints.NOT_NULL
            ));
        }

        Property property =  new Property(name, type, length, constraints);
        meriseObject.addProperty(property);

        mcdNodeView.getAttributes().add(property.name);
        System.out.println(mcdGraph);
//        System.out.println(System.currentTimeMillis() - currentTimeMillis+" ms");
    }

    public void removeProperty(String name, GraphicalMCDNode mcdNodeView) {

        MeriseObject meriseObject;
        if (mcdNodeView instanceof EntityView)
            meriseObject = mcdGraph.containsEntity(mcdNodeView.getName());
        else
            meriseObject = mcdGraph.containsAssociation(mcdNodeView.getName());
        Property property = meriseObject.getPropertyList().stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);
        if (property == null)
            return;
        meriseObject.removeProperty(property);
        mcdNodeView.getAttributes().remove(property.name);
    }

    public void remove(GraphicalMCDNode mcdNodeView) {
        if (mcdNodeView == null)
            return;
        if (mcdNodeView instanceof EntityView)
            mcdGraph.removeEntity(mcdGraph.containsEntity(mcdNodeView.getName()));
        else
            mcdGraph.removeAssociation(mcdGraph.containsAssociation(mcdNodeView.getName()));
        this.nodes.remove(mcdNodeView);
        this.removeAttachedLinks(mcdNodeView);
        System.out.println(mcdGraph);
    }

    protected void removeAttachedLinks(GraphicalMCDNode nodeUnderCursor) {
        this.edges.removeIf(e -> e.getNodeA().equals(nodeUnderCursor) || e.getNodeB().equals(nodeUnderCursor) );
    }

    public void rename(GraphicalMCDNode mcdNodeView, String newName) {
        if (mcdNodeView == null)
            return;
        MeriseObject meriseObject;
        if (mcdNodeView instanceof EntityView)
            meriseObject = mcdGraph.containsEntity(mcdNodeView.getName());
        else
            meriseObject = mcdGraph.containsAssociation(mcdNodeView.getName());
        meriseObject.setName(newName);
        mcdNodeView.setName(newName);

        System.out.println(mcdGraph);
    }

    public void addAssociation(AssociationView associationView) {
        int AssociationNo = mcdGraph.getAssociations().size();
        String associationName = "Association "+AssociationNo;
        Association association = new Association(associationName);
        associationView.setName(associationName);
        try {
            mcdGraph.addAssociation(association);
            this.nodes.add(associationView);
        } catch (DuplicateMeriseObject e) {
            throw new RuntimeException(e);
        }
        System.out.println(mcdGraph);
    }

    public GraphicalMCDNode contains(int x, int y) {
        return this.nodes.stream().filter(node -> node.contains(x, y))
                .findAny()
                .orElse(null);
    }

    public GraphicalMCDLink containsLink(int x, int y) {
        return this.edges.stream().filter(edge -> edge.contains(x, y))
                .findAny()
                .orElse(null);
    }

    public void addLink(EntityView entityToLink, AssociationView associationToLink) {
        Entity entity = mcdGraph.containsEntity(entityToLink.getName());
        Association association = mcdGraph.containsAssociation(associationToLink.getName());

        mcdGraph.link(entity, association);

        GraphicalMCDLink graphicalLink = new GraphicalMCDLink(entityToLink, associationToLink);
        graphicalLink.setCard(Cardinality.DEFAULT_CARDINALITY.toString());
        this.edges.add(graphicalLink);

        System.out.println(mcdGraph);
    }

    public void removeLink(GraphicalMCDLink linkUnderCursor) {
        GraphicalNode associationView = linkUnderCursor.getAssociationView();
        GraphicalNode entityView = linkUnderCursor.getEntityView();

        this.mcdGraph.unlink(associationView.getName(), entityView.getName());
        this.edges.remove(linkUnderCursor);

        System.out.println(mcdGraph);
    }

    public void editCard(GraphicalMCDLink linkUnderCursor, int cardIndex) {
        Cardinality[] cardinalities = Cardinality.values();
        linkUnderCursor.setCard(cardinalities[cardIndex].toString());

        String associationName = linkUnderCursor.getAssociationView().getName();
        Entity entity = mcdGraph.containsEntity(linkUnderCursor.getEntityView().getName());

        Association association = this.mcdGraph.containsAssociation(associationName);
        association.setCard(entity, cardinalities[cardIndex]);

        System.out.println(mcdGraph);
    }
}
