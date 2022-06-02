package com;

import com.mcd.*;
import com.mld.MLDGraph;
import com.mld.MLDTable;
import com.mpd.MPDGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                return;
            }

            Map<Entity, Cardinality> links = association.getLinks();
            Set<Entity> entities = association.getLinks().keySet();

            Entity entity1 = entities.iterator().next();
            Entity entity2 = entities.iterator().next();

            MLDTable table1 = mldGraph.search(entity1.getName());
            MLDTable table2 = mldGraph.search(entity2.getName());

            Cardinality Cardinality1 = links.get(entity1);
            Cardinality Cardinality2 = links.get(entity2);

            if ((Cardinality1.equals(com.mcd.Cardinality.ZERO_MANY) || Cardinality1.equals(com.mcd.Cardinality.ONE_MANY))
            && (Cardinality2.equals(com.mcd.Cardinality.ZERO_MANY) || Cardinality2.equals(com.mcd.Cardinality.ONE_MANY))) {
                createTableFromAssociation(association);
            }
            else if ((Cardinality1.equals(Cardinality.ZERO_ONE) || Cardinality1.equals(Cardinality.ONE_ONE))
                    && (Cardinality2.equals(Cardinality.ZERO_ONE) || Cardinality2.equals(com.mcd.Cardinality.ONE_ONE))) {
                table2.addForeignKey(table1);
            }
            else {
                if (Cardinality1.equals(com.mcd.Cardinality.ZERO_MANY) || Cardinality1.equals(com.mcd.Cardinality.ONE_MANY)) {
                    table2.addForeignKey(table1);
                } else {
                    table1.addForeignKey(table2);
                }
            }

        });

        return mldGraph;
    }

    private void createTableFromAssociation(Association association) {
        MLDTable associationTable = createMLDTable(association);
        association.getLinks().forEach((entity, cardinality) -> {
            associationTable.addPrimaryKey(entity.getPropertyList().get(0));
            associationTable.addForeignKey(mldGraph.search(entity.getName()));
        });
        associationTable.getPropertyList().addAll(associationTable.getPrimaryKeys());
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
//            mpdTable.addPrimaryKey(mldTable.getPrimaryKeys());
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

    public String mpdToSQL(MPDGraph mpdGraph) {
        //TODO specify the targeted DBMS
        StringBuilder stringBuilder = new StringBuilder();
        mpdGraph.getTables().forEach(table -> stringBuilder
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
        list.add(this.createPrimaryKey(table.getName(), table.getPrimaryKeys()));
        if (!table.getForeignKeys().isEmpty())
            this.foreignKeyConstraint.add(this.createForeignKeyColumn(table.getName(), table.getForeignKeys()));
        return String.join(",\n", list);
    }

    private String createPrimaryKey(String tableName, List<Property> primaryKeys) {
        List<String> list = new ArrayList<>();
        primaryKeys.forEach(primaryKey ->
                list.add("CONSTRAINT PK_" + tableName + " ADD PRIMARY KEY (" + primaryKey.getCode() + ")")
        );
        return String.join(",\n", list);
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
