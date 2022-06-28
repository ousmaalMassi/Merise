package com.MeriseGUI.mcd;

import com.MeriseGUI.GraphController;
import com.graphics.GraphicalNode;
import com.MeriseGUI.ddd.DDPanel;
import com.graphics.mcd.AssociationView;
import com.graphics.mcd.EntityView;
import com.graphics.mcd.GraphicalMCDLink;
import com.graphics.mcd.GraphicalMCDNode;
import com.models.MeriseObject;
import com.models.Property;
import com.exceptions.DuplicateMeriseObject;
import com.models.mcd.*;

import java.awt.*;
import java.util.*;

public class MCDGraphController extends GraphController<GraphicalMCDNode, GraphicalMCDLink> {
    private MCDGraph mcdGraph;

    public MCDGraphController() {
    }

    public void setMcdGraph(MCDGraph mcdGraph) {
        this.mcdGraph = mcdGraph;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        this.links.forEach(edge -> edge.draw(graphics2D));
        this.nodes.forEach(node -> node.draw(graphics2D));
    }

    @Override
    public void remove(GraphicalMCDNode graphicalNode) {
        if (graphicalNode == null)
            return;
        if (graphicalNode instanceof EntityView)
            mcdGraph.removeEntity(mcdGraph.containsEntity(graphicalNode.getName()));
        else
            mcdGraph.removeAssociation(mcdGraph.containsAssociation(graphicalNode.getName()));
        this.nodes.remove(graphicalNode);
        this.removeAttachedLinks(graphicalNode);
        System.out.println(mcdGraph);
    }

    @Override
    public void rename(GraphicalMCDNode node, String newName) {
        if (node == null)
            return;
        MeriseObject meriseObject;
        if (node instanceof EntityView)
            meriseObject = mcdGraph.containsEntity(node.getName());
        else
            meriseObject = mcdGraph.containsAssociation(node.getName());
        meriseObject.setName(newName);
        node.setName(newName);

        System.out.println(mcdGraph);
    }

    @Override
    public void addLink(GraphicalMCDNode gn1, GraphicalMCDNode gn2) {
        Entity entity = mcdGraph.containsEntity(gn1.getName());
        Association association = mcdGraph.containsAssociation(gn2.getName());

        mcdGraph.link(entity, association);

        GraphicalMCDLink graphicalLink = new GraphicalMCDLink((EntityView) gn1, (AssociationView) gn2);
        graphicalLink.setText(Cardinality.DEFAULT_CARDINALITY.toString());
        this.links.add(graphicalLink);

        System.out.println(mcdGraph);
    }

    @Override
    public void addNode(GraphicalMCDNode node) {
        switch (node){
            case EntityView entityView -> addEntity(entityView);
            case AssociationView associationView -> addAssociation(associationView);
            default -> throw new IllegalStateException("Unexpected value: " + node);
        }
    }

    @Override
    public void removeLink(GraphicalMCDLink link) {
        GraphicalNode associationView = link.getAssociationView();
        GraphicalNode entityView = link.getEntityView();
        Entity entity = mcdGraph.containsEntity(entityView.getName());

        this.mcdGraph.unlink(associationView.getName(), entity);
        this.links.remove(link);

        System.out.println(mcdGraph);
    }

    private void addEntity(EntityView entityGUI) {
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

        Property property =  new Property(name, type, length);
        meriseObject.addProperty(property);

        mcdNodeView.getAttributes().add(property.getCode());
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
        mcdNodeView.getAttributes().remove(property.getCode());
    }

    private void removeAttachedLinks(GraphicalMCDNode nodeUnderCursor) {
        this.links.removeIf(e -> e.getNodeA().equals(nodeUnderCursor) || e.getNodeB().equals(nodeUnderCursor) );
    }

    private void addAssociation(AssociationView associationView) {
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

    public void editCard(GraphicalMCDLink linkUnderCursor, int cardIndex) {
        Cardinality[] cardinalities = Cardinality.values();
        linkUnderCursor.setText(cardinalities[cardIndex].toString());

        String associationName = linkUnderCursor.getAssociationView().getName();
        Entity entity = mcdGraph.containsEntity(linkUnderCursor.getEntityView().getName());

        Association association = this.mcdGraph.containsAssociation(associationName);
        association.setCard(entity, cardinalities[cardIndex]);

        System.out.println(mcdGraph);
    }

}
