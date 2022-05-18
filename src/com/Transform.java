package com;

import com.mcd.*;
import com.mld.MLDGraph;
import com.mld.MLDTable;
import com.mpd.MPDGraph;
import com.mpd.MPDTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Transform {

    private final MLDGraph mldGraph;
    private final MPDGraph mpdGraph;
    private final List<String> foreignKeyConstraint;

    public Transform() {
        mldGraph = new MLDGraph();
        mpdGraph = new MPDGraph();
        foreignKeyConstraint = new ArrayList<>();
    }

    public MLDGraph mcdToMld(MCDGraph mcdGraph) {

        mcdGraph.getEntities().forEach(entity -> mldGraph.getTables().add(createMLDTable(entity)));

        mcdGraph.getAssociations().forEach(association -> {
            if (!association.getPropertyList().isEmpty() || association.getLinks().size() > 2) {
                createTableFromAssociation(association);
            } else {
                Map<String, Cardinalities> links = association.getLinks();
                List<String> entities = new ArrayList<>(association.getLinks().keySet());
                MLDTable tableA = mldGraph.search(entities.get(0));
                MLDTable tableB = mldGraph.search(entities.get(1));

                if (links.get(entities.get(0)).toString().endsWith("n") && links.get(entities.get(1)).toString().endsWith("n")) {
                    createTableFromAssociation(association);
                } else if (links.get(entities.get(0)).toString().endsWith("1") && links.get(entities.get(1)).toString().endsWith("1")) {
                    tableB.addForeignKey(tableA);
                } else {
                    if (links.get(entities.get(0)).toString().endsWith("n")) {
                        tableB.addForeignKey(tableA);
                    } else {
                        tableA.addForeignKey(tableB);
                    }
                }
            }
        });

        return mldGraph;
    }

    private void createTableFromAssociation(Association association) {
        MLDTable associationTable = createMLDTable(association);
        association.getLinks().forEach((entityName, cardinality) -> associationTable.addForeignKey(mldGraph.search(entityName)));
        mldGraph.getTables().add(associationTable);
    }

    private Property duplicateProperty(Property pkProp) {
        Property fkProp = new Property();
        fkProp.setName(pkProp.getCode());
        fkProp.setType(pkProp.getType());
        fkProp.setLength(pkProp.getLength());
        fkProp.setConstraints(List.of(Property.Constraints.PRIMARY_KEY));
        return fkProp;
    }

    /**
     * @param node to convert to table
     * @return MLD table
     */
    private MLDTable createMLDTable(MeriseObject node) {
        MLDTable table = new MLDTable(node.getName());
        table.setPropertyList(node.getPropertyList());
        return table;
    }

    public MPDGraph mldToMpd(MLDGraph mldGraph) {
//        this.mldGraph.getTables().forEach(mldTable -> {
//            MPDTable mpdTable = new MPDTable(mldTable.getName());
//            mpdTable.addPrimaryKey(mldTable.getPrimaryKey());
//            mpdTable.getPropertyList().forEach(property -> mpdTable.addProperty(property));
//            mldTable.getForeignKeys().forEach((s, mldTable1) -> );
//            this.mpdGraph.getTables().add(mpdTable);
//
//        });
//        return this.mpdGraph;
        this.mldGraph.getTables().forEach(mldTable -> {
            this.mpdGraph.getTables().add(mldTable);
        });
        return this.mpdGraph;
    }

    public String mpdToSQL(MLDGraph mldGraph) {
        //TODO specify the targeted DBMS
        StringBuilder stringBuilder = new StringBuilder();
        mldGraph.getTables().forEach(table -> stringBuilder
                .append("DROP TABLE IF EXISTS `").append(table.getName()).append("`;\n")
                .append("CREATE TABLE `").append(table.getName()).append("` (\n")
                .append(this.createColumn(table))
                .append(") ").append("ENGINE=InnoDB;").append("\n\n")
        );
        stringBuilder.append(String.join(",\n", this.foreignKeyConstraint));
        return stringBuilder.toString();
    }

    private String createColumn(MLDTable table) {
        List<String> list = new ArrayList<>();
        table.getPropertyList().forEach(property -> list.add(
                "`" + property.getCode() + "`" +
                " " + property.getType().toString() +
                " (" + property.getLength() + ")" +
                " " + this.createConstraints(property.getConstraints())
        ));
        list.add(this.createPrimaryKey(table.getName(), table.getPrimaryKey()));
        if (!table.getForeignKeys().isEmpty())
            this.foreignKeyConstraint.add(this.createForeignKeyColumn(table.getName(), table.getForeignKeys()));
        return String.join(",\n", list);
    }

    private String createPrimaryKey(String tableName, Property primaryKey) {
        //TODO remove AutoIncrements constraint from Association table and primaryKey can contain multiple properties
        return "CONSTRAINT PK_"+tableName+" ADD PRIMARY KEY ("+primaryKey.getCode()+")";
    }

    private String createConstraints(List<Property.Constraints> constraints) {
        List<String> list = new ArrayList<>();
        constraints.forEach(
                constraint -> list.add(constraint.toString())
        );
        return String.join(" ", list);
    }

    private String createForeignKeyColumn(String tableName, Map<String, MLDTable> foreignKeys) {
        List<String> list = new ArrayList<>();
            foreignKeys.forEach((prop, tableRef) -> list.add(
                    "ALTER TABLE " + tableName + " FOREIGN KEY (`" + prop + "`) REFERENCES " + tableRef.getName() + "(`" + prop + "`)")
            );
        return String.join(",\n", list);
    }
}
