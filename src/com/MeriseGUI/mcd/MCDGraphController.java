package com.MeriseGUI.mcd;

import com.MeriseGUI.GraphController;
import com.MeriseGUI.ddd.DDPanel;
import com.NamesGenerator;
import com.exceptions.DuplicateMeriseObject;
import com.graphics.GNode;
import com.graphics.mcd.GMCDLink;
import com.graphics.mcd.GMCDNode;
import com.graphics.mcd.MCDNodeType;
import com.models.EntityObject;
import com.models.Property;
import com.models.mcd.Association;
import com.models.mcd.Cardinality;
import com.models.mcd.Entity;
import com.models.mcd.MCDGraph;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MCDGraphController extends GraphController<GMCDNode, GMCDLink> {
    private MCDGraph mcdGraph;

    public MCDGraphController() {
    }

    public MCDGraph getGraph() {
        return mcdGraph;
    }

    public void setGraph(MCDGraph mcdGraph) {
        this.mcdGraph = mcdGraph;
    }

    public void convertMCDGraph(int centerX, int centerY) {
        this.nodes.clear();
        this.links.clear();

        this.mcdGraph.getEntities().forEach(entity -> this.nodes.add(this.convertEntity(entity, centerX, centerY)));

        this.mcdGraph.getAssociations().forEach(association -> {
            GMCDNode gAssociation = new GMCDNode(centerX, centerY, association.getName(), MCDNodeType.ASSOCIATION);
            gAssociation.setAttributes(association.getPropertyList().stream().map(Property::getName).collect(Collectors.toList()));
            this.nodes.add(gAssociation);
            association.getLinks().forEach((entity, cardinality) -> {
                GMCDNode gEntity = this.nodes.stream().filter(gmcdNode -> gmcdNode.getName().equals(entity.getName())).findFirst().orElse(null);
                GMCDLink gmcdLink = new GMCDLink(gEntity, gAssociation);
                gmcdLink.setText(cardinality.toString());
                this.links.add(gmcdLink);
            });
        });
    }

    public GMCDNode convertEntity(Entity entity, int centerX, int centerY) {
        GMCDNode gEntity = new GMCDNode(centerX, centerY, entity.getName(), MCDNodeType.ENTITY);
        List<Property> propertyList = entity.getPropertyList();
        List<String> properties = propertyList.stream().map(Property::getName).collect(Collectors.toList());
        gEntity.setAttributes(properties);
        return gEntity;
    }

    @Override
    public void printGraph(Graphics2D graphics2D) {
        this.links.forEach(edge -> edge.draw(graphics2D));
        this.nodes.forEach(node -> node.draw(graphics2D));
    }

    @Override
    public void removeNode(GMCDNode graphicalNode) {
        if (graphicalNode == null)
            return;
        if (graphicalNode.getType().equals(MCDNodeType.ENTITY))
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
        if (node.getType().equals(MCDNodeType.ENTITY))
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

        GMCDLink graphicalLink = new GMCDLink(gn1, gn2);
        graphicalLink.setText(Cardinality.DEFAULT_CARDINALITY.toString());
        this.links.add(graphicalLink);

        System.out.println(mcdGraph);
    }

    @Override
    public void removeLink(GMCDLink link) {
        GNode associationView = link.getGAssociation();
        GNode entityView = link.getGEntity();
        Entity entity = mcdGraph.containsEntity(entityView.getName());

        this.mcdGraph.unlink(associationView.getName(), entity);
        this.links.remove(link);

        System.out.println(mcdGraph);
    }

    @Override
    public void addNode(GMCDNode node) {
        String nodeName;
        try {
            if (node.getType().equals(MCDNodeType.ENTITY)) {
                nodeName = NamesGenerator.generateName(mcdGraph.getEntities(), "Entity");
                Entity entity = new Entity(nodeName);
                mcdGraph.addEntity(entity);
            } else {
                nodeName = NamesGenerator.generateName(mcdGraph.getEntities(), "Association");
                Association association = new Association(nodeName);
                mcdGraph.addAssociation(association);
            }
        } catch (DuplicateMeriseObject e) {
            throw new RuntimeException(e);
        }

        node.setName(nodeName);
        this.nodes.add(node);

        System.out.println(mcdGraph);
    }

    public void addProperty(String propertyName, GMCDNode mcdNodeView) {

//        long currentTimeMillis = System.currentTimeMillis();
        EntityObject entityObject = mcdNodeView.getType().equals(MCDNodeType.ENTITY)
                ? mcdGraph.containsEntity(mcdNodeView.getName())
                : mcdGraph.containsAssociation(mcdNodeView.getName());

        Property property =DDPanel.getProperty(propertyName);
        entityObject.addProperty(property);
        mcdNodeView.getAttributes().add(property.getCode());

        System.out.println(mcdGraph);
//        System.out.println(System.currentTimeMillis() - currentTimeMillis+" ms");
    }

    public void removeProperty(String name, GMCDNode mcdNodeView) {

        EntityObject entityObject = mcdNodeView.getType().equals(MCDNodeType.ENTITY)
                ? mcdGraph.containsEntity(mcdNodeView.getName())
                : mcdGraph.containsAssociation(mcdNodeView.getName());
        Property property = entityObject.getPropertyList().stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);
        if (property == null)
            return;
        entityObject.removeProperty(property);
        mcdNodeView.getAttributes().remove(property.getCode());
    }

    private void removeAttachedLinks(GMCDNode nodeUnderCursor) {
        this.links.removeIf(e -> e.getNodeA().equals(nodeUnderCursor) || e.getNodeB().equals(nodeUnderCursor));
    }

    public void editCard(GMCDLink linkUnderCursor, int cardIndex) {
        Cardinality[] cardinalities = Cardinality.values();
        linkUnderCursor.setText(cardinalities[cardIndex].toString());

        String associationName = linkUnderCursor.getGAssociation().getName();
        Entity entity = mcdGraph.containsEntity(linkUnderCursor.getGEntity().getName());

        Association association = this.mcdGraph.containsAssociation(associationName);
        association.setCard(entity, cardinalities[cardIndex]);

        System.out.println(mcdGraph);
    }
}
