package com.MeriseGUI.mcd;

import com.MeriseGUI.GraphController;
import com.graphics.GNode;
import com.MeriseGUI.ddd.DDPanel;
import com.graphics.mcd.GAssociation;
import com.graphics.mcd.GEntity;
import com.graphics.mcd.GMCDLink;
import com.graphics.mcd.GMCDNode;
import com.models.EntityObject;
import com.models.Property;
import com.exceptions.DuplicateMeriseObject;
import com.models.mcd.*;

import java.awt.*;
import java.util.*;

public class MCDGraphController extends GraphController<GMCDNode, GMCDLink> {
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
    public void remove(GMCDNode graphicalNode) {
        if (graphicalNode == null)
            return;
        if (graphicalNode instanceof GEntity)
            mcdGraph.removeEntity(mcdGraph.containsEntity(graphicalNode.getName()));
        else
            mcdGraph.removeAssociation(mcdGraph.containsAssociation(graphicalNode.getName()));
        this.nodes.remove(graphicalNode);
        this.removeAttachedLinks(graphicalNode);
        System.out.println(mcdGraph);
    }

    @Override
    public void rename(GMCDNode node, String newName) {
        if (node == null)
            return;
        EntityObject entityObject;
        if (node instanceof GEntity)
            entityObject = mcdGraph.containsEntity(node.getName());
        else
            entityObject = mcdGraph.containsAssociation(node.getName());
        entityObject.setName(newName);
        node.setName(newName);

        System.out.println(mcdGraph);
    }

    @Override
    public void addLink(GMCDNode gn1, GMCDNode gn2) {
        Entity entity = mcdGraph.containsEntity(gn1.getName());
        Association association = mcdGraph.containsAssociation(gn2.getName());

        mcdGraph.link(entity, association);

        GMCDLink graphicalLink = new GMCDLink((GEntity) gn1, (GAssociation) gn2);
        graphicalLink.setText(Cardinality.DEFAULT_CARDINALITY.toString());
        this.links.add(graphicalLink);

        System.out.println(mcdGraph);
    }

    @Override
    public void addNode(GMCDNode node) {
        switch (node){
            case GEntity GEntity -> addEntity(GEntity);
            case GAssociation GAssociation -> addAssociation(GAssociation);
            default -> throw new IllegalStateException("Unexpected value: " + node);
        }
    }

    @Override
    public void removeLink(GMCDLink link) {
        GNode associationView = link.getAssociationView();
        GNode entityView = link.getEntityView();
        Entity entity = mcdGraph.containsEntity(entityView.getName());

        this.mcdGraph.unlink(associationView.getName(), entity);
        this.links.remove(link);

        System.out.println(mcdGraph);
    }

    private void addEntity(GEntity entityGUI) {
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

    public void addProperty(String propertyName, GMCDNode mcdNodeView) {

//        long currentTimeMillis = System.currentTimeMillis();
        EntityObject entityObject;
        if (mcdNodeView instanceof GEntity)
            entityObject = mcdGraph.containsEntity(mcdNodeView.getName());
        else
            entityObject = mcdGraph.containsAssociation(mcdNodeView.getName());

        Map<String, String> map = DDPanel.getProperty(propertyName);
        String name = map.get("name");
        Property.Types type = Property.Types.valueOf(map.get("type"));
        int length = Integer.parseInt(map.get("length"));

        Property property =  new Property(name, type, length);
        entityObject.addProperty(property);

        mcdNodeView.getAttributes().add(property.getCode());
        System.out.println(mcdGraph);
//        System.out.println(System.currentTimeMillis() - currentTimeMillis+" ms");
    }

    public void removeProperty(String name, GMCDNode mcdNodeView) {

        EntityObject entityObject;
        if (mcdNodeView instanceof GEntity)
            entityObject = mcdGraph.containsEntity(mcdNodeView.getName());
        else
            entityObject = mcdGraph.containsAssociation(mcdNodeView.getName());
        Property property = entityObject.getPropertyList().stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);
        if (property == null)
            return;
        entityObject.removeProperty(property);
        mcdNodeView.getAttributes().remove(property.getCode());
    }

    private void removeAttachedLinks(GMCDNode nodeUnderCursor) {
        this.links.removeIf(e -> e.getNodeA().equals(nodeUnderCursor) || e.getNodeB().equals(nodeUnderCursor) );
    }

    private void addAssociation(GAssociation GAssociation) {
        int AssociationNo = mcdGraph.getAssociations().size();
        String associationName = "Association "+AssociationNo;
        Association association = new Association(associationName);
        GAssociation.setName(associationName);
        try {
            mcdGraph.addAssociation(association);
            this.nodes.add(GAssociation);
        } catch (DuplicateMeriseObject e) {
            throw new RuntimeException(e);
        }
        System.out.println(mcdGraph);
    }

    public void editCard(GMCDLink linkUnderCursor, int cardIndex) {
        Cardinality[] cardinalities = Cardinality.values();
        linkUnderCursor.setText(cardinalities[cardIndex].toString());

        String associationName = linkUnderCursor.getAssociationView().getName();
        Entity entity = mcdGraph.containsEntity(linkUnderCursor.getEntityView().getName());

        Association association = this.mcdGraph.containsAssociation(associationName);
        association.setCard(entity, cardinalities[cardIndex]);

        System.out.println(mcdGraph);
    }

}
