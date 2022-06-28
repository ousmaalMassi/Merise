package com.MeriseGUI.mpd;

import com.MeriseGUI.GraphController;
import com.graphics.GraphicalLink;
import com.models.mpd.MPDGraph;
import com.graphics.mpd.GraphicalMPDTable;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MPDGraphController  extends GraphController<GraphicalMPDTable, GraphicalLink> {
    
    public MPDGraphController() {
    }

    public void draw(Graphics2D graphics2D) {
        nodes.forEach(edge -> edge.draw(graphics2D));
        links.forEach(node -> node.draw(graphics2D));
    }

    @Override
    public void remove(GraphicalMPDTable node) {

    }

    @Override
    public void rename(GraphicalMPDTable node, String newName) {

    }

    @Override
    public void addLink(GraphicalMPDTable node1, GraphicalMPDTable node2) {

    }

    @Override
    public void addNode(GraphicalMPDTable node) {

    }

    @Override
    public void removeLink(GraphicalLink link) {

    }

    public void printMPD(MPDGraph mpdGraph){
        nodes.clear();
        links.clear();

        AtomicInteger x1 = new AtomicInteger(30);
        AtomicInteger y1 = new AtomicInteger(30);

        mpdGraph.getTables().forEach(table -> {

            if (this.getGraphicalMPDTable(table.getName()) != null)
                return;
            GraphicalMPDTable graphicalMPDTable = new GraphicalMPDTable(x1.get(), y1.get(), table.getName());
            x1.addAndGet(100);
            y1.addAndGet(100);
            graphicalMPDTable.setPrimaryKey(table.getPrimaryAllKeys());
            table.getPropertyList().forEach(property -> graphicalMPDTable.getAttributes().add(property));
            table.getForeignKeys().forEach((foreignKey, table1) -> {
                GraphicalMPDTable gTbl = getGraphicalMPDTable(table1.getName());
                if (gTbl == null)
                    gTbl = new GraphicalMPDTable(500, 500, table1.getName());
                GraphicalLink graphicalLink = new GraphicalLink(graphicalMPDTable, gTbl);
                links.add(graphicalLink);
                graphicalMPDTable.addForeignKeys(foreignKey);
            });
            nodes.add(graphicalMPDTable);
        });
    }

    public GraphicalMPDTable getGraphicalMPDTable(String name) {
        return this.nodes.stream().filter(table -> table.getName().equals(name))
                .findAny().orElse(null);
    }

    }
