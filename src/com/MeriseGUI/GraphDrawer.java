package com.MeriseGUI;

import com.MeriseGUI.mcd.GraphicalMCDLink;
import com.MeriseObject;
import com.exception.DuplicateMeriseObject;
import com.mcd.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

public class GraphDrawer {

    private List<MCDNodeView> nodes;
    private List<GraphicalLink> edges;
    private MCDGraph mcdGraph;

    public GraphDrawer() {
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

    public void addAttribute(MCDNodeView mcdNodeView) {

        long currentTimeMillis = System.currentTimeMillis();
        MeriseObject meriseObject;
        if (mcdNodeView instanceof EntityView)
            meriseObject = mcdGraph.containsEntity(mcdNodeView.getName());
        else
            meriseObject = mcdGraph.containsAssociation(mcdNodeView.getName());

        Property property =  new Property("identifier", Property.Types.DIGITAL, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENT));
        meriseObject.addProperty(property);

        mcdNodeView.getAttributes().add(property.name);
        System.out.println(mcdGraph);
        System.out.println(System.currentTimeMillis() - currentTimeMillis+" ms");
    }

    public void remove(MCDNodeView mcdNodeView) {
        if (mcdNodeView == null)
            return;
        if (mcdNodeView instanceof EntityView)
            mcdGraph.removeEntity(mcdGraph.containsEntity(mcdNodeView.getName()));
        else
            mcdGraph.removeAssociation(mcdGraph.containsAssociation(mcdNodeView.getName()));
        this.nodes.remove(mcdNodeView);
        System.out.println(mcdGraph);
    }

    public void update(EntityView entityGUI) {
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

    public MCDNodeView contains(int x, int y) {
        return this.nodes.stream().filter(node -> node.contains(x, y))
                .findAny()
                .orElse(null);
    }

    public void addLink(EntityView entityToLink, AssociationView associationToLink) {
        Entity entity = mcdGraph.containsEntity(entityToLink.getName());
        Association association = mcdGraph.containsAssociation(associationToLink.getName());

        mcdGraph.link(entity, association);

        GraphicalMCDLink graphicalLink = new GraphicalMCDLink(entityToLink, associationToLink);
        graphicalLink.setCard(Cardinalities.DEFAULT_CARDINALITY.toString());
        this.edges.add(graphicalLink);

        System.out.println(mcdGraph);
    }
}
