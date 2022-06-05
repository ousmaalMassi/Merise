package com.MeriseGUI.mpd;

import com.mpd.MPDGraph;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MPDGraphDrawer {

    private List<GraphicalMPDTable> graphicalMPDTables;
    private List<GraphicalMPDLink> graphicalMPDLinks;

    public MPDGraphDrawer() {
        graphicalMPDTables = new LinkedList<>();
        graphicalMPDLinks = new LinkedList<>();
    }

    public void draw(Graphics2D graphics2D) {
        graphicalMPDLinks.forEach(edge -> edge.draw(graphics2D));
        graphicalMPDTables.forEach(node -> node.draw(graphics2D));
    }

    public void makeMPD (MPDGraph mpdGraph){
        graphicalMPDTables.clear();
        graphicalMPDLinks.clear();

        AtomicInteger x1 = new AtomicInteger(30);
        AtomicInteger y1 = new AtomicInteger(30);

        mpdGraph.getTables().forEach(table -> {
            System.out.println(table.getForeignKeys());
            if (this.getGraphicalMPDTable(table.getName()) != null)
                return;
            GraphicalMPDTable graphicalMPDTable = new GraphicalMPDTable(x1.get(), y1.get(), table.getName());
            x1.addAndGet(100);
            y1.addAndGet(100);
            graphicalMPDTable.setPrimaryKey(table.getPrimaryKeys());
            table.getPropertyList().forEach(property -> graphicalMPDTable.getAttributes().add(property.getName()));
            table.getForeignKeys().forEach((foreignKey, table1) -> {
                GraphicalMPDTable gTbl = getGraphicalMPDTable(table1.getName());
                if (gTbl == null)
                    gTbl = new GraphicalMPDTable(500, 500, table1.getName());
                GraphicalMPDLink graphicalMPDLink = new GraphicalMPDLink(graphicalMPDTable, gTbl);
                graphicalMPDLinks.add(graphicalMPDLink);
                graphicalMPDTable.addForeignKeys(foreignKey);
            });
            graphicalMPDTables.add(graphicalMPDTable);
        });
    }


    public GraphicalMPDTable getGraphicalMPDTable(String name) {
        return this.graphicalMPDTables.stream().filter(table -> table.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public GraphicalMPDTable contains(int x, int y) {
        return this.graphicalMPDTables.stream().filter(table -> table.contains(x, y))
                .findAny()
                .orElse(null);
    }
}
