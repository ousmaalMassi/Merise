package com.MeriseGUI.mpd;

import com.MeriseGUI.GraphController;
import com.graphics.GLink;
import com.models.mpd.MPDGraph;
import com.graphics.mpd.GMPDTable;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MPDGraphController  extends GraphController<GMPDTable, GLink> {
    
    public MPDGraphController() {
    }

    public void draw(Graphics2D graphics2D) {
        links.forEach(edge -> edge.draw(graphics2D));
        nodes.forEach(node -> node.draw(graphics2D));
    }

    @Override
    public void remove(GMPDTable node) {

    }

    @Override
    public void rename(GMPDTable node, String newName) {

    }

    @Override
    public void addLink(GMPDTable node1, GMPDTable node2) {

    }

    @Override
    public void addNode(GMPDTable node) {

    }

    @Override
    public void removeLink(GLink link) {

    }

    public void printMPD(MPDGraph mpdGraph){
        nodes.clear();
        links.clear();

        AtomicInteger x1 = new AtomicInteger(30);
        AtomicInteger y1 = new AtomicInteger(30);

        mpdGraph.getTables().forEach(table -> {

            if (this.getGraphicalMPDTable(table.getName()) != null)
                return;
            GMPDTable graphicalMPDTable = new GMPDTable(x1.get(), y1.get(), table.getName());
            x1.addAndGet(100);
            y1.addAndGet(100);
            graphicalMPDTable.setPrimaryKey(table.getPrimaryAllKeys());
            table.getPropertyList().forEach(property -> graphicalMPDTable.getAttributes().add(property));
            table.getForeignKeys().forEach((foreignKey, table1) -> {
                GMPDTable gTbl = getGraphicalMPDTable(table1.getName());
                if (gTbl == null)
                    gTbl = new GMPDTable(500, 500, table1.getName());
                GLink gLink = new GLink(graphicalMPDTable, gTbl);
                links.add(gLink);
                graphicalMPDTable.addForeignKeys(foreignKey);
            });
            nodes.add(graphicalMPDTable);
        });
    }

    public GMPDTable getGraphicalMPDTable(String name) {
        return this.nodes.stream().filter(table -> table.getName().equals(name))
                .findAny().orElse(null);
    }

    }
